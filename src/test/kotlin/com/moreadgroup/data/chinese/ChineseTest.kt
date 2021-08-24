package com.moreadgroup.data.chinese

import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @Author conan8chan@yahoo.com
 * @Date 8/24/21T5:00 PM-Tuesday
 */
class ChineseTest {

    private val ROOT_FOLDER = "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/"

    //    val ROOT_FOLDER = "/Users/conanchen/github/moreadgroup/moread-cloud-data/src/main/resources/"
    private val GRAPHQL_SERVER = "http://localhost:18060/domain-task/graphql"

    private val srcFolder = ROOT_FOLDER + "chinese/"
    private val destFolder = ROOT_FOLDER + "chinese/"

    @Test
    fun testFixCnWordWithPinyinsAndStrokesThenOK() {
        Files.newBufferedReader(Paths.get(srcFolder + "character/cnchar.csv")).use { reader ->
            val strategy = ColumnPositionMappingStrategy<CnWordLine>()
            strategy.type = CnWordLine::class.java

            // seq,word,pinyins,strokes
            strategy.setColumnMapping("seq", "word", "pinyins", "strokes")
            val csvToBean: CsvToBean<CnWordLine> = CsvToBeanBuilder<CnWordLine>(reader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
            val wordLineIterator: Iterator<CnWordLine> = csvToBean.iterator()
            val words = mutableListOf<CnWordLine>()
            while (wordLineIterator.hasNext()) {
                val wordLine: CnWordLine = wordLineIterator.next()
                val pinyins = wordLine.word?.first()
                    ?.let { PinyinHelper.convertToPinyinArray(it, PinyinFormat.WITH_TONE_MARK).joinToString(separator = "、") }

                println("${wordLine.seq},${wordLine.word},${pinyins}")
                words.add(wordLine)
            }

//            generateQuizItemsFromWords(words, qif)
        }
    }
}