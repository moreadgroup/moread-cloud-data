package com.moreadgroup.data.magicear8times

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.spi.json.JacksonJsonProvider
import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import net.minidev.json.JSONObject
import org.apache.commons.io.FileUtils
import org.apache.commons.text.WordUtils
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList
import java.util.ArrayList

import com.opencsv.CSVWriter

import com.opencsv.bean.StatefulBeanToCsvBuilder

import com.opencsv.bean.StatefulBeanToCsv


/**
 * @Author conan8chan@yahoo.com
 * @Date 3/29/21T9:57 AM-Monday
 */
class MagicEar8TimesTest {

//    private val ROOT_FOLDER = "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/"
  val ROOT_FOLDER = "/Users/conanchen/github/moreadgroup/moread-cloud-data/src/main/resources/"


    private val srcFolder = ROOT_FOLDER + "static/playlist/magic-ear-8-times"
    private val destFolder = ROOT_FOLDER + "textbook/magic-ear-8-times"

    @Test
    fun testParseFromAliaiJson2VttThenOK() {

        val srcFolder = "static/playlist/magic-ear-8-times"
        val destFolder = "static/playlist/magic-ear-8-times"

        listOf(
//            "${srcFolder}/introduction/introduction-1.json",
//            "${srcFolder}/introduction/introduction-2.json",
//            "${srcFolder}/introduction/introduction-3.json",
//
//            "${srcFolder}/primary/primary-1.json",
//            "${srcFolder}/primary/primary-2.json",
            "${srcFolder}/primary/primary-3.json",
            "${srcFolder}/primary/primary-4.json",
            "${srcFolder}/primary/primary-5.json",
            "${srcFolder}/primary/primary-6.json",
            "${srcFolder}/primary/primary-7.json",
            "${srcFolder}/primary/primary-8.json",
//
//            "${srcFolder}/junior/junior-01.json",
//            "${srcFolder}/junior/junior-02.json",
//            "${srcFolder}/junior/junior-03.json",
//            "${srcFolder}/junior/junior-04.json",
//            "${srcFolder}/junior/junior-05.json",
//            "${srcFolder}/junior/junior-06.json",
//            "${srcFolder}/junior/junior-07.json",
//            "${srcFolder}/junior/junior-08.json",
//            "${srcFolder}/junior/junior-09.json",
//            "${srcFolder}/junior/junior-10.json",
//            "${srcFolder}/junior/junior-11.json",
//            "${srcFolder}/junior/junior-12.json",
//            "${srcFolder}/junior/junior-13.json",
//            "${srcFolder}/junior/junior-14.json",
//            "${srcFolder}/junior/junior-15.json",
//            "${srcFolder}/junior/junior-16.json",
//            "${srcFolder}/junior/junior-17.json",
//            "${srcFolder}/junior/junior-18.json",
//            "${srcFolder}/junior/junior-19.json",
//            "${srcFolder}/junior/junior-20.json",
//            "${srcFolder}/junior/junior-21.json",
//
//            "${srcFolder}/senior/senior-01.json",
//            "${srcFolder}/senior/senior-02.json",
//            "${srcFolder}/senior/senior-03.json",
//            "${srcFolder}/senior/senior-04.json",
//            "${srcFolder}/senior/senior-05.json",
//            "${srcFolder}/senior/senior-06.json",
//            "${srcFolder}/senior/senior-07.json",
//            "${srcFolder}/senior/senior-08.json",
//            "${srcFolder}/senior/senior-09.json",
//            "${srcFolder}/senior/senior-10.json",
//            "${srcFolder}/senior/senior-11.json",
//            "${srcFolder}/senior/senior-12.json",
//            "${srcFolder}/senior/senior-13.json",
//            "${srcFolder}/senior/senior-14.json",
//            "${srcFolder}/senior/senior-15.json",
//            "${srcFolder}/senior/senior-16.json",
//            "${srcFolder}/senior/senior-17.json",
//            "${srcFolder}/senior/senior-18.json",
//            "${srcFolder}/senior/senior-19.json",
//            "${srcFolder}/senior/senior-20.json",
//            "${srcFolder}/senior/senior-21.json",
//            "${srcFolder}/senior/senior-22.json",
//            "${srcFolder}/senior/senior-23.json",
//            "${srcFolder}/senior/senior-24.json",
//            "${srcFolder}/senior/senior-25.json",
//            "${srcFolder}/senior/senior-26.json",
//            "${srcFolder}/senior/senior-27.json",
//            "${srcFolder}/senior/senior-28.json",
//            "${srcFolder}/senior/senior-29.json",
//            "${srcFolder}/senior/senior-30.json",
        )
            .stream()

            .forEach {
                parseAliaiJson2VttItems(it)
            }


//        parseToVttItems(jsonFiles[0])

    }

