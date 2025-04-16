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
        MenuItem exportar = new MenuItem("Exportar datos");
        exportar.setOnAction(e -> {
            ChoiceDialog<String> formatoDialog = new ChoiceDialog<>("CSV", "CSV", "XLSX");
            formatoDialog.setTitle("Exportar datos");
            formatoDialog.setHeaderText("¿En qué formato desea exportar los datos?");
            formatoDialog.setContentText("Elija el formato:");
            var result = formatoDialog.showAndWait();
            if (result.isEmpty()) return;
            String formato = result.get();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo de libros");
            if (formato.equalsIgnoreCase("CSV")) {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
            } else {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx"));
            }
            File file = fileChooser.showSaveDialog(stage);
            if (file == null) return;

            List<Libro> libros = libroRepository.findAll();
            try {
                if (formato.equalsIgnoreCase("CSV")) {
                    ExportarLibrosUtil.exportarCSV(libros, file);
                } else {
                    ExportarLibrosUtil.exportarXLSX(libros, file);
                }
                Alert ok = new Alert(Alert.AlertType.INFORMATION, "Exportación exitosa en: " + file.getAbsolutePath());
                ok.setTitle("Éxito");
                ok.showAndWait();
            } catch (Exception ex) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Error al exportar: " + ex.getMessage());
                err.setTitle("Error");
                err.showAndWait();
            }
        });
        MenuItem importarGutendex = new MenuItem("Importar desde Gutendex");
        importarGutendex.setOnAction(e -> {
            BuscarGutendexView buscarGutendexView = SpringContextProvider.getBean(BuscarGutendexView.class);
            buscarGutendexView.mostrar(stage);
        });
        MenuItem buscarLibros = new MenuItem("Buscar libros");
        buscarLibros.setOnAction(e -> {
            BusquedaLibrosView busquedaView = SpringContextProvider.getBean(BusquedaLibrosView.class);
            busquedaView.mostrar(stage);
        });
        menuHerramientas.getItems().addAll(exportar, importarGutendex, buscarLibros);

        menuBar.getMenus().addAll(menuArchivo, menuHerramientas);

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
