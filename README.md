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

### Pendientes
- [ ] Implementar sistema de recomendaciones basado en preferencias
- [ ] Agregar interfaz gráfica

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

2. Menú de opciones:
    - Opción 1: Buscar libro por título
    - Opción 2: Listar libros registrados
    - Opción 3: Listar autores registrados
    - Opción 4: Listar autores vivos en un año específico
    - Opción 5: Listar libros por idioma
    - Opción 6: Mostrar estadísticas
        - Estadísticas de Libros
            - Total de libros
            - Distribución por idioma
            - Estadísticas de descargas
        - Estadísticas de Autores
            - Total de autores
            - Promedio de libros por autor
            - Estadísticas de edad de autores
        - Estadísticas Combinadas
            - Estadísticas de descargas por autor
            - Distribución de libros por siglo
    - Opción 7: Top 10 libros más descargados
    - Opción 8: Buscar autor por nombre
    - Opción 9: Listar autores nacidos en un rango de años
    - Opción 10: Marcar o desmarcar un libro como favorito (por título exacto)
    - Opción 11: Listar todos los libros favoritos registrados
    - Opción 12: Exportar todos los libros
    - Opción 13: Exportar libros favoritos
    - Opción 14: Búsqueda avanzada de libros
    - Opción 0: Salir

### Sistema de Favoritos

Puedes marcar o desmarcar libros como favoritos y consultar la lista de tus favoritos desde el menú principal:

- **Opción 10:** Marcar o desmarcar un libro como favorito (por título exacto).
- **Opción 11:** Listar todos los libros favoritos registrados.

Los favoritos se almacenan en la base de datos y puedes gestionarlos fácilmente desde la interfaz de consola.

### Búsqueda avanzada de libros (Interfaz Gráfica)

A partir de la versión 1.1.0, la búsqueda avanzada de libros se realiza desde una interfaz gráfica JavaFX:

- Accede desde el menú principal con el botón **"Buscar libros"**.
- Puedes filtrar por título, autor y/o idioma (todos los campos son opcionales).
- Si no ingresas ningún parámetro, se mostrará el mensaje: _"No ingresó ningún parámetro de búsqueda"_ debajo de los botones.
- Si no se encuentra ningún resultado, se mostrará el mensaje: _"No se encontró ningún resultado"_ en el mismo lugar.
- Los resultados aparecen en una tabla dentro de la misma ventana.
- El botón **"Volver"** te regresa al menú principal.
- El botón **"Salir"** cierra completamente la aplicación.

> La integración entre JavaFX y Spring Boot permite que todas las vistas y controladores usen inyección de dependencias correctamente.

### Nueva función: Búsqueda de libros en Gutendex desde la interfaz gráfica

Ahora puedes buscar libros directamente en la API de Gutendex desde la aplicación JavaFX.

#### ¿Cómo funciona?
- Accede desde el menú principal con el botón **"Importar libros desde Gutendex"**.
- Ingresa una **palabra clave** relevante del título (ejemplo: `quijote`, `pride`, etc.).
- (Opcional) Puedes filtrar por idioma usando el código ISO (ej: `es` para español, `en` para inglés).
- Haz clic en **Buscar en Gutendex**.
- Si hay resultados, se muestran en una tabla con título, autor, idioma y descargas.
- Si no hay resultados, se sugiere probar con una sola palabra clave o variantes del título.
- Usa el botón **Volver** para regresar al menú principal.

> **Nota:** La búsqueda en Gutendex es literal: funciona mejor con una sola palabra clave relevante.

#### Ejemplo visual
- Buscar: `quijote` → muestra resultados.
- Buscar: `don quijote` → puede no mostrar resultados (probar solo con `quijote`).

### Importar libros desde Gutendex a la base de datos local

Ahora puedes importar cualquier libro encontrado en la búsqueda Gutendex directamente a tu base de datos local.

#### ¿Cómo funciona?
1. Busca un libro usando una palabra clave relevante del título.
2. Selecciona el libro deseado en la tabla de resultados.
3. Haz clic en **"Importar a base de datos"**.
4. El sistema:
    - Busca o crea el autor en tu base de datos.
    - Verifica si el libro ya existe (por título y autor).
    - Si no existe, lo guarda en la base de datos local.
    - Muestra un mensaje de éxito, advertencia si ya existe, o error si ocurre algún problema.

> **Nota:** Solo se permite importar un libro a la vez. Si el libro ya existe, recibirás una advertencia.

#### Ejemplo visual
- Buscar: `quijote` → seleccionar un resultado → **Importar a base de datos**.
- Si el libro ya está en la base local, verás un mensaje indicando que ya existe.

### Exportación de datos

Puedes exportar la lista de libros registrados o tus libros favoritos desde el menú principal:

- **Opción 12:** Exportar todos los libros
- **Opción 13:** Exportar libros favoritos

Al seleccionar una opción, elige el formato de exportación:
- **1:** CSV
- **2:** JSON

Los archivos exportados se guardarán automáticamente en tu escritorio con los siguientes nombres:
- `libros.csv` o `libros.json`
- `favoritos.csv` o `favoritos.json`

Esto facilita el acceso y manejo de los datos fuera de la aplicación.

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