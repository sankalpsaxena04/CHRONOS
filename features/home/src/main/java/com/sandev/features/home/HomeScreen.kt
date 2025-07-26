package com.sandev.features.home

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.sandev.core.common.extensions.showToast
import com.sandev.ui.util.getNotificationPermission
import com.sandev.ui.util.remNotifPermissionLauncher
import com.sandev.core.common.Result
import com.sandev.core.common.extensions.formatToDateTime
import com.sandev.domain.reminder.model.Reminder
import com.sandev.ui.components.LoadingDialogue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddReminder: () -> Unit,
    onReminderClick: (String) -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val remindersState by viewModel.remindersState.collectAsState()
    val signOutState by viewModel.signOutState.collectAsState()
    val hasNotificationPermission = viewModel.hasNotificationPermission()
    val aiGreetingState by viewModel.aiGreetingState.collectAsState()
    val shareClickState by viewModel.shareClickState.collectAsState()
    var aiText by remember { mutableStateOf("") }

    val notificationPermissionLauncher = remNotifPermissionLauncher { isGranted ->
        if (isGranted) {
            context.showToast("Notification permission granted!")
        } else {
            context.showToast("Notification permission denied. You may not receive reminders.")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadReminders()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
            notificationPermissionLauncher.launch(getNotificationPermission())
        }
    }

    LaunchedEffect(signOutState) {
        when (signOutState) {
            is Result.Success -> {
                context.showToast("Signed out successfully!")
                onSignOut()
            }
            is Result.Error -> {
                context.showToast("Sign out failed: ${(signOutState as Result.Error).exception.message}")
            }
            else -> Unit
        }
    }
    LaunchedEffect(aiGreetingState) {
        when (aiGreetingState) {
            is Result.Success -> {
                val message = (aiGreetingState as Result.Success).data
                if (message.isNotBlank()) {
                    val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(android.content.Intent.EXTRA_TEXT, message)
                    }
                    context.startActivity(android.content.Intent.createChooser(shareIntent, "Share AI Message"))
                } else {
                    context.showToast("Could not generate AI message.")
                }
                viewModel.resetAiGreetingState()
            }
            is Result.Error -> {
                context.showToast("Error generating AI message: ${(aiGreetingState as Result.Error).exception.message}")
                viewModel.resetAiGreetingState()
            }
            is Result.Loading ->{
                context.showToast("Generating Message...")
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Column {
                    Text("Welcome to Chronos")
                    Text(
                        text = Firebase.auth.currentUser?.displayName?:"User", style = MaterialTheme.typography.bodySmall)
                }},
                actions = {
                    IconButton(onClick = { viewModel.signOut() }) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Sign Out")
                    }
                    IconButton(onClick = {
                            viewModel.shareStateToggle()
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share AI Message")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddReminder) {
                Icon(Icons.Filled.Add, "Add new reminder")
            }
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if(shareClickState){
                Row (modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically){
                    OutlinedTextField(value = aiText,
                        onValueChange = {
                            aiText = it
                        },
                        label = { Text("Enter prompt...") },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        enabled = aiText.isNotBlank(),
                        onClick = {
                            viewModel.shareAiGreeting(aiText)
                            aiText=""
                        },

                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
                    }
                }
            }
            when (remindersState) {
                is Result.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Text("Loading reminders...")
                    }
                }
                is Result.Success -> {
                    val reminders = (remindersState as Result.Success).data
                    if (reminders.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("No reminders yet. Tap '+' to add one!")
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(reminders, key = { it!!.id }) { reminder ->
                                ReminderItem(
                                    reminder = reminder!!,
                                    onClick = { onReminderClick(reminder.id) }
                                )
                            }
                        }
                    }
                }
                is Result.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Error loading reminders: ${(remindersState as Result.Error).exception.message}")
                    }
                }

                else -> {}
            }
        }

        LoadingDialogue(showDialog = signOutState is Result.Loading)
    }
}


@Composable
fun ReminderItem(
    reminder: Reminder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            reminder.imageUri?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "Reminder image",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = reminder.title, style = MaterialTheme.typography.headlineSmall)
                Text(text = reminder.dateTime.formatToDateTime(), style = MaterialTheme.typography.bodyMedium)
                if (!reminder.notes.isNullOrBlank()) {
                    Text(text = reminder.notes!!, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

