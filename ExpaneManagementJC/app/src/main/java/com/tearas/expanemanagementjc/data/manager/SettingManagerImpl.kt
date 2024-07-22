package com.tearas.expanemanagementjc.data.manager

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.tearas.expanemanagementjc.domain.model.OptionsSetting
import com.tearas.expanemanagementjc.domain.repository.ISettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingManagerImpl(private val context: Context)  : ISettingRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting_mng")

    private object PreferencesKeys {
        val SETTING = stringPreferencesKey("SETTING")
    }

    override suspend fun setSetting(settings: OptionsSetting) {
        context.dataStore.edit { setting ->
            setting[PreferencesKeys.SETTING] = Gson().toJson(settings)
        }
    }

    override fun getSetting(): Flow<OptionsSetting?> {
        return context.dataStore.data.map { preferences ->
            Gson().fromJson(preferences[PreferencesKeys.SETTING], OptionsSetting::class.java)
        }
    }
}