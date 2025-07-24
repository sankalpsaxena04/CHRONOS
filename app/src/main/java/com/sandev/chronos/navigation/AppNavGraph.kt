package com.sandev.chronos.navigation

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sandev.features.auth.AuthScreen
import com.sandev.features.auth.AuthViewModel
import com.sandev.features.home.HomeScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    isUserSignedIn: Boolean
) {
    navigation(
        startDestination = if (isUserSignedIn) AppFeatureRoutes.homeScreen else AppFeatureRoutes.authScreen,
        route = "main_graph"
    ) {

        composable(AppFeatureRoutes.authScreen) {
            val viewModel: AuthViewModel = hiltViewModel()
            val state by viewModel.authState.collectAsStateWithLifecycle()

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        result.data?.let {
                            viewModel.signInWithIntent(it)
                        }
                    }
                }
            )

            LaunchedEffect(state.isSignInSuccess) {
                if (state.isSignInSuccess) {
                    navController.navigate(AppFeatureRoutes.homeScreen) {
                        popUpTo(AppFeatureRoutes.authScreen) { inclusive = true }
                    }
                }
            }

            AuthScreen(
                state = state,
                signIn = {
                    viewModel.beginSignIn { intentSender ->
                        launcher.launch(
                            IntentSenderRequest.Builder(intentSender ?: return@beginSignIn).build()
                        )
                    }
                }
            )
        }

        composable(AppFeatureRoutes.homeScreen) {
            HomeScreen(
                onAddReminder = {
                    navController.navigate(ReminderFeatureRoutes.addReminderScreenRoute)
                },
                onReminderClick = { reminderId ->
                    navController.navigate(ReminderFeatureRoutes.reminderDetailScreenRoute(reminderId))
                },
                onSignOut = {
                    navController.navigate(AppFeatureRoutes.authScreen) {
                        popUpTo(AppFeatureRoutes.homeScreen) { inclusive = true }
                    }
                }
            )
        }

        reminderGraph(navController)
    }
}

object AppFeatureRoutes {
    const val authScreen = "auth_screen"
    const val homeScreen = "home_screen"
}