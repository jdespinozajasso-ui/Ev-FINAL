package com.citas.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Cita {
    private int id;
    private LocalDateTime fechaHora;
    private String motivo;
    private int doctorId;
    private int pacienteId;


    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    public Cita() {}


    public Cita(int id, LocalDateTime fechaHora, String motivo, int doctorId, int pacienteId) {
       this.id = id;
       this.fechaHora = fechaHora;
       this.motivo = motivo;
       this.doctorId = doctorId;
       this.pacienteId = pacienteId;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }


    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }


    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }


    public int getPacienteId() { return pacienteId; }
    public void setPacienteId(int pacienteId) { this.pacienteId = pacienteId; }


    public String toCsv() {
       return String.format("%d,%s,%s,%d,%d", id, fechaHora.format(FORMAT), escape(motivo), doctorId, pacienteId);
    }


    public static Cita fromCsv(String line) {
       String[] parts = CsvUtil.parseCsvLine(line);
       if (parts.length < 5) return null;
       int id = Integer.parseInt(parts[0]);
       LocalDateTime dt = LocalDateTime.parse(parts[1], FORMAT);
       return new Cita(id, dt, parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
    }


    private String escape(String s) { return s == null ? "" : s.replace(",", " "); }
}