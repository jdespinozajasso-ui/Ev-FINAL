USO DEL PROGRAMA
El sistema permite:
Registrar doctores con ID, nombre y especialidad.
Registrar pacientes con ID y nombre completo.
Crear citas con fecha, hora y motivo.
Relacionar citas con un doctor y un paciente.
Acceso seguro mediante administrador con usuario y contraseña.
Almacenamiento de datos en archivos CSV dentro de la carpeta db/.
Si los archivos CSV no existen, el sistema los genera automáticamente al iniciar.

# Descripción de clases

### `Doctor`
- **Paquete:** `com.citas.model`  
- **Descripción:** Representa a un doctor del consultorio.  
- **Atributos:**  
  - `String idDoctor`  
  - `String nombre`  
  - `String especialidad`  
- **Métodos:**  
  - Getters y setters.

---

###  `Paciente`
- **Paquete:** `com.citas.model`  
- **Descripción:** Representa a un paciente registrado.  
- **Atributos:**  
  - `String idPaciente`  
  - `String nombre`  
- **Métodos:**  
  - Getters y setters.

---

###  `Cita`
- **Paquete:** `com.citas.model`  
- **Descripción:** Representa una cita médica con su fecha, hora y motivo.  
- **Atributos:**  
  - `String idCita`  
  - `String fechaHora`  
  - `String motivo`  
  - `Doctor doctor`  
  - `Paciente paciente`  
- **Métodos:**  
  - Getters y setters.  
  - Métodos para asociar doctor y paciente.

---

###  `Admin`
- **Paquete:** `com.citas.model`  
- **Descripción:** Representa un usuario administrador.  
- **Atributos:**  
  - `String usuario`  
  - `String contraseña`  
- **Métodos:**  
  - Validar credenciales.

---

###  `DoctorRepository`, `PacienteRepository`, `CitaRepository`
- **Paquete:** `com.citas.repo`  
- **Descripción:** Manejan la lectura y escritura de datos en CSV.  
- **Responsabilidades:**  
  - `DoctorRepository`: Guardar y leer doctores.  
  - `PacienteRepository`: Guardar y leer pacientes.  
  - `CitaRepository`: Guardar y leer citas.

---

###  `CsvUtil`
- **Paquete:** `com.citas.util`  
- **Descripción:** Clase auxiliar para leer y escribir archivos CSV.  
- **Métodos:**  
  - `leerArchivo(String ruta)`  
  - `escribirArchivo(String ruta, List<String> lineas)`

---

###  `AuthService`
- **Paquete:** `com.citas.service`  
- **Descripción:** Controla el inicio de sesión del administrador.  
- **Métodos:**  
  - `boolean login(String usuario, String contraseña)`

---

###  `IdGenerator`
- **Paquete:** `com.citas.util`  
- **Descripción:** Genera identificadores únicos para doctores, pacientes y citas.  
- **Métodos:**  
  - `String generarId(String prefijo)`

---

###  `MainApp`
- **Paquete:** `com.citas.app`  
- **Descripción:** Punto de entrada del sistema. Muestra el menú principal y gestiona la lógica de flujo.  
- **Métodos:**  
  - `main(String[] args)`  
  - Métodos auxiliares de interacción con el usuario.

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

