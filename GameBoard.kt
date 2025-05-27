package com.example.lab4

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun GameBoard(
    dim: Int,
    playerXScore: Int,
    playerOScore: Int,
    onScoreUpdate: (Int, Int) -> Unit,
    onNewGame: () -> Unit
) {
    var field by remember { mutableStateOf(List(dim * dim) { "" }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var gameFinished by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(10) }

    // Таймер
    LaunchedEffect(currentPlayer, gameFinished) {
        timer = 10
        while (timer > 0 && !gameFinished) {
            delay(1000)
            timer--
        }
        if (timer == 0 && !gameFinished) {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Гравець: $currentPlayer", style = MaterialTheme.typography.headlineSmall)
        Text("Таймер: $timer", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Сітка гри
        for (row in 0 until dim) {
            Row {
                for (col in 0 until dim) {
                    val index = row * dim + col
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(4.dp)
                            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                            .clickable(enabled = field[index] == "" && !gameFinished) {
                                field = field.toMutableList().apply { set(index, currentPlayer) }
                                if (checkWin(field, dim, currentPlayer)) {
                                    gameFinished = true
                                    if (currentPlayer == "X") {
                                        onScoreUpdate(playerXScore + 1, playerOScore)
                                    } else {
                                        onScoreUpdate(playerXScore, playerOScore + 1)
                                    }
                                } else if (!field.contains("")) {
                                    gameFinished = true
                                } else {
                                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = field[index],
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопки керування
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            Button(onClick = {
                field = List(dim * dim) { "" }
                currentPlayer = "X"
                gameFinished = false
            }, modifier = Modifier.padding(8.dp)) {
                Text("Скинути раунд")
            }
            Button(onClick = onNewGame, modifier = Modifier.padding(8.dp)) {
                Text("Нова гра")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Рахунок - X: $playerXScore | O: $playerOScore", style = MaterialTheme.typography.bodyLarge)
    }
}

// Функція перевірки перемоги
fun checkWin(field: List<String>, dim: Int, player: String): Boolean {
    // Перевірка рядків
    for (row in 0 until dim) {
        if ((0 until dim).all { col -> field[row * dim + col] == player }) return true
    }
    // Перевірка стовпців
    for (col in 0 until dim) {
        if ((0 until dim).all { row -> field[row * dim + col] == player }) return true
    }
    // Перевірка головної діагоналі
    if ((0 until dim).all { i -> field[i * dim + i] == player }) return true
    // Перевірка побічної діагоналі
    if ((0 until dim).all { i -> field[i * dim + (dim - i - 1)] == player }) return true

    return false
}
