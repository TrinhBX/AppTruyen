package com.example.apptruyen.truyenchu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.example.apptruyen.R;

import java.util.ArrayList;
import java.util.List;

public class SearchStoryActivity extends AppCompatActivity {
    private ListView listView;
    private RowStoryListAdapter rowStoryListAdapter;
    private EditText edtSearch;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_story);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listView = (ListView) findViewById(R.id.listView);
        edtSearch = (EditText) findViewById(R.id.edtSearch);

       edtSearch.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               if(edtSearch.getText().toString().trim().length()>2){
                    List<Story> storyList = new ArrayList<>();
                    rowStoryListAdapter = new RowStoryListAdapter(SearchStoryActivity.this, R.layout.row_story_list, storyList, storyList.size());
                    listView.setAdapter(rowStoryListAdapter);
                    VolleySingleton.getInstance(SearchStoryActivity.this).getStoryList("name", edtSearch.getText().toString(), storyList, rowStoryListAdapter);
               }else {
                   List<Story> storyList = new ArrayList<>();
                   rowStoryListAdapter = new RowStoryListAdapter(SearchStoryActivity.this, R.layout.row_story_list, storyList, storyList.size());
                   listView.setAdapter(rowStoryListAdapter);
               }
           }
       });
    }
}
