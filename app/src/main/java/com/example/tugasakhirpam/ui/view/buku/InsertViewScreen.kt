package com.example.tugasakhirpam.ui.view.buku

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DestinasiEntry : DestinasiNavigasi {
    override val route = "buku_entry"
    override val titleRes = "Entry Buku"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertViewScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Fetch dropdown data when the screen is initialized
    LaunchedEffect(Unit) {
        viewModel.fetchDropdownData()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            bukuUiState = viewModel.uiState,
            onBukuValueChange = viewModel::updateBukuState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertBuku()
                    navigateBack()
                }
            },
            kategoriList = viewModel.kategoriList.map { DropdownItem(it.id, it.name) }, // Adjust list of DropdownItem
            penulisList = viewModel.penulisList.map { DropdownItem(it.id, it.name) },
            penerbitList = viewModel.penerbitList.map { DropdownItem(it.id, it.name) },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    bukuUiState: InsertUiState,
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
        FormInput(
            bukuUiEvent = bukuUiState.bukuUiEvent,
            onValueChange = onBukuValueChange,
            kategoriList = kategoriList,
            penulisList = penulisList,
            penerbitList = penerbitList,
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
fun FormInput(
    bukuUiEvent: BukuUiEvent,
    kategoriList: List<DropdownItem>,
    penulisList: List<DropdownItem>,
    penerbitList: List<DropdownItem>,
    modifier: Modifier = Modifier,
    onValueChange: (BukuUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    var expandedKategori by remember { mutableStateOf(false) }
    var expandedPenulis by remember { mutableStateOf(false) }
    var expandedPenerbit by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Nama Buku
        OutlinedTextField(
            value = bukuUiEvent.nama_buku,
            onValueChange = { onValueChange(bukuUiEvent.copy(nama_buku = it)) },
            label = { Text("Nama Buku") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Deskripsi Buku
        OutlinedTextField(
            value = bukuUiEvent.deskripsi_buku,
            onValueChange = { onValueChange(bukuUiEvent.copy(deskripsi_buku = it)) },
            label = { Text("Deskripsi Buku") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Tanggal Terbit
        OutlinedTextField(
            value = bukuUiEvent.tanggal_terbit,
            onValueChange = {},
            label = { Text("Tanggal Terbit") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            enabled = false, // Non-editable, hanya lewat DatePicker
            singleLine = true,
            trailingIcon = {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Date")
            }
        )
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        val selectedDate = datePickerState.selectedDateMillis?.let { millis ->
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(millis))
                        } ?: ""
                        onValueChange(bukuUiEvent.copy(tanggal_terbit = selectedDate))
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        // Status Buku
        StatusDropdown(
            selectedStatus = bukuUiEvent.status_buku,
            onStatusSelected = { selectedStatus ->
                onValueChange(bukuUiEvent.copy(status_buku = selectedStatus))
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        )

        // Dropdown for Kategori
        SimpleDropdown(
            label = "Kategori",
            items = kategoriList,
            selectedItem = bukuUiEvent.id_kategori,
            onItemSelected = { selectedId ->
                onValueChange(bukuUiEvent.copy(id_kategori = selectedId))
            },
            expanded = expandedKategori,
            onExpandedChange = { expandedKategori = it }
        )

        // Dropdown for Penulis
        SimpleDropdown(
            label = "Penulis",
            items = penulisList,
            selectedItem = bukuUiEvent.id_penulis,
            onItemSelected = { selectedId ->
                onValueChange(bukuUiEvent.copy(id_penulis = selectedId))
            },
            expanded = expandedPenulis,
            onExpandedChange = { expandedPenulis = it }
        )

        // Dropdown for Penerbit
        SimpleDropdown(
            label = "Penerbit",
            items = penerbitList,
            selectedItem = bukuUiEvent.id_penerbit,
            onItemSelected = { selectedId ->
                onValueChange(bukuUiEvent.copy(id_penerbit = selectedId))
            },
            expanded = expandedPenerbit,
            onExpandedChange = { expandedPenerbit = it }
        )
    }
}

@Composable
fun SimpleDropdown(
    label: String,
    items: List<DropdownItem>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = items.find { it.id == selectedItem }?.name ?: "", // Display the name instead of ID
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpandedChange(true) }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.name) }, // Show name of the item
                    onClick = {
                        onItemSelected(item.id) // Use ID for selection
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

@Composable
fun StatusDropdown(
    selectedStatus: String,
    onStatusSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    val statusOptions = listOf("Tersedia", "Habis", "Dipesan")

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedStatus,
            onValueChange = {},
            label = { Text("Status Buku") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { if (enabled) expanded = true },
            readOnly = true, // Membuat input tidak bisa diketik langsung
            enabled = enabled,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { if (enabled) expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            statusOptions.forEach { status ->
                DropdownMenuItem(
                    text = { Text(status) },
                    onClick = {
                        onStatusSelected(status)
                        expanded = false
                    }
                )
            }
        }
    }
}