    private fun parseAliaiJson2VttItems(jsonFile: String) {


        val objectMapper = ObjectMapper()
        val conf: Configuration = Configuration.builder()
            .jsonProvider(JacksonJsonProvider(objectMapper))
            .build()

        //JSON file to Java object
        val content =
            FileUtils.readFileToString(File(ClassLoader.getSystemResource(jsonFile).file), StandardCharsets.UTF_8);

        // https://jsonpath.com/ $.msg[0].task_result.detailed_result
        val jsonContext: List<LinkedHashMap<String, Any>> =
            JsonPath.using(conf).parse(content).read("\$.msg[0].task_result.detailed_result")


        val vttFileName =
            ROOT_FOLDER + jsonFile.replace(".json", ".webvtt")
        println("Writing to WEBVTT file $vttFileName")

        val deleted = File(vttFileName).delete()
        val created = File(vttFileName).createNewFile()
        val vttFile = File(vttFileName)
        vttFile.writeText(
            """
WEBVTT
X-TIMESTAMP-MAP=MPEGTS:126000,LOCAL:00:00:00.000

     """.trimIndent()
        )


        jsonContext.stream()
            .map {
                JSONObject(it).toString()
            }
            .map {

                objectMapper.readValue(it, AliTaskDetailResult::class.java)

            }.map {

                val start = formatHMSFromMs(it.begin_time)

                val end = formatHMSFromMs(it.end_time)

                var lines = ""
                WordUtils.wrap(it.res[0], 40, "\n", false, "，")
                    .lines()
                    .map {
                        lines += "<u>$it</u>\n"
                    }

                """ |
                    |$start --> $end 
                    |$lines
                    """
                    .trimMargin("|")
            }.forEach {
                vttFile.appendText(it, Charsets.UTF_8)
                println(it)
            }


    }

    private fun formatHMSFromMs(it: Int): String {
        val sh = it / 1000 / 3600
        val sm = it / 1000 / 60
        val ss = it / 1000 - sh * 3600 - sm * 60
        val sss = it % 1000
        return "%02d:%02d:%02d.%03d".format(sh, sm, ss, sss);
    }


