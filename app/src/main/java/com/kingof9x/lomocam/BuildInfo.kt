package com.kingof9x.lomocam

import android.content.pm.PackageManager

object BuildInfo {
    /** "v0.1.42" style label so you can tell which CI build is installed. */
    fun versionLabel(pm: PackageManager, packageName: String): String =
        formatVersion(pm.getPackageInfo(packageName, 0).versionName)

    fun formatVersion(versionName: String?): String =
        if (versionName.isNullOrBlank()) "v?" else "v$versionName"
}
