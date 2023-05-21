package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.loginpage.cerceve.AramaFragment;
import com.example.loginpage.cerceve.BildirimFragment;
import com.example.loginpage.cerceve.HomeFragment;
import com.example.loginpage.cerceve.ProfilFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class AnaActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment seciliCerceve=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici,new HomeFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @androidx.annotation.NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            //ana çerçeveyi çağır
                            seciliCerceve=new HomeFragment();
                            break;
                        case R.id.nav_arama:
                            //arama çerçeveyi çağır
                            seciliCerceve=new AramaFragment();
                            break;
                        case R.id.nav_ekle:
                            //çerçeve boş gönderi activitye gider
                            seciliCerceve=null;
                            startActivity(new Intent(AnaActivity.this,GonderiActivity.class));

                            break;
                        case R.id.nav_kalp:
                            //bildirim çerçevesi
                            seciliCerceve=new BildirimFragment();
                            break;
                        case R.id.nav_profil:
                            SharedPreferences.Editor editor=getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            seciliCerceve=new ProfilFragment();
                            //profil çerçeveyi çağır
                            break;





                    }

                    if(seciliCerceve!=null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici,seciliCerceve).commit();
                    }




                    return true;
                }
            };
}