package com.example.parcial.Model;

public class Empleado {

    private Long id;
    private  String nombre;
    private String password;
    private String email;


    public Empleado(){

    }


    public Empleado(Long id, String email,String nombre, String password ) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.password = password;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +

                '}';
    }
}
