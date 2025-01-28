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
import com.example.tugasakhirpam.viewmodel.penulis.InsertPenulisUiEvent
import com.example.tugasakhirpam.viewmodel.penulis.UpdatePenulisViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object DestinasiUpdatePenulis : DestinasiNavigasi {
    override val route = "update_penulis/{id}"
    override val titleRes: String = "Update Penulis" // Implementasi titleRes
    const val ID_KATEGORI = "idPenulis"
    val routeWithArgs = "update_penulis/{$ID_KATEGORI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePenulisView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdatePenulisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Update Penulis",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { innerPadding ->
        // Tampilan Form untuk Update Penulis
        UpdatePenulisFormBody(
            penulisUiEvent = viewModel.updateUiState.insertUiEvent,
            onPenulisValueChange = viewModel::updatePenulisState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePenulis()
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
fun UpdatePenulisFormBody(
    penulisUiEvent: InsertPenulisUiEvent,
    onPenulisValueChange: (InsertPenulisUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Nama Penulis
        OutlinedTextField(
            value = penulisUiEvent.nama_penulis,
            onValueChange = { onPenulisValueChange(penulisUiEvent.copy(nama_penulis = it)) },
            label = { Text("Nama Penulis") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Deskripsi Penulis
        OutlinedTextField(
            value = penulisUiEvent.biografi,
            onValueChange = { onPenulisValueChange(penulisUiEvent.copy(biografi = it)) },
            label = { Text("Deskripsi Penulis") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false
        )
        // Deskripsi Penulis
        OutlinedTextField(
            value = penulisUiEvent.kontak,
            onValueChange = { onPenulisValueChange(penulisUiEvent.copy(kontak = it)) },
            label = { Text("Deskripsi Penulis") },
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


