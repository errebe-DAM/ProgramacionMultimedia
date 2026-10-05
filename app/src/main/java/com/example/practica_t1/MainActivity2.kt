package com.example.practica_t1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.practica_t1.ui.theme.PracticaT1Theme
import java.time.LocalDate
import java.util.Date

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticaT1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Form(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Form(mod: Modifier = Modifier) {
    val form = FormData()

    Column(modifier = mod) {
        OutlinedTextField(
            value = form.nombre,
            onValueChange = {form.nombre = it},
            label = { Text("Nombre") },
            singleLine = true,
        )
        OutlinedTextField(
            value = form.primerApellido,
            onValueChange = {form.primerApellido = it},
            label = { Text("Primer apellido") },
            singleLine = true,
        )
        OutlinedTextField(
            value = form.segundoApellido,
            onValueChange = {form.segundoApellido = it},
            label = { Text("Nombre") },
            singleLine = true,
        )
        OutlinedTextField(
            value = form.tlf.toString(),
            onValueChange = {form.tlf = it.toInt()},
            label = { Text("Telefono") },
            singleLine = true,
        )
        OutlinedTextField(
            value = form.email,
            onValueChange = {form.email = it},
            label = { Text("Email") },
            singleLine = true,
        )
        OutlinedTextField(
            value = form.contraseña,
            onValueChange = {form.contraseña = it},
            label = { Text("Contraseña") },
            singleLine = true,
        )
    }
}

data class FormData(
    var nombre: String = "",
    var primerApellido: String = "",
    var segundoApellido: String = "",
    var contraseña: String = "",
    var tlf : Int = 0,
    var email: String = "",
    var direccion: String = "",
    var fNacimiento: LocalDate = LocalDate.now()
)