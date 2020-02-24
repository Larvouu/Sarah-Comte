package com.example.ghias.sarahcompte;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MenuPrincipal extends AppCompatActivity {

    //DECLARATION DES CONSTANTES
    public static final String EXTRA_DIFFICULTE= "com.example.ghiassi.sarahcompte.EXTRA_DIFFICULTE";
    public static final String EXTRA_MODE_DE_JEU= "com.example.ghiassi.sarahcompte.EXTRA_MODE_DE_JEU";

    //DECLARATION DES VARIABLES
    String difficulte = "aucune";
    String mode_de_jeu = "aucune";
    boolean deja_clique_sur_jouer = false;
    boolean deja_clique_sur_options = false;
    // DECLARATION DES BOUTONS
    Button settings_btn = null;
    Button jouer_btn = null;
    Button statistiques_btn = null;
    Button quitter_btn = null;
    //DECLARATION DES SOUS-BOUTONS DE JOUER
    Button addition_btn = null;
    Button soustraction_btn = null;
    Button division_btn = null;
    Button multiplication_btn = null;
    //DECLARATION DES SOUS SOUS BOUTONS
    Button deb_btn, int_btn, pro_btn, exp_btn = null;
    //SOUS BOUTONS DE SETTINGS
    Button envoyer_commentaire_btn = null;
    Button theme_btn = null;
    Button notifs_btn = null;
    //DECLARATION DES LINEARLAYOUT
    LinearLayout linear_layout_menu_principal = null;
    LinearLayout linear_layout_sous_btn = null;
    LinearLayout linear_layout_sous_sous_btn = null;
    LinearLayout linear_layout_sous_btn_settings = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        //AFFILIATION DES VIEWS
        //boutons
        settings_btn = (Button) findViewById(R.id.settings_btn);
        jouer_btn = (Button) findViewById(R.id.jouer_btn);
        statistiques_btn = (Button) findViewById(R.id.statistiques_btn);
        envoyer_commentaire_btn = (Button) findViewById(R.id.envoyer_commentaire_btn);
        quitter_btn = (Button) findViewById(R.id.quitter_btn);
        //sous-boutons de jouer
        addition_btn = (Button) findViewById(R.id.addition_btn);
        soustraction_btn = (Button) findViewById(R.id.soustraction_btn);
        division_btn = (Button) findViewById(R.id.division_btn);
        multiplication_btn = (Button) findViewById(R.id.multiplication_btn);
        //leur LinearLayout
        linear_layout_sous_btn = (LinearLayout) findViewById(R.id.linear_layout_sous_btn);
        //sous-sous-boutons de jouer
        deb_btn = (Button) findViewById(R.id.deb_btn);
        int_btn = (Button) findViewById(R.id.int_btn);
        pro_btn = (Button) findViewById(R.id.pro_btn);
        exp_btn = (Button) findViewById(R.id.exp_btn);
        //leur LinearLayout
        linear_layout_sous_sous_btn = (LinearLayout) findViewById(R.id.linear_layout_sous_sous_btn);
        linear_layout_menu_principal = (LinearLayout) findViewById(R.id.linear_layout_menu_principal);
        //Settings
        linear_layout_sous_btn_settings = (LinearLayout) findViewById(R.id.linear_layout_sous_btn_settings);
        theme_btn = (Button) findViewById(R.id.theme_btn);
        notifs_btn = (Button) findViewById(R.id.notif_btn);

        //Animation
        final Animation depop_btn_anim = AnimationUtils.loadAnimation(MenuPrincipal.this, R.anim.depop_sous_btn);
        final Animation pop_btn_anim = AnimationUtils.loadAnimation(MenuPrincipal.this, R.anim.pop_sous_btn);

        //CLIC SUR LES DIFFERENTS BOUTONS
        //Clic sur Jouer
        jouer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Premier clic : On affiche les sous boutons l'un après l'autre avec l'animation
                if(!deja_clique_sur_jouer){
                afficher_les_boutons_de_jouer(pop_btn_anim, linear_layout_sous_btn, addition_btn, soustraction_btn, multiplication_btn, division_btn);
                deja_clique_sur_jouer = true;
                }
                //Deuxième clic : On masque les sous boutons l'un après l'autre avec l'animation
                else if(deja_clique_sur_jouer == true)
                {
                    //On reset les boutons selectionnés en désélectionés
                    addition_btn.setBackgroundResource(R.drawable.button_background);
                    soustraction_btn.setBackgroundResource(R.drawable.button_background);
                    division_btn.setBackgroundResource(R.drawable.button_background);
                    multiplication_btn.setBackgroundResource(R.drawable.button_background);
                    //Masquage des boutons
                    masquer_les_boutons_de_jouer(depop_btn_anim, linear_layout_sous_btn, addition_btn, soustraction_btn, multiplication_btn, division_btn);
                    deja_clique_sur_jouer=false;
                }
                //On masque les sous sous boutons si ils sont affichés
                if(linear_layout_sous_sous_btn.getVisibility() == View.VISIBLE) {
                    masquer_les_boutons_de_jouer(depop_btn_anim, linear_layout_sous_sous_btn, deb_btn, int_btn, pro_btn, exp_btn);
                }
            }
        });
        //Clic sur Additions
        addition_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Le mode de jeu se voit attribuer une valeur
                mode_de_jeu = "addition";
                //la fonction qui fait tout
                afficher_masquer_boutons((Button) v);
                addition_btn.setBackgroundResource(R.drawable.button_background_selected);
                afficher_les_boutons_de_jouer(pop_btn_anim, linear_layout_sous_sous_btn, deb_btn, int_btn, pro_btn, exp_btn);
            }
        });
        //Clic sur Soustraction
        soustraction_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode_de_jeu = "soustraction";
                afficher_masquer_boutons((Button) v);
                soustraction_btn.setBackgroundResource(R.drawable.button_background_selected);
                afficher_les_boutons_de_jouer(pop_btn_anim, linear_layout_sous_sous_btn, deb_btn, int_btn, pro_btn, exp_btn);
            }
        });
        //Clic sur division
        division_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode_de_jeu = "division";
                afficher_masquer_boutons((Button) v);
                division_btn.setBackgroundResource(R.drawable.button_background_selected);
                afficher_les_boutons_de_jouer(pop_btn_anim, linear_layout_sous_sous_btn, deb_btn, int_btn, pro_btn, exp_btn);
            }
        });
        //Clic sur Multiplication
        multiplication_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode_de_jeu = "multiplication";
                afficher_masquer_boutons((Button) v);
                multiplication_btn.setBackgroundResource(R.drawable.button_background_selected);
                afficher_les_boutons_de_jouer(pop_btn_anim, linear_layout_sous_sous_btn, deb_btn, int_btn, pro_btn, exp_btn);
            }
        });
        //Clic sur Débutant
        deb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulte = "debutant";
                lancer_la_bonne_activity();
            }
        });
        //Clic sur Intermediaire
        int_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulte = "intermediaire";
                lancer_la_bonne_activity();
            }
        });
        //Clic sur Pro
        pro_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulte = "pro";
                lancer_la_bonne_activity();
            }
        });
        //Clic sur Expert
        exp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulte = "expert";
                lancer_la_bonne_activity();
            }
        });

        //Clic sur Statistiques
        statistiques_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(MenuPrincipal.this, Statistiques.class);
                startActivity(i);
            }
        });

        //Clic sur Options
        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!deja_clique_sur_options)
                {
                    afficher_les_boutons_de_options(pop_btn_anim, linear_layout_sous_btn_settings, theme_btn, envoyer_commentaire_btn, notifs_btn );
                    deja_clique_sur_options = true;
                }
                else if(deja_clique_sur_options == true)
                {
                    masquer_les_boutons_de_options(depop_btn_anim, linear_layout_sous_btn_settings, theme_btn, envoyer_commentaire_btn, notifs_btn);
                    deja_clique_sur_options = false;
                }
            }
        });

        //Clic sur envoyer commentaire
        envoyer_commentaire_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ouvre la page associée sur le PlayStore
                try{
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + /* getPackageName()*/ "com.MaglStudio.TastyClouds")));
                } catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + "com.MaglStudio.TastyClouds")));
                }
            }
        });
        //Clic sur Quitter
        quitter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(1);
            }
        });
    }

    //On ne laisse affiché que le sous bouton selectionné
    public void afficher_masquer_boutons(Button b)
    {
        //On masque TOUS les boutons...
        addition_btn.setVisibility(View.GONE);
        soustraction_btn.setVisibility(View.GONE);
        division_btn.setVisibility(View.GONE);
        multiplication_btn.setVisibility(View.GONE);
        ///Pour ensuite réafficher celui sur lequel on a cliqué.
        b.setVisibility(View.VISIBLE);
    }

    //On lance l'activité correspondante au choix de l'utilisateur
    //En se basant sur le seul sous bouton encore visible
    public void lancer_la_bonne_activity()
    {
        String difficulte_envoyee = difficulte;
        String mode_de_jeu_envoye = mode_de_jeu;
        //On utilise un intent pour passer d'une activity à l'autre
        final Intent i = new Intent(MenuPrincipal.this, Multiplication.class);

        //En envoyant à la prochaine activité les variables suivantes
        i.putExtra(EXTRA_DIFFICULTE, difficulte_envoyee);
        i.putExtra(EXTRA_MODE_DE_JEU, mode_de_jeu_envoye);

        //Puis on lance l'activité qui va recevoir la variable
        startActivity(i);
    }

    //Gestion des apparitions des boutons et sous boutons (pas que de jouer) avec les animation pop/depop
    public void afficher_les_boutons_de_jouer(final Animation anim, LinearLayout lin_lay, Button b1, final Button b2, final Button b3, final Button b4)
    {

        //On rend les sous boutons associés visibles
        lin_lay.setVisibility(View.VISIBLE);
        //linear_layout_sous_btn.startAnimation(pop_btn_anim);
        b1.setVisibility(View.VISIBLE);
        b1.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b2.setVisibility(View.VISIBLE);
                b2.startAnimation(anim);
            }
        },400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b3.setVisibility(View.VISIBLE);
                b3.startAnimation(anim);
            }
        },800);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b4.setVisibility(View.VISIBLE);
                b4.startAnimation(anim);
            }
        },1200);
    }

    //Gestion des désapparitions des boutons et sous boutons (pas que de jouer) avec les animation pop/depop
    public void masquer_les_boutons_de_jouer(final Animation anim, final LinearLayout lin_lay, final Button b1, final Button b2, final Button b3, final Button b4)
    {

        b4.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b4.setVisibility(View.GONE);
                b3.startAnimation(anim);
            }
        },200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b3.setVisibility(View.GONE);
                b2.startAnimation(anim);
            }
        },400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                b2.setVisibility(View.GONE);
                b1.startAnimation(anim);
            }
        },600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                b1.setVisibility(View.GONE);
                lin_lay.setVisibility(View.GONE);
            }
        },800);
    }

    public void afficher_les_boutons_de_options(final Animation anim, LinearLayout lin_lay, Button b1, final Button b2, final Button b3)
    {
        //On rend les sous boutons associés visibles
        lin_lay.setVisibility(View.VISIBLE);
        //linear_layout_sous_btn.startAnimation(pop_btn_anim);
        b1.setVisibility(View.VISIBLE);
        b1.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b2.setVisibility(View.VISIBLE);
                b2.startAnimation(anim);
            }
        },400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b3.setVisibility(View.VISIBLE);
                b3.startAnimation(anim);
            }
        },800);
    }

    public void masquer_les_boutons_de_options(final Animation anim, final LinearLayout lin_lay, final Button b1, final Button b2, final Button b3)
    {
        b3.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b3.setVisibility(View.GONE);
                b2.startAnimation(anim);
            }
        },200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b2.setVisibility(View.GONE);
                b1.startAnimation(anim);
            }
        },400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                b1.setVisibility(View.GONE);
                lin_lay.setVisibility(View.GONE);
            }
        },600);
    }
}
