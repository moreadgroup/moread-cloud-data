package com.moreadgroup.data.magicear8times

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.spi.json.JacksonJsonProvider
import jodd.io.FileUtil
import net.minidev.json.JSONObject
import org.apache.commons.io.FileUtils
import org.apache.commons.text.WordUtils
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.charset.StandardCharsets


/**
 * @Author conan8chan@yahoo.com
 * @Date 3/29/21T9:57 AM-Monday
 */
class MagicEar8TimesTest {

//    val ROOT_FOLDER =         "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/"
  val ROOT_FOLDER = "/Users/conanchen/github/moreadgroup/moread-cloud-data/src/main/resources/"

    @Test
    fun testParseFromAliaiJson2VttThenOK() {

        val srcFolder = "static/playlist/magic-ear-8-times"
        val destFolder = "static/playlist/magic-ear-8-times"

        listOf(
            Pair("${srcFolder}/introduction/introduction-1.json", false),
            Pair("${srcFolder}/introduction/introduction-2.json", false),
            Pair("${srcFolder}/introduction/introduction-3.json", false),

            Pair("${srcFolder}/primary/primary-1.json", false),
            Pair("${srcFolder}/primary/primary-2.json", true),
            Pair("${srcFolder}/primary/primary-3.json", false),
            Pair("${srcFolder}/primary/primary-4.json", false),
            Pair("${srcFolder}/primary/primary-5.json", false),
            Pair("${srcFolder}/primary/primary-6.json", false),
            Pair("${srcFolder}/primary/primary-7.json", false),
            Pair("${srcFolder}/primary/primary-8.json", false),

            Pair("${srcFolder}/junior/junior-01.json", false),
            Pair("${srcFolder}/junior/junior-02.json", false),
            Pair("${srcFolder}/junior/junior-03.json", false),
            Pair("${srcFolder}/junior/junior-04.json", false),
            Pair("${srcFolder}/junior/junior-05.json", false),
            Pair("${srcFolder}/junior/junior-06.json", false),
            Pair("${srcFolder}/junior/junior-07.json", false),
            Pair("${srcFolder}/junior/junior-08.json", false),
            Pair("${srcFolder}/junior/junior-09.json", false),
            Pair("${srcFolder}/junior/junior-10.json", false),
            Pair("${srcFolder}/junior/junior-11.json", false),
            Pair("${srcFolder}/junior/junior-12.json", false),
            Pair("${srcFolder}/junior/junior-13.json", false),
            Pair("${srcFolder}/junior/junior-14.json", false),
            Pair("${srcFolder}/junior/junior-15.json", false),
            Pair("${srcFolder}/junior/junior-16.json", false),
            Pair("${srcFolder}/junior/junior-17.json", false),
            Pair("${srcFolder}/junior/junior-18.json", false),
            Pair("${srcFolder}/junior/junior-19.json", false),
            Pair("${srcFolder}/junior/junior-20.json", false),
            Pair("${srcFolder}/junior/junior-21.json", false),

            Pair("${srcFolder}/senior/senior-01.json", false),
            Pair("${srcFolder}/senior/senior-02.json", false),
            Pair("${srcFolder}/senior/senior-03.json", false),
            Pair("${srcFolder}/senior/senior-04.json", false),
            Pair("${srcFolder}/senior/senior-05.json", false),
            Pair("${srcFolder}/senior/senior-06.json", false),
            Pair("${srcFolder}/senior/senior-07.json", false),
            Pair("${srcFolder}/senior/senior-08.json", false),
            Pair("${srcFolder}/senior/senior-09.json", false),
            Pair("${srcFolder}/senior/senior-10.json", false),
            Pair("${srcFolder}/senior/senior-11.json", false),
            Pair("${srcFolder}/senior/senior-12.json", false),
            Pair("${srcFolder}/senior/senior-13.json", false),
            Pair("${srcFolder}/senior/senior-14.json", false),
            Pair("${srcFolder}/senior/senior-15.json", false),
            Pair("${srcFolder}/senior/senior-16.json", false),
            Pair("${srcFolder}/senior/senior-17.json", false),
            Pair("${srcFolder}/senior/senior-18.json", false),
            Pair("${srcFolder}/senior/senior-19.json", false),
            Pair("${srcFolder}/senior/senior-20.json", false),
            Pair("${srcFolder}/senior/senior-21.json", false),
            Pair("${srcFolder}/senior/senior-22.json", false),
            Pair("${srcFolder}/senior/senior-23.json", false),
            Pair("${srcFolder}/senior/senior-24.json", false),
            Pair("${srcFolder}/senior/senior-25.json", false),
            Pair("${srcFolder}/senior/senior-26.json", false),
            Pair("${srcFolder}/senior/senior-27.json", false),
            Pair("${srcFolder}/senior/senior-28.json", false),
            Pair("${srcFolder}/senior/senior-29.json", false),
            Pair("${srcFolder}/senior/senior-30.json", false),
        )
            .stream()
            .filter {
                it.second
            }
            .forEach {
                parseToVttItems(it.first)
            }


//        parseToVttItems(jsonFiles[0])

    }

    private fun parseToVttItems(jsonFile: String) {


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

    private fun formatHMSFromMs(it: Int):String {
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

}