package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import OceanDAO.ReservationDao;
import OceanDAO.GuestDao;
import OceanDAO.RoomDao;
import OceanModel.ReservationBean;
import OceanModel.GuestBean;
import OceanModel.RoomBean;

/**
 * Servlet to get all reservations
 */
@WebServlet("/GetAllReservations")
public class GetAllReservationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ReservationDao reservationDao;
    private GuestDao guestDao;
    private RoomDao roomDao;
    
    public GetAllReservationsServlet() {
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
        
        JSONObject jsonResponse = new JSONObject();
        JSONArray reservationsArray = new JSONArray();
        
        try {
            // Get all reservations from DAO
            List<ReservationBean> reservationList = reservationDao.getAllReservations();
            
            // Convert to JSON with guest and room info
            for (ReservationBean reservation : reservationList) {
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
                reservationJson.put("guest_name", guest != null ? guest.getName() : "Unknown");
                reservationJson.put("room_no", room != null ? room.getRoomNo() : "Unknown");
                
                reservationsArray.put(reservationJson);
            }
            
            if (reservationsArray.length() > 0) {
                jsonResponse.put("success", true);
                jsonResponse.put("reservations", reservationsArray);
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "No reservations found");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error retrieving reservations: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}