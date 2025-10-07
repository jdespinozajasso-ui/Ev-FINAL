package com.citas.service;


public class AuthService {
    private final Path file = Paths.get("db", "admins.csv");
    private final Map<String, Admin> store = new HashMap<>();
    private Admin current = null;


    public AuthService() {
        try { validateFile(); load(); } catch (Exception e) { System.err.println("Error inicializando AuthService: " + e.getMessage()); }
    }


    private void validateFile() throws Exception {
        Files.createDirectories(file.getParent());
        if (!Files.exists(file)) {
            CsvUtil.writeAllLines(file, Arrays.asList("id,usuario,passwordHash", "1,admin," + hash("admin123")));
        }
    }


    public void load() {
        try {
            List<String> lines = CsvUtil.readAllLines(file);
            store.clear();
            for (int i = 1; i < lines.size(); i++) {
                String l = lines.get(i).trim();
                if (l.isEmpty()) continue;
                Admin a = Admin.fromCsv(l);
                if (a != null) store.put(a.getUsuario(), a);
            }
        } catch (Exception e) { System.err.println("Error cargando admins: " + e.getMessage()); }
    }


    public boolean login(String usuario, String password) {
        try {
            Admin a = store.get(usuario);
            if (a == null) return false;
            String h = hash(password);
            if (h.equals(a.getPasswordHash())) { current = a; return true; }
            return false;
        } catch (Exception e) { System.err.println("Error en login: " + e.getMessage()); return false; }
    }


    public void logout() { current = null; }


    public Optional<Admin> currentAdmin() { return Optional.ofNullable(current); }


    private static String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] b = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte x : b) { sb.append(String.format("%02x", x)); }
            return sb.toString();
        } catch (Exception e) { return input; }
    }
}