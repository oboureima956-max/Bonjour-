package com.neuralos.app

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.neuralos.app.ai.TFLiteEngine
import com.neuralos.app.eeg.EEGManager
import com.neuralos.app.eeg.EEGSimulator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        setContentView(webView)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("file:///android_asset/web/index.html")

        val engine = TFLiteEngine(this)
        val eegSource = EEGSimulator()
        val manager = EEGManager(eegSource, engine)

        manager.start { result ->
            runOnUiThread {
                webView.evaluateJavascript("onMentalState(${result.joinToString()})", null)
            }
        }
    }
}