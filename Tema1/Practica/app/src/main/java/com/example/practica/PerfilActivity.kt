package com.example.practica

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.IntentCompat
import com.example.practica.ui.theme.PracticaT1Theme
import androidx.core.net.toUri

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val data = IntentCompat.getSerializableExtra(intent, "data", FormData::class.java)
            PracticaT1Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(Modifier.width(IntrinsicSize.Max)) {
                            FormField("Nombre", data?.nombre)
                            FormField("Primer apellido", data?.primerApellido)
                            FormField("Segundo apellido", data?.segundoApellido)
                            FormField("Tlf", data?.tlf, !data?.tlf.isNullOrEmpty()) {
                                startActivity(
                                    Intent(Intent.ACTION_DIAL)
                                        .setData("tel:${data?.tlf}".toUri())
                                )
                            }
                            FormField("Direccion", data?.direccion, !data?.direccion.isNullOrEmpty()) {
                                startActivity(
                                    Intent(Intent.ACTION_VIEW)
                                        .setData("geo:0,0?q=${data?.direccion}".toUri())
                                )
                            }
                            FormField("F. Nacimiento", data?.fNacimiento?.toString() ?: "")
                            FormField("Email", data?.email, !data?.email.isNullOrEmpty()) {
                                startActivity(
                                    Intent(Intent.ACTION_SENDTO)
                                        .setData("mailto:${data?.email}".toUri())
                                )
                            }
                            FormField("Password", data?.password)
                            Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceAround) {
                                Button(
                                    onClick = {
                                        val intent = Intent().apply {
                                            putExtra("data", data)
                                        }
                                        setResult(RESULT_OK, intent)
                                        finish()
                                    },
                                ) {
                                    Text("Volver")
                                }
                                Button(
                                    onClick = {
                                        val intent = Intent(this@PerfilActivity, MainActivity::class.java).apply {
                                            setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        }
                                        startActivity(intent)
                                    },
                                ) {
                                    Text("Cerrar sesion")
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun FormField(key: String, value: String?, clickable: Boolean = false, event: () -> Unit = {}) {
    val txt = if (value.isNullOrEmpty()) {
        "[${key}]"
    } else value

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("$key:")
        Text(
            txt,
            color = if (clickable && value != null) Color.Blue else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable(
                enabled = clickable,
                onClick = { event() }
            )
        )
    }
}