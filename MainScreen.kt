package com.example.lab4

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    darkTheme: Boolean,
    onToggleTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    var fieldSize by remember { mutableStateOf(3) }
    var startGame by remember { mutableStateOf(false) }
    var playerXScore by remember { mutableStateOf(0) }
    var playerOScore by remember { mutableStateOf(0) }

    Column(modifier = modifier.fillMaxSize()) {
        // Кнопка перемикання теми
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { onToggleTheme() }) {
                Icon(
                    imageVector = if (darkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                    contentDescription = "Перемкнути тему"
                )
            }
        }

        if (!startGame) {
            // Вибір розміру поля
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Виберіть розмір поля:", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    listOf(3, 4, 5).forEach { size ->
                        Button(
                            onClick = {
                                fieldSize = size
                                startGame = true
                            },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text("${size}x$size")
                        }
                    }
                }
            }
        } else {
            GameBoard(
                dim = fieldSize,
                playerXScore = playerXScore,
                playerOScore = playerOScore,
                onScoreUpdate = { x, o ->
                    playerXScore = x
                    playerOScore = o
                },
                onNewGame = {
                    startGame = false
                    playerXScore = 0
                    playerOScore = 0
                }
            )
        }
    }
}

