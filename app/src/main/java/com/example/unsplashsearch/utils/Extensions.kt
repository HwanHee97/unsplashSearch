package com.example.unsplashsearch.utils

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.content.ContextCompat.startActivity
import com.example.unsplashsearch.PhotoCollectionActivity
import com.example.unsplashsearch.recyclerview.PhotoItemViewHolder
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

//문자열이 json 형태인지
fun String?.isJsonObject():Boolean{
//    if (this?.startsWith("{")==true &&this.endsWith("}")){
//        return true
//    }else{
//        return false
//    }
    return this?.startsWith("{")==true &&this.endsWith("}")//위 코드를 한줄로 줄일수있다.
}
//json 배열 형태인지
fun String?.isJsonArray():Boolean{
//    if (this?.startsWith("[")==true &&this.endsWith("]")){
//        return true
//    }else{
//        return false
//    }
    return this?.startsWith("[")==true &&this.endsWith("]")    //위 코드를 한줄로 줄일수있다.
}

//EditText에대한 확장함수
fun EditText.onMyTextChange(completion:(Editable?)->Unit){
    this.addTextChangedListener(object: TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            completion(s)
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}
fun start(context: Context?, searchText: String? = null, bundle: Bundle) =
    context?.startActivity(
        context.intentFor<PhotoCollectionActivity>(
            "search_term" to searchText,
            "array_bundle" to bundle
        ).newTask()
    )