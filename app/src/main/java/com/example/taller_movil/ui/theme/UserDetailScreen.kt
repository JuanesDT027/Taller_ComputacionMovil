package com.example.taller_movil.ui.theme

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.taller_movil.network.User

@Composable
fun UserDetailScreen(
    user: User,
    onBack: () -> Unit
) {
    val ctx = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${user.firstName} ${user.lastName}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row {
                AsyncImage(
                    model = user.image,
                    contentDescription = null,
                    modifier = Modifier.size(96.dp).clip(CircleShape)
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("${user.firstName} ${user.lastName}", style = MaterialTheme.typography.titleLarge)
                    Text(user.company?.name ?: "—", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(Modifier.height(16.dp))

            InfoRow(
                label = "Teléfono",
                value = user.phone,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${user.phone}"))
                    ctx.startActivity(intent)
                }
            )

            // 6 campos adicionales
            InfoRow("Email", user.email ?: "—")
            InfoRow("Edad", user.age?.toString() ?: "—")
            InfoRow("Género", user.gender ?: "—")
            InfoRow("Nacimiento", user.birthDate ?: "—")
            InfoRow("Ciudad", user.address?.city ?: "—")
            InfoRow("Cargo", user.company?.title ?: "—")
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(12.dp))
    }
}
