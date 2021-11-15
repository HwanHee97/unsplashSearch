package com.example.unsplashsearch.retrofit

import android.util.Log
import com.example.unsplashsearch.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Url

//object 사용은 싱글턴
object RetrofitClient {
    //레트로핏 클라이언트 선언
    //private lateinit var retrofitClient:Retrofit  // 아래와 같은 표현
    private var retrofitClient:Retrofit?=null

    //레트로핏클라이언트 가져오기
    fun getClient(baseUrl: String):Retrofit?{
        Log.d(Constants.TAG,"RetrofitClient-getClient() called")
        if (retrofitClient == null){
            //레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient=Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()//레트로핏 빌드할때 .옵션 들을 추가하고 가장 마지막에 .build()로 마무리
        }
        return retrofitClient
    }

}