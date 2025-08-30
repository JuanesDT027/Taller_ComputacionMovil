package com.example.taller_movil.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taller_movil.data.UserViewModel
import com.example.taller_movil.data.UsersUiState

object Routes {
    const val LIST = "list"
    const val DETAIL = "detail/{id}"
}

@Composable
fun NavGraph(vm: UserViewModel) {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            val state by vm.state.collectAsState()
            when (state) {
                is UsersUiState.Loading -> LoadingScreen()
                is UsersUiState.Error -> ErrorScreen((state as UsersUiState.Error).message)
                is UsersUiState.Ready -> {
                    val users = (state as UsersUiState.Ready).data
                    UserListScreen(users = users) { u ->
                        nav.navigate("detail/${u.id}")
                    }
                }
            }
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            val user = vm.userById(id)
            if (user != null) {
                UserDetailScreen(user = user) { nav.popBackStack() }
            } else {
                ErrorScreen("Usuario no encontrado")
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { CircularProgressIndicator() }
}

@Composable
private fun ErrorScreen(msg: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { Text(text = msg) }
}
