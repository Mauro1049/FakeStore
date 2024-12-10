package com.example.fakestore.views.Login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fakestore.viewModel.LoginViewModel

@Composable
fun LoginView(
    loginViewModel: LoginViewModel,
    navController: NavController
){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isFormValid by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        loginViewModel.loadUserSession(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            modifier = Modifier
                .fillMaxWidth()
        )

        if (!isFormValid && username.isBlank()){
            Text(text = "El usuario es obligatorio", color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
        )

        if (!isFormValid && password.isBlank()){
            Text(text = "La contraseña es obligatoria", color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isFormValid = validateLoginForm(username, password)
                if (isFormValid) {
                    loginViewModel.login(username, password, context)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (loginViewModel.isLoading){
                CircularProgressIndicator(color = MaterialTheme.colorScheme.error, strokeWidth = 2.dp)
            }else{
                Text(text = "Login")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        loginViewModel.token?.let {
            LaunchedEffect(it) {
                navController.navigate("ProductsView/$it"){
                    popUpTo("LoginView") { inclusive = true }
                    launchSingleTop = true
                }
            }
        }

        loginViewModel.errorMessage?.let {
            Text(text = "Contraseña y/o Usuario incorrecto", color = MaterialTheme.colorScheme.error)
        }
    }
}

fun validateLoginForm(username: String, password: String): Boolean {
    return username.isNotBlank() && password.isNotBlank()
}