package com.example.software_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.software_project.MainActivity.setDB;

public class BookRecommend extends AppCompatActivity {

    private Object imgbtn;
    private RecyclerAdapter adapter;

    public SQLiteDatabase db;
    public Cursor c;
    ProductDBHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_recommend);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        setDB(this);
        mHelper = new ProductDBHelper(this);
        db = mHelper.getReadableDatabase();
        c = db.rawQuery("SELECT * FROM Book", null);
        startManagingCursor(c);

        while (c.moveToNext()) {

            Data data = new Data();
            data.setTitle(c.getString(1));
            data.setContent(c.getString(2));
            data.setImg(c.getBlob(6));

            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();
        c.close();
        db.close();



        //메인 화면

        ImageButton imgbtn_main = (ImageButton) findViewById(R.id.br_imgbtn_Main);
        imgbtn_main.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent_ma = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_ma);
                finish();
            }
        });

        //명언 액티비티
        ImageButton imgbtn_bestword = (ImageButton) findViewById(R.id.br_imgbtn_BestWord);
        imgbtn_bestword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent_bw = new Intent(getApplicationContext(), BestWord.class);
                startActivity(intent_bw);
                finish();
            }
        });

        //커뮤니티 액티비티
        ImageButton imgbtn_community = (ImageButton) findViewById(R.id.br_imgbtn_Community);
        imgbtn_community.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent_cm = new Intent(getApplicationContext(), Community.class);
                startActivity(intent_cm);
                finish();
            }
        });

        //책 추천 액티비티
        ImageButton imgbtn_bookrecommend = (ImageButton) findViewById(R.id.br_imgbtn_BookRecommend);
        imgbtn_bookrecommend.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent intent_br = new Intent(getApplicationContext(), BookRecommend.class);
                startActivity(intent_br);
                finish();
            }
        });

        TextView textView = (TextView) findViewById(R.id.Txt_Br);
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "Maplestory Light.ttf");
        textView.setTypeface(typeface3);
    }
}