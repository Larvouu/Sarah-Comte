package com.example.ghias.sarahcompte;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

import static java.lang.Thread.sleep;

public class Multiplication extends AppCompatActivity {

    //LES VARIABLES EXTRA
    public static final String EXTRA_SCORE = "com.example.ghiassi.sarahcompte.EXTRA_SCORE";
    public static final String EXTRA_DIFFICULTE_POUR_RESULTAT = "com.example.ghiassi.sarahcompte.EXTRA_DIFFICULTE_POUR_RESULTAT";
    public static final String EXTRA_MODE_DE_JEU_POUR_RESULTAT = "com.example.ghiassi.sarahcompte.EXTRA_MODE_DE_JEU_POUR_RESULTAT";
    //POUR LE TIMER
    private static final long START_TIME_IN_MILLIS_MULT_DEBUTANT = 16000; //16s
    private static final long START_TIME_IN_MILLIS_MULT_INTERMEDIAIRE = 36000; //36s
    private static final long START_TIME_IN_MILLIS_MULT_PRO = 91000; //1mn31s
    private static final long START_TIME_IN_MILLIS_MULT_EXPERT = 121000; //2mn1s
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = 0; //Prend une valeur une fois l'intent recupéré avec getIntent()
    private boolean mTimerRunning;
    TextView timer = null;
    //LE SCORE
    private int score = 0;
    private int num_calcul = 1;
    private int tmp_faux = 0;
    //LES VARIABLES QUI DETERMINENT LA DIFFICULTE
    private int nombre_un, nombre_deux = 1; //Prend une valeur en fonction de difficute_recue de l'activity MenuPrincipal
    //pour la multiplication
    private static int MULT_MIN_DEBUTANT = 1;
    private static int MULT_MIN_INTERMEDIAIRE_UN = 2;
    private static int MULT_MIN_INTERMEDIAIRE_DEUX = 11;
    private static int MULT_MIN_PRO = 3;
    private static int MULT_MIN_EXPERT = 11;
    private static int MULT_MAX_DEBUTANT = 12;
    private static int MULT_MAX_INTERMEDIAIRE_UN = 10;
    private static int MULT_MAX_INTERMEDIAIRE_DEUX = 20;
    private static int MULT_MAX_PRO = 50;
    private static int MULT_MAX_EXPERT = 99;
    //pour l'addition
    private static int ADD_MIN_DEBUTANT = 1;
    private static int ADD_MIN_INTERMEDIAIRE = 20;
    private static int ADD_MIN_PRO = 200;
    private static int ADD_MIN_EXPERT = 1000;
    private static int ADD_MAX_DEBUTANT = 30;
    private static int ADD_MAX_INTERMEDIAIRE = 100;
    private static int ADD_MAX_PRO = 500;
    private static int ADD_MAX_EXPERT = 10000;
    //pour la soustraction
    private static int SOUS_MIN_DEBUTANT = 1;
    private static int SOUS_MIN_INTERMEDIAIRE = 20;
    private static int SOUS_MIN_PRO = 200;
    private static int SOUS_MIN_EXPERT = 1000;
    private static int SOUS_MAX_DEBUTANT = 30;
    private static int SOUS_MAX_INTERMEDIAIRE = 100;
    private static int SOUS_MAX_PRO = 500;
    private static int SOUS_MAX_EXPERT = 10000;
    //pour la division
    private static int DIV_MIN_DEBUTANT = 2;
    private static int DIV_MIN_INTERMEDIAIRE = 3;
    private static int DIV_MIN_PRO = 3;
    private static int DIV_MIN_EXPERT = 3;
    private static int DIV_MAX_DEBUTANT = 30;
    private static int DIV_MAX_INTERMEDIAIRE = 70;
    private static int DIV_MAX_PRO = 300;
    private static int DIV_MAX_EXPERT = 1000;

