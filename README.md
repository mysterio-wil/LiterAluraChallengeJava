# LiterAlura Challenge Java

## Descripción
LiterAlura es una aplicación Java que permite buscar y gestionar información sobre libros y autores utilizando la API de Gutendex. La aplicación permite realizar búsquedas de libros, almacenar información en una base de datos y realizar consultas específicas sobre autores y libros.

## Características
- Búsqueda de libros por título
- Almacenamiento de información de libros y autores en base de datos
- Listado de libros registrados
- Listado de autores registrados
- Búsqueda de autores vivos en un año específico
- Filtrado de libros por idioma

## Tecnologías Utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Jackson (para mapeo JSON)
- Maven

## Estructura del Proyecto
```
src/
├── main/
│   ├── java/
│   │   └── com/alura/LiterAluraChallengeJava/
│   │       ├── model/           # Entidades y DTOs
│   │       ├── repository/      # Repositorios JPA
│   │       ├── service/         # Servicios y utilidades
│   │       └── principal/       # Clase principal de la aplicación
│   └── resources/
│       └── application.properties
```

## Entidades Principales
- `Autor`: Representa un autor con su nombre, fecha de nacimiento y fecha de fallecimiento
- `Libro`: Representa un libro con su título, idioma, número de descargas y relación con el autor

## Funcionalidades Implementadas
1. **Búsqueda de Libros**
   - Permite buscar libros por título en la API de Gutendex
   - Almacena automáticamente la información del libro y su autor

2. **Gestión de Autores**
   - Almacena información de autores
   - Permite buscar autores vivos en un año específico
   - Evita duplicados de autores en la base de datos

3. **Gestión de Libros**
   - Almacena información de libros
   - Permite filtrar libros por idioma
   - Muestra información detallada de cada libro

## Cómo Usar
1. Ejecutar la aplicación
2. Seleccionar una opción del menú:
   - 1: Buscar libro por título
   - 2: Listar libros registrados
   - 3: Listar autores registrados
   - 4: Listar autores vivos en un año específico
   - 5: Listar libros por idioma
   - 0: Salir

## Configuración
La aplicación utiliza una base de datos H2 en memoria. No se requiere configuración adicional para ejecutar la aplicación.

## Próximos Pasos
- Implementar validaciones adicionales
- Mejorar el manejo de errores
- Agregar más funcionalidades de búsqueda
- Implementar interfaz gráfica 