package com.example.movieapp.ui.screen.redundant

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstTimeScreen(isDarkTheme: Boolean,  onLoginClick: () -> Unit, onGetStartedClick: () -> Unit, onThemeToggle: (Boolean) -> Unit) {
    var email by remember { mutableStateOf(TextFieldValue("")) }

    AppBackground(isDarkTheme = isDarkTheme) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
            ) {
                // Header Text
                Text(
                    text = "Ready to keep track of all of your movies??",
                    color = TextWhite,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Enter your email to create or sign in to your account.",
                    color = LightPurple,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 24.dp),
                    textAlign = TextAlign.Center
                )

                // Email TextField
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text(text = "Email", color = LightPurple) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        focusedIndicatorColor = LightPurple,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.background
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                // Get Started Button
                Button(
                    onClick = { onGetStartedClick() },
                    colors = ButtonDefaults.buttonColors(containerColor = LightPurple),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "GET STARTED",
                        color = TextWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Login Text
                Text(
                    text = "Already have an account?\nClick here to login",
                    color = if (isDarkTheme) LightPurple else Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { onLoginClick() }
                        .padding(top = 16.dp),
                    textAlign = TextAlign.Center
                )

                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { onThemeToggle(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = LightPurple,
                        uncheckedThumbColor = Color.Gray
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FirstTimeScreenPreview() {
    FirstTimeScreen(onLoginClick = {}, onGetStartedClick = {}, isDarkTheme = true, onThemeToggle = {})
}

