package com.example.unsplashsearch.retrofit

import com.example.unsplashsearch.utils.API

class RetrofitManager {
    companion object{//싱글턴이 적용되도록 선언
        val instance=RetrofitManager()
    }
    //레트로핏 인터페이스 만들기(가져오기): BASE_URL을 매매변수로 getClient 함수 호출한 결과
    private val iRetrofit:IRetrofit?=RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //사진검색 api 호출
    fun searchPhotos(searchTerm:String?,completion:(String)->Unit){//searchTerm글자,
        val term=searchTerm.let { it }?:""//searchTerm이 비어있으면""을 반환 아니면 그대로 // 아래 호출할 searchPhotos의 매개변수 타입과  searchTerm이 널 허용여부가 다르기때문에 재정의
        val call =iRetrofit?.searchPhotos(searchTerm=term).let {  }?:return //값이 없으면 return한다.
    }





}