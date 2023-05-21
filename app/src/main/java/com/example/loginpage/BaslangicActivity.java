package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BaslangicActivity extends AppCompatActivity {

    Button btn_giris;
    Button btn_kaydol;
    FirebaseUser baslangicKullanici;

@Override
protected void onStart(){
    super.onStart();
    baslangicKullanici= FirebaseAuth.getInstance().getCurrentUser();
    //Eğer veritabanında varsa direkt anasayfaya döner.
    if(baslangicKullanici!=null){
        startActivity(new Intent(BaslangicActivity.this,AnaActivity.class));

        finish();

    }
}

    private void startActivities(Intent intent) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baslangic);
        btn_giris=findViewById(R.id.btn_giris);
        btn_kaydol=findViewById(R.id.btn_kaydol);

        btn_giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivities(new Intent(BaslangicActivity.this,GirisActivity.class));
            }
        });
        btn_kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivities(new Intent(BaslangicActivity.this,KaydolActivity.class));
            }
        });
    }
}