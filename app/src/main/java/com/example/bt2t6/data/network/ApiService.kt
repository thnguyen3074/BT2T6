package com.example.bt2t6.data.network

import com.example.bt2t6.data.model.ApiResponse
import com.example.bt2t6.data.model.Task
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {
    @GET("researchUTH/tasks")
    suspend fun getTasks(): ApiResponse<List<Task>>

    @POST("researchUTH/tasks")
    suspend fun addTask(@Body task: Task): ApiResponse<Task>
}