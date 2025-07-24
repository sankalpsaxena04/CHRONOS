<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   
   </head>
<body>
    <div class="logo-container">

  <img width="200" height="200" alt="Gemini_Generated_Image_l5jwwvl5jwwvl5jw" src="https://github.com/user-attachments/assets/faa035f1-c911-4e70-8b5e-d3ef7ae3ab5f" 
        />
        <img height="200" alt="Gemini_Generated_Image_l5jwwvl5jwwvl5jw" src="https://github.com/user-attachments/assets/6578cf6a-20e1-4b4d-b885-d5dd6923dc6f" 
        />
        <img  height="50" alt="Gemini_Generated_Image_l5jwwvl5jwwvl5jw" src="https://github.com/user-attachments/assets/b41f6aa6-a136-48ea-b6a6-7da0293ab665" 
        />
        <img  height="200" alt="Gemini_Generated_Image_l5jwwvl5jwwvl5jw" src="https://github.com/user-attachments/assets/e0e868a5-507a-4c11-aa2b-d9c206c40938" 
        />
        <img  height="200" alt="Gemini_Generated_Image_l5jwwvl5jwwvl5jw" src="https://github.com/user-attachments/assets/c6866397-318a-4c83-8ead-fb7e1333475d" 
        />
        <img  height="200" alt="Gemini_Generated_Image_l5jwwvl5jwwvl5jw" src="https://github.com/user-attachments/assets/e0e868a5-507a-4c11-aa2b-d9c206c40938" 
        />
  </div>


   <h1>🚀 Project Chronos – Reminder App</h1>
    <p>
        <strong>Mission Code:</strong> CHRONOS/REMINDER-X1<br>
    </p>

   <div class="section">
        <h2>🧠 Objective</h2>
        <p>
            Chronos is an elegant, efficient, and future-ready Android application designed to empower users in scheduling and managing their reminders. It aims to be a thoughtful digital companion, focusing on polish, clarity, and simplicity to deliver a delightful user experience.
        </p>
    </div>

   <div class="section">
        <h2>🛠 Tech Arsenal</h2>
        <p>This project leverages modern Android development tools and patterns to ensure a robust and scalable application:</p>
        <ul>
            <li><span class="badge badge-green">UI Framework:</span> <strong>Jetpack Compose (Material 3)</strong> for declarative UI development.</li>
            <li><span class="badge badge-blue">Architecture:</span> <strong>MVVM + Clean Architecture</strong> for modular, testable, and maintainable code.</li>
            <li><span class="badge badge-purple">Async Operations:</span> <strong>Kotlin Coroutines + Flow</strong> for efficient asynchronous programming.</li>
            <li><span class="badge badge-orange">Authentication:</span> <strong>Firebase Auth (Google Sign-In)</strong> for secure user authentication.</li>
            <li><span class="badge badge-green">Database:</span> <strong>Firebase Firestore</strong> for cloud-based data persistence.</li>
            <li><span class="badge badge-blue">Notifications:</span> <strong>WorkManager or AlarmManager</strong> for reliable local reminder notifications, with proper Android 13+ permission handling.</li>
            <li><span class="badge badge-purple">Dependency Injection:</span> <strong>Dagger (or Hilt)</strong> for managing dependencies effectively.</li>
            <li><span class="badge badge-orange">Theming:</span> Support for <strong>dynamic light/dark modes</strong> to align with system preferences and offer manual switching.</li>
        </ul>
    </div>

  <div class="section">
        <h2>📋 Core Functionalities</h2>
        <ul>
            <li>
                <h3>✅ Reminder Management</h3>
                <ul>
                    <li>Add, edit, and delete reminders effortlessly.</li>
                    <li>Each reminder includes a <strong>Title</strong>, <strong>Date and Time</strong>, and optional <strong>Notes</strong>.</li>
                    <li>Ability to attach a <strong>Circular Image</strong> to reminders, picked from the device camera or gallery.</li>
                    <li>All reminders are securely persisted using <strong>Firebase Firestore</strong>.</li>
                </ul>
            </li>
            <li>
                <h3>🖼️ Image Picker + Upload</h3>
                <ul>
                    <li>Seamlessly pick images from the device gallery when creating or editing a reminder.</li>
                    <li>Selected images are efficiently uploaded to <strong>Firebase Storage</strong>.</li>
                    <li>The image URL is stored in Firestore, directly linked to its corresponding reminder.</li>
                    <li>Images (if added) are displayed within the reminder details or list view.</li>
                </ul>
            </li>
            <li>
                <h3>🔔 Notifications</h3>
                <ul>
                    <li>Reliable local notifications are scheduled to trigger precisely at the reminder time.</li>
                    <li>Utilizes <strong>WorkManager or AlarmManager</strong> for robust notification handling.</li>
                    <li>Ensures proper handling of notification permissions, especially for Android 13 and above.</li>
                </ul>
            </li>
            <li>
                <h3>👤 Authentication</h3>
                <ul>
                    <li>Secure and convenient user sign-in using <strong>Google Sign-In via Firebase Auth</strong>.</li>
                    <li>A simple and clear logout option for user control.</li>
                </ul>
            </li>
            <li>
                <h3>🎨 Dynamic Theming</h3>
                <ul>
                    <li>The app seamlessly follows the system-wide light/dark theme preference.</li>
                    <li>Users have the option for manual switching between light and dark modes within the app settings.</li>
                </ul>
            </li>
        </ul>
    </div>


   <hr>
  
</body>
</html>
