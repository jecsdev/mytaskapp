package com.jecsdev.mytasklist.ui.navigation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jecsdev.mytasklist.ui.views.TaskScreen
import com.jecsdev.mytasklist.ui.views.AddEditTaskScreen

@Composable
fun NavigationHost(navController: NavHostController) {
    val defaultColor = MaterialTheme.colors.background.toArgb()

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
                defaultValue = defaultColor
            },
                navArgument(name = "taskColor") {
                    type = NavType.IntType
                    defaultValue = defaultColor
                })
        ) {
            val color = it.arguments?.getInt("taskColor") ?: defaultColor
            AddEditTaskScreen(
                navController = navController,
                taskColor = color
            )
        }
    }
}