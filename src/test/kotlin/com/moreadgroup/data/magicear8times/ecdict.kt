package com.moreadgroup.data.magicear8times//package kotlin.com.moreadgroup.data.magicear8times
//
//import com.opencsv.CSVParser
//import org.apache.commons.io.FileUtils
//import org.apache.commons.io.LineIterator
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import java.io.File
//
//
//public fun loadECDICT(ecdictcsvfile: String? = "ecdict.csv", start:Int=0,end:Int=Integer.MAX_VALUE) {
//    val logger: Logger = LoggerFactory.getLogger(MagicEar8TimesTest::class.java)
//
//    val it: LineIterator = FileUtils.lineIterator(File(ClassLoader.getSystemResource(ecdictcsvfile).file), "UTF-8")
//    try {
//        // read the first line from the text file
//        var line = it.nextLine()
//        var lineNo = 0
//        println("${lineNo}:${line}")
//        while (it.hasNext()) {
//
//            line = it.nextLine()
//            lineNo++
//            if(lineNo<start){
//                continue
//            }
//            // do something with line
//            logger.info("${lineNo}:${line}")
//
////            val word: WordEty = parseLineToWord(line)
//            parseLineToWord(line)
////            println(word)
//
////            wordRepository.save(word)
////
//            if (lineNo > end)
//                break
//        }
//    } finally {
//        LineIterator.closeQuietly(it)
//    }
//
//}
//
//fun parseLineToWord(line: String)  {
//    val parser = CSVParser()
//    val fields: Array<String> = parser.parseLine(line)
//
//    val word: String = fields[0]
//    val phonetic: String = fields[1]
//    val definition: String = fields[2]
//    val translation: String = fields[3]
//    val pos: String = fields[4]
//    val collins: Int? = fields[5].toIntOrNull()?.or(0)
//    val oxford: Int? = fields[6].toIntOrNull()?.or(0)
//    val tag: String = fields[7]
//    val bnc: Int? = fields[8].toIntOrNull()?.or(0)
//    val frq: Int? = fields[9].toIntOrNull()?.or(0)
//    val exchange: String = fields[10]
//    val detail: String = fields[11]
//    val audio: String = fields[12]
//
//    val ts = tag.split(" ")
//    val zk = ts.contains("zk")
//    val gk = ts.contains("gk")
//    val cet4 = ts.contains("cet4")
//    val ky = ts.contains("ky")
//
//
//    val regex = Regex("[^A-Za-z]")
//    val sw = regex.replace(word, "").toLowerCase()
//
//
////    return WordEty(
////        WordIdVersion(ID.WordID.create(word), 0), sw,phonetic, definition, translation, pos, collins,
////        oxford, tag, bnc, frq, exchange, detail, audio, zk, gk, cet4, ky
////    )
//}
////
////
////fun main() {
////    val s = "Test@@String#123-"
////
////    val regex = Regex("[^A-Za-z]")
////    val result = regex.replace(s, "").toLowerCase()
////    println(result)
////}