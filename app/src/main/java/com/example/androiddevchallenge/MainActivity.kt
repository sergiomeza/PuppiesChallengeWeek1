/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.androiddevchallenge.ui.theme.MyTheme
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.data.viewmodel.FirestoreViewModel
import com.example.androiddevchallenge.ui.detail.DetailScreen
import com.example.androiddevchallenge.ui.home.HomeScreen
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

class MainActivity : AppCompatActivity() {
    private val fireStoreViewModel by viewModels<FirestoreViewModel>()

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyTheme {
                ProvideWindowInsets {
                    MyApp(firestoreViewModel = fireStoreViewModel)
                }
            }
        }
    }
}

// Start building your app here!
@ExperimentalStdlibApi
@Composable
fun MyApp(firestoreViewModel: FirestoreViewModel) {
    val navController = rememberNavController()
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = "HomeScreen",
        builder = {
            composable("HomeScreen") {
                HomeScreen(
                    navController = navController,
                    firestoreViewModel = firestoreViewModel)
            }
            composable(
                "puppyScreen/{puppyId}",
                arguments = listOf(navArgument("puppyId") {
                    type = NavType.StringType
                })
            ) {
                it.arguments?.getString("puppyId").run {
                    firestoreViewModel.puppyItems.value?.first { puppyObj -> puppyObj.id == this ?: 0 }.let {
                        puppyFound ->
                        if (puppyFound != null){
                            DetailScreen(navController = navController, puppy = puppyFound)
                        }
                    }
                }
            }
        }
    )
}