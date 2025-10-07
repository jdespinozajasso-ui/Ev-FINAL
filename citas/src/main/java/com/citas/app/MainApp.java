package com.citas.app;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.citas.model.Cita;
import com.citas.model.Doctor;
import com.citas.model.Paciente;
import com.citas.repo.CitaRepository;
import com.citas.repo.DoctorRepository;
import com.citas.repo.PacienteRepository;
import com.citas.service.AuthService;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DoctorRepository doctorRepo = new DoctorRepository();
    private static final PacienteRepository pacienteRepo = new PacienteRepository();
    private static final CitaRepository citaRepo = new CitaRepository();
    private static final AuthService auth = new AuthService();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("=== SISTEMA DE CITAS MEDICAS ===");
                System.out.println("1. Login");
                System.out.println("2. Registro Doctor");
                System.out.println("3. Registro Paciente");
                System.out.println("4. Portal de Citas");
                System.out.println("5. Listar Doctores");
                System.out.println("6. Listar Pacientes");
                System.out.println("7. Listar Citas");
                System.out.println("8. Salir");
                System.out.print("Opcion: ");

                String opt = scanner.nextLine().trim();
                switch (opt) {
                    case "1": doLogin(); break;
                    case "2": doRegistroDoctor(); break;
                    case "3": doRegistroPaciente(); break;
                    case "4": portalCitas(); break;
                    case "5": listarDoctores(); break;
                    case "6": listarPacientes(); break;
                    case "7": listarCitas(); break;
                    case "8": exit = true; break;
                    default: System.out.println("Opcion invalida");
                }
            } catch (Exception e) {
                System.err.println("Ocurrio un error: " + e.getMessage());
            }
        }
        System.out.println("Saliendo...");
    }

    private static void doLogin() {
        System.out.print("Usuario: ");
        String u = scanner.nextLine().trim();
        System.out.print("Password: ");
        String p = scanner.nextLine().trim();
        if (auth.login(u, p)) System.out.println("Login exitoso"); else System.out.println("Credenciales invalidas");
    }

    private static void doRegistroDoctor() {
        if (!auth.currentAdmin().isPresent()) { System.out.println("Acceso denegado. Requiere admin."); return; }
        System.out.print("Nombre completo doctor: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Especialidad: ");
        String esp = scanner.nextLine().trim();
        Doctor d = doctorRepo.add(nombre, esp);
        System.out.println("Doctor creado. ID=" + d.getId());
    }

    private static void doRegistroPaciente() {
        if (!auth.currentAdmin().isPresent()) { System.out.println("Acceso denegado. Requiere admin."); return; }
        System.out.print("Nombre completo paciente: ");
        String nombre = scanner.nextLine().trim();
        Paciente p = pacienteRepo.add(nombre);
        System.out.println("Paciente creado. ID=" + p.getId());
    }

    private static void portalCitas() {
        System.out.println("--- Portal de Citas ---");
        System.out.println("1. Crear cita");
        System.out.println("2. Volver");
        System.out.print("Opcion: ");
        String o = scanner.nextLine().trim();
        if ("1".equals(o)) crearCita();
    }

    private static void crearCita() {
        try {
            System.out.print("ID doctor: ");
            int idDoc = Integer.parseInt(scanner.nextLine().trim());
            Optional<Doctor> doc = doctorRepo.findById(idDoc);
            if (!doc.isPresent()) { System.out.println("Doctor no encontrado"); return; }
            
            System.out.print("ID paciente: ");
            int idPac = Integer.parseInt(scanner.nextLine().trim());
            Optional<Paciente> pac = pacienteRepo.findById(idPac);
            if (!pac.isPresent()) { System.out.println("Paciente no encontrado"); return; }
            
            System.out.print("Fecha y hora (yyyy-MM-ddTHH:mm): ");
            String fh = scanner.nextLine().trim();
            LocalDateTime dt = LocalDateTime.parse(fh, Cita.FORMAT);
            
            if (citaRepo.doctorHasConflict(idDoc, dt)) {
                System.out.println("El doctor ya tiene una cita en esa fecha y hora. Recomendando otra...\n");
                return;
            }
            
            System.out.print("Motivo: ");
            String motivo = scanner.nextLine().trim();
            
            Cita c = citaRepo.add(dt, motivo, idDoc, idPac);
            System.out.println("Cita creada. ID=" + c.getId());
        } catch (Exception e) {
            System.err.println("Error creando cita: " + e.getMessage());
        }
    }

    private static void listarDoctores() {
        List<Doctor> ds = doctorRepo.findAll();
        System.out.println("--- Doctores ---");
        ds.forEach(d -> System.out.println(d.getId() + ": " + d.getNombre() + " (" + d.getEspecialidad() + ")"));
    }
    
    private static void listarPacientes() {
        List<Paciente> ps = pacienteRepo.findAll();
        System.out.println("--- Pacientes ---");
        ps.forEach(p -> System.out.println(p.getId() + ": " + p.getNombre()));
    }

    private static void listarCitas() {
        List<Cita> cs = citaRepo.findAll();
        System.out.println("--- Citas ---");
        
        cs.forEach(c -> System.out.println(c.getId() + ": " + c.getFechaHora().format(Cita.FORMAT) + " Dr=" + c.getDoctorId() + " Pac=" + c.getPacienteId() + " Motivo=" + c.getMotivo()));
    }
}