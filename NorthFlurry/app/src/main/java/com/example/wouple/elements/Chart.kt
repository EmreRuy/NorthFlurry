package com.example.wouple.elements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp


@Composable
fun BarChart(data: List<Double>, labels: List<String>) {

    val truncatedData = data.take(7) // Take only the first 7 elements of the data list

    Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(300.dp)) {
            drawIntoCanvas { _ ->
                val canvasWidth = size.width
                val canvasHeight = size.height

                val barCount = truncatedData.size
                val barWidth = (canvasWidth - (barCount + 1) * 10.dp.toPx()) / barCount

                truncatedData.forEachIndexed { index, value ->
                    // Calculate the height of the bar based on the UV index value
                    val barHeight = ((value / 5) * 150.dp.toPx()).toFloat()

                    // Calculate the position of the bar
                    val startX = index * (barWidth + 10.dp.toPx()) + 10.dp.toPx()
                    val startY = (canvasHeight - barHeight)

                    // Draw the bar
                    drawRect(
                        color = Color.Blue,
                        topLeft = Offset(startX, startY),
                        size = Size(barWidth, barHeight)
                    )

                    // Draw day labels below the bars
                    val dayLabel = labels.getOrNull(index)
                    dayLabel?.let {
                        drawContext.canvas.nativeCanvas.drawText(
                            it,
                            startX + barWidth / 2, // Center the label horizontally under the bar
                            canvasHeight + 20.dp.toPx(), // Position the label below the bar
                            android.graphics.Paint().apply {
                                color = Color.White.toArgb()
                                textAlign = android.graphics.Paint.Align.CENTER
                                textSize = 16.dp.toPx() // Adjust text size as needed
                            }
                        )
                    }
                }
            }
        }
    }
}