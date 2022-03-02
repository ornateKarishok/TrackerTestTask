package com.example.trackertesttask.ui;

import static com.example.trackertesttask.util.DataStorage.getThemeColor;
import static com.example.trackertesttask.theme.util.ThemeManager.setCustomizedThemes;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackertesttask.util.DataStorage;
import com.example.trackertesttask.R;
import com.example.trackertesttask.model.WorkSession;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements WorkSessionAdapter.OnDeleteSessionClickListener {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomizedThemes(this, getThemeColor(this));
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.details);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        recyclerView = findViewById(R.id.list);
        List<WorkSession> workSessions = DataStorage.getListFromStorage(this);
        WorkSessionAdapter workSessionAdapter = new WorkSessionAdapter(workSessions, this);
        recyclerView.setAdapter(workSessionAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onButtonClick(int position) {
        List<WorkSession> workSessions = DataStorage.getListFromStorage(this);
        workSessions.remove(position);
        DataStorage.setListToStorage(this, workSessions);
        WorkSessionAdapter workSessionAdapter = new WorkSessionAdapter(workSessions, this);
        recyclerView.setAdapter(workSessionAdapter);

    }
}
