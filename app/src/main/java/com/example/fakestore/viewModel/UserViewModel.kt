package com.example.fakestore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.model.UserModel
import com.example.fakestore.repository.FakeStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//ViewModel para el usuario desde la API inyectada por Hilt y Repository con Retrofit
@HiltViewModel
class UserViewModel @Inject constructor(private val repository: FakeStoreRepository) : ViewModel() {

    //Esta variable la usamos para almacenar el usuario que se obtiene de la API
    private val _user = MutableStateFlow<UserModel?>(null)
    val user = _user.asStateFlow()

    //Esta funcion la usamos para obtener el usuario de la API y almacenarlo en la variable user del ViewModel
    fun getUser(id: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = repository.getUser(id)
                _user.value = result
            }
        }
    }
}