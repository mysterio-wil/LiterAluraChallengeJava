# LiterAlura Challenge Java

[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.9.9-orange.svg)](https://maven.apache.org/)
![version](https://img.shields.io/badge/version-1.0.0-blue.svg)

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

### Pendientes
- [ ] Implementar sistema de favoritos
- [ ] Agregar funcionalidad de exportación de datos
- [ ] Implementar búsqueda avanzada por múltiples criterios
- [ ] Agregar sistema de recomendaciones basado en preferencias

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
    - Opción 0: Salir

## Últimas actualizaciones
- Implementación de un sistema completo de estadísticas con menús anidados
- Mejora en la organización del código con nueva clase MenuEstadisticas
- Optimización del manejo de datos estadísticos usando DoubleSummaryStatistics
- Corrección del cierre de la aplicación al seleccionar la opción 0
- Optimización de la gestión de recursos (cierre de Scanner)
- Cambio de puerto del servidor a 8888

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

### v1.0.0 (Actual)
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
- Sistema de favoritos
- Búsqueda avanzada con filtros
- Exportación de datos
- Interfaz gráfica
- Estadísticas avanzadas