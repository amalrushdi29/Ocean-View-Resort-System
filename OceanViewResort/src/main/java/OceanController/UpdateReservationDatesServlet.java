package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import org.json.JSONObject;
import OceanDAO.ReservationDao;

/**
 * Servlet to update reservation dates
 * Allows extending stay even after check-in
 */
@WebServlet("/UpdateReservationDates")
public class UpdateReservationDatesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ReservationDao reservationDao;
    
    public UpdateReservationDatesServlet() {
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
            String newCheckIn = jsonRequest.getString("checkInDate");
            String newCheckOut = jsonRequest.getString("checkOutDate");
            int roomId = jsonRequest.getInt("roomId");
            String currentStatus = jsonRequest.optString("reservationStatus", "Confirmed");
            
            // Get old dates for comparison
            String oldCheckIn = jsonRequest.optString("oldCheckIn", newCheckIn);
            String oldCheckOut = jsonRequest.optString("oldCheckOut", newCheckOut);
            
            // LOGIC: If guest is already checked in
            if ("CheckedIn".equals(currentStatus)) {
                // Cannot change check-in date after check-in
                if (!newCheckIn.equals(oldCheckIn)) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Cannot change check-in date. Guest is already checked in. You can only extend the check-out date.");
                    out.print(jsonResponse.toString());
                    out.flush();
                    return;
                }
                
                // Allow check-out date extension/change
                // Check if room is available for new check-out date
                int result = reservationDao.updateReservationDates(reservationId, newCheckIn, newCheckOut, roomId);
                
                if (result == 1) {
                    jsonResponse.put("success", true);
                    jsonResponse.put("message", "Check-out date updated successfully. Guest stay extended!");
                } else if (result == 0) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Room is already booked for the selected dates. Please check out from the current room and book a new room.");
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Failed to update reservation dates.");
                }
            } 
            // LOGIC: If reservation is still Confirmed (not checked in yet)
            else {
                // Can change both check-in and check-out dates
                int result = reservationDao.updateReservationDates(reservationId, newCheckIn, newCheckOut, roomId);
                
                if (result == 1) {
                    jsonResponse.put("success", true);
                    jsonResponse.put("message", "Reservation dates updated successfully!");
                } else if (result == 0) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Room is already booked for the selected dates. Please choose different dates or a different room.");
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Failed to update reservation dates.");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error updating reservation: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}