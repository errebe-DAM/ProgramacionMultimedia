package com.example.practica

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practica.ui.theme.PracticaTheme
import java.io.File
import java.time.LocalDate
import kotlin.math.absoluteValue

class TitulosActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    private fun readFromCSV(fileName: String): List<TitulosActivityData> {
        try {
            val inputStream = baseContext.assets.open(fileName)
            inputStream.bufferedReader().useLines { lines ->
                return lines.map { line ->
                    val s = line.split(",")

                    return@map TitulosActivityData(
                        titulo = s[0],
                        fecha_lanzamiento = s[1],
                        descripcion = s[2],
                        puntuacion = s[3].toInt(),
                        plataforma = s[4],
                        n_jugadores = s[5].toInt()
                    )
                }.toList()
            }
        } catch (e: Exception) {
            Log.e("WTF", e.message ?: "Nada")
            return emptyList<TitulosActivityData>()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val data = readFromCSV("titulos.csv")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(data.size) { i ->
                            Column(
                                Modifier
                                    .background(
                                        Color(0, 0, 255, 50),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                            ) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(data[i].titulo,
                                        Modifier
                                            .weight(1f)
                                    )
                                    Spacer(Modifier.weight(0.3f))
                                    Text(data[i].fecha_lanzamiento,
                                        Modifier
                                            .padding(8.dp)
                                    )
                                }
                                Text(data[i].descripcion, Modifier.fillMaxWidth())
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(data[i].puntuacion.absoluteValue.toString())
                                    Text(data[i].plataforma)
                                    Text(data[i].n_jugadores.toString())
                                }
                            }
                            }
                    }
                }
            }
        }
    }
}

private data class TitulosActivityData(
    val titulo: String,
    val fecha_lanzamiento: String,
    val descripcion: String,
    val puntuacion: Int,
    val plataforma: String,
    val n_jugadores: Int
)