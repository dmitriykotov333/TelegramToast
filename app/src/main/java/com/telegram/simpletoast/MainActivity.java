package com.telegram.simpletoast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener<User> {

    private MyAdapter adapter;
    private Toaster toaster;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new MyAdapter();
        adapter.setData(Utils.getAccounts());
        adapter.setOnItemClick(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position, User model) {
        toaster = new Toaster()
                .setContext(MainActivity.this)
                .setMessage(String.format("%s %s", getString(R.string.with), model.getName()))
                .setButtonText(getString(R.string.cancel))
                .setTimeProgress(5)
                .setPosition(position)
                .setCallback(new Toaster.Callback<User>() {
                    @Override
                    public User delete(int position) {
                        return adapter.delete(position);
                    }

                    @Override
                    public void dismiss(int position, User user) {
                        adapter.reestablish(user, position);
                    }
                })
                .run();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toaster.dispose();
    }
}
