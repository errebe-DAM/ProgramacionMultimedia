package com.example.practica

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as C
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.practica.ui.theme.PracticaTheme
import java.time.LocalDate
import java.util.Calendar

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalContext.current.startActivity(
                Intent(this, TareaActivity::class.java)
            )
            PracticaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                    ) {
                        // FECHA
                        Row(Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center)
                        {
                            val date = Calendar.getInstance()
                            val day = date.get(Calendar.DAY_OF_MONTH)
                            val month = date.get(Calendar.MONTH)
                            val year = date.get(Calendar.MONTH)
                            Text("${day}, ${month} de ${year}",
                                Modifier
                                    .background(
                                        C(1,1,255,50),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                            )
                        }
                        // ##########

                        // ZONA DEL PERFIL
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccountBox,
                                contentDescription = "Icono del usuario",
                                modifier = Modifier.size(128.dp).weight(1f)
                            )
                            Column(
                                Modifier.weight(2f)
                                    .padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("¡Bienvenido!", textAlign = TextAlign.Center)
                                Text("[NOMBRE DE USUARIO]")
                                Text("[ROL DE USUARIO]")
                            }
                        }
                        // ##########

                        // ZONA TAREAS
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                            textAlign = TextAlign.Center,
                            text="TAREAS PENDIENTES",
                        )
                        Column(Modifier.fillMaxWidth().weight(1f)) {
                            val tareas = listOf(
                                Tarea("Tarea 1", LocalDate.now()),
                                Tarea("Tarea 2", LocalDate.now()),
                                Tarea("Tarea 3", LocalDate.now()),
                                Tarea("Tarea 4", LocalDate.now()),
                            )
                            for (tarea in tareas) {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(tarea.nombre, Modifier.weight(2f))
                                    Text(tarea.fecha.toString(), Modifier.weight(1f))
                                }
                            }
                        }
                        // ##########


                        // ZONA BOTONES
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {}
                        ) {
                            Text("Añadir tareas")
                        }
                        Row( horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(
                                modifier = Modifier.weight(2f),
                                onClick = {}
                            ) {
                                Text("Perfil")
                            }
                            Spacer(Modifier.weight(0.5f))
                            Button(
                                modifier = Modifier.weight(2f),
                                onClick = {}
                            ) {
                                Text("Ajustes")
                            }
                        }
                    }
                }
            }
        }
    }
}