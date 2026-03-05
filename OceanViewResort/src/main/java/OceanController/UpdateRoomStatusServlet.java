package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import OceanDAO.RoomDao;

/**
 * Servlet for updating room status
 * Follows MVC pattern - uses RoomDao for database operations
 */
@WebServlet("/UpdateRoomStatus")
public class UpdateRoomStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private RoomDao roomDao;

    public UpdateRoomStatusServlet() {
        super();
        this.roomDao = new RoomDao();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String roomIdStr = request.getParameter("roomId");
        String status = request.getParameter("status");

        try {
            // Validate room ID
            if (roomIdStr == null || roomIdStr.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Room ID is required.");
                response.sendRedirect("rooms.jsp");
                return;
            }
            
            // Validate status
            if (status == null || status.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Status is required.");
                response.sendRedirect("rooms.jsp");
                return;
            }
            
            // Validate status value
            if (!isValidStatus(status)) {
                request.getSession().setAttribute("errorMessage", "Invalid status value.");
                response.sendRedirect("rooms.jsp");
                return;
            }
            
            int roomId = Integer.parseInt(roomIdStr);
            
            // Check if room is currently booked before changing status to Available
            if ("Available".equals(status)) {
                if (roomDao.isRoomBooked(roomId)) {
                    request.getSession().setAttribute("errorMessage", 
                        "Cannot set status to Available. Room has active reservations.");
                    response.sendRedirect("rooms.jsp");
                    return;
                }
            }
            
            // Use DAO to update room status
            boolean success = roomDao.updateRoomStatus(roomId, status);

            if (success) {
                request.getSession().setAttribute("successMessage", "Room status updated successfully!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update room status.");
            }

            response.sendRedirect("rooms.jsp");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Invalid room ID format.");
            response.sendRedirect("rooms.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Error updating status: " + e.getMessage());
            response.sendRedirect("rooms.jsp");
        }
    }
    
    /**
     * Validate if the status is one of the allowed values
     * @param status - the status to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidStatus(String status) {
        return "Available".equals(status) || 
               "Booked".equals(status) || 
               "Occupied".equals(status) || 
               "Maintenance".equals(status);
    }
}