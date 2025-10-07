package com.citas.repo;


import com.citas.model.Doctor;
import com.citas.util.CsvUtil;


import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;


public class DoctorRepository {
    private final Path file = Paths.get("db", "doctores.csv");
    private final Map<Integer, Doctor> store = new LinkedHashMap<>();


    public DoctorRepository() {
        try { validateFile(); load(); } catch (Exception e) { System.err.println("Error inicializando DoctorRepository: " + e.getMessage()); }
    }


    private void validateFile() throws Exception {
        Files.createDirectories(file.getParent());
        if (!Files.exists(file)) {
            CsvUtil.writeAllLines(file, Arrays.asList("id,nombre,especialidad"));
        }
    }


    public void load() {
        try {
            List<String> lines = CsvUtil.readAllLines(file);
            store.clear();
            for (int i = 1; i < lines.size(); i++) {
                String l = lines.get(i).trim();
                if (l.isEmpty()) continue;
                Doctor d = Doctor.fromCsv(l);
                if (d != null) store.put(d.getId(), d);
            }
        } catch (Exception e) { System.err.println("Error cargando doctores: " + e.getMessage()); }
    }


    public void saveAll() {
        try {
            List<String> out = new ArrayList<>();
            out.add("id,nombre,especialidad");
            out.addAll(store.values().stream().map(Doctor::toCsv).collect(Collectors.toList()));
            CsvUtil.writeAllLines(file, out);
        } catch (Exception e) { System.err.println("Error guardando doctores: " + e.getMessage()); }
    }


    public Doctor add(String nombre, String especialidad) {
        int id = IdGenerator.nextId(store.keySet());
        Doctor d = new Doctor(id, nombre, especialidad);
        store.put(id, d);
        saveAll();
        return d;
    }


    public Optional<Doctor> findById(int id) { return Optional.ofNullable(store.get(id)); }


    public List<Doctor> findAll() { return new ArrayList<>(store.values()); }
}