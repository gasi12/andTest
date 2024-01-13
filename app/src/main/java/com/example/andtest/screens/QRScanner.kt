package com.example.andtest.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

@Composable
fun QRScanner(context: Context) {
//    RowWithValue(row = "pizda", description ="ogien" )
    Column {
        Text(text = "AAA")
        Text(text = "BBB")
    }

//    val options = GmsBarcodeScannerOptions.Builder()
//        .setBarcodeFormats(
//            Barcode.FORMAT_QR_CODE)
//        .build()
//    val scanner = GmsBarcodeScanning.getClient(context, options)
//    scanner.startScan()
//        .addOnSuccessListener { barcode ->
//            val rawValue: String? = barcode.rawValue
//            Log.i("Scanner",barcode.rawValue?:"nic" )
//        }
//        .addOnCanceledListener {
//            // Task canceled
//        }
//        .addOnFailureListener { e ->
//            Log.i("QRScanner exception", e.toString())
//        }

}