package com.moreadgroup.data.english

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.junit.Test
import java.io.File


/**
 * @Author conan8chan@yahoo.com
 * @Date 1/6/23T10:05 AM-Friday
 */
class YasiJiaqiangbanYuminghongWordsTest {


    private val ROOT_FOLDER = "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/"

    private val srcFolderOfYasiYuminghong = ROOT_FOLDER + "english/"
    private val srcFolderOfMagicear8times = ROOT_FOLDER + "listens/magic-ear-8-times/"
    private val destFolder = ROOT_FOLDER + "chinese/"

    @Test
    fun testCheckYasiJiaqiangbanVsMagicear8timesAllThenOK() {
        val mapper = ObjectMapper(YAMLFactory()).findAndRegisterModules()
        val yasi: YasiYuminghong = mapper.readValue(
            File(srcFolderOfYasiYuminghong+ "YasiJiaqiangbanYuminghongWords.yaml"),
            YasiYuminghong::class.java
        )

        println("yasi=${yasi.words.size}")

    }

    @Test
    fun testSampleOrderParseThenOK() {
        val mapper = ObjectMapper(YAMLFactory()).findAndRegisterModules()
        val order: Order = mapper.readValue(
            File("src/main/resources/orderInput.yaml"),
            Order::class.java
        )

        println("orderNo=${order.orderNo}")

    }
}