package com.alura.literalura.principal;

//import com.alura.literalura.model.*;
//import com.alura.literalura.repository.SerieRepository;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosResultado;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.LibroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class Principal {
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoApi;
    private final LibroService libroService;

    private final String URL_BASE = "https://gutendex.com/books/?search=";

    public Principal(ConsumoAPI consumoApi, LibroService libroService, LibroRepository libroRepository ,
                     AutorRepository autorRepository) {
        this.consumoApi = consumoApi;
        this.libroService = libroService;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {

        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un año
                    5 - Listar libros por idioma
                    0 - Salir
                    """);

            opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2-> listarLibros();
                case 3-> listarAutores();
                case 4-> listarAutoresVivosPorAnio();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {

        System.out.println("Ingrese el título del libro:");
        String titulo = teclado.nextLine();

        String json = consumoApi.obtenerDatos(URL_BASE + titulo.replace(" ", "+"));

        try {
            ObjectMapper mapper = new ObjectMapper();
            DatosResultado resultado = mapper.readValue(json, DatosResultado.class);

            if (resultado.results().isEmpty()) {
                System.out.println("No se encontraron libros");
                return;
            }

            resultado.results().forEach(libroService::guardarLibro);

        } catch (Exception e) {
            System.out.println("Error al procesar datos");
        }
    }
    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados todavía 📭");
            return;
        }

        libros.forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getNumeroDescargas());
            System.out.println("----------------------------");
        });
    }
    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados todavía 💤");
            return;
        }

        autores.forEach(autor -> {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getFechaNacimiento());
            System.out.println("Fallecimiento: " + autor.getFechaFallecimiento());
            System.out.println("Libros registrados: " + autor.getLibros().size());
            System.out.println("----------------------------");
        });
    }
    private void listarAutoresVivosPorAnio() {
        System.out.print("Ingrese el año: ");
        Integer anio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autores = autorRepository.autoresVivosEnAnio(anio);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
            return;
        }

        System.out.println("Autores vivos en el año " + anio + ":");
        autores.forEach(autor -> {
            System.out.println( autor.getNombre()
                    + " (" + autor.getFechaNacimiento()
                    + " - " + autor.getFechaFallecimiento() + ")");
        });
    }
    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma (ej: es, en, fr): ");
        String idioma = teclado.nextLine().toLowerCase();

        List<Libro> libros = libroRepository.findByIdiomaIgnoreCase(idioma);

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en el idioma: " + idioma);
            return;
        }

        System.out.println("Libros en idioma '" + idioma + "':");
        libros.forEach(libro -> {
            System.out.println("""
                ---------------------------
                Título: %s
                Autor: %s
                Descargas: %d
                """.formatted(
                    libro.getTitulo(),
                    libro.getAutor().getNombre(),
                    libro.getNumeroDescargas()
            ));
        });
    }


}

