package com.example.unsplashsearch

import android.app.Application

class App :Application(){//RetrofitClient에서 Toast메시지를 띄우기위해 필요한 ui 스레드를 사용하기 위한 클래스  manifests에 앱이름을 이 클래스로 선언 해줘야함
    companion object{
        lateinit var instance:App
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
    }
}