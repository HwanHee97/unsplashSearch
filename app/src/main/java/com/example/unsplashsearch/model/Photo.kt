package com.example.unsplashsearch.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.unsplashsearch.App
import com.example.unsplashsearch.R
import java.io.Serializable

data class Photo(
    var thumbnail: String?,//썸네일
    var author: String?,//작가
    var createAt: String?,//생성일
    var likesCount: Int?//좋아요 눌린수
):Serializable {
//    object ImgBind{
//        @JvmStatic
//        @BindingAdapter("setPhotoImage")
//        fun loadPhotoImage(view: ImageView, imageUrl: String) {
//            Glide.with(App.instance)
//                .load(imageUrl)//표시할 이미지 값(링크).
//                .placeholder(R.drawable.ic_baseline_insert_photo_24)//이미지가 없으면 나오는 기본 화면
//                .into(view)//표시할 이미지 뷰
//
//        }
//    }
}
