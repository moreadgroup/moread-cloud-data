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
        val objectMapper = ObjectMapper(YAMLFactory()
            .configure(YAMLGenerator.Feature.MINIMIZE_QUOTES,true)
            .configure(YAMLGenerator.Feature.SPLIT_LINES,false)
        ).findAndRegisterModules()
        val yasi: YasiYuminghong = objectMapper.readValue(
            File(srcFolderOfYasiYuminghong+ "GaozhongXindongfangLubaoshuWords.yaml"),
            YasiYuminghong::class.java
        )

        println("GaozhongXindongfangLubaoshuWords=${yasi.words.size}")
        println("=============missing primary words==========")
         removeWordsFromPutongGaozhongDict(srcFolderOfMagicear8times + "primary/primary-all.csv",yasi)
        println("=============missing junior words==========")
        removeWordsFromPutongGaozhongDict(srcFolderOfMagicear8times + "junior/junior-all.csv",yasi)
        println("=============missing senior words==========")
         removeWordsFromPutongGaozhongDict(srcFolderOfMagicear8times + "senior/senior-all.csv",yasi)

        // We write the `employee` into `person2.yaml`
        //objectMapper.writeValue(File(destFolder+"/gaozhong-out.yaml"), yasi);

    }

    fun removeWordsFromPutongGaozhongDict(file:String, yasi:YasiYuminghong){
        //
        Files.newBufferedReader(
            Paths.get(file
        )).use { reader ->
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
                    for(word in yasi.words){
                        if (word.word.equals(wordLine.word,true)){
                            found=true;
                            break;
                        }
                    }

                    if (!found){
                        println(wordLine.word)
                    }

                }
            }
        }

    }

}