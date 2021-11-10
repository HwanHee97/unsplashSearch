package com.example.unsplashsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.unsplashsearch.utils.Constants
import com.example.unsplashsearch.utils.SEARCH_TYPE
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    //검색할 타입을 미리 지정해놓은 enum클래스에서 가져옴  기본값은 PHOTO
    private var currentSearchTypes:SEARCH_TYPE=SEARCH_TYPE.PHOTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(Constants.TAG,"MainActivity-onCreate() called")

        //라디오그룹 가져오기
        search_term_radio_group.setOnCheckedChangeListener{_,checkedId->
            when(checkedId){
                R.id.photo_search_radio_btn->{
                    Log.d(Constants.TAG,"사진검색")
                    search_term_text_layout.hint="사진검색"
                    search_term_text_layout.startIconDrawable=resources.getDrawable(R.drawable.ic_baseline_photo_library_24,resources.newTheme())
                    this.currentSearchTypes=SEARCH_TYPE.PHOTO
                }
                R.id.user_search_radio_btn->{
                    Log.d(Constants.TAG,"사용자 검색")
                    search_term_text_layout.hint="사진검색"
                    search_term_text_layout.startIconDrawable=resources.getDrawable(R.drawable.ic_baseline_person_24,resources.newTheme())
                    this.currentSearchTypes=SEARCH_TYPE.USER
                }
            }

        }
    }


}