package com.example.fakestore.views.products

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fakestore.components.CardProducts
import com.example.fakestore.components.DrawerContent
import com.example.fakestore.components.MainTopBar
import com.example.fakestore.viewModel.CategoriesViewModel
import com.example.fakestore.viewModel.LoginViewModel
import com.example.fakestore.viewModel.ProductsViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductsView(
    productsViewModel: ProductsViewModel,
    categoriesViewModel: CategoriesViewModel,
    loginViewModel: LoginViewModel,
    navController: NavController
) {
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
                MainTopBar(
                    title = "Fake Store",
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
                    .padding(paddingValues = it)
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        text = "Productos Destacados",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    ContentProductsView(
                        productsViewModel,
                        navController
                    )
                }
                item {
                    Text(
                        text = "Women's clothing",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    ContentProductsViewByCategory(
                        categoriesViewModel = categoriesViewModel,
                        navController = navController,
                        category = "women's clothing"
                    )
                }
                item {
                    Text(
                        text = "Men's Clothing",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    ContentProductsViewByCategory(
                        categoriesViewModel = categoriesViewModel,
                        navController = navController,
                        category = "men's clothing"
                    )
                }
                item {
                    Text(
                        text = "Jewelery",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    ContentProductsViewByCategory(
                        categoriesViewModel = categoriesViewModel,
                        navController = navController,
                        category = "jewelery"
                    )
                }
                item {
                    Text(
                        text = "Electronics",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    ContentProductsViewByCategory(
                        categoriesViewModel = categoriesViewModel,
                        navController = navController,
                        category = "electronics"
                    )
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ContentProductsView(
    productsViewModel: ProductsViewModel,
    navController: NavController,
){
    val products by productsViewModel.products.collectAsState(emptyList())

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(products){ product ->
            Column(
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ){
                    CardProducts(product) {
                        navController.navigate("ProductDetailsView/${product.id}")
                    }
                    Surface(
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.TopStart),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "$${product.price}",
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContentProductsViewByCategory(
    categoriesViewModel: CategoriesViewModel,
    navController: NavController,
    category: String
){

    LaunchedEffect(Unit) {
        categoriesViewModel.getProductsByCategory(category)
    }

    val products by categoriesViewModel.products
        .map { it[category] ?: emptyList() }
        .collectAsState(initial = emptyList())

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(products){ product ->
            Column(
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ){
                    CardProducts(product) {
                        navController.navigate("ProductDetailsView/${product.id}")
                    }
                    Surface(
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.TopStart),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "$${product.price}",
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}