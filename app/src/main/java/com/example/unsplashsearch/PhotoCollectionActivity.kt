package com.example.unsplashsearch

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashsearch.model.Photo
import com.example.unsplashsearch.recyclerview.PhotoGridRecyclerViewAdapter
import com.example.unsplashsearch.utils.Constants
import com.google.android.material.appbar.MaterialToolbar

class PhotoCollectionActivity:AppCompatActivity() {
    //데이터
    private var photoList=ArrayList<Photo>()

    //어답터
    private lateinit var photoGridRecyclerViewAdapter: PhotoGridRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_collection)
        val my_photo_recycler_view = findViewById<RecyclerView>(R.id.my_photo_recycler_view)
        val top_app_bar=findViewById<MaterialToolbar>(R.id.top_app_bar)
        Log.d(Constants.TAG,"PhotoCollectionActivity - onCreate() called")

        val bundle=intent.getBundleExtra("array_bundle")//번들 받아서 저장
        val search_term = intent.getStringExtra("search_term")//받은 검색어 저장

        top_app_bar.title=search_term//검색어를 앱바 제목에 표시

        photoList= bundle?.getSerializable("photo_array_list") as ArrayList<Photo>//받은 번들안에있는 요청 결과를 photoList에 저장

        Log.d(Constants.TAG,"PhotoCollectionActivity - onCreate() called / search_term : $search_term / photoList.count() : ${photoList.count()}")

        this.photoGridRecyclerViewAdapter= PhotoGridRecyclerViewAdapter()
        this.photoGridRecyclerViewAdapter.submitList(photoList)

        my_photo_recycler_view.layoutManager=GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        my_photo_recycler_view.adapter=this.photoGridRecyclerViewAdapter



    }// end of onCreate()

}