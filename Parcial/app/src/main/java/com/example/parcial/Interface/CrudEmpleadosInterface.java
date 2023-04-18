package com.example.parcial.Interface;

import  com.example.parcial.Model.Empleado ;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CrudEmpleadosInterface
{
       @GET("/consultarAll")//consultar
       Call<List<Empleado>>getAll();

       @POST("/guardar")//insertar
       Call<Empleado> createEmpleado(@Body RequestBody empleado);

       @GET("/actualizar/{id}")
       Call<Empleado> getEmpleadoById(@Path("id") Long id);

       @DELETE("/eliminarTodos")
       Call<Void> deleteAllEmpleados();

       @DELETE("/user/{id}")
       Call<Void> deleteEmpleadoById(@Path("id") Long id);

       @PATCH("actualizar/{id}")
       Call<Empleado> updateEmpleado(@Path("id") Long id, @Body Empleado empleado);



}