    //DECLARATION DES VIEWS
    LinearLayout linear_layout_page = null;
    LinearLayout linear_layout_reponse_fausse = null;
    TextView titre = null;
    TextView diff_text = null;
    TextView nombre_un_txt = null;
    TextView signe_operation = null;
    TextView nombre_deux_txt = null;
    TextView resultat = null;
    TextView tips = null;
    Button btn_un, btn_deux, btn_trois, btn_quatre, btn_cinq, btn_six, btn_sept, btn_huit, btn_neuf, btn_zero = null;
    Button btn_effacer = null;
    Button btn_valider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication);

        //PREMIER TRUC A FAIRE : RECCUPERER LES VARIABLES ENVOYEES DEPUIS MENU PRINCIPAL VIA INTENT
        Intent i = getIntent();
        final String difficulte_recue = i.getStringExtra(MenuPrincipal.EXTRA_DIFFICULTE);
        final String mode_de_jeu_recu = i.getStringExtra(MenuPrincipal.EXTRA_MODE_DE_JEU);

        //On set mTimeLeftInMillis grace à la difficulte recue
        affiliation_timer(difficulte_recue);

        //AFFILIATION DES VARIABLES
        linear_layout_page = (LinearLayout) findViewById(R.id.linear_layout_page);
        linear_layout_reponse_fausse = (LinearLayout) findViewById(R.id.linear_layout_reponse_fausse);
        titre = (TextView) findViewById(R.id.titre);
        diff_text = (TextView) findViewById(R.id.diff_text);
        timer = (TextView) findViewById(R.id.timer);
        nombre_un_txt = (TextView) findViewById(R.id.nombre_un);
        nombre_deux_txt = (TextView) findViewById(R.id.nombre_deux);
        signe_operation = (TextView) findViewById(R.id.signe_operation);
        resultat = (TextView) findViewById(R.id.resultat);
        tips = (TextView) findViewById(R.id.tips);
        btn_un = (Button) findViewById(R.id.btn_un);
        btn_deux = (Button) findViewById(R.id.btn_deux);
        btn_trois = (Button) findViewById(R.id.btn_trois);
        btn_quatre = (Button) findViewById(R.id.btn_quatre);
        btn_cinq = (Button) findViewById(R.id.btn_cinq);
        btn_six = (Button) findViewById(R.id.btn_six);
        btn_sept = (Button) findViewById(R.id.btn_sept);
        btn_huit = (Button) findViewById(R.id.btn_huit);
        btn_neuf = (Button) findViewById(R.id.btn_neuf);
        btn_zero = (Button) findViewById(R.id.btn_zero);
        btn_effacer = (Button) findViewById(R.id.btn_effacer);
        btn_valider = (Button) findViewById(R.id.btn_valider);


        //On masque immédiatement le cas de la réponse fausse
        linear_layout_reponse_fausse.setVisibility(View.GONE);

        //Affiliation de la bonne difficulté
        affiliation_difficulte(difficulte_recue);
        //et du bon mode de jeu
        affiliation_mode_de_jeu(mode_de_jeu_recu);
        //Affiliation des valeurs aléatoires
        affiliation_nombre_un_nombre_deux(difficulte_recue, mode_de_jeu_recu);
        //Affiliation des TextViews du calcul
        nombre_un_txt.setText(""+nombre_un);

        nombre_deux_txt.setText(""+nombre_deux);
        //On start le timer, qui prend une valeur en fonction de la difficulté choisie
        startTimer(difficulte_recue, mode_de_jeu_recu);

        //Gestion des clics sur le pavé numérique
        btn_un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultat.setText(resultat.getText() + "1");
            }
        });
        btn_deux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultat.setText(resultat.getText() + "2");
            }
        });
        btn_trois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultat.setText(resultat.getText() + "3");
            }
        });
        btn_quatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultat.setText(resultat.getText() + "4");
            }
        });
        btn_cinq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultat.setText(resultat.getText() + "5");
            }
        });
        btn_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultat.setText(resultat.getText() + "6");
            }
        });
        btn_sept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultat.setText(resultat.getText() + "7");
            }
        });
        btn_huit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultat.setText(resultat.getText() + "8");
            }
        });
        btn_neuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultat.setText(resultat.getText() + "9");
            }
        });
        btn_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultat.setText(resultat.getText() + "0");
            }
        });
        //Reset le TextView resultat si on clique sur le bouton effacer
        btn_effacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultat.setText("");
            }
        });
        //Valide le choix
        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //La formule magique pour savoir si un textView/EditableText est rempli
                if (resultat.getText().toString().matches("")) {
                    Toast.makeText(Multiplication.this, "REPONDEZ !", Toast.LENGTH_SHORT).show();
                } else {
                    //SI LE RESULTAT ENTRE EST CORRECT
                    if (mode_de_jeu_recu.equals("multiplication") && (Float.valueOf(resultat.getText().toString()) == (nombre_un * nombre_deux))
                            || mode_de_jeu_recu.equals("addition") && (Float.valueOf(resultat.getText().toString()) == (nombre_un + nombre_deux))
                            || mode_de_jeu_recu.equals("soustraction") && (Float.valueOf(resultat.getText().toString()) == (nombre_un - nombre_deux))
                            || mode_de_jeu_recu.equals("division") && (Float.valueOf(resultat.getText().toString()) == (nombre_un / nombre_deux))){
                        //Score +1
                        score++;
                        //tmp_faux
                        tmp_faux = 0;
                        Toast.makeText(Multiplication.this, "Score : " + score, Toast.LENGTH_LONG).show();
                        //On reset le resultat et les nombres
                        changer_de_calcul(difficulte_recue, mode_de_jeu_recu);
                    }
                    //SI L UTILISATEUR SE TROMPE
                    else {
                        //On affiche "Incorrect !" + un tips
                        linear_layout_reponse_fausse.setVisibility(View.VISIBLE);
                        //On lui redonne une chance et on clear la reponse qu'il avait  entré
                        resultat.setText("");
                        //Tmp_faux = est le nombre de fois où l'utilisateur se trompe sur le calcul, si elle passe à 2, il est compté comme faux
                        tmp_faux++;
                        //SI L UTILISATEUR SE TROMPE UNE DEUXIEME FOIS
                        if (tmp_faux == 2) {
                            //reset tmp_faux pour le calcul suivant
                            tmp_faux = 0;
                            changer_de_calcul(difficulte_recue,mode_de_jeu_recu);
                            Toast.makeText(Multiplication.this, "Deux mauvaises réponses - Calcul suivant", Toast.LENGTH_LONG).show();
                        }
                    }
                    //SI LA PARTIE EST TERMINEE
                    if (num_calcul > 10) {
                        //Passage du score à l'activity Resultat via INTENT
                        int score_final = score;
                        String difficulte_envoyee = difficulte_recue;
                        String mode_de_jeu_envoye = mode_de_jeu_recu;
                        final Intent i = new Intent(Multiplication.this, Resultat.class);
                        i.putExtra(EXTRA_SCORE, score_final);
                        i.putExtra(EXTRA_DIFFICULTE_POUR_RESULTAT, difficulte_envoyee);
                        i.putExtra(EXTRA_MODE_DE_JEU_POUR_RESULTAT, mode_de_jeu_envoye);
                        startActivity(i);
                        //Stop le temps
                        pauseTimer();
                    }
                }
            }
        });

        //Clic sur tips
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Tips_multiplication tips_multiplication = new Tips_multiplication(Multiplication.this);
                tips_multiplication.getBtn_tips_multiplication().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tips_multiplication.dismiss();
                    }
                });
                tips_multiplication.build();
            }
        });
        //Mise à jour du timer
        updateCountDownText();
    }

    //PASSAGE AU CALCUL SUIVANT
    public void changer_de_calcul(String difficulte_recue, String mode_de_jeu_recu)
    {
        //On reset le resultat et les nombres
        resultat.setText("");
        affiliation_nombre_un_nombre_deux(difficulte_recue, mode_de_jeu_recu);
        //Affichage du nouveau calcul
        nombre_un_txt.setText(""+nombre_un);
        nombre_deux_txt.setText(""+nombre_deux);
        //On masque "Incorrect !"
        linear_layout_reponse_fausse.setVisibility(View.GONE);
        //num_calcul augmente
        num_calcul ++;
        //Affiche le bon numero de question
        affiliation_mode_de_jeu(mode_de_jeu_recu);
        //On restart le timer puis on le relance
        pauseTimer();
        resetTimer(difficulte_recue);
        startTimer(difficulte_recue, mode_de_jeu_recu);
    }

    //LE COUNTDOWN COMMENCE
    private void startTimer(final String difficulte_recue,final String mode_de_jeu_recu)
    {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                changer_de_calcul(difficulte_recue, mode_de_jeu_recu);
                Toast.makeText(Multiplication.this, "Temps écoulé - Calcul suivant", Toast.LENGTH_LONG).show();
            }
        }.start();

        mTimerRunning = true;
    }

    //Pause
    private void pauseTimer()
    {
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }

    //Reset
    private void resetTimer(String difficulte_recue)
    {
        affiliation_timer(difficulte_recue);
        updateCountDownText();
    }

    //Maj du timer
    private void updateCountDownText()
    {
        int seconds = (int) (mTimeLeftInMillis / 1000);
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d", seconds);
        timer.setText(timeLeftFormatted);
    }

    //AFFILIATION DES DEUX NOMBRES EN FONCTION DE LA DIFFICULTE ET DU MODE DE JEU CHOISIS
    public void affiliation_nombre_un_nombre_deux(String diff, String mode)
    {
       /* switch (diff)
        {
            case "debutant" : nombre_un = MULT_MIN_DEBUTANT + (int)(Math.random() * ((MULT_MAX_DEBUTANT - MULT_MIN_DEBUTANT) + 1));
                nombre_deux = MULT_MIN_DEBUTANT + (int)(Math.random() * ((MULT_MAX_DEBUTANT - MULT_MIN_DEBUTANT) + 1));
                break;
            case "intermediaire" : nombre_un = MULT_MIN_INTERMEDIAIRE_UN + (int)(Math.random() * ((MULT_MAX_INTERMEDIAIRE_UN - MULT_MIN_INTERMEDIAIRE_UN) + 1));
                nombre_deux = MULT_MIN_INTERMEDIAIRE_DEUX + (int)(Math.random() * ((MULT_MAX_INTERMEDIAIRE_DEUX - MULT_MIN_INTERMEDIAIRE_DEUX) + 1));
                break;
            case "pro" : nombre_un = MULT_MIN_PRO + (int)(Math.random() * ((MULT_MAX_PRO - MULT_MIN_PRO) + 1));
                nombre_deux = MULT_MIN_PRO + (int)(Math.random() * ((MULT_MAX_PRO - MULT_MIN_PRO) + 1));
                break;
            case "expert" : nombre_un = MULT_MIN_EXPERT + (int)(Math.random() * ((MULT_MAX_EXPERT - MULT_MIN_EXPERT) + 1));
                nombre_deux = MULT_MIN_EXPERT + (int)(Math.random() * ((MULT_MAX_EXPERT - MULT_MIN_EXPERT) + 1));
                break;
            default: Toast.makeText(this, "Erreur de chargement du string difficulte pour les nombres", Toast.LENGTH_LONG).show();
            break;
        }*/
        if(mode.equals("addition") && diff.equals("debutant"))
        {
            nombre_un = ADD_MIN_DEBUTANT + (int)(Math.random()*((ADD_MAX_DEBUTANT - ADD_MIN_DEBUTANT) + 1));
            nombre_deux = ADD_MIN_DEBUTANT + (int)(Math.random() * ((ADD_MAX_DEBUTANT - ADD_MIN_DEBUTANT) + 1));
        }
        else if(mode.equals("addition") && diff.equals("intermediaire"))
        {
            nombre_un = ADD_MIN_INTERMEDIAIRE + (int)(Math.random()*((ADD_MAX_INTERMEDIAIRE - ADD_MIN_INTERMEDIAIRE) + 1));
            nombre_deux = ADD_MIN_INTERMEDIAIRE + (int)(Math.random() * ((ADD_MAX_INTERMEDIAIRE - ADD_MIN_INTERMEDIAIRE) + 1));
        }
        else if(mode.equals("addition") && diff.equals("pro"))
        {
            nombre_un = ADD_MIN_PRO + (int)(Math.random()*((ADD_MAX_PRO - ADD_MIN_PRO) + 1));
            nombre_deux = ADD_MIN_PRO + (int)(Math.random() * ((ADD_MAX_PRO - ADD_MIN_PRO) + 1));
        }
        else if(mode.equals("addition") && diff.equals("expert"))
        {
            nombre_un = ADD_MIN_EXPERT + (int)(Math.random()*((ADD_MAX_EXPERT - ADD_MIN_EXPERT) + 1));
            nombre_deux = ADD_MIN_EXPERT + (int)(Math.random() * ((ADD_MAX_EXPERT - ADD_MIN_EXPERT) + 1));
        }
        else if(mode.equals("soustraction") && diff.equals("debutant"))
        {
            nombre_un = SOUS_MIN_DEBUTANT + (int)(Math.random()*((SOUS_MAX_DEBUTANT - SOUS_MIN_DEBUTANT) + 1));
            nombre_deux = SOUS_MIN_DEBUTANT + (int)(Math.random() * ((SOUS_MAX_DEBUTANT - SOUS_MIN_DEBUTANT) + 1));
            if(nombre_deux>nombre_un)
            {
                int tmp;
                tmp = nombre_un;
                nombre_un = nombre_deux;
                nombre_deux = tmp;
            }
        }
        else if(mode.equals("soustraction") && diff.equals("intermediaire"))
        {
            nombre_un = SOUS_MIN_INTERMEDIAIRE + (int)(Math.random()*((SOUS_MAX_INTERMEDIAIRE - SOUS_MIN_INTERMEDIAIRE) + 1));
            nombre_deux = SOUS_MIN_INTERMEDIAIRE + (int)(Math.random() * ((SOUS_MAX_INTERMEDIAIRE - SOUS_MIN_INTERMEDIAIRE) + 1));
            if(nombre_deux>nombre_un)
            {
                int tmp;
                tmp = nombre_un;
                nombre_un = nombre_deux;
                nombre_deux = tmp;
            }
        }
        else if(mode.equals("soustraction") && diff.equals("pro"))
        {
            nombre_un = SOUS_MIN_PRO + (int)(Math.random()*((SOUS_MAX_PRO - SOUS_MIN_PRO) + 1));
            nombre_deux = SOUS_MIN_PRO + (int)(Math.random() * ((SOUS_MAX_PRO - SOUS_MIN_PRO) + 1));
            if(nombre_deux>nombre_un)
            {
                int tmp;
                tmp = nombre_un;
                nombre_un = nombre_deux;
                nombre_deux = tmp;
            }
        }
        else if(mode.equals("soustraction") && diff.equals("expert"))
        {
            nombre_un = SOUS_MIN_EXPERT + (int)(Math.random()*((SOUS_MAX_EXPERT - SOUS_MIN_EXPERT) + 1));
            nombre_deux = SOUS_MIN_EXPERT + (int)(Math.random() * ((SOUS_MAX_EXPERT - SOUS_MIN_EXPERT) + 1));
            if(nombre_deux>nombre_un)
            {
                int tmp;
                tmp = nombre_un;
                nombre_un = nombre_deux;
                nombre_deux = tmp;
            }
        }
        else if(mode.equals("multiplication") && diff.equals("debutant"))
        {
            nombre_un = MULT_MIN_DEBUTANT + (int)(Math.random()*((MULT_MAX_DEBUTANT - MULT_MIN_DEBUTANT) + 1));
            nombre_deux = MULT_MIN_DEBUTANT + (int)(Math.random() * ((MULT_MAX_DEBUTANT - MULT_MIN_DEBUTANT) + 1));
        }
        else if(mode.equals("multiplication") && diff.equals("intermediaire"))
        {
            nombre_un = MULT_MIN_INTERMEDIAIRE_UN + (int)(Math.random() * ((MULT_MAX_INTERMEDIAIRE_UN - MULT_MIN_INTERMEDIAIRE_UN) + 1));
            nombre_deux = MULT_MIN_INTERMEDIAIRE_DEUX + (int)(Math.random() * ((MULT_MAX_INTERMEDIAIRE_DEUX - MULT_MIN_INTERMEDIAIRE_DEUX) + 1));
        }
        else if(mode.equals("multiplication") && diff.equals("pro"))
        {
            nombre_un = MULT_MIN_PRO + (int)(Math.random()*((MULT_MAX_PRO - MULT_MIN_PRO) + 1));
            nombre_deux = MULT_MIN_PRO + (int)(Math.random() * ((MULT_MAX_PRO - MULT_MIN_PRO) + 1));
        }
        else if(mode.equals("multiplication") && diff.equals("expert"))
        {
            nombre_un = MULT_MIN_EXPERT + (int)(Math.random()*((MULT_MAX_EXPERT - MULT_MIN_EXPERT) + 1));
            nombre_deux = MULT_MIN_EXPERT + (int)(Math.random() * ((MULT_MAX_EXPERT - MULT_MIN_EXPERT) + 1));
        }
        else if(mode.equals("division") && diff.equals("debutant"))
        {
            do {
                nombre_un = DIV_MIN_DEBUTANT + (int)(Math.random()*((DIV_MAX_DEBUTANT - DIV_MIN_DEBUTANT) + 1));
                nombre_deux = DIV_MIN_DEBUTANT + (int)(Math.random() * ((DIV_MAX_DEBUTANT - DIV_MIN_DEBUTANT) + 1));
            } while (nombre_un%nombre_deux != 0 || nombre_deux>=nombre_un);

        }
        else if(mode.equals("division") && diff.equals("intermediaire"))
        {
            do {
            nombre_un = DIV_MIN_INTERMEDIAIRE + (int)(Math.random()*((DIV_MAX_INTERMEDIAIRE - DIV_MIN_INTERMEDIAIRE) + 1));
            nombre_deux = DIV_MIN_INTERMEDIAIRE + (int)(Math.random() * ((DIV_MAX_INTERMEDIAIRE - DIV_MIN_INTERMEDIAIRE) + 1));
            } while (nombre_un%nombre_deux != 0|| nombre_deux>nombre_un);
        }
        else if(mode.equals("division") && diff.equals("pro"))
        {
            do {
            nombre_un = DIV_MIN_PRO + (int)(Math.random()*((DIV_MAX_PRO - DIV_MIN_PRO) + 1));
            nombre_deux = DIV_MIN_PRO + (int)(Math.random() * ((DIV_MAX_PRO - DIV_MIN_PRO) + 1));
            } while (nombre_un%nombre_deux != 0|| nombre_deux>nombre_un);
        }
        else if(mode.equals("division") && diff.equals("expert"))
        {
            do {
            nombre_un = DIV_MIN_EXPERT + (int)(Math.random()*((DIV_MAX_EXPERT - DIV_MIN_EXPERT) + 1));
            nombre_deux = DIV_MIN_EXPERT + (int)(Math.random() * ((DIV_MAX_EXPERT - DIV_MIN_EXPERT) + 1));
            } while (nombre_un%nombre_deux != 0|| nombre_deux>nombre_un);
        }
        else
            Toast.makeText(this,"Erreur affiliation des nombres", Toast.LENGTH_SHORT).show();
    }

    //AFFILIATIN DU TEMPS EN FONCTION DE LA DIFFICULTE CHOISIE
    public void affiliation_timer(String s)
    {
        switch (s)
        {
            case "debutant" : mTimeLeftInMillis = START_TIME_IN_MILLIS_MULT_DEBUTANT;
                break;
            case "intermediaire" : mTimeLeftInMillis = START_TIME_IN_MILLIS_MULT_INTERMEDIAIRE;
                break;
            case "pro" : mTimeLeftInMillis = START_TIME_IN_MILLIS_MULT_PRO;
                break;
            case "expert" : mTimeLeftInMillis = START_TIME_IN_MILLIS_MULT_EXPERT;
                break;
            default: Toast.makeText(this, "Erreur de chargement du string difficulte pour le timer", Toast.LENGTH_LONG).show();
                break;
        }
    }

    //AFFILIATION DU TEXTE AFFICHANT LA DIFFICULTE EN FONCTION DU NIVEAU CHOISI
    public void affiliation_difficulte(String s)
    {
        switch (s)
        {
            case "debutant" : diff_text.setText("Difficulté : Débutant");
                break;
            case "intermediaire" :diff_text.setText("Difficulté : Intermédiaire");
                break;
            case "pro" : diff_text.setText("Difficulté : Pro");
                break;
            case "expert" : diff_text.setText("Difficulté : Expert");
                break;
            default: Toast.makeText(this, "Erreur de chargement du string difficulte pour le texte_titre difficulte", Toast.LENGTH_LONG).show();
                break;
        }
    }

    //AFFILIATION DU TEXTE AFFICHANT LE MODE DE JEU EN FONCTION DE CELUI CHOISI
    public void affiliation_mode_de_jeu(String s)
    {
        switch (s)
        {
            //Affiliation du titre
            case "addition" : titre.setText("Addition - Question " + num_calcul);
                signe_operation.setText("+");
                break;
            case "soustraction" :titre.setText("Soustraction - Question " + num_calcul);
                signe_operation.setText("-");
                break;
            case "division" : titre.setText("Division - Question " + num_calcul);
                signe_operation.setText(":");
                break;
            case "multiplication" : titre.setText("Multiplication - Question " + num_calcul);
                signe_operation.setText("x");
                break;
            default: Toast.makeText(this, "Erreur d\'affiliation du titre mode_de_jeu et du signe du calcul", Toast.LENGTH_LONG).show();
                break;
        }
    }


}
