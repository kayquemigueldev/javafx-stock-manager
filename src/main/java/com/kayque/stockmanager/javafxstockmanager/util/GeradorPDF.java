package com.kayque.stockmanager.javafxstockmanager.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.kayque.stockmanager.javafxstockmanager.model.Produto;

import java.io.FileOutputStream;
import java.util.List;

public class GeradorPDF {

    public static void gerar(List<Produto> produtos) {

        try {

            Document document = new Document();

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream("estoque.pdf")
            );

            document.open();

            Font titulo = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    18
            );

            Paragraph p = new Paragraph(
                    "Relatório de Estoque",
                    titulo
            );

            p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);
            document.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(6);

            tabela.addCell("ID");
            tabela.addCell("Nome");
            tabela.addCell("Categoria");
            tabela.addCell("Preço");
            tabela.addCell("Qtd");
            tabela.addCell("Status");

            for (Produto produto : produtos) {

                tabela.addCell(
                        String.valueOf(produto.getId())
                );

                tabela.addCell(
                        produto.getNome()
                );

                tabela.addCell(
                        produto.getCategoria()
                );

                tabela.addCell(
                        String.valueOf(produto.getPreco())
                );

                tabela.addCell(
                        String.valueOf(produto.getQuantidade())
                );

                tabela.addCell(
                        produto.getStatus()
                );
            }

            document.add(tabela);

            document.close();

            System.out.println(
                    "PDF gerado com sucesso!"
            );

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}