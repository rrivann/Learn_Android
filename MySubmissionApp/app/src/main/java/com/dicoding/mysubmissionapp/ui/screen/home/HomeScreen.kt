package com.dicoding.mysubmissionapp.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.mysubmissionapp.R
import com.dicoding.mysubmissionapp.core.di.Injection
import com.dicoding.mysubmissionapp.core.model.Crypto
import com.dicoding.mysubmissionapp.ui.ViewModelFactory
import com.dicoding.mysubmissionapp.ui.common.UiState
import com.dicoding.mysubmissionapp.ui.components.CryptoItem
import com.dicoding.mysubmissionapp.ui.components.MySearchBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateToDetail: (Crypto) -> Unit
) {
    Column {
        val query by viewModel.query
        MySearchBar(query = query, onQueryChange = viewModel::search)
        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.drawBehind {
                                drawCircle(
                                    Color.Blue,
                                    radius = size.width / 2 - 5.dp.toPx() / 2,
                                    style = Stroke(5.dp.toPx())
                                )
                            },
                            color = Color.Blue,
                            strokeWidth = 5.dp
                        )
                    }
                    viewModel.getAllCrypto()
                }

                is UiState.Success -> {
                    if (uiState.data.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(stringResource(R.string.data_not_found))
                        }
                    } else {
                        HomeContent(
                            crypto = uiState.data,
                            navigateToDetail = navigateToDetail
                        )
                    }

                }

                is UiState.Error -> {}
            }
        }
    }
}


@Composable
fun HomeContent(
    crypto: List<Crypto>,
    navigateToDetail: (Crypto) -> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        items(crypto) { data ->
            CryptoItem(
                image = data.photo,
                title = data.name,
                modifier = Modifier.clickable {
                    navigateToDetail(data)
                }
            )
        }
    }
}
