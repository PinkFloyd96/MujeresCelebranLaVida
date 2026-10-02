package com.ong.desktop.servicios;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font; 
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;

public class Exportador {

    
    public static void exportarExcel(String rutaArchivo, String titulo, String[] encabezados, List<String[]> filas) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet hoja = workbook.createSheet(titulo);

            CellStyle estiloHeader = workbook.createCellStyle();
            Font fuenteHeader = workbook.createFont();
            fuenteHeader.setBold(true);
            fuenteHeader.setColor(IndexedColors.WHITE.getIndex());
            estiloHeader.setFont(fuenteHeader);
            estiloHeader.setFillForegroundColor(IndexedColors.PINK.getIndex());
            estiloHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row filaHeader = hoja.createRow(0);
            for (int i = 0; i < encabezados.length; i++) {
                Cell celda = filaHeader.createCell(i);
                celda.setCellValue(encabezados[i]);
                celda.setCellStyle(estiloHeader);
            }

            int numFila = 1;
            for (String[] fila : filas) {
                Row filaDatos = hoja.createRow(numFila++);
                for (int i = 0; i < fila.length; i++) {
                    Cell celda = filaDatos.createCell(i);
                    celda.setCellValue(fila[i] != null ? fila[i] : "");
                }
            }

            for (int i = 0; i < encabezados.length; i++) {
                hoja.autoSizeColumn(i);
            }

            try (FileOutputStream out = new FileOutputStream(rutaArchivo)) {
                workbook.write(out);
            }
        }
    }


    public static void exportarPDF(String rutaArchivo, String titulo, String[] encabezados, List<String[]> filas) throws Exception {
        try (PDDocument doc = new PDDocument()) {
            PDPage pagina = new PDPage();
            doc.addPage(pagina);

            PDPageContentStream content = new PDPageContentStream(doc, pagina);

            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 16);
            content.newLineAtOffset(50, 750);
            content.showText(titulo);
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 10);
            content.newLineAtOffset(50, 720);
            content.showText(String.join(" | ", encabezados));
            content.endText();

            float y = 700;
            content.setFont(PDType1Font.HELVETICA, 9);
            for (String[] fila : filas) {
                if (y < 50) {
                    content.close();
                    PDPage nuevaPagina = new PDPage();
                    doc.addPage(nuevaPagina);
                    content = new PDPageContentStream(doc, nuevaPagina);
                    content.setFont(PDType1Font.HELVETICA, 9);
                    y = 750;
                }
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(String.join(" | ", fila));
                content.endText();
                y -= 15;
            }

            content.close();
            doc.save(rutaArchivo);
        }
    }
}
