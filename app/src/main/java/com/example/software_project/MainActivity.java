package com.example.software_project;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.software_project.MainActivity.setDB;


public class MainActivity extends AppCompatActivity {

    public static final String ROOT_DIR = "/data/data/com.example.software_project/databases/";
    public SQLiteDatabase db;
    public Cursor c;
    ProductDBHelper mHelper;

    private Object imgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date currentTime = Calendar.getInstance().getTime();

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());

        String month = monthFormat.format(currentTime);
        String day = dayFormat.format(currentTime);

        final String Sday = month + day;

        TextView tvSaying = (TextView) findViewById(R.id.tvSaying);
        TextView tvname = (TextView) findViewById(R.id.tvname);

        setDB(this);
        mHelper = new ProductDBHelper(this);
        db = mHelper.getReadableDatabase();
        c = db.rawQuery("SELECT * FROM Saying Where _id='" + Sday + "'", null);
        startManagingCursor(c);

        AtomicReference<String> content = new AtomicReference<>("");
        AtomicReference<String> name= new AtomicReference<>("");

        while (c.moveToNext()) {

            content.set(c.getString(1));
            name.set(c.getString(4));

        }


        c.close();
        db.close();

        tvSaying.setText(content.get());
        tvname.setText(name.get());

        //메인 화면

        ImageButton imgbtn_main = (ImageButton) findViewById(R.id.imgbtn_Main);
        imgbtn_main.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent_ma = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_ma);
                finish();
            }
        });

        //명언 액티비티
        ImageButton imgbtn_bestword = (ImageButton) findViewById(R.id.imgbtn_BestWord);
        imgbtn_bestword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent_bw = new Intent(getApplicationContext(), BestWord.class);
                startActivity(intent_bw);
                finish();
            }
        });

        //커뮤니티 액티비티
        ImageButton imgbtn_community = (ImageButton) findViewById(R.id.imgbtn_Community);
        imgbtn_community.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent_cm = new Intent(getApplicationContext(), Community.class);
                startActivity(intent_cm);
                finish();
            }
        });

        //책 추천 액티비티
        ImageButton imgbtn_bookrecommend = (ImageButton) findViewById(R.id.imgbtn_BookRecommend);
        imgbtn_bookrecommend.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent intent_br = new Intent(getApplicationContext(), BookRecommend.class);
                startActivity(intent_br);
                finish();
            }
        });

        //명언 세부
        tvSaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_s = new Intent(getApplicationContext(), Saying.class);
                intent_s.putExtra("sDay", Sday);
                startActivity(intent_s);
                finish();
            }
        });


        TextView textView = (TextView) findViewById(R.id.Txt_Main_bw);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "Maplestory Light.ttf");
        textView.setTypeface(typeface);

        TextView textView2 = (TextView) findViewById(R.id.Txt_Main_cm);
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "Maplestory Light.ttf");
        textView2.setTypeface(typeface2);

        TextView textView3 = (TextView) findViewById(R.id.Txt_Main_br);
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "Maplestory Light.ttf");
        textView3.setTypeface(typeface3);

    }

    public static
    void setDB(Context ctx) {
        File folder = new File(ROOT_DIR);
        if(folder.exists()) {
        } else {
            folder.mkdirs();
        }
        AssetManager assetManager = ctx.getResources().getAssets();
        File outfile = new File(ROOT_DIR+"sp.db");
        InputStream is = null;
        FileOutputStream fo = null;
        long filesize = 0;
        try {
            is = assetManager.open("sp.db", AssetManager.ACCESS_BUFFER);
            filesize = is.available();
            if (outfile.length() <= 0) {
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            } else {}
        } catch (IOException e) {}
    }
}
