package com.example.taller_movil.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taller_movil.network.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface UsersUiState {
    data object Loading : UsersUiState
    data class Ready(val data: List<User>) : UsersUiState
    data class Error(val message: String) : UsersUiState
}

class UserViewModel(
    private val repo: UserRepository = UserRepository()
) : ViewModel() {

    private val _state = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val state: StateFlow<UsersUiState> = _state

    init {
        viewModelScope.launch {
            try {
                _state.value = UsersUiState.Ready(repo.users())
            } catch (e: Exception) {
                _state.value = UsersUiState.Error("No se pudo cargar: ${e.message}")
            }
        }
    }

    fun userById(id: Int): User? = repo.findById(id)
}
