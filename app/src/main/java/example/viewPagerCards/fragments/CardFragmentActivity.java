package example.viewPagerCards.fragments;

import android.content.Context;
import android.os.Bundle;

import com.xiajun.base.BaseTitleBarActivity;
import com.xiajun.app.MyApp.R;
import com.xiajun.app.MyApp.databinding.ViewPagerCardsFragmentsActivityBinding;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import example.viewPagerCards.ShadowTransformer;

public class CardFragmentActivity extends BaseTitleBarActivity {

    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private List<CardFragment> mFragments;
    private ViewPagerCardsFragmentsActivityBinding bd;

    @Override
    protected void onCreate(@Nullable Bundle arg0) {
        super.onCreate(arg0);
        bd = ViewPagerCardsFragmentsActivityBinding.inflate(getLayoutInflater());
        setContentView(bd.getRoot());
        setTitleBarTitle("fragments");

        bd.cardTypeBtn.setOnClickListener(v -> {
            bd.viewPager.setCurrentItem(4, true);
        });
        mFragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CardFragment f = new CardFragment();
            Bundle b = new Bundle();
            b.putInt("data", i);
            f.setArguments(b);
            mFragments.add(f);
        }
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), mFragments, dpToPixels(2, this));
        mFragmentCardShadowTransformer = new ShadowTransformer(bd.viewPager, mFragmentCardAdapter);
        bd.viewPager.setAdapter(mFragmentCardAdapter);
        bd.viewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        bd.viewPager.setOffscreenPageLimit(3);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}
