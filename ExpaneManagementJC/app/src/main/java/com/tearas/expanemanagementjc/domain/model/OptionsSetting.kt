package com.tearas.expanemanagementjc.domain.model

import android.content.Context
import java.util.Locale

data class OptionsSetting(
    val currencyFormat: CurrencyFormat = CurrencyFormat.OPTION_1,
    val timeFormat: TimeFormat = TimeFormat.H24,
    val language: Language = Language.VI,
    val themeMode: ThemeMode = ThemeMode.LIGHT_MODE,
    val isPassword: Boolean = false
)

enum class ThemeMode {
    LIGHT_MODE, DARK_MODE
}

enum class Language {
    VI, EN;

    companion object {
        fun setLocale(lang: Language, context: Context) {
            val locale = Locale(lang.name.lowercase())
            Locale.setDefault(locale)
            val resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(locale)
//            context.createConfigurationContext(configuration)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
    }
}

enum class TimeFormat {
    H12, H24
}

enum class CurrencyFormat(val format: String) {
    OPTION_1("##,###.##"),
    OPTION_2("## ###.##")
}
