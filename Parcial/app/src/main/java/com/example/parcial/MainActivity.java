package com.example.parcial;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.parcial.Interface.CrudEmpleadosInterface;
import com.example.parcial.Model.Empleado;
import com.google.gson.Gson;


import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button boton_Eliminar ;
    private Button  boton_Guardar ;
    private Button boton_consultar  ;
    private EditText Textid ,editTextNombre,editTextPassword,editTextEmail;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAll();

        // Pasar valor de los elementos en la interfaz
        Textid = findViewById(R.id.text_id);
        boton_Eliminar = findViewById(R.id.botonEliminar);
        editTextNombre = findViewById(R.id.txtNombre);
        editTextPassword = findViewById(R.id.txtPassword);
        editTextEmail = findViewById(R.id.txtCorreo);
        boton_Guardar = findViewById(R.id.button_guardar);
        boton_consultar= findViewById(R.id.ButonConsultar);

        // Realizar accion
        boton_Eliminar.setOnClickListener(this);
        boton_Guardar.setOnClickListener(this);
        boton_consultar.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == boton_Eliminar) {
            // Obtener el ID ingresado por el usuario
            Long id = Long.parseLong(Textid.getText().toString());

            // Eliminar el empleado correspondiente
            delete(id);
        } else if(v == boton_consultar) {
            // Obtener el ID ingresado por el usuario
                Long id = Long.parseLong(Textid.getText().toString());

            BuscarEmpleados(id);
        }else if(v ==  boton_Guardar) {
            // Obtener el ID ingresado por el usuario
            Long id = Long.parseLong(Textid.getText().toString());

            createEmpleados(id);
        }


    }



    private void getAll(){

        Retrofit retrofit = new Retrofit.Builder()

                //baseUrl("http://192.168.20.6:8081/")
                .baseUrl("http://10.10.16.199:8081")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CrudEmpleadosInterface cruempleado = retrofit.create(CrudEmpleadosInterface.class) ;
        Call<List<Empleado>> call = cruempleado.getAll();


        call.enqueue(new Callback<List<Empleado>>() {
            @Override
            public void onResponse(Call<List<Empleado>> call, Response<List<Empleado>> response) {
                if(!response.isSuccessful()){
                    //System.out.println(response.body());

                    Log.e("Response err:",response.message());
                    return;
                }
                List<Empleado> listEmpleado = response.body();
                //System.out.println(p.toString()));
                listEmpleado.forEach(p->Log.i("Empleados:",p.toString()));


            }

            @Override
            public void onFailure(Call<List<Empleado>> call, Throwable t) {

                //System.out.println(t.getMessage());
                Log.e("Throw error:",t.getMessage());

            }



        });

    }


    public void createEmpleados(Long view){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.10.16.199:8081")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CrudEmpleadosInterface cruempleado = retrofit.create(CrudEmpleadosInterface.class);
        Textid = findViewById(R.id.text_id);
        editTextNombre = findViewById(R.id.txtNombre);
        editTextPassword = findViewById(R.id.txtPassword);
        editTextEmail = findViewById(R.id.txtCorreo);
        Long ID = Long.parseLong(Textid.getText().toString());
        String nombre = editTextNombre.getText().toString();
        String password = editTextPassword.getText().toString();
        String email = editTextEmail.getText().toString();
        Empleado empleado = new Empleado(ID, nombre, password, email);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                new Gson().toJson(empleado));
        Call<Empleado> call =  cruempleado.createEmpleado(requestBody);

        call.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Log.e("Throw error: ",t.getMessage());
            }
        });
        Textid.setText("");
        editTextNombre.setText("");
        editTextPassword.setText("");
        editTextEmail.setText("");
    }



    private void delete(Long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.10.16.199:8081")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CrudEmpleadosInterface crudEmpleadosInterface = retrofit.create(CrudEmpleadosInterface.class);

        Call<Void> call = crudEmpleadosInterface.deleteEmpleadoById(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("DELETE", "Empleado eliminado con éxito");
                } else {
                    Log.e("DELETE", "Error al eliminar el empleado");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("DELETE", t.getMessage());
            }
        });
    }


    public void BuscarEmpleados(Long id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.10.16.199:8081")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CrudEmpleadosInterface crudEmpleadosInterface = retrofit.create(CrudEmpleadosInterface.class);
        Textid = findViewById(R.id.text_id);
        editTextNombre = findViewById(R.id.txtNombre);
        editTextPassword = findViewById(R.id.txtPassword);
        editTextEmail = findViewById(R.id.txtCorreo);
        Call<Empleado> call = crudEmpleadosInterface.getEmpleadoById(id);
        call.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if (response.isSuccessful()) {
                    Empleado empleado = response.body();
                    editTextNombre.setText(empleado.getNombre());
                    editTextPassword.setText(empleado.getPassword());
                    editTextEmail.setText(empleado.getEmail());
                }
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Log.e("Throw error: ",t.getMessage());
            }
        });
    }



}