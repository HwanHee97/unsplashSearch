package com.example.unsplashsearch

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.example.unsplashsearch.databinding.ActivityMainBinding
import com.example.unsplashsearch.utils.Constants
import com.example.unsplashsearch.utils.SEARCH_TYPE
import com.example.unsplashsearch.utils.onMyTextChange
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


private lateinit var binding: ActivityMainBinding
private lateinit var btn_progress: ProgressBar
private lateinit var btn_search: Button
private lateinit var frame_search_btn: FrameLayout
class MainActivity : AppCompatActivity() {
    //검색할 타입을 미리 지정해놓은 enum클래스에서 가져옴  기본값은 PHOTO
    private var currentSearchTypes: SEARCH_TYPE = SEARCH_TYPE.PHOTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBinding()
        onListner()
        setContentView(binding.root)
    }
    
    private fun onBinding(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        frame_search_btn: = findViewById(R.id.frame_search_btn)//커스텀 레이아웃은 바인딩 불가해서 findViewById 사용
        btn_search = findViewById(R.id.btn_search)//커스텀 레이아웃은 바인딩 불가해서 findViewById 사용
        btn_progress = findViewById(R.id.btn_progress)//커스텀 레이아웃은 바인딩 불가해서 findViewById 사용
    }
    
    private fun onListner(){
        with(binding){
            searchTermRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.photo_search_radio_btn -> {
                    searchTermTextLayout.apply{
                        hint = "사진검색"
                        startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_photo_library_24, resources.newTheme())
                    }
                    this.currentSearchTypes = SEARCH_TYPE.PHOTO
                }
                R.id.user_search_radio_btn -> {
                    searchTermTextLayout.apply{
                        hint = "사용자 검색"
                        startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_person_24, resources.newTheme())
                    }
                    this.currentSearchTypes = SEARCH_TYPE.USER
                }
            }
        }
            
        searchTermEditText.onMyTextChange {
            if (it.toString().count() > 0) {
                frame_search_btn.visibility = View.VISIBLE
                searchTermTextLayout.helperText = it.toString()
                //스크롤뷰를 올린다
                mainScrollview.scrollTo(0, 200)
            } else {
                frame_search_btn.visibility = View.INVISIBLE
                searchTermTextLayout.helperText = "검색어를 입력해 주세요"
            }
            if (it.toString().count() == 12) {
                Toast.makeText(this, "검색하는 글자는 12자까지 입니다.", Toast.LENGTH_SHORT).show()
            }
        }
        btn_search.setOnClickListener {
            Log.d(Constants.TAG, "MainActivity-검색버튼 클릭 currentSearchTypes:$currentSearchTypes")
            this.handleSearchButtonUi()
        }
      }   
    }
    
    private fun handleSearchButtonUi() {
        //btn_search.text=""
        btn_search.visibility = View.INVISIBLE
        btn_progress.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            btn_progress.visibility = View.INVISIBLE
            //btn_search.text="검색"
            btn_search.visibility = View.VISIBLE
        }, 1500L)


    }
}
