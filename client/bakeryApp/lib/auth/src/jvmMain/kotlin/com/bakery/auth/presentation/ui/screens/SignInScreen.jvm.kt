package com.bakery.auth.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bakery.auth.presentation.events.SignInEvents
import com.bakery.auth.presentation.ui.components.AuthFooter
import com.bakery.auth.presentation.ui.components.AuthTitle
import com.bakery.auth.presentation.ui.components.HaveAnAccountComponent
import com.bakery.auth.presentation.ui.components.PasswordTextField
import com.bakery.auth.presentation.ui.components.UsernameTextField
import com.bakery.auth.presentation.ui.components.desktop.DesktopAuthLayout
import com.bakery.auth.presentation.viewmodel.SignInViewModel
import com.bakery.core.presentation.ui.components.display.TextComponent
import com.bakery.core.resources.resources.generated.resources.Res
import com.bakery.core.resources.resources.generated.resources.dont_have_an_account
import com.bakery.core.resources.resources.generated.resources.forgot_password
import com.bakery.core.resources.resources.generated.resources.log_in
import com.bakery.core.resources.resources.generated.resources.sign_up
import com.bakery.core.resources.resources.generated.resources.welcome_back
import org.jetbrains.compose.resources.stringResource

// todo: improve responsive
@Composable
actual fun SignInScreen(
    viewModel: SignInViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    DesktopAuthLayout(
        title = {
            AuthTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f),
                title = {
                    TextComponent(
                        text = stringResource(Res.string.welcome_back),
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterVertically),
                ) {
                    UsernameTextField(
                        modifier = Modifier.fillMaxWidth(0.4f),
                        value = state.username,
                        onValueChange = { newValue ->
                            onEvent(SignInEvents.OnSignInUsernameChanged(newValue))
                        },
                        errorMessage = state.usernameError?.let { stringResource(it) }
                    )

                    PasswordTextField(
                        modifier = Modifier.fillMaxWidth(0.4f),
                        value = state.password,
                        onValueChange = { newValue ->
                            onEvent(SignInEvents.OnSignInPasswordChanged(newValue))
                        },
                        errorMessage = state.passwordError?.let { stringResource(it) },
                        passwordVisibility = state.passwordVisibility,
                        onVisibilityChange = {
                            onEvent(SignInEvents.TogglePasswordVisibility)
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextComponent(
                        modifier = Modifier.clickable {
                            onEvent(SignInEvents.OnNavigateToForgotPassword)
                        },
                        text = stringResource(Res.string.forgot_password),
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    onClick = {
                        onEvent(SignInEvents.OnSignInSubmit)
                    },
                    enabled = state.signInEnabled
                ) {
                    TextComponent(
                        text = stringResource(Res.string.log_in)
                    )
                }

                HaveAnAccountComponent(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    initialText = "${stringResource(Res.string.dont_have_an_account)}, ",
                    screenText = "${stringResource(Res.string.sign_up)} ->",
                    onClick = {
                        onEvent(SignInEvents.OnNavigateToSignUp)
                    }
                )
            }
        },
        footer = {
            AuthFooter(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            )
        }
    )
}
