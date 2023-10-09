package com.jecsdev.mytasklist.ui.views
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jecsdev.mytasklist.feature_task.domain.model.Task
import com.jecsdev.mytasklist.ui.components.TransParentTextField
import com.jecsdev.mytasklist.ui.event.AddEditTaskEvent
import com.jecsdev.mytasklist.ui.event.UiEvent
import com.jecsdev.mytasklist.ui.viewModels.AddEditTaskViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditTaskScreen(
    navController: NavController,
    taskColor: Int,
    viewModel: AddEditTaskViewModel = hiltViewModel()
){
    val titleState = viewModel.taskTitle.value
    val contentState = viewModel.taskContent.value

    val scaffoldState = rememberScaffoldState()

    val taskBackGroundAnimation = remember {
        Animatable(
            Color(if(taskColor != 1) taskColor else viewModel.taskColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowSnackBarr -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.SaveTask-> {
                    navController.navigateUp()
                }
            }
        }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditTaskEvent.SaveTask)
            }, backgroundColor = MaterialTheme.colors.primary) {
                Icon(imageVector = Icons.Default.Save,
                    contentDescription = "Save task",
                    tint = Color.White)
            }
        },
        scaffoldState = scaffoldState
    ) {it.calculateBottomPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(taskBackGroundAnimation.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Task.taskColor.forEach {color ->
                    val colors = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.taskColor.value == colors) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    taskBackGroundAnimation.animateTo(
                                        targetValue = Color(colors),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditTaskEvent.ChangeColor(colors))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransParentTextField(text = titleState.text,
                hint = titleState.hint,
                onValueChange = { value ->
                                viewModel.onEvent(AddEditTaskEvent.EnteredTitle(value))
                },
                onFocusChange = { focusState ->
                    viewModel.onEvent(AddEditTaskEvent.ChangeTitleFocus(focusState))
                },
                isSingleLine = true,
                hintVisible = titleState.isHintVisible,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransParentTextField(text = contentState.text,
                hint = contentState.hint,
                onValueChange = {value ->
                    viewModel.onEvent(AddEditTaskEvent.EnteredContent(value))
                },
                onFocusChange = { focusState ->
                    viewModel.onEvent(AddEditTaskEvent.ChangeContentFocus(focusState))
                },
                isSingleLine = false    ,
                hintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
