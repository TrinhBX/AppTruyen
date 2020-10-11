package com.example.apptruyen.truyenchu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.widget.ListView;

import com.example.apptruyen.R;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout categoryTabs;
    private ListView listView;
    private RowStoryListAdapter rowStoryListAdapter;
    private List<Story> storyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        toolbar = (Toolbar) findViewById(R.id.categoryToolbar);
        setSupportActionBar(toolbar);
        categoryTabs = (TabLayout)findViewById(R.id.categoryTabs);

        listView = (ListView)findViewById(R.id.listView);
        storyList = new ArrayList<>();
        rowStoryListAdapter = new RowStoryListAdapter(CategoriesActivity.this,R.layout.row_category,storyList,storyList.size());
        listView.setAdapter(rowStoryListAdapter);
        VolleySingleton.getInstance(this).getStoryList("type","tien hiep",storyList,rowStoryListAdapter);


        categoryTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                List<Story> list = new ArrayList<>();
                RowStoryListAdapter adapter = new RowStoryListAdapter(CategoriesActivity.this,R.layout.row_category,list,list.size());
                listView.setAdapter(adapter);
                VolleySingleton.getInstance(CategoriesActivity.this).getStoryList("type",tab.getText().toString(),list,adapter);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
