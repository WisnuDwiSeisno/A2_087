package com.example.tugasakhirpam.ui.customwidget


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.tugasakhirpam.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CostumeTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    onRefresh: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color(0xFF3E2723), // Dark brown for text
                    fontFamily = FontFamily.Serif
                )
            )
        },
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.refresh), // Custom refresh icon
                contentDescription = "Refresh",
                tint = Color(0xFF4CAF50), // Green pastel
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onRefresh() }
            )
        },
        modifier = modifier.background(Color(0xFFFFF3E0)), // Creamy background
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(id = R.drawable.back), // Custom back icon
                        contentDescription = "Back",
                        tint = Color(0xFF1976D2), // Blue pastel
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    )
}
