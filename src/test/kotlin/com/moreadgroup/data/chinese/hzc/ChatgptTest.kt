package com.moreadgroup.data.chinese.hzc

import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import com.moreadgroup.data.chinese.CnCharLine
import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
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
        var split_file_prefix = "义务教育字表一";
        split_file_prefix = "义务教育字表一";
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
            var count_start=1;
            var page_items = ""
            while (wordLineIterator.hasNext()) {
                val wordLine: CnCharLine = wordLineIterator.next()
                if (count_items < page_size) {
                    page_items = "${page_items}\n${wordLine.seq},${wordLine.word}"
                    count_items += 1
                }else{
                    File(destFolder+"${split_file_prefix}${count_start}.jsonl").writeText(
                        "针对如下每个汉字填写对应的信息，words里的所有组词一定要有word本字，sentences里的所有例句一定也要有word本字： \n\n" + page_items +

                    "\n例如JSONL格式：\n\n"+
                    "{\"seq\": 122, \"word\": \"钟\", \"pinyin\": \"zhōng\", \"definition\": \"用于计量和报告时间的设备，通常由一个圆形表盘和一个或多个指针组成\", \"words\":[ \"时钟\", \"铃声\"], \"sentences\":[ \"这个房间里有一个大的墙上挂钟。\", \"我设置了三个不同的闹钟，因为我经常会睡过头。\" ] }\n"
                    )
                    count_start += page_size
                    page_items = "${wordLine.seq},${wordLine.word}"
                    count_items = 1;
                }
            }
            println("针对如下每个汉字填写对应的信息，words里的所有组词一定要有word本字，sentences里的所有例句一定也要有word本字：\n\n" + page_items +

            "\n例如JSONL格式：\n\n"+
                    "{\"seq\": 122, \"word\": \"钟\", \"pinyin\": \"zhōng\", \"definition\": \"用于计量和报告时间的设备，通常由一个圆形表盘和一个或多个指针组成\", \"words\":[ \"时钟\", \"铃声\"], \"sentences\":[ \"这个房间里有一个大的墙上挂钟。\", \"我设置了三个不同的闹钟，因为我经常会睡过头。\" ] }\n"
            )
        }
    }

}