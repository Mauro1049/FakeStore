package com.example.fakestore.viewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.repository.FakeStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//ViewModel para el login de la aplicacion desde la API inyectada por Hilt y Repository con Retrofit
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: FakeStoreRepository): ViewModel(){

    //Variable para el login de la aplicacion desde la API
    var token by mutableStateOf<String?>(null)
        private set

    //Variable para el manejo de errores en el login de la aplicacion desde la API
    var errorMessage by mutableStateOf<String?>(null)
        private set

    //Variable para el manejo de la vista de login de la aplicacion desde la API
    var isLoading by mutableStateOf(false)
        private set

    /*
    * Funcion para el login de la aplicacion desde la API y almacenar el token
    *  en la variable token del ViewModel y en SharedPreferences para persistencia de datos
    * */
    fun login(username: String, password: String, context: Context){
        isLoading = true
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    token = repository.login(username, password)

                    token?.let { token ->
                        saveUserSession(context, token)
                    }
                }catch (e: Exception){
                    errorMessage = "Login failed: ${e.message}"
                } finally {
                    isLoading = false
                }
            }
        }
    }

    /*
    * Funcion para el logout de la aplicacion desde la API
    *  y eliminar el token de SharedPreferences para persistencia de datos
    * */
    fun logOut(context: Context){
        val sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        token = null
    }

    /*
    * Funcion para el manejo de la persistencia de datos del token del usuario en SharedPreferences
    * */
    private fun saveUserSession(context: Context, token: String){
        val sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userToken", token)
        editor.apply()
    }

    /*
    * Funcion para el manejo de la persistencia de datos del token del usuario en SharedPreferences
    * */
    fun loadUserSession(context: Context){
        val sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("userToken", null)
    }
}