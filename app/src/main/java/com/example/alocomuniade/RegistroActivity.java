package com.example.alocomuniade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegistroActivity extends AppCompatActivity {

    EditText nome, email, senha;
    Button registro;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nome = findViewById(R.id.nome_id);
        email = findViewById(R.id.email_id);
        senha = findViewById(R.id.senha_id);
        registro = findViewById(R.id.registro_bt);
        login = findViewById(R.id.login_tx);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.barprogress);



        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String password = senha.getText().toString().trim();

                // Teste de conteúdo das informações

                if(TextUtils.isEmpty(Email)){
                    email.setError("Email está vazio!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    senha.setError("Senha está vazia!");
                    return;
                }
                 if(password.length()<6){
                     senha.setError("Senha requer mais de 6 caracteres");
                     return;
                 }
                 progressBar.setVisibility(View.VISIBLE);

                 // Registro efetico no FireBase

                fAuth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistroActivity.this,"Usuário criado com sucesso!",Toast.LENGTH_SHORT).show();
                            //Entra na área de login
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }else{
                            Toast.makeText(RegistroActivity.this,"ERRO ao registrar usuário!",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }
}
