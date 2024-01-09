package com.example.andtest.api.dto


enum class DeviceType(val visibleName: String){
    LAPTOP("laptop"),
    DESKTOP("desktop"),
    SMARTPHONE("smartphone"),
    TV("tv"),
    PRINTER("printer or scanner"),
    MONITOR("monitor"),
    GAME_CONSOLE("game console"),
    OTHER("other")
}