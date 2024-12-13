package com.example.fakestore.views.shoppingCart

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fakestore.components.CardCart
import com.example.fakestore.components.DrawerContent
import com.example.fakestore.components.MainTopBar
import com.example.fakestore.viewModel.LoginViewModel
import com.example.fakestore.viewModel.ProductsViewModel
import com.example.fakestore.viewModel.ShoppingCartViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShoppingCartView(
    shoppingCartViewModel: ShoppingCartViewModel,
    productsViewModel: ProductsViewModel,
    navController: NavController,
    loginViewModel: LoginViewModel
    ){

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        shoppingCartViewModel.getCartByUser(1)
    }

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
                    title = "Fake Store",
                    showBackButton = true,
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onClickCart = {
                        navController.navigate("ShoppingCartView")
                    },
                    onClickBackButton = {
                        navController.popBackStack()
                    }
                )
            },
        ) {
            ContentShoppingCartView(
                shoppingCartViewModel,
                productsViewModel,
                it,
                navController
            )
        }
    }
}

@Composable
fun ContentShoppingCartView(
    shoppingCartViewModel: ShoppingCartViewModel,
    productsViewModel: ProductsViewModel,
    pad: PaddingValues,
    navController: NavController
){

    val cart by shoppingCartViewModel.cart.collectAsState()
    val products by productsViewModel.products.collectAsState()

    Column(
        modifier = Modifier
            .padding(pad)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(products){ product ->
                CardCart(product){
                    navController.navigate("ProductDetailsView/${product.id}")
                }
            }
        }
    }
}