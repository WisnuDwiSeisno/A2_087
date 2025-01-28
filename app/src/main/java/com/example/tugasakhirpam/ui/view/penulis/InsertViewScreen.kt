package com.example.tugasakhirpam.ui.view.penulis

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.navigation.DestinasiNavigasi
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.viewmodel.buku.BukuUiEvent
import com.example.tugasakhirpam.viewmodel.buku.DropdownItem
import com.example.tugasakhirpam.viewmodel.buku.InsertUiState
import com.example.tugasakhirpam.viewmodel.buku.InsertViewModel
import com.example.tugasakhirpam.viewmodel.penulis.InsertPenulisUiEvent
import com.example.tugasakhirpam.viewmodel.penulis.InsertPenulisUiState
import com.example.tugasakhirpam.viewmodel.penulis.InsertPenulisViewModel
import kotlinx.coroutines.launch
object DestinasiPenulisEntry : DestinasiNavigasi {
    override val route = "penulis_entry"
    override val titleRes = "Entry Penulis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPenulisScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPenulisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiPenulisEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        InsertPenulisBody(
            insertUiState = viewModel.uiState,
            onPenulisValueChange = viewModel::updateInsertPenulisState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPenulis()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun InsertPenulisBody(
    insertUiState: InsertPenulisUiState,
    onPenulisValueChange: (InsertPenulisUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPenulis(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPenulisValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPenulis(
    insertUiEvent: InsertPenulisUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPenulisUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.nama_penulis,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_penulis = it)) },
            label = { Text("Nama Penulis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.biografi,
            onValueChange = { onValueChange(insertUiEvent.copy(biografi = it)) },
            label = { Text("Biografi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false,
            maxLines = 3
        )
        OutlinedTextField(
            value = insertUiEvent.kontak,
            onValueChange = { onValueChange(insertUiEvent.copy(kontak = it)) },
            label = { Text("Kontak") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false,
            maxLines = 3
        )
    }
}
