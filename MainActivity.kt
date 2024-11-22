package com.example.datatohtmlbyjs

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.webkit.JavascriptInterface

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("file:///android_asset/Data.html")

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Выполняем JavaScript-код
                webView.evaluateJavascript("""
                (function() {
                    var block = document.getElementById('answers_block');
                    if (block) {
                        block.innerHTML = `
                            <div class="answer" name="answer" id="answer_0" status="active" style="opacity: 1;">${getString(R.string.yes)}</div>
                            <div class="answer" name="answer" id="answer_1">${getString(R.string.no)}</div>
                        `;
                        return 'HTML inserted successfully';
                    } else {
                        return 'Element not found';
                    }
                  })();
          """) { result ->
                      //Log.d("MagicBall", "Result: $result")
                }
            }
        }
        webView.addJavascriptInterface(WebAppInterface(this), "Android")
    }
}
