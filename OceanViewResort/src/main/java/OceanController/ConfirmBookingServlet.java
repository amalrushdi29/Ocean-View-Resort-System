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
import OceanModel.ReservationBean;

/**
 * Servlet for confirming bookings
 * Controller that handles reservation creation
 */
@WebServlet("/ConfirmBooking")
public class ConfirmBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // DAO instance
    private ReservationDao reservationDao;
    
    /**
     * Constructor - initialize DAO
     */
    public ConfirmBookingServlet() {
        super();
        reservationDao = new ReservationDao();
    }
    
    /**
     * Handle POST requests
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // Read JSON data from request body
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        
        JSONObject jsonRequest = new JSONObject(sb.toString());
        JSONObject jsonResponse = new JSONObject();
        
        try {
            // Create ReservationBean from JSON data
            ReservationBean reservation = new ReservationBean();
            reservation.setReservationNo(jsonRequest.getString("reservationNo"));
            reservation.setGuestId(jsonRequest.getInt("guestId"));
            reservation.setRoomId(jsonRequest.getInt("roomId"));
            reservation.setCheckInDate(jsonRequest.getString("checkInDate"));
            reservation.setCheckOutDate(jsonRequest.getString("checkOutDate"));
            reservation.setReservationDate(jsonRequest.getString("reservationDate"));
            reservation.setReservationStatus(jsonRequest.getString("reservationStatus"));
            
            // Call DAO method to create reservation
            int reservationId = reservationDao.createReservation(reservation);
            
            // Build response based on result
            if (reservationId > 0) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Reservation created successfully");
                jsonResponse.put("reservationId", reservationId);
                jsonResponse.put("reservationNo", reservation.getReservationNo());
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to create reservation");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error creating reservation: " + e.getMessage());
        }
        
        // Send JSON response
        out.print(jsonResponse.toString());
        out.flush();
    }
    
    /**
     * Handle GET requests - redirect to booking page
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("booking.jsp");
    }
}