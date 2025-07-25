package com.sandev.features.reminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.test.espresso.base.Default
import com.sandev.core.common.Result
import com.sandev.core.common.extensions.showToast
import java.util.Calendar
import java.util.Date
import androidx.compose.ui.text.input.ImeAction
import coil.compose.AsyncImage
import com.sandev.core.common.extensions.formatToDate
import com.sandev.core.common.extensions.formatToTime
import com.sandev.domain.reminder.model.Reminder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditReminderScreen(
    reminderId: String?,
    onReminderSaved: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEditReminderViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val currentReminder by viewModel.reminder.collectAsState()
    val saveReminderState by viewModel.saveReminderState.collectAsState()

    var title by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var selectedDateTime by remember { mutableStateOf(Date()) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var existingImageUrl by remember { mutableStateOf<String?>(null) }

    val isEditMode = reminderId != null

    LaunchedEffect(reminderId) {
        if (isEditMode && reminderId != null) {
            viewModel.loadReminder(reminderId)
        }
    }

    LaunchedEffect(currentReminder) {
        if (isEditMode && currentReminder is Result.Success) {
            val reminder = (currentReminder as Result.Success).data
            if (reminder != null) {
                title = reminder.title
                notes = reminder.notes ?: ""
                selectedDateTime = reminder.dateTime
                existingImageUrl = reminder.imageUri
            }
        }
    }

    LaunchedEffect(saveReminderState) {
        when (saveReminderState) {
            is Result.Success -> {
                context.showToast(if (isEditMode) "Reminder updated!" else "Reminder added!")
                onReminderSaved()
            }
            is Result.Error -> {
                context.showToast("Error: ${(saveReminderState as Result.Error).exception.message}")
            }
            else -> {
                
            }
        }
    }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val newDate = Calendar.getInstance().apply {
                    time = selectedDateTime
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }.time
                selectedDateTime = newDate
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
    }

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val newTime = Calendar.getInstance().apply {
                    time = selectedDateTime
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                }.time
                selectedDateTime = newTime
            },
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            false
        )
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Reminder" else "Add Reminder") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (isEditMode) {
                        IconButton(onClick = {
                            viewModel.deleteReminder(reminderId!!)
                            onReminderSaved()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Reminder")
                        }
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = selectedDateTime.formatToDate(),
                    onValueChange = { },
                    label = { Text("Date") },
                    readOnly = true,
                    modifier = Modifier.weight(1f),
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Select Date")
                        }
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    value = selectedDateTime.formatToTime(),
                    onValueChange = {  },
                    label = { Text("Time") },
                    readOnly = true,
                    modifier = Modifier.weight(1f),
                    trailingIcon = {
                        IconButton(onClick = { timePickerDialog.show() }) {
                            Icon(Icons.Default.AddCircle, contentDescription = "Select Time")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                    Icon(Icons.Default.Person, contentDescription = "Pick Image")
                    Text("Pick Image")
                }
                Spacer(modifier = Modifier.size(16.dp))
                val imageToDisplay = selectedImageUri ?: existingImageUrl
                if (imageToDisplay != null) {
                    AsyncImage(
                        model = imageToDisplay,
                        contentDescription = "Selected Reminder Image",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .clickable {
                                imagePickerLauncher.launch("image/*")
                            },
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    if (selectedImageUri != null || existingImageUrl != null) {
                        IconButton(onClick = {
                            selectedImageUri = null
                            existingImageUrl = null
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove Image")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val reminder = Reminder(
                        id = reminderId ?: "",
                        userId = "",
                        title = title,
                        dateTime = selectedDateTime,
                        notes = notes.takeIf { it.isNotBlank() },
                        imageUri = existingImageUrl
                    )
                    if (isEditMode) {
                        viewModel.updateReminder(reminder, selectedImageUri)
                    } else {
                        viewModel.addReminder(reminder, selectedImageUri)
                    }
                },
                enabled = title.isNotBlank() ,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Star, contentDescription = "Save Reminder")
                Text(if (isEditMode) "Update Reminder" else "Add Reminder")
            }
        }


    }
}