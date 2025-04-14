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
│   │   │               └── service/
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