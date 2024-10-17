package com.example.tipcalculator.Util

fun calculateTotalTip(bill: Double, percent: Int): Double {
    return if(bill > 1 && bill.toString().isNotEmpty()) (bill * percent)/100
    else 0.0
}


fun calculateTotalPerPerson(totalBill:Double, splitBy:Int, tipPercent:Int):Double {
    val bill = calculateTotalTip(totalBill, tipPercent) + totalBill
    return (bill/splitBy)
}