package com.alura.LiterAluraChallengeJava.ui;

import com.alura.LiterAluraChallengeJava.repository.LibroRepository;
import com.alura.LiterAluraChallengeJava.model.Libro;
import com.alura.LiterAluraChallengeJava.util.ExportarLibrosUtil;
import com.alura.LiterAluraChallengeJava.SpringContextProvider;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.util.List;
import java.io.File;

@Component
public class MenuPrincipalView {
    @Autowired
    private LibroRepository libroRepository;

    public void mostrar(Stage stage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        Button btnBuscar = new Button("Buscar libros");
        Button btnImportarGutendex = new Button("Importar libros desde Gutendex");
        Button btnExportar = new Button("Exportar datos");
        Button btnSalir = new Button("Salir");

        btnBuscar.setOnAction(e -> {
            BusquedaLibrosView busquedaView = SpringContextProvider.getBean(BusquedaLibrosView.class);
            busquedaView.mostrar(stage);
        });
        btnImportarGutendex.setOnAction(e -> {
            BuscarGutendexView buscarGutendexView = SpringContextProvider.getBean(BuscarGutendexView.class);
            buscarGutendexView.mostrar(stage);
        });
        btnExportar.setOnAction(e -> {
            // 1. Preguntar formato con ChoiceDialog
            ChoiceDialog<String> formatoDialog = new ChoiceDialog<>("CSV", "CSV", "XLSX");
            formatoDialog.setTitle("Exportar datos");
            formatoDialog.setHeaderText("¿En qué formato desea exportar los datos?");
            formatoDialog.setContentText("Elija el formato:");
            var result = formatoDialog.showAndWait();
            if (result.isEmpty()) return;
            String formato = result.get();

            // 2. FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo de libros");
            if (formato.equalsIgnoreCase("CSV")) {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
            } else {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx"));
            }
            File file = fileChooser.showSaveDialog(stage);
            if (file == null) return;

            // 3. Obtener libros y exportar
            List<Libro> libros = libroRepository.findAll();
            try {
                if (formato.equalsIgnoreCase("CSV")) {
                    ExportarLibrosUtil.exportarCSV(libros, file);
                } else {
                    ExportarLibrosUtil.exportarXLSX(libros, file);
                }
                Alert ok = new Alert(AlertType.INFORMATION, "Exportación exitosa en: " + file.getAbsolutePath());
                ok.setTitle("Éxito");
                ok.showAndWait();
            } catch (Exception ex) {
                Alert err = new Alert(AlertType.ERROR, "Error al exportar: " + ex.getMessage());
                err.setTitle("Error");
                err.showAndWait();
            }
        });
        btnSalir.setOnAction(e -> {
            stage.close();
            javafx.application.Platform.exit();
            System.exit(0);
        });

        root.getChildren().addAll(btnBuscar, btnImportarGutendex, btnExportar, btnSalir);

        Scene scene = new Scene(root, 400, 320);
        stage.setTitle("LiterAlura - Menú Principal");
        stage.setScene(scene);
        stage.show();
    }
}
