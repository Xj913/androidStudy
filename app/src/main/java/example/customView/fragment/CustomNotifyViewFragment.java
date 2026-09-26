package example.customView.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiajun.base.BaseFragment;
import com.xiajun.app.MyApp.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class CustomNotifyViewFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_notifyview, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
