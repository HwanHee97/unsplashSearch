package com.example.unsplashsearch.recyclerview

import android.media.Image
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplashsearch.App
import com.example.unsplashsearch.R
import com.example.unsplashsearch.model.Photo
import com.example.unsplashsearch.utils.Constants


class PhotoItemViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView) {

    //뷰들을 가져온다.
    private val photoImageView=itemView.findViewById<ImageView>(R.id.photo_image)
    private val photoCreateAtText=itemView.findViewById<TextView>(R.id.created_at_text)
    private val photoLikesCountText=itemView.findViewById<TextView>(R.id.likes_count_text)

    //데이터와 뷰를 묶는 곳
    fun bindWithView(photoItem:Photo){
        Log.d(Constants.TAG,"PhotoItemViewHolder - bindWithView() called")

        photoCreateAtText.text=photoItem.createAt
        photoLikesCountText.text=photoItem.likesCount.toString()
        //글라이드를 사용하여 이미지 표시
        Glide.with(App.instance)
            .load(photoItem.thumbnail)//표시할 이미지 값(링크).
            .placeholder(R.drawable.ic_baseline_insert_photo_24)//이미지가 없으면 나오는 기본 화면
            .into(photoImageView)//표시할 이미지 뷰




    }

}