package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.time.LocalDate;
import org.json.JSONObject;
import OceanDAO.ReservationDao;
import OceanModel.ReservationBean;

/**
 * Servlet to check out a guest
 * Validates checkout date before proceeding to billing
 */
@WebServlet("/CheckOutGuest")
public class CheckOutGuestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ReservationDao reservationDao;
    
    public CheckOutGuestServlet() {
        super();
        reservationDao = new ReservationDao();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // Read JSON data
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        
        JSONObject jsonRequest = new JSONObject(sb.toString());
        JSONObject jsonResponse = new JSONObject();
        
        try {
            int reservationId = jsonRequest.getInt("reservationId");
            int roomId = jsonRequest.getInt("roomId");
            
            // Get reservation details to check scheduled checkout date
            ReservationBean reservation = reservationDao.getReservationById(reservationId);
            
            if (reservation == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Reservation not found.");
                out.print(jsonResponse.toString());
                out.flush();
                return;
            }
            
            // Get today's date
            LocalDate today = LocalDate.now();
            LocalDate scheduledCheckout = LocalDate.parse(reservation.getCheckOutDate());
            
            // VALIDATION: Check if early or late checkout
            if (today.isBefore(scheduledCheckout)) {
                // EARLY CHECKOUT
                jsonResponse.put("success", false);
                jsonResponse.put("action", "update_required");
                jsonResponse.put("message", "Guest is checking out EARLY (scheduled: " + scheduledCheckout + ", today: " + today + "). Please update the check-out date to today before proceeding to billing.");
                
            } else if (today.isAfter(scheduledCheckout)) {
                // LATE CHECKOUT
                jsonResponse.put("success", false);
                jsonResponse.put("action", "update_required");
                jsonResponse.put("message", "Guest is checking out LATE (scheduled: " + scheduledCheckout + ", today: " + today + "). Please update the check-out date to today before proceeding to billing.");
                
            } else {
                // ON-TIME CHECKOUT - Proceed
                boolean success = reservationDao.checkOutGuest(reservationId, roomId);
                
                if (success) {
                    jsonResponse.put("success", true);
                    jsonResponse.put("action", "proceed_to_billing");
                    jsonResponse.put("message", "Guest checked out successfully. Proceeding to billing...");
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Unable to check out. Guest may not be checked in.");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error checking out guest: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}