package com.pki.homebakery.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class PrefsService(context: Context) {
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("HOME_BAKERY")
    private val pref = context.datastore

    suspend fun setCurrentUser(username: String) {
        pref.edit { it[CURRENT_USER] = username }
    }

    suspend fun getCurrentUser() = pref.data.map { it[CURRENT_USER] }.firstOrNull()

    suspend fun removeCurrentUser() {
        pref.edit { it.remove(CURRENT_USER) }
    }

    companion object {
        private val CURRENT_USER = stringPreferencesKey("CURRENT_USER")
    }
}
