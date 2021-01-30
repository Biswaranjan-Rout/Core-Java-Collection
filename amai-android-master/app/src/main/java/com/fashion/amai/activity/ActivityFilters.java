package com.fashion.amai.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.adapter.AdapterFilterOptions;
import com.fashion.amai.adapter.AdapterFilters;
import com.fashion.amai.R;

import java.util.Arrays;
import java.util.List;

public class ActivityFilters extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerFilterOptions, recyclerFilters;
    private LinearLayoutManager linearLayoutManagerFilterOptions, linearLayoutManagerFilters;
    private AdapterFilterOptions adapterFilterOptions;
    private AdapterFilters adapterFilters;
    private List<String> filterOptionsList, filtersList;
    private TextView tvClearAll, tvClose, tvApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        filterOptionsList = Arrays.asList(getResources().getStringArray(R.array.filter_options_list));
        recyclerFilterOptions = findViewById(R.id.recycler_filter_options);
        adapterFilterOptions = new AdapterFilterOptions(this, filterOptionsList);
        linearLayoutManagerFilterOptions = new LinearLayoutManager(this);
        recyclerFilterOptions.setLayoutManager(linearLayoutManagerFilterOptions);
        recyclerFilterOptions.setAdapter(adapterFilterOptions);

        filtersList = Arrays.asList(getResources().getStringArray(R.array.filters_option_categories));
        recyclerFilters = findViewById(R.id.recycler_filters);
        adapterFilters = new AdapterFilters(this, filtersList);
        linearLayoutManagerFilters = new LinearLayoutManager(this);
        recyclerFilters.setLayoutManager(linearLayoutManagerFilters);
        recyclerFilters.setAdapter(adapterFilters);

        tvClearAll = findViewById(R.id.tv_clear_all);
        tvClose = findViewById(R.id.tv_close);
        tvApply = findViewById(R.id.tv_apply);

        tvClearAll.setOnClickListener(this);
        tvClose.setOnClickListener(this);
        tvApply.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear_all: {
                break;
            }
            case R.id.tv_close: {
                finish();
                break;
            }
            case R.id.tv_apply: {
                break;
            }
        }
    }
}
