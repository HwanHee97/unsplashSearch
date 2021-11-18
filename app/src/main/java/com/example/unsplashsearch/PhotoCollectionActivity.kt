package com.example.unsplashsearch

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.unsplashsearch.model.Photo
import com.example.unsplashsearch.utils.Constants

class PhotoCollectionActivity:AppCompatActivity() {

    private var photoList=ArrayList<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_collection)

        Log.d(Constants.TAG,"PhotoCollectionActivity - onCreate() called")

        val bundle=intent.getBundleExtra("array_bundle")//번들 받아서 저장
        val search_term = intent.getStringExtra("search_term")//받은 검색어 저장

        photoList= bundle?.getSerializable("photo_array_list") as ArrayList<Photo>//받은 번들안에있는 요청 결과를 photoList에 저장


        Log.d(Constants.TAG,"PhotoCollectionActivity - onCreate() called / search_term : $search_term / photoList.count() : ${photoList.count()}")


    }// end of onCreate()

}