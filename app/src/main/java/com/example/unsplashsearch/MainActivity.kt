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

class MainActivity : AppCompatActivity() {
    //검색할 타입을 미리 지정해놓은 enum클래스에서 가져옴  기본값은 PHOTO
    private var currentSearchTypes: SEARCH_TYPE = SEARCH_TYPE.PHOTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        Log.d(Constants.TAG, "MainActivity-onCreate() called~!!@@")
//        val search_term_radio_group:RadioGroup=findViewById(R.id.search_term_radio_group)
//        val search_term_text_layout:TextInputLayout=findViewById(R.id.search_term_text_layout)
//        val search_term_edit_text:TextInputEditText=findViewById(R.id.search_term_edit_text)
        val frame_search_btn: FrameLayout =
            findViewById(R.id.frame_search_btn)//커스텀 레이아웃은 바인딩 불가해서 findViewById 사용
        btn_search = findViewById(R.id.btn_search)//커스텀 레이아웃은 바인딩 불가해서 findViewById 사용
        btn_progress = findViewById(R.id.btn_progress)//커스텀 레이아웃은 바인딩 불가해서 findViewById 사용

        //라디오그룹 가져오기
        binding.searchTermRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.photo_search_radio_btn -> {
                    Log.d(Constants.TAG, "사진검색")
                    binding.searchTermTextLayout.hint = "사진검색"
                    binding.searchTermTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_photo_library_24, resources.newTheme())
                    this.currentSearchTypes = SEARCH_TYPE.PHOTO
                }
                R.id.user_search_radio_btn -> {
                    Log.d(Constants.TAG, "사용자 검색")
                    binding.searchTermTextLayout.hint = "사용자 검색"
                    binding.searchTermTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_person_24, resources.newTheme())
                    this.currentSearchTypes = SEARCH_TYPE.USER
                }
            }
            Log.d(Constants.TAG, "setOnCheckedChange()called /currentSearchTypes:$currentSearchTypes")
        }
        //텍스트가 변경될때
        binding.searchTermEditText.onMyTextChange {
            //입력된 글자가 있으면 검색버튼 보여쥼
            if (it.toString().count() > 0) {
                frame_search_btn.visibility = View.VISIBLE
                binding.searchTermTextLayout.helperText = it.toString()
                //스크롤뷰를 올린다
                binding.mainScrollview.scrollTo(0, 200)
            } else {
                frame_search_btn.visibility = View.INVISIBLE
                binding.searchTermTextLayout.helperText = "검색어를 입력해 주세요"
            }
            if (it.toString().count() == 12) {
                Log.d(Constants.TAG, "MainActivity-글자수 초과")
                Toast.makeText(this, "검색하는 글자는 12자까지 입니다.", Toast.LENGTH_SHORT).show()
            }
        }
        btn_search.setOnClickListener {
            Log.d(Constants.TAG, "MainActivity-검색버튼 클릭 currentSearchTypes:$currentSearchTypes")
            this.handleSearchButtonUi()
        }
    }//oncreate

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