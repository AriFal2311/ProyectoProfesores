package com.example.proyectoprofesores;

public class Notificaciones {
    public int imageNotificaciones;
    public String titulo;
    public String noti;

    public static class Builder {
        private Notificaciones notificaciones;

        public Builder() {
            notificaciones = new Notificaciones();
        }

        public Builder withImageNotificaciones(int imageNotificaciones) {
            notificaciones.imageNotificaciones = imageNotificaciones;
            return this;
        }

        public Builder withTitulo(String titulo) {
            notificaciones.titulo = titulo;
            return this;
        }

        public Builder withNoti(String noti) {
            notificaciones.noti = noti;
            return this;
        }

        public Notificaciones build() {
            return notificaciones;
        }
    }
}
