package com.tearas.expanemanagementjc

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import com.tearas.expanemanagementjc.domain.init.InitApp
import com.tearas.expanemanagementjc.domain.model.Language
import com.tearas.expanemanagementjc.domain.model.OptionsSetting
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale
import javax.inject.Inject


@HiltAndroidApp
class ExpenseApplication : Application() {

    companion object {
        var SETTING_OPTIONS: OptionsSetting = OptionsSetting()
        val TIME_FORMAT get() = SETTING_OPTIONS.timeFormat
        val LANGUAGE get() = SETTING_OPTIONS.language
        val CURRENCY_FORMAT get() = SETTING_OPTIONS.currencyFormat
        val THEME_MODE get() = SETTING_OPTIONS.themeMode
        var isThemeNightMode by mutableStateOf(false)
        var onChangeLang by mutableStateOf(Language.VI)
        var isPasscode = false
    }

    @Inject
    lateinit var initApp: InitApp

    override fun onCreate() {
        super.onCreate()
//        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(LANGUAGE.name.lowercase())
//        AppCompatDelegate.setApplicationLocales(appLocale)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initApp.initSettings()
        initApp.setUpDataFirstOpen()
        initApp.setUpNotification()
    }
}