package com.example.testkmpdecomposeapp.feature.c.impl

import org.koin.dsl.module
import platform.Foundation.NSUUID
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter

actual val featureCPlatformModule = module {
    single<FeatureCNotificationManager> {
        IosFeatureCNotificationManager()
    }
}

private class IosFeatureCNotificationManager : FeatureCNotificationManager {

    override fun showTestNotification(itemId: Int) {
        val center = UNUserNotificationCenter.currentNotificationCenter()
        val options = UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge

        center.requestAuthorizationWithOptions(options) { granted, _ ->
            if (!granted) return@requestAuthorizationWithOptions

            val content = UNMutableNotificationContent().apply {
                setTitle("Feature C")
                setBody("Test notification for item #$itemId")
            }

            val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
                timeInterval = 1.0,
                repeats = false
            )
            val request = UNNotificationRequest.requestWithIdentifier(
                identifier = "feature_c_test_${NSUUID().UUIDString}",
                content = content,
                trigger = trigger
            )

            center.addNotificationRequest(request) { _ -> }
        }
    }
}
