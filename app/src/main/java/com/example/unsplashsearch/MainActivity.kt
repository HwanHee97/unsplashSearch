package com.example.unsplashsearch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Contacts
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.unsplashsearch.databinding.ActivityMainBinding
import com.example.unsplashsearch.retrofit.RetrofitManager
import com.example.unsplashsearch.utils.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import java.lang.Exception


private lateinit var binding: ActivityMainBinding
private lateinit var btn_progress: ProgressBar
private lateinit var btn_search: Button
private lateinit var frame_search_btn: FrameLayout
private lateinit var viewModel : MainViewModel
private lateinit var searchText:String

class MainActivity : AppCompatActivity() {
    //검색할 타입을 미리 지정해놓은 enum클래스에서 가져옴  기본값은 PHOTO
    private var currentSearchTypes: SEARCH_TYPE = SEARCH_TYPE.PHOTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        onBinding()//findViewByID는  setContentView()를해서 레이아웃이 존재할때만 리턴하기때문에 setContentView 아래 함수호출
        onListner()
        setObserve()
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

            this.handleSearchButtonUi()

            searchText=binding.searchTermEditText.text.toString()
            Log.d(Constants.TAG, "MainActivity-검색버튼 클릭 searchText:$searchText")

            viewModel.getPhotoData(searchText,"main")

            btn_progress.visibility = View.INVISIBLE
            btn_search.visibility = View.VISIBLE
            binding.searchTermEditText.setText("")

        }
    }//end of onListner()

    fun setObserve(){
        viewModel.photoList.observe(this, Observer {
            //PhotoCollectionActivity로 결과를 인텐트를 통해 전달 하기
            //번들에 responseDataArrayList를 넣고, 번들과 검색어를 미리 선언한 함수의 매개변수를 통해 새로운 액티비티 생성/Anko사용 코드라인수 감소
            val bundle = Bundle()
            bundle.putSerializable("photo_array_list",it)//Photo클래스에 Serializable선언해줘야 직렬화해서 값 전달가능
            start(applicationContext, searchText,bundle)//utils.Extensions 파일에 선언된함수/새로운 액티비티 실행
        })
        viewModel.photoFailList.observe(this, Observer {
            Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
        })
    }

    //버큰클릭시 프로그래스바 표시
    private fun handleSearchButtonUi() {
        btn_search.visibility = View.INVISIBLE
        btn_progress.visibility = View.VISIBLE

    }

}

