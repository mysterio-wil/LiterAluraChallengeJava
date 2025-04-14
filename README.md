# LiterAlura Challenge Java

[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.9.9-orange.svg)](https://maven.apache.org/)

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
- [Licencia](#licencia)
- [Contacto](#contacto)

## Descripción
LiterAlura es una aplicación Java que permite explorar y gestionar un catálogo de libros y autores. La aplicación se conecta a la API de Gutendex para obtener información sobre libros y autores, y almacena esta información en una base de datos MySQL para su posterior consulta.

## Características
### Implementadas
- [x] Búsqueda de libros por título
- [x] Listado de libros registrados
- [x] Listado de autores registrados
- [x] Búsqueda de autores vivos en un año específico
- [x] Listado de libros por idioma
- [x] Estadísticas de libros por idioma
- [x] Persistencia de datos en base de datos MySQL

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
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   ```

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
   - Opción 6: Mostrar estadísticas de idiomas
   - Opción 0: Salir

## Estructura del Proyecto
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── alura/
│   │           └── LiterAluraChallengeJava/
│   │               ├── model/
│   │               │   ├── Autor.java
│   │               │   └── Libro.java
│   │               ├── repository/
│   │               │   ├── AutorRepository.java
│   │               │   └── LibroRepository.java
│   │               ├── service/
│   │               │   └── ConsumoAPI.java
│   │               └── LiterAluraChallengeJavaApplication.java
│   └── resources/
│       └── application.properties
```

## Tecnologías Utilizadas
- **Spring Boot 3.4.4**: Framework principal
- **Spring Data JPA**: Persistencia de datos
- **MySQL**: Base de datos
- **Jackson**: Procesamiento JSON
- **Maven**: Gestión de dependencias

## Contribución
1. Fork del proyecto
2. Crear rama de feature (`git checkout -b feature/AmazingFeature`)
3. Commit de cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

## Licencia
Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

## Contacto
Wilmer Gulcochia - [@misterio1989w](https://x.com/misterio1989w) - karlwgs1989@gmail.com

Link del Proyecto: [https://github.com/mysterio-wil/LiterAluraChallengeJava](https://github.com/mysterio-wil/LiterAluraChallengeJava) 