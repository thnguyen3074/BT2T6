package com.example.bt2t6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bt2t6.ui.screens.AddTaskScreen
import com.example.bt2t6.ui.screens.ListScreen
import com.example.bt2t6.viewmodel.TaskViewModel
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val taskViewModel: TaskViewModel = viewModel()

            NavHost(navController = navController, startDestination = "ListScreen") {
                composable("ListScreen") { ListScreen(navController, taskViewModel) }
                composable("AddTaskScreen") { AddTaskScreen(navController, taskViewModel) }
            }
        }
    }
}