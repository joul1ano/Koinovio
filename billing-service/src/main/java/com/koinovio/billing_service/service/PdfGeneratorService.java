package com.koinovio.billing_service.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.koinovio.billing_service.model.Bill;
import com.koinovio.billing_service.model.BillItem;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {

    public byte[] generateBillPdf(Bill bill) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Title
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        document.add(new Paragraph("Your Koinovio bill - " + bill.getMonth() + "/" + bill.getYear(), titleFont));
        document.add(new Paragraph(" "));

        // Building info
        document.add(new Paragraph("Building: " + bill.getBuildingAddress()));
        document.add(new Paragraph("Apartment Floor: " + bill.getFloor()));
        document.add(new Paragraph("Tenant: " + bill.getTenantName()));
        document.add(new Paragraph(" "));

        // Bill items table
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.addCell("Description");
        table.addCell("Amount");

        for (BillItem item : bill.getBillItems()) {
            table.addCell(item.getDescription());
            table.addCell("€" + item.getAmount());
        }

        document.add(table);
        document.add(new Paragraph(" "));

        // Total
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        document.add(new Paragraph("Total: €" + bill.getTotalAmount(), boldFont));

        document.close();

        return outputStream.toByteArray();
    }
}
