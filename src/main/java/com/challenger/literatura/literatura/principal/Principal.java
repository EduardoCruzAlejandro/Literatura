<<<<<<< HEAD
package com.challenger.literatura.literatura.principal;

import com.challenger.literatura.literatura.model.*;
import com.challenger.literatura.literatura.repository.AutorRepository;
import com.challenger.literatura.literatura.repository.LibroRepository;
import com.challenger.literatura.literatura.service.ConsumoAPI;
import com.challenger.literatura.literatura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String url = "https://gutendex.com/books/";


    private AutorRepository autorRepository;

    private LibroRepository libroRepository;

    @Autowired
    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void menu() {
        while (true) {
            System.out.println("""
                    *Bienvenido, seleccione una opcion.
                    1.Buscar libros
                    2.Mostrar autores vivos por fecha
                    3.Mostrar libros registrados
                    4.Mostrar autores registrados
                    5.Mostrar libros por idioma
                    0.Salir
                    """);
            int opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1:
                    getLibros();
                    break;
                case 2:
                    getAutoresVivos();
                    break;
                case 3:
                    getRegistros();
                    break;
                case 4:
                    getAutores();
                    break;
                case 5:
                    getIdiomas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida, intente de nuevo");
                    continue;
            }
            if (opcion == 0) {
                break;
            }
        }
    }

    private void getIdiomas() {
        // Solicitar el idioma al usuario
        System.out.println("Ingrese el idioma para buscar libros (ejemplo: en, es):");
        String idiomaIngresado = teclado.nextLine().toLowerCase();

        // Obtener los libros que coinciden con el idioma de la base de datos
        List<Libro> libros = libroRepository.findAll();
        if (libros != null && !libros.isEmpty()) {
            List<String> librosConIdioma = libros.stream()
                    .filter(libro -> libro.getIdiomas().contains(idiomaIngresado))
                    .map(libro -> "Título: " + libro.getTitulo())
                    .toList();

            // Mostrar resultados
            if (librosConIdioma.isEmpty()) {
                System.out.println("No se encontraron libros en el idioma " + idiomaIngresado + ".");
            } else {
                System.out.println("Libros en el idioma " + idiomaIngresado + ":");
                librosConIdioma.forEach(System.out::println);
            }
        } else {
            System.out.println("No se encontraron libros para evaluar.");
        }
    }

    private void getAutores() {
        // Mostrar los autores registrados en la base de datos
        List<Autor> autores = autorRepository.findAll();
        if (autores != null && !autores.isEmpty()) {
            System.out.println("Autores registrados:");
            autores.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de muerte: " + autor.getFechaMuerte());
                System.out.println("----");
            });
        } else {
            System.out.println("No hay autores registrados.");
        }
    }

    private void getRegistros() {
        // Mostrar los libros registrados en la base de datos
        List<Libro> libros = libroRepository.findAllWithAutores();
        if (libros != null && !libros.isEmpty()) {
            System.out.println("Libros registrados:");
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());

                // Imprimir autores correctamente
                if (libro.getAutores() != null && !libro.getAutores().isEmpty()) {
                    libro.getAutores().forEach(autor ->
                            System.out.println("Autor: " + autor.getNombre())
                    );
                } else {
                    System.out.println("Sin autores.");
                }

                System.out.println("Idiomas: " + libro.getIdiomas());
                System.out.println("Descargas: " + libro.getDescargas());
                System.out.println("----");
            });
        } else {
            System.out.println("No hay libros registrados.");
        }
    }

    private void getAutoresVivos() {
        System.out.println("Ingrese fecha");
        int anioIngresado;
        try {
            anioIngresado = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
            return;
        }

        // Obtener autores de la base de datos
        List<Autor> autores = autorRepository.findAll();
        if (autores != null && !autores.isEmpty()) {
            // Filtrar autores vivos antes del año ingresado y muertos después o en ese año
            List<String> autoresFiltrados = autores.stream()
                    .filter(autor -> autor.getFechaNacimiento() != null && autor.getFechaNacimiento() < anioIngresado
                            && (autor.getFechaMuerte() == null || autor.getFechaMuerte() >= anioIngresado))
                    .map(autor -> autor.getNombre() + " (Nacido: " + autor.getFechaNacimiento() + ", Fallecido: " + autor.getFechaMuerte() + ")")
                    .toList();

            // Mostrar resultados
            if (autoresFiltrados.isEmpty()) {
                System.out.println("No se encontraron autores que cumplan con la condición.");
            } else {
                System.out.println("Autores que vivieron antes de " + anioIngresado + " y murieron después o en ese año:");
                autoresFiltrados.forEach(System.out::println);
            }
        } else {
            System.out.println("No se encontraron autores en la base de datos.");
        }
    }

    // Buscar libros desde la API
    private Datos buscarLibros() {
        System.out.println("Ingrese el nombre del libro");
        var libroname = teclado.nextLine();
        var json = consumoApi.obtenerDatos(url + "?search=" + libroname.toLowerCase().replace(" ", "%20"));
        System.out.println("url creada: " + json);
        return conversor.obtenerDatos(json, Datos.class);
    }

    // Mostrar libros desde la API
    private void getLibros() {
        Datos datosLibro = buscarLibros();
        if (datosLibro != null && datosLibro.datos() != null && !datosLibro.datos().isEmpty()) {
            datosLibro.datos().forEach(libro -> {
                // Crear una lista para almacenar los autores que ya existen en la base de datos
                List<Autor> autoresGuardados = new ArrayList<>();

                // Recorrer los autores del libro
                libro.autores().forEach(autor -> {
                    // Buscar si el autor ya existe en la base de datos (suponiendo que buscas por nombre)
                    Autor autorExistente = autorRepository.findByNombre(autor.nombre());

                    // Si no existe, crear una nueva instancia y guardarlo
                    if (autorExistente == null) {
                        try {
                            autorExistente = new Autor(autor); // Crear una nueva instancia de Autor
                            autorRepository.save(autorExistente); // Guardar el autor en la base de datos
                        } catch (Exception e){
                            System.out.println("***Error al guardar autor: "+ e.getMessage());
                        }

                    }
                    // Añadir el autor a la lista de autores guardados
                    autoresGuardados.add(autorExistente);
                });
                try {
                    // Crear una nueva instancia de Libro y establecer la lista de autores
                    Libro libroEntity = new Libro(libro);
                    libroEntity.setAutores(autoresGuardados); // Establecer la relación entre libro y autores

                    // Guardar el libro en la base de datos
                    libroRepository.save(libroEntity);
                }catch (Exception e){
                    System.out.println("***Error al guardar libro: "+e.getMessage());
                }


                // Mostrar los libros y autores
                System.out.println("Título: " + libro.titulo());
                libro.autores().forEach(autor ->
                        System.out.println("Autor: " + autor.nombre()));
                System.out.println("Idiomas: " + String.join(", ", libro.idiomas()));
                System.out.println("Descargas: " + libro.descargas());
            });
        } else {
            System.out.println("No se encontraron libros.");
        }
    }
}
=======
package com.challenger.literatura.literatura.principal;

