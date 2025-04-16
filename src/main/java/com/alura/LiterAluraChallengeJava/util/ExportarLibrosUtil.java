package com.alura.LiterAluraChallengeJava.util;

import com.alura.LiterAluraChallengeJava.model.Libro;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportarLibrosUtil {
    public static void exportarCSV(List<Libro> libros, File archivo) throws IOException {
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write("Título,Autor,Idioma,Descargas\n");
            for (Libro libro : libros) {
                writer.write(String.format("\"%s\",\"%s\",%s,%d\n",
                        libro.getTitulo().replace("\"", "\"\""),
                        libro.getAutor().getNombre().replace("\"", "\"\""),
                        libro.getIdioma(),
                        libro.getNumeroDescargas() != null ? libro.getNumeroDescargas() : 0));
            }
        }
    }

    public static void exportarXLSX(List<Libro> libros, File archivo) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Libros");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Título");
        header.createCell(1).setCellValue("Autor");
        header.createCell(2).setCellValue("Idioma");
        header.createCell(3).setCellValue("Descargas");
        int rowIdx = 1;
        for (Libro libro : libros) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(libro.getTitulo());
            row.createCell(1).setCellValue(libro.getAutor().getNombre());
            row.createCell(2).setCellValue(libro.getIdioma());
            row.createCell(3).setCellValue(libro.getNumeroDescargas() != null ? libro.getNumeroDescargas() : 0);
        }
        for (int i = 0; i < 4; i++) sheet.autoSizeColumn(i);
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(archivo)) {
            workbook.write(fos);
        }
        workbook.close();
    }
}
