package com.sandev.chronos.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.sandev.features.reminder.AddEditReminderScreen
import com.sandev.features.reminder.ReminderDetailScreen

object ReminderFeatureRoutes {
    const val reminderGraphRoute = "reminder_graph"
    const val addReminderScreenRoute = "add_reminder_screen"
    const val editReminderScreenRoute = "edit_reminder_screen/{reminderId}"
    const val reminderDetailScreenBaseRoute = "reminder_detail_screen"
    const val reminderDetailScreenRouteWithArgs = "$reminderDetailScreenBaseRoute/{reminderId}"

    fun reminderDetailScreenRoute(reminderId: String) = "$reminderDetailScreenBaseRoute/$reminderId"
}

fun NavGraphBuilder.reminderGraph(navController: NavController) {
    navigation(startDestination = ReminderFeatureRoutes.addReminderScreenRoute, route = ReminderFeatureRoutes.reminderGraphRoute) {
        composable(route = ReminderFeatureRoutes.addReminderScreenRoute) {
            AddEditReminderScreen(
                reminderId = null,
                onReminderSaved = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = ReminderFeatureRoutes.editReminderScreenRoute,
            arguments = listOf(navArgument("reminderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val reminderId = backStackEntry.arguments?.getString("reminderId")
            AddEditReminderScreen(
                reminderId = reminderId,
                onReminderSaved = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = ReminderFeatureRoutes.reminderDetailScreenRouteWithArgs,
            arguments = listOf(navArgument("reminderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val reminderId = backStackEntry.arguments?.getString("reminderId")
            if (reminderId != null) {
                ReminderDetailScreen(
                    reminderId = reminderId,
                    onEditReminder = { id ->
                        navController.navigate(ReminderFeatureRoutes.editReminderScreenRoute.replace("{reminderId}", id))
                    },
                    onNavigateBack = { navController.popBackStack() }
                )
            } else {

                navController.popBackStack()
            }
        }
    }
}
