package com.xiajun.ui

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

object StatusBarHelper {

    fun setup2edge(activity: ComponentActivity, applyBottomInset: Boolean = true, useDarkIcons: Boolean = true) {
        edge2edge(activity)
        if (applyBottomInset) applyBottomInset(activity)
        setIconAppearance(activity, useDarkIcons)
    }

    fun edge2edge(activity: ComponentActivity) {
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(Color.WHITE, Color.BLACK)
        )
    }

    fun setup2edgeCompat(activity: Activity, useDarkIcons: Boolean = true) {
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
        setIconAppearance(activity, useDarkIcons)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            @Suppress("DEPRECATION")
            activity.window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun setIconAppearance(activity: Activity, useDarkIcons: Boolean = true) {
        WindowCompat.getInsetsController(activity.window, activity.window.decorView)
            .apply {
                isAppearanceLightStatusBars = useDarkIcons
                isAppearanceLightNavigationBars = useDarkIcons
            }
    }

    fun applyTopInset(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val top = insets.getInsets(
                WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.displayCutout()
            ).top
            v.updatePadding(top = top)
            insets
        }
    }

    fun applyBottomInset(activity: Activity) {
        applyBottomInset(activity.window.decorView)
    }

    fun applyBottomInset(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            v.updatePadding(bottom = sys)
            insets
        }
    }

    fun applyBottomImeInset(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            v.updatePadding(bottom = maxOf(ime, sys))
            insets
        }
    }
}
