package com.xiajun.base;

import android.graphics.Color;

import androidx.annotation.StringRes;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiajun.lib.common.R;
import com.xiajun.ui.StatusBarHelper;

import org.jetbrains.annotations.Nullable;

public abstract class BaseTitleBarActivity extends BaseActivity {

    private LinearLayout layoutRoot;
    private View statusBar;
    private RelativeLayout layoutTitle;
    private ImageView viewBack;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarHelper.INSTANCE.setup2edge(this, true, true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initTitleBar(view);
    }

    protected void initTitleBar(View mContentView) {
        layoutRoot = mContentView.findViewById(R.id.base_title_bar_layout_root);
        statusBar = mContentView.findViewById(R.id.base_title_bar_status_bar);
        layoutTitle = mContentView.findViewById(R.id.base_title_bar_layout_title);
        viewBack = mContentView.findViewById(R.id.base_title_bar_iv_back);
        tvTitle = mContentView.findViewById(R.id.base_title_bar_tv_title);
        viewBack.setOnClickListener(view -> onClickTitleBack());
        statusBar.getLayoutParams().height = getStatusHeight();
    }

    protected void onClickTitleBack() {
        onBackPressed();
    }

    protected void setTitleBarTitle(String title) {
        tvTitle.setText(title);
    }

    protected void setTitleBarTitle(@StringRes int resId) {
        tvTitle.setText(getContext().getString(resId));
    }

    protected TextView addRightMenu(String text, boolean whiteText) {
        TextView menu = (TextView) getLayoutInflater().inflate(R.layout.title_bar_menu_single_text, null);
        menu.setText(text);
        if (whiteText)
            menu.setTextColor(Color.WHITE);
        else
            menu.setTextColor(Color.GRAY);
        ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dp2px(40f));
        mlp.setMarginEnd(dp2px(6f));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mlp);
        lp.addRule(RelativeLayout.ALIGN_PARENT_END);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutTitle.addView(menu, lp);
        menu.setOnClickListener(view -> onClickTitleOption());
        return menu;
    }

    protected void onClickTitleOption() {

    }
}
