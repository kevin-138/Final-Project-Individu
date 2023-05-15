package com.kevin.netkick.presentation.view.home.news.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityArticleBinding
import com.kevin.netkick.presentation.PresentationUtils

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var url: String
    private lateinit var progressBar: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoArticleActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProgressBar()
        receiveIntent()
    }

    private fun setProgressBar() {
        progressBar = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
    }

    private fun receiveIntent() {
        url = if (intent!=null) {
            intent.getStringExtra(PresentationUtils.NEWS_URL)!!
        }else{
            ""
        }
        webViewSetup(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup(url:String) {
        binding.apply {
            wvNews.webViewClient= object: WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                        progressBar.dismiss()
                }
            }

            wvNews.apply {
                loadUrl(url)
                wvNews.settings.javaScriptEnabled = true
            }
        }
    }
}