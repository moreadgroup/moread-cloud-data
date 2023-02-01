package com.moreadgroup.data.english

import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @Author conan8chan@yahoo.com
 * @Date 1/3/23T12:04 PM-Tuesday
 */
class PutongGaozhongCheckTest {


    private val ROOT_FOLDER = "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/"

    private val srcFolderOfPutongGaozhong2020 = ROOT_FOLDER + "english/"
    private val srcFolderOfMagicear8times = ROOT_FOLDER + "listens/magic-ear-8-times/"
    private val destFolder = ROOT_FOLDER + "chinese/"

   fun removeWordsFromPutongGaozhongDict(file:String, putongGaozhongDict:HashMap<String,PutongGaozhongWordLine>): HashMap<String,PutongGaozhongWordLine>{
       //
       Files.newBufferedReader(Paths.get(file
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
                   putongGaozhongDict.remove(wordLine.word.trim())
               }
           }
       }

       return putongGaozhongDict
    }

    @Test
    fun testCheckPutongGaozhongVsMagicear8timesAllThenOK() {
        var putongGaozhongWords = HashMap<String,PutongGaozhongWordLine>();

        // PutongGaozhong2020Dict.csv
        Files.newBufferedReader(Paths.get(
            srcFolderOfPutongGaozhong2020 + "普通高中英语课程标准words.csv",
        )).use { reader ->
            val strategy = ColumnPositionMappingStrategy<PutongGaozhongWordLine>()
            strategy.type = PutongGaozhongWordLine::class.java

            // word,exchange,mark
            strategy.setColumnMapping("word", "exchange", "mark")
            val csvToBean: CsvToBean<PutongGaozhongWordLine> = CsvToBeanBuilder<PutongGaozhongWordLine>(reader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
            val wordLineIterator: Iterator<PutongGaozhongWordLine> = csvToBean.iterator()
            while (wordLineIterator.hasNext()) {
                val wordLine: PutongGaozhongWordLine = wordLineIterator.next()
                if (wordLine?.word != null) {
                    putongGaozhongWords.put(wordLine.word.trim(),wordLine)
                }
            }
        }

        putongGaozhongWords = removeWordsFromPutongGaozhongDict(srcFolderOfMagicear8times + "primary/primary-all.csv",putongGaozhongWords)
        putongGaozhongWords = removeWordsFromPutongGaozhongDict(srcFolderOfMagicear8times + "junior/junior-all.csv",putongGaozhongWords)
        putongGaozhongWords = removeWordsFromPutongGaozhongDict(srcFolderOfMagicear8times + "senior/senior-all.csv",putongGaozhongWords)

        println("=============missing words==========")
        val wordIterator:Iterator<String> = putongGaozhongWords.keys.iterator();
        while (wordIterator.hasNext()) {
            val word: String = wordIterator.next()
            val wordLine:PutongGaozhongWordLine? = putongGaozhongWords.get(word)
            println("${word}:${wordLine?.mark}")
        }
    }
}