package com.example.andtest.api.downloader

interface Downloader {
    fun downloadFile(url: String, serviceId: Long): Long
}