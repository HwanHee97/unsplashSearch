package com.example.unsplashsearch.retrofit

import android.util.Log
import com.example.unsplashsearch.model.Photo
import com.example.unsplashsearch.utils.API
import com.example.unsplashsearch.utils.Constants
import com.example.unsplashsearch.utils.RESPONSE_STATUS
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat

class RetrofitManager {
    companion object {
        //싱글턴처럼 적용되도록 companion을 사용하여 선언
        val instance = RetrofitManager()
    }

    //레트로핏 인터페이스 만들기(가져오기): BASE_URL을 매매변수로 getClient 함수 호출한 결과
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //사진검색 api 호출
    fun searchPhotos(
        searchTerm: String?,
        completion: (RESPONSE_STATUS, ArrayList<Photo>?) -> Unit
    ) {//searchTerm글자,

        Log.d(Constants.TAG, "RetorfitManager - searchPhotos()")

        val term = searchTerm.let { it }
            ?: ""//searchTerm이 비어있으면""을 반환 아니면 그대로(it) // 아래 호출할 searchPhotos의 매개변수 타입과  searchTerm이 널 허용여부가 다르기때문에 재정의
        //val term = searchTerm ?: ""//위 코드랑 같은 의미

        val call = iRetrofit?.searchPhotos(searchTerm = term).let { it }
            ?: return //값이 없으면 return한다.있으면 it return
        //val call = iRetrofit?.searchPhotos(searchTerm = term) ?: return//위 코드랑 같은 의미
        call.enqueue(object : retrofit2.Callback<JsonElement> {
            //enqueue로 비동기 통신 실행
            //응답성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                Log.d(
                    Constants.TAG,
                    "RetorfitManager-onResponse() called / response: ${response.body()}"
                )
                when (response.code()) {//응답 코드기 200(정상)일떄만 completion을(메인 액티비티로) 보낸다. 정상이 아닌 반환코드가 통신 성공으로 뜰수 있음을 방지
                    200 -> {
                        response.body()?.let { //response.body()에 데이터가 있다면
                            //결과(JsonObject)에서 원하는값 가져올때 .get(요소).as자료형 // .getAsJsonArray(요소)등을 사용
                            var parsedPhotoDataArray = ArrayList<Photo>()
                            val body = it.asJsonObject
                            val results =
                                body.getAsJsonArray("results")//body에서 results란 이름을getAsJsonArray 형태로 가져온다

                            val total = body.get("total").asInt//body에서total이라는 요소의 값을 Int형으로 가져온다,
                            //Log.d(Constants.TAG,"RetorfitManager-onResponse() called /  total : $total")
                            //데이터가 없으면 no_content로 보낸다.
                            if (total == 0) {
                                completion(RESPONSE_STATUS.NO_CONTENT, null)
                            } else {
                                //호출 결과인 results를 반복문 을 통해 원하는 값을 가져온다.
                                results.forEach { resultItem -> //results의 값을 it으로 해도되는데 이와같이 resultItem으로 이름을 정할수 있다. resultItem 은 하나의 사진에 대한 정보

                                    val resultItemObject =
                                        resultItem.asJsonObject //resultItem(하나의 사진)을 jsonobject로 뺴온다.

                                    val user =
                                        resultItemObject.get("user").asJsonObject//사진정보중 user이란 jsonobject를 가져옴
                                    val username: String = user.get("username").asString

                                    val likesCount = resultItemObject.get("likes").asInt
                                    val thumbnailLink =
                                        resultItemObject.get("urls").asJsonObject.get("thumb").asString
                                    val createAt = resultItemObject.get("created_at").asString

                                    //createAt의 날짜 형태을 변환하기
                                    val paser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                    val formatter = SimpleDateFormat("yyyy년\nMM월 dd일")
                                    val outputDateString = formatter.format(paser.parse(createAt))
                                    //Log.d(Constants.TAG,"RetorfitManager-onResponse() called /  outputDateString : $outputDateString")

                                    val photoItem = Photo(
                                        author = username,
                                        likesCount = likesCount,
                                        thumbnail = thumbnailLink,
                                        createAt = outputDateString
                                    )
                                    parsedPhotoDataArray.add(photoItem)
                                }

                                completion(
                                    RESPONSE_STATUS.OKAY,
                                    parsedPhotoDataArray
                                )//mainActivity로 Photo객체를 담은 Array리스트를 보낸다.
                            }
                        }
                    }
                }

            }

            //응답실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetorfitManager-onFailure() called / t:$t")
                completion(RESPONSE_STATUS.FAIL, null)
            }

        })
    }


}