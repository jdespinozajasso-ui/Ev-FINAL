package com.citas.repo;


import com.citas.model.Paciente;
import com.citas.util.CsvUtil;


import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;


public class PacienteRepository {
    private final Path file = Paths.get("db", "pacientes.csv");
    private final Map<Integer, Paciente> store = new LinkedHashMap<>();


    public PacienteRepository() {
        try { validateFile(); load(); } catch (Exception e) { System.err.println("Error inicializando PacienteRepository: " + e.getMessage()); }
    }


    private void validateFile() throws Exception {
        Files.createDirectories(file.getParent());
        if (!Files.exists(file)) {
            CsvUtil.writeAllLines(file, Arrays.asList("id,nombre"));
        }
    }


    public void load() {
        try {
            List<String> lines = CsvUtil.readAllLines(file);
            store.clear();
            for (int i = 1; i < lines.size(); i++) {
                String l = lines.get(i).trim();
                if (l.isEmpty()) continue;
                Paciente p = Paciente.fromCsv(l);
                if (p != null) store.put(p.getId(), p);
            }
        } catch (Exception e) { System.err.println("Error cargando pacientes: " + e.getMessage()); }
    }


    public void saveAll() {
        try {
            List<String> out = new ArrayList<>();
            out.add("id,nombre");
            out.addAll(store.values().stream().map(Paciente::toCsv).collect(Collectors.toList()));
            CsvUtil.writeAllLines(file, out);
        } catch (Exception e) { System.err.println("Error guardando pacientes: " + e.getMessage()); }
    }


    public Paciente add(String nombre) {
        int id = IdGenerator.nextId(store.keySet());
        Paciente p = new Paciente(id, nombre);
        store.put(id, p);
        saveAll();
        return p;
    }


    public Optional<Paciente> findById(int id) { return Optional.ofNullable(store.get(id)); }


    public List<Paciente> findAll() { return new ArrayList<>(store.values()); }
}