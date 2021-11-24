package com.example.unsplashsearch.recyclerview

import android.media.Image
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.unsplashsearch.App
import com.example.unsplashsearch.R
import com.example.unsplashsearch.databinding.ActivityPhotoCollectionBinding
import com.example.unsplashsearch.databinding.LayoutPhotoItemBinding
import com.example.unsplashsearch.model.Photo
import com.example.unsplashsearch.utils.Constants


class PhotoItemViewHolder(val binding:LayoutPhotoItemBinding ) :RecyclerView.ViewHolder(binding.root) {

    //데이터바인딩 사용하여 데이터를 넘김
    fun bindWithView(item:Photo){
        Log.d(Constants.TAG,"PhotoItemViewHolder - bindWithView() called")
            //이미지만을 위한 어댑터를  Photo클래스 안에 이미지 바인딩 어댑터함수를 따로 선언하고 xml파일에서 함수이름=@{이미지url} 을 선언히여 사용가능
            with(binding){
                photoItem=item
                loadPhotoImage(photoImage,item.thumbnail)
            }
    }

    fun loadPhotoImage(view: ImageView, imageUrl: String?) {
        Glide.with(App.instance)
            .load(imageUrl)//표시할 이미지 값(링크).
            .placeholder(R.drawable.ic_baseline_insert_photo_24)//이미지가 없으면 나오는 기본 화면
            .into(view)//표시할 이미지 뷰
    }

}