import com.challenger.literatura.literatura.model.*;
import com.challenger.literatura.literatura.repository.AutorRepository;
import com.challenger.literatura.literatura.repository.LibroRepository;
import com.challenger.literatura.literatura.service.ConsumoAPI;
import com.challenger.literatura.literatura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String url = "https://gutendex.com/books/";


    private AutorRepository autorRepository;

    private LibroRepository libroRepository;

    @Autowired
    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void menu() {
        while (true) {
            System.out.println("""
                    *Bienvenido, seleccione una opcion.
                    1.Buscar libros
                    2.Mostrar autores vivos por fecha
                    3.Mostrar libros registrados
                    4.Mostrar autores registrados
                    5.Mostrar libros por idioma
                    0.Salir
                    """);
            int opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1:
                    getLibros();
                    break;
                case 2:
                    getAutoresVivos();
                    break;
                case 3:
                    getRegistros();
                    break;
                case 4:
                    getAutores();
                    break;
                case 5:
                    getIdiomas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida, intente de nuevo");
                    continue;
            }
            if (opcion == 0) {
                break;
            }
        }
    }

    private void getIdiomas() {
        // Solicitar el idioma al usuario
        System.out.println("Ingrese el idioma para buscar libros (ejemplo: en, es):");
        String idiomaIngresado = teclado.nextLine().toLowerCase();

        // Obtener los libros que coinciden con el idioma de la base de datos
        List<Libro> libros = libroRepository.findAll();
        if (libros != null && !libros.isEmpty()) {
            List<String> librosConIdioma = libros.stream()
                    .filter(libro -> libro.getIdiomas().contains(idiomaIngresado))
                    .map(libro -> "Título: " + libro.getTitulo())
                    .toList();

            // Mostrar resultados
            if (librosConIdioma.isEmpty()) {
                System.out.println("No se encontraron libros en el idioma " + idiomaIngresado + ".");
            } else {
                System.out.println("Libros en el idioma " + idiomaIngresado + ":");
                librosConIdioma.forEach(System.out::println);
            }
        } else {
            System.out.println("No se encontraron libros para evaluar.");
        }
    }

    private void getAutores() {
        // Mostrar los autores registrados en la base de datos
        List<Autor> autores = autorRepository.findAll();
        if (autores != null && !autores.isEmpty()) {
            System.out.println("Autores registrados:");
            autores.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de muerte: " + autor.getFechaMuerte());
                System.out.println("----");
            });
        } else {
            System.out.println("No hay autores registrados.");
        }
    }

    private void getRegistros() {
        // Mostrar los libros registrados en la base de datos
        List<Libro> libros = libroRepository.findAllWithAutores();
        if (libros != null && !libros.isEmpty()) {
            System.out.println("Libros registrados:");
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());

                // Imprimir autores correctamente
                if (libro.getAutores() != null && !libro.getAutores().isEmpty()) {
                    libro.getAutores().forEach(autor ->
                            System.out.println("Autor: " + autor.getNombre())
                    );
                } else {
                    System.out.println("Sin autores.");
                }

                System.out.println("Idiomas: " + libro.getIdiomas());
                System.out.println("Descargas: " + libro.getDescargas());
                System.out.println("----");
            });
        } else {
            System.out.println("No hay libros registrados.");
        }
    }

    private void getAutoresVivos() {
        System.out.println("Ingrese fecha");
        int anioIngresado;
        try {
            anioIngresado = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
            return;
        }

        // Obtener autores de la base de datos
        List<Autor> autores = autorRepository.findAll();
        if (autores != null && !autores.isEmpty()) {
            // Filtrar autores vivos antes del año ingresado y muertos después o en ese año
            List<String> autoresFiltrados = autores.stream()
                    .filter(autor -> autor.getFechaNacimiento() != null && autor.getFechaNacimiento() < anioIngresado
                            && (autor.getFechaMuerte() == null || autor.getFechaMuerte() >= anioIngresado))
                    .map(autor -> autor.getNombre() + " (Nacido: " + autor.getFechaNacimiento() + ", Fallecido: " + autor.getFechaMuerte() + ")")
                    .toList();

            // Mostrar resultados
            if (autoresFiltrados.isEmpty()) {
                System.out.println("No se encontraron autores que cumplan con la condición.");
            } else {
                System.out.println("Autores que vivieron antes de " + anioIngresado + " y murieron después o en ese año:");
                autoresFiltrados.forEach(System.out::println);
            }
        } else {
            System.out.println("No se encontraron autores en la base de datos.");
        }
    }

    // Buscar libros desde la API
    private Datos buscarLibros() {
        System.out.println("Ingrese el nombre del libro");
        var libroname = teclado.nextLine();
        var json = consumoApi.obtenerDatos(url + "?search=" + libroname.toLowerCase().replace(" ", "%20"));
        System.out.println("url creada: " + json);
        return conversor.obtenerDatos(json, Datos.class);
    }

    // Mostrar libros desde la API
    private void getLibros() {
        Datos datosLibro = buscarLibros();
        if (datosLibro != null && datosLibro.datos() != null && !datosLibro.datos().isEmpty()) {
            datosLibro.datos().forEach(libro -> {
                // Crear una lista para almacenar los autores que ya existen en la base de datos
                List<Autor> autoresGuardados = new ArrayList<>();

                // Recorrer los autores del libro
                libro.autores().forEach(autor -> {
                    // Buscar si el autor ya existe en la base de datos (suponiendo que buscas por nombre)
                    Autor autorExistente = autorRepository.findByNombre(autor.nombre());

                    // Si no existe, crear una nueva instancia y guardarlo
                    if (autorExistente == null) {
                        try {
                            autorExistente = new Autor(autor); // Crear una nueva instancia de Autor
                            autorRepository.save(autorExistente); // Guardar el autor en la base de datos
                        } catch (Exception e){
                            System.out.println("***Error al guardar autor: "+ e.getMessage());
                        }

                    }
                    // Añadir el autor a la lista de autores guardados
                    autoresGuardados.add(autorExistente);
                });
                try {
                    // Crear una nueva instancia de Libro y establecer la lista de autores
                    Libro libroEntity = new Libro(libro);
                    libroEntity.setAutores(autoresGuardados); // Establecer la relación entre libro y autores

                    // Guardar el libro en la base de datos
                    libroRepository.save(libroEntity);
                }catch (Exception e){
                    System.out.println("***Error al guardar libro: "+e.getMessage());
                }


                // Mostrar los libros y autores
                System.out.println("Título: " + libro.titulo());
                libro.autores().forEach(autor ->
                        System.out.println("Autor: " + autor.nombre()));
                System.out.println("Idiomas: " + String.join(", ", libro.idiomas()));
                System.out.println("Descargas: " + libro.descargas());
            });
        } else {
            System.out.println("No se encontraron libros.");
        }
    }
}
>>>>>>> 9ed73d94a27d4af91ed1b6fa3c2491d71764ac91
