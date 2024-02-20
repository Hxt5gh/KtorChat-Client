package com.hxt5gh.frontend.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hxt5gh.frontend.R
import com.hxt5gh.frontend.ui.screen.User
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun ChatViewItem(
    image: String,
    name: String,
    lastMessage: String = "",
    time: Long? = 0,
    onClick: () -> Unit
) {



    Column {
        Row(
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .heightIn()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (image.isBlank()) {
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
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    modifier = Modifier.padding(end = 5.dp),
                    text = if (time != null) formatTime(time) else "",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )


            } else {
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
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    modifier = Modifier.padding(end = 5.dp),
                    text =if (time != null) formatTime(time) else "",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
            }
        }
        Divider(modifier = Modifier.fillMaxWidth())
    }

}


@Preview(showBackground = true)
@Composable
fun ChatViewItemPrev() {
    ChatViewItem("", name = "Harry", lastMessage = "hey?", time = 1613851800000, onClick = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextChatInput(modifier: Modifier = Modifier, value: String, onTextChange: (String) -> Unit) {
    val localFocusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    TextField(value = value,
        onValueChange = {
            Log.d("debug", "TextChatInput: ${it}")
            onTextChange(it)
        },
        modifier = Modifier
            .heightIn(min = 50.dp, max = 100.dp),
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
            imeAction = ImeAction.Done
        ),
    )
}


@Preview(showBackground = true)
@Composable
fun TextChatInputPrev() {
    // TextChatInput(value = "", onTextChange = {})
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
fun SearchUserView(
    image: String,
    userId: String,
    userName: String,
    displayName: String,
    onClick: (User) -> Unit
) {
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
                        lastMessage = "",
                        timestamp = 0
                    )
                )
            }
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
    ) {

        if (image != "") {
            AsyncImage(
                model = image,
                contentDescription = "",
                modifier = Modifier
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
        } else {
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


@Composable
fun profilePicView(modifierc: Modifier = Modifier, image: String, onClick: () -> Unit) {
    if (image == "" || image == null) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Image(
                painter = painterResource(id = R.drawable.blankpic),
                contentDescription = "",
                modifier = modifierc,
                contentScale = ContentScale.Crop
            )
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = -10.dp, y = -10.dp)
                    .clickable {
                        onClick()
                    }
            )
        }
    } else {
        Box(contentAlignment = Alignment.BottomEnd) {
            AsyncImage(
                model = image,
                contentDescription = "",
                modifier = modifierc,
                contentScale = ContentScale.Crop
            )
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = -10.dp, y = -10.dp)
                    .clickable {
                        onClick()
                    }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun profilePicViewPrev() {
    profilePicView(modifierc = Modifier, "", onClick = {})
    // profilePicView( "" , onClick = {})
}

@Composable
fun profileInputFieldView(
    inputType: String,
    inputValue: String,
    onValueChange: (String) -> Unit,
) {
    var isReadable by remember {
        mutableStateOf(true)
    }
    var text by remember {
        mutableStateOf(inputValue)
    }

    Column {
        Text(
            text = inputType,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 5.dp)
        )
        TextField(
            modifier = Modifier
                .heightIn(min = 50.dp, max = 50.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (text.trim() == "") {
                        text = inputValue
                        isReadable = !isReadable
                    }
                },
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = MaterialTheme.colorScheme.onBackground
            ),
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            maxLines = 1,
            singleLine = true,
            readOnly = isReadable,
            trailingIcon = {
                if (isReadable) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "edit",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                isReadable = !isReadable
                            }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "edit",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                isReadable = !isReadable
                            }
                    )
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun profileInputFieldViewPrev() {
    profileInputFieldView(inputType = "Name", inputValue = "Harry", onValueChange = {})
}


@Composable
fun senderChatBubble(message: String , isMe : Boolean = true) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(start = if (isMe) 55.dp else 4.dp , end = if (isMe) 4 .dp else 55.dp , top = 4.dp , bottom = 4.dp),
        contentAlignment = if (isMe) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape
                        (
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (isMe) 48f else 0f,
                        bottomEnd = if (isMe) 0f else 48f
                    )
                )
                .background(if (isMe) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary)
                .padding(16.dp)
        ) {
            Text(text = message , color = if (isMe)MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onTertiary)
        }
    }

}

@Preview(showBackground = true , heightDp = 500 , widthDp = 100)
@Composable
fun senderChatBubblePrev() {
    senderChatBubble(message = "hi my name is harry hi hi my name is harry ")
}

fun formatTime(millis: Long): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis
    return sdf.format(calendar.time)
}