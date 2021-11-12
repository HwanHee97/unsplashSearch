package com.example.unsplashsearch.retrofit

import com.example.unsplashsearch.utils.API
import com.example.unsplashsearch.utils.Constants
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {

    //쿼리스트링으로 searchTerm를 넘긴다. 함수의 리턴값은 :Call<JsonElement>로 정해서 JsonElement를 받는다.
    @GET(API.SEARCH_PHOTOS)//util에 url을 선언해놨다.
    fun searchPhotos(@Query("query")searchTerm:String):Call<JsonElement>
    @GET(API.SEARCH_USERS)
    fun searchUsers(@Query("query")searchTerm:String):Call<JsonElement>


}