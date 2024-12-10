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

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: FakeStoreRepository) : ViewModel() {

    private val _user = MutableStateFlow<UserModel?>(null)
    val user = _user.asStateFlow()

    fun getUser(id: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = repository.getUser(id)
                _user.value = result
            }
        }
    }
}