package com.sandev.chronos.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sandev.features.home.HomeScreen

object HomeFeatureRoutes {
    const val homeGraphRoute = "home_graph"
    const val homeScreenRoute = "home_screen"
}

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = HomeFeatureRoutes.homeScreenRoute, route = HomeFeatureRoutes.homeGraphRoute) {
        composable(route = HomeFeatureRoutes.homeScreenRoute) {
            HomeScreen(
                onAddReminder = { navController.navigate(ReminderFeatureRoutes.addReminderScreenRoute) },
                onReminderClick = { reminderId ->
                    navController.navigate(ReminderFeatureRoutes.reminderDetailScreenRoute(reminderId))
                },
                onSignOut = {
                    navController.navigate(AuthFeatureRoutes.authGraphRoute) {
                        popUpTo(HomeFeatureRoutes.homeGraphRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}