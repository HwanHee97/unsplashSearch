package com.example.unsplashsearch

import android.content.Intent
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




    }//oncreate()

    private fun onBinding() {
        frame_search_btn = findViewById(R.id.frame_search_btn)//커스텀 레이아웃은 바인딩 불가해서 findViewById 사용
        btn_search = findViewById(R.id.btn_search)//커스텀 레이아웃은 바인딩 불가해서 findViewById 사용
        btn_progress = findViewById(R.id.btn_progress)//커스텀 레이아웃은 바인딩 불가해서 findViewById 사용
    }

    private fun onListner() {

        //라디오그룹 리스너 달기
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
            //입력된 글자가 있으면 검색버튼,helpertext 보여쥼
            if (it.toString().count() > 0) {
                frame_search_btn.visibility = View.VISIBLE
                binding.searchTermTextLayout.helperText = it.toString()
                //스크롤뷰를 올린다
                binding.mainScrollview.scrollTo(0, 200)
            } else {
                frame_search_btn.visibility = View.INVISIBLE
                binding.searchTermTextLayout.helperText = "검색어를 입력해 주세요"
            }
            //검색글자수 제한
            if (it.toString().count() == 12) {
                Log.d(Constants.TAG, "MainActivity-글자수 초과")
                Toast.makeText(this, "검색하는 글자는 12자까지 입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        //버튼 클릭시 리스너 달기
        btn_search.setOnClickListener {
            Log.d(Constants.TAG, "MainActivity-검색버튼 클릭 currentSearchTypes:$currentSearchTypes")
            var searchText:String=binding.searchTermEditText.text.toString()
            Log.d(Constants.TAG, "MainActivity-검색버튼 클릭 searchText:$searchText")

            //completion을 사용한 이유는 비동기 처리를 위함
            RetrofitManager.instance.searchPhotos(searchTerm =searchText ,completion = {
                    responseState, responseDataArrayList->
                when(responseState){
                    RESPONSE_STATE.OKAY->{
                        Log.d(Constants.TAG, "MainActivity - api 호출 성공: ${responseDataArrayList?.size}")

                        //PhotoCollectionActivity로 결과를 인텐트를 통해 전달 하기
                        //번들에 responseDataArrayList를 넣고, 인텐트에 번들을 넣어서 전달
                        val intent=Intent(this,PhotoCollectionActivity::class.java)
                        val bundle = Bundle()
                        bundle.putSerializable("photo_array_list",responseDataArrayList)//Photo클래스에 Serializable선언해줘야 직렬화해서 값 전달가능
                        intent.putExtra("array_bundle",bundle)
                        intent.putExtra("search_term",searchText)
                        startActivity(intent)
                    }

                    RESPONSE_STATE.FAIL->{
                        Toast.makeText(this,"api 호출 실패: $responseDataArrayList",Toast.LENGTH_SHORT).show()
                        Log.d(Constants.TAG, "MainActivity - api 호출 실패: $responseDataArrayList")
                    }
                }
            })
            this.handleSearchButtonUi()
        }

    }//end of onListner()

    //버큰클릭시 프로그래스바 표시 핸들러
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