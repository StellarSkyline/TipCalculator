package com.example.tipcalculator.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.Util.calculateTotalPerPerson
import com.example.tipcalculator.Util.calculateTotalTip
import com.example.tipcalculator.components.InputField
import com.example.tipcalculator.widgets.RoundIconButton

@Composable
fun TipCalculatorUI(content: @Composable () -> Unit = {}) {
    val totalPerPerson = remember { mutableStateOf(0.0) }
    val totalBil = remember { mutableStateOf(0.0) }
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
            .padding(10.dp)
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
fun BillForm(
    modifier: Modifier = Modifier,
    onValChanged: (String) -> (Unit) = {}
) {
    //States
    val totalBillState = remember { mutableStateOf("") }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val person = remember { mutableStateOf(1) }
    val sliderPositionState = remember { mutableStateOf(0f) }
    val tipPercentage = (sliderPositionState.value * 100).toInt()
    val tipAmmountState = remember { mutableStateOf(0.0) }
    val totalPerPersonState = remember {mutableStateOf(0.0)}

    val keyboardController = LocalSoftwareKeyboardController.current

    TopHeader(totalPerPerson = totalPerPersonState.value)

    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 3.dp, color = Color.LightGray)
    ) {

        Column(
            modifier = Modifier
                .padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            InputField(
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChanged(totalBillState.value.trim())
                    keyboardController?.hide()
                }
            )

            if(validState) {
                Row(
                    modifier = Modifier
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.Start

                ) {
                    Text(
                        text = "Split",
                        modifier = Modifier
                            .align(
                                alignment = Alignment.CenterVertically
                            ),
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.width(120.dp))

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {

                        RoundIconButton(
                            imageVector = Icons.Default.Remove,
                            onClick = {
                                if (person.value > 1) person.value -= 1 else person.value = 1

                                totalPerPersonState.value =
                                    calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                        splitBy = person.value,
                                        tipPercent = tipPercentage)
                            }
                        )

                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 9.dp),
                            text = "${person.value}",
                            fontSize = 20.sp
                        )

                        RoundIconButton(
                            imageVector = Icons.Default.Add,
                            onClick = {
                                person.value += 1
                                totalPerPersonState.value =
                                    calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                        splitBy = person.value,
                                        tipPercent = tipPercentage)
                            }
                        )

                    }

                }

                //Tip Row
                Row(
                    modifier = Modifier
                        .padding(horizontal = 3.dp, vertical = 12.dp)
                )
                {

                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 3.dp),
                        text = "Tip",
                        fontSize = 20.sp
                    )

                    Spacer(
                        modifier = Modifier
                            .width(180.dp)
                    )

                    Text(
                        text = "$${tipAmmountState.value}",
                        fontSize = 20.sp

                    )

                }

                //Tip and Slider
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        "$tipPercentage%",
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    //Slider
                    Slider(value = sliderPositionState.value,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp),
                        onValueChange = { newVal ->

                            sliderPositionState.value = newVal

                            tipAmmountState.value =
                                calculateTotalTip(bill = totalBillState.value.toDouble(),percent =  tipPercentage)

                            totalPerPersonState.value =
                                calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                    splitBy = person.value,
                                    tipPercent = tipPercentage)

                            Log.d("STLog", "Slider: $newVal")

                        })

                }

            } else Box{}

        }

    }

}


