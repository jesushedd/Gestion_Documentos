package modelo;

import java.time.LocalDate;

public class Documento {
    private int id;
    private Integer numeroExpediente;
    private LocalDate fecha;
    private String tipo;
    private String remitente;
    private Estado estado;

    public Documento(Estado estado){
        this.fecha = LocalDate.now();
        this.estado = estado;
    }

    public Documento(int id, Integer numeroExpediente, LocalDate fecha, String tipo, String remitente, Estado estado) {
        this.id = id;
        this.numeroExpediente = numeroExpediente;
        this.fecha = fecha;
        this.tipo = tipo;
        this.remitente = remitente;
        this.estado = estado;
    }

    public Documento(Integer numeroExpediente, LocalDate fecha, String tipo, String remitente, Estado estado) {
        this.numeroExpediente = numeroExpediente;
        this.fecha = fecha;
        this.tipo = tipo;
        this.remitente = remitente;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "id=" + id +
                ", numeroExpediente=" + numeroExpediente +
                ", fecha=" + fecha +
                ", tipo='" + tipo + '\'' +
                ", remitente='" + remitente + '\'' +
                ", estado=" + estado +
                '}';
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(int numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }



}
