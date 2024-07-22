package com.tearas.expanemanagementjc.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.tearas.expanemanagementjc.data.manager.LocalUserManager.PreferencesKeys.PASSWORD
import com.tearas.expanemanagementjc.domain.model.Account
import com.tearas.expanemanagementjc.domain.repository.ILocalUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManager(private val context: Context) : ILocalUserManager {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "account_mng")

    private object PreferencesKeys {
        val ACCOUNT = stringPreferencesKey("ACCOUNT")
        val PASSWORD = stringPreferencesKey("PASSWORD")
    }

    override suspend fun setAccountUsing(account: Account) {
        context.dataStore.edit { setting ->
            setting[PreferencesKeys.ACCOUNT] = Gson().toJson(account)
        }
    }

    override fun getAccountUsing(): Flow<Account?> {
        return context.dataStore.data.map { preferences ->
            Gson().fromJson(preferences[PreferencesKeys.ACCOUNT], Account::class.java)
        }
    }

    override fun getPassword(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[PASSWORD]
        }
    }

    override suspend fun setPassword(password: String) {
        context.dataStore.edit { it[PASSWORD] = password }
    }

    override suspend fun deletePassword() {
        context.dataStore.edit { preferences ->
            preferences.remove(PASSWORD)
        }
    }
}