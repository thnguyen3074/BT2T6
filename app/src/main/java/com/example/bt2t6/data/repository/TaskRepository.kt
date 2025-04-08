package com.example.bt2t6.data.repository

import com.example.bt2t6.data.model.Task
import com.example.bt2t6.data.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository {
    suspend fun getTasks(): List<Task> {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.apiService.getTasks()
            if (response.isSuccess && response.data != null) {
                response.data
            } else {
                emptyList()
            }
        }
    }

    suspend fun addTask(task: Task): Result<Task> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.apiService.addTask(task)
                if (response.isSuccess && response.data != null) {
                    Result.success(response.data)
                } else {
                    Result.failure(Exception(response.message))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}