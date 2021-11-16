package com.example.unsplashsearch.retrofit

import android.util.Log
import com.example.unsplashsearch.utils.API
import com.example.unsplashsearch.utils.Constants
import com.example.unsplashsearch.utils.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object {
        //싱글턴이 적용되도록 선언
        val instance = RetrofitManager()
    }

    //레트로핏 인터페이스 만들기(가져오기): BASE_URL을 매매변수로 getClient 함수 호출한 결과
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //사진검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE,String) -> Unit) {//searchTerm글자,
        Log.d(Constants.TAG,"RetorfitManager - searchPhotos()")

        val term = searchTerm.let { it } ?: ""//searchTerm이 비어있으면""을 반환 아니면 그대로(it) // 아래 호출할 searchPhotos의 매개변수 타입과  searchTerm이 널 허용여부가 다르기때문에 재정의
        //val term = searchTerm ?: ""//위 코드랑 같은 의미

        val call = iRetrofit?.searchPhotos(searchTerm = term).let { it } ?: return //값이 없으면 return한다.있으면 it return
        //val call = iRetrofit?.searchPhotos(searchTerm = term) ?: return//위 코드랑 같은 의미
        call.enqueue(object :retrofit2.Callback<JsonElement>{
            //응답성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                //Log.d(Constants.TAG,"RetorfitManager-onResponse() called / response:")

                Log.d(Constants.TAG,"RetorfitManager-onResponse() called / response: ${response.body()}")
                completion(RESPONSE_STATE.OKAY,response.body().toString())
            }
            //응답실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG,"RetorfitManager-onFailure() called / t:$t")
                completion(RESPONSE_STATE.FAIL,t.toString())
            }

        })
    }


}