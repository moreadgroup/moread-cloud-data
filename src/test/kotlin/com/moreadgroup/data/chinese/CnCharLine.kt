package com.moreadgroup.data.chinese

/**
 * @Author conan8chan@yahoo.com
 * @Date 8/24/21T5:00 PM-Tuesday
 */
data class CnCharLine(

    // seq,word,pinyins,strokes,strokesok,explanation

    val seq: String?=null,
    val word: String?=null,
    val hzctable:String?=null, // 汉字应用水平测试字表甲乙丙表
    val strokesnum:String?=null, // 笔画数,根据汉字应用水平测试字表甲乙丙表
    val pinyins: String?=null,
    val strokes: String?=null,
    val strokesok:String?=null,
    val explanation:String?=null,

    ) {

}
