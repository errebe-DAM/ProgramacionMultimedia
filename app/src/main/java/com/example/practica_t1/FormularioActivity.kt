package com.example.practica_t1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.practica_t1.ui.theme.PracticaT1Theme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class FormularioActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticaT1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Form()
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Form() {
    var nombre by remember { mutableStateOf("") }
    var primerApellido by remember { mutableStateOf("") }
    var segundoApellido by remember { mutableStateOf("") }
    var tlf by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fNacimiento by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    val context = LocalContext.current

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = fNacimiento
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        fNacimiento = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    showDatePicker = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(datePickerState)
        }
    }

    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
        )
        OutlinedTextField(
            value = primerApellido,
            onValueChange = { primerApellido = it },
            label = { Text("Primer apellido") },
            singleLine = true,
        )
        OutlinedTextField(
            value = segundoApellido,
            onValueChange = { segundoApellido = it },
            label = { Text("Segundo apellido") },
            singleLine = true,
        )
        OutlinedTextField(
            value = tlf,
            onValueChange = { tlf = it },
            label = { Text("Telefono") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
        )
        OutlinedTextField(
            value = fNacimiento.toString(),
            onValueChange = {},
            label = { Text("Fecha de nacimiento") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Seleccionar fecha"
                    )
                }
            }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Button({
            Bundle()
            val intent = Intent(context, PerfilActivity::class.java).apply {
                val data = FormData(
                    nombre,
                    primerApellido,
                    segundoApellido,
                    tlf,
                    email,
                    fNacimiento.toString(),
                    password
                )
                putExtra("data", data)
            }
            context.startActivity(intent)
        }) {
            Text("Enviar")
        }
    }
}

data class FormData(
    val nombre: String,
    val primerApellido: String,
    val segundoApellido: String,
    val tlf: String,
    val email: String,
    val fNacimiento: String,
    val password: String
) : java.io.Serializable