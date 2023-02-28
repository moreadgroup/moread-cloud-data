package com.moreadgroup.data.chinese.xushici

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.moreadgroup.data.english.YasiYuminghong
import org.junit.Test
import java.io.File

/**
 * @Author conan8chan@yahoo.com
 * @Date 2/28/23T4:36 PM-Tuesday
 */
class XushiWordTest {


    private val ROOT_FOLDER = "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/"

    private val srcFolder = ROOT_FOLDER + "chinese/xushici/"
    private val destFolder = ROOT_FOLDER + "chinese/xushici/out/"

    @Test
    fun testCheckXushiWordAllThenOK() {
        val objectMapper = ObjectMapper(
            YAMLFactory()
                .configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true)
                .configure(YAMLGenerator.Feature.SPLIT_LINES, false)
        ).findAndRegisterModules()

       var allXushiWordQuizzes = XushiWordQuizzes()

        listOf<String>(
            "高考高频虚词001-而er.yaml",
            "高考高频虚词002-何he.yaml",
            "高考高频虚词003-乎hu.yaml",
            "高考高频虚词004-乃nai.yaml",
            "高考高频虚词005-其qi.yaml",
            "高考高频虚词006-且qie.yaml",
            "高考高频虚词007-若ruo4.yaml",
            "高考高频虚词008-所suo3.yaml",
            "高考高频虚词009-为wei.yaml",
            "高考高频虚词010-焉yan1.yaml",
            "高考高频虚词011-也ye3.yaml",
            "高考高频虚词012-以yi3.yaml",
            "高考高频虚词013-因yin1.yaml",
            "高考高频虚词014-于yu2.yaml",
            "高考高频虚词015-与yu.yaml",
            "高考高频虚词016-则ze2.yaml",
            "高考高频虚词017-者zhe3.yaml",
            "高考高频虚词018-之zhi1.yaml",

        ).forEach { fileName ->

            val xushiWords: GushiwenXushiWords = objectMapper.readValue(
                File(srcFolder + fileName),
                GushiwenXushiWords::class.java
            )

            println("${fileName} Words=${xushiWords.words.size}")
            val xushiWordQuizzes = convertFrom(xushiWords);
            // We write the `employee` into `person2.yaml`
            objectMapper.writeValue(File(destFolder +"Quiz"+fileName ), xushiWordQuizzes);

            allXushiWordQuizzes.quizzes.addAll(xushiWordQuizzes.quizzes)
        }
        allXushiWordQuizzes.quizzes.shuffle()
        objectMapper.writeValue(File(destFolder +"Quiz高考高频虚词000-全部.yaml" ), allXushiWordQuizzes);

    }

    private fun convertFrom(xushiWords: GushiwenXushiWords): XushiWordQuizzes {
        var result = XushiWordQuizzes()
        xushiWords.words.forEach { word ->
            word.samples?.forEach { trans ->
                trans.sentences?.forEach { sentence ->
                    result.quizzes.add(
                        XushiWordQuiz(
                            word.word,
                            word.type,
                            word.phonetic,
                            trans.trans,
                            sentence.ancient,
                            sentence.url,
                            sentence.vernacular
                        )
                    )
                }
            }
        }
        return result
    }

}