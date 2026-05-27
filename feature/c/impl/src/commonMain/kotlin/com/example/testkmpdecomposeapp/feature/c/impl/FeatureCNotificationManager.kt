package com.example.testkmpdecomposeapp.feature.c.impl

import org.koin.core.module.Module

interface FeatureCNotificationManager {
    fun showTestNotification(itemId: Int)
}

expect val featureCPlatformModule: Module
