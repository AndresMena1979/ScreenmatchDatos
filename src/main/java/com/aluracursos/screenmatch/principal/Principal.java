package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=464cb2ce";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries= new ArrayList<>();              //para series web


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }


    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");  // pide el nombre de la serie a buscar
        var nombreSerie = teclado.nextLine();    // Teclea el nombre de la serie
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY); // Envia URL a consumoApi y obtiene el Json
        System.out.println(json);                            // Imprime el Json
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);  // envia json y la clase a convertir los datos a Convierte datos y obtiene resultado
        return datos;       //retorna resultados de ConvierteDatos
    }
    private void buscarEpisodioPorSerie() {
        DatosSerie datosSerie = getDatosSerie();                // Se declara la variable datosSerie como tipo Datoserie y se le da el valor de getDatosSerie que tiene los datos convertidos a la clase DatosSerie
        List<DatosTemporadas> temporadas = new ArrayList<>();   // Se crea un array list de tipo Datos temporada, que tiene el numero de la temporada y la lista de capitulos

        for (int i = 1; i <= datosSerie.totalTemporadas(); i++) {   // genera ciclo for para recorrer cada temporada
            var json = consumoApi.obtenerDatos(URL_BASE + datosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);// envia la url a obtenerDatos de ConsumoApi, y obtiene el jason
            DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class); //crea variable datosTemporada como tipo DatosTemporadas y envia el json y la clase(DatosTemporada) a ConvierteDatos() y obtiene
                                                                                                  // el resultado de la conversion del json a java
            temporadas.add(datosTemporada); // agrega los datos al array temporadas
        }
        temporadas.forEach(System.out::println); //imprime los datos en la consola
    }
    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();  // Se crea la variable datos como tipo DatosSerie y se le da el valor de getDatosSerie, el cual retorna los datos convertidos de json a java
        datosSeries.add(datos);        // se agrega la informacion de los datos al array datosSeries
       System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {

        List<Serie> series = new ArrayList<>();                // Se crea array series de tipo Serie el cual tiene la misma informacion de datosSeries, con toString propio
        series = datosSeries.stream()    // Crea una lista de objetos Serie a partir de una lista de datos crudos (datosSeries), y transforma con stream la lista en una secuencia de datos

                .map(d -> new Serie(d))            //  Usa .map() para transformar cada elemento 'd' de datosSeries en un nuevo objeto Serie.
                .collect(Collectors.toList());              // Recoge (collect) todos los objetos Serie generados en una nueva lista.
        series.stream()                                     //  Toma la lista de series y comienza a procesarla nuevamente como un stream, ya que solo se puede usar el stream una vez
                .sorted(Comparator.comparing(Serie::getGenero)) // Ordena las series por el valor devuelto por el método getGenero().
                .forEach(System.out::println);                  //imprime


    }
}

