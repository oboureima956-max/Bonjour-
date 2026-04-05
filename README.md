# Android CI/CD

![Android CI/CD](https://oboureima956@gmail.com>/<github>/actions/workflows/android-ci.yml/badge.svg)
# ================================================================
# 🤖 ANDROID CI/CD — Pipeline complet
# Déclencheurs : push sur principal, pull_request, tag v*
# ================================================================

name: Android CI/CD

on:
  push:
    branches: [ "principal" ]        # ← votre branche principale
    tags:
      - "v*"                         # ex: v1.0.0 → déclenche la release
  pull_request:
    branches: [ "principal" ]
  workflow_dispatch:                 # déclenchement manuel possible

# ---------------------------------------------------------------
# Variables globales réutilisées dans tous les jobs
# ---------------------------------------------------------------
env:
  JAVA_VERSION: '17'
  JAVA_DISTRIBUTION: 'temurin'

# ================================================================
# ✅ JOB 1 — BUILD & TEST (lint + tests unitaires)
# ================================================================
jobs:
  build-and-test:
    name: 🧪 Build & Test
    runs-on: ubuntu-latest

    steps:
      - name: 📥 Checkout du code
        uses: actions/checkout@v4

      - name: ☕ Configurer JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: ${{ env.JAVA_VERSION }}
          distribution: ${{ env.JAVA_DISTRIBUTION }}
          cache: gradle

      - name: 🔐 Permissions Gradle
        run: chmod +x ./gradlew

      - name: 📝 Créer local.properties
        run: echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties

      - name: 🗂️ Cache Gradle
        uses: actions/cache@v4
        with:
          path: |
            ~/.gradle/caches
            ~/.gradle/wrapper
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
          restore-keys: |
            ${{ runner.os }}-gradle-

      - name: 🔍 Analyse Lint
        run: ./gradlew lintDebug
        continue-on-error: true

      - name: 🧪 Tests unitaires
        run: ./gradlew testDebugUnitTest

      - name: 📊 Upload rapport Lint
        uses: actions/upload-artifact@v4
        if: always()
        with:
          name: lint-results
          path: app/build/reports/lint-results-*.html

      - name: 🏗️ Build Debug APK
        run: ./gradlew assembleDebug

      - name: 📤 Upload APK Debug
        uses: actions/upload-artifact@v4
        with:
          name: app-debug.apk
          path: app/build/outputs/apk/debug/app-debug.apk

# ================================================================
# ✅ JOB 2 — BUILD RELEASE (signé avec keystore)
# ================================================================
  build-release:
    name: 🚀 Build Release (APK + AAB signés)
    runs-on: ubuntu-latest
    needs: build-and-test
    if: github.ref == 'refs/heads/principal'

    steps:
      - name: 📥 Checkout du code
        uses: actions/checkout@v4

      - name: ☕ Configurer JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: ${{ env.JAVA_VERSION }}
          distribution: ${{ env.JAVA_DISTRIBUTION }}
          cache: gradle

      - name: 🔐 Permissions Gradle
        run: chmod +x ./gradlew

      - name: 📝 Créer local.properties
        run: echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties

      - name: 🗂️ Cache Gradle
        uses: actions/cache@v4
        with:
          path: |
            ~/.gradle/caches
            ~/.gradle/wrapper
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*') }}

      - name: 🔑 Décoder le Keystore
        run: |
          echo "${{ secrets.SIGNING_KEY }}" | base64 --decode > app/release-keystore.jks

      - name: 📦 Build APK Release
        env:
          KEYSTORE_FILE: release-keystore.jks
          KEYSTORE_PASSWORD: ${{ secrets.KEYSTORE_PASSWORD }}
          KEY_ALIAS: ${{ secrets.KEY_ALIAS }}
          KEY_PASSWORD: ${{ secrets.KEY_PASSWORD }}
        run: ./gradlew assembleRelease

      - name: 📦 Build AAB Release
        env:
          KEYSTORE_FILE: release-keystore.jks
          KEYSTORE_PASSWORD: ${{ secrets.KEYSTORE_PASSWORD }}
          KEY_ALIAS: ${{ secrets.KEY_ALIAS }}
          KEY_PASSWORD: ${{ secrets.KEY_PASSWORD }}
        run: ./gradlew bundleRelease

      - name: 📤 Upload APK Release
        uses: actions/upload-artifact@v4
        with:
          name: app-release.apk
          path: app/build/outputs/apk/release/app-release.apk

      - name: 📤 Upload AAB Release
        uses: actions/upload-artifact@v4
        with:
          name: app-release.aab
          path: app/build/outputs/bundle/release/app-release.aab

# ================================================================
# ✅ JOB 3 — DISTRIBUTION FIREBASE
# ================================================================
  distribute-firebase:
    name: 🔥 Distribution Firebase
    runs-on: ubuntu-latest
    needs: build-release

    steps:
      - name: ⬇️ Télécharger APK Release
        uses: actions/download-artifact@v4
        with:
          name: app-release.apk
          path: app/build/outputs/apk/release/

      - name: 🔥 Envoyer sur Firebase App Distribution
        uses: wzieba/Firebase-Distribution-Github-Action@v1
        with:
          appId: ${{ secrets.FIREBASE_APP_ID }}
          serviceCredentialsFileContent: ${{ secrets.SERVICE_ACCOUNT_JSON }}
          file: app/build/outputs/apk/release/app-release.apk
          releaseNotes: |
            🔖 Build : ${{ github.run_number }}
            📝 Commit : ${{ github.sha }}
            🌿 Branche : ${{ github.ref_name }}

# ================================================================
# ✅ JOB 4 — DÉPLOIEMENT GOOGLE PLAY STORE
# ================================================================
  deploy-play-store:
    name: 🏪 Déploiement Play Store
    runs-on: ubuntu-latest
    needs: build-release
    if: startsWith(github.ref, 'refs/tags/v')

    steps:
      - name: ⬇️ Télécharger AAB Release
        uses: actions/download-artifact@v4
        with:
          name: app-release.aab
          path: app/build/outputs/bundle/release/

      - name: 🏪 Upload sur Google Play (canal interne)
        uses: r0adkll/upload-google-play@v1
        with:
          serviceAccountJsonPlainText: ${{ secrets.PLAY_STORE_SERVICE_ACCOUNT_JSON }}
          packageName: com.votreentreprise.votreapp   # ← adapter
          releaseFiles: app/build/outputs/bundle/release/app-release.aab
          track: internal
          status: completed

# ================================================================
# ✅ JOB 5 — CRÉATION D'UNE GITHUB RELEASE
# ================================================================
  create-github-release:
    name: 🎉 Créer GitHub Release
    runs-on: ubuntu-latest
    needs: build-release
    if: startsWith(github.ref, 'refs/tags/v')

    steps:
      - name: ⬇️ Télécharger APK
        uses: actions/download-artifact@v4
        with:
          name: app-release.apk
          path: ./release/

      - name: ⬇️ Télécharger AAB
        uses: actions/download-artifact@v4
        with:
          name: app-release.aab
          path: ./release/

      - name: 🎉 Créer la Release GitHub
        uses: softprops/action-gh-release@v2
        with:
          generate_release_notes: true
          prerelease: false
          files: |
            release/app-release.apk
            release/app-release.aab