# Bonjour-
api-flask-wiw
<!DOCTYPE html>  <html class="dark" lang="en"><head>  
<meta charset="utf-8"/>  
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>  
<title>Neural Link V1.0</title>  
<script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>  
<link href="https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@300..700&amp;display=swap" rel="stylesheet"/>  
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet"/>  
<script>  
        tailwind.config = {  
            darkMode: "class",  
            theme: {  
                extend: {  
                    colors: {  
                        "primary": "#00BFFF",  
                        "background-light": "#f6f6f8",  
                        "background-dark": "#121212",  
                    },  
                    fontFamily: {  
                        "display": ["Space Grotesk", "sans-serif"]  
                    },  
                    borderRadius: {  
                        "DEFAULT": "0.25rem",  
                        "lg": "0.5rem",  
                        "xl": "0.75rem",  
                        "full": "9999px"  
                    },  
                    animation: {  
                        pulse: 'pulse 3s cubic-bezier(0.4, 0, 0.6, 1) infinite',  
                    },  
                    keyframes: {  
                        pulse: {  
                            '0%, 100%': {  
                                opacity: 1,  
                                transform: 'scale(1)'  
                            },  
                            '50%': {  
                                opacity: 0.7,  
                                transform: 'scale(1.05)'  
                            },  
                        },  
                    },  
                },  
            },  
        }  
    </script>  
<style>  
    body {  
      min-height: max(884px, 100dvh);  
    }  
  </style>  
  </head>  
<body class="bg-background-light dark:bg-background-dark font-display text-[#E0E0E0]">  
<div class="relative flex h-auto min-h-screen w-full flex-col group/design-root overflow-x-hidden">  
<!-- Top App Bar -->  
<div class="flex items-center p-4">  
<button class="text-[#E0E0E0] flex size-10 shrink-0 items-center justify-center rounded-full hover:bg-white/10 transition-colors">  
<span class="material-symbols-outlined text-2xl">arrow_back</span>  
</button>  
<h2 class="text-[#E0E0E0] text-xl font-bold leading-tight tracking-tight flex-1 text-center">Neural Link</h2>  
<button class="text-[#E0E0E0] flex size-10 shrink-0 items-center justify-center rounded-full hover:bg-white/10 transition-colors">  
<span class="material-symbols-outlined text-2xl">menu</span>  
</button>  
</div>  
<!-- Main Content -->  
<main class="flex-grow flex flex-col items-center px-4 pt-8 pb-6">  
<!-- Device Selector -->  
<div class="w-full max-w-sm mb-8">  
<div class="flex items-center justify-between rounded-lg border border-white/20 p-3 bg-white/5">  
<div class="flex flex-col">  
<span class="text-xs text-white/50">SELECTED DEVICE</span>  
<span class="text-[#E0E0E0] font-medium">External Machine ID: XM-7B4</span>  
</div>  
<button class="text-primary hover:text-white transition-colors">  
<span class="material-symbols-outlined text-3xl">expand_circle_down</span>  
</button>  
</div>  
</div>  
<!-- Status Indicator -->  
<div class="flex w-full grow items-center justify-center py-6 @container">  
<div class="relative flex items-center justify-center w-full aspect-square max-w-[280px]">  
<div class="absolute inset-0 bg-primary/10 rounded-full animate-pulse"></div>  
<div class="absolute inset-[15%] bg-primary/20 rounded-full animate-pulse [animation-delay:-0.5s]"></div>  
<div class="absolute inset-[30%] bg-primary/40 rounded-full"></div>  
<div class="absolute w-full h-full bg-center bg-no-repeat bg-contain" style='background-image: url("https://lh3.googleusercontent.com/aida-public/AB6AXuCb25AcgzB-Yxnk6OM1N9M9aXrdQMvBf1U6qjLQr45CuJQTWoPz7Cqu_msEeWBINcMG6Wl8WsT1vLDPo87XvZzPolwMzLM0DFqzRQ4mOZ-UW5yk_AMhi3b7c0oRmtc0GLR3kLfZRaIeob6HzxCy8H4vtnn3HuPUneqd-E-c1sponfM0mJ1F723qTdZK6mUeeTU277wlnFXLXQwFe_Y7XwoNr_gNhsPdNpJHcoExF_7QerDAx3xIMaJfXkkEOZCLXFaJO9sb88T6DUZI"); mix-blend-mode: screen; opacity: 0.3;'></div>  
<div class="relative flex flex-col items-center z-10">  
<h1 class="text-[#39FF14] tracking-wider text-[32px] font-bold leading-tight">CONNECTED</h1>  
<p class="text-white/70 text-sm font-normal leading-normal">Status: Nominal</p>  
</div>  
</div>  
</div>  
<!-- Connection Control Button -->  
<div class="w-full max-w-sm my-8">  
<button class="w-full text-black font-bold py-4 px-6 rounded-lg bg-[#FF073A] hover:bg-red-700 transition-colors shadow-lg shadow-red-500/20">Disconnect</button>  
</div>  
<!-- Real-time Data Panels -->  
<div class="w-full flex flex-wrap gap-4">  
<div class="flex min-w-[158px] flex-1 flex-col gap-2 rounded-xl p-5 border border-white/20 bg-white/5">  
<p class="text-[#E0E0E0] text-base font-medium leading-normal">Signal Strength</p>  
<p class="text-[#39FF14] tracking-light text-3xl font-bold leading-tight">98%</p>  
</div>  
<div class="flex min-w-[158px] flex-1 flex-col gap-2 rounded-xl p-5 border border-white/20 bg-white/5">  
<p class="text-[#E0E0E0] text-base font-medium leading-normal">Data Throughput</p>  
<p class="text-primary tracking-light text-3xl font-bold leading-tight">1.2 <span class="text-xl">Gbps</span></p>  
</div>  
<div class="flex min-w-[158px] flex-1 flex-col gap-2 rounded-xl p-5 border border-white/20 bg-white/5">  
<p class="text-[#E0E0E0] text-base font-medium leading-normal">Latency</p>  
<p class="text-[#FFD700] tracking-light text-3xl font-bold leading-tight">12<span class="text-xl">ms</span></p>  
</div>  
</div>  
<!-- Status Log -->  
<div class="w-full mt-8">  
<details class="group">  
<summary class="list-none flex items-center justify-between cursor-pointer p-2 rounded-md hover:bg-white/10 transition-colors">  
<h3 class="text-lg font-semibold text-white/90">Status Log</h3>  
<span class="material-symbols-outlined transition-transform duration-300 group-open:rotate-180">expand_more</span>  
</summary>  
<div class="mt-4 space-y-3 pl-2 text-sm text-white/70 border-l border-white/20">  
<p><span class="text-white/50">[14:32:01]</span> Connection initiated...</p>  
<p><span class="text-white/50">[14:32:03]</span> Device XM-7B4 found.</p>  
<p><span class="text-white/50">[14:32:04]</span> Handshake successful.</p>  
<p class="text-[#39FF14]"><span class="text-white/50">[14:32:05]</span> Neural link established.</p>  
</div>  
</details>  
</div>  
</main>  
</div>  
</body></html>