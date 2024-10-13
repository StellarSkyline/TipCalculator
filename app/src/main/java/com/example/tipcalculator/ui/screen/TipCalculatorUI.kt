package com.example.tipcalculator.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.components.InputField

@Composable
fun TipCalculatorUI( content:@Composable () -> Unit = {}) {
    val totalPerPerson =  remember { mutableStateOf(0.0) }
    val totalBil = remember{ mutableStateOf(0.0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 35.dp, start = 8.dp, end = 8.dp)
    ) {
        //TopHeader()
        MainContent()
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
fun MainContent() {

    BillForm() { billAmt ->
        Log.d("STLOG", "${billAmt.toInt() * 100}")
    }



}

@Composable
@Preview
fun BillForm(modifier:Modifier = Modifier,
             onValChanged: (String) -> (Unit) = {}
             ) {

    val totalBillState = remember{mutableStateOf("")}
    val validState = remember(totalBillState.value){
        totalBillState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 3.dp, color = Color.LightGray)
    ) {

        Column() {

            InputField(
                valueState = totalBillState ,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions{
                    if(!validState) return@KeyboardActions
                    onValChanged(totalBillState.value.trim())
                    keyboardController?.hide()
                }
            )
            if(validState) {

                Text(text = "Valid")

            } else {Box()}


        }

    }

}
