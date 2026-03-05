package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.json.JSONObject;
import OceanDAO.ReservationDao;
import OceanDAO.GuestDao;
import OceanDAO.RoomDao;
import OceanModel.ReservationBean;
import OceanModel.GuestBean;
import OceanModel.RoomBean;

/**
 * Servlet to search reservation for billing
 * Searches by reservation number or guest name
 */
@WebServlet("/SearchReservationForBilling")
public class SearchReservationForBillingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ReservationDao reservationDao;
    private GuestDao guestDao;
    private RoomDao roomDao;
    
    public SearchReservationForBillingServlet() {
        super();
        reservationDao = new ReservationDao();
        guestDao = new GuestDao();
        roomDao = new RoomDao();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        String searchType = request.getParameter("searchType");
        String searchValue = request.getParameter("searchValue");
        
        JSONObject jsonResponse = new JSONObject();
        
        try {
            ReservationBean reservation = null;
            
            // Search by reservation number or guest name
            if ("reservationNo".equals(searchType)) {
                // Search by reservation number
                reservation = reservationDao.getReservationByNumber(searchValue);
            } else if ("guestName".equals(searchType)) {
                // Search by guest name (get first matching reservation)
                reservation = reservationDao.getReservationByGuestName(searchValue);
            }
            
            if (reservation == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "No reservation found. Please check the " + 
                               (searchType.equals("reservationNo") ? "reservation number" : "guest name") + 
                               " and try again.");
                out.print(jsonResponse.toString());
                out.flush();
                return;
            }
            
            // Check if reservation is checked out
            if (!"CheckedOut".equals(reservation.getReservationStatus())) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Cannot generate bill. Guest has not checked out yet. Current status: " + 
                               reservation.getReservationStatus());
                out.print(jsonResponse.toString());
                out.flush();
                return;
            }
            
            // Get guest details
            GuestBean guest = guestDao.getGuestById(reservation.getGuestId());
            
            // Get room details
            RoomBean room = roomDao.getRoomById(reservation.getRoomId());
            
            // Calculate number of nights
            LocalDate checkIn = LocalDate.parse(reservation.getCheckInDate());
            LocalDate checkOut = LocalDate.parse(reservation.getCheckOutDate());
            long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
            
            // Calculate room charges
            double ratePerNight = Double.parseDouble(room.getRatePerNight());
            double roomCharges = nights * ratePerNight;
            
            // Build response with all details
            JSONObject reservationData = new JSONObject();
            reservationData.put("reservationId", reservation.getReservationId());
            reservationData.put("reservationNo", reservation.getReservationNo());
            reservationData.put("guestName", guest.getName());
            reservationData.put("roomNo", room.getRoomNo());
            reservationData.put("roomType", room.getRoomType());
            reservationData.put("checkInDate", reservation.getCheckInDate());
            reservationData.put("checkOutDate", reservation.getCheckOutDate());
            reservationData.put("nights", nights);
            reservationData.put("ratePerNight", ratePerNight);
            reservationData.put("roomCharges", roomCharges);
            reservationData.put("totalAmount", roomCharges);
            
            jsonResponse.put("success", true);
            jsonResponse.put("reservation", reservationData);
            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error searching reservation: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}