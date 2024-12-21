package com.bakery.core.presentation.ui.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.core.presentation.ui.components.icons.IconComponent
import com.bakery.core.resources.resources.generated.resources.Res
import com.bakery.core.resources.resources.generated.resources.close
import com.bakery.core.resources.resources.generated.resources.ic_x
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CloseIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier.size(size = 28.dp),
        onClick = onClick
    ) {
        IconComponent(
            painter = painterResource(Res.drawable.ic_x),
            contentDescription = stringResource(Res.string.close)
        )
    }
}
