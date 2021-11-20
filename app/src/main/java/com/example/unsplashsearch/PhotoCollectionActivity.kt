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
    private var isSuccess : Boolean = false
    private val photoGridRecyclerViewAdapter : PhotoGridRecyclerViewAdapter by lazy {
        PhotoGridRecyclerViewAdapter(photoList) //그냥 이런식으로 넘기는거 추천 
    }
  
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_collection)
        getIntent()
        setBinding()
        setRecyclerview()
    }// end of onCreate()

    private fun getIntent(){
        if (intent.hasExtra("array_bundle")){
            val bundle=intent.getBundleExtra("array_bundle")//번들 받아서 저장
            photoList= bundle?.getSerializable("photo_array_list") as ArrayList<Photo>//받은 번들안에있는 요청 결과를 photoList에 저장
            isSuccess = true
        }
        if (intent.hasExtra("array_bundle")){
            val search_term = intent.getStringExtra("search_term")//받은 검색어 저장
        }
    }
    
    private fun setBinding(){
        val my_photo_recycler_view = findViewById<RecyclerView>(R.id.my_photo_recycler_view)
        val top_app_bar=findViewById<MaterialToolbar>(R.id.top_app_bar)
        top_app_bar.title=search_term//검색어를 앱바 제목에 표시
    }
    
    private fun setRecyclerview(){
        if(isSuccess){
            my_photo_recycler_view.apply{
            layoutManager=GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
            adapter=this.photoGridRecyclerViewAdapter
        }else {
            //데이터가 안 넘어오는 경우 앱이 터짐 이런 경우 예외로 이전 스택으로 이동한는 코드 
        }
       
        
    }
   
}
