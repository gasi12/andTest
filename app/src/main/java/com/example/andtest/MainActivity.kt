package com.example.andtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.example.andtest.navigation.Navigation
import com.example.andtest.navigation.Screen
import com.example.andtest.ui.theme.AndTestTheme


// MainActivity with the implemented AuthNavigationCallback interface
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndTestTheme {
                // Pass the AuthNavigationCallback to the Navigation composable
                Navigation()
            }
        }
    }

    // Implement the navigateToLoginScreen method

}