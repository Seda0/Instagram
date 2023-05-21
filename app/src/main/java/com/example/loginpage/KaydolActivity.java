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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class KaydolActivity extends AppCompatActivity {
    EditText edit_kullaniciAdi,edit_Ad,edit_Email,edit_Sifre;
    Button btn_kaydol_activity;
    TextView txt_GirisSayfasinaGit;
    FirebaseAuth yetki;
    DatabaseReference yol;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaydol);
        edit_kullaniciAdi=findViewById(R.id.edit_kullaniciAdi);
        edit_Ad=findViewById(R.id.edit_Ad);
        edit_Email=findViewById(R.id.edit_Email);
        edit_Sifre=findViewById(R.id.edit_Sifre);

        btn_kaydol_activity=findViewById(R.id.btn_kaydol_activity);
       txt_GirisSayfasinaGit=findViewById(R.id.txt_GirisSayfasina_git);

       yetki=FirebaseAuth.getInstance();
       txt_GirisSayfasinaGit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               startActivities(new Intent(KaydolActivity.this,GirisActivity.class));
           }

           private void startActivities(Intent intent) {
           }
       });


       btn_kaydol_activity.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               pd=new ProgressDialog(KaydolActivity.this);
               pd.setMessage("Lütfen Bekleyin.");
               pd.show();

               String str_kullaniciAdi=edit_kullaniciAdi.getText().toString();
               String str_Ad=edit_Ad.getText().toString();
               String str_Email=edit_Email.getText().toString();
               String str_Sifre=edit_Sifre.getText().toString();
               if(TextUtils.isEmpty(str_kullaniciAdi)||TextUtils.isEmpty(str_Ad)||TextUtils.isEmpty(str_Email)||TextUtils.isEmpty(str_Sifre))
               {
                   Toast.makeText(KaydolActivity.this, "Lütfen Bütün Alanları Doldurun!", Toast.LENGTH_SHORT).show();
               }
               else if (str_Sifre.length()<6)
               {
                   Toast.makeText(KaydolActivity.this, "Şifre Altıdan Fazla Karakter Olamaz!", Toast.LENGTH_SHORT).show();
               }
               else{
                   //yeni kullanıcı kayıt etmee


                   kaydet(str_kullaniciAdi,str_Ad,str_Email,str_Sifre);
               }


           }
       });
    }
    private void kaydet(String kullanıcıadi,String ad,String email,String sifre){
        //yeni kullanıcı kayıt etmee
        yetki.createUserWithEmailAndPassword(email,sifre)
                .addOnCompleteListener(KaydolActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){

                            FirebaseUser firebaseKullanici= yetki.getCurrentUser();
                            String kullaniciId=firebaseKullanici.getUid();
                            yol= FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(kullaniciId);
                            HashMap<String, Object> hashMap=new HashMap<>();
                            hashMap.put("id",kullaniciId);
                            String kullaniciadi = null;
                            hashMap.put("kullaniciadi",kullaniciadi.toLowerCase());
                            hashMap.put("ad",ad);
                            hashMap.put("bio","");
                            hashMap.put("resimurl","https://firebasestorage.googleapis.com/v0/b/loginpage-32abd.appspot.com/o/placeholder.png?alt=media&token=4c685bf7-b341-4c9a-b89e-79de28655c4f");

                            yol.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>(){
                                @Override
                                public void onComplete(@NonNull Task<Void> task){
                                    if(task.isSuccessful()){

                                        pd.dismiss();
                                        Intent intent=new Intent(KaydolActivity.this,AnaActivity.class);

                                        //Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivities(new Intent[]{intent});
                                    }

                                }

                            });

                        }
                        else{
                            pd.dismiss();
                            Toast.makeText(KaydolActivity.this, "Bu mail veya şifre ile kayıt başarısız.", Toast.LENGTH_LONG).show();
                        }


                    }
                });

    }
}