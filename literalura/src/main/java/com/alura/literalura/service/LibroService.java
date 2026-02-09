package com.alura.literalura.service;

import com.alura.literalura.model.DatosAutor;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void guardarLibro(DatosLibro datosLibro) {

        // ⚠️ Gutendex puede traer varios autores, usamos el primero
        DatosAutor datosAutor = datosLibro.autores().get(0);

        Autor autor = autorRepository
                .findByNombre(datosAutor.nombre())
                .orElseGet(() -> autorRepository.save(
                        new Autor(
                                datosAutor.nombre(),
                                datosAutor.fechaNacimiento(),
                                datosAutor.fechaFallecimiento()
                        )
                ));

        boolean existeLibro = libroRepository
                .findByTitulo(datosLibro.titulo())
                .isPresent();

        if (existeLibro) {
            System.out.println("⚠️ El libro ya existe en la base de datos");
            return;
        }

        Libro libro = new Libro(
                datosLibro.titulo(),
                autor,
                datosLibro.idiomas().get(0),
                datosLibro.numeroDescargas()
        );

        libroRepository.save(libro);

        System.out.println("✅ Libro guardado: " + libro.getTitulo());
    }
}