    /**
     * {
    "res": [
    "啊，踏实新东方20年功勋教师在星宇讲台驰骋近20年累计授课超过15000个小时，曾作为新东方全国教师的唯一代表登台演讲，他被学生称为暴风女神，从小学生一直到博士生，有近百万名学生上过他的课，用他的方法轻松通过英语考试，他曾是美国宾夕法尼。"
    ],
    "end_time": 3880,
    "begin_time": 2680,
    "words_info": [],
    "sn": "206626101471616822760",
    "corpus_no": "6944200878807528977"
    }
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class AliTaskDetailResult(
        @JsonProperty("res")
        val res: List<String>,
        @JsonProperty("end_time")
        val end_time: Int,
        @JsonProperty("begin_time")
        val begin_time: Int,
        @JsonProperty("words_info")
        val words_info: List<String>,
        @JsonProperty("sn")
        val sn: String,
        @JsonProperty("corpus_no")
        val corpus_no: String,
    ) {

    }


    @Test
    fun testGenerateQuizFromWordCVSfiles() {

        listOf(
//            QuizFileInfo("${srcFolder}/primary/primary-1.csv", "meqzp01",1,"primary","Primary Quiz First"  ,"For Primary Textbook 1","qi-primary-1.csv"),

//            QuizFileInfo("${srcFolder}/primary/primary-2.csv", "meqzp02",2,"primary","Primary Quiz Second" ,"For Primary Textbook 2","qi-primary-2.csv"),
//            QuizFileInfo("${srcFolder}/primary/primary-3.csv", "meqzp03",3,"primary","Primary Quiz Third"  ,"For Primary Textbook 3","qi-primary-3.csv"),
//            QuizFileInfo("${srcFolder}/primary/primary-4.csv", "meqzp04",4,"primary","Primary Quiz Fourth" ,"For Primary Textbook 4","qi-primary-4.csv"),
//            QuizFileInfo("${srcFolder}/primary/primary-5.csv", "meqzp05",5,"primary","Primary Quiz Fifth"  ,"For Primary Textbook 5","qi-primary-5.csv"),
//            QuizFileInfo("${srcFolder}/primary/primary-6.csv", "meqzp06",6,"primary","Primary Quiz Sixth"  ,"For Primary Textbook 6","qi-primary-6.csv"),
//            QuizFileInfo("${srcFolder}/primary/primary-7.csv", "meqzp07",7,"primary","Primary Quiz Seventh","For Primary Textbook 7","qi-primary-7.csv"),
//            QuizFileInfo("${srcFolder}/primary/primary-8.csv", "meqzp08",8,"primary","Primary Quiz Eighth" ,"For Primary Textbook 8","qi-primary-8.csv"),
//
//            QuizFileInfo("${srcFolder}/junior/junior-01.csv", "meqzj01",1, "junior","Junior Quiz First"  ,"For Junior Textbook 01","qi-junior-01.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-02.csv", "meqzj02",2, "junior","Junior Quiz Second" ,"For Junior Textbook 02","qi-junior-02.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-03.csv", "meqzj03",3, "junior","Junior Quiz Third"  ,"For Junior Textbook 03","qi-junior-03.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-04.csv", "meqzj04",4, "junior","Junior Quiz Fourth" ,"For Junior Textbook 04","qi-junior-04.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-05.csv", "meqzj05",5, "junior","Junior Quiz Fifth"  ,"For Junior Textbook 05","qi-junior-05.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-06.csv", "meqzj06",6, "junior","Junior Quiz Sixth"  ,"For Junior Textbook 06","qi-junior-06.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-07.csv", "meqzj07",7, "junior","Junior Quiz Seventh","For Junior Textbook 07","qi-junior-07.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-08.csv", "meqzj08",8, "junior","Junior Quiz Eighth" ,"For Junior Textbook 08","qi-junior-08.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-09.csv", "meqzj09",9, "junior","Junior Quiz Eighth" ,"For Junior Textbook 09","qi-junior-09.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-10.csv", "meqzj10",10,"junior","Junior Quiz Eighth" ,"For Junior Textbook 10","qi-junior-10.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-11.csv", "meqzj11",11,"junior","Junior Quiz First"  ,"For Junior Textbook 11","qi-junior-11.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-12.csv", "meqzj12",12,"junior","Junior Quiz Second" ,"For Junior Textbook 12","qi-junior-12.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-13.csv", "meqzj13",13,"junior","Junior Quiz Third"  ,"For Junior Textbook 13","qi-junior-13.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-14.csv", "meqzj14",14,"junior","Junior Quiz Fourth" ,"For Junior Textbook 14","qi-junior-14.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-15.csv", "meqzj15",15,"junior","Junior Quiz Fifth"  ,"For Junior Textbook 15","qi-junior-15.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-16.csv", "meqzj16",16,"junior","Junior Quiz Sixth"  ,"For Junior Textbook 16","qi-junior-16.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-17.csv", "meqzj17",17,"junior","Junior Quiz Seventh","For Junior Textbook 17","qi-junior-17.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-18.csv", "meqzj18",18,"junior","Junior Quiz Eighth" ,"For Junior Textbook 18","qi-junior-18.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-19.csv", "meqzj19",19,"junior","Junior Quiz Eighth" ,"For Junior Textbook 19","qi-junior-19.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-20.csv", "meqzj20",20,"junior","Junior Quiz Eighth" ,"For Junior Textbook 20","qi-junior-20.csv"),
//            QuizFileInfo("${srcFolder}/junior/junior-21.csv", "meqzj21",21,"junior","Junior Quiz Eighth" ,"For Junior Textbook 21","qi-junior-21.csv"),
//
//            QuizFileInfo("${srcFolder}/senior/senior-01.csv", "meqzs01",1, "senior","Senior Quiz First"  ,"For Senior Textbook 01","qi-senior-01.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-02.csv", "meqzs02",2, "senior","Senior Quiz Second" ,"For Senior Textbook 02","qi-senior-02.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-03.csv", "meqzs03",3, "senior","Senior Quiz Third"  ,"For Senior Textbook 03","qi-senior-03.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-04.csv", "meqzs04",4, "senior","Senior Quiz Fourth" ,"For Senior Textbook 04","qi-senior-04.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-05.csv", "meqzs05",5, "senior","Senior Quiz Fifth"  ,"For Senior Textbook 05","qi-senior-05.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-06.csv", "meqzs06",6, "senior","Senior Quiz Sixth"  ,"For Senior Textbook 06","qi-senior-06.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-07.csv", "meqzs07",7, "senior","Senior Quiz Seventh","For Senior Textbook 07","qi-senior-07.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-08.csv", "meqzs08",8, "senior","Senior Quiz Eighth" ,"For Senior Textbook 08","qi-senior-08.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-09.csv", "meqzs09",9, "senior","Senior Quiz Eighth" ,"For Senior Textbook 09","qi-senior-09.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-10.csv", "meqzs10",10,"senior","Senior Quiz Eighth" ,"For Senior Textbook 10","qi-senior-10.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-11.csv", "meqzs11",11,"senior","Senior Quiz First"  ,"For Senior Textbook 11","qi-senior-11.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-12.csv", "meqzs12",12,"senior","Senior Quiz Second" ,"For Senior Textbook 12","qi-senior-12.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-13.csv", "meqzs13",13,"senior","Senior Quiz Third"  ,"For Senior Textbook 13","qi-senior-13.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-14.csv", "meqzs14",14,"senior","Senior Quiz Fourth" ,"For Senior Textbook 14","qi-senior-14.csv"),
            QuizFileInfo("${srcFolder}/senior/senior-15.csv", "meqzs15",15,"senior","Senior Quiz Fifth"  ,"For Senior Textbook 15","qi-senior-15.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-16.csv", "meqzs16",16,"senior","Senior Quiz Sixth"  ,"For Senior Textbook 16","qi-senior-16.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-17.csv", "meqzs17",17,"senior","Senior Quiz Seventh","For Senior Textbook 17","qi-senior-17.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-18.csv", "meqzs18",18,"senior","Senior Quiz Eighth" ,"For Senior Textbook 18","qi-senior-18.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-19.csv", "meqzs19",19,"senior","Senior Quiz Eighth" ,"For Senior Textbook 19","qi-senior-19.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-20.csv", "meqzs20",20,"senior","Senior Quiz Eighth" ,"For Senior Textbook 20","qi-senior-20.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-21.csv", "meqzs21",21,"senior","Senior Quiz Eighth" ,"For Senior Textbook 21","qi-senior-21.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-22.csv", "meqzs22",22,"senior","Senior Quiz Second" ,"For Senior Textbook 22","qi-senior-22.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-23.csv", "meqzs23",23,"senior","Senior Quiz Third"  ,"For Senior Textbook 23","qi-senior-23.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-24.csv", "meqzs24",24,"senior","Senior Quiz Fourth" ,"For Senior Textbook 24","qi-senior-24.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-25.csv", "meqzs25",25,"senior","Senior Quiz Fifth"  ,"For Senior Textbook 25","qi-senior-25.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-26.csv", "meqzs26",26,"senior","Senior Quiz Sixth"  ,"For Senior Textbook 26","qi-senior-26.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-27.csv", "meqzs27",27,"senior","Senior Quiz Seventh","For Senior Textbook 27","qi-senior-27.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-28.csv", "meqzs28",28,"senior","Senior Quiz Eighth" ,"For Senior Textbook 28","qi-senior-28.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-29.csv", "meqzs29",29,"senior","Senior Quiz Eighth" ,"For Senior Textbook 29","qi-senior-29.csv"),
//            QuizFileInfo("${srcFolder}/senior/senior-30.csv", "meqzs30",30,"senior","Senior Quiz Eighth" ,"For Senior Textbook 30","qi-senior-30.csv"),

        ).stream().forEach {
            parseWordCSV2EnglishWords(it)
        }
    }

    private fun parseWordCSV2EnglishWords(qif: QuizFileInfo) {
        Files.newBufferedReader(Paths.get(qif.srcfile)).use { reader ->
            val strategy = ColumnPositionMappingStrategy<EnglishWord>()
            strategy.type = EnglishWord::class.java

            strategy.setColumnMapping("seq", "word", "symbol", "tense", "paraphrase")
            val csvToBean: CsvToBean<EnglishWord> = CsvToBeanBuilder<EnglishWord>(reader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
            val wordIterator: Iterator<EnglishWord> = csvToBean.iterator()
            val words = mutableListOf<EnglishWord>()
            while (wordIterator.hasNext()) {
                val word: EnglishWord = wordIterator.next()
                println("seq : " + word.seq)
                println("word : " + word.word)
                println("symbol : " + word.symbol)
                println("tense : " + word.tense)
                println("paraphrase : " + word.paraphrase)
                println("---------------------------")
                words.add(word)
            }

            generateQuizItemsFromWords(words, qif)
        }
    }

    private fun generateQuizItemsFromWords(words: List<EnglishWord>, qif: QuizFileInfo) {


        Files.newBufferedWriter(Paths.get(destFolder + "/" + qif.qifile)).use { writer ->

            val strategy = ColumnPositionMappingStrategy<QuizItemLine>()
            strategy.type = QuizItemLine::class.java
            strategy.setColumnMapping("id","type","mchoice","ans","title","desc","opa","opb","opc","opd","ope","opf","opg","oph")

            writer.write("id,type,mchoice,ans,title,desc,opa,opb,opc,opd,ope,opf,opg,oph\n")

            val beanToCsv: StatefulBeanToCsv<QuizItemLine> = StatefulBeanToCsvBuilder<QuizItemLine>(writer)
                .withMappingStrategy(strategy)
                .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)

                .build()

            val qiids = mutableListOf<String>()

            words.stream().map {
                generateQuizItemLineFromWord(it, words, QI.EN, qif)
            }.filter {
                it.id.isNotBlank()
            }.toArray().forEachIndexed { index, value ->
                    val qil = value as QuizItemLine
                    qil.id = qif.id.plus("%03d".format(index))
                    beanToCsv.write(qil)
                    println()
                    println("generated qiline=$qil")
                    println()

                    qiids.add(qil.id)
                }

            words.stream().map {
                generateQuizItemLineFromWord(it, words, QI.ZH, qif)
            }.filter {
                it.id.isNotBlank()
            }.toArray().forEachIndexed { index, value ->
                    val qil = value as QuizItemLine
                    qil.id = qif.id.plus("%03d".format(words.size+index))
                    beanToCsv.write(qil)
                    println()
                    println("generated qiline=$qil")
                    println()

                    qiids.add(qil.id)
                }
            val commaSeperatedString = qiids.joinToString (separator = ",") { it }

            println("===========quiz item ids==========")
            print(commaSeperatedString)
            println()
            println("===========quiz item ids==========")


        }

    }

    private fun generateQuizItemLineFromWord(
        word: EnglishWord,
        words: List<EnglishWord>,
        en: QI,
        qif: QuizFileInfo
    ): QuizItemLine {

        println("word=$word")

        val qil = QuizItemLine(
            id = word.seq!!,
            type = qif.type,
            mchoice = "N",
            ans = listOf("A", "B", "C", "D").shuffled()[0],
            title = if (QI.EN == en) word.word!! else word.paraphrase!!,
            desc = (word.symbol ?: "Magic Ear 8 Times").plus(" ").plus(word.tense ?: ""),
            opa = if (QI.EN == en) word.paraphrase!! else word.word!!,
            opb = if (QI.EN == en) word.paraphrase!! else word.word!!,
            opc = if (QI.EN == en) word.paraphrase!! else word.word!!,
            opd = if (QI.EN == en) word.paraphrase!! else word.word!!,
        )

        val usedWords = mutableListOf<EnglishWord>(word)
        if ("A" != qil.ans) {
            val randomWord = words.stream().filter { !usedWords.contains(it) }.toList().shuffled()[0]
            println("radomWord=$randomWord")
            qil.opa = if (QI.EN == en) randomWord.paraphrase!! else randomWord.word!!
            usedWords.add(randomWord)
        }
        if ("B" != qil.ans) {
            val randomWord = words.stream().filter { !usedWords.contains(it) }.toList().shuffled()[0]
            println("radomWord=$randomWord")
            qil.opb = if (QI.EN == en) randomWord.paraphrase!! else randomWord.word!!
            usedWords.add(randomWord)
        }
        if ("C" != qil.ans) {
            val randomWord = words.stream().filter { !usedWords.contains(it) }.toList().shuffled()[0]
            println("radomWord=$randomWord")
            qil.opc = if (QI.EN == en) randomWord.paraphrase!! else randomWord.word!!
            usedWords.add(randomWord)
        }
        if ("D" != qil.ans) {
            val randomWord = words.stream().filter { !usedWords.contains(it) }.toList().shuffled()[0]
            println("radomWord=$randomWord")
            qil.opd = if (QI.EN == en) randomWord.paraphrase!! else randomWord.word!!
            usedWords.add(randomWord)
        }

        return qil
    }

    enum class QI {
        EN,
        ZH
    }

    class QuizFileInfo(
        val srcfile: String,
        val id: String,
        val seq: Int,
        val type: String,
        val name: String,
        val desc: String,
        val qifile: String,
    )
}