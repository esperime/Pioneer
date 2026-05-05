package com.ki_bun.pioneer

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ki_bun.pioneer.ui.theme.ThemeMode

@Composable
fun SettingsScreen(onThemeModeChange: (ThemeMode) -> Unit, themeMode: ThemeMode, progressViewModel: ProgressViewModel) {

    val uriHandler = LocalUriHandler.current
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val createFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/csv")
    ) { uri ->
        if (uri != null) {
            progressViewModel.exportToUri(context, uri)
        }
    }

        Column(modifier = Modifier.fillMaxSize().padding(all = 30.dp)) {
            Text(
                text = "Theme",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(25.dp))
            Surface(modifier = Modifier.clickable {
                expanded = !expanded
            }) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Auto") },
                        onClick = {
                            onThemeModeChange(ThemeMode.AUTO)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Light") },
                        onClick = {
                            onThemeModeChange(ThemeMode.LIGHT)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Dark") },
                        onClick = {
                            onThemeModeChange(ThemeMode.DARK)
                            expanded = false
                        }
                    )
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "App theme",
                        fontSize = 18.sp
                    )
                    Text(
                        text = when (themeMode) {
                            ThemeMode.AUTO -> {
                                "Auto"
                            }
                            ThemeMode.DARK -> {
                                "Dark"
                            }
                            else -> {
                                "Light"
                            }
                        },
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "Links",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(25.dp))
            Surface(
                modifier = Modifier.clickable {
                    createFileLauncher.launch("pioneer_export.csv")
                }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Export to CSV",
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Backup your data",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "Links",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(25.dp))
            Surface(
                modifier = Modifier.clickable {
                    uriHandler.openUri("https://github.com/ki-bun/Pioneer")
                }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Source Code",
                        fontSize = 18.sp
                    )
                    Text(
                        text = "https://github.com/ki-bun/Pioneer",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Surface(
                modifier = Modifier.clickable {
                    uriHandler.openUri("https://github.com/ki-bun/Pioneer/releases")
                }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Releases",
                        fontSize = 18.sp
                    )
                    Text(
                        text = "https://github.com/ki-bun/Pioneer/releases",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }
            }
        }
}