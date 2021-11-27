package com.example.unsplashsearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplashsearch.model.Photo
import com.example.unsplashsearch.retrofit.RetrofitManager
import com.example.unsplashsearch.utils.Constants
import com.example.unsplashsearch.utils.RESPONSE_STATUS
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    //성공시 사진데이터
    private var _photoList = MutableLiveData<ArrayList<Photo>>()
    val photoList: LiveData<ArrayList<Photo>>
        get() = _photoList
    //실패시 전달할 문자
    private var _photoFailList = MutableLiveData<String>()
    val photoFailList: LiveData<String>
        get() = _photoFailList

    init {
        Log.d(Constants.TAG, "MainViewModel - init()")
    }

    fun getPhotoData(searchText: String) {
         //viewModelScope는 뷰모델이 삭제되면 실행작업이 삭제 된다. 리소스 소모를 방지
        viewModelScope.launch {
            RetrofitManager.instance.searchPhotos(searchTerm = searchText, completion = { //completion을 사용한 이유는 비동기 처리를 위함
                      responseState, responseDataArrayList ->
                    when (responseState) {
                        RESPONSE_STATUS.OKAY -> {
                            Log.d(Constants.TAG, "MainActivity - api 호출 성공: ${responseDataArrayList?.size}")
                            _photoList.value = responseDataArrayList
                        }
                        RESPONSE_STATUS.FAIL -> {
                            _photoFailList.value = "api 호출실패"
                            Log.d(Constants.TAG, "MainActivity - api 호출 실패: $responseDataArrayList")
                        }
                        RESPONSE_STATUS.NO_CONTENT -> {
                            _photoFailList.value = "검색결과가 없습니다."
                            Log.d(Constants.TAG, "MainActivity - 검색 결과가 없습니다.")
                        }
                    }
                })
        }
    }

}