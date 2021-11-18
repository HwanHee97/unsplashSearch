package com.example.unsplashsearch.model

import java.io.Serializable

data class Photo(
    var thumbnail: String?,//썸네일
    var author: String?,//작가
    var createAt: String?,//생성일
    var likesCount: Int?//좋아요 눌린수
):Serializable {


}
