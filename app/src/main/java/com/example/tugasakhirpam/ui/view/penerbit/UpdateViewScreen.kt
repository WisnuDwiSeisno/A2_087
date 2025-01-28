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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.viewmodel.buku.BukuUiEvent
import com.example.tugasakhirpam.viewmodel.buku.DropdownItem
import kotlinx.coroutines.launch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tugasakhirpam.navigation.DestinasiNavigasi
import com.example.tugasakhirpam.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.viewmodel.buku.UpdateViewModel
import com.example.tugasakhirpam.viewmodel.penerbit.InsertPenerbitUiEvent
import com.example.tugasakhirpam.viewmodel.penerbit.UpdatePenerbitViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object DestinasiUpdatePenerbit : DestinasiNavigasi {
    override val route = "update_penerbit/{id}"
    override val titleRes: String = "Update Penerbit" // Implementasi titleRes
    const val ID_KATEGORI = "idPenerbit"
    val routeWithArgs = "update_penerbit/{$ID_KATEGORI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePenerbitView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdatePenerbitViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Update Penerbit",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { innerPadding ->
        // Tampilan Form untuk Update Penerbit
        UpdatePenerbitFormBody(
            penerbitUiEvent = viewModel.updateUiState.insertUiEvent,
            onPenerbitValueChange = viewModel::updatePenerbitState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePenerbit()
                    delay(600) // Tunggu sebentar setelah proses update
                    withContext(Dispatchers.Main) {
                        onNavigate() // Navigasi setelah berhasil update
                    }
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
fun UpdatePenerbitFormBody(
    penerbitUiEvent: InsertPenerbitUiEvent,
    onPenerbitValueChange: (InsertPenerbitUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Nama Penerbit
        OutlinedTextField(
            value = penerbitUiEvent.nama_penerbit,
            onValueChange = { onPenerbitValueChange(penerbitUiEvent.copy(nama_penerbit = it)) },
            label = { Text("Nama Penerbit") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Deskripsi Penerbit
        OutlinedTextField(
            value = penerbitUiEvent.alamat_penerbit,
            onValueChange = { onPenerbitValueChange(penerbitUiEvent.copy(alamat_penerbit = it)) },
            label = { Text("Deskripsi Penerbit") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false
        )
        // Deskripsi Penerbit
        OutlinedTextField(
            value = penerbitUiEvent.telepon_penerbit,
            onValueChange = { onPenerbitValueChange(penerbitUiEvent.copy(telepon_penerbit = it)) },
            label = { Text("Deskripsi Penerbit") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false
        )

        // Tombol Simpan
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}


