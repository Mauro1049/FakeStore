package com.example.fakestore.views.categories.Categories

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fakestore.components.CardCart
import com.example.fakestore.components.CardProducts
import com.example.fakestore.components.CardProductsDetails
import com.example.fakestore.components.DrawerContent
import com.example.fakestore.components.MainTopBar
import com.example.fakestore.viewModel.CategoriesViewModel
import com.example.fakestore.viewModel.LoginViewModel
import com.example.fakestore.viewModel.ProductsViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

//Componente para visualizar los productos de la categoria Electronics.
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Electronics(
    nameCategory: String,
    loginViewModel: LoginViewModel,
    navController: NavController,
    categoriesViewModel: CategoriesViewModel,
){
    //Se crea el drawer.
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    //Se obtiene la lista de productos de la categoria Electronics.
    LaunchedEffect(Unit) {
        categoriesViewModel.getProductsByCategory(nameCategory)
    }

    //Se obtiene la lista de productos de la categoria Electronics.
    val products by categoriesViewModel.products
        .map { it[nameCategory] ?: emptyList() }
        .collectAsState(initial = emptyList())

    //Se muestra el drawer.
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
                MainTopBar(
                    title = nameCategory,
                    showBackButton = false,
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onClickCart = {
                        navController.navigate("ShoppingCartView")
                    }
                )
            },
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
            ) {
                items(products){ product ->
                    CardCart(product = product){
                        navController.navigate("ProductDetailsView/${product.id}")
                    }
                    Text(
                        text = product.title,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }
        }
    }
}