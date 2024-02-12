package com.example.andtest.api.dto

import androidx.annotation.StringRes
import com.example.andtest.R

enum class Status(@StringRes val title: Int) {
    PENDING(R.string.statusPending),
    IN_PROCESS(R.string.statusInProcess),
    ON_HOLD(R.string.statusOnHold),
    FINISHED(R.string.statusFinished)
}