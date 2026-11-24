package com.example.practica

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.IntentCompat
import com.example.practica.ui.theme.PracticaTheme
import com.example.practica.utils.convertMillisToDate
import java.time.Clock
import java.time.LocalDate
import java.util.Calendar
import kotlin.time.Instant

class LlegadaActivity : ComponentActivity() {
    private var tareas by mutableStateOf(
        listOf(
            Tarea("Tarea 1", "Lorem ipsum", System.currentTimeMillis(), 2),
            Tarea("Tarea 2", "Lorem ipsum", System.currentTimeMillis(), 9),
            Tarea("Tarea 3", "Lorem ipsum", System.currentTimeMillis(), 5),
            Tarea("Tarea 4", "Lorem ipsum", System.currentTimeMillis(), 1),
        )
    )

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            tareas = result.data?.let {
                IntentCompat.getSerializableExtra(
                    it,
                    "data",
                    ArrayList::class.java
                ) as ArrayList<Tarea>
            } ?: emptyList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current

            PracticaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                    ) {
                        // FECHA
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val date = Calendar.getInstance()
                            val day = date.get(Calendar.DAY_OF_MONTH)
                            val month = date.get(Calendar.MONTH)
                            val year = date.get(Calendar.MONTH)

                            Text("${day}, ${month} de ${year}",
                                Modifier
                                    .background(
                                        Color(1,1,255,50),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                            )
                        }
                        // ##########

                        // ZONA DEL PERFIL
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.profile),
                                contentDescription = "User profile image",
                                modifier = Modifier.size(150.dp),
                            )
                            Column(
                                Modifier
                                    .weight(2f)
                                    .padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("¡Bienvenido!")
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
                        Spacer(Modifier.size(width = 60.dp, height = 5.dp).fillMaxWidth().border(width = 1.dp, color = Color.Unspecified, shape = RectangleShape))
                        Column(Modifier.fillMaxWidth().weight(1f)) {
                            // Esto que da un poco raro pero es para no meterlo to' en un if
                            if (tareas.isEmpty())
                                return@Column Column(
                                    Modifier.weight(1f),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text("No tienes tareas ahora mismo",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
                                    )
                                }

                            for ((nombre, _, fecha) in tareas) {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(nombre, Modifier.weight(2f))
                                    fecha?.let {
                                        Text(
                                            convertMillisToDate(it),
                                            Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                        // ##########


                        // ZONA BOTONES
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                val intent = Intent(context, NuevaTareaActivity::class.java)
                                launcher.launch(intent)
                            }
                        ) {
                            Text("Añadir tareas")
                        }
                        Button(
                            onClick = {
                                val intent = Intent(context, TitulosActivity::class.java)
                                context.startActivity(intent)
                            },
                            Modifier.fillMaxWidth()
                        ) {
                            Text("Mis títulos")
                        }
                        Row( horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(
                                modifier = Modifier.weight(2f),
                                onClick = {
                                        startActivity(
                                            Intent(this@LlegadaActivity, PerfilActivity::class.java)
                                        )
                                }
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

