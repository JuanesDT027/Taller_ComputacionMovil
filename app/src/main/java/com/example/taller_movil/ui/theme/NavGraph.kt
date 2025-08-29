package com.example.taller_movil.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    androidx.compose.material3.Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) { androidx.compose.material3.CircularProgressIndicator() }
}

@Composable
private fun ErrorScreen(msg: String) {
    androidx.compose.material3.Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) { androidx.compose.material3.Text(msg) }
}
