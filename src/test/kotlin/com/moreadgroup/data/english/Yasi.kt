package com.moreadgroup.data.english

import kotlinx.serialization.Serializable
import com.fasterxml.jackson.annotation.JsonInclude;

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class EnwordYaml(
    val word: String,
    val exchange: String?,
    val tags: Array<String>?,
    val phonetic: String?,
    val trans: String?,
    val tips: String?,
    val usage: String?,
    val samples: Array<String>?,
    val synonyms: String?,
    val antonym: String?,
    val derive: String?,
    val distinguish: String?,
    val test: String?,
    val reference: String?
) {
    constructor() : this("", null, null, null, null, null, null, null, null, null, null, null, null, null)
    constructor(word: String, exchange: String?, phonetic: String?, trans: String?) : this(
        word,
        exchange,
        null,
        phonetic,
        trans,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
//
//    - word: abundance*
//    tags: [常见]
//    phonetic: 【əˈbʌndəns】
//    trans:  n. 大量，丰富，充足，充裕
//    tips: 记 来自abundant(a. 丰富的，充裕的)
//    antonym: 搭 in abundance 丰富
//    samples: 例 At the party, there was food and drink inabundance. 宴会上，食品和饮料供应充足。
//    synonyms: 同 profusion(n. 丰富)；affluence(n. 富足)；wealth(n. 大量)
//    antonym: 反 scarcity(n. 缺乏，不足)；deficiency(n. 缺少)

}


@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class YasiYuminghong(
    var words: ArrayList<EnwordYaml>
) {
    constructor() : this(ArrayList())

}
