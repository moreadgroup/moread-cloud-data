package com.moreadgroup.data.magicear8times

import com.apollographql.apollo.ApolloClient
import com.moreadgroup.domaintask.graphql.client.queries.FIND_EWORDQuery
import com.moreadgroup.graphql.client.util.ApolloClientUtils
import okhttp3.OkHttpClient

fun main(args: Array<String>) {
    println("Hello, World")

    val client = ApolloClient.builder()
        .serverUrl("http://192.168.3.174:18060/domain-task/graphql") //                .addCustomTypeAdapter(CustomType.DATE, new DateGraphQLAdapter())
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder().addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb25hbkBza3lzdGFydHJhZGUuY29tIiwiYXVkIjoiaHR0cDovL3N6d2VicWEwMS5za3lzdGFydHJhZGUuY29tOjk0ODAvc3N0LWFkbWluLW0vYS9sb2dpbiIsImlzcyI6InNzdCIsImV4cCI6MTY0Nzk0ODg4MCwiaWF0IjoxNjE2NDEyODgwLCJ1c2VySWQiOiJjb25hbkBzc3QuY29tIn0.V5lcy7ez4EYT0f9Qc8ZrFxpMmfinAg6qITRo0bv_kJg")
                            .build()
                    )
                }
                .build())
        .build()


    val actualMonoData =  ApolloClientUtils.toMono(client.query(FIND_EWORDQuery("hood"))).block()?.data

    println(actualMonoData)
}

