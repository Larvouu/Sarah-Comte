package com.example.ghias.sarahcompte;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.os.SystemClock.sleep;

public class Resultat extends AppCompatActivity {

    TextView txt_pop_up_resultat = null;
    ProgressBar progressBar = null;
    Button replay_btn, retourMenu_btn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        Intent i = getIntent();
        final int score_pop_up = i.getIntExtra(Multiplication.EXTRA_SCORE,0);
        final String difficulte_recue_pour_resultat = i.getStringExtra(Multiplication.EXTRA_DIFFICULTE_POUR_RESULTAT);
        final String mode_de_jeu_recu_pour_resultat = i.getStringExtra(Multiplication.EXTRA_MODE_DE_JEU_POUR_RESULTAT);


        txt_pop_up_resultat = (TextView) findViewById(R.id.txt_pop_up_resultat);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        replay_btn = (Button) findViewById(R.id.replay_btn);
        retourMenu_btn = (Button) findViewById(R.id.retourMenu_btn);

            //progress bar fluide
            progressBar.setProgress(0); //Besoin de cette ligne ?
            for(int j=0; j<= (score_pop_up*10); j++)
            {
                final int finalJ = j;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(finalJ);

                    }
                },300 + j*8);
            }


        //Affichage du score
        txt_pop_up_resultat.setText("Score : "+ score_pop_up +" / 10 !");

        //Ne marchAIT pas, parce que Multiplication essayAIT de récupérer un string depuis MenuPrincipal quand l'activité ETAIT lancée
        replay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(Resultat.this, Multiplication.class);
                //On doit renvoyer la difficulté et le mode_de_jeu puisque lors du lancement de Multiplication, elle essaye de la récupérer
                i.putExtra(MenuPrincipal.EXTRA_DIFFICULTE, difficulte_recue_pour_resultat);
                i.putExtra(MenuPrincipal.EXTRA_MODE_DE_JEU, mode_de_jeu_recu_pour_resultat);
                startActivity(i);
            }
        });
        retourMenu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On ouvre simplement le menu principal
                final Intent i = new Intent(Resultat.this, MenuPrincipal.class);
                startActivity(i);
            }
        });
    }
}
