package com.kingof9x.lomocam

import android.app.Activity
import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Process
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

/**
 * Catches any crash and shows its details on screen (in a separate, plain-Android process
 * that doesn't depend on Compose), so problems on the Pixel can be reported without a
 * USB cable or Android Studio.
 */
class LomoCamApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val previous = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, error ->
            try {
                val intent = Intent(this, CrashReportActivity::class.java)
                    .putExtra(CrashReportActivity.EXTRA_REPORT, CrashReport.format(BuildConfigInfo.label(this), thread.name, error))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                Process.killProcess(Process.myPid())
                exitProcess(10)
            } catch (_: Throwable) {
                previous?.uncaughtException(thread, error)
            }
        }
    }
}

object CrashReport {
    fun format(version: String, threadName: String, error: Throwable): String {
        val trace = StringWriter().also { error.printStackTrace(PrintWriter(it)) }.toString()
        return "LomoCam $version crashed on thread \"$threadName\"\n\n$trace"
    }
}

private object BuildConfigInfo {
    fun label(context: Context): String = try {
        BuildInfo.versionLabel(context.packageManager, context.packageName)
    } catch (_: Throwable) {
        "v?"
    }
}

class CrashReportActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val report = intent.getStringExtra(EXTRA_REPORT) ?: "No crash details were captured."

        val text = TextView(this).apply {
            setText(report)
            setTextColor(Color.WHITE)
            textSize = 12f
            setTextIsSelectable(true)
        }
        val copy = Button(this).apply {
            setText("Copy crash details")
            setOnClickListener {
                val cm = getSystemService(ClipboardManager::class.java)
                cm.setPrimaryClip(ClipData.newPlainText("LomoCam crash", report))
                setText("Copied - paste it to Claude")
            }
        }
        val column = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 160, 48, 160)
            setBackgroundColor(Color.BLACK)
            addView(copy)
            addView(text)
        }
        setContentView(ScrollView(this).apply {
            setBackgroundColor(Color.BLACK)
            addView(column, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        })
    }

    companion object {
        const val EXTRA_REPORT = "report"
    }
}
