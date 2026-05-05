package com.ki_bun.pioneer.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ki_bun.pioneer.viewmodel.ProgressViewModel
import com.ki_bun.pioneer.R
import com.ki_bun.pioneer.component.ProgressCard
import com.ki_bun.pioneer.data.Item

@Composable
fun HomeScreen(progressViewModel: ProgressViewModel) {

    val progressList by progressViewModel.progressList.collectAsState()
    var selectedItem by remember { mutableStateOf<Item?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        InputDialog(progressViewModel = progressViewModel)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true },
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sharp_add_24),
                    contentDescription = "Add",
                    Modifier.size(40.dp)
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(progressList) { item ->
                ProgressCard(
                    item,
                    onDelete = {
                        progressViewModel.deleteItem(item)
                    },
                    onEdit = {
                        selectedItem = item
                        showEditDialog = true
                    },
                    progressViewModel = progressViewModel
                )
            }
        }
        if (showEditDialog && selectedItem != null) {
            selectedItem?.let { item ->
                EditDialog(
                    progressList = item,
                    onUpdate = { newItem ->
                        progressViewModel.updateItem(newItem)
                        showEditDialog = false
                    },
                    onDismiss = {
                        showEditDialog = false
                    }
                )
            }
        }
    }
}