package com.moreadgroup.data.magicear8times

/**
 * @Author conan8chan@yahoo.com
 * @Date 3/5/21T12:14 PM-Friday
 *
 * Refer to [Free English to Chinese Dictionary Database.](https://github.com/skywind3000/ECDICT)
 */
data class QuizItemLine(
    var id: String,
    val level: String,
    // is multiple choices ?
    val mchoice: String,
    var ans: String?=null,
    var eword: String?=null,
    val title: String,
    val desc: String,

    var opa: String?=null,
    var opb: String?=null,
    var opc: String?=null,
    var opd: String?=null,
    var ope: String?=null,
    var opf: String?=null,
    var opg: String?=null,
    var oph: String?=null,

    ) {

}
