package com.example.unsplashsearch.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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