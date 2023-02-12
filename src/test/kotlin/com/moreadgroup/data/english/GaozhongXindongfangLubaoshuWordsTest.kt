package com.moreadgroup.data.english

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


/**
 * @Author conan8chan@yahoo.com
 * @Date 1/6/23T10:05 AM-Friday
 */
class GaozhongXindongfangLubaoshuWordsTest {


    private val ROOT_FOLDER = "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/"

    private val srcFolderOfYasiYuminghong = ROOT_FOLDER + "english/"
    private val srcFolderOfMagicear8times = ROOT_FOLDER + "listens/magic-ear-8-times/"
    private val destFolder = ROOT_FOLDER + "english/out"

    @Test
    fun testCheckGaozhongXindongfangLubaoshuWordsVsMagicear8timesAllThenOK() {
        val objectMapper = ObjectMapper(
            YAMLFactory()
                .configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true)
                .configure(YAMLGenerator.Feature.SPLIT_LINES, false)
        ).findAndRegisterModules()
        val yasi: YasiYuminghong = objectMapper.readValue(
            File(srcFolderOfYasiYuminghong + "GaozhongXindongfangLubaoshuWords.yaml"),
            YasiYuminghong::class.java
        )

        println("GaozhongXindongfangLubaoshuWords=${yasi.words.size}")

        println("=============missing primary words==========")
        val missingPrimaryWords =
            foundMissingWordsInYasiYuminghong("小学", srcFolderOfMagicear8times + "primary/primary-all.csv", yasi)
        println("missing primary words total = ${missingPrimaryWords.words.size}")
        objectMapper.writeValue(File(destFolder + "/missing-primary-out.yaml"), missingPrimaryWords);

        println("=============missing junior words==========")
        val missingJuniorWords =
            foundMissingWordsInYasiYuminghong("初中", srcFolderOfMagicear8times + "junior/junior-all.csv", yasi)
        println("missing junior words total = ${missingJuniorWords.words.size}")
        objectMapper.writeValue(File(destFolder + "/missing-junior-out.yaml"), missingJuniorWords);

        println("=============missing senior words==========")
        val missingSeniorWords =
            foundMissingWordsInYasiYuminghong("高中", srcFolderOfMagicear8times + "senior/senior-all.csv", yasi)
        println("missing senior words total = ${missingSeniorWords.words.size}")
        objectMapper.writeValue(File(destFolder + "/missing-senior-out.yaml"), missingSeniorWords);

        // We write the `employee` into `person2.yaml`
        //objectMapper.writeValue(File(destFolder+"/gaozhong-out.yaml"), yasi);

    }

    fun foundMissingWordsInYasiYuminghong(tag: String, file: String, yasi: YasiYuminghong): YasiYuminghong {
        var missingWords: YasiYuminghong = YasiYuminghong()
        //
        Files.newBufferedReader(
            Paths.get(
                file
            )
        ).use { reader ->
            val strategy = ColumnPositionMappingStrategy<EnwordLine>()
            strategy.type = EnwordLine::class.java

            // seq,seqn,word,phonetic,exchange,trans
            strategy.setColumnMapping("seq", "seqn", "word", "phonetic", "exchange", "trans")
            val csvToBean: CsvToBean<EnwordLine> = CsvToBeanBuilder<EnwordLine>(reader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
            val wordLineIterator: Iterator<EnwordLine> = csvToBean.iterator()
            while (wordLineIterator.hasNext()) {
                val wordLine: EnwordLine = wordLineIterator.next()
                if (wordLine?.word != null) {
//                    val idx = yasi.words.binarySearch{String.CASE_INSENSITIVE_ORDER.compare(it.word.trim(), wordLine.word.trim())};
//                   if ( idx < 0){ // not found
//                       println(wordLine.word)
//                   }
                    var found = false;
                    for (word in yasi.words) {
                        if (word.word.equals(wordLine.word, true)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        println(wordLine.word)
                        var exchange = if (wordLine.exchange.equals("")) null else wordLine.exchange
                        var phonetic = if (wordLine.phonetic.equals("")) null else wordLine.phonetic
                        var tags = arrayListOf(tag)
                        missingWords.words.add(
                            EnwordYaml(
                                word = wordLine.word,
                                tags = tags,
                                exchange = exchange,
                                phonetic = phonetic,
                                trans = wordLine.trans
                            )
                        )
                    }
                }
            }
        }

        return missingWords

    }

    @Test
    fun testFixGaozhongXindongfangLubaoshuWordsWithMagicear8timesAllThenOK() {
        val objectMapper = ObjectMapper(
            YAMLFactory()
                .configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true)
                .configure(YAMLGenerator.Feature.SPLIT_LINES, false)
        ).findAndRegisterModules()
        var yasi: YasiYuminghong = objectMapper.readValue(
            File(srcFolderOfYasiYuminghong + "GaozhongXindongfangLubaoshuWords.yaml"),
            YasiYuminghong::class.java
        )

        println("GaozhongXindongfangLubaoshuWords=${yasi.words.size}")

        yasi = fixTagsWithMagicear8times("小学", srcFolderOfMagicear8times + "primary/primary-all.csv", yasi)
        yasi = fixTagsWithMagicear8times("初中", srcFolderOfMagicear8times + "junior/junior-all.csv", yasi)
        yasi = fixTagsWithMagicear8times("高中", srcFolderOfMagicear8times + "senior/senior-all.csv", yasi)

        println("GaozhongXindongfangLubaoshuWords null tags:")
        yasi.words.forEach {
            if (it.tags == null) {
                println("${it.word}:${it.tags}")
                it.tags = arrayListOf("高中")
            }
        }

        println("GaozhongXindongfangLubaoshuWords null phonetic:")
        yasi.words.forEach {
            if (it.phonetic == null) {
                println("${it.word}:${it.phonetic}")
            }
        }

        objectMapper.writeValue(File(destFolder + "/fix-GaozhongXindongfangLubaoshuWords.yaml"), yasi);

    }

    private fun fixTagsWithMagicear8times(tag: String, file: String, yasi: YasiYuminghong): YasiYuminghong {
        //
        Files.newBufferedReader(
            Paths.get(
                file
            )
        ).use { reader ->
            val strategy = ColumnPositionMappingStrategy<EnwordLine>()
            strategy.type = EnwordLine::class.java

            // seq,seqn,word,phonetic,exchange,trans
            strategy.setColumnMapping("seq", "seqn", "word", "phonetic", "exchange", "trans")
            val csvToBean: CsvToBean<EnwordLine> = CsvToBeanBuilder<EnwordLine>(reader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
            val wordLineIterator: Iterator<EnwordLine> = csvToBean.iterator()
            while (wordLineIterator.hasNext()) {
                val wordLine: EnwordLine = wordLineIterator.next()
                if (wordLine?.word != null) {
                    for (word in yasi.words) {
                        if (word.word.equals(wordLine.word, false)) { //found
                            if (word.tags == null) {
                                word.tags = arrayListOf(tag)
                            } else {
                                word.tags = word.tags!!.plus(tag).distinct()
                            }
                            if (word.tags != null) {
                                println(word.word + ":" + word.tags?.distinct())
                            }

                            // fill exchange
                            if (wordLine?.exchange != null && wordLine?.exchange.compareTo("") != 0) {
                                word.exchange = wordLine.exchange
                            }
                            break;
                        }
                    }
                }
            }
        }

        return yasi
    }


}