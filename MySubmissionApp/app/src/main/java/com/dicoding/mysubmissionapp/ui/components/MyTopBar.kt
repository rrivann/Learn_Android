package com.dicoding.mysubmissionapp.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dicoding.mysubmissionapp.R

@Composable
fun MyTopBar(onProfileClick: () -> Unit, onFavoriteClick: () -> Unit) {
    TopAppBar(
        actions = {
            IconButton(onClick = { onFavoriteClick() }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.favorite)
                )
            }
            IconButton(onClick = {
                onProfileClick()
            }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(R.string.profile)
                )
            }
        },
        title = {
            Text(stringResource(R.string.app_name))
        },
    )
}