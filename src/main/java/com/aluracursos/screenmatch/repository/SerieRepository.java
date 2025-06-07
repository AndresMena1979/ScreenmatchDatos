package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long>{

    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);





/* Optional<Serie>:
El método devuelve un Optional que puede contener una entidad Serie si se encuentra, o estar vacío si no hay coincidencias.

findByTitulo:
Spring busca por el campo titulo de la entidad Serie. El nombre del campo debe coincidir con un atributo de la clase Serie.

Contains:
Permite coincidencias parciales (como si usaras %nombre% en SQL).
Por ejemplo, si el título es "Breaking Bad", la búsqueda "Break" también lo encuentra.

IgnoreCase:
La búsqueda ignora mayúsculas y minúsculas.
"breaking" encuentra "Breaking Bad", "BREAKING" también lo hace.*/

}
