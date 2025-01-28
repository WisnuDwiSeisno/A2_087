package com.example.tugasakhirpam.ui.view.penerbit

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
import com.example.tugasakhirpam.viewmodel.penerbit.InsertPenerbitUiEvent
import com.example.tugasakhirpam.viewmodel.penerbit.InsertPenerbitUiState
import com.example.tugasakhirpam.viewmodel.penerbit.InsertPenerbitViewModel
import kotlinx.coroutines.launch
object DestinasiPenerbitEntry : DestinasiNavigasi {
    override val route = "penerbit_entry"
    override val titleRes = "Entry Penerbit"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPenerbitScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPenerbitViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiPenerbitEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        InsertPenerbitBody(
            insertUiState = viewModel.uiState,
            onPenerbitValueChange = viewModel::updateInsertPenerbitState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPenerbit()
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
fun InsertPenerbitBody(
    insertUiState: InsertPenerbitUiState,
    onPenerbitValueChange: (InsertPenerbitUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPenerbit(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPenerbitValueChange,
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
fun FormInputPenerbit(
    insertUiEvent: InsertPenerbitUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPenerbitUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.nama_penerbit,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_penerbit = it)) },
            label = { Text("Nama Penerbit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.telepon_penerbit,
            onValueChange = { onValueChange(insertUiEvent.copy(telepon_penerbit = it)) },
            label = { Text("Telepon Penerbit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false,
            maxLines = 3
        )
        OutlinedTextField(
            value = insertUiEvent.alamat_penerbit,
            onValueChange = { onValueChange(insertUiEvent.copy(alamat_penerbit = it)) },
            label = { Text("Alamat Penerbit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false,
            maxLines = 3
        )
    }
}
