package com.citas.model;


public class Doctor {
    private int id;
    private String nombre;
    private String especialidad;
    
    public Doctor() {}
    
    public Doctor(int id, String nombre, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }


    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }


    public String toCsv() {
        return String.format("%d,%s,%s", id, escape(nombre), escape(especialidad));
    }


    public static Doctor fromCsv(String line) {
        String[] parts = CsvUtil.parseCsvLine(line);
        if (parts.length < 3) return null;
        int id = Integer.parseInt(parts[0]);
        return new Doctor(id, parts[1], parts[2]);
    }


    private String escape(String s) {
        return s == null ? "" : s.replace("\n", " ").replace(",", " ");
    }
}