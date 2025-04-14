# LiterAlura Challenge Java

## Descripción
LiterAlura es una aplicación Java que permite explorar y gestionar un catálogo de libros y autores. La aplicación se conecta a la API de Gutendex para obtener información sobre libros y autores, y almacena esta información en una base de datos MySQL para su posterior consulta.

## Checklist de Funcionalidades
- [x] Búsqueda de libros por título
- [x] Listado de libros registrados
- [x] Listado de autores registrados
- [x] Búsqueda de autores vivos en un año específico
- [x] Listado de libros por idioma
- [x] Estadísticas de libros por idioma
- [x] Persistencia de datos en base de datos MySQL
- [ ] Implementar sistema de favoritos
- [ ] Agregar funcionalidad de exportación de datos
- [ ] Implementar búsqueda avanzada por múltiples criterios
- [ ] Agregar sistema de recomendaciones basado en preferencias

## Características Implementadas
- Búsqueda de libros por título
- Listado de libros registrados
- Listado de autores registrados
- Búsqueda de autores vivos en un año específico
- Listado de libros por idioma
- Estadísticas de libros por idioma
- Persistencia de datos en base de datos MySQL

## Requisitos
- Java 21 o superior
- MySQL 8.0 o superior
- Maven 3.9.9 o superior

## Configuración
1. Clonar el repositorio
2. Configurar la base de datos MySQL:
   - Crear una base de datos llamada `literalura`
   - Asegurarse de que el usuario `root` tenga acceso con la contraseña configurada
3. Las credenciales de la base de datos se configuran en `src/main/resources/application.properties`

## Uso
1. Ejecutar la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```
2. Seguir las instrucciones en el menú interactivo:
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
- Spring Boot 3.4.4
- Spring Data JPA
- MySQL
- Jackson para procesamiento JSON
- Maven para gestión de dependencias

## Notas
- Los datos se persisten automáticamente en la base de datos MySQL
- La aplicación mantiene un registro de libros y autores consultados
- Se pueden realizar búsquedas históricas de autores vivos en años específicos 