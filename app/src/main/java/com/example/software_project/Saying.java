package com.example.software_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.software_project.MainActivity.setDB;

public class Saying extends AppCompatActivity {
    private Object imgbtn;

    public SQLiteDatabase db;
    public Cursor c;
    ProductDBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saying);

        Intent intent_s = getIntent();
        String s_id = intent_s.getExtras().getString("sDay");

        TextView tvcontent = (TextView) findViewById(R.id.tvcontent);
        TextView tvname = (TextView) findViewById(R.id.tvname);
        TextView tvdes = (TextView) findViewById(R.id.tvdes);
        TextView tvp_des = (TextView) findViewById(R.id.tvp_des);
        ImageView imgv = (ImageView) findViewById(R.id.imageView);

        setDB(this);
        mHelper = new ProductDBHelper(this);
        db = mHelper.getReadableDatabase();
        c = db.rawQuery("SELECT * FROM Saying Where _id='" + s_id + "'", null);
        startManagingCursor(c);

        String content="";
        String name ="";
        String des = "";
        String p_des = "";
        int p_id = 0;
        byte[] img = new byte[0];


        while (c.moveToNext()) {
            content = c.getString(1);
            name = c.getString(4);
            des = c.getString(2);
            p_id = c.getInt(3);
        }
        //c.close();

        c = db.rawQuery("SELECT * FROM Person Where P_ID='" + p_id + "'", null);

        while (c.moveToNext()) {
            p_des = c.getString(2);
            img = c.getBlob(3);
        }
        Bitmap image = BitmapFactory.decodeByteArray(img,0,img.length);

        c.close();
        db.close();

        tvcontent.setText(content);
        tvname.setText(name);
        tvdes.setText(des);
        tvp_des.setText(p_des);
        imgv.setImageBitmap(image);

        //메인 화면

        ImageButton imgbtn_main = (ImageButton) findViewById(R.id.s_imgbtn_Main);
        imgbtn_main.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent_ma = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_ma);
                //finish();
            }
        });

        //명언 액티비티
        ImageButton imgbtn_bestword = (ImageButton) findViewById(R.id.s_imgbtn_BestWord);
        imgbtn_bestword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent_bw = new Intent(getApplicationContext(), BestWord.class);
                startActivity(intent_bw);
                //finish();
            }
        });

        //커뮤니티 액티비티
        ImageButton imgbtn_community = (ImageButton) findViewById(R.id.s_imgbtn_Community);
        imgbtn_community.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent_cm = new Intent(getApplicationContext(), Community.class);
                startActivity(intent_cm);
                //finish();
            }
        });

        //책 추천 액티비티
        ImageButton imgbtn_bookrecommend = (ImageButton) findViewById(R.id.s_imgbtn_BookRecommend);
        imgbtn_bookrecommend.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent intent_br = new Intent(getApplicationContext(), BookRecommend.class);
                startActivity(intent_br);
                //finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        //this.finish();
        //super.onBackPressed();
    }
}
