package com.example.bt2t6.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T?
)

data class Task(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("status") val status: String,
    @SerializedName("priority") val priority: String,
    @SerializedName("category") val category: String,
    @SerializedName("dueDate") val dueDate: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("subtasks") val subtasks: List<Subtask> = emptyList(),
    @SerializedName("attachments") val attachments: List<Attachment> = emptyList(),
    @SerializedName("reminders") val reminders: List<Reminder> = emptyList()
)

data class Subtask(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("isCompleted") val isCompleted: Boolean
)

data class Attachment(
    @SerializedName("id") val id: Int,
    @SerializedName("fileName") val fileName: String,
    @SerializedName("fileUrl") val fileUrl: String
)

data class Reminder(
    @SerializedName("id") val id: Int,
    @SerializedName("time") val time: String,
    @SerializedName("type") val type: String
)