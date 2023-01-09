package com.moreadgroup.data.chinese

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import com.moreadgroup.data.chinese.hzc.Hzc
import com.moreadgroup.data.chinese.hzc.HzcChar
import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


/**
 * @Author conan8chan@yahoo.com
 * @Date 8/24/21T5:00 PM-Tuesday
 */
class CnCharFixTest {

    private val ROOT_FOLDER = "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/"

    private val srcFolder = ROOT_FOLDER + "chinese/"
    private val destFolder = ROOT_FOLDER + "chinese/"

    @Test
    fun testFixCnWordWithPinyinsAndStrokesAndExplanationThenOK() {
        val strokeOrderJian = prepareCnWordStrokeOrderJain()
        val strokeTable = prepareCnWordStrokeTable()
        val strokeNumMap = prepareCnWordStrokeNum();
        val xinhuaWordMap = prepareCnWordXinhuaWord();
        val hzcTables: Hzc = prepareHzcTables();

        Files.newBufferedReader(Paths.get(srcFolder + "cnchar.csv")).use { reader ->
            val strategy = ColumnPositionMappingStrategy<CnCharLine>()
            strategy.type = CnCharLine::class.java

            // seq,word,hzctable,strokesnum,pinyins,strokes,strokesok,explanation
            strategy.setColumnMapping(
                "seq",
                "word",
                "hzctable",
                "strokesnum",
                "pinyins",
                "strokes",
                "strokesok",
                "explanation"
            )
            val csvToBean: CsvToBean<CnCharLine> = CsvToBeanBuilder<CnCharLine>(reader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
            val wordLineIterator: Iterator<CnCharLine> = csvToBean.iterator()
            while (wordLineIterator.hasNext()) {
                val wordLine: CnCharLine = wordLineIterator.next()
                val hzcChar: HzcChar? = wordLine.word
                    ?.let {
                        getCnWordHzcChar(it, hzcTables)
                    }


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

                val explanation =
//                    if (wordLine.explanation.isNullOrEmpty())
                    wordLine.word
                        ?.let {
                            getCnWordExplanation(it, xinhuaWordMap)
                        }.orEmpty().trim();
//                    else
//                        wordLine.explanation

                println("${wordLine.seq},${wordLine.word},${hzcChar?.hzctable},${hzcChar?.strokenum},${pinyins},${strokes},${strokeSize == strokesNum}(a${strokeSize}~e${strokesNum}),${explanation}")
            }

        }
    }

    private fun getCnWordHzcChar(word: String, hzcTables: Hzc): HzcChar? {
        var result = HzcChar();

        for (table in hzcTables.hzctables) {
            for (stroke in table.strokes) {
                for (it in stroke.words) {
                    if (it.word.equals(word)) {
                        result = HzcChar(table.table, stroke.stroke, word)
                        break;
                    }
                }
            }
        }


        return result
    }

    private fun prepareHzcTables(): Hzc {

        val mapper = ObjectMapper(YAMLFactory()).findAndRegisterModules()
        val yasi: Hzc = mapper.readValue(
            File(srcFolder + "hzc/汉字应用水平等级及测试大纲.yaml"),
            Hzc::class.java
        )

        return yasi
    }

    @Test
    fun testPrepareHzcTablesThenOk() {
        prepareHzcTables();
    }

    private fun getCnWordExplanation(it: String, xinhuaWordMap: Map<String, XinhuaWord>): String? {

        val result = xinhuaWordMap.get(it)?.explanation
//            ?.replace(Regex("[\na-zA-Zāáǎàēéěèīíǐìōóǒòūúǔùǖǘǚǜ]+"), "")
            ?.replace(Regex("[\n]+"), "")
            ?.replace(",", "，")
            ?.replace(Regex("\""), "'")
            ?.replace(Regex("  ")," ")

//        if (it.equals("挨", true)) {
//            println("explanation result=${result} vs ${xinhuaWordMap.get(it)?.explanation}")
//        }

        return result

    }

    private fun prepareCnWordXinhuaWord(): Map<String, XinhuaWord> {
        val result = mutableMapOf<String, XinhuaWord>()
        Files.newBufferedReader(Paths.get(srcFolder + "xinhua/word.json")).use { reader ->

            val moshi: Moshi = Moshi.Builder().addLast(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
                .build()

            val type = Types.newParameterizedType(
                MutableList::class.java,
                XinhuaWord::class.java
            )

            val jsonAdapter: JsonAdapter<List<XinhuaWord>> = moshi.adapter(type)

            val blackjackHand: List<XinhuaWord>? = jsonAdapter.fromJson(reader.readText())
            blackjackHand?.let {
                it.stream().forEach { xw ->
                    result.put(xw.word!!, xw)
                }
            }
        }
        return result
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
            val strategy = ColumnPositionMappingStrategy<CnCharLine>()
            strategy.type = CnCharLine::class.java

            // seq,word,pinyins,strokes
            strategy.setColumnMapping("seq", "word", "pinyins", "strokes")
            val csvToBean: CsvToBean<CnCharLine> = CsvToBeanBuilder<CnCharLine>(reader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
            val wordLineIterator: Iterator<CnCharLine> = csvToBean.iterator()
            var currentStrokeNumber = 1
            while (wordLineIterator.hasNext()) {
                val wordLine: CnCharLine = wordLineIterator.next()
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