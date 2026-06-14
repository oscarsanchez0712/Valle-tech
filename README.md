# ValleTech - Gestión de Productos
## Evaluación Final CO1 - Análisis de Sistemas

---

## 📋 REQUISITOS PREVIOS

- **Java JDK 11+** (o superior)
- **IntelliJ IDEA** (Community o Ultimate)
- **Docker Desktop** instalado y corriendo
- **Maven** (IntelliJ lo incluye por defecto)

---

## 🐳 PASO 1: Levantar la Base de Datos con Docker

Abre una terminal en la carpeta del proyecto y ejecuta:

```bash
docker-compose up -d
```

Esto creará un contenedor MySQL en el puerto **3307** con:
- Base de datos: `valletech_db`
- Usuario: `valletech`
- Contraseña: `valle2024`
- Root password: `root123`

El script `sql/init.sql` se ejecuta automáticamente y crea las tablas con datos de prueba.

### Verificar que el contenedor está corriendo:
```bash
docker ps
```
Debes ver `mysql_valletech` en estado `Up`.

---

## 🔧 PASO 2: Conectar MySQL Workbench (opcional)

Crea una nueva conexión en MySQL Workbench con estos datos:
- **Host:** 127.0.0.1
- **Port:** 3307
- **Username:** valletech
- **Password:** valle2024
- **Schema:** valletech_db

---

## 💻 PASO 3: Abrir en IntelliJ IDEA

1. Abre IntelliJ IDEA
2. Selecciona **File → Open** y navega hasta la carpeta `ValleTech`
3. IntelliJ detecta automáticamente el `pom.xml` → clic en **"Load Maven Project"**
4. Espera que Maven descargue las dependencias (MySQL Connector + iText PDF)

---

## ▶️ PASO 4: Ejecutar la Aplicación

1. Abre el archivo `LoginFrame.java`  
   `src/main/java/com/valletech/ui/LoginFrame.java`
2. Haz clic derecho → **"Run 'LoginFrame.main()'"**

### Credenciales de prueba:
| Usuario | Contraseña |
|---------|-----------|
| admin   | admin123   |
| oscar   | oscar123   |

---

## 📱 FUNCIONALIDADES

### Requisito 1 - Login
- Valida usuario y contraseña contra MySQL en Docker
- Mensaje ✓ verde si el acceso es correcto
- Mensaje ✗ rojo si son incorrectos

### Requisito 2 - Dashboard
- Menú con 3 opciones: Gestión de Productos, Reportes, Cerrar Sesión

### Requisito 3 - CRUD de Productos
- **Registrar:** Llena el formulario y clic en "Guardar"
- **Modificar:** Selecciona fila de la tabla, edita campos, clic en "Modificar"
- **Eliminar:** Selecciona fila, clic en "Eliminar" y confirma
- **Buscar:** Escribe en el campo de búsqueda y clic en "Buscar"
- **Listar:** La tabla JTable muestra todos los productos al iniciar

### Requisito 4 - Reportes
- Pantalla de reporte con tabla completa
- Muestra total de productos y valor del inventario
- Datos en tiempo real desde Docker MySQL

### Requisito 5 - Exportar PDF
- Clic en "📥 Exportar PDF"
- Selecciona la carpeta donde guardar
- El PDF se genera con iText (sin JasperReports)
- Opción para abrir el PDF automáticamente

---

## 🗂️ ESTRUCTURA DEL PROYECTO

```
ValleTech/
├── pom.xml                         ← Dependencias Maven
├── docker-compose.yml              ← Contenedor MySQL
├── sql/
│   └── init.sql                    ← Script de base de datos
└── src/main/java/com/valletech/
    ├── model/
    │   ├── Producto.java
    │   └── Usuario.java
    ├── dao/
    │   ├── ProductoDAO.java         ← CRUD completo
    │   └── UsuarioDAO.java          ← Autenticación
    ├── util/
    │   ├── Conexion.java            ← Conexión a Docker MySQL
    │   └── ReporteUtil.java         ← Generación PDF con iText
    └── ui/
        ├── LoginFrame.java          ← Req 1: Login
        ├── DashboardFrame.java      ← Req 2: Dashboard
        ├── ProductoFrame.java       ← Req 3: CRUD
        └── ReporteFrame.java        ← Req 4 y 5: Reporte + PDF
```

---

## 🛑 DETENER EL CONTENEDOR

```bash
docker-compose down
```

Para eliminar también los datos:
```bash
docker-compose down -v
```

---

## ⚠️ SOLUCIÓN DE PROBLEMAS

**Error de conexión:**
- Verifica que Docker Desktop esté corriendo
- Ejecuta `docker ps` para confirmar el contenedor activo
- El puerto debe ser 3307, no 3306

**Maven no descarga dependencias:**
- Ve a File → Settings → Maven → verifica la conexión a internet
- Clic derecho en `pom.xml` → Maven → Reload Project
