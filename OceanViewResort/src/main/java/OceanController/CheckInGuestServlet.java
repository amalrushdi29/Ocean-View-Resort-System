package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import org.json.JSONObject;
import OceanDAO.ReservationDao;

/**
 * Servlet to check in a guest
 */
@WebServlet("/CheckInGuest")
public class CheckInGuestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ReservationDao reservationDao;
    
    public CheckInGuestServlet() {
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
            
            // Call DAO method to check in guest
            boolean success = reservationDao.checkInGuest(reservationId, roomId);
            
            if (success) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Guest checked in successfully");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Unable to check in. Reservation may not be in Confirmed status.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error checking in guest: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}