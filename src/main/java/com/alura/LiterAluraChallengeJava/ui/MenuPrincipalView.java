package com.alura.LiterAluraChallengeJava.ui;

import com.alura.LiterAluraChallengeJava.SpringContextProvider;
import com.alura.LiterAluraChallengeJava.repository.LibroRepository;
import com.alura.LiterAluraChallengeJava.model.Libro;
import com.alura.LiterAluraChallengeJava.util.ExportarLibrosUtil;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.FileChooser;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class MenuPrincipalView {
    @Autowired
    private LibroRepository libroRepository;

    public void mostrar(Stage stage) {
        BorderPane root = new BorderPane();

        // Menú superior
        MenuBar menuBar = new MenuBar();
        Menu menuArchivo = new Menu("Archivo");
        MenuItem salir = new MenuItem("Salir");
        salir.setOnAction(e -> {
            stage.close();
            javafx.application.Platform.exit();
            System.exit(0);
        });
        menuArchivo.getItems().addAll(salir);

        Menu menuHerramientas = new Menu("Herramientas");
        // Sección principal
        MenuItem buscarLibros = new MenuItem("Buscar libros");
        MenuItem importarGutendex = new MenuItem("Importar desde Gutendex");
        Menu menuImportarArchivo = new Menu("Importar desde archivo...");
        MenuItem importarCSV = new MenuItem("CSV");
        MenuItem importarExcel = new MenuItem("Excel");
        menuImportarArchivo.getItems().addAll(importarCSV, importarExcel);
        // Exportar datos como submenú
        Menu menuExportarDatos = new Menu("Exportar datos");
        MenuItem exportarCSV = new MenuItem("CSV");
        MenuItem exportarExcel = new MenuItem("Excel");
        menuExportarDatos.getItems().addAll(exportarCSV, exportarExcel);
        MenuItem exportarFavoritos = new MenuItem("Exportar favoritos");
        menuHerramientas.getItems().addAll(
            buscarLibros,
            importarGutendex,
            menuImportarArchivo,
            menuExportarDatos,
            exportarFavoritos,
            new SeparatorMenuItem()
        );
        // Gestión de autores
        Menu menuAutores = new Menu("Gestión de autores");
        MenuItem verAutores = new MenuItem("Ver autores");
        MenuItem agregarAutor = new MenuItem("Agregar autor");
        MenuItem editarAutor = new MenuItem("Editar autor");
        MenuItem eliminarAutor = new MenuItem("Eliminar autor");
        menuAutores.getItems().addAll(verAutores, agregarAutor, editarAutor, eliminarAutor);
        menuHerramientas.getItems().addAll(menuAutores, new SeparatorMenuItem());
        // Estadísticas
        Menu menuEstadisticas = new Menu("Estadísticas");
        MenuItem librosPorIdioma = new MenuItem("Libros por idioma");
        MenuItem librosMasDescargados = new MenuItem("Libros más descargados");
        MenuItem autoresProlificos = new MenuItem("Autores más prolíficos");
        MenuItem descargasTotales = new MenuItem("Descargas totales");
        menuEstadisticas.getItems().addAll(librosPorIdioma, librosMasDescargados, autoresProlificos, descargasTotales);
        menuHerramientas.getItems().addAll(menuEstadisticas, new SeparatorMenuItem());
        // Respaldar/restaurar
        MenuItem respaldarBD = new MenuItem("Respaldar base de datos");
        MenuItem restaurarBD = new MenuItem("Restaurar base de datos");
        menuHerramientas.getItems().addAll(respaldarBD, restaurarBD, new SeparatorMenuItem());
        // Configuración
        Menu menuConfiguracion = new Menu("Configuración");
        MenuItem tema = new MenuItem("Tema claro/oscuro");
        MenuItem idioma = new MenuItem("Idioma de la interfaz");
        menuConfiguracion.getItems().addAll(tema, idioma);
        menuHerramientas.getItems().addAll(menuConfiguracion, new SeparatorMenuItem());
        // Ayuda
        MenuItem ayuda = new MenuItem("Ayuda / Acerca de");
        menuHerramientas.getItems().addAll(ayuda);
        // Añadir menús al menú bar
        menuBar.getMenus().setAll(menuArchivo, menuHerramientas);

        // Handlers para acciones ya implementadas
        buscarLibros.setOnAction(e -> {
            BusquedaLibrosView busquedaView = SpringContextProvider.getBean(BusquedaLibrosView.class);
            busquedaView.mostrar(stage);
        });
        importarGutendex.setOnAction(e -> {
            BuscarGutendexView buscarGutendexView = SpringContextProvider.getBean(BuscarGutendexView.class);
            buscarGutendexView.mostrar(stage);
        });

        // Banner central
        Label mensaje = new Label("Bienvenidos a\nLiterAlura");
        mensaje.setStyle("-fx-font-size: 48px; -fx-font-family: 'Comic Sans MS', 'Comic Sans', cursive; -fx-text-alignment: center; -fx-font-weight: bold; -fx-text-fill: #222;");
        StackPane centro = new StackPane(mensaje);
        centro.setAlignment(Pos.CENTER);

        // Layout final
        VBox topBox = new VBox(menuBar);
        root.setTop(topBox);
        root.setCenter(centro);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("LiterAlura - Catálogo de Libros");
        stage.setScene(scene);
        stage.show();
    }
}
