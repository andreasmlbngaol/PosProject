package features.auth.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthForm(
    onLogin: (
        uid: String,
        pin: String,
        onFailure: () -> Unit
    ) -> Unit
) {
    var id by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var pinVisible by remember { mutableStateOf(false) }
    var errorMessageVisible by remember { mutableStateOf(false) }

    val pinFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Card(
        elevation = 8.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Masuk",
                style = MaterialTheme.typography.h5
            )

            AnimatedVisibility(visible = errorMessageVisible) {
                Text(
                    text = "ID atau PIN salah",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.error
                )
            }

            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("ID") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        pinFocusRequester.requestFocus()
                    }
                ),
                singleLine = true
            )
            OutlinedTextField(
                value = pin,
                onValueChange = { pin = it },
                label = { Text("PIN") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onLogin(id, pin) {
                            errorMessageVisible = true
                            focusManager.clearFocus()
                        }
                    }
                ),
                singleLine = true,
                visualTransformation = if(!pinVisible) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            pinVisible = !pinVisible
                        }
                    ) {
                        Icon(
                            imageVector = if(pinVisible) Icons.Default.Call else Icons.Default.Call,
                            contentDescription = "Show Password"
                        )
                    }
                },
                modifier = Modifier.focusRequester(pinFocusRequester)
            )

            Button(
                onClick = {
                    onLogin(id, pin) {
                        errorMessageVisible = true
                        focusManager.clearFocus()
                    }
                }
            ) {
                Text(text = "Login")
            }

        }
    }
}