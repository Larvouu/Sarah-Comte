package com.example.ghias.sarahcompte;

//POUR ENLEVER L ACTION BAR DU HAUT
//  Styles.xml  xxx.NoActionBar au lieu de xxx.DarkActionBar


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    private TextView tv = null;
    private ImageView iv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);

        Animation splash_anim = AnimationUtils.loadAnimation(this, R.anim.splash_transition);
        tv.startAnimation(splash_anim);
        iv.startAnimation(splash_anim);

        final Intent i = new Intent(this, MenuPrincipal.class);

        Thread timer = new Thread(){
          public void run(){
              try{
                  sleep(3000);
              } catch (InterruptedException e) {
                  e.printStackTrace();;
              }
              finally {
                  startActivity(i);
                  finish();
              }
          }
        };
            timer.start();
    }
}
