package com.hxt5gh.frontend.ui

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hxt5gh.frontend.R
import com.hxt5gh.frontend.data.remote.userDetail.UserDataDto
import com.hxt5gh.frontend.ui.screen.User

@Composable
fun ChatViewItem(
    image: String,
    name: String,
    lastMessage: String = "",
    time: String = "",
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .heightIn()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (image.isBlank()){
            Image(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.blankpic),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
                Text(
                    text = lastMessage,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
            }

        }else
        {
            AsyncImage(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape),
                model = image,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
                Text(
                    text = lastMessage,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
            }
        }

    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatViewItemPrev() {
  //  ChatViewItem(R.drawable.cap, name = "", lastMessage = "", time = "", onClick = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextChatInput(modifier: Modifier = Modifier, value: String, onTextChange: (String) -> Unit) {
    val localFocusManager = LocalFocusManager.current

    TextField(value = value,
        onValueChange = {
            Log.d("debug", "TextChatInput: ${it}")
            onTextChange(it)
        },
        modifier = modifier,
        placeholder = { Text(text = "Message") },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions { localFocusManager.clearFocus() }
    )
}


@Preview(showBackground = true, showSystemUi = true, heightDp = 100)
@Composable
fun TextChatInputPrev() {
    TextChatInput(value = "", onTextChange = {})
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSearchInput(modifier: Modifier = Modifier, value: String, onTextChange: (String) -> Unit) {

    TextField(value = value,
        onValueChange = {
            Log.d("debug", "TextChatInput: ${it}")
            onTextChange(it)
        },
        modifier = modifier,
        placeholder = { Text(text = "Search") },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }

    )
}


@Composable
fun SearchUserView(image: String,userId : String , userName: String, displayName: String , onClick: (User) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn()
            .clickable {
                    onClick(
                        User(
                            id = userId,
                            name = userName,
                            profileImage = image,
                            lastMessage ="" ,
                            timestamp =0
                        )
                    )
            }
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
    ) {

        if (image != "") {
            AsyncImage(model = image,
                contentDescription = "",
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape) ,
                contentScale = ContentScale.Crop)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = userName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
                Text(
                    text = displayName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
            }
        }
        else {
            Image(
                painter = painterResource(id = R.drawable.blankpic),
                contentDescription = "", modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = userName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
                Text(
                    text = displayName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
            }
        }
       // Divider(color = MaterialTheme.colorScheme.onBackground)
    }

}


@Preview()
@Composable
fun SearchUserViewPrev() {
 //   SearchUserView(image = "", userName = "Hxt5gh", displayName = "Harpreet")
}
