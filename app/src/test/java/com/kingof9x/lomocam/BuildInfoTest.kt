package com.kingof9x.lomocam

import org.junit.Assert.assertEquals
import org.junit.Test

class BuildInfoTest {
    @Test
    fun formatsVersionName() {
        assertEquals("v0.1.42", BuildInfo.formatVersion("0.1.42"))
    }

    @Test
    fun handlesMissingVersionName() {
        assertEquals("v?", BuildInfo.formatVersion(null))
        assertEquals("v?", BuildInfo.formatVersion(""))
    }
}

class CrashReportTest {
    @Test
    fun includesVersionThreadAndCause() {
        val report = CrashReport.format("v0.1.9", "main", IllegalStateException("boom"))
        org.junit.Assert.assertTrue(report.startsWith("LomoCam v0.1.9 crashed on thread \"main\""))
        org.junit.Assert.assertTrue(report.contains("IllegalStateException: boom"))
    }
}
