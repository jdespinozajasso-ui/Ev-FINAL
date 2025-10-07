USO DEL PROGRAMA
El sistema permite:
Registrar doctores con ID, nombre y especialidad.
Registrar pacientes con ID y nombre completo.
Crear citas con fecha, hora y motivo.
Relacionar citas con un doctor y un paciente.
Acceso seguro mediante administrador con usuario y contraseña.
Almacenamiento de datos en archivos CSV dentro de la carpeta db/.
Si los archivos CSV no existen, el sistema los genera automáticamente al iniciar.

ESTRUCTURA

src/main/java/com/citas/
├── app/MainApp.java
├── model/
│   ├── Doctor.java
│   ├── Paciente.java
│   ├── Cita.java
│   └── Admin.java
├── repo/
│   ├── DoctorRepository.java
│   ├── PacienteRepository.java
│   └── CitaRepository.java
├── service/
│   └── AuthService.java
└── util/
    ├── CsvUtil.java
    └── IdGenerator.java


CREDITOS

Proyecto desarrollado por:
JOSUE DANIEL ESPINOZA JASSO
Materia: COMPUTACION EN JAVA
Universidad: UNIVERSIDAD TECMILENIO


Licencia

Este proyecto se distribuye bajo la licencia MIT.
Puedes usarlo, modificarlo y distribuirlo libremente mencionando al autor original.


db/  ← Archivos CSV (no se suben al repositorio)

