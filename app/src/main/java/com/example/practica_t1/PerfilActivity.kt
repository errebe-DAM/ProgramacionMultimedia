package com.example.practica_t1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.core.content.IntentCompat
import com.example.practica_t1.ui.theme.PracticaT1Theme
import kotlin.reflect.full.memberProperties

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val data = IntentCompat.getSerializableExtra(intent, "data", FormData::class.java)

        setContent {
            PracticaT1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row() {
                            for (property in FormData::class.memberProperties) {
                                val key = property.name
                                val value = property.getter.call(data)
                                Text("${key}: ${if ((value as String).isNotEmpty()) value else "[${key}]"}")
                            }
                        }
                    }
                }
            }
        }
    }
}