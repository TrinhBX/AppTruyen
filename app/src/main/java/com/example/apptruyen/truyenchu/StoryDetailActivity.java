package com.example.apptruyen.truyenchu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.apptruyen.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoryDetailActivity extends AppCompatActivity {
    private TextView txtStoryName,txtAuthor,txtStatus,txtType, txtChapterTotal;
    private TextView txtLatestChapter, txtReviewContent;
    private ImageView avatar;
    private LinearLayout chapterListLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private GridView gvListStoryOfAuthor;
    private ColumnStoryListAdapter columnStoryListAdapter;
    private NestedScrollView scrollView;
    private static final String URL = "https://mis58pm.000webhostapp.com/GetChapterList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        Intent intent = getIntent();
        final Story story =(Story)intent.getSerializableExtra("story");

        mapping();
        setSupportActionBar(toolbar);
        txtAuthor.setText("Tác giả: "+story.getAuthor());
        txtStatus.setText("Tình Trạng: "+story.getStatus());
        txtType.setText("Thể loại: "+story.getType());
        txtReviewContent.setText(story.getReview());
        VolleySingleton.getInstance(StoryDetailActivity.this).getTotalChapter(URL,story.getIdStory(),txtChapterTotal);
        VolleySingleton.getInstance(StoryDetailActivity.this).setImage(story.getAvatar(),avatar);

        if(story!=null){
            getChapterList(story.getIdStory());
            List<Story> listStoryOfAuthor = new ArrayList<>();
            columnStoryListAdapter = new ColumnStoryListAdapter(this,R.layout.column_story_list, listStoryOfAuthor,listStoryOfAuthor.size());
            gvListStoryOfAuthor.setAdapter(columnStoryListAdapter);
            VolleySingleton.getInstance(this).getStoryList("author",story.getAuthor(),listStoryOfAuthor,columnStoryListAdapter);
        }

        chapterListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(StoryDetailActivity.this, ChapterListActivity.class);
                sendIntent.putExtra("idStory",story.getIdStory());
                sendIntent.putExtra("storyName",story.getStoryName());
                startActivity(sendIntent);
            }
        });

        collapsingToolbarLayout.setTitle(story.getStoryName());
    }

    private void mapping(){
        txtStoryName = (TextView) findViewById(R.id.txtStoryName);
        txtAuthor = (TextView)findViewById(R.id.txtAuthor);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        txtType = (TextView)findViewById(R.id.txtType);
        txtChapterTotal =(TextView) findViewById(R.id.txtChapterTotal);
        txtLatestChapter = (TextView)findViewById(R.id.txtLatestChapter);
        txtReviewContent = (TextView)findViewById(R.id.txtReviewContent);
        chapterListLayout =(LinearLayout)findViewById(R.id.chapterListLayout);
        avatar = (ImageView)findViewById(R.id.imgAvatar);
        toolbar = (Toolbar) findViewById(R.id.storyDetailToolbar);
        gvListStoryOfAuthor = (GridView)findViewById(R.id.gvStoryOfAuthor);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        scrollView = (NestedScrollView)findViewById(R.id.scrollView);
        scrollView.scrollTo(0,0);
    }

    private void getChapterList(final int idStory){
        StringRequest getChapter = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray chapterList = new JSONArray(response);
                    JSONObject latestChapter = chapterList.getJSONObject(chapterList.length()-1);
                    txtLatestChapter.setText(latestChapter.getString("ChapterName"));
                    txtChapterTotal.setText("Số chương: "+chapterList.length());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StoryDetailActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idStory",""+idStory);
                return params;
            }
        };

        VolleySingleton.getInstance(this).getRequestQueue().add(getChapter);
    }
}
