package com.jecsdev.mytasklist.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jecsdev.mytasklist.ui.views.AddEditTaskScreen
import com.jecsdev.mytasklist.ui.components.TaskScreen
import com.jecsdev.mytasklist.ui.navigation.Screen
import com.jecsdev.mytasklist.ui.theme.MyTaskListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTaskListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController, startDestination = Screen.TaskScreen.route
                    ) {
                        composable(route = Screen.TaskScreen.route) {
                            TaskScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditTaskScreen.route + "?taskId={taskId}&taskColor={taskColor}",
                            arguments = listOf(navArgument(name = "taskId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                                navArgument(name = "taskColor") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                })
                        ) {
                            val color = it.arguments?.getInt("taskColor") ?: -1
                            AddEditTaskScreen(
                                navController = navController,
                                taskColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}
