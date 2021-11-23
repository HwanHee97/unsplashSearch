package com.example.unsplashsearch.recyclerview

import android.media.Image
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplashsearch.App
import com.example.unsplashsearch.R
import com.example.unsplashsearch.databinding.ActivityPhotoCollectionBinding
import com.example.unsplashsearch.databinding.LayoutPhotoItemBinding
import com.example.unsplashsearch.model.Photo
import com.example.unsplashsearch.utils.Constants


class PhotoItemViewHolder(val binding:LayoutPhotoItemBinding ) :RecyclerView.ViewHolder(binding.root) {

    //데이터바인딩 사용하여 데이터를 넘김
    fun bindWithView(Item:Photo){
        Log.d(Constants.TAG,"PhotoItemViewHolder - bindWithView() called")
            binding.photoItem=Item
            //이미지는 일반 바인딩으로는 할수없어서 Photo클래스 안에 이미지 바인딩 어댑터함수를 선언했음 xml파일에서 함수이름=@{이미지url} 을 선언해줌
    }


}