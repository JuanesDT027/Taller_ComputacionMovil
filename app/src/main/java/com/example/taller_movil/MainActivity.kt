package com.example.taller_movil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taller_movil.data.UserViewModel
import com.example.taller_movil.ui.theme.NavGraph
import com.example.taller_movil.ui.theme.Taller_movilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Taller_movilTheme {
                val vm: UserViewModel = viewModel()
                NavGraph(vm)
            }
        }
    }
}
