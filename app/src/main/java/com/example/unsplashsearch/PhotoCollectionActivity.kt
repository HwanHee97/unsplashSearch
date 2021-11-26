package com.example.unsplashsearch

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.unsplashsearch.databinding.ActivityPhotoCollectionBinding
import com.example.unsplashsearch.databinding.LayoutPhotoItemBinding
import com.example.unsplashsearch.model.Photo
import com.example.unsplashsearch.recyclerview.PhotoGridRecyclerViewAdapter
import com.example.unsplashsearch.utils.Constants



private lateinit var binding: ActivityPhotoCollectionBinding



class PhotoCollectionActivity:AppCompatActivity(),SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {
    //데이터
    private var photoList=ArrayList<Photo>()
    //어답터
    private lateinit var photoGridRecyclerViewAdapter : PhotoGridRecyclerViewAdapter //선언과 동시에 초기화
    //
    var viewModel=MainViewModel()
    //searchview
    private lateinit var mySearchView: androidx.appcompat.widget.SearchView
    //searchviewEditText
    private lateinit var mySearchViewEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setContentView(binding.root)
        Log.d(Constants.TAG,"PhotoCollectionActivity - onCreate() called")

        setSupportActionBar(binding.topAppBar)//탑 바 이벤트 선언
        getIntents()
        setRecyclerView()

    }// end of onCreate()

    fun getIntents(){
        val bundle=intent.getBundleExtra("array_bundle")//번들 받아서 저장
        val search_term = intent.getStringExtra("search_term")//받은 검색어 저장
        binding.topAppBar.title=search_term//검색어를 앱바 제목에 표시
        photoList= bundle?.getSerializable("photo_array_list") as ArrayList<Photo>//받은 번들안에있는 요청 결과를 photoList에 저장
        Log.d(Constants.TAG,"PhotoCollectionActivity - getIntents() called / search_term : $search_term / photoList.count() : ${photoList.count()}")
    }
    fun setBinding(){
        Log.d(Constants.TAG,"PhotoCollectionActivity - setBinding() called ")
        binding = ActivityPhotoCollectionBinding.inflate(layoutInflater)
    }
    fun setRecyclerView(){
        photoGridRecyclerViewAdapter  = PhotoGridRecyclerViewAdapter(photoList)
        binding.myPhotoRecyclerView.apply {
            layoutManager=GridLayoutManager(this.context,2,GridLayoutManager.VERTICAL,false)
            adapter=photoGridRecyclerViewAdapter
            photoGridRecyclerViewAdapter.notifyDataSetChanged()
        }
    }
    fun setchangePhotoListObserve(){
        viewModel.changePhotoList.observe(this, Observer {
            photoList=it
            setRecyclerView()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(Constants.TAG,"PhotoCollectionActivity - onCreateOptionsMenu() called ")
        //메뉴 xml을 inflater하고 top_app_bar_menu랑 연결시킴
        val inflater=menuInflater
        inflater.inflate(R.menu.top_app_bar_menu,menu)

        val searchManager=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        //menu안의 searchview가져온다
        this.mySearchView= menu?.findItem(R.id.search_menu_item)?.actionView as androidx.appcompat.widget.SearchView
        this.mySearchView.apply {
            this.queryHint="검색어를 입력해 주세요"
            this.setOnQueryTextListener(this@PhotoCollectionActivity)
            this.setOnQueryTextFocusChangeListener{_, hasExpaned->
                when(hasExpaned){
                    true->{
                        Log.d(Constants.TAG,"서치뷰 열림 ")
                    }
                    false->{
                        Log.d(Constants.TAG,"서치뷰 닫힘 ")
                    }
                }
            }
            //searchviewEditText 가져오기
            mySearchViewEditText=this.findViewById(androidx.appcompat.R.id.search_src_text)

        }
        this.mySearchViewEditText.apply {
            this.filters= arrayOf(InputFilter.LengthFilter(12))//검색글자수 12 로 제한
            this.setTextColor(Color.WHITE)
            this.setHintTextColor(Color.WHITE)

        }

        return super.onCreateOptionsMenu(menu)
    }
    //탑바 서치뷰 검색버튼 클릭 이벤트
    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d(Constants.TAG,"PhotoCollectionActivity - onQueryTextSubmit() called / query: ${query}")
        if(!query.isNullOrEmpty()){//검색어가 있으면 탑바의 타이틀을 변경
            binding.topAppBar.title=query
            //api호출!!!!!
            viewModel.getPhotoData(query,"photo")
            setchangePhotoListObserve()

        }
        binding.topAppBar.collapseActionView()//탑바에 액션뷰가 닫힘//키보드 사라짐
        return true
    }
    //탑바 서치뷰 검색어 입력 이벤트
    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(Constants.TAG,"PhotoCollectionActivity - onQueryTextSubmit() called / newText: ${newText}")
        val userInputText = newText?:""//입력된값이 없으면 ""을 넣겠다

        if (userInputText.count()==12){
            Toast.makeText(this,"12자까지 검색가능 합니다.",Toast.LENGTH_SHORT).show()
            Log.d(Constants.TAG,"12자까지 검색가능 합니다.${userInputText.count()}")
        }

        return true
    }

}