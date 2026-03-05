package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import OceanDAO.RoomDao;
import OceanModel.RoomBean;

/**
 * Servlet for editing room details
 * Follows MVC pattern - uses RoomDao for database operations
 */
@WebServlet("/EditRoom")
public class EditRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private RoomDao roomDao;
       
    public EditRoomServlet() {
        super();
        this.roomDao = new RoomDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String roomIdStr = request.getParameter("roomId");
        String roomNumber = request.getParameter("roomNumber");
        String roomType = request.getParameter("roomType");
        String ratePerNightStr = request.getParameter("ratePerNight");
        String maxCapacityStr = request.getParameter("maxCapacity");
        String floorNumberStr = request.getParameter("floorNumber");
        String view = request.getParameter("view");
        
        try {
            // Validate room ID
            if (roomIdStr == null || roomIdStr.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Room ID is required.");
                response.sendRedirect("rooms.jsp");
                return;
            }
            
            int roomId = Integer.parseInt(roomIdStr);
            
            // Validate required fields
            if (roomNumber == null || roomNumber.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Room number is required.");
                response.sendRedirect("rooms.jsp");
                return;
            }
            
            if (roomType == null || roomType.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Room type is required.");
                response.sendRedirect("rooms.jsp");
                return;
            }
            
            if (ratePerNightStr == null || ratePerNightStr.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Rate per night is required.");
                response.sendRedirect("rooms.jsp");
                return;
            }
            
            // Check for duplicate room number (excluding current room)
            if (roomDao.isRoomNumberExists(roomNumber, roomId)) {
                request.getSession().setAttribute("errorMessage", "Room number already exists.");
                response.sendRedirect("rooms.jsp");
                return;
            }
            
            // Validate numeric format for rate
            try {
                Double.parseDouble(ratePerNightStr);
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("errorMessage", "Invalid rate per night format.");
                response.sendRedirect("rooms.jsp");
                return;
            }
            
            // Validate numeric format for optional fields
            if (maxCapacityStr != null && !maxCapacityStr.trim().isEmpty()) {
                try {
                    Integer.parseInt(maxCapacityStr);
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid max capacity format.");
                    response.sendRedirect("rooms.jsp");
                    return;
                }
            }
            
            if (floorNumberStr != null && !floorNumberStr.trim().isEmpty()) {
                try {
                    Integer.parseInt(floorNumberStr);
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid floor number format.");
                    response.sendRedirect("rooms.jsp");
                    return;
                }
            }
            
            // Create RoomBean object with updated data
            RoomBean room = new RoomBean();
            room.setRoomId(roomId);
            room.setRoomNo(roomNumber);
            room.setRoomType(roomType);
            room.setRatePerNight(ratePerNightStr);
            room.setMaxCapacity(maxCapacityStr);
            room.setFloorNo(floorNumberStr);
            room.setView(view);
            
            // Use DAO to update room
            boolean success = roomDao.updateRoom(room);
            
            if (success) {
                request.getSession().setAttribute("successMessage", "Room updated successfully!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update room.");
            }
            
            response.sendRedirect("rooms.jsp");
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Invalid number format.");
            response.sendRedirect("rooms.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Error updating room: " + e.getMessage());
            response.sendRedirect("rooms.jsp");
        }
    }
}