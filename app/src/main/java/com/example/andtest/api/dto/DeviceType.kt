package com.example.andtest.api.dto

import androidx.annotation.StringRes
import com.example.andtest.R


enum class DeviceType(@StringRes val title: Int){
    LAPTOP(R.string.laptop),
    DESKTOP(R.string.desktop),
    SMARTPHONE(R.string.smartphone),
    TV(R.string.tv),
    PRINTER(R.string.printerScanner),
    MONITOR(R.string.monitor),
    GAME_CONSOLE(R.string.console),
    OTHER(R.string.other)
}