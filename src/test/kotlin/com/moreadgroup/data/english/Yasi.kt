package com.moreadgroup.data.english

import kotlinx.serialization.Serializable

@Serializable
data class YasiWord(
    val word: String,
    val tags: Array<String>?,
    val phonetic: String?,
    val trans: String?,
    val tips: String?,
    val collocation: String?,
    val samples: Array<String>?,
    val same: String?,
    val antonym: String?,
    val derive:String?
    ) {
    constructor() : this("", arrayOf(),"", "", "", "", arrayOf(), "", "","")
//
//    - word: abundance*
//    tags: [常见]
//    phonetic: 【əˈbʌndəns】
//    trans:  n. 大量，丰富，充足，充裕
//    tips: 记 来自abundant(a. 丰富的，充裕的)
//    antonym: 搭 in abundance 丰富
//    samples: 例 At the party, there was food and drink inabundance. 宴会上，食品和饮料供应充足。
//    same: 同 profusion(n. 丰富)；affluence(n. 富足)；wealth(n. 大量)
//    antonym: 反 scarcity(n. 缺乏，不足)；deficiency(n. 缺少)

}


@Serializable
data class YasiYuminghong(
    val words: List<YasiWord>
) {
    constructor() : this(ArrayList())

}
