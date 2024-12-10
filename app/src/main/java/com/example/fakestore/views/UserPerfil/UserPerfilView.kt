package com.example.fakestore.views.UserPerfil

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fakestore.components.DrawerContent
import com.example.fakestore.components.MainTopBar
import com.example.fakestore.components.UserInfo
import com.example.fakestore.model.UserModel
import com.example.fakestore.viewModel.LoginViewModel
import com.example.fakestore.viewModel.UserViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserPerfilView(
    userViewModel: UserViewModel,
    id: Int,
    loginViewModel: LoginViewModel,
    navController: NavController,
    ) {
    val user by userViewModel.user.collectAsState()

    LaunchedEffect(id) {
        userViewModel.getUser(id)
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onLogoutClick = {
                    scope.launch {
                        drawerState.close()
                    }
                    loginViewModel.logOut(context)
                    navController.navigate("LoginView"){
                        popUpTo("ProductsView") { inclusive = true }
                    }
                },
                navController
            )
        },
    ) {
        Scaffold(
            topBar = {
                user?.let {
                    MainTopBar(
                        title = it.username,
                        showBackButton = true,
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onClickBackButton = { navController.popBackStack() },
                        onClickCart = {
                            navController.navigate("ShoppingCartView")
                        }
                    )
                }
            },
        ) {
            user?.let { it1 -> ContentUserPerfilView(it, it1) }
        }
    }


}

@Composable
fun ContentUserPerfilView(paddingValues: PaddingValues, user: UserModel){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ){
                }
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = user.name.firstname + " " + user.name.lastname,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = user.username,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        UserInfo(
            title = "Titulo",
            icon = Icons.Default.Home,
            content = {
                UserInfoItem("Ciudad", user.address.city)
                UserInfoItem("Calle", user.address.street)
                UserInfoItem("Código Postal", user.address.zipcode)

            }
        )
    }
}

@Composable
private fun UserInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}