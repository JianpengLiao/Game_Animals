package com.example.jianpeng.game_animals;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    public static GameActivity instance = null;

    public  int Time=3;
    public int ResultID=-1;
    public int Score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        instance=this;
        init();
    }

    protected void init(){
        ((TextView)findViewById(R.id.tv_score)).setText(Integer.toString(Score));
        Refresh();
    }


    public void Refresh(){
        String[] AnimalsList=new String[]{"Bear","Bird","Cat","Elephant","Fish","Flower","Giraffe",
                "Honey","House","Hypo","Kangaroo","Leo","Lion","Pig","Rhino","Sun","Tiger","Wolf"};
        int[] AnimalsListID=new int[]{R.drawable.bear, R.drawable.bird, R.drawable.cat,R.drawable.elephant,
                R.drawable.fish, R.drawable.flower, R.drawable.giraffe, R.drawable.honey, R.drawable.house, R.drawable.hypo,
                R.drawable.kangaroo, R.drawable.leo, R.drawable.lion, R.drawable.pig, R.drawable.rhino, R.drawable.sun,
                R.drawable.tiger, R.drawable.wolf};
        int[] ImgViewID=new int[]{R.id.iv_img1,R.id.iv_img2, R.id.iv_img3, R.id.iv_img4};

        int resultid=(int)(Math.random()*18);
        int imgviewid=(int)(Math.random()*4);
        ResultID=imgviewid;
        ((TextView)findViewById(R.id.tv_result)).setText(AnimalsList[resultid]);
        ((ImageView) findViewById(ImgViewID[imgviewid])).setImageDrawable(
                ContextCompat.getDrawable(getApplicationContext(), AnimalsListID[resultid]));

        int[] selectid=new int[]{-1,-1,-1};
        do{
            selectid[0]=(int)(Math.random()*18);
        }while (selectid[0]==resultid);
        do {
            selectid[1]=(int)(Math.random()*18);
        }while (selectid[1]==resultid || selectid[1]==selectid[0]);
        do{
            selectid[2]=(int)(Math.random()*18);
        }while (selectid[2]==resultid || selectid[2]==selectid[1]||selectid[2]==selectid[0]);

        for(int i=0,j=0;i<4;i++){
            if(i!=imgviewid){
                ((ImageView) findViewById(ImgViewID[i])).setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), AnimalsListID[selectid[j]]));
                j++;
            }
        }

    }


    private void checkResult(int select, int viewId){
        if(select==ResultID){
            Score=Score+1;
            ((TextView)findViewById(R.id.tv_score)).setText(Integer.toString(Score));

            if(Score>=10)
            {
                try
                {
                    Intent intent = new Intent(this, GreatActivity.class);
                    startActivity(intent);
                    this.finish();
                }
                catch (Exception ex)
                {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Refresh();
            }
        }
        else{
            Score=Score-1;
            Time=Time-1;
            ((TextView)findViewById(R.id.tv_score)).setText(Integer.toString(Score));
            ((TextView)findViewById(R.id.tv_time)).setText(Integer.toString(Time));

            ((ImageView) findViewById(viewId)).setImageDrawable(
                    ContextCompat.getDrawable(getApplicationContext(), R.drawable.false_icon));

            if(Time<=0)
            {
                try
                {
                    Intent intent = new Intent(this, OverActivity.class);
                    startActivity(intent);
                    this.finish();
                }
                catch (Exception ex)
                {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }


    public void onSelect0(View view) {
        int id=view.getId();
        checkResult(0, id);
    }
    public void onSelect1(View view) {
        int id=view.getId();
        checkResult(1, id);
    }
    public void onSelect2(View view) {
        int id=view.getId();
        checkResult(2, id);
    }
    public void onSelect3(View view) {
        int id=view.getId();
        checkResult(3, id);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder Dlg=new AlertDialog.Builder(this);
        Dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GameActivity.super.onBackPressed();
            }
        });
        Dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        Dlg.setMessage("\nAre you sure to end the game?");
        Dlg.setTitle("Confirm");
        Dlg.setIcon(R.mipmap.ic_launcher);
        Dlg.show();
    }
}
