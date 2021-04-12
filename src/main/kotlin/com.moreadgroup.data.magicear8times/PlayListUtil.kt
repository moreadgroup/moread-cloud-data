package com.moreadgroup.writeside.domaintask.util

/**
 * @Author conan8chan@yahoo.com
 * @Date 3/11/21T10:34 AM-Thursday
 */


import org.apache.commons.io.FileUtils
import org.apache.commons.io.LineIterator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.File

@Component
class PlayListUtil {
    val logger: Logger = LoggerFactory.getLogger(PlayListUtil::class.java)
    fun loadPlayList(
        csvfile: String? = "static/playlist/playlist.csv",
        start: Int = 0,
        end: Int = Integer.MAX_VALUE
    ) {

        val it: LineIterator = FileUtils.lineIterator(File(ClassLoader.getSystemResource(csvfile).file), "UTF-8")
        try {
            // skip the first header line from the text file
            var line = it.nextLine()
            var lineNo = 0
            println("${lineNo}:${line}")
            while (it.hasNext()) {

                line = it.nextLine()
                lineNo++
                if (lineNo < start) {
                    continue
                }
                // do something with line
                logger.info("${lineNo}:${line}")

//                val listen  = parseLineToListen(line)

//            println(word)

//                val s =  listenRepository.save(listen)

                if (lineNo > end)
                    break
            }
        } finally {
            LineIterator.closeQuietly(it)
        }

    }

//    fun parseLineToListen(line: String):String  {
//        val parser = CSVParser()
//        val fields: Array<String> = parser.parseLine(line)
//
//        val type: String = fields[0].trim()
//        val seq: String = fields[1].trim()
//        val uri: String = fields[2]
//        val name: String = fields[3]
//
//        val bookId = ID.BookID.create("$type-$seq");
//
//        return ListenEty(
//            ListenIdVersion( bookId, 0), type, seq.toInt(), uri, name
//        )
//    }
}