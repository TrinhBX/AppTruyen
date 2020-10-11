package com.example.apptruyen.truyenchu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.apptruyen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChapterContentActivity extends AppCompatActivity {

    private static final  String URL_GET_CHAPTER_CONTENT = "http://192.168.1.36:8080/AppStories/GetChapterContent.php";
    private static final String URL = "https://mis58pm.000webhostapp.com/GetChapterList.php";
    private Intent intent;

    private TextView txtStoryName,txtChapterName,txtUploader, txtChapterContent,txtChapterList;
    private TextView txtBackHome;
    private Chapter chapterCurrent;
    private Button btnNextChapter;
    private Button btnPreviousChapter;
    private ScrollView scrollView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);


        intent = getIntent();
        chapterCurrent = (Chapter) intent.getSerializableExtra("chapter");
        sharedPreferences = getSharedPreferences("infor", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        txtStoryName = (TextView)findViewById(R.id.txtStoryName);
        txtChapterName = (TextView)findViewById(R.id.txtChapterName);
        txtUploader = (TextView)findViewById(R.id.txtUploader);
        txtChapterContent = (TextView)findViewById(R.id.txtChapterContent);
        btnNextChapter = (Button) findViewById(R.id.btnNextChapter);
        btnPreviousChapter = (Button) findViewById(R.id.btnPreviousChapter);
        txtBackHome = (TextView)findViewById(R.id.txtBackHome);
        txtChapterList = (TextView)findViewById(R.id.txtChapterList);
        scrollView =(ScrollView)findViewById(R.id.content);

        VolleySingleton.getInstance(this).getStoryName(chapterCurrent.getIdStory(),txtStoryName);

        txtBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChapterContentActivity.this, MainActivity.class));
            }
        });

        if(chapterCurrent!= null){
            getChapterContent(URL,chapterCurrent.getIdStory(),chapterCurrent.getIdChapter());
        }

        btnNextChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChapterContent(URL,chapterCurrent.getIdStory(),sharedPreferences.getInt("next",0));
                scrollView.scrollTo(0,0);
            }
        });

        btnPreviousChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.scrollTo(0,0);
                getChapterContent(URL,chapterCurrent.getIdStory(),sharedPreferences.getInt("previous",0));
            }
        });

        txtChapterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChapterContentActivity.this, ChapterListActivity.class);
                intent.putExtra("idStory",chapterCurrent.getIdStory());
                startActivity(intent);
            }
        });

    }


    private void getChapterContent(String url, final int idStory, final int idChapter){

        StringRequest getContent = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    editor.clear();
                    final JSONArray array = new JSONArray(response);
                    JSONObject next;
                    JSONObject previous;
                    for (int i=0;i<array.length();i++){
                        JSONObject chapter = array.getJSONObject(i);
                        if(chapter.getInt("IDChapter")==idChapter){
                            if(i==0){
                                txtChapterName.setText(chapter.getString("ChapterName"));
                                txtUploader.setText("Người đăng: "+chapter.getString("Uploader"));
                                txtChapterContent.setText(chapter.getString("Content")+"\n\n\n");
                                next = array.getJSONObject(i+1);
                                editor.putInt("next",next.getInt("IDChapter"));
                                editor.commit();

                            }else if(i==array.length()-1){
                                txtChapterName.setText(chapter.getString("ChapterName"));
                                txtUploader.setText("Người đăng: "+chapter.getString("Uploader"));
                                txtChapterContent.setText(chapter.getString("Content")+"\n\n\n");
                                previous = array.getJSONObject(i-1);
                                editor.putInt("previous",previous.getInt("IDChapter"));
                                editor.commit();
                            }else {
                                txtChapterName.setText(chapter.getString("ChapterName"));
                                txtUploader.setText("Người đăng: "+chapter.getString("Uploader"));
                                txtChapterContent.setText(chapter.getString("Content")+"\n\n\n");
                                previous = array.getJSONObject(i-1);
                                next = array.getJSONObject(i+1);
                                editor.putInt("next",next.getInt("IDChapter"));
                                editor.putInt("previous",previous.getInt("IDChapter"));
                                editor.commit();
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idStory",""+idStory);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(getContent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.clear();
    }
}
