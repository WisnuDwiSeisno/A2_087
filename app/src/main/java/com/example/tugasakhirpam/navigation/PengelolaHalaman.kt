package com.example.tugasakhirpam.navigation

import com.example.tugasakhirpam.ui.view.buku.BukuHomeScreen
import com.example.tugasakhirpam.ui.view.buku.DestinasiHomeBuku
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasakhirpam.ui.view.buku.DestinasiDetail
import com.example.tugasakhirpam.ui.view.buku.DestinasiEntry
import com.example.tugasakhirpam.ui.view.buku.DestinasiUpdate
import com.example.tugasakhirpam.ui.view.buku.DetailView
import com.example.tugasakhirpam.ui.view.buku.InsertViewScreen
import com.example.tugasakhirpam.ui.view.buku.UpdateView
import com.example.tugasakhirpam.ui.view.kategori.DestinasiDetailKategori
import com.example.tugasakhirpam.ui.view.kategori.DestinasiHomeKategori
import com.example.tugasakhirpam.ui.view.kategori.DestinasiKategoriEntry
import com.example.tugasakhirpam.ui.view.kategori.DestinasiUpdateKategori
import com.example.tugasakhirpam.ui.view.kategori.DetailKategoriScreen
import com.example.tugasakhirpam.ui.view.kategori.InsertKategoriScreen
import com.example.tugasakhirpam.ui.view.kategori.KategoriHomeScreen
import com.example.tugasakhirpam.ui.view.kategori.UpdateKategoriView
import com.example.tugasakhirpam.ui.view.penerbit.DestinasiDetailPenerbit
import com.example.tugasakhirpam.ui.view.penerbit.DestinasiHomePenerbit
import com.example.tugasakhirpam.ui.view.penerbit.DestinasiPenerbitEntry
import com.example.tugasakhirpam.ui.view.penerbit.DestinasiUpdatePenerbit
import com.example.tugasakhirpam.ui.view.penerbit.DetailPenerbitScreen
import com.example.tugasakhirpam.ui.view.penerbit.InsertPenerbitScreen
import com.example.tugasakhirpam.ui.view.penerbit.PenerbitHomeScreen
import com.example.tugasakhirpam.ui.view.penerbit.UpdatePenerbitView
import com.example.tugasakhirpam.ui.view.penulis.DestinasiDetailPenulis
import com.example.tugasakhirpam.ui.view.penulis.DestinasiHomePenulis
import com.example.tugasakhirpam.ui.view.penulis.DestinasiPenulisEntry
import com.example.tugasakhirpam.ui.view.penulis.DestinasiUpdatePenulis
import com.example.tugasakhirpam.ui.view.penulis.DetailPenulisScreen
import com.example.tugasakhirpam.ui.view.penulis.InsertPenulisScreen
import com.example.tugasakhirpam.ui.view.penulis.PenulisHomeScreen
import com.example.tugasakhirpam.ui.view.penulis.UpdatePenulisView

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeBuku.route, // Start di Home Buku
        modifier = Modifier
    ) {
        composable(DestinasiHomeBuku.route) {
            BukuHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetail.route}/$id") // Isi argumen rute dengan benar
                },
                navigateToPenerbit = { navController.navigate(DestinasiHomePenerbit.route) },
                navigateToPenulis = { navController.navigate(DestinasiHomePenulis.route) }
            )
        }
        composable(
            route = "home_kategori/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                // Tampilkan halaman kategori berdasarkan id
                KategoriHomeScreen(
                    id = id.toInt(),
                    navigateToItemEntry = { navController.navigate(DestinasiKategoriEntry.route) },
                    onKategoriClick = { id ->
                        navController.navigate("${DestinasiDetailKategori.route}/$id")
                    }
                )
            }
        }
        composable(DestinasiEntry.route) {
            InsertViewScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeBuku.route) {
                        popUpTo(DestinasiHomeBuku.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(DestinasiHomePenerbit.route) {
            PenerbitHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiPenerbitEntry.route) },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailPenerbit.route}/$id")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeBuku.route) {
                        popUpTo(DestinasiHomeBuku.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(DestinasiHomePenulis.route) {
            PenulisHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiPenulisEntry.route) },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailPenulis.route}/$id")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeBuku.route) {
                        popUpTo(DestinasiHomeBuku.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = DestinasiHomeKategori.route // "home_kategori"
        ) {
            KategoriHomeScreen(
                onKategoriClick = {},
                navigateToItemEntry = {},
                onBackClick = { },
                id = id.toInt()
            )
        }
        composable(DestinasiKategoriEntry.route) {
            InsertKategoriScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeKategori.route) {
                        popUpTo(DestinasiHomeKategori.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(DestinasiPenerbitEntry.route) {
            InsertPenerbitScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePenerbit.route) {
                        popUpTo(DestinasiHomePenerbit.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(DestinasiPenulisEntry.route) {
            InsertPenulisScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePenulis.route) {
                        popUpTo(DestinasiHomePenulis.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.ID_BUKU) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_buku = it.arguments?.getString(DestinasiDetail.ID_BUKU)
            id_buku?.let { id_buku ->
                DetailView(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$it")
                    },
                    id = id_buku.toInt(),
                    onDeleteClick = {
                        navController.popBackStack()
                    },
                    onViewAllKategori = {
                        // Navigasi ke halaman kategori berdasarkan ID Buku
                        navController.navigate("${DestinasiHomeKategori.route}/$id_buku")
                    }
                )
            }
        }
        composable(
            DestinasiDetailKategori.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailKategori.ID_KATEGORI) {
                    type = NavType.IntType
                }
            )
        ) {
            val id_kategori = it.arguments?.getInt(DestinasiDetailKategori.ID_KATEGORI)
            id_kategori?.let { id ->
                DetailKategoriScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditClick = { idKategori ->
                        navController.navigate(
                            DestinasiUpdateKategori.routeWithArgs.replace(
                                "{${DestinasiUpdateKategori.ID_KATEGORI}}",
                                idKategori.toString()
                            )
                        )
                    },
                    id = id,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            DestinasiDetailPenerbit.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPenerbit.ID_KATEGORI) {
                    type = NavType.IntType
                }
            )
        ) {
            val id_penerbit = it.arguments?.getInt(DestinasiDetailPenerbit.ID_KATEGORI)
            id_penerbit?.let { id ->
                DetailPenerbitScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditClick = { idPenerbit ->
                        navController.navigate(
                            DestinasiUpdatePenerbit.routeWithArgs.replace(
                                "{${DestinasiUpdatePenerbit.ID_KATEGORI}}",
                                idPenerbit.toString()
                            )
                        )
                    },
                    id = id,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            DestinasiDetailPenulis.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPenulis.ID_KATEGORI) {
                    type = NavType.IntType
                }
            )
        ) {
            val id_penulis = it.arguments?.getInt(DestinasiDetailPenulis.ID_KATEGORI)
            id_penulis?.let { id ->
                DetailPenulisScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditClick = { idPenulis ->
                        navController.navigate(
                            DestinasiUpdatePenulis.routeWithArgs.replace(
                                "{${DestinasiUpdatePenulis.ID_KATEGORI}}",
                                idPenulis.toString()
                            )
                        )
                    },
                    id = id,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            route = DestinasiUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.ID_BUKU) { type = NavType.IntType } // Ganti ke IntType
            )
        ) { backStackEntry ->
            // Ambil ID_Buku sebagai Int
            val idBuku = backStackEntry.arguments?.getInt(DestinasiUpdate.ID_BUKU)

            // Pastikan idBuku tidak null
            idBuku?.let {
                UpdateView(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeBuku.route) {
                            popUpTo(DestinasiHomeBuku.route) { inclusive = true }
                        }
                    }
                )
            } ?: run {
                // Tangani error jika idBuku null
                Log.e("UpdateView", "ID Buku tidak ditemukan atau tidak valid")
            }
        }
        composable(
            route = DestinasiUpdateKategori.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateKategori.ID_KATEGORI) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idKategori = backStackEntry.arguments?.getInt(DestinasiUpdateKategori.ID_KATEGORI)
            idKategori?.let {
                UpdateKategoriView(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeKategori.route) {
                            popUpTo(DestinasiHomeKategori.route) { inclusive = true }
                        }
                    }
                )
            } ?: run {
                Log.e("UpdateKategoriView", "ID Kategori tidak ditemukan atau tidak valid")
            }
        }
        composable(
            route = DestinasiUpdatePenerbit.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdatePenerbit.ID_KATEGORI) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idPenerbit = backStackEntry.arguments?.getInt(DestinasiUpdatePenerbit.ID_KATEGORI)
            idPenerbit?.let {
                UpdatePenerbitView(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomePenerbit.route) {
                            popUpTo(DestinasiHomePenerbit.route) { inclusive = true }
                        }
                    }
                )
            } ?: run {
                Log.e("UpdateKategoriView", "ID Kategori tidak ditemukan atau tidak valid")
            }
        }
        composable(
            route = DestinasiUpdatePenulis.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdatePenulis.ID_KATEGORI) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idPenulis = backStackEntry.arguments?.getInt(DestinasiUpdatePenulis.ID_KATEGORI)
            idPenulis?.let {
                UpdatePenulisView(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomePenulis.route) {
                            popUpTo(DestinasiHomePenulis.route) { inclusive = true }
                        }
                    }
                )
            } ?: run {
                Log.e("UpdateKategoriView", "ID Kategori tidak ditemukan atau tidak valid")
            }
        }
    }
}


