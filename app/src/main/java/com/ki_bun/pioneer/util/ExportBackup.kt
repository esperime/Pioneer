package com.ki_bun.pioneer.util

import com.ki_bun.pioneer.data.Item

object CSVExport {
    fun itemsToCSV(items: List<Item>): String {
        val header = "title,description,progress,total"
        val rows = items.map { item ->
            listOf(
                item.title,
                item.description,
                item.progress,
                item.total ?: ""
            ).joinToString(",") { value ->
                "\"$value\""
            }
        }

        return (listOf(header) + rows).joinToString("\n")
    }

}