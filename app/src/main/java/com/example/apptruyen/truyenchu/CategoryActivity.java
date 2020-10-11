package com.example.apptruyen.truyenchu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.apptruyen.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private RowStoryListAdapter rowStoryListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        //mapping
        Intent getIntent = getIntent();

        listView = (ListView) findViewById(R.id.listView);
        toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent.getStringExtra("label"));


        List<Story> storyArrayList = new ArrayList<>();
        rowStoryListAdapter = new RowStoryListAdapter(this,R.layout.row_category, storyArrayList, storyArrayList.size());
        listView.setAdapter(rowStoryListAdapter);
        VolleySingleton.getInstance(this).getStoryList(getIntent.getStringExtra("column"),getIntent.getStringExtra("type"),storyArrayList,rowStoryListAdapter);
    }
}
