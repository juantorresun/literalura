package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        Long id,
        @JsonProperty("title") String titulo,
        @JsonProperty("authors") List<DatosAutor> autores,
        @JsonProperty("languages") List<String> idiomas,
        @JsonProperty("download_count") Integer numeroDescargas
) {}