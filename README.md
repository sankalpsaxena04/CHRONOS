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
<a class="github-link" href="https://github.com/sankalpsaxena04/CHRONOS/releases/tag/1.0" target="_blank">🔗 The Release is available</a>

<h1>🚀 Project Chronos – Reminder App</h1>
<p>
    <strong>Mission Code:</strong> CHRONOS/REMINDER-X1 <br>
</p>

<div class="section">
    <h2>🧠 Objective</h2>
    <p>
        Chronos was crafted as an elegant, efficient, and future-ready Android application to empower users with seamless scheduling and management of reminders. The objective was to build a thoughtful digital companion—polished, clear, and simple—to ensure a delightful user experience.
    </p>
</div>

<div class="section">
    <h2>🛠 Tech Arsenal</h2>
    <ul>
        <li><span class="badge badge-green">UI Framework:</span> <strong>Jetpack Compose (Material 3)</strong> for declarative UI.</li>
        <li><span class="badge badge-blue">Architecture:</span> <strong>MVVM + Clean Architecture</strong> for modular, testable, maintainable code.</li>
        <li><span class="badge badge-purple">Async Operations:</span> <strong>Kotlin Coroutines + Flow</strong> for efficient async handling.</li>
        <li><span class="badge badge-orange">Authentication:</span> <strong>Firebase Auth (Google Sign-In)</strong> for secure user authentication.</li>
        <li><span class="badge badge-green">Database:</span> <strong>Firebase Firestore</strong> for cloud-based data persistence.</li>
        <li><span class="badge badge-blue">Notifications:</span> <strong>WorkManager or AlarmManager</strong> for precise local notifications (Android 13+ permissions handled).</li>
        <li><span class="badge badge-purple">Dependency Injection:</span> <strong>Dagger (Hilt)</strong> for effective dependency management.</li>
        <li><span class="badge badge-orange">Theming:</span> Dynamic <strong>light/dark mode</strong> with both system and manual switch options.</li>
    </ul>
</div>

<div class="section">
    <h2>📋 Core Functionalities</h2>
    <ul>
        <li>
            <h3>✅ Reminder Management</h3>
            <ul>
                <li>Add, edit, and delete reminders with ease.</li>
                <li>Each reminder includes a <strong>Title</strong>, <strong>Date and Time</strong>, and optional <strong>Notes</strong>.</li>
                <li>Attach a <strong>Circular Image</strong> (camera/gallery) to reminders.</li>
                <li>All reminders securely persisted using <strong>Firebase Firestore</strong>.</li>
            </ul>
        </li>
        <li>
            <h3>🖼️ Image Picker + Upload</h3>
            <ul>
                <li>Pick images from your device gallery (or camera) while creating/editing reminders.</li>
                <li>Images uploaded to <strong>Firebase Storage</strong>, and the URL stored in Firestore alongside each reminder.</li>
            </ul>
        </li>
        <li>
            <h3>🔔 Notifications</h3>
            <ul>
                <li>Schedules local notifications for reminders with precision.</li>
                <li>Leverages <strong>WorkManager/AlarmManager</strong> for reliability, even if app is closed.</li>
                <li>Handles Android 13+ notification permission requirements gracefully.</li>
            </ul>
        </li>
        <li>
            <h3>👤 Authentication</h3>
            <ul>
                <li>Google Sign-In via <strong>Firebase Auth</strong> for quick, secure login.</li>
                <li>Simple logout for user control.</li>
            </ul>
        </li>
        <li>
            <h3>🎨 Dynamic Theming</h3>
            <ul>
                <li>App follows system-wide light/dark theme preference.</li>
                <li>Manual theme switching within app settings.</li>
            </ul>
        </li>
        <li>
            <h3>🤖 AI-Powered Sharing Feature</h3>
            <div class="ai-share">
                The app features a <strong>Share</strong> button that lets the user enter a short prompt/context (such as “Remind my teammate about demo at 3pm”). Using an AI text-generation service, Chronos creates a context-aware message (e.g., <em>“Don’t forget: Demo meeting at 3pm today! Sent via Chronos.”</em>) which the user can instantly share via any messaging or social app.<br>
                <span style="color:#5a78b2;">Effortless, personalized, smart sharing.</span>
            </div>
        </li>
    </ul>
</div>


</body>
</html>
