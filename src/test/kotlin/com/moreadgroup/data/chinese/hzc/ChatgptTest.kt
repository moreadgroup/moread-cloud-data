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

/**
 * @Author conan8chan@yahoo.com
 * @Date 3/20/23T4:51 PM-Monday
 */
class ChatgptTest {

    private val ROOT_FOLDER = "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/"

    private val srcFolder = ROOT_FOLDER + "chinese/hzc/"
    private val destFolder = ROOT_FOLDER + "chinese/hzc/chatgpt/"

    @Test
    fun testSplitZibiao4ChatgptQuestionFilesThenOK() {

        var file_name = "通用规范汉字3级字表1605chatgpt.csv";
        var split_file_prefix = "通用规范汉字3级字表1605plus";
        val page_size = 20;
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

            var count_items = 0;
            var count_start = 1;
            var page_items = ""
            while (wordLineIterator.hasNext()) {
                val wordLine: CnCharLine = wordLineIterator.next()
                if (count_items < page_size) {
                    page_items = "${page_items}\n${wordLine.seq},${wordLine.word}"
                    count_items += 1
                } else {
                    File(destFolder + "${split_file_prefix}${count_start}.jsonl").writeText(
                        "针对如下每个汉字填写对应的信息，words里的所有组词一定要有word本字，sentences里的所有例句一定也要有word本字：\nseq,word\n" + page_items +

                                "\n例如JSONL格式：\n\n" +
                                "{\"seq\": 122, \"word\": \"钟\", \"pinyin\": \"zhōng\", \"definition\": \"用于计量和报告时间的设备，通常由一个圆形表盘和一个或多个指针组成\", \"words\":[ \"时钟\", \"铃声\"], \"sentences\":[ \"这个房间里有一个大的墙上挂钟。\", \"我设置了三个不同的闹钟，因为我经常会睡过头。\" ] }\n"
                    )
                    count_start += page_size
                    page_items = "${wordLine.seq},${wordLine.word}"
                    count_items = 1;
                }
            }
            println(
                "针对如下每个汉字填写对应的信息，words里的所有组词一定要有word本字，sentences里的所有例句一定也要有word本字：\nseq,word\n" + page_items +

                        "\n例如JSONL格式：\n\n" +
                        "{\"seq\": 122, \"word\": \"钟\", \"pinyin\": \"zhōng\", \"definition\": \"用于计量和报告时间的设备，通常由一个圆形表盘和一个或多个指针组成\", \"words\":[ \"时钟\", \"铃声\"], \"sentences\":[ \"这个房间里有一个大的墙上挂钟。\", \"我设置了三个不同的闹钟，因为我经常会睡过头。\" ] }\n"
            )
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