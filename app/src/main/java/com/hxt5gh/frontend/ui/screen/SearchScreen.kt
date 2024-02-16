package com.hxt5gh.frontend.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.hxt5gh.frontend.presentation.chat.SearchScreenViewModel
import com.hxt5gh.frontend.ui.SearchUserView
import com.hxt5gh.frontend.ui.TextChatInput
import com.hxt5gh.frontend.ui.UserSearchInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController , onClick : (User) -> Unit) {
    var textState by remember { mutableStateOf("") }
    var enable by remember { mutableStateOf(false) }
    var items = remember { mutableStateListOf("harry" ,"james" ,"potter" , "lily" ) }
    val searchScreenViewModel : SearchScreenViewModel = hiltViewModel()

    Scaffold(
         /*
        topBar = {

            Row {
                Spacer(modifier = Modifier.size(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(CutCornerShape(0.dp))
                        .padding(start = 18.dp, end = 18.dp)
                ) {
                    UserSearchInput(
                        value = textState,
                        onTextChange = {
                            textState = it
                        }
                    )
                }

            }
        }
          */
    ) {
        SearchBar(
            query = textState,
            onQueryChange ={
                textState = it
                if (textState.length >=1){
                    searchScreenViewModel.getSearchedUser(textState)
                }
            } ,
            onSearch = {
                enable =false
            },
            active = enable ,
            onActiveChange ={
                enable = it
            },
            placeholder = { Text(text = "Search") },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "null")},
            trailingIcon = {
                if (enable){
                    Icon(
                        modifier = Modifier.clickable
                        {
                            if (textState.isNotEmpty())
                            {
                                textState = ""
                            }else
                            {
                                enable = false
                            }
                        },
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "null")
                }

            }
        ){
            searchScreenViewModel.userList.forEach{

                    SearchUserView(image = it.profileUri,userId = it.userId , userName = it.userName , displayName =  it.displayName){
                        val userId = it.id
                        val displayName = it.name
                        val pic = it.profileImage

                        onClick(
                           User(
                               id = userId,
                               name = displayName,
                               profileImage = pic,
                               lastMessage = "",
                               timestamp = 0
                           )
                       )
                    }

            }
        }
        Surface(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colorScheme.background),
        ) {


        }
    }
}

@Preview(showBackground =  true , showSystemUi = true)
@Composable
fun SearchScreenPrev() {
    SearchScreen(navController = rememberNavController(), onClick = {})
}

data class UserInfo(
    val userid: String,
    val displayName: String,
    val profileImage: String?,
)