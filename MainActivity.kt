package com.example.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.lab4.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by rememberSaveable { mutableStateOf(false) }

            Lab4Theme(darkTheme = darkTheme) {
                Scaffold { innerPadding ->
                    MainScreen(
                        darkTheme = darkTheme,
                        onToggleTheme = { darkTheme = !darkTheme },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

