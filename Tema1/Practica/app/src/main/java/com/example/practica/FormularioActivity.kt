package com.example.practica

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.core.content.IntentCompat
import com.example.practica.ui.theme.PracticaT1Theme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class FormularioActivity : ComponentActivity() {
    private var data by mutableStateOf(FormData())

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val d = result.data?.let {
                IntentCompat.getSerializableExtra(it, "data", FormData::class.java)
            }
            data = d ?: FormData()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            PracticaT1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        key(data) {
                            Form(data) { data ->
                                val intent = Intent(
                                    context,
                                    PerfilActivity::class.java
                                ).putExtra("data", data)

                                launcher.launch(intent)
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Form(data: FormData, onSend: (FormData) -> Unit) {
    var formData by remember(data) { mutableStateOf(data) }

    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = null
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        formData = formData.copy(
                            fNacimiento = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        )
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
            value = formData.nombre,
            onValueChange = {formData = formData.copy(nombre = it)},
            label = { Text("Nombre") },
            singleLine = true,
        )
        OutlinedTextField(
            value = formData.primerApellido,
            onValueChange = { formData = formData.copy(primerApellido = it) },
            label = { Text("Primer apellido") },
            singleLine = true,
        )
        OutlinedTextField(
            value = formData.segundoApellido,
            onValueChange = { formData = formData.copy(segundoApellido = it) },
            label = { Text("Segundo apellido") },
            singleLine = true,
        )
        OutlinedTextField(
            value = formData.tlf,
            onValueChange = { formData = formData.copy(tlf = it) },
            label = { Text("Telefono") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        OutlinedTextField(
            value = formData.direccion,
            onValueChange = { formData = formData.copy(direccion = it) },
            label = { Text("Direccion") },
            singleLine = true,
        )
        OutlinedTextField(
            value = formData.email,
            onValueChange = { formData = formData.copy(email = it) },
            label = { Text("Email") },
            singleLine = true,
        )
        OutlinedTextField(
            value = formData.fNacimiento?.toString() ?: "",
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
            value = formData.password,
            onValueChange = { formData = formData.copy(password = it) },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Button({
            onSend(formData)
        }) {
            Text("Enviar")
        }
    }
}

data class FormData(
    val nombre: String = "",
    val primerApellido: String = "",
    val segundoApellido: String = "",
    val tlf: String = "",
    val direccion: String = "",
    val email: String = "",
    val fNacimiento: LocalDate? = null,
    val password: String = ""
) : java.io.Serializable