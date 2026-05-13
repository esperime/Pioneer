package com.ki_bun.pioneer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ki_bun.pioneer.viewmodel.ProgressViewModel
import com.ki_bun.pioneer.R
import com.ki_bun.pioneer.data.Item
import com.ki_bun.pioneer.util.nullToString
import java.io.File

@Composable
fun ProgressCard(
    progressList: Item,
    onDelete: (Item) -> Unit,
    onEdit: (Item) -> Unit,
    progressViewModel: ProgressViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    var updatedProgress by remember { mutableIntStateOf(progressList.progress) }
    val newTotal = progressList.total
    val totalString = newTotal ?: nullToString(newTotal)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (progressList.imagePath != null) {
            Box(
                modifier = Modifier
                    .width(360.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .height(150.dp)
            ) {
                AsyncImage(
                    model = File(progressList.imagePath),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.surfaceContainerLow
                                )
                            )
                        )
                ) {}
            }
        }
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                modifier = Modifier
                    .width(360.dp)
                    .clip(if (progressList.imagePath != null) RoundedCornerShape(10.dp) else RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                painterResource(id = R.drawable.more_vert_24px),
                                contentDescription = "More options"
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Edit") },
                                onClick = {
                                    onEdit(progressList)
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Delete") },
                                onClick = {
                                    onDelete(progressList)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = progressList.title,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 50.dp),
                        style = if (updatedProgress == progressList.total) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = progressList.description,
                        modifier = Modifier.padding(end = 50.dp),
                        style = if (updatedProgress == progressList.total) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle(),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (progressList.total != null) {
                        val indicator = progressList.progress / newTotal.toFloat()
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "${(indicator * 100).toInt()} %", fontSize = 14.sp)
                            LinearProgressIndicator(
                                modifier = Modifier.padding(start = 5.dp).fillMaxWidth(),
                                progress = { indicator }
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                        FlowRow {
                            progressList.tags.forEach { tag ->
                                AssistChip(
                                    onClick = {},
                                    label = { Text(tag) },
                                    modifier = Modifier.padding(horizontal = 5.dp)
                                )
                            }
                        }
                    Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    if (updatedProgress > 0) {
                                        updatedProgress--
                                        progressViewModel.updateItem(progressList.copy(progress = updatedProgress))
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(50.dp),
                                contentPadding = PaddingValues((0.dp))
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.sharp_subtract_24),
                                    contentDescription = "Subtract"
                                )
                            }
                            Text(
                                text = "${progressList.unit} ${progressList.progress} / $totalString",
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                            )
                            Button(
                                onClick = {
                                    if (updatedProgress < (newTotal ?: Int.MAX_VALUE)) {
                                        updatedProgress++
                                        progressViewModel.updateItem(progressList.copy(progress = updatedProgress))
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(50.dp),
                                contentPadding = PaddingValues((0.dp))
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.sharp_add_24),
                                    contentDescription = "Add"
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }