package com.example.tugasakhirpam.ui.view.kategori

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
import com.example.tugasakhirpam.viewmodel.kategori.InsertKategoriUiEvent
import com.example.tugasakhirpam.viewmodel.kategori.UpdateKategoriViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object DestinasiUpdateKategori : DestinasiNavigasi {
    override val route = "update_kategori/{id}"
    override val titleRes: String = "Update Kategori" // Implementasi titleRes
    const val ID_KATEGORI = "idKategori"
    val routeWithArgs = "update_kategori/{$ID_KATEGORI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKategoriView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Update Kategori",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { innerPadding ->
        // Tampilan Form untuk Update Kategori
        UpdateKategoriFormBody(
            kategoriUiEvent = viewModel.updateUiState.insertUiEvent,
            onKategoriValueChange = viewModel::updateKategoriState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateKategori()
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
fun UpdateKategoriFormBody(
    kategoriUiEvent: InsertKategoriUiEvent,
    onKategoriValueChange: (InsertKategoriUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Nama Kategori
        OutlinedTextField(
            value = kategoriUiEvent.nama_kategori,
            onValueChange = { onKategoriValueChange(kategoriUiEvent.copy(nama_kategori = it)) },
            label = { Text("Nama Kategori") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Deskripsi Kategori
        OutlinedTextField(
            value = kategoriUiEvent.deskripsi_kategori,
            onValueChange = { onKategoriValueChange(kategoriUiEvent.copy(deskripsi_kategori = it)) },
            label = { Text("Deskripsi Kategori") },
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


