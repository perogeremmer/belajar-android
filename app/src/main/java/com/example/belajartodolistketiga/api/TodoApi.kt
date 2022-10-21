package com.example.belajartodolistketiga.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TodoApi {
    @GET("/rest/v1/todo?select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String
    ) : Response<List<TodoItem>>

    @POST("/rest/v1/todo")
    suspend fun create(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Body todoData: TodoData
    )
}