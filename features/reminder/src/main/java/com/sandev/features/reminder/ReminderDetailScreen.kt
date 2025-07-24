package com.sandev.features.reminder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sandev.core.common.Result
import com.sandev.core.common.extensions.formatToDateTime
import com.sandev.core.common.extensions.showToast
import com.sandev.ui.components.LoadingDialogue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderDetailScreen(
    reminderId: String,
    onEditReminder: (String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReminderDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val reminderState by viewModel.reminder.collectAsState()


    LaunchedEffect(reminderId) {
        viewModel.loadReminder(reminderId)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reminder Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEditReminder(reminderId) }) {
                Icon(Icons.Filled.Edit, "Edit reminder")
            }
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when (reminderState) {
                is Result.Loading -> {
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        CircularProgressIndicator()
//                        Text("Loading reminder...")
//                    }
                }
                is Result.Success -> {
                    val reminder = (reminderState as Result.Success).data
                    if (reminder == null) {
                        Text("Reminder not found.", style = MaterialTheme.typography.headlineSmall)
                    } else {
                        Text(text = reminder.title, style = MaterialTheme.typography.headlineLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Date & Time: ${reminder.dateTime.formatToDateTime()}", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        if (!reminder.notes.isNullOrBlank()) {
                            Text(text = "Notes: ${reminder.notes}", style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        reminder.imageUri?.let { url ->
                            AsyncImage(
                                model = url,
                                contentDescription = "Reminder image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
                is Result.Error -> {
                    Text("Error: ${(reminderState as Result.Error).exception.message}", color = MaterialTheme.colorScheme.error)
                }
            }
        }
//        LoadingDialogue(showDialog = aiGreetingState is Result.Loading)
    }
}