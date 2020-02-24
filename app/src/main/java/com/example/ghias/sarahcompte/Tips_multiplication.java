package com.example.ghias.sarahcompte;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;

public class Tips_multiplication extends Dialog{

    Button btn_tips_multiplication = null;

    //Constructeur requis
    public Tips_multiplication(Activity activity)
    {
        super(activity, R.style.Theme_AppCompat_Dialog);
        setContentView(R.layout.tips_multiplication);

        this.btn_tips_multiplication = (Button) findViewById(R.id.btn_tips_multiplication);
    }

    public Button getBtn_tips_multiplication() {
        return btn_tips_multiplication;
    }

    public void build(){show();}
}
