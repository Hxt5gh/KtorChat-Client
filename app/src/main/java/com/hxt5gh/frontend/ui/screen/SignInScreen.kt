package com.hxt5gh.frontend.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hxt5gh.frontend.R
import com.hxt5gh.frontend.presentation.signin.SignInState
import com.hxt5gh.frontend.presentation.signin.SignInViewModel

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick : () -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect(key1 = state.errorMessage) {
        state.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }
    val viewModel : SignInViewModel = hiltViewModel()

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 150.dp)
        ) {

            if (viewModel.loadingStatus.value.isLoading){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Blue,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            Button(shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(8.dp).align(Alignment.BottomCenter),
                onClick = {
                onSignInClick()
            }) {
                Row(horizontalArrangement = Arrangement.SpaceEvenly , verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp),
                        painter = painterResource(id = R.drawable.googel),
                        contentDescription = ""
                    )
                    Text(text = "With Google")
                }

            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun SignInScreenPrev() {
    SignInScreen(state = SignInState() ) {
        
    }
}