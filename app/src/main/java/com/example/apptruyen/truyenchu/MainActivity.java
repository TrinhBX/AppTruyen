package com.example.apptruyen.truyenchu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.apptruyen.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvHotStories, lvMostViewStories;
    private GridView gvNewStories, gvCompletedStories;
    private TextView txtNewStoriesMore, txtHotStoriesMore, txtCompletedStoriesMore,txtMostViewStoriesMore;
    private TextView txtNewStories, txtHotStories, txtCompletedStories,txtMostViewStories;
    private TextView txtMenuCategory;
    private ColumnStoryListAdapter newStoriesAdapter,completedStoriesAdapter;
    private RowStoryListAdapter hotStoriesAdapter,mostViewStoriesAdapter;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapping();
        setValue();

        setValueForMoreText(txtNewStoriesMore,txtNewStories,"","");
        setValueForMoreText(txtHotStoriesMore, txtHotStories,"","");
        setValueForMoreText(txtCompletedStoriesMore, txtCompletedStories,"status","hoan thanh");
        setValueForMoreText(txtMostViewStoriesMore,txtMostViewStories,"","");

        txtMenuCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });

    }

        private void mapping(){
        //Anh xa
        lvHotStories = (ListView) findViewById(R.id.lvHotStories);
        lvMostViewStories = (ListView) findViewById(R.id.lvMostViewStories);
        gvNewStories = (GridView) findViewById(R.id.gvNewStories);
        gvCompletedStories = (GridView) findViewById(R.id.gvCompletedStories);

        txtNewStoriesMore = (TextView) findViewById(R.id.txtNewStoriesMore);
        txtHotStoriesMore = (TextView) findViewById(R.id.txtHotStoriesMore);
        txtCompletedStoriesMore = (TextView) findViewById(R.id.txtCompletedStoriesMore);
        txtMostViewStoriesMore = (TextView) findViewById(R.id.txtMostViewStoriesMore);

        txtNewStories = (TextView) findViewById(R.id.txtNewStories);
        txtHotStories = (TextView) findViewById(R.id.txtHotStories);
        txtCompletedStories = (TextView) findViewById(R.id.txtCompletedStories);
        txtMostViewStories = (TextView) findViewById(R.id.txtMostViewStories);

        txtMenuCategory = (TextView) findViewById(R.id.txtMenuCategory);
    }

    private void setValue(){
        //Do du lieu cho gvNewStories
        List<Story> newStoryList = new ArrayList<>();
        newStoriesAdapter = new ColumnStoryListAdapter(this,R.layout.column_story_list, newStoryList,4);
        gvNewStories.setAdapter(newStoriesAdapter);
        VolleySingleton.getInstance(this).getStoryList("","",newStoryList,newStoriesAdapter,4);

        //Do du lieu cho lvHotStories
        List<Story> hotStoryList = new ArrayList<>();
        hotStoriesAdapter = new RowStoryListAdapter(this,R.layout.row_story_list, hotStoryList, 4);
        lvHotStories.setAdapter(hotStoriesAdapter);
        VolleySingleton.getInstance(this).getStoryList("","",hotStoryList,hotStoriesAdapter,4);

        //Do du lieu cho gvCompletedStories
        List<Story> listCompletedStories = new ArrayList<>();
        completedStoriesAdapter = new ColumnStoryListAdapter(this,R.layout.column_story_list, listCompletedStories,4);
        gvCompletedStories.setAdapter(completedStoriesAdapter);
        VolleySingleton.getInstance(this).getStoryList("status","hoan thanh",listCompletedStories,completedStoriesAdapter,4);

        //Do du lieu cho lvHotStories
        List<Story> listMostViewStory = new ArrayList<>();
        mostViewStoriesAdapter = new RowStoryListAdapter(this,R.layout.row_story_list, listMostViewStory,4);
        lvMostViewStories.setAdapter(mostViewStoriesAdapter);
        VolleySingleton.getInstance(this).getStoryList("","",listMostViewStory,mostViewStoriesAdapter,4);
    }

    private void setValueForMoreText(final TextView txtMore, final TextView label, final String column, final String type) {
        txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                intent.putExtra("label", label.getText().toString());
                intent.putExtra("column",column);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, SearchStoryActivity.class);
        //startActivity(intent);
        return true;
    }
}
