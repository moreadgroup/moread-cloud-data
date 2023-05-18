package com.moreadgroup.data.chinese.hzc

import kotlinx.serialization.Serializable

/**
 * @Author conan8chan@yahoo.com
 * @Date 1/9/23T7:36 PM-Monday
 */


@Serializable
data class Hzc(
    val name: String,
    val desc: String,
    val version: String,
    val hzctables: List<Table>
) {
    constructor() : this("","","",ArrayList())

}
@Serializable
data class Table(
    val table: String,
    val strokes: List<Stroke>
) {
    constructor() : this("",ArrayList())

}


@Serializable
data class Stroke(
    val stroke: String,
    val words: List<Word>
) {
    constructor() : this("",ArrayList())

}

@Serializable
data class Word(
    val word: String,
    val pinyin:String?,
    val phrase: String?
) {
    constructor() : this("","","")

}


data class HzcChar(
    val hzctable:String,
    val strokenum:String,
    val word:String,
){
    constructor() : this("","","")

}
