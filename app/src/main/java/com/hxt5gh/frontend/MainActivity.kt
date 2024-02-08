package com.hxt5gh.frontend

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModelStore
import androidx.navigation.compose.rememberNavController

import com.hxt5gh.frontend.ui.routes.RootNavigationGraph
import com.hxt5gh.frontend.ui.theme.FrontEndTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontEndTheme {

                val navController = rememberNavController()

                Log.d("debug", "onCreate: towardRood")
                RootNavigationGraph(navController = navController)


            }
        }
    }
}

