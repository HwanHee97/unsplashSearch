package com.example.unsplashsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.RadioGroup
import com.example.unsplashsearch.utils.Constants
import com.example.unsplashsearch.utils.SEARCH_TYPE
import com.example.unsplashsearch.utils.onMyTextChange
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout



class MainActivity : AppCompatActivity() {
    //검색할 타입을 미리 지정해놓은 enum클래스에서 가져옴  기본값은 PHOTO
    private var currentSearchTypes: SEARCH_TYPE = SEARCH_TYPE.PHOTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(Constants.TAG,"MainActivity-onCreate() called~!!@@")
        val search_term_radio_group:RadioGroup=findViewById(R.id.search_term_radio_group)
        val search_term_text_layout:TextInputLayout=findViewById(R.id.search_term_text_layout)
        val search_term_edit_text:TextInputEditText=findViewById(R.id.search_term_edit_text)
        val frame_search_btn:FrameLayout=findViewById(R.id.frame_search_btn)

        //라디오그룹 가져오기
        search_term_radio_group.setOnCheckedChangeListener{_,checkedId->
            when(checkedId){
                R.id.photo_search_radio_btn->{
                    Log.d(Constants.TAG,"사진검색")
                    search_term_text_layout.hint="사진검색"
                    search_term_text_layout.startIconDrawable=resources.getDrawable(R.drawable.ic_baseline_photo_library_24,resources.newTheme())
                    this.currentSearchTypes= SEARCH_TYPE.PHOTO
                }
                R.id.user_search_radio_btn->{
                    Log.d(Constants.TAG,"사용자 검색")
                    search_term_text_layout.hint="사용자 검색"
                    search_term_text_layout.startIconDrawable=resources.getDrawable(R.drawable.ic_baseline_person_24,resources.newTheme())
                    this.currentSearchTypes= SEARCH_TYPE.USER
                }
            }
            Log.d(Constants.TAG,"setOnCheckedChange()called /currentSearchTypes:$currentSearchTypes")
        }
        //텍스트가 변경될때
        search_term_edit_text.onMyTextChange {
            //입력된 글자가 있으면 검색버튼 보여쥼
            if (it.toString().count()>0){
                frame_search_btn.visibility= View.VISIBLE
            }else{
                frame_search_btn.visibility= View.INVISIBLE

            }
        }

    }
}