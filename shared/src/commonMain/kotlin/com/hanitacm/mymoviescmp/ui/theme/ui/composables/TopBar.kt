package com.hanitacm.mymoviescmp.ui.theme.ui.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import mymoviescmp.shared.generated.resources.Res
import mymoviescmp.shared.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar() {
    TopAppBar(title = { Text(text = stringResource(Res.string.app_name)) })
}
