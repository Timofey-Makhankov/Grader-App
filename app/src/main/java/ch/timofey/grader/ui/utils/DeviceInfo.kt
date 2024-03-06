package ch.timofey.grader.ui.utils

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import ch.timofey.grader.BuildConfig

@Serializable
data class DeviceInfo(
    @Required val versionCode: Int = BuildConfig.VERSION_CODE,
    @Required val versionName: String = BuildConfig.VERSION_NAME,
    @Required val buildType: String = BuildConfig.BUILD_TYPE,
    @Required val applicationId: String = BuildConfig.APPLICATION_ID,
    @Required val device: String = android.os.Build.DEVICE,
    @Required val model: String = android.os.Build.MODEL,
    @Required val brand: String = android.os.Build.BRAND,
    @Required val codeName: String = android.os.Build.VERSION.CODENAME,
    @Required val version: Int = android.os.Build.VERSION.SDK_INT,
    @Required val product: String = android.os.Build.PRODUCT
)
