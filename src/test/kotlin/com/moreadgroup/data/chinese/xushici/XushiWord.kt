package com.moreadgroup.data.chinese.xushici

import kotlinx.serialization.Serializable
import com.fasterxml.jackson.annotation.JsonInclude;

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class GushiwenXushiWords(
    var words: ArrayList<XushiWordYaml>
) {
    constructor() : this(ArrayList())

}

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class XushiWordYaml(
    val word: String,
    var type: String,
    val phonetic: String,
    var tags: List<String>?,
    val samples: ArrayList<XushiWordTrans>?,
) {
    constructor() : this("", "", "", null, null)
    constructor(word: String, type: String, phonetic: String, tags: List<String>?) : this(
        word,
        type,
        phonetic,
        tags,
        null
    )

}

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class XushiWordTrans(
    val trans: String,
    val sentences: ArrayList<XushiWordTransSentence>?,
) {
    constructor() : this("", null)
    constructor(trans: String) : this(
        trans,
        null
    )

}

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class XushiWordTransSentence(
    /*
            - ancient: 学而时习<之>，不亦说乎？（《论语•学而》）
            url: xxxx
            vernacular: xxxx
     */
    var ancient: String,
    var url: String?,
    var vernacular: String?,
) {
    constructor() : this("", null, null)
    constructor(ancient: String) : this(
        ancient,
        null,
        null
    )

}


@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class XushiWordQuizzes(
    var quizzes: ArrayList<XushiWordQuiz>
) {
    constructor() : this(ArrayList())

}

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class XushiWordQuiz(
    val word: String,
    val type: String,
    val phonetic: String,
    val trans: String,
    val ancient: String,
    val url: String?,
    val vernacular: String?,

    ) {
    constructor() : this("", "", "", "", "", null, null)
    constructor(word: String, type: String, phonetic: String, trans: String, ancient: String) : this(
        word,
        type,
        phonetic, trans, ancient, null, null
    )

}
