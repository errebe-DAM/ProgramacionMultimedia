package com.example.practica

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.practica.ui.theme.PracticaTheme
import com.example.practica.utils.convertMillisToDate
import kotlin.math.absoluteValue

class TareaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val tarea = intent.getSerializableExtra("data") as? Tarea

            val context = LocalContext.current
            val titulo = tarea?.nombre ?: "[TITULO TAREA]"
            val descripcion = tarea?.descripcion ?: "[DESCRIPCION]"
            val prioridad = tarea?.prioridad?.toString() ?: "[Dígito de prioridad]"
            val fecha = tarea?.fecha?.let { convertMillisToDate(it) } ?: "[Fecha límite]"

            PracticaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // TITLE
                        Text(titulo,
                            Modifier
                                .fillMaxWidth()
                                .background(
                                    Color(1, 1, 255, 50),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(Modifier.size(20.dp))
                        //

                        // DESCRIPTION
                        Text(descripcion, Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        )
                        Spacer(Modifier.size(20.dp))
                        //

                        // DATA
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                        ) {
                            Text(
                                "Prioridad",
                                Modifier
                                    .align(Alignment.TopStart)
                                    .background(
                                        Color(121, 232, 139, 255),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(4.dp)
                                ,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(prioridad,
                                Modifier.align(Alignment.BottomStart)
                            )
                            Text("Fecha límite",
                                Modifier
                                    .align(Alignment.TopEnd)
                                    .background(
                                        Color(121, 232, 139, 255),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(4.dp),
                                fontWeight = FontWeight.Bold
                            )

                             Text(fecha, Modifier.align(Alignment.BottomEnd))
                        }
                        Spacer(Modifier
                            .size(20.dp)
                            .weight(1f))
                        //

                        // BUTTONS
                        Button(
                            onClick = {
                                val intent = Intent().apply {
                                    putExtra("data", Tarea(
                                        titulo,
                                        descripcion,
                                        tarea?.fecha,
                                        tarea?.prioridad?.absoluteValue
                                    ))
                                }
                                setResult(RESULT_OK, intent)
                                finish()
                            },
                            Modifier.fillMaxWidth()
                        ) {
                            Text("Editar tarea")
                        }
                        Button(
                            onClick = {
                                context.let {
                                    it.startActivity(
                                        Intent(it, LlegadaActivity::class.java)
                                    )
                                }
                            },
                            Modifier.fillMaxWidth()
                        ) {
                            Text("Volver al menú principal")
                        }
                    }
                }
            }
        }
    }
}
