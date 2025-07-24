package com.sandev.data.reminder

import android.net.Uri
import com.sandev.core.common.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.sandev.data.reminder.dto.ReminderDTO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderDataSource @Inject constructor(
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
        val fileName = "${UUID.randomUUID()}_${imageUri.lastPathSegment}"
        val storageRef = firebaseStorage.reference
            .child(Constants.REMINDER_IMAGE_STORAGE_PATH)
            .child(userId)
            .child(fileName)

        val uploadTask = storageRef.putFile(imageUri).await()
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
}