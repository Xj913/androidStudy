package example.viewPager;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dmcbig.mediapicker.entity.Media;
import com.xiajun.base.BaseRecyclerViewAdapter;
import com.xiajun.base.BaseTitleBarActivity;
import com.xiajun.app.MyApp.R;
import com.xiajun.app.MyApp.databinding.GlideListTestBinding;
import com.xiajun.view.diviver.DividerItemDecoration;

import java.util.ArrayList;


public class GlideListActivity extends BaseTitleBarActivity {

    GlideListTestBinding bd;
    private ArrayList<Media> dataList;
    private GlideListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = GlideListTestBinding.inflate(getLayoutInflater());
        setContentView(bd.getRoot());
        setTitleBarTitle("glide测试");

        dataList = new ArrayList<>();
        dataList = getIntent().getParcelableArrayListExtra("list");

        adapter = new GlideListAdapter(this, dataList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        bd.recyclerView.setLayoutManager(layoutManager);
        bd.recyclerView.addItemDecoration(new DividerItemDecoration(this));
        bd.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {

            }
        });
    }


}
