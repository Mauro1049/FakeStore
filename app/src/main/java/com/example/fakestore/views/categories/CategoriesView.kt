package com.example.fakestore.views.categories

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fakestore.components.CardCategories
import com.example.fakestore.components.DrawerContent
import com.example.fakestore.components.MainTopBar
import com.example.fakestore.viewModel.CategoriesViewModel
import com.example.fakestore.viewModel.LoginViewModel
import kotlinx.coroutines.launch

//Componente que muestra las categorias de productos disponibles en la tienda de productos.
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoriesView(
    categoriesViewModel: CategoriesViewModel,
    navController: NavController,
    loginViewModel: LoginViewModel
){
    //Variables para el manejo del drawer y el manejo de corutinas y el contexto.
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    //ModelNavigationDrawer para el manejo del drawer.
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
                    title = "Categories",
                    showBackButton = true,
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onClickBackButton = { navController.popBackStack() },
                    onClickCart = {
                        navController.navigate("ShoppingCartView")
                    }
                )
            },
        ) {
            //Contenido de la vista de categorias.
            ContentCategoriesView(categoriesViewModel, it, navController)
        }
    }
}

//Contenido de la vista de categorias.
@Composable
fun ContentCategoriesView(
    categoriesViewModel: CategoriesViewModel,
    paddingValues: PaddingValues,
    navController: NavController
){
    //Se obtiene la lista de categorias desde el viewModel.
    val categories by categoriesViewModel.categories.collectAsState()

    //Si la lista de categorias esta vacia, se muestra un texto cargando.
    if(categories.isEmpty()){
        Text(text = "Cargando")
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            //Se muestra la lista de categorias en una grilla.
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                //Se muestra cada categoria en un card.
                items(categories){ category ->
                    CardCategories(category){ categoryName ->
                        Log.d("Category", "Navigating to CategoryDetailsView with: $categoryName")
                        navController.navigate("CategoryDetailsView/$categoryName")
                    }
                }
            }
        }
    }
}
