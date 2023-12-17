package com.example.andtest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ExampleScreen() {
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {
        Column {
       
            Text(text = "EXAMPLE")
            Button(onClick = { /*TODO*/ }) {
                Text(text = "BUTTON EXAMPLE")
            }
        }
}
    }
@Preview
@Composable
fun ExampleScreenPreview(){
    ExampleScreen()
}