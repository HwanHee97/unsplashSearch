package com.example.unsplashsearch

import android.app.DownloadManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.webkit.MimeTypeMap
import android.webkit.WebViewClient
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.unsplashsearch.databinding.ActivityDownloadWebViewBinding
import com.example.unsplashsearch.databinding.ActivityPhotoCollectionBinding
import com.example.unsplashsearch.model.Photo
import com.example.unsplashsearch.utils.Constants
import org.jetbrains.anko.downloadManager
import org.jetbrains.anko.webView
import java.io.File
import java.net.URLDecoder

private lateinit var binding: ActivityDownloadWebViewBinding


class DownloadWedViewActivity :AppCompatActivity() {

    private lateinit var url:String
    private lateinit var actionBar:ActionBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constants.TAG,"DownloadWedViewActivity - onCreate() called ")

        setBinding()

        setContentView(binding.root)

        setActionBar()
        getIntents()
        setWebView()

    }
    fun setBinding(){
        Log.d(Constants.TAG,"DownloadWedViewActivity - setBinding() called ")
        binding = ActivityDownloadWebViewBinding.inflate(layoutInflater)
    }
    //액션바 설정
    fun setActionBar(){
        setSupportActionBar(binding.downloadActivityToolbar)
        actionBar=supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
    //url 받기
    fun getIntents(){
        url=intent.getStringExtra("download_url") as String
        Log.d(Constants.TAG,"DownloadWedViewActivity - getIntents() called / url: $url ")
    }
    //웹뷰에 url연결해서 띄우기
    fun setWebView(){
        binding.webview.apply {
            webViewClient = WebViewClient()//하이퍼링크 클릭시 새창 띄우기 방지
            settings.javaScriptEnabled = true//자바스크립트 허용
            settings.loadsImagesAutomatically = true
        }
        binding.webview.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}