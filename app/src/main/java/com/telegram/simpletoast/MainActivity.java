package com.telegram.simpletoast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;

import static com.telegram.simpletoast.Utils.getAccounts;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter adapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, getAccounts(), (view, position) ->
                new Toaster(adapter, this, String.format("%s %s", getString(R.string.with), view.getName()), getString(R.string.cancel), 5, position).run()
        );
        recyclerView.setAdapter(adapter);
    }
}
