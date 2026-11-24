package com.example.practica

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practica.ui.theme.PracticaTheme

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var password by remember { mutableStateOf("") }
            val radioOptions = listOf("Administrador", "Cliente")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
            var boxChecked by remember { mutableStateOf(true) }


            PracticaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Perfil de usuario",
                            Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray)
                                .padding(vertical = 8.dp),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.padding(8.dp))
                        Image(
                            painter = painterResource(R.drawable.profile),
                            contentDescription = "User profile image",
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(200.dp),
                        )
                        Spacer(Modifier.padding(8.dp))
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextField(
                                value = intent.getStringExtra("") ?: "[Nombre de usuario]",
                                onValueChange = { },
                                label = {},
                                modifier = Modifier.weight(1f),
                                readOnly = true,
                                singleLine = true
                            )
                            Spacer(Modifier.weight(0.3f))
                            Text("[ROL DE USUARIO]",
                                Modifier
                                    .background(Color.Cyan)
                                    .padding(8.dp)
                                    .fillMaxHeight()
                                    .wrapContentHeight()
                                    .weight(1f),
                                textAlign = TextAlign.Center,
                            )
                        }
                        Spacer(Modifier.size(30.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            visualTransformation = PasswordVisualTransformation(),
                            label = { Text("Contraseña") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.size(30.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("Rol actual",
                                    Modifier
                                        .background(Color.LightGray)
                                        .padding(8.dp),
                                )
                                radioOptions.forEach { option ->
                                    Row(
                                        Modifier
                                            .padding(vertical = 8.dp)
                                            .selectable(
                                                selected = (option == selectedOption),
                                                onClick = { onOptionSelected(option) },
                                                role = Role.RadioButton
                                            ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = (option == selectedOption),
                                            onClick = {},
                                        )
                                        Text(option)
                                    }
                                }
                            }
                            Column(
                                Modifier.weight(1f),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text("Ajustes de correo",
                                    Modifier
                                        .background(Color.LightGray)
                                        .padding(8.dp),
                                )
                                Row(
                                    Modifier
                                            .selectable(
                                                selected = boxChecked,
                                                onClick = { boxChecked = !boxChecked },
                                                role = Role.Checkbox
                                            ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = boxChecked,
                                        onCheckedChange = {boxChecked = !boxChecked},
                                    )
                                    Text("Recibir correo")
                                }
                            }

                        }
                        // CHECKBOX Y RADIO
                        Spacer(Modifier.weight(1f))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                startActivity(
                                    Intent(this@PerfilActivity, LlegadaActivity::class.java)
                                )
                            }
                        ) {
                            Text("Volver al menu principal")
                        }
                    }
                }
            }
        }
    }
}
