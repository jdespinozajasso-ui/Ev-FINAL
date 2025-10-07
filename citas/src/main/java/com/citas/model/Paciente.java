package com.citas.model;


public class Paciente {
    private int id;
    private String nombre;


    public Paciente() {}


    public Paciente(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }


    public String toCsv() {
        return String.format("%d,%s", id, escape(nombre));
    }


    public static Paciente fromCsv(String line) {
       String[] parts = CsvUtil.parseCsvLine(line);
       if (parts.length < 2) return null;
       int id = Integer.parseInt(parts[0]);
       return new Paciente(id, parts[1]);
    }


    private String escape(String s) {
       return s == null ? "" : s.replace("\n", " ").replace(",", " ");
    }
}