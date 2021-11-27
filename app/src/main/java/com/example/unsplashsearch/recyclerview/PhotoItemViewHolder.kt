package com.example.unsplashsearch.recyclerview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.unsplashsearch.App
import com.example.unsplashsearch.DownloadWedViewActivity
import com.example.unsplashsearch.PhotoCollectionActivity
import com.example.unsplashsearch.R
import com.example.unsplashsearch.databinding.ActivityPhotoCollectionBinding
import com.example.unsplashsearch.databinding.LayoutPhotoItemBinding
import com.example.unsplashsearch.model.Photo
import com.example.unsplashsearch.utils.Constants
import com.example.unsplashsearch.utils.start


class PhotoItemViewHolder(val binding:LayoutPhotoItemBinding, val context: Context) :RecyclerView.ViewHolder(binding.root) {

    //데이터바인딩 사용하여 데이터를 넘김
    fun bindWithView(item:Photo){
        Log.d(Constants.TAG,"PhotoItemViewHolder - bindWithView() called/${item.thumbnail}")
            //이미지만을 위한 어댑터를  Photo클래스 안에 이미지 바인딩 어댑터함수를 따로 선언하고 xml파일에서 함수이름=@{이미지url} 을 선언히여 사용가능
            with(binding){
                photoItem=item
                loadPhotoImage(photoImage,item.thumbnail,item.download_link)
            }
    }

    fun loadPhotoImage(view: ImageView, imageUrl: String?,downloadUrl:String?) {
        Glide.with(App.instance)
            .load(imageUrl)//표시할 이미지 값(링크).
            .placeholder(R.drawable.ic_baseline_insert_photo_24)//이미지가 없으면 나오는 기본 화면
            .into(view)//표시할 이미지 뷰
        view.setOnClickListener{
            //photocollectionactivity에서 다운로드 액티비티를 호출위해
            // photocollectionactivity의 context를 어댑터 생성시에 받아오고 그것을 holder생성시에 받아와서 사용
            //액티비티가아닌 viewholderd에서 사용하기 때문에 context가 필요
            val nextIntent = Intent(context, DownloadWedViewActivity::class.java)
            nextIntent.putExtra("download_url", downloadUrl)
            context.startActivity(nextIntent)
        }
    }

}