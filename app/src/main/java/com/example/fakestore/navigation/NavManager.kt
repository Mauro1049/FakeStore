package com.example.fakestore.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fakestore.viewModel.CategoriesViewModel
import com.example.fakestore.viewModel.LoginViewModel
import com.example.fakestore.viewModel.ProductsViewModel
import com.example.fakestore.viewModel.ShoppingCartViewModel
import com.example.fakestore.viewModel.UserViewModel
import com.example.fakestore.views.categories.CategoriesView
import com.example.fakestore.views.Login.LoginView
import com.example.fakestore.views.shoppingCart.ShoppingCartView
import com.example.fakestore.views.UserPerfil.UserPerfilView
import com.example.fakestore.views.categories.Categories.Electronics
import com.example.fakestore.views.categories.Categories.Jewelery
import com.example.fakestore.views.categories.Categories.Mensclothing
import com.example.fakestore.views.categories.Categories.WomensClothing
import com.example.fakestore.views.products.ProductDetailView
import com.example.fakestore.views.products.ProductsView

@Composable
fun NavManager(
    loginViewModel: LoginViewModel,
    productsViewModel: ProductsViewModel,
    categoriesViewModel: CategoriesViewModel,
    userViewModel: UserViewModel,
    shoppingCartViewModel: ShoppingCartViewModel
    ){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "LoginView") {
        composable("LoginView") {
            LoginView(loginViewModel, navController)
        }
        //
        composable("ProductsView/{token}", arguments = listOf(
            navArgument("token") { type = NavType.StringType }
        )) {
            val token = it.arguments?.getString("token") ?: ""
            ProductsView(productsViewModel, categoriesViewModel, loginViewModel, navController)
        }
        composable("ProductDetailsView/{id}", arguments = listOf(
            navArgument("id") { type = NavType.IntType }
        )) {
            val id = it.arguments?.getInt("id") ?: 0
            ProductDetailView(navController, productsViewModel, id, loginViewModel)
        }
        composable("CategoriesView") {
            CategoriesView(categoriesViewModel, navController, loginViewModel)
        }
        composable("UserPerfilView") {
            UserPerfilView(userViewModel, 1, loginViewModel, navController,)
        }
        composable("CategoryDetailsView/{name}", arguments = listOf(
            navArgument("name") { type = NavType.StringType }
        )) {
            val name = it.arguments?.getString("name") ?: ""
            Log.d("Category", "Received category: $name")

            when (name) {
                "electronics" -> Electronics(
                    name,
                    loginViewModel,
                    navController,
                    categoriesViewModel
                )

                "jewelery" -> Jewelery(name, loginViewModel, navController, categoriesViewModel)
                "men's clothing" -> Mensclothing(
                    name,
                    loginViewModel,
                    navController,
                    categoriesViewModel
                )

                "women's clothing" -> WomensClothing(
                    name,
                    loginViewModel,
                    navController,
                    categoriesViewModel
                )
            }
        }
        composable("ShoppingCartView") {
            ShoppingCartView(
                shoppingCartViewModel,
                productsViewModel,
                navController,
                loginViewModel
            )
        }
    }
}