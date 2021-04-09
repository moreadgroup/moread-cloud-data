package com.moreadgroup.data.magicear8times

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.apache.commons.lang3.builder.ToStringBuilder
import org.eclipse.collections.impl.list.mutable.FastList
import java.time.OffsetDateTime

/**
 * @Author conan8chan@yahoo.com
 * @Date 3/5/21T12:14 PM-Friday
 *
 * Refer to [Free English to Chinese Dictionary Database.](https://github.com/skywind3000/ECDICT)
 */
data class EnglishWord(

    //seq,word,symbol,tense,paraphrase
    val seq: String?=null,
    val word: String?=null,
    val symbol: String?=null,
    val tense: String?=null,
    val paraphrase: String?=null,

) {

}
