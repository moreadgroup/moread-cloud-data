//package com.moreadgroup.data.magicear8times
//
///**
// * @Author conan8chan@yahoo.com
// * @Date 3/11/21T10:34 AM-Thursday
// */
//
//
//import com.opencsv.CSVParser
//import org.apache.commons.io.FileUtils
//import org.apache.commons.io.LineIterator
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import org.springframework.stereotype.Component
//import java.io.File
//import okhttp3.OkHttpClient
//
//import com.apollographql.apollo.ApolloClient
//import okhttp3.Response
//
//
//@Component
//class QuizUtil {
//    val logger: Logger = LoggerFactory.getLogger(QuizUtil::class.java)
//    fun loadQuizItemList(
//        csvfile: String? = "textbook/quizitem.csv",
//        start: Int = 0,
//        end: Int = Integer.MAX_VALUE
//    ) {
//
//
//        val it: LineIterator = FileUtils.lineIterator(File(ClassLoader.getSystemResource(csvfile).file), "UTF-8")
//        try {
//            // skip the first header line from the text file
//            var line = it.nextLine()
//            var lineNo = 0
//            println("${lineNo}:${line}")
//            while (it.hasNext()) {
//
//                line = it.nextLine()
//                if (line.isBlank()) continue
//                lineNo++
//                if (lineNo < start) {
//                    continue
//                }
//                // do something with line
//                println("${lineNo}:${line}")
//
//                val quizItem  = parseLineToQuizItem(line)
//
//                println(quizItem.toString())
//                println()
//
////                val s = quizItemRepository.save(quizItem)
//
//                if (lineNo > end)
//                    break
//            }
//        } finally {
//            LineIterator.closeQuietly(it)
//        }
//
//    }
//
//    fun ssss(){
//        val a = QuizItemCreateMutation()
//        val client = ApolloClient.builder()
//            .serverUrl("https://example.com/graphql")
//            .addCustomTypeAdapter<Any>(CustomType.DATE, DateGraphQLAdapter())
//            .okHttpClient(
//                OkHttpClient.Builder()
//                    .addInterceptor(object : Interceptor() {
//                        @Throws(IOException::class)
//                        fun intercept(chain: Interceptor.Chain): Response? {
//                            return chain.proceed(
//                                chain.request().newBuilder()
//                                    .addHeader("Authorization", "Basic cnllYnJ5ZTpiVarArsVzMTIz").build()
//                            )
//                        }
//                    })
//                    .build()
//            )
//            .build()
//    }
//
//    fun parseLineToQuizItem(line: String): QuizItemEty {
////        id,type,mchoice,ans,title,desc,opa,opb,opc,opd,ope,opf,opg,oph,,
//
//        val parser = CSVParser()
//        val fields: Array<String> = parser.parseLine(line)
//
//        val id: String = fields[0].trim()
//        val type: String = fields[1].trim()
//        val mchoice: String = fields[2].trim()
//        val ans: String = fields[3].trim()
//        val title: String = fields[4].trim()
//        val desc: String = fields[5].trim()
//
//
//        val opa: String = fields[6].trim()
//        val opb: String = fields[7].trim()
//        val opc: String = fields[8].trim()
//        val opd: String = fields[9].trim()
//        val ope: String = fields[10].trim()
//        val opf: String = fields[11].trim()
//        val opg: String = fields[12].trim()
//        val oph: String = fields[13].trim()
//
//        val itemId = ID.ItemID.create(id);
//        val multiChoice = "Y" == mchoice
//
//        var options: List<ItemOption> = mutableListOf()
//        if (opa.isNotBlank())
//            options = options.plus(ItemOption(OptionA2Z.A, opa))
//        if (opb.isNotBlank())
//            options = options.plus(ItemOption(OptionA2Z.B, opb))
//        if (opc.isNotBlank())
//            options = options.plus(ItemOption(OptionA2Z.C, opc))
//        if (opd.isNotBlank())
//            options = options.plus(ItemOption(OptionA2Z.D, opd))
//        if (ope.isNotBlank())
//            options = options.plus(ItemOption(OptionA2Z.E, ope))
//        if (opf.isNotBlank())
//            options = options.plus(ItemOption(OptionA2Z.F, opf))
//        if (opg.isNotBlank())
//            options = options.plus(ItemOption(OptionA2Z.G, opg))
//        if (oph.isNotBlank())
//            options = options.plus(ItemOption(OptionA2Z.H, oph))
//
//        val answers = ans.split(",").filter { it.isNotBlank() }.map { it.trim() }.map { OptionA2Z.valueOf(it) }
//
//        val qi = QuizItemEty(
//            idv = QuizItemIdVersion(itemId, 0),
//            type = type,
//            multiChoice = multiChoice,
//            title = title,
//            description = desc
//        )
//        qi.options = options
//        qi.answer = answers
//        return qi
//
//    }
//
//
//    fun loadQuizList(
//        quizRepository: QuizRepository,
//        csvfile: String? = "textbook/quiz.csv",
//        start: Int = 0,
//        end: Int = Integer.MAX_VALUE
//    ) {
//
//
//        val it: LineIterator = FileUtils.lineIterator(File(ClassLoader.getSystemResource(csvfile).file), "UTF-8")
//        try {
//            // skip the first header line from the text file
//            var line = it.nextLine()
//            var lineNo = 0
//            println("${lineNo}:${line}")
//            while (it.hasNext()) {
//
//                line = it.nextLine()
//                if (line.isBlank()) continue
//
//                lineNo++
//                if (lineNo < start) {
//                    continue
//                }
//                // do something with line
//                println("${lineNo}:${line}")
//
//                val quiz: QuizEty = parseLineToQuiz(line)
//
//                println(quiz.toString())
//                println()
//
//                val s = quizRepository.save(quiz)
//
//                if (lineNo > end)
//                    break
//            }
//        } finally {
//            LineIterator.closeQuietly(it)
//        }
//
//    }
//
//    fun parseLineToQuiz(line: String): QuizEty {
//
//        //id,seq,type,name,desc,items
//
//        val parser = CSVParser()
//        val fields: Array<String> = parser.parseLine(line)
//
//        val id: String = fields[0].trim()
//        val seq: String = fields[1].trim()
//        val type: String = fields[2].trim()
//        val name: String = fields[3].trim()
//        val desc: String = fields[4].trim()
//        val items: String = fields[5].trim()
//
//        val quiz = QuizEty(
//            QuizIdVersion(id = ID.BookID.create(id), version = 0),
//            seq = seq.toInt(),
//            type = type,
//            title = name,
//            description = desc,
//        )
//        val quizItems = items.split(",").map { it.trim() }.filter { it.isNotBlank() }.map { ID.ItemID.create(it) }
//        quiz.items = quizItems
//
//        return quiz
//    }
//}