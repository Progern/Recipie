package com.olegmisko.recipie.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.olegmisko.recipie.Config.ActivityHelper
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Services.CustomWebViewClient
import kotlinx.android.synthetic.main.activity_browser.*
import org.jetbrains.anko.toast


class BrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityHelper.hideStatusBar(this)
        setContentView(R.layout.activity_browser)
        val customWebViewClient = CustomWebViewClient()

        mainWebView.setWebChromeClient(object  : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                loadPageProgress.progress = newProgress
                if (newProgress == 100) {
                    loadPageProgress.visibility = View.GONE
                }
            }
        })

        mainWebView.settings.javaScriptEnabled = true
        mainWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        mainWebView.setWebViewClient(customWebViewClient)

        val urlToLoad = intent.getStringExtra("URL")
        toast(urlToLoad)
        if (Patterns.WEB_URL.matcher(urlToLoad).matches()) {
            mainWebView.loadUrl(urlToLoad)
        }
    }


}
