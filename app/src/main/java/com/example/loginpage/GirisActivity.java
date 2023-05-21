package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GirisActivity extends AppCompatActivity {
    EditText edit_Email_giris,edit_Sifre_giris;
    Button btn_Giris_activity;
    TextView txt_kayitSayfasina_git;
    FirebaseAuth girisYetkisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        edit_Email_giris=findViewById(R.id.edit_Email_giris);
        edit_Sifre_giris=findViewById(R.id.edit_Sifre_giris);
        btn_Giris_activity=findViewById(R.id.btn_Giris_activity);
        girisYetkisi=FirebaseAuth.getInstance();

        txt_kayitSayfasina_git=findViewById(R.id.txt_kayitSayfasina_git);
        txt_kayitSayfasina_git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(GirisActivity.this,KaydolActivity.class));



            }
        });
        btn_Giris_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog pdGiris= new ProgressDialog(GirisActivity.this);
                pdGiris.setMessage("Giriş yapılıyor...");
                pdGiris.show();

                String str_emailGiris=edit_Email_giris.getText().toString();
                String str_sifreGiris=edit_Sifre_giris.getText().toString();
                if(TextUtils.isEmpty(str_emailGiris)||TextUtils.isEmpty(str_sifreGiris)){

                    Toast.makeText(GirisActivity.this, "Bütün alanları doldurun!", Toast.LENGTH_LONG).show();
                }
                else{
                    //Giriş yapma kodları
                    girisYetkisi.signInWithEmailAndPassword(str_emailGiris,str_sifreGiris)
                            .addOnCompleteListener(GirisActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        DatabaseReference yolGiris= FirebaseDatabase.getInstance().getReference().
                                                 child("Kullanıcılar").child(girisYetkisi.getCurrentUser().getUid());
                                        yolGiris.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                pdGiris.dismiss();
                                                Intent intent=new Intent(GirisActivity.this,AnaActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                                startActivity(intent);
                                                finish();

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                pdGiris.dismiss();

                                            }
                                        });
                                    }
                                    else{
                                        pdGiris.dismiss();
                                        Toast.makeText(GirisActivity.this, "Giriş Başarısız!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                }





            }
        });
    }
}