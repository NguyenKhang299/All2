package com.tearas.expanemanagementjc.domain.init

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import com.tearas.expanemanagementjc.ExpenseApplication
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.data.local.AccountDAO
import com.tearas.expanemanagementjc.data.manager.LocalUserManager
import com.tearas.expanemanagementjc.domain.model.Account
import com.tearas.expanemanagementjc.domain.model.CategoryImage
import com.tearas.expanemanagementjc.domain.model.Language
import com.tearas.expanemanagementjc.domain.model.ThemeMode
import com.tearas.expanemanagementjc.usecases.category.CategoryUseCases
import com.tearas.expanemanagementjc.usecases.category_image.CategoryImageUseCase
import com.tearas.expanemanagementjc.usecases.setting.SettingUseCases
import com.tearas.expanemanagementjc.utils.AssetUtils
import com.tearas.expanemanagementjc.utils.dataCategoriesDefaults
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class InitApp @Inject constructor(
    @ApplicationContext private val context: Context,
    private val categoryUseCases: CategoryUseCases,
    private val localUserManager: LocalUserManager,
    private val categoryImageUseCases: CategoryImageUseCase,
    private val settingUseCases: SettingUseCases,
    private val accountDAO: AccountDAO
) {
    fun initSettings() {
      val corou=  CoroutineScope(Dispatchers.IO)
        corou.launch {
             settingUseCases.getSetting().collect {
                if (it != null) {
                    setTheme(it.themeMode)
                    ExpenseApplication.onChangeLang = it.language
                    ExpenseApplication.SETTING_OPTIONS = it
                }
            }
        }
        corou.launch {
            localUserManager.getPassword().collect {
                ExpenseApplication.isPasscode = it != null
            }
        }
    }


    private fun setTheme(themeMode: ThemeMode) {
        val isNightMode = themeMode != ThemeMode.LIGHT_MODE
        AppCompatDelegate.setDefaultNightMode(
            if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        ExpenseApplication.isThemeNightMode = isNightMode
    }

    fun setUpNotification() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            "2992003",
            "Alarm Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        val soundUri = Uri.parse(
            "android.resource://" +
                    context.packageName +
                    "/" +
                    R.raw.sound_noti
        )
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()
        channel.setSound(soundUri, audioAttributes)
        notificationManager.createNotificationChannel(channel)
    }

    fun setUpDataFirstOpen() {
        CoroutineScope(Dispatchers.IO).launch {
            if (categoryImageUseCases.selectedCategoryImages().isEmpty()) {
                accountDAO.addAccount(Account(0, "Expense Management", true))
                val account = accountDAO.selectAccount()
                localUserManager.setAccountUsing(account)

                val categoryImages =
                    AssetUtils.getAssetCategoryFromJson(context).toMutableList()
                val listImageCategories = mutableListOf<CategoryImage>()
                categoryImages.forEach { dto ->
                    dto.paths.forEach {
                        listImageCategories.add(CategoryImage(0, it, dto.type))
                    }
                }
                categoryImageUseCases.insertCategoryImage(*listImageCategories.toTypedArray())
                    .forEachIndexed { index, id ->
                        listImageCategories[index] =
                            listImageCategories[index].copy(idCategoryImage = id)
                    }
                val listCateDf = dataCategoriesDefaults

                val mutableListCate = listCateDf.mapNotNull { (s, category) ->
                    listImageCategories.find { it.pathIcon.contains(s) }?.let {
                        category.copy(
                            categoryImageId = it.idCategoryImage,
                            categoryType = it.type,
                            transactionType = category.transactionType,
                            isDefault = true
                        )
                    }
                }
                categoryUseCases.insertCategory(*mutableListCate.toTypedArray())
            }
        }
    }
}