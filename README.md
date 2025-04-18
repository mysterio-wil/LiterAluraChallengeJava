# LiterAlura Challenge Java

[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.9.9-orange.svg)](https://maven.apache.org/)
![version](https://img.shields.io/badge/version-1.1.0-blue.svg)

## Tabla de Contenidos
- [Descripción](#descripción)
- [Características](#características)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Contribución](#contribución)
- [Contacto](#contacto)
- [Historial de Versiones](#historial-de-versiones)
- [Avance de la Interfaz Gráfica (JavaFX)](#avance-de-la-interfaz-gráfica-javafx)

## Descripción
Aplicación Spring Boot que gestiona un catálogo de libros conectándose a la API Gutendex y una base de datos MySQL.

## Características
### Implementadas
- [x] Búsqueda de libros por título
- [x] Listado de libros registrados
- [x] Listado de autores registrados
- [x] Búsqueda de autores vivos en un año específico
- [x] Listado de libros por idioma
- [x] Estadísticas de libros por idioma
- [x] Integración con Gutendex API
- [x] Base de datos MySQL
- [x] Listado de autores nacidos en un rango de años
- [x] Sistema de favoritos para libros
- [x] Exportación de libros y favoritos a CSV o JSON en el escritorio del usuario
- [x] Búsqueda avanzada de libros por múltiples criterios (título, autor, idioma) desde consola
- [x] Integración de la pantalla de búsqueda avanzada: el botón 'Buscar libros' ahora abre un formulario gráfico para búsqueda avanzada (BusquedaLibrosView).
feat: exportación de libros a CSV y Excel desde la interfaz

- Implementados los handlers para exportar libros en formatos CSV y Excel desde el menú Herramientas > Exportar datos.
- Diálogo para elegir ubicación y nombre del archivo a exportar.
- Mensajes claros al usuario en caso de éxito, error o si no hay libros para exportar.
- Se utiliza ExportarLibrosUtil para la generación de archivos.
- Actualización del README: instrucciones detalladas sobre cómo usar la exportación y formato de los archivos generados.feat: exportación de libros a CSV y Excel desde la interfaz

- Implementados los handlers para exportar libros en formatos CSV y Excel desde el menú Herramientas > Exportar datos.
- Diálogo para elegir ubicación y nombre del archivo a exportar.
- Mensajes claros al usuario en caso de éxito, error o si no hay libros para exportar.
- Se utiliza ExportarLibrosUtil para la generación de archivos.
- Actualización del README: instrucciones detalladas sobre cómo usar la exportación y formato de los archivos generados.- [x] **Exportación desde interfaz gráfica:** Ahora puedes exportar todos los libros a CSV o Excel (XLSX) desde la opción "Exportar datos" en el menú principal de la interfaz JavaFX. El usuario elige el formato y la ubicación del archivo, y recibe mensajes claros de éxito o error.
- [x] **Menú principal mejorado:** El menú principal ahora es más limpio (eliminado botón de Favoritos) y utiliza un flujo moderno para la exportación.
- [x] **Nueva interfaz con cinta de opciones:** La aplicación ahora utiliza una barra de menú superior (MenuBar) y un panel central dinámico. Al iniciar, se muestra la consulta de libros disponibles en la base de datos con filtros rápidos. Todas las demás funcionalidades están accesibles desde la barra de menú.
- [x] **Pantalla principal minimalista:** Al iniciar, se muestra únicamente un mensaje de bienvenida (“Bienvenidos a LiterAlura”) y el menú superior. Todas las funcionalidades (búsqueda, exportar, importar, etc.) se acceden desde el menú.
- [x] **Importación de libros desde Excel y CSV** con ventana de progreso en tiempo real, barra de avance y listado de títulos importados, duplicados y errores.
- [x] **Manejo de archivos CSV robusto:** ahora se soportan títulos y autores con comas y comillas, gracias a la integración de OpenCSV.
- [x] **Feedback visual:** El usuario ve el avance de la importación y un resumen amigable al finalizar.
- [x] **Detección y reporte de duplicados y errores** durante la importación, sin detener el proceso.
- [x] **Dependencia agregada:** OpenCSV 5.7.1 incluida en pom.xml.

### Pendientes
- [ ] Implementar sistema de recomendaciones basado en preferencias
- [ ] Mejorar la experiencia de usuario con paneles contextuales y más filtros en la tabla principal.

## Requisitos
- Java 21 o superior
- MySQL 8.0 o superior
- Maven 3.9.9 o superior
- Conexión a internet para acceder a la API de Gutendex

## Instalación
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/mysterio-wil/LiterAluraChallengeJava.git
   cd LiterAluraChallengeJava
   ```

2. Compilar el proyecto:
   ```bash
   ./mvnw clean install
   ```

## Configuración
1. Configurar la base de datos MySQL:
   ```sql
   CREATE DATABASE literalura;
   ```

2. Configurar las credenciales en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/literalura
   spring.datasource.username=root
   spring.datasource.password=22021989
   ```

3. Puerto del servidor: 8888

## Uso
1. Ejecutar la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

2. Al iniciar, verás una pantalla limpia con el mensaje “Bienvenidos a LiterAlura” y el menú superior.
3. Usa el menú (“Archivo”, “Herramientas”) para acceder a todas las funcionalidades: buscar libros, importar, exportar, salir, etc.

## Importación de libros y autores desde archivo CSV o Excel

Puedes importar libros y autores desde archivos **CSV** o **Excel (.xlsx)** usando la opción "Importar desde archivo..." en el menú principal.

**Formato y orden de columnas requerido:**

    titulo,autor,idioma,fechaNacimiento,fechaFallecimiento,descargas

- **titulo**: Título del libro (obligatorio)
- **autor**: Nombre completo del autor (obligatorio)
- **idioma**: Código del idioma (obligatorio, ej: es, en, fr)
- **fechaNacimiento**: Año de nacimiento del autor (opcional, numérico)
- **fechaFallecimiento**: Año de fallecimiento del autor (opcional, numérico)
- **descargas**: Número de descargas del libro (opcional, numérico)

**Ejemplo de fila:**

    Don Quijote,Miguel de Cervantes,es,1547,1616,17635
    Pride and Prejudice,Jane Austen,en,1775,1817,60004

**Reglas importantes:**
- El **orden de las columnas es obligatorio**. Si el encabezado no coincide exactamente, la importación será rechazada.
- Los autores se crean o actualizan automáticamente. Si ya existen, se actualizan los años si vienen en el archivo.
- No se permiten duplicados: se compara título, autor e idioma (de forma normalizada, ignorando tildes, mayúsculas y espacios).
- Si el archivo contiene datos inválidos, la fila será ignorada y se notificará al usuario.

## Instrucciones para importar libros desde CSV o Excel
1. Ve al menú "Herramientas" > "Importar desde archivo" y elige CSV o Excel.
2. Selecciona el archivo a importar. El proceso mostrará una ventana de progreso.
3. Al finalizar, verás un resumen de libros importados, duplicados y errores.

**Formato esperado para CSV:**
```
titulo,autor,idioma,fechaNacimiento,fechaFallecimiento,descargas
"Título con, coma","Apellido, Nombre",pt,1900,1980,100
```

**Nota:** Si tu archivo tiene comillas y comas en los campos, ¡ahora es soportado!

## Exportación de libros a CSV y Excel

Puedes exportar el catálogo de libros desde el menú "Herramientas > Exportar datos" en los formatos **CSV** o **Excel (.xlsx)**.

- Al seleccionar la opción, elige la ubicación y el nombre del archivo a guardar.
- Si no hay libros en la base de datos, se mostrará un mensaje informativo.
- Si la exportación es exitosa, se notificará la ruta del archivo guardado.
- Si ocurre un error, se mostrará un mensaje detallando el problema.

**Formato de exportación:**

- **CSV:**
    - Columnas: Título, Autor, Idioma, Descargas
    - Codificación: UTF-8
- **Excel (.xlsx):**
    - Hoja llamada "Libros" con las mismas columnas que el CSV

**Ejemplo de fila (CSV):**

    "Don Quijote","Miguel de Cervantes",es,17635
    "Pride and Prejudice","Jane Austen",en,60004

## Últimas actualizaciones
- Implementación de un sistema completo de estadísticas con menús anidados
- Mejora en la organización del código con nueva clase MenuEstadisticas
- Optimización del manejo de datos estadísticos usando DoubleSummaryStatistics
- Corrección del cierre de la aplicación al seleccionar la opción 0
- Optimización de la gestión de recursos (cierre de Scanner)
- Cambio de puerto del servidor a 8888
- Interfaz gráfica de búsqueda avanzada completamente funcional y conectada a la base de datos.
- Mensajes informativos en pantalla para búsquedas sin parámetros o sin resultados.
- El botón 'Salir' ahora cierra la ventana y detiene la JVM.
- Refactor para garantizar obtención de beans vía Spring en vistas/controladores JavaFX.

## Avance de la Interfaz Gráfica (JavaFX)
- [x] Configuración inicial de JavaFX en el proyecto (pom.xml y dependencias).
- [x] Creación de la clase principal `LiterAluraApp` que muestra una ventana básica de bienvenida.
- [x] Verificación de ejecución correcta tanto por consola (Maven) como desde la interfaz de IntelliJ IDEA.
- [x] Menú principal gráfico con botones para Buscar libros, Favoritos, Exportar datos y Salir, usando JavaFX (MenuPrincipalView).
- [x] Integración de la pantalla de búsqueda avanzada: el botón 'Buscar libros' ahora abre un formulario gráfico para búsqueda avanzada (BusquedaLibrosView).
- [x] Búsqueda avanzada funcional en la GUI: formulario gráfico conectado a la base de datos, resultados en tabla y botón para volver al menú principal.

> A partir de aquí, el desarrollo continuará implementando las funcionalidades gráficas paso a paso.

## Estructura del Proyecto
```
src/
├── main/
│   ├── java/
│   │   └── com/alura/LiterAluraChallengeJava/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       └── principal/
│   └── resources/
│       └── application.properties
```

## Tecnologías Utilizadas
- Java 21
- Spring Boot 3.4.4
- MySQL 8.0
- Maven 3.9.9
- JPA/Hibernate
- Gutendex API
- OpenCSV 5.7.1

## Contribución
Si deseas contribuir al proyecto:
1. Haz un Fork del repositorio
2. Crea una rama para tu funcionalidad (`git checkout -b feature/AmazingFeature`)
3. Haz commit de tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Contacto
Wilmer Gulcochia - [@misterio1989w](https://x.com/misterio1989w) - karlwgs1989@gmail.com

Link del Proyecto: [https://github.com/mysterio-wil/LiterAluraChallengeJava](https://github.com/mysterio-wil/LiterAluraChallengeJava)

## Historial de Versiones

### v1.2.0 — Última versión con interfaz de consola
- Todas las funcionalidades implementadas hasta aquí funcionan mediante consola.
- A partir de este punto, el desarrollo continuará con la migración a una interfaz gráfica.

### v1.1.0
- Nuevo: Sistema de favoritos para libros (marcar, desmarcar y listar favoritos desde el menú)

### v1.0.0
- Funcionalidades base implementadas:
    - Búsqueda de libros por título
    - Búsqueda de autores por nombre
    - Listado de libros registrados
    - Listado de autores registrados
    - Búsqueda de autores por año
    - Listado de libros por idioma
    - Sistema completo de estadísticas con menús anidados
    - Top 10 libros más descargados
- Integración con Gutendex API
- Base de datos MySQL
- Cierre correcto de la aplicación
- Puerto del servidor: 8888

### Próximas Versiones (Planificado)
- Sistema de recomendaciones
- Interfaz gráfica
- Estadísticas avanzadas