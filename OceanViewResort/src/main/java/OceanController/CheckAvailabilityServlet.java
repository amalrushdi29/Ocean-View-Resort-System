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
import OceanDAO.RoomDao;
import OceanModel.RoomBean;

/**
 * Servlet for checking room availability
 * Controller that handles availability check requests
 */
@WebServlet("/CheckAvailability")
public class CheckAvailabilityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // DAO instance
    private RoomDao roomDao;
    
    /**
     * Constructor - initialize DAO
     */
    public CheckAvailabilityServlet() {
        super();
        roomDao = new RoomDao();
    }
    
    /**
     * Handle POST requests
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // Get parameters from request
        String checkInDate = request.getParameter("checkInDate");
        String checkOutDate = request.getParameter("checkOutDate");
        String roomType = request.getParameter("roomType");
        
        JSONObject jsonResponse = new JSONObject();
        JSONArray roomsArray = new JSONArray();
        
        try {
            // Call DAO method to find available rooms
            List<RoomBean> roomList = roomDao.findAvailableRooms(checkInDate, checkOutDate, roomType);
            
            // Convert RoomBean objects to JSON
            for (RoomBean room : roomList) {
                JSONObject roomJson = new JSONObject();
                roomJson.put("room_id", room.getRoomId());
                roomJson.put("room_no", room.getRoomNo());
                roomJson.put("room_type", room.getRoomType());
                roomJson.put("rate_per_night", room.getRatePerNight());
                roomJson.put("max_capacity", room.getMaxCapacity());
                roomJson.put("floor_no", room.getFloorNo());
                roomJson.put("view", room.getView());
                roomJson.put("status", room.getStatus());
                roomsArray.put(roomJson);
            }
            
            // Build response
            if (roomsArray.length() > 0) {
                jsonResponse.put("success", true);
                jsonResponse.put("rooms", roomsArray);
                jsonResponse.put("message", roomsArray.length() + " room(s) available");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "No rooms available for the selected dates and type");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error checking availability: " + e.getMessage());
        }
        
        // Send JSON response
        out.print(jsonResponse.toString());
        out.flush();
    }
    
    /**
     * Handle GET requests
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
}