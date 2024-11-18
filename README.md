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



