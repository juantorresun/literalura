package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("""
    SELECT a FROM Autor a
    WHERE a.fechaNacimiento <= :anio
    AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento >= :anio)
""")
    List<Autor> autoresVivosEnAnio(Integer anio);
    Optional<Autor> findByNombre(String nombre);
}
