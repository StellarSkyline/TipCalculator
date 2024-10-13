package com.example.tipcalculator.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TipCalculatorUI( content:@Composable () -> Unit = {}) {
    val totalPerPerson = remember{ mutableStateOf(0.0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 35.dp, start = 16.dp, end = 16.dp)
    ) {
        TopHeader()
    }
}

@Composable
@Preview
fun TopHeader(totalPerPerson: Double = 0.0, updateTotal: (Double) -> (Unit) = {}) {
    val total = "%.2f".format(totalPerPerson)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(corner = CornerSize(15.dp))),
        color = Color.Gray
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Per Person:",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "$$total",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@Composable
@Preview
fun mainContent() {

}
