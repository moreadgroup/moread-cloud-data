package com.moreadgroup.data.chinese

import com.moreadgroup.data.QuizLine
import com.moreadgroup.data.magicear8times.QuizItemLine
import com.opencsv.CSVWriter
import com.opencsv.bean.*
import org.jetbrains.kotlin.util.collectionUtils.concat
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.streams.toList

/**
 * @Author conan8chan@yahoo.com
 * @Date 8/24/21T5:00 PM-Tuesday
 */
class CnWordQuizTest {

    private val ROOT_FOLDER = "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/"

    //    val ROOT_FOLDER = "/Users/conanchen/github/moreadgroup/moread-cloud-data/src/main/resources/"
    private val GRAPHQL_SERVER = "http://localhost:18060/domain-task/graphql"

    private val srcFolder = ROOT_FOLDER + "chinese/"
    private val destFolder = ROOT_FOLDER + "chinese/"

    @Test
    fun testGeneratePinyinQuizzesThenOK() {

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
                words.add(wordLine)
            }

            generatePinyinQuizItemsFromWords(
                words,
                destFolder + "quizzes/quiz-pinyin-item.csv",
                destFolder + "quizzes/quiz-pinyin.csv"
            )
        }
    }

    private fun generatePinyinQuizItemsFromWords(
        wordLines: MutableList<CnWordLine>,
        qiuzItemFilePath: String,
        qiuzFilePath: String
    ) {

        val qiids = mutableListOf<String>()

        Files.newBufferedWriter(Paths.get(qiuzItemFilePath)).use { writer ->

            val strategy = ColumnPositionMappingStrategy<QuizItemLine>()
            strategy.type = QuizItemLine::class.java
            strategy.setColumnMapping(
                "id",
                "level",
                "mchoice",
                "ans",
                "eword",
                "title",
                "desc",
                "opa",
                "opb",
            )

            writer.write("id,level,mchoice,ans,eword,title,desc,opa,opb\n")

            val beanToCsv: StatefulBeanToCsv<QuizItemLine> = StatefulBeanToCsvBuilder<QuizItemLine>(writer)
                .withMappingStrategy(strategy)
                .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)

                .build()


            wordLines.stream().map {
                generatePinyinQuizItemLineFromWord(it, wordLines)
            }.forEach {
                beanToCsv.write(it)
                qiids.add(it.id)
            }
        }

        Files.newBufferedWriter(Paths.get(qiuzFilePath)).use { writer ->

            val strategy = ColumnPositionMappingStrategy<QuizLine>()
            strategy.type = QuizLine::class.java
            strategy.setColumnMapping(
                "id",
                "seq",
                "level",
                "title",
                "desc",
                "items",
            )

            writer.write("id,seq,level,title,desc,items\n")

            val beanToCsv: StatefulBeanToCsv<QuizLine> = StatefulBeanToCsvBuilder<QuizLine>(writer)
                .withMappingStrategy(strategy)
                .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)

                .build()


            //目录
            //1	一级字表（3500字）
            //2	二级字表（3000字）
            //3	三级字表（1605字）
            qiids.chunked(100).stream().toList().mapIndexed { index, list ->
                val items = list.joinToString(",") { it }
                val level =
                    if (index <= 35) "level1"
                    else if (index <= 60) "level2"
                    else "level3"
                val stitle =
                    if (index <= 35) "一级字表"
                    else if (index <= 60) "二级字表"
                    else "三级字表"
                val ltitle =
                    if (index <= 35) "一级字表（3500字）"
                    else if (index <= 60) "二级字表（3000字）"
                    else "三级字表（1605字）"

                QuizLine(
                    "cnwq$index",
                    "1%03d".format(index),
                    level,
                    "拼音测试$stitle$index",
                    "拼音测试通用规范汉字表$ltitle$index,汉字拼音测试$index",
                    items
                )
            }.forEach {
                beanToCsv.write(it)
            }
        }

    }

    private fun generatePinyinQuizItemLineFromWord(
        cnWord: CnWordLine,
        wordLines: MutableList<CnWordLine>
    ): QuizItemLine {

        val type =
            if (Integer.valueOf(cnWord.seq) <= 3500) "primary"
            else if (Integer.valueOf(cnWord.seq) <= 6500) "junior"
            else
                "senior"

        val qil = QuizItemLine(
            id = "cnwpyqi${cnWord.seq!!}",
            level = type,
            mchoice = "N",
            ans = listOf("A", "B").shuffled()[0],
            eword = cnWord.word!!,
            title = cnWord.word!!,
            desc = cnWord.word!!,
            opa = cnWord.pinyins,
            opb = cnWord.pinyins,
        )
        val usedWords = mutableListOf<CnWordLine>(cnWord)
        if ("A" != qil.ans) {
            val randomWord = wordLines.stream().filter { !usedWords.contains(it) }.toList().shuffled()[0]
            println("radomWord=$randomWord")
            qil.opa = randomWord.pinyins!!
            usedWords.add(randomWord)
        }
        if ("B" != qil.ans) {
            val randomWord = wordLines.stream().filter { !usedWords.contains(it) }.toList().shuffled()[0]
            println("radomWord=$randomWord")
            qil.opb = randomWord.pinyins!!
            usedWords.add(randomWord)
        }
        return qil
    }


    @Test
    fun testGenerateStrokeQuizzesThenOK() {

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
                words.add(wordLine)
            }

            generateStrokeQuizItemsFromWords(
                words,
                destFolder + "quizzes/quiz-stroke-item.csv",
                destFolder + "quizzes/quiz-stroke.csv"
            )
        }
    }

    private fun generateStrokeQuizItemsFromWords(
        wordLines: MutableList<CnWordLine>,
        qiuzItemFilePath: String,
        qiuzFilePath: String
    ) {

        val qiids = mutableListOf<String>()

        Files.newBufferedWriter(Paths.get(qiuzItemFilePath)).use { writer ->

            val strategy = ColumnPositionMappingStrategy<QuizItemLine>()
            strategy.type = QuizItemLine::class.java
            strategy.setColumnMapping(
                "id",
                "level",
                "mchoice",
                "ans",
                "eword",
                "title",
                "desc",
                "opa",
                "opb",
            )

            writer.write("id,type,mchoice,ans,eword,title,desc,opa,opb\n")

            val beanToCsv: StatefulBeanToCsv<QuizItemLine> = StatefulBeanToCsvBuilder<QuizItemLine>(writer)
                .withMappingStrategy(strategy)
                .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)

                .build()


            wordLines.stream().map {
                generateStrokeQuizItemLineFromWord(it)
            }.forEach {
                beanToCsv.write(it)
                qiids.add(it.id)
            }
        }

        Files.newBufferedWriter(Paths.get(qiuzFilePath)).use { writer ->

            val strategy = ColumnPositionMappingStrategy<QuizLine>()
            strategy.type = QuizLine::class.java
            strategy.setColumnMapping(
                "id",
                "seq",
                "level",
                "title",
                "desc",
                "items",
            )

            writer.write("id,seq,level,title,desc,items\n")

            val beanToCsv: StatefulBeanToCsv<QuizLine> = StatefulBeanToCsvBuilder<QuizLine>(writer)
                .withMappingStrategy(strategy)
                .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)

                .build()


            //目录
            //1	一级字表（3500字）
            //2	二级字表（3000字）
            //3	三级字表（1605字）
            qiids.chunked(100).stream().toList().mapIndexed { index, list ->
                val items = list.joinToString(",") { it }
                val level =
                    if (index <= 35) "level1"
                    else if (index <= 60) "level2"
                    else "level3"
                val stitle =
                    if (index <= 35) "一级字表"
                    else if (index <= 60) "二级字表"
                    else "三级字表"
                val ltitle =
                    if (index <= 35) "一级字表（3500字）"
                    else if (index <= 60) "二级字表（3000字）"
                    else "三级字表（1605字）"

                QuizLine(
                    "cnwstq$index",
                    "2%03d".format(index),
                    level,
                    "笔画测试$stitle$index",
                    "笔画测试通用规范汉字表$ltitle$index,汉字笔画测试$index",
                    items
                )
            }.forEach {
                beanToCsv.write(it)
            }
        }

    }

    private fun generateStrokeQuizItemLineFromWord(cnWord: CnWordLine): QuizItemLine {

        val type =
            if (Integer.valueOf(cnWord.seq) <= 3500) "primary"
            else if (Integer.valueOf(cnWord.seq) <= 6500) "junior"
            else
                "senior"

        val qil = QuizItemLine(
            id = "cnwstqi${cnWord.seq!!}",
            level = type,
            mchoice = "N",
            ans = listOf("A", "B").shuffled()[0],
            eword = cnWord.word!!,
            title = cnWord.word!!,
            desc = cnWord.word!!,
            opa = cnWord.strokes,
            opb = cnWord.strokes,
        )
        if ("A" != qil.ans) {
            qil.opa = shuffleStrokes(cnWord)
            var i = 0;
            if(!qil.opb.isNullOrBlank() && !qil.opa.isNullOrBlank()) {
                while(qil.opb!!.compareTo(qil.opa!!) ==0 && i<5){
                    qil.opa = shuffleStrokes(cnWord)
                    i++
                }
                // last chance
                if (qil.opb!!.compareTo(qil.opa!!) == 0){
                    qil.opa = "X"
                }
            }
        }

        if ("B" != qil.ans) {
            qil.opb = shuffleStrokes(cnWord)
            if(!qil.opb.isNullOrBlank() && !qil.opa.isNullOrBlank()) {
                var i = 0;
                while(qil.opb!!.compareTo(qil.opa!!) ==0 && i<5){
                    qil.opb = shuffleStrokes(cnWord)
                    i++
                }
                // last chance
                if (qil.opb!!.compareTo(qil.opa!!) == 0){
                    qil.opb = "X"
                }
            }
        }
        return qil
    }

    private fun shuffleStrokes(cnWord: CnWordLine): String? = Optional.ofNullable(cnWord.strokes).map {
        var result: String? = "X"
        val ars = it.split(" ")
        if (ars.size > 3) {
            val first = ars.slice(IntRange(0, 1))
            val last = ars.slice(IntRange(2, ars.size - 1)).shuffled()
            val sss = mutableListOf<String>()
            sss.addAll(first)
            sss.addAll(last)
            result = sss.joinToString(" ") { it }

            println("word=${cnWord.word},ars=${ars}, first=${first}, last=${last}, concat=${sss}, result=$result")

        }
        result

    }.orElse("")


}