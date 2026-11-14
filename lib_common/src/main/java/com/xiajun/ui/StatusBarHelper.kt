package com.xiajun.ui

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

object StatusBarHelper {

    fun setup2edgeCompat(a: Activity, useDarkIcons: Boolean = true) {
        WindowCompat.setDecorFitsSystemWindows(a.window, false)
        setIconAppearance(a.window, useDarkIcons)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            @Suppress("DEPRECATION")
            a.window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun setup2edge(a: ComponentActivity, applyBottomInset: Boolean = true, useDarkIcons: Boolean = true) {
        edge2edge(a)
        if (applyBottomInset) applyBottomInset(a.window.decorView)
        setIconAppearance(a.window, useDarkIcons)
    }

    fun edge2edge(a: ComponentActivity) {
        a.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(Color.WHITE, Color.BLACK)
        )
    }

    fun setIconAppearance(w: Window, useDarkIcons: Boolean = true) {
        WindowCompat.getInsetsController(w, w.decorView)
            .apply {
                isAppearanceLightStatusBars = useDarkIcons
                isAppearanceLightNavigationBars = useDarkIcons
            }
    }

    fun applyStatusBarInset(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.updatePadding(top = top)
            insets
        }
    }

    fun applyTopInset(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.updatePadding(top = top)
            insets
        }
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
            val sys = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            v.updatePadding(bottom = maxOf(ime, sys))
            insets
        }
    }
}
