package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import OceanDAO.BillingDao;
import OceanDAO.ReservationDao;
import OceanDAO.GuestDao;
import OceanDAO.RoomDao;
import OceanModel.BillingBean;
import OceanModel.ReservationBean;
import OceanModel.GuestBean;
import OceanModel.RoomBean;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Servlet to print receipt as PDF
 * Generates PDF bill/receipt for download
 */
@WebServlet("/PrintReceipt")
public class PrintReceiptServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private BillingDao billingDao;
    private ReservationDao reservationDao;
    private GuestDao guestDao;
    private RoomDao roomDao;
    
    public PrintReceiptServlet() {
        super();
        billingDao = new BillingDao();
        reservationDao = new ReservationDao();
        guestDao = new GuestDao();
        roomDao = new RoomDao();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String billIdParam = request.getParameter("billId");
        
        if (billIdParam == null || billIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bill ID is required");
            return;
        }
        
        try {
            int billId = Integer.parseInt(billIdParam);
            
            // Get billing details
            BillingBean billing = billingDao.getBillById(billId);
            
            if (billing == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found");
                return;
            }
            
            // Get reservation, guest, and room details
            ReservationBean reservation = reservationDao.getReservationById(billing.getReservationId());
            GuestBean guest = guestDao.getGuestById(reservation.getGuestId());
            RoomBean room = roomDao.getRoomById(reservation.getRoomId());
            
            // Calculate nights
            LocalDate checkIn = LocalDate.parse(reservation.getCheckInDate());
            LocalDate checkOut = LocalDate.parse(reservation.getCheckOutDate());
            long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
            
            // Set response headers for PDF download
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", 
                "attachment; filename=\"Receipt_" + billing.getBillNo() + ".pdf\"");
            
            // Create PDF document
            Document document = new Document(PageSize.A4);
            OutputStream out = response.getOutputStream();
            PdfWriter.getInstance(document, out);
            
            document.open();
            
            // Define fonts
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.DARK_GRAY);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);
            Font smallFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.GRAY);
            
         // =============================================
         // HOTEL LOGO SECTION
         // =============================================

         // Top blue header bar
         PdfPTable headerBar = new PdfPTable(1);
         headerBar.setWidthPercentage(100);
         headerBar.setSpacingAfter(0);

         PdfPCell headerBarCell = new PdfPCell();
         headerBarCell.setBackgroundColor(new BaseColor(41, 128, 185));
         headerBarCell.setBorder(Rectangle.NO_BORDER);
         headerBarCell.setPadding(20);
         headerBarCell.setHorizontalAlignment(Element.ALIGN_CENTER);

         // Hotel name
         Font hotelNameFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.WHITE);
         Paragraph hotelName = new Paragraph("OCEAN VIEW RESORT", hotelNameFont);
         hotelName.setAlignment(Element.ALIGN_CENTER);
         hotelName.setSpacingAfter(5);

         // Hotel tagline
         Font taglineFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, new BaseColor(213, 234, 248));
         Paragraph tagline = new Paragraph("~ Galle, Sri Lanka ~", taglineFont);
         tagline.setAlignment(Element.ALIGN_CENTER);
         tagline.setSpacingAfter(5);

         // Hotel contact
         Font contactFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, new BaseColor(213, 234, 248));
         Paragraph contact = new Paragraph("Tel: +94 91 222 3456  |  Email: info@oceanviewresort.lk", contactFont);
         contact.setAlignment(Element.ALIGN_CENTER);

         headerBarCell.addElement(hotelName);
         headerBarCell.addElement(tagline);
         headerBarCell.addElement(contact);
         headerBar.addCell(headerBarCell);
         document.add(headerBar);

         // Thin gold accent bar below header
         PdfPTable accentBar = new PdfPTable(1);
         accentBar.setWidthPercentage(100);
         accentBar.setSpacingAfter(20);

         PdfPCell accentCell = new PdfPCell();
         accentCell.setBackgroundColor(new BaseColor(241, 196, 15));
         accentCell.setBorder(Rectangle.NO_BORDER);
         accentCell.setFixedHeight(4);
         accentBar.addCell(accentCell);
         document.add(accentBar);

         // PAYMENT RECEIPT title
         Font receiptTitleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, new BaseColor(41, 128, 185));
         Paragraph receiptTitle = new Paragraph("PAYMENT RECEIPT", receiptTitleFont);
         receiptTitle.setAlignment(Element.ALIGN_CENTER);
         receiptTitle.setSpacingAfter(20);
         document.add(receiptTitle);
            
            
            // Bill information table
            PdfPTable billInfoTable = new PdfPTable(2);
            billInfoTable.setWidthPercentage(100);
            billInfoTable.setWidths(new float[]{1, 1});
            billInfoTable.setSpacingAfter(15);
            
            // Left column
            PdfPCell leftCell = new PdfPCell();
            leftCell.setBorder(Rectangle.NO_BORDER);
            leftCell.addElement(new Paragraph("Bill No: " + billing.getBillNo(), boldFont));
            leftCell.addElement(new Paragraph("Bill Date: " + billing.getBillDate(), normalFont));
            billInfoTable.addCell(leftCell);
            
            // Right column
            PdfPCell rightCell = new PdfPCell();
            rightCell.setBorder(Rectangle.NO_BORDER);
            rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            rightCell.addElement(new Paragraph("Reservation No: " + reservation.getReservationNo(), normalFont));
            rightCell.addElement(new Paragraph("Payment Method: " + billing.getPaymentMethod(), normalFont));
            billInfoTable.addCell(rightCell);
            
            document.add(billInfoTable);
            
            // Guest details section
            document.add(new Paragraph("Guest Details", headerFont));
            document.add(Chunk.NEWLINE);
            
            PdfPTable guestTable = new PdfPTable(2);
            guestTable.setWidthPercentage(100);
            guestTable.setSpacingAfter(15);
            
            addTableRow(guestTable, "Guest Name:", guest.getName(), normalFont, boldFont);
            addTableRow(guestTable, "Room Number:", room.getRoomNo(), normalFont, boldFont);
            addTableRow(guestTable, "Room Type:", room.getRoomType(), normalFont, boldFont);
            
            document.add(guestTable);
            
            // Stay details section
            document.add(new Paragraph("Stay Details", headerFont));
            document.add(Chunk.NEWLINE);
            
            PdfPTable stayTable = new PdfPTable(2);
            stayTable.setWidthPercentage(100);
            stayTable.setSpacingAfter(15);
            
            addTableRow(stayTable, "Check-in Date:", reservation.getCheckInDate(), normalFont, boldFont);
            addTableRow(stayTable, "Check-out Date:", reservation.getCheckOutDate(), normalFont, boldFont);
            addTableRow(stayTable, "Total Nights:", nights + " night(s)", normalFont, boldFont);
            
            document.add(stayTable);
            
            // Charges section
            document.add(new Paragraph("Charges Breakdown", headerFont));
            document.add(Chunk.NEWLINE);
            
            PdfPTable chargesTable = new PdfPTable(3);
            chargesTable.setWidthPercentage(100);
            chargesTable.setWidths(new float[]{3, 1, 1.5f});
            chargesTable.setSpacingAfter(10);
            
            // Table header
            PdfPCell headerCell1 = new PdfPCell(new Phrase("Description", boldFont));
            headerCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell1.setPadding(8);
            chargesTable.addCell(headerCell1);
            
            PdfPCell headerCell2 = new PdfPCell(new Phrase("Nights", boldFont));
            headerCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell2.setPadding(8);
            headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            chargesTable.addCell(headerCell2);
            
            PdfPCell headerCell3 = new PdfPCell(new Phrase("Amount (LKR)", boldFont));
            headerCell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell3.setPadding(8);
            headerCell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
            chargesTable.addCell(headerCell3);
            
            // Room charges row
            PdfPCell descCell = new PdfPCell(new Phrase("Room Charges @ LKR " + 
                String.format("%.2f", Double.parseDouble(room.getRatePerNight())) + " per night", normalFont));
            descCell.setPadding(8);
            descCell.setBorder(Rectangle.BOTTOM);
            chargesTable.addCell(descCell);
            
            PdfPCell nightsCell = new PdfPCell(new Phrase(String.valueOf(nights), normalFont));
            nightsCell.setPadding(8);
            nightsCell.setBorder(Rectangle.BOTTOM);
            nightsCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            chargesTable.addCell(nightsCell);
            
            PdfPCell amountCell = new PdfPCell(new Phrase(String.format("%.2f", billing.getRoomCharges()), normalFont));
            amountCell.setPadding(8);
            amountCell.setBorder(Rectangle.BOTTOM);
            amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            chargesTable.addCell(amountCell);
            
            // Total row
            PdfPCell totalLabelCell = new PdfPCell(new Phrase("TOTAL AMOUNT", boldFont));
            totalLabelCell.setPadding(8);
            totalLabelCell.setColspan(2);
            totalLabelCell.setBorder(Rectangle.NO_BORDER);
            totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            chargesTable.addCell(totalLabelCell);
            
            PdfPCell totalAmountCell = new PdfPCell(new Phrase("LKR " + String.format("%.2f", billing.getTotalAmount()), boldFont));
            totalAmountCell.setPadding(8);
            totalAmountCell.setBorder(Rectangle.TOP);
            totalAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            chargesTable.addCell(totalAmountCell);
            
            document.add(chargesTable);
            document.add(Chunk.NEWLINE);
            
            // Footer
            document.add(new Paragraph("_________________________________________________________________________"));
            document.add(Chunk.NEWLINE);
            
            Paragraph thanks = new Paragraph("Thank you for choosing Ocean View Resort!", normalFont);
            thanks.setAlignment(Element.ALIGN_CENTER);
            thanks.setSpacingAfter(20);
            document.add(thanks);
            
            document.close();
            out.flush();
            out.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error generating PDF: " + e.getMessage());
        }
    }
    
    /**
     * Helper method to add rows to PDF table
     */
    private void addTableRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(5);
        table.addCell(labelCell);
        
        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(5);
        table.addCell(valueCell);
    }
}