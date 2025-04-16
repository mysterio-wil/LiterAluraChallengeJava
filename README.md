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
- [x] **Exportación desde interfaz gráfica:** Ahora puedes exportar todos los libros a CSV o Excel (XLSX) desde la opción "Exportar datos" en el menú principal de la interfaz JavaFX. El usuario elige el formato y la ubicación del archivo, y recibe mensajes claros de éxito o error.
- [x] **Menú principal mejorado:** El menú principal ahora es más limpio (eliminado botón de Favoritos) y utiliza un flujo moderno para la exportación.

### Pendientes
- [ ] Implementar sistema de recomendaciones basado en preferencias
- [ ] Mejorar la experiencia de usuario con barra de menú superior (MenuBar) y panel central dinámico.

## Requisitos
- Java 21 o superior
- MySQL 8.0 o superior
- Maven 3.9.9 o superior
- Conexión a internet para acceder a la API de Gutendex

## Instalación
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/LiterAluraChallengeJava.git
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

2. **Exportar libros desde la interfaz gráfica:**
   - Abre la aplicación y selecciona "Exportar datos" en el menú principal.
   - Elige el formato de exportación (CSV o Excel).
   - Selecciona la ubicación y nombre del archivo a guardar.
   - Recibirás un mensaje de confirmación o error según corresponda.

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
- [x] Limpieza de archivos auxiliares y scripts innecesarios.
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