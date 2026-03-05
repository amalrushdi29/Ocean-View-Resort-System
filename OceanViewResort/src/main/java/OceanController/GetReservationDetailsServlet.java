package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;
import OceanDAO.ReservationDao;
import OceanDAO.GuestDao;
import OceanDAO.RoomDao;
import OceanModel.ReservationBean;
import OceanModel.GuestBean;
import OceanModel.RoomBean;

/**
 * Servlet to get detailed reservation information
 */
@WebServlet("/GetReservationDetails")
public class GetReservationDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ReservationDao reservationDao;
    private GuestDao guestDao;
    private RoomDao roomDao;
    
    public GetReservationDetailsServlet() {
        super();
        reservationDao = new ReservationDao();
        guestDao = new GuestDao();
        roomDao = new RoomDao();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        int reservationId = Integer.parseInt(request.getParameter("reservationId"));
        
        JSONObject jsonResponse = new JSONObject();
        
        try {
            // Get reservation from DAO
            ReservationBean reservation = reservationDao.getReservationById(reservationId);
            
            if (reservation != null) {
                // Get guest and room details
                GuestBean guest = guestDao.getGuestById(reservation.getGuestId());
                RoomBean room = roomDao.getRoomById(reservation.getRoomId());
                
                JSONObject reservationJson = new JSONObject();
                reservationJson.put("reservation_id", reservation.getReservationId());
                reservationJson.put("reservation_no", reservation.getReservationNo());
                reservationJson.put("reservation_date", reservation.getReservationDate());
                reservationJson.put("check_in_date", reservation.getCheckInDate());
                reservationJson.put("check_out_date", reservation.getCheckOutDate());
                reservationJson.put("reservation_status", reservation.getReservationStatus());
                reservationJson.put("guest_id", reservation.getGuestId());
                reservationJson.put("room_id", reservation.getRoomId());
                
                if (guest != null) {
                    reservationJson.put("guest_name", guest.getName());
                    reservationJson.put("contact_no", guest.getContactNo());
                    reservationJson.put("email", guest.getEmail());
                }
                
                if (room != null) {
                    reservationJson.put("room_no", room.getRoomNo());
                    reservationJson.put("room_type", room.getRoomType());
                    reservationJson.put("rate_per_night", room.getRatePerNight());
                    reservationJson.put("floor_no", room.getFloorNo());
                    reservationJson.put("view", room.getView());
                }
                
                jsonResponse.put("success", true);
                jsonResponse.put("reservation", reservationJson);
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Reservation not found");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error retrieving reservation: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}