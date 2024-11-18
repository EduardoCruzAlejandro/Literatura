# Proyecto Literatura API con Spring Boot

Este proyecto es una aplicación de consola desarrollada en Java utilizando Spring Boot y Maven, que permite gestionar libros y autores a partir de una base de datos y la API pública de **Gutendex**. A través de una interfaz de línea de comandos, los usuarios pueden interactuar con los libros y autores registrados, y buscar información sobre libros mediante la API.

## Características

- **Buscar libros**: Permite buscar libros en la API de Gutendex por título y guardar los datos de libros y autores en la base de datos.
- **Mostrar autores vivos por fecha**: Muestra los autores vivos para un año especificado.
- **Mostrar libros registrados**: Muestra todos los libros que están registrados en la base de datos.
- **Mostrar autores registrados**: Muestra todos los autores que están registrados en la base de datos.
- **Mostrar libros por idioma**: Muestra los libros que están disponibles en un idioma específico.

## Tecnologías

- **Java 17** (o superior)
- **Spring Boot** 3.x
- **Maven** como gestor de dependencias y construcción
- **JPA** para la gestión de la base de datos
- **PostgreSQL** como base de datos
- **API de Gutendex** para consultar libros
- **Jackson** para el procesamiento de JSON
- **H2** (opcional, para pruebas locales)

## Requisitos

Antes de ejecutar este proyecto, asegúrate de tener instalados:

- **Java 17** o superior.
- **Maven**.
- **PostgreSQL** o cualquier base de datos configurada.
- **IDE** como IntelliJ IDEA o Eclipse (opcional).

## Instalación

### 1. Clonar el repositorio

```bash ```
git clone https://github.com/EduardoCruzAlejandro/literatura.git
cd literatura

### 2. Configurar la base de datos

En el archivo src/main/resources/application.properties, configura la conexión a tu base de datos PostgreSQL:

```properties```
spring.datasource.url=jdbc:postgresql://localhost/literatura_db
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

Asegúrate de crear la base de datos literatura_db en PostgreSQL o modificar el nombre de la base de datos en la configuración si es necesario.

### 3. Instalar dependencias

Ejecuta el siguiente comando para instalar todas las dependencias del proyecto:

```bash```
mvn clean install

### 4. Ejecutar la aplicación

Para ejecutar el proyecto, puedes usar Maven o tu IDE:

```bash```
mvn spring-boot:run

### Estructura del proyecto
El proyecto sigue una estructura de capas común en aplicaciones basadas en Spring:

- **model:** Contiene las clases Autor y Libro, así como los registros de datos (Datos, DatosAutor, DatosLibro).
- **repository:** Contiene las interfaces AutorRepository y LibroRepository para la interacción con la base de datos.
- **service:** Contiene las clases de servicio ConsumoAPI y ConvierteDatos para manejar la comunicación con la API de Gutendex y la conversión de datos JSON.
- **principal:** Contiene la clase Principal, que maneja la lógica de la interfaz de línea de comandos.
- **LiteraturaApplication:** Clase principal para ejecutar la aplicación Spring Boot.

### Licencia 
Este proyecto está bajo la licencia MIT. Consulta el archivo LICENSE para más detalles.






