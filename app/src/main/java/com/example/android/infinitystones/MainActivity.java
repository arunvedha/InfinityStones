package com.example.android.infinitystones;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
int randoms,i;
String[] stones = new String[6];
String s;
    private MediaPlayer mediaPlayer;
    ArrayList<String>stonesPicked = new ArrayList<String>();
Random rand = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        stones[0]="power stone";
        stones[1]="space stone";
        stones[2]="time stone";
        stones[3]="reality stone";
        stones[4]="soul stone";
        stones[5]="mind stone";
        Button randomStone = (Button)findViewById(R.id.random_stone);
        final LinearLayout lv = (LinearLayout)findViewById(R.id.list_stones) ;
        final LinearLayout lp = (LinearLayout)findViewById(R.id.parent_view);
        lp.setBackgroundResource(R.drawable.thanoscomplete);
        final TextView finished = (TextView)findViewById(R.id.stones_collected);
        Button reset = (Button)findViewById(R.id.reset_button);
        final TextView color = (TextView)findViewById(R.id.color_stone_view);
        color.setBackgroundColor(Color.parseColor("#FFFFFF"));

        randomStone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randoms=rand.nextInt(6);
                s = stones[randoms];
                display(s);
                switch (randoms)
                {
                    case 0: color.setBackgroundColor(Color.parseColor("#0b35ff"));
                        break;
                    case 1:  color.setBackgroundColor(Color.parseColor("#80D8FF"));
                        break;
                    case 2:  color.setBackgroundColor(Color.parseColor("#00BFA5"));
                        break;
                    case 3: color.setBackgroundColor(Color.parseColor("#D50000"));
                        break;
                    case 4:  color.setBackgroundColor(Color.parseColor("#EF5350"));
                        break;
                    case 5:  color.setBackgroundColor(Color.parseColor("#FFEC33"));
                        break;
                    default:color.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        break;
                }
              if(stonesPicked.size()==0 || !stonesPicked.contains(s)) {
                    stonesPicked.add(s);
                  TextView stoneList = new TextView(MainActivity.this);
                  stoneList.setText(s);
                  stoneList.setPadding(16,16,0,0);
                  stoneList.setTextSize(20);
                  stoneList.setTextColor(Color.parseColor("#FFFFFF"));
                  lv.addView(stoneList);
                  saveData();
                  if (stonesPicked.size()==6){
                      lv.removeAllViews();
                      stonesPicked.clear();
                      saveData();
                      Intent i = new Intent(MainActivity.this,MissioncompleteActivity.class);
                      startActivity(i);
                      mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.fingersnap);
                      mediaPlayer.start();
                      color.setBackgroundColor(Color.parseColor("#FFFFFF"));
                      display("nothing");
                  }


                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.removeAllViews();
                stonesPicked.clear();
                saveData();
                color.setBackgroundColor(Color.parseColor("#FFFFFF"));
                lp.setBackgroundResource(R.drawable.thanoscomplete);
                display("nothing");
            }
        });
    }
    private void display(String stone) {
        TextView stoneTextView = (TextView) findViewById(R.id.stone_picked_view);
        stoneTextView.setText("You have retrieved\n"+stone);
    }
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(stonesPicked);
        editor.putString("task list", json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        stonesPicked = gson.fromJson(json, type);
        if(stonesPicked==null){
            stonesPicked = new ArrayList<>();
        }
        else {
            int textViewCount = stonesPicked.size();

            TextView[] textViewArray = new TextView[textViewCount];

            for(int i = 0; i < textViewCount; i++) {
                textViewArray[i] = new TextView(MainActivity.this);
                textViewArray[i].setText(stonesPicked.get(i));
                textViewArray[i].setPadding(16,16,0,0);
                textViewArray[i].setTextSize(20);
                textViewArray[i].setTextColor(Color.parseColor("#FFFFFF"));
                final LinearLayout lv = (LinearLayout)findViewById(R.id.list_stones) ;
                lv.addView(textViewArray[i]);
            }

        }
    }

}
