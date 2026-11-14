package com.xiajun.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.xiajun.lib.common.R
import com.xiajun.ui.StatusBarHelper.setup2edge

abstract class BaseTitleBarActivity : BaseActivity() {
    private lateinit var layoutRoot: LinearLayout
    private lateinit var statusBar: View
    private lateinit var layoutTitle: RelativeLayout
    private lateinit var viewBack: ImageView
    private lateinit var tvTitle: TextView

    protected override fun onCreate(savedInstanceState: Bundle?) {
        setup2edge(this)
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        initTitleBar(view!!)
    }

    protected fun initTitleBar(mContentView: View) {
        layoutRoot = mContentView.findViewById(R.id.base_title_bar_layout_root)
        statusBar = mContentView.findViewById(R.id.base_title_bar_status_bar)
        layoutTitle = mContentView.findViewById(R.id.base_title_bar_layout_title)
        viewBack = mContentView.findViewById(R.id.base_title_bar_iv_back)
        tvTitle = mContentView.findViewById(R.id.base_title_bar_tv_title)
        viewBack.setOnClickListener { view: View? -> onClickTitleBack() }
        statusBar.layoutParams.height = 0
        ViewCompat.setOnApplyWindowInsetsListener(layoutRoot) { v, insets ->
            val top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.updatePadding(top = top)
            insets
        }
    }

    protected fun onClickTitleBack() {
        onBackPressed()
    }

    protected fun setTitleBarTitle(title: String?) {
        tvTitle.text = title
    }

    protected fun setTitleBarTitle(@StringRes resId: Int) {
        tvTitle.text = context.getString(resId)
    }

    protected fun addRightMenu(text: String?, whiteText: Boolean): TextView {
        val menu =
            layoutInflater.inflate(R.layout.title_bar_menu_single_text, null) as TextView
        menu.text = text
        if (whiteText) menu.setTextColor(Color.WHITE)
        else menu.setTextColor(Color.GRAY)
        val mlp = MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dp2px(40f))
        mlp.setMarginEnd(dp2px(6f))
        val lp = RelativeLayout.LayoutParams(mlp)
        lp.addRule(RelativeLayout.ALIGN_PARENT_END)
        lp.addRule(RelativeLayout.CENTER_VERTICAL)
        layoutTitle.addView(menu, lp)
        menu.setOnClickListener { _: View? -> onClickTitleOption() }
        return menu
    }

    protected fun onClickTitleOption() {
    }
}
