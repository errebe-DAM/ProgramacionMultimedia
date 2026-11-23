package com.example.practica

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.IntentCompat
import com.example.practica.ui.theme.PracticaTheme
import com.example.practica.utils.convertMillisToDate
import kotlin.math.absoluteValue


class NuevaTareaActivity : ComponentActivity() {
        var data: Tarea? by mutableStateOf(null)

        private val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                data = result.data?.let {
                    IntentCompat.getSerializableExtra(
                        it,
                        "data",
                        Tarea::class.java
                    ) as Tarea
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var title by remember { mutableStateOf(data?.nombre ?: "") }
            var description by remember { mutableStateOf(data?.descripcion ?: "") }
            var priority by remember { mutableIntStateOf(data?.prioridad ?: 0) }

            val datePickerState = rememberDatePickerState()
            var showDatePicker by remember { mutableStateOf(false) }
            var selectedDate by remember { mutableStateOf(data?.fecha) }

            val context = LocalContext.current

            PracticaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Titulo Actividad
                        Text("Añadir nueva tarea",
                            Modifier
                                .fillMaxWidth()
                                .background(
                                    Color(1, 1, 255, 50),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(vertical = 16.dp)
                            ,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                        )

                        Column(Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                            // TITLE
                            OutlinedTextField(
                                value = title,
                                onValueChange = { title = it },
                                label = {
                                    Text("Título")
                                },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Spacer(Modifier.size(20.dp))

                            // DESCRIPTION
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                label = {
                                    Text("Descripción")
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.size(20.dp))

                            // PRIORITY
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .background(Color(121, 232, 139, 255), RoundedCornerShape(4.dp))
                                    .padding(8.dp)
                            ) {
                                Text("Prioridad", Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
                                Slider(
                                    value = priority.toFloat(),
                                    onValueChange = {priority = it.toInt()},
                                    valueRange = 0f..10f,
                                    steps = 10
                                )
                                Text(priority.toString(), Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                            }
                            Spacer(Modifier.size(20.dp))
                            //

                            // DEADLINE
                            OutlinedTextField(
                                value = selectedDate?.let { convertMillisToDate(it) } ?: "",
                                onValueChange =  {},
                                label = { Text("Fecha límite") },
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(
                                        onClick = {showDatePicker = !showDatePicker}
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.DateRange,
                                            contentDescription = "Select date"
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (showDatePicker) {
                                DatePickerDialog(
                                    onDismissRequest = {showDatePicker = !showDatePicker},
                                    confirmButton = {
                                        TextButton(onClick = {
                                            selectedDate = datePickerState.selectedDateMillis
                                            showDatePicker = !showDatePicker
                                        }) {
                                            Text("OK")
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = {
                                            showDatePicker = !showDatePicker
                                        }) {
                                            Text("Cancel")
                                        }
                                    }
                                ) {
                                    DatePicker(state = datePickerState)
                                }
                            }
                            Spacer(Modifier.size(20.dp))
                        }

                        Button(
                            onClick = {
                                context.let {
                                    launcher.launch(
                                        Intent(it, TareaActivity::class.java).apply {
                                            putExtra("data", Tarea(
                                                title,
                                                description,
                                                selectedDate,
                                                    priority.absoluteValue
                                            ))
                                        }
                                    )
                                }
                            },
                            enabled = title.isNotBlank() && description.isNotBlank()
                            ,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Guardar tarea")
                        }
                        Button(
                            onClick = {
                                context.let {
                                    it.startActivity(Intent(
                                        it,
                                        LlegadaActivity::class.java
                                    ))
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