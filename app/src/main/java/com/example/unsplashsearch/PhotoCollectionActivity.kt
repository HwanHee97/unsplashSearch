package com.example.unsplashsearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.unsplashsearch.databinding.ActivityPhotoCollectionBinding
import com.example.unsplashsearch.databinding.LayoutPhotoItemBinding
import com.example.unsplashsearch.model.Photo
import com.example.unsplashsearch.recyclerview.PhotoGridRecyclerViewAdapter
import com.example.unsplashsearch.utils.Constants

private lateinit var binding: ActivityPhotoCollectionBinding

class PhotoCollectionActivity:AppCompatActivity() {
    //데이터
    private var photoList=ArrayList<Photo>()
    //어답터
    private val photoGridRecyclerViewAdapter : PhotoGridRecyclerViewAdapter by lazy {
        PhotoGridRecyclerViewAdapter(photoList) //선언과 동시에 초기화
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setContentView(binding.root)
        Log.d(Constants.TAG,"PhotoCollectionActivity - onCreate() called")
        getIntents()
        setRecyclerView()
    }// end of onCreate()

    fun getIntents(){
        val bundle=intent.getBundleExtra("array_bundle")//번들 받아서 저장
        val search_term = intent.getStringExtra("search_term")//받은 검색어 저장
        binding.topAppBar.title=search_term//검색어를 앱바 제목에 표시
        photoList= bundle?.getSerializable("photo_array_list") as ArrayList<Photo>//받은 번들안에있는 요청 결과를 photoList에 저장
        Log.d(Constants.TAG,"PhotoCollectionActivity - getIntents() called / search_term : $search_term / photoList.count() : ${photoList.count()}")
    }
    fun setBinding(){
        Log.d(Constants.TAG,"PhotoCollectionActivity - setBinding() called ")
        binding = ActivityPhotoCollectionBinding.inflate(layoutInflater)
    }
    fun setRecyclerView(){
        binding.myPhotoRecyclerView.apply {
            layoutManager=GridLayoutManager(this.context,2,GridLayoutManager.VERTICAL,false)
            adapter=photoGridRecyclerViewAdapter
        }
    }



}