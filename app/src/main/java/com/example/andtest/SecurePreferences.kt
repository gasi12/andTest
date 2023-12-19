package com.example.andtest

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SecurePreferences private constructor(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "encrypted_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveToken(token: String, type: TokenType) {
        sharedPreferences.edit().putString(type.name, token).apply()
    }

    fun getToken(type: TokenType): String? {
        return sharedPreferences.getString(type.name, null)
    }

    fun saveAnything(key: String, value: String)
    {
        sharedPreferences.edit().putString(key,value).apply()
    }
    fun getAnything(key: String): String
    {
        return sharedPreferences.getString(key,null)?: "not found"
    }
    fun clearTokens(){
        val editor = sharedPreferences.edit()
        for(tokenType in TokenType.values()){
            editor.remove(tokenType.name)
        }
        editor.apply()
    }

    enum class TokenType {
        AUTH,
        REFRESH
    }

    companion object {
        @Volatile private var instance: SecurePreferences? = null

        fun getInstance(context: Context): SecurePreferences {
            return instance ?: synchronized(this) {
                instance ?: SecurePreferences(context).also { instance = it }
            }
        }
    }
}