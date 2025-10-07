package com.citas.repo;


import com.citas.model.Cita;
import com.citas.util.CsvUtil;


import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class CitaRepository {
    private final Path file = Paths.get("db", "citas.csv");
    private final Map<Integer, Cita> store = new LinkedHashMap<>();


    public CitaRepository() {
        try { validateFile(); load(); } catch (Exception e) { System.err.println("Error inicializando CitaRepository: " + e.getMessage()); }
    }


    private void validateFile() throws Exception {
        Files.createDirectories(file.getParent());
        if (!Files.exists(file)) {
            CsvUtil.writeAllLines(file, Arrays.asList("id,fechaHora,motivo,doctorId,pacienteId"));
        }
    }


    public void load() {
        try {
            List<String> lines = CsvUtil.readAllLines(file);
            store.clear();
            for (int i = 1; i < lines.size(); i++) {
                String l = lines.get(i).trim();
                if (l.isEmpty()) continue;
                Cita c = Cita.fromCsv(l);
                if (c != null) store.put(c.getId(), c);
            }
        } catch (Exception e) { System.err.println("Error cargando citas: " + e.getMessage()); }
    }


    public void saveAll() {
        try {
            List<String> out = new ArrayList<>();

            out.add("id,fechaHora,motivo,doctorId,pacienteId");
            out.addAll(store.values().stream().map(Cita::toCsv).collect(Collectors.toList()));
            CsvUtil.writeAllLines(file, out);
        } catch (Exception e) { System.err.println("Error guardando citas: " + e.getMessage()); }
    }


    public Cita add(LocalDateTime fechaHora, String motivo, int doctorId, int pacienteId) {
        int id = IdGenerator.nextId(store.keySet());

        Cita c = new Cita(id, fechaHora, motivo, doctorId, pacienteId);
        store.put(id, c);
        saveAll();
        return c;
    }


    public Optional<Cita> findById(int id) { return Optional.ofNullable(store.get(id)); }


    public List<Cita> findAll() { return new ArrayList<>(store.values()); }


    public boolean doctorHasConflict(int doctorId, LocalDateTime fechaHora) {
        return store.values().stream().anyMatch(c -> c.getDoctorId() == doctorId && c.getFechaHora().equals(fechaHora));
    }
}