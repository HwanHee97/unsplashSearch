package com.example.unsplashsearch.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashsearch.R
import com.example.unsplashsearch.databinding.ActivityPhotoCollectionBinding
import com.example.unsplashsearch.databinding.LayoutPhotoItemBinding
import com.example.unsplashsearch.model.Photo
import com.example.unsplashsearch.utils.Constants

class PhotoGridRecyclerViewAdapter(var photoList: ArrayList<Photo>,val context: Context) :RecyclerView.Adapter<PhotoItemViewHolder>() {

    //뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        Log.d(Constants.TAG,"PhotoGridRecyclerViewAdapter - onCreateViewHolder() called")
        val binding = LayoutPhotoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return PhotoItemViewHolder(binding,context)
    }
    //뷰홀더에 있는 바인드 함수 호출, 포토 리스트값 하나씩 전달
    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
            holder.bindWithView(this.photoList[position])
    }
    //보여줄 목록의 갯수
    override fun getItemCount(): Int {
        return this.photoList.size
    }
    //외부에서 어답터에 데이터 배열을 넣어준다
    fun submitList(photoList: ArrayList<Photo>){
        this.photoList=photoList
    }
    fun notifyPhotoDataChange(list:ArrayList<Photo>){
        photoList=list
        notifyDataSetChanged()
    }

}