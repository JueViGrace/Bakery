package com.bakery.auth.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.core.presentation.ui.components.display.ImageComponent
import com.bakery.core.resources.resources.generated.resources.Res
import com.bakery.core.resources.resources.generated.resources.app_logo
import com.bakery.core.resources.resources.generated.resources.reimu
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AuthTitle(
    modifier: Modifier = Modifier,
    title: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        title()
        ImageComponent(
            modifier = Modifier.size(100.dp),
            painter = painterResource(Res.drawable.reimu),
            contentDescription = stringResource(Res.string.app_logo)
        )
    }
}
