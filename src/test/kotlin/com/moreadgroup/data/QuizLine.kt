package com.moreadgroup.data

/**
 * @Author conan8chan@yahoo.com
 * @Date 3/5/21T12:14 PM-Friday
 *
 * Refer to [Free English to Chinese Dictionary Database.](https://github.com/skywind3000/ECDICT)
 */
data class QuizLine(
    //id,seq,level,title,desc,items
    var id: String,
    val seq: String,
    val level: String,
    var title: String?=null,
    var desc: String?=null,
    val items: String,


    ) {

}
