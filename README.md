# LiterAlura Challenge Java

## Descripción
LiterAlura es una aplicación Java que permite explorar y gestionar un catálogo de libros y autores utilizando la API de Gutendex. La aplicación permite buscar libros, listar autores, y realizar búsquedas específicas por idioma o año.

## Características
- Búsqueda de libros por título
- Listado de libros registrados
- Listado de autores registrados
- Búsqueda de autores vivos en un año específico
- Búsqueda de libros por idioma
- Persistencia de datos en base de datos H2
- Interfaz de línea de comandos intuitiva

## Tecnologías Utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven
- Jackson para procesamiento de JSON
- Jansi para colores en la consola

## Requisitos
- Java 17 o superior
- Maven 3.9.9 o superior
- Conexión a internet para acceder a la API de Gutendex

## Configuración
1. Clonar el repositorio
2. Asegurarse de tener Java 17 instalado
3. Configurar las variables de entorno si es necesario
4. Compilar el proyecto con Maven:
   ```bash
   mvn clean install
   ```

## Ejecución
Para ejecutar la aplicación:
```bash
mvn spring-boot:run
```

## Uso
La aplicación presenta un menú interactivo con las siguientes opciones:
1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. Listar autores vivos en un año
5. Listar libros por idioma
0. Salir

## Base de Datos
- La aplicación utiliza H2 Database en memoria
- La consola H2 está disponible en: http://localhost:8081/h2-console
- Credenciales:
  - URL: jdbc:h2:mem:literalura
  - Usuario: sa
  - Contraseña: (vacía)

## Estructura del Proyecto
```
src/main/java/com/alura/LiterAluraChallengeJava/
├── model/
│   ├── Autor.java
│   └── Libro.java
├── repository/
│   ├── AutorRepository.java
│   └── LibroRepository.java
├── service/
│   ├── AutorService.java
│   └── LibroService.java
├── dto/
│   ├── AutorDTO.java
│   └── LibroDTO.java
└── LiterAluraApplication.java
```

## Contribución
Las contribuciones son bienvenidas. Por favor, asegúrate de:
1. Hacer fork del proyecto
2. Crear una rama para tu feature
3. Hacer commit de tus cambios
4. Hacer push a la rama
5. Abrir un Pull Request

## Licencia
Este proyecto está bajo la Licencia MIT. 