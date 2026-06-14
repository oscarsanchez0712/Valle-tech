package com.valletech.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.valletech.model.Producto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReporteUtil {

    private static final BaseColor COLOR_HEADER   = new BaseColor(26, 82, 118);
    private static final BaseColor COLOR_FILA_PAR = new BaseColor(212, 230, 241);
    private static final BaseColor COLOR_TITULO   = new BaseColor(21, 67, 96);

    public static String generarReporte(List<Producto> productos, String rutaDestino) throws DocumentException, IOException {

        if (rutaDestino == null || rutaDestino.isEmpty()) {
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            rutaDestino = System.getProperty("user.home") + "/Reporte_Productos_" + fecha + ".pdf";
        }

        Document doc = new Document(PageSize.A4, 36, 36, 54, 36);
        PdfWriter.getInstance(doc, new FileOutputStream(rutaDestino));
        doc.open();

        // ── Encabezado ─────────────────────────────────────────────
        Font fuenteEmpresa   = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD,   COLOR_TITULO);
        Font fuenteSubtitulo = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.DARK_GRAY);
        Font fuenteFecha     = new Font(Font.FontFamily.HELVETICA,  9, Font.ITALIC, BaseColor.GRAY);

        Paragraph empresa = new Paragraph("ValleTech", fuenteEmpresa);
        empresa.setAlignment(Element.ALIGN_CENTER);
        doc.add(empresa);

        Paragraph subtitulo = new Paragraph("Sistema de Gestión de Productos", fuenteSubtitulo);
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        doc.add(subtitulo);

        Paragraph titulo = new Paragraph("REPORTE DE PRODUCTOS",
                new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, COLOR_HEADER));
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingBefore(8);
        doc.add(titulo);

        String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        Paragraph fecha = new Paragraph("Generado el: " + fechaActual, fuenteFecha);
        fecha.setAlignment(Element.ALIGN_CENTER);
        fecha.setSpacingAfter(6);
        doc.add(fecha);

        // ── Línea separadora (sin LineSeparator) ───────────────────
        PdfPTable lineaSep = new PdfPTable(1);
        lineaSep.setWidthPercentage(100);
        lineaSep.setSpacingAfter(10);
        PdfPCell celdaLinea = new PdfPCell(new Phrase(" "));
        celdaLinea.setBorderWidthBottom(2f);
        celdaLinea.setBorderColorBottom(COLOR_HEADER);
        celdaLinea.setBorderWidthTop(0);
        celdaLinea.setBorderWidthLeft(0);
        celdaLinea.setBorderWidthRight(0);
        celdaLinea.setPaddingBottom(4);
        lineaSep.addCell(celdaLinea);
        doc.add(lineaSep);

        // ── Tabla de productos ──────────────────────────────────────
        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{0.8f, 3f, 2f, 1.5f, 1.2f});
        tabla.setSpacingBefore(4);

        String[] encabezados = {"ID", "Nombre", "Categoría", "Precio (S/)", "Stock"};
        Font fuenteEncabezado = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
        for (String enc : encabezados) {
            PdfPCell celda = new PdfPCell(new Phrase(enc, fuenteEncabezado));
            celda.setBackgroundColor(COLOR_HEADER);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setPadding(7);
            tabla.addCell(celda);
        }

        Font fuenteDato = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
        int fila = 0;
        double totalValor = 0;

        for (Producto p : productos) {
            BaseColor colorFila = (fila % 2 == 0) ? BaseColor.WHITE : COLOR_FILA_PAR;
            fila++;
            agregarCelda(tabla, String.valueOf(p.getId()),              fuenteDato, colorFila, Element.ALIGN_CENTER);
            agregarCelda(tabla, p.getNombre(),                          fuenteDato, colorFila, Element.ALIGN_LEFT);
            agregarCelda(tabla, p.getCategoria(),                       fuenteDato, colorFila, Element.ALIGN_LEFT);
            agregarCelda(tabla, String.format("%.2f", p.getPrecio()),   fuenteDato, colorFila, Element.ALIGN_RIGHT);
            agregarCelda(tabla, String.valueOf(p.getStock()),           fuenteDato, colorFila, Element.ALIGN_CENTER);
            totalValor += p.getPrecio() * p.getStock();
        }
        doc.add(tabla);

        // ── Resumen ─────────────────────────────────────────────────
        doc.add(Chunk.NEWLINE);
        Font fuenteResumen = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, COLOR_TITULO);
        doc.add(new Paragraph("Total de productos: " + productos.size(), fuenteResumen));
        doc.add(new Paragraph(String.format("Valor total del inventario: S/ %.2f", totalValor), fuenteResumen));

        // ── Pie de página ───────────────────────────────────────────
        doc.add(Chunk.NEWLINE);
        PdfPTable lineaPie = new PdfPTable(1);
        lineaPie.setWidthPercentage(100);
        lineaPie.setSpacingBefore(8);
        PdfPCell celdaPie = new PdfPCell(new Phrase(" "));
        celdaPie.setBorderWidthTop(1f);
        celdaPie.setBorderColorTop(BaseColor.GRAY);
        celdaPie.setBorderWidthBottom(0);
        celdaPie.setBorderWidthLeft(0);
        celdaPie.setBorderWidthRight(0);
        lineaPie.addCell(celdaPie);
        doc.add(lineaPie);

        Font fuentePie = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY);
        Paragraph pie = new Paragraph("ValleTech © 2024  |  Documento generado automáticamente", fuentePie);
        pie.setAlignment(Element.ALIGN_CENTER);
        doc.add(pie);

        doc.close();
        return rutaDestino;
    }

    private static void agregarCelda(PdfPTable tabla, String texto, Font fuente,
                                     BaseColor fondo, int alineacion) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBackgroundColor(fondo);
        celda.setHorizontalAlignment(alineacion);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(5);
        tabla.addCell(celda);
    }
}