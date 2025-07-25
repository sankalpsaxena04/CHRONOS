package com.sandev.data.reminder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.sandev.core.common.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.sandev.data.reminder.dto.ReminderDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) {

    private fun getRemindersCollection(userId: String) =
        firestore.collection(Constants.USERS_COLLECTION)
            .document(userId)
            .collection(Constants.REMINDERS_COLLECTION)

    suspend fun addReminder(userId: String, reminderDto: ReminderDTO) {
        getRemindersCollection(userId).add(reminderDto).await()
    }

    suspend fun updateReminder(userId: String, reminderDto: ReminderDTO) {
        getRemindersCollection(userId).document(reminderDto.id).set(reminderDto).await()
    }

    suspend fun deleteReminder(userId: String, reminderId: String) {
        getRemindersCollection(userId).document(reminderId).delete().await()
    }

    fun getReminders(userId: String): Flow<List<ReminderDTO>> {
        return callbackFlow {
            val registration = getRemindersCollection(userId)
                .orderBy("dateTime", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val reminders = snapshot?.toObjects(ReminderDTO::class.java) ?: emptyList()
                    trySend(reminders).isSuccess
                }

            awaitClose {
                registration.remove()
            }
        }
    }

    suspend fun getReminderById(userId: String, reminderId: String): ReminderDTO? {
        return getRemindersCollection(userId).document(reminderId).get().await().toObject(ReminderDTO::class.java)
    }

    suspend fun uploadImage(userId: String, imageUri: Uri): String {
        val compressedUri = compressImage(context,imageUri,60)
        val fileName = "${UUID.randomUUID()}_${compressedUri.lastPathSegment}"
        val storageRef = firebaseStorage.reference
            .child(Constants.REMINDER_IMAGE_STORAGE_PATH)
            .child(userId)
            .child(fileName)

        val uploadTask = storageRef.putFile(compressedUri).await()
        return uploadTask.storage.downloadUrl.await().toString()
    }

    suspend fun deleteImage(imageUrl: String) {
        if (imageUrl.isNotEmpty()) {
            try {
                firebaseStorage.getReferenceFromUrl(imageUrl).delete().await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun compressImage(context: Context, inputUri: Uri, quality: Int = 50): Uri {
        val bitmap: Bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, inputUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, inputUri)
        }

        val compressedFile = File(context.cacheDir, "compressed_${System.currentTimeMillis()}.jpg")
        FileOutputStream(compressedFile).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        }

        return Uri.fromFile(compressedFile)
    }
}