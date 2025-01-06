package com.example.movieapp.ui.screen.redundant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(onLoginClick: () -> Unit) {
    var email by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = "Ready to keep track of all of your movies??",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Enter your email to create or sign in to your account.",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Email TextField
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "Email", color = Color.Gray) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Get Started Button
            Button(
                onClick = { /* Handle Get Started click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC0CB)), // Pink color
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "GET STARTED", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login Text
            Text(
                text = "Already have an account?\nClick here to login",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable { onLoginClick() }
                    .padding(top = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}