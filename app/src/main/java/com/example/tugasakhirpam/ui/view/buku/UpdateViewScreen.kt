package com.example.tugasakhirpam.ui.view.buku

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Buku"
    const val ID_BUKU = "id_buku"
    val routesWithArg = "$route/{$ID_BUKU}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    // Mengambil data dropdown dan buku saat tampilan dimuat
    LaunchedEffect(Unit) {
        viewModel.fetchDropdownData()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { innerPadding ->
        // Tampilan Form untuk Update Buku
        UpdateFormBody(
            bukuUiEvent = viewModel.updateUiState.bukuUiEvent,
            onBukuValueChange = viewModel::updateBukuState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateBuku()
                    delay(600)  // Menunggu sebentar setelah update
                    withContext(Dispatchers.Main) {
                        onNavigate()  // Navigasi setelah berhasil update
                    }
                }
            },
            kategoriList = viewModel.updateUiState.kategoriList,
            penulisList = viewModel.updateUiState.penulisList,
            penerbitList = viewModel.updateUiState.penerbitList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun UpdateFormBody(
    bukuUiEvent: BukuUiEvent,
    onBukuValueChange: (BukuUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    kategoriList: List<DropdownItem>,
    penulisList: List<DropdownItem>,
    penerbitList: List<DropdownItem>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Nama Buku
        OutlinedTextField(
            value = bukuUiEvent.nama_buku,
            onValueChange = { onBukuValueChange(bukuUiEvent.copy(nama_buku = it)) },
            label = { Text("Nama Buku") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Deskripsi Buku
        OutlinedTextField(
            value = bukuUiEvent.deskripsi_buku,
            onValueChange = { onBukuValueChange(bukuUiEvent.copy(deskripsi_buku = it)) },
            label = { Text("Deskripsi Buku") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Tanggal Terbit
        OutlinedTextField(
            value = bukuUiEvent.tanggal_terbit,
            onValueChange = { onBukuValueChange(bukuUiEvent.copy(tanggal_terbit = it)) },
            label = { Text("Tanggal Terbit") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Status Buku
        OutlinedTextField(
            value = bukuUiEvent.status_buku,
            onValueChange = { onBukuValueChange(bukuUiEvent.copy(status_buku = it)) },
            label = { Text("Status Buku") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Dropdown untuk Kategori
        SimpleDropdown(
            label = "Kategori",
            items = kategoriList,
            selectedItem = bukuUiEvent.id_kategori,
            onItemSelected = { selectedId ->
                onBukuValueChange(bukuUiEvent.copy(id_kategori = selectedId))
            }
        )

        // Dropdown untuk Penulis
        SimpleDropdown(
            label = "Penulis",
            items = penulisList,
            selectedItem = bukuUiEvent.id_penulis,
            onItemSelected = { selectedId ->
                onBukuValueChange(bukuUiEvent.copy(id_penulis = selectedId))
            }
        )

        // Dropdown untuk Penerbit
        SimpleDropdown(
            label = "Penerbit",
            items = penerbitList,
            selectedItem = bukuUiEvent.id_penerbit,
            onItemSelected = { selectedId ->
                onBukuValueChange(bukuUiEvent.copy(id_penerbit = selectedId))
            }
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

@Composable
fun SimpleDropdown(
    label: String,
    items: List<DropdownItem>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = items.find { it.id == selectedItem }?.name ?: "",
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.name) },
                    onClick = {
                        onItemSelected(item.id)
                        expanded = false
                    }
                )
            }
        }
    }
}
