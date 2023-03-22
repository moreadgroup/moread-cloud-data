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

        var file_name = "义务教育字表一2500.csv";
        file_name = "义务教育字表一2500.csv";
        file_name = "hzc甲表4000chatgpt.csv";
        file_name = "hzc乙表500chatgpt.csv";
//        file_name = "hzc丙表1000chatgpt.csv";
        var split_file_prefix = "义务教育字表一";
        split_file_prefix = "义务教育字表一";
        split_file_prefix = "hzc甲表4000chatgpt";
        split_file_prefix = "hzc乙表500chatgpt";
//        split_file_prefix = "hzc丙表1000chatgpt";
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
                        "针对如下每个汉字填写对应的信息，words里的所有组词一定要有word本字，sentences里的所有例句一定也要有word本字：\n seq,word\n" + page_items +

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
                        "{\"seq\": 122, \"word\": \"钟\", \"pinyin\": \"zhōng\", \"definition\": \"用于计量和报告时间的设备，通常由一个圆形表盘和一个或多个指针组成\", \"words\":[ \"时钟\", \"钟声\"], \"sentences\":[ \"这个房间里有一个大的墙上挂钟。\", \"我设置了三个不同的闹钟，因为我经常会睡过头。\" ] }\n"
            )
        }
    }

    @Test
    fun testCheckMissingWords4ChatgptQuestionThenOK() {
        val okJsonlFilenames = listOf(
            "${destFolder}/义务教育字表一1.jsonl",
            "${destFolder}/义务教育字表一1001.jsonl",
            "${destFolder}/义务教育字表一101.jsonl",
            "${destFolder}/义务教育字表一1021.jsonl",
            "${destFolder}/义务教育字表一1041.jsonl",
            "${destFolder}/义务教育字表一1061.jsonl",
            "${destFolder}/义务教育字表一1081.jsonl",
            "${destFolder}/义务教育字表一1101.jsonl",
            "${destFolder}/义务教育字表一1121.jsonl",
            "${destFolder}/义务教育字表一1141.jsonl",
            "${destFolder}/义务教育字表一1161.jsonl",
            "${destFolder}/义务教育字表一1181.jsonl",
            "${destFolder}/义务教育字表一1201.jsonl",
            "${destFolder}/义务教育字表一121.jsonl",
            "${destFolder}/义务教育字表一1221.jsonl",
            "${destFolder}/义务教育字表一1241.jsonl",
            "${destFolder}/义务教育字表一1261.jsonl",
            "${destFolder}/义务教育字表一1281.jsonl",
            "${destFolder}/义务教育字表一1301.jsonl",
            "${destFolder}/义务教育字表一1321.jsonl",
            "${destFolder}/义务教育字表一1341.jsonl",
            "${destFolder}/义务教育字表一1361.jsonl",
            "${destFolder}/义务教育字表一1381.jsonl",
            "${destFolder}/义务教育字表一1401.jsonl",
            "${destFolder}/义务教育字表一141.jsonl",
            "${destFolder}/义务教育字表一1421.jsonl",
            "${destFolder}/义务教育字表一1441.jsonl",
            "${destFolder}/义务教育字表一1461.jsonl",
            "${destFolder}/义务教育字表一1481.jsonl",
            "${destFolder}/义务教育字表一1501.jsonl",
            "${destFolder}/义务教育字表一1521.jsonl",
            "${destFolder}/义务教育字表一1541.jsonl",
            "${destFolder}/义务教育字表一1561.jsonl",
            "${destFolder}/义务教育字表一1581.jsonl",
            "${destFolder}/义务教育字表一1601.jsonl",
            "${destFolder}/义务教育字表一161.jsonl",
            "${destFolder}/义务教育字表一1621.jsonl",
            "${destFolder}/义务教育字表一1641.jsonl",
            "${destFolder}/义务教育字表一1661.jsonl",
            "${destFolder}/义务教育字表一1681.jsonl",
            "${destFolder}/义务教育字表一1701.jsonl",
            "${destFolder}/义务教育字表一1721.jsonl",
            "${destFolder}/义务教育字表一1741.jsonl",
            "${destFolder}/义务教育字表一1761.jsonl",
            "${destFolder}/义务教育字表一1781.jsonl",
            "${destFolder}/义务教育字表一1801.jsonl",
            "${destFolder}/义务教育字表一181.jsonl",
            "${destFolder}/义务教育字表一1821.jsonl",
            "${destFolder}/义务教育字表一1841.jsonl",
            "${destFolder}/义务教育字表一1861.jsonl",
            "${destFolder}/义务教育字表一1881.jsonl",
            "${destFolder}/义务教育字表一1901.jsonl",
            "${destFolder}/义务教育字表一1921.jsonl",
            "${destFolder}/义务教育字表一1941.jsonl",
            "${destFolder}/义务教育字表一1961.jsonl",
            "${destFolder}/义务教育字表一1981.jsonl",
            "${destFolder}/义务教育字表一2001.jsonl",
            "${destFolder}/义务教育字表一201.jsonl",
            "${destFolder}/义务教育字表一2021.jsonl",
            "${destFolder}/义务教育字表一2041.jsonl",
            "${destFolder}/义务教育字表一2061.jsonl",
            "${destFolder}/义务教育字表一2081.jsonl",
            "${destFolder}/义务教育字表一21.jsonl",
            "${destFolder}/义务教育字表一2101.jsonl",
            "${destFolder}/义务教育字表一2121.jsonl",
            "${destFolder}/义务教育字表一2141.jsonl",
            "${destFolder}/义务教育字表一2161.jsonl",
            "${destFolder}/义务教育字表一2181.jsonl",
            "${destFolder}/义务教育字表一2201.jsonl",
            "${destFolder}/义务教育字表一221.jsonl",
            "${destFolder}/义务教育字表一2221.jsonl",
            "${destFolder}/义务教育字表一2241.jsonl",
            "${destFolder}/义务教育字表一2261.jsonl",
            "${destFolder}/义务教育字表一2281.jsonl",
            "${destFolder}/义务教育字表一2301.jsonl",
            "${destFolder}/义务教育字表一2321.jsonl",
            "${destFolder}/义务教育字表一2341.jsonl",
            "${destFolder}/义务教育字表一2361.jsonl",
            "${destFolder}/义务教育字表一2381.jsonl",
            "${destFolder}/义务教育字表一2401.jsonl",
            "${destFolder}/义务教育字表一241.jsonl",
            "${destFolder}/义务教育字表一2421.jsonl",
            "${destFolder}/义务教育字表一2441.jsonl",
            "${destFolder}/义务教育字表一2461.jsonl",
            "${destFolder}/义务教育字表一2481.jsonl",
            "${destFolder}/义务教育字表一261.jsonl",
            "${destFolder}/义务教育字表一281.jsonl",
            "${destFolder}/义务教育字表一301.jsonl",
            "${destFolder}/义务教育字表一321.jsonl",
            "${destFolder}/义务教育字表一341.jsonl",
            "${destFolder}/义务教育字表一361.jsonl",
            "${destFolder}/义务教育字表一381.jsonl",
            "${destFolder}/义务教育字表一401.jsonl",
            "${destFolder}/义务教育字表一41.jsonl",
            "${destFolder}/义务教育字表一421.jsonl",
            "${destFolder}/义务教育字表一441.jsonl",
            "${destFolder}/义务教育字表一461.jsonl",
            "${destFolder}/义务教育字表一481.jsonl",
            "${destFolder}/义务教育字表一501.jsonl",
            "${destFolder}/义务教育字表一521.jsonl",
            "${destFolder}/义务教育字表一541.jsonl",
            "${destFolder}/义务教育字表一561.jsonl",
            "${destFolder}/义务教育字表一581.jsonl",
            "${destFolder}/义务教育字表一601.jsonl",
            "${destFolder}/义务教育字表一61.jsonl",
            "${destFolder}/义务教育字表一621.jsonl",
            "${destFolder}/义务教育字表一641.jsonl",
            "${destFolder}/义务教育字表一661.jsonl",
            "${destFolder}/义务教育字表一681.jsonl",
            "${destFolder}/义务教育字表一701.jsonl",
            "${destFolder}/义务教育字表一721.jsonl",
            "${destFolder}/义务教育字表一741.jsonl",
            "${destFolder}/义务教育字表一761.jsonl",
            "${destFolder}/义务教育字表一781.jsonl",
            "${destFolder}/义务教育字表一801.jsonl",
            "${destFolder}/义务教育字表一81.jsonl",
            "${destFolder}/义务教育字表一821.jsonl",
            "${destFolder}/义务教育字表一841.jsonl",
            "${destFolder}/义务教育字表一861.jsonl",
            "${destFolder}/义务教育字表一881.jsonl",
            "${destFolder}/义务教育字表一901.jsonl",
            "${destFolder}/义务教育字表一921.jsonl",
            "${destFolder}/义务教育字表一941.jsonl",
            "${destFolder}/义务教育字表一961.jsonl",
            "${destFolder}/义务教育字表一981.jsonl",
            "${destFolder}/义务教育字表二1.jsonl",
            "${destFolder}/义务教育字表二101.jsonl",
            "${destFolder}/义务教育字表二121.jsonl",
            "${destFolder}/义务教育字表二141.jsonl",
            "${destFolder}/义务教育字表二161.jsonl",
            "${destFolder}/义务教育字表二181.jsonl",
            "${destFolder}/义务教育字表二201.jsonl",
            "${destFolder}/义务教育字表二21.jsonl",
            "${destFolder}/义务教育字表二221.jsonl",
            "${destFolder}/义务教育字表二241.jsonl",
            "${destFolder}/义务教育字表二261.jsonl",
            "${destFolder}/义务教育字表二281.jsonl",
            "${destFolder}/义务教育字表二301.jsonl",
            "${destFolder}/义务教育字表二321.jsonl",
            "${destFolder}/义务教育字表二341.jsonl",
            "${destFolder}/义务教育字表二361.jsonl",
            "${destFolder}/义务教育字表二381.jsonl",
            "${destFolder}/义务教育字表二401.jsonl",
            "${destFolder}/义务教育字表二41.jsonl",
            "${destFolder}/义务教育字表二421.jsonl",
            "${destFolder}/义务教育字表二441.jsonl",
            "${destFolder}/义务教育字表二461.jsonl",
            "${destFolder}/义务教育字表二481.jsonl",
            "${destFolder}/义务教育字表二501.jsonl",
            "${destFolder}/义务教育字表二521.jsonl",
            "${destFolder}/义务教育字表二541.jsonl",
            "${destFolder}/义务教育字表二561.jsonl",
            "${destFolder}/义务教育字表二581.jsonl",
            "${destFolder}/义务教育字表二601.jsonl",
            "${destFolder}/义务教育字表二61.jsonl",
            "${destFolder}/义务教育字表二621.jsonl",
            "${destFolder}/义务教育字表二641.jsonl",
            "${destFolder}/义务教育字表二661.jsonl",
            "${destFolder}/义务教育字表二681.jsonl",
            "${destFolder}/义务教育字表二701.jsonl",
            "${destFolder}/义务教育字表二721.jsonl",
            "${destFolder}/义务教育字表二741.jsonl",
            "${destFolder}/义务教育字表二761.jsonl",
            "${destFolder}/义务教育字表二781.jsonl",
            "${destFolder}/义务教育字表二801.jsonl",
            "${destFolder}/义务教育字表二81.jsonl",
            "${destFolder}/义务教育字表二821.jsonl",
            "${destFolder}/义务教育字表二841.jsonl",
            "${destFolder}/义务教育字表二861.jsonl",
            "${destFolder}/义务教育字表二881.jsonl",
            "${destFolder}/义务教育字表二901.jsonl",
            "${destFolder}/义务教育字表二921.jsonl",
            "${destFolder}/义务教育字表二941.jsonl",
            "${destFolder}/义务教育字表二961.jsonl",
            "${destFolder}/义务教育字表二981.jsonl",

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
                to_check_file_name = "${srcFolder}hzc甲表4000.csv";
//        to_check_file_name = "${srcFolder}hzc乙表500.csv";
//        to_check_file_name = "${srcFolder}hzc丙表1000.csv";

        File(to_check_file_name).readLines().forEach { line ->
            val parts = line.split(",") // 假设列之间由逗号分隔
            val f = okWords.find { it.word?.compareTo(parts[1]) == 0 }
            if (f == null) {
                println("${parts[0]},${parts[1]}")
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