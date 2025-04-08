package com.example.bt2t6.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bt2t6.R
import com.example.bt2t6.data.model.Task
import com.example.bt2t6.ui.theme.Blue
import com.example.bt2t6.ui.theme.LightBlue
import com.example.bt2t6.ui.theme.LightYellow
import com.example.bt2t6.ui.theme.Pink
import com.example.bt2t6.viewmodel.TaskViewModel
import com.google.gson.Gson

@Composable
fun ListScreen(navController: NavController, taskViewModel: TaskViewModel = viewModel()) {
    val tasks by taskViewModel.tasks.collectAsState()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Blue,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "List",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue,
                    modifier = Modifier.align(Alignment.Center)
                )

                // Add button in top-right
                IconButton(
                    onClick = { navController.navigate("AddTaskScreen") },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add_icon),
                        contentDescription = "Add Task",
                        modifier = Modifier.size(42.dp)
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("AddTaskScreen") },
                containerColor = Blue,
                shape = CircleShape,
                modifier = Modifier
                    .size(55.dp)
                    .offset(y = 53.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .padding(top = 10.dp, bottom = 40.dp, start = 20.dp, end = 20.dp)
            ) {
                // Main pill-shaped container with shadow
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(30.dp),
                            clip = false
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(30.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { /* Home action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.home_icon),
                                contentDescription = "Home",
                                tint = Blue,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        IconButton(onClick = { /* Calendar action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.calendar_icon),
                                contentDescription = "Calendar",
                                tint = Color.Gray,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(64.dp))

                        IconButton(onClick = { /* Notes action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.notes_icon),
                                contentDescription = "Notes",
                                tint = Color.Gray,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        IconButton(onClick = { /* Settings action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.settings_icon),
                                contentDescription = "Settings",
                                tint = Color.Gray,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                if (tasks.isNotEmpty()) {
                    TaskList(tasks)
                } else {
                    NoTasksView()
                }
            }
        }
    }
}

@Composable
fun TaskList(tasks: List<Task>) {
    val colors = listOf(
        LightBlue,
        Pink,
        LightYellow
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        itemsIndexed(tasks) { index, task ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(
                        color = colors[index % colors.size],
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = task.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )

                    task.description?.let {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = it,
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoTasksView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .width(350.dp)
            .height(255.dp)
            .background(Color(0x4DBBBBBB), shape = RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_tasks),
                contentDescription = "No Data",
                modifier = Modifier
                    .size(112.dp)
                    .padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "No Tasks Yet!",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Stay productive—add something to do",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                ),
            )
        }
    }
}