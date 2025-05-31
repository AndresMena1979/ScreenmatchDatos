package com.aluracursos.screenmatch.model;

import com.aluracursos.screenmatch.service.ConsultaChatGPT;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;   // para importar la clase OptionalDouble, que es parte del paquete estándar de Java (java.util), y se usa cuando trabajas con valores double
                                   // opcionales (es decir, que pueden o no estar presentes).

public class Serie {

    private String titulo;

    private Integer totalTemporadas;

    private Double evaluacion;

    private String poster;

    private Categoria genero;

    private String actores;

    private String sinopsis;

    public Serie(DatosSerie datosSerie){   //constructor de Serie

        this.titulo = datosSerie.titulo();
        this.totalTemporadas = datosSerie.totalTemporadas();

        this.evaluacion= OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse( 0 );
        this.poster = datosSerie.poster();
        this.genero =Categoria.fromString(datosSerie.genero().split(",")[0].trim()); //Esta línea convierte el primer género de una cadena (que probablemente contiene varios géneros separados por comas)
                                                                                           // en una instancia del enum Categoria. Si hay mas de un genero selecciona el primero(genero que estan divididos por la ,)
        this.actores =datosSerie.actores();
        this.sinopsis = ConsultaChatGPT.obtenerTraduccion(datosSerie.sinopsis());

//        try {
//            this.sinopsis = ConsultaChatGPT.obtenerTraduccion(datosSerie.sinopsis());
//        } catch (Exception e) {
//            System.out.println("Error al traducir sinopsis: " + e.getMessage());
//            this.sinopsis = "Sinopsis no disponible";
//        }

    }

   /* OptionalDouble.of = es una forma segura y clara de trabajar con resultados double que podrían no existir.
      Double.valueOf = Convierte ese String (por ejemplo "8.5") en un objeto Double.
      orElse = Extrae el valor del OptionalDouble. Si el OptionalDouble tuviera un valor, lo devuelve.
      Si no tuviera valor (aunque en este caso siempre lo tiene), devuelve 0 como valor por defecto.  */


    // El método fromString busca convertir un texto recibido (por ejemplo "Action" o "Comedy") en un valor del enum Categoria correspondiente (como Categoria.ACCION o Categoria.COMEDIA).
    // Categoria.fromString(...) Convierte ese texto ("Action") en una instancia del enum Categoria, usando el método que tú definiste:
    // Categoria.ACCION
    // La función split() en Java se utiliza para dividir un String en partes, usando un delimitador (como una coma, espacio, guion, etc.).
    // trinm()= no traiga un valor vacio.  La función .trim() en Java elimina los espacios en blanco al inicio y al final de un String.

//----------------------------------------getter y setter


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    @Override
    public String toString() {
        return  "genero=" + genero + '\'' +
                ", titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", evaluacion=" + evaluacion +
                ", poster='" + poster + '\'' +
                ", actores='" + actores + '\'' +
                ", sinopsis='" + sinopsis + '\'';
    }
}
