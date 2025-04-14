# LiterAlura Challenge Java

## Descripción
LiterAlura es una aplicación de consola en Java que permite buscar, catalogar y gestionar libros a través de la API Gutendex, ofreciendo funcionalidades como búsqueda de libros, listado de autores, filtrado por idioma y consulta de autores por año específico.

## Tecnologías Utilizadas
- Java 21
- Spring Boot 3.4.4
- Maven
- Jackson (para manejo de JSON)
- PostgreSQL (base de datos)

## Estructura del Proyecto
```
LiterAluraChallengeJava/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── alura/
│   │   │           └── LiterAluraChallengeJava/
│   │   │               ├── principal/
│   │   │               ├── model/
│   │   │               │   ├── Datos.java
│   │   │               │   ├── DatosLibros.java
│   │   │               │   └── DatosAutor.java
│   │   │               └── service/
│   │   │                   ├── IConvierteDatos.java
│   │   │                   ├── ConvierteDatos.java
│   │   │                   └── ConsumoAPI.java
│   │   └── resources/
│   └── test/
```

## Avances del Proyecto

### Fase 1: Configuración Inicial
- [x] Creación del proyecto Spring Boot
- [x] Configuración de dependencias en pom.xml
- [x] Establecimiento de la estructura de directorios

### Fase 2: Implementación del Cliente HTTP
- [x] Creación de la interfaz IConvierteDatos
- [x] Implementación de ConvierteDatos con Jackson
- [x] Desarrollo de ConsumoAPI para peticiones HTTP

### Fase 3: Mapeo de Datos JSON
- [x] Creación de DatosAutor para mapear información de autores
- [x] Creación de DatosLibros para mapear información de libros
- [x] Creación de Datos para mapear la respuesta general de la API

### Fase 4: Conversión de Records a Clases Java
- [x] Transformación de DatosAutor a clase Java con getters y setters
- [x] Transformación de DatosLibros a clase Java con getters y setters
- [x] Transformación de Datos a clase Java con getters y setters
- [x] Implementación de métodos toString() para mejor representación
- [x] Adición de constructores vacíos para Jackson
- [x] Mantenimiento de anotaciones JSON para mapeo de datos

### Próximos Pasos
- [ ] Implementación de la persistencia de datos
- [ ] Desarrollo de la interfaz de usuario por consola
- [ ] Implementación de las funcionalidades de búsqueda y filtrado

## API Utilizada
- Gutendex API: https://gutendex.com/books/
- Documentación: https://github.com/garethbjohnson/gutendex

## Requisitos
- Java 21 o superior
- Maven
- PostgreSQL (opcional para desarrollo) 