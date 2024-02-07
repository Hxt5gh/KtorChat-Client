package com.hxt5gh.frontend.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    Scaffold(

    ) {
        Surface(
            modifier = Modifier.padding(it)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)) {

            }
        }
    }
}

@Preview(showBackground =  true , showSystemUi = true)
@Composable
fun SearchScreenPrev() {
    SearchScreen()
}