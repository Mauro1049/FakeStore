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

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: FakeStoreRepository): ViewModel(){

    var token by mutableStateOf<String?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

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

    fun logOut(context: Context){
        val sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        token = null
    }

    private fun saveUserSession(context: Context, token: String){
        val sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userToken", token)
        editor.apply()
    }

    fun loadUserSession(context: Context){
        val sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("userToken", null)
    }
}