package com.example.bt2t6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bt2t6.data.model.Task
import com.example.bt2t6.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val repository = TaskRepository()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _newTaskTitle = MutableStateFlow("")
    val newTaskTitle: StateFlow<String> = _newTaskTitle.asStateFlow()

    private val _newTaskDescription = MutableStateFlow("")
    val newTaskDescription: StateFlow<String> = _newTaskDescription.asStateFlow()

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _tasks.value = repository.getTasks()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateNewTaskTitle(title: String) {
        _newTaskTitle.value = title
    }

    fun updateNewTaskDescription(description: String) {
        _newTaskDescription.value = description
    }

    fun addTask() {
        val title = _newTaskTitle.value.trim()
        val description = _newTaskDescription.value.trim()

        if (title.isEmpty()) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Create a new task with default values
                val newTask = Task(
                    id = 0, // Will be assigned by server
                    title = title,
                    description = description.ifEmpty { null },
                    status = "TODO",
                    priority = "MEDIUM",
                    category = "GENERAL",
                    dueDate = "",
                    createdAt = "",
                    updatedAt = ""
                )

                val currentTasks = _tasks.value.toMutableList()
                currentTasks.add(0, newTask)
                _tasks.value = currentTasks

                val result = repository.addTask(newTask)
                println("Add task result: $result")

                if (result.isSuccess) {
                    val addedTask = result.getOrNull()
                    if (addedTask != null) {
                        val updatedTasks = _tasks.value.toMutableList()
                        val index = updatedTasks.indexOfFirst { it.title == newTask.title && it.description == newTask.description }
                        if (index != -1) {
                            updatedTasks[index] = addedTask
                            _tasks.value = updatedTasks
                        }
                    }
                } else {

                    _error.value = "Task added locally but not synced with server"
                }

                // Clear input fields
                _newTaskTitle.value = ""
                _newTaskDescription.value = ""

            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to add task"
                println("Error adding task: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}