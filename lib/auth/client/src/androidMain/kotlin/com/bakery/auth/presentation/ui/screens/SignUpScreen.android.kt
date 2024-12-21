package com.bakery.auth.presentation.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.bakery.auth.presentation.ui.components.mobile.MobileAuthLayout
import com.bakery.auth.presentation.viewmodel.SignUpViewModel
import com.bakery.core.presentation.ui.components.display.TextComponent
import com.bakery.core.resources.resources.generated.resources.Res
import com.bakery.core.resources.resources.generated.resources.welcome
import org.jetbrains.compose.resources.stringResource

@Composable
actual fun SignUpScreen(
    viewModel: SignUpViewModel
) {
    MobileAuthLayout(
        title = {
            TextComponent(
                text = stringResource(Res.string.welcome),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                textAlign = MaterialTheme.typography.titleLarge.textAlign,
            )
        },
        content = {},
        footer = {}
    )
}
