package com.sandev.chronos.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sandev.features.auth.AuthScreen


object AuthFeatureRoutes {
    const val authGraphRoute = "auth_graph"
    const val authScreenRoute = "auth_screen"
}
fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = AuthFeatureRoutes.authScreenRoute, route = AuthFeatureRoutes.authGraphRoute) {
        composable(route = AuthFeatureRoutes.authScreenRoute) {
//            AuthScreen(
////                onSignInSuccess = {
////                    navController.navigate(HomeFeatureRoutes.homeGraphRoute) {
////                        popUpTo(AuthFeatureRoutes.authGraphRoute) {
////                            inclusive = true
////                        }
////                    }
////                }
//            )
        }
    }
}