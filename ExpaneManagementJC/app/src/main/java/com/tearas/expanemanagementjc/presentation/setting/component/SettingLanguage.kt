package com.tearas.expanemanagementjc.presentation.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.domain.model.Language

@Composable
fun SettingLanguage(langSelected: Language, onLangSelected: (Language) -> Unit) {
    Column {
        Text(text = stringResource(id = R.string.language), modifier = Modifier.padding(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            LanguageButton(selected = langSelected == Language.VI, text = "Viet Nam") {
                onLangSelected(Language.VI)
            }
            Spacer(modifier = Modifier.width(15.dp))
            LanguageButton(selected = langSelected == Language.EN, text = "English") {
                onLangSelected(Language.EN)
            }
        }
    }
}