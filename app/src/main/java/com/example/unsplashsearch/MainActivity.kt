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
import com.example.unsplashsearch.retrofit.RetrofitManager
import com.example.unsplashsearch.utils.Constants
import com.example.unsplashsearch.utils.RESPONSE_STATE
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
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        onBinding()//findViewByID는  setContentView()를해서 레이아웃이 존재할때만 리턴하기때문에 setContentView 아래 함수호출
        onListner()
        Log.d(Constants.TAG, "MainActivity-onCreate() called~!!@@")




    }//oncreate

    private fun onBinding() {
        frame_search_btn = findViewById(R.id.frame_search_btn)//커스텀 레이아웃은 바인딩 불가해서 findViewById 사용
        btn_search = findViewById(R.id.btn_search)//커스텀 레이아웃은 바인딩 불가해서 findViewById 사용
        btn_progress = findViewById(R.id.btn_progress)//커스텀 레이아웃은 바인딩 불가해서 findViewById 사용
    }

    private fun onListner() {
        //라디오그룹 가져오기
        binding.searchTermRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.photo_search_radio_btn -> {
                    Log.d(Constants.TAG, "사진검색")
                    binding.searchTermTextLayout.apply {
                        hint="사진검색"
                        startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_photo_library_24, resources.newTheme())
                    }
                    //binding.searchTermTextLayout.hint = "사진검색"
                    //binding.searchTermTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_photo_library_24, resources.newTheme())
                    this.currentSearchTypes = SEARCH_TYPE.PHOTO
                }
                R.id.user_search_radio_btn -> {
                    Log.d(Constants.TAG, "사용자 검색")
                    binding.searchTermTextLayout.apply {
                        hint="사용자 검색"
                        startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_person_24, resources.newTheme())
                    }
                    //binding.searchTermTextLayout.hint = "사용자 검색"
                    //binding.searchTermTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_person_24, resources.newTheme())
                    this.currentSearchTypes = SEARCH_TYPE.USER
                }
            }
            Log.d(Constants.TAG, "MainActivity - setOnCheckedChange()called /currentSearchTypes:$currentSearchTypes")
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
        //버튼 클릭시
        btn_search.setOnClickListener {
            Log.d(Constants.TAG, "MainActivity-검색버튼 클릭 currentSearchTypes:$currentSearchTypes")

            RetrofitManager.instance.searchPhotos(searchTerm = binding.searchTermEditText.toString(),completion = {
                    responseState, responseBody->
                when(responseState){
                    RESPONSE_STATE.OKAY->{
                        Log.d(Constants.TAG, "MainActivity - api 호출 성공: $responseBody")
                    }
                    RESPONSE_STATE.FAIL->{
                        Toast.makeText(this,"api 호출 실패: $responseBody",Toast.LENGTH_SHORT).show()
                        Log.d(Constants.TAG, "MainActivity - api 호출 실패: $responseBody")

                    }
                }

            })
            this.handleSearchButtonUi()

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