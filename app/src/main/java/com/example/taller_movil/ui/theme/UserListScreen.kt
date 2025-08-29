package com.example.taller_movil.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.stickyHeader
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.taller_movil.network.User

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserListScreen(
    users: List<User>,
    onUserClick: (User) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Usuarios") }) }
    ) { padding ->
        if (users.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(Modifier.padding(padding)) {
                stickyHeader {
                    Surface(tonalElevation = 2.dp) {
                        Text(
                            text = "Total: ${users.size}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                items(users, key = { it.id }) { u ->
                    ListItem(
                        headlineContent = { Text("${u.firstName} ${u.lastName}") },
                        supportingContent = { Text(u.company?.name ?: "—") },
                        leadingContent = {
                            AsyncImage(
                                model = u.image,
                                contentDescription = null,
                                modifier = Modifier.size(56.dp).clip(CircleShape)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onUserClick(u) }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Divider()
                }
            }
        }
    }
}
