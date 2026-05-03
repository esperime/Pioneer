package com.ki_bun.pioneer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ki_bun.pioneer.ui.theme.PioneerTheme

@Composable
fun SettingsScreen() {
    val uriHandler = LocalUriHandler.current
    PioneerTheme {
        Column(modifier = Modifier.fillMaxSize().padding(start = 30.dp, top = 30.dp)) {
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
                Column {
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
                Column {
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
}