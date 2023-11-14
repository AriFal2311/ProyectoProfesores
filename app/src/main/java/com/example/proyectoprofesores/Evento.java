package com.example.proyectoprofesores;

import java.sql.Time;
import java.text.SimpleDateFormat;

public class Evento {

    int idHorario;

    String curso;
    String aula;
    String nivel;
    String dia;
    Time horaInicio;
    Time horaFin;

    public Evento(int idHorario, String curso, String aula, String nivel, String dia, Time horaInicio, Time horaFin) {
        this.idHorario = idHorario;
        this.curso = curso;
        this.aula = aula;
        this.nivel = nivel;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getAula() {
        return "Salón " + aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getNivel() {
        switch (this.nivel){
            case "PRIM":
                return "NIVEL PRIMARIA";
            case "SEC":
                return "NIVEL SECUNDARIA";
            default:
                return "NIVEL NO ENCONTRADO";
        }

    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }
    public static String convertirTiempoAstring(Time hora){
        Time horaF = hora;
        // Formatear la hora como una cadena
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String horaFormateada = sdf.format(hora);

        return horaFormateada;
    }
}
