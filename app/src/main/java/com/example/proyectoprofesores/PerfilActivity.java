package com.example.proyectoprofesores;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {
    ImageView back;
    Button actualizar;
    EditText edNombre,edApellido,edCurso,edTutor,edCorreo;
    //private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        back = findViewById(R.id.backB);
        actualizar =findViewById(R.id.button_actualilzar);
        edNombre=findViewById(R.id.editTextNombre);
        edApellido=findViewById(R.id.editTextApellido);
        edCurso=findViewById(R.id.editTextCursos);
        edTutor=findViewById(R.id.editTextTutor);
        edCorreo=findViewById(R.id.editTextCorreo);

        Intent intent=getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            String idUsuario = bundle.getString("idUsuario");
            String idDocente = bundle.getString("idDocente");
            String nombre = bundle.getString("nombre");
            String apellido = bundle.getString("apellido");
            String correo = bundle.getString("correo");
            String aulaTuto = bundle.getString("aulaTuto");

            // Usa los valores según sea necesario
            edNombre.setText(nombre);
            edApellido.setText(apellido);
            //edCurso.setText(curso);
            edTutor.setText(aulaTuto);
            edCorreo.setText(correo);
        }

        //position=intent.getExtras().getInt("position");

        //edNombre.setText(MainActivity.usuariosArraylist.get(position).getNombre());
        //edApellido.setText(MainActivity.usuariosArraylist.get(position).getApellido());
        //edCurso.setText(MainActivity.usuariosArraylist.get(position).getCurso());
        //edTutor.setText(MainActivity.usuariosArraylist.get(position).getTutor());
        //edCorreo.setText(MainActivity.usuariosArraylist.get(position).getCorreo());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), R.string.completar_espacio, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void actualizar(View view){
        final String Nombre=edNombre.getText().toString().trim();
        final String Apellido=edApellido.getText().toString().trim();
        final String Curso=edCurso.getText().toString().trim();
        final String Tutor=edTutor.getText().toString().trim();
        final String Correo=edCorreo.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Actualizando");
        progressDialog.dismiss();

        StringRequest request = new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PerfilActivity.this, response, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ContraActivity.class));
                finish();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PerfilActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                //params.put("id",id);
                return params;
            }
        };
    }
}
