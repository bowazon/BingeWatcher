package com.example.designsystem


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Preview(
    name = "Tablet Landscape",
    widthDp = 1024,
    heightDp = 600,
    showBackground = true
)
@Preview(
    name = "Foldable Phone",
    widthDp = 840,
    heightDp = 480,
    showBackground = true
)
@Composable
fun FilmReel() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotation by infiniteTransition.animateFloat(
        0f, 360f,
        animationSpec = infiniteRepeatable(
            tween(3000, easing = LinearEasing)
        ),
        label = ""
    )

    Canvas(modifier = Modifier.size(200.dp)) {
        drawArc(
            topLeft = Offset(445f, 70f),
            color = Color.Black,
            startAngle = 90f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(3f, cap = StrokeCap.Butt),
            size = Size(size.width - 150, size.height - 150)
        )

        rotate(rotation) {
            drawCircle(color = Color.Black, radius = size.minDimension / 2 - 100)

            drawCircle(
                color = Color.White,
                radius = 10f,
            )

            drawCircle(
                color = Color.White,
                radius = 10f,
                //center = Offset(0, 0)
            )

            // отверстия в бобине
            repeat(6) { i ->
                val angle = i * 60f
                val x = size.center.x + cos(Math.toRadians(angle.toDouble())).toFloat() * 110
                val y = size.center.y + sin(Math.toRadians(angle.toDouble())).toFloat() * 110

                drawCircle(
                    color = Color.White,
                    radius = 45f,
                    center = Offset(x, y)
                )
            }
        }
    }
}