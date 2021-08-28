package com.moreadgroup.data.chinese

import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @Author conan8chan@yahoo.com
 * @Date 8/24/21T5:00 PM-Tuesday
 */
class CnWordFixTest {

//    private val ROOT_FOLDER = "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/"

        val ROOT_FOLDER = "/Users/conanchen/github/moreadgroup/moread-cloud-data/src/main/resources/"
    private val GRAPHQL_SERVER = "http://localhost:18060/domain-task/graphql"

    private val srcFolder = ROOT_FOLDER + "chinese/"
    private val destFolder = ROOT_FOLDER + "chinese/"

    @Test
    fun testFixCnWordWithPinyinsAndStrokesThenOK() {
        val strokeOrderJian = prepareCnWordStrokeOrderJain()
        val strokeTable = prepareCnWordStrokeTable()
        val strokeNumMap = prepareCnWordStrokeNum();

        Files.newBufferedReader(Paths.get(srcFolder + "cnchar.csv")).use { reader ->
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
                val pinyins =
                    if (wordLine.pinyins.isNullOrEmpty())
                        wordLine.word
                            ?.let {
                                PinyinHelper.convertToPinyinArray(it.first(), PinyinFormat.WITH_TONE_MARK)
                                    .joinToString(separator = "、")
                            }
                    else
                        wordLine.pinyins

                val strokes =
                    if (wordLine.strokes.isNullOrEmpty())
                        wordLine.word
                            ?.let {
                                getCnWordStrokesOrder(it, strokeOrderJian, strokeTable)
                            }.orEmpty().trim()
                    else
                        wordLine.strokes

                val strokesNum = wordLine.word
                    ?.let {
                        strokeNumMap.get(it + "")
                    }?.or(0)

                val strokeSize = strokes.split(" ", "|").size

                println("${wordLine.seq},${wordLine.word},${pinyins},${strokes},${strokeSize == strokesNum}(a${strokeSize}~e${strokesNum})")
                words.add(wordLine)
            }

//            generateQuizItemsFromWords(words, qif)
        }
    }

    fun prepareCnWordStrokeOrderJain(): Map<String, String> {
        val result = mutableMapOf<String, String>()
        Files.newBufferedReader(Paths.get(srcFolder + "stroke/stroke-order-jian.json")).use { reader ->

            val moshi: Moshi = Moshi.Builder().addLast(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
                .build()
            val type = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
            val jsonAdapter: JsonAdapter<Map<String, String>> = moshi.adapter(type)

            val blackjackHand: Map<String, String>? = jsonAdapter.fromJson(reader.readText())
            blackjackHand?.let {
                result.putAll(blackjackHand)
            }
        }
        return result;
    }

    fun prepareCnWordStrokeTable(): Map<String, StrokeItem> {
        val result = mutableMapOf<String, StrokeItem>()
        Files.newBufferedReader(Paths.get(srcFolder + "stroke/stroke-table.json")).use { reader ->

            val moshi: Moshi = Moshi.Builder().addLast(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
                .build()
            val type = Types.newParameterizedType(Map::class.java, String::class.java, StrokeItem::class.java)
            val jsonAdapter: JsonAdapter<Map<String, StrokeItem>> = moshi.adapter(type)

            val blackjackHand: Map<String, StrokeItem>? = jsonAdapter.fromJson(reader.readText())
            blackjackHand?.let {
                result.putAll(blackjackHand)
            }
        }
        return result;
    }

    fun prepareCnWordStrokeNum(): Map<String, Int> {
        val result = mutableMapOf<String, Int>()
        Files.newBufferedReader(Paths.get(srcFolder + "stroke/stroke-number.csv")).use { reader ->
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
            var currentStrokeNumber = 1
            while (wordLineIterator.hasNext()) {
                val wordLine: CnWordLine = wordLineIterator.next()
                wordLine.word?.let {
                    val headLine = wordLine.seq?.indexOf("画")
                    if (headLine != null && headLine > 0) {
                        currentStrokeNumber = Integer.valueOf(wordLine.word)
                    } else {
                        result.put(wordLine.word, currentStrokeNumber)
                    }
                }
            }
        }
        return result
    }

    fun getCnWordStrokesOrder(
        word: String,
        strokeOrderJian: Map<String, String>,
        strokeTable: Map<String, StrokeItem>
    ): String? {
        return strokeOrderJian.get(word)
            ?.split("")
            ?.stream()
            ?.map { strokeTable.get(it) }
            ?.map { it?.shape }
            ?.filter { it?.isEmpty() == false }
            ?.reduce { sum, ele ->
                "$sum $ele"
            }?.orElse("")
    }

    @Test
    fun testGetCnWordStrokesOrderThenOK() {
        val strokeOrderJian = prepareCnWordStrokeOrderJain()
        val strokeTable = prepareCnWordStrokeTable()

        val strokes = getCnWordStrokesOrder("仓", strokeOrderJian, strokeTable)

        println(strokes)
    }

    @Test
    fun testPrepareCnWordStrokeOrderJianThenOK() {
        val strokeOrderJian = prepareCnWordStrokeOrderJain()

        println(strokeOrderJian)

        println("仓 : " + strokeOrderJian.get("仓"))
    }

    @Test
    fun testPrepareCnWordStrokeTableThenOK() {
        val strokeTable = prepareCnWordStrokeTable()

        println(strokeTable)

        println("a : " + strokeTable.get("a"))
    }

    @Test
    fun testPrepareCnWordStroNumThenOK() {
        val strokeNumberDict = prepareCnWordStrokeNum()
        println(strokeNumberDict)
    }
}