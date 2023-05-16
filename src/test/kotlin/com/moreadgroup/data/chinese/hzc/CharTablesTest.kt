package com.moreadgroup.data.chinese.hzc

import kotlinx.serialization.json.*

import com.moreadgroup.data.chinese.CnCharLine
import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import kotlinx.serialization.decodeFromString
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

/**
 * @Author conan8chan@yahoo.com
 * @Date 5/16/23T4:51 PM-Tuesday
 */
class CharTablesTest {

    private val ROOT_FOLDER = "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/chinese/hzc/"

    private val srcFolder = ROOT_FOLDER + "chartables/"
    private val destFolder = ROOT_FOLDER + "chartables/out"

    @Test
    fun testMergeChineseWordTagsFromTableFilesThenOK() {

        var cnWordsMap: HashMap<String, HashSet<String>> = HashMap();
        listOf(
            CharTableFile("通用规范汉字表1级字3500.csv", "通用1级","通用规范汉字表1级字3500.jsonl"),
            CharTableFile("通用规范汉字表2级字3000.csv", "通用2级","通用规范汉字表2级字3000.jsonl"),
            CharTableFile("通用规范汉字表3级字1605.csv", "通用3级","通用规范汉字表3级字1605.jsonl"),
            CharTableFile("汉字应用水平等级1甲表4000.csv", "应用甲表","汉字应用水平等级1甲表4000.jsonl"),
            CharTableFile("汉字应用水平等级2乙表500.csv", "应用乙表","汉字应用水平等级2乙表500.jsonl"),
            CharTableFile("汉字应用水平等级3丙表1000.csv", "应用丙表","汉字应用水平等级3丙表1000.jsonl"),
            CharTableFile("义务教育语文字表二1000.csv", "义务表二","义务教育语文字表二1000.jsonl"),
            CharTableFile("义务教育语文字表一2500.csv", "义务表一","义务教育语文字表一2500.jsonl"),
            CharTableFile("义务教育语文识字写字教学基本字表300.csv", "义务基表","国际中文教育中文水平1级初等300.jsonl"),
            CharTableFile("国际中文教育中文水平1级初等300.csv", "国际1级初等","国际中文教育中文水平2级初等300.jsonl"),
            CharTableFile("国际中文教育中文水平2级初等300.csv", "国际2级初等","国际中文教育中文水平3级初等300.jsonl"),
            CharTableFile("国际中文教育中文水平3级初等300.csv", "国际3级初等","国际中文教育中文水平4级中等300.jsonl"),
            CharTableFile("国际中文教育中文水平4级中等300.csv", "国际4级中等","国际中文教育中文水平5级中等300.jsonl"),
            CharTableFile("国际中文教育中文水平5级中等300.csv", "国际5级中等","国际中文教育中文水平6级中等300.jsonl"),
            CharTableFile("国际中文教育中文水平6级中等300.csv", "国际6级中等","国际中文教育中文水平789级高等1200.jsonl"),
            CharTableFile("国际中文教育中文水平789级高等1200.csv", "国际789级高等","义务教育语文识字写字教学基本字表300.jsonl"),
        ).forEach { ctf ->
            var file_name = ctf.srcFileName;
            Files.newBufferedReader(Paths.get(srcFolder + file_name)).use { reader ->
                val strategy = ColumnPositionMappingStrategy<CnCharLine>()
                strategy.type = CnCharLine::class.java

                // seq,word,hzctable,strokesnum,pinyins,strokes,strokesok,explanation
                strategy.setColumnMapping(
                    "seq",
                    "word",
                )
                val csvToBean: CsvToBean<CnCharLine> = CsvToBeanBuilder<CnCharLine>(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                val wordLineIterator: Iterator<CnCharLine> = csvToBean.iterator()

                while (wordLineIterator.hasNext()) {
                    val wordLine: CnCharLine = wordLineIterator.next()
                    var tags = cnWordsMap.get(wordLine.word!!);
                    if (Objects.isNull(tags)) {
                        tags = HashSet<String>();
                        tags.add(ctf.tag)
                    } else {
                        tags?.add(ctf.tag)
                    }

                    cnWordsMap.put(wordLine.word!!, tags!!)
                }

            }
        }

        println("size=${cnWordsMap.size}")

        cnWordsMap.forEach { t, u ->
            val json = buildJsonObject {
                put("word", t)
                putJsonArray("tags") {
                       u.forEach {it-> add(it) }
                }
            }
            println("${json}")
        }
    }

    @Test
    fun testCheckMissingWords4ChatgptQuestionThenOK() {
        val okJsonlFilenames = listOf(
            "${destFolder}/义务教育语文字表一000.jsonl",
            "${destFolder}/义务教育语文字表二000.jsonl",
            "${destFolder}/汉字应用水平等级丙表1000plus000.jsonl",
            "${destFolder}/汉字应用水平等级乙表500plus000.jsonl",
            "${destFolder}/汉字应用水平等级甲表4000plus000.jsonl",
        )

        // 读取所有文件并解析为JSON对象列表
        val jsonList = readJsonlFiles(okJsonlFilenames)

        // 将JSON对象列表转换为 Person 对象列表
        val okWords = jsonList.map {
            CnCharLine(
                it["seq"]!!.jsonPrimitive.content,
                it["word"]!!.jsonPrimitive.content,
            )
        }


        var
                to_check_file_name = "${srcFolder}通用规范汉字1级字表3500.csv";
        to_check_file_name = "${srcFolder}通用规范汉字2级字表3000.csv";
        to_check_file_name = "${srcFolder}通用规范汉字3级字表1605.csv";

        File(to_check_file_name).readLines().forEach { line ->
            val parts = line.split(",") // 假设列之间由逗号分隔
            val f = okWords.find { it.word?.compareTo(parts[1].trim()) == 0 }
            if (f == null) {
                println("${parts[0].trim()},${parts[1].trim()}")
            }
        }
        // 打印 Person 对象列表
//        println(okWords)


    }


    // 遍历文件并解析为JSON对象列表
    fun readJsonlFiles(filenames: List<String>): List<JsonObject> {
        val jsonList = mutableListOf<JsonObject>()
        filenames.forEach { filename ->
            println("readJsonlFiles: filename=${filename}")
            val jsonlText = File(filename).readText()
            val fileJsonList = jsonlText.lines().map { Json.decodeFromString<JsonObject>(it) }
            jsonList.addAll(fileJsonList)
        }
        return jsonList
    }
}

data class CharTableFile(val srcFileName: String, val tag: String, val destFileName:String)
