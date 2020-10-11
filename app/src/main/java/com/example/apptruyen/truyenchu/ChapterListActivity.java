package com.example.apptruyen.truyenchu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apptruyen.R;

import java.util.ArrayList;
import java.util.List;

public class ChapterListActivity extends AppCompatActivity {
    private ListView lvChapterList;
    private ChapterListAdapter chapterListAdapter;
    private TextView txtBackHome;
    private EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

        txtBackHome = (TextView)findViewById(R.id.txtBackHome);
        txtBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChapterListActivity.this,MainActivity.class));
            }
        });

        final Intent getIntent = getIntent();
        lvChapterList = (ListView) findViewById(R.id.lvChapterList);

        final List<Chapter> chapterList = new ArrayList<>();
        chapterListAdapter = new ChapterListAdapter(this,R.layout.row_chapter_list,chapterList,chapterList.size());
        lvChapterList.setAdapter(chapterListAdapter);
        VolleySingleton.getInstance(this).getChapterList(getIntent.getIntExtra("idStory",0),chapterList,chapterListAdapter);

        edtSearch = (EditText)findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                VolleySingleton.getInstance(ChapterListActivity.this).getChapterList(getIntent.getIntExtra("idStory",0),chapterList,chapterListAdapter);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<Chapter> chapterList1 =  new ArrayList<>();
                chapterListAdapter = new ChapterListAdapter(ChapterListActivity.this,R.layout.row_chapter_list,chapterList1,chapterList1.size());
                lvChapterList.setAdapter(chapterListAdapter);
                VolleySingleton.getInstance(ChapterListActivity.this).getChapterList(getIntent.getIntExtra("idStory",0),chapterList1,chapterListAdapter,edtSearch.getText().toString());
            }
        });
    }
}
