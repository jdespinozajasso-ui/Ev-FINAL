package com.citas.model;


public class Admin {
    private int id;
    private String usuario;
    private String passwordHash;


    public Admin() {}


    public Admin(int id, String usuario, String passwordHash) {
       this.id = id;
       this.usuario = usuario;
       this.passwordHash = passwordHash;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }


    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }


    public String toCsv() {
       return String.format("%d,%s,%s", id, usuario, passwordHash);
   }


    public static Admin fromCsv(String line) {
       String[] parts = CsvUtil.parseCsvLine(line);
       if (parts.length < 3) return null;
       return new Admin(Integer.parseInt(parts[0]), parts[1], parts[2]);
    }
}