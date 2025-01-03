package com.bakery.auth.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.auth.presentation.viewmodel.SignUpViewModel
import com.bakery.core.presentation.ui.components.display.TextComponent

@Composable
actual fun SignUpScreen(
    viewModel: SignUpViewModel
) {
    ElevatedCard(
        modifier = Modifier.fillMaxSize().wrapContentSize(),
        shape = RoundedCornerShape(16),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextComponent(text = "Jvm Sign Up")
        }
    }
}
