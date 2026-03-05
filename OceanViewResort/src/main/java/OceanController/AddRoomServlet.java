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
 * Servlet implementation class AddRooms
 * Follows MVC pattern - uses RoomDao for database operations
 */
@WebServlet("/AddRooms")
public class AddRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private RoomDao roomDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRoomServlet() {
        super();
        this.roomDao = new RoomDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect GET requests to the rooms page
        response.sendRedirect("rooms.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String roomNumber = request.getParameter("roomNumber");
        String roomType = request.getParameter("roomType");
        String ratePerNightStr = request.getParameter("ratePerNight");
        String maxCapacityStr = request.getParameter("maxCapacity");
        String floorNumberStr = request.getParameter("floorNumber");
        String view = request.getParameter("view");
        
        try {
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
            
            // Check for duplicate room number
            if (roomDao.isRoomNumberExists(roomNumber, 0)) {
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
            
            // Create RoomBean object
            RoomBean room = new RoomBean();
            room.setRoomNo(roomNumber);
            room.setRoomType(roomType);
            room.setRatePerNight(ratePerNightStr);
            room.setMaxCapacity(maxCapacityStr);
            room.setFloorNo(floorNumberStr);
            room.setView(view);
            
            // Use DAO to add room
            boolean success = roomDao.addRoom(room);
            
            if (success) {
                // Success - redirect back to rooms page with success message
                request.getSession().setAttribute("successMessage", "Room added successfully!");
                response.sendRedirect("rooms.jsp");
            } else {
                // Failed to insert
                request.getSession().setAttribute("errorMessage", "Failed to add room. Please try again.");
                response.sendRedirect("rooms.jsp");
            }
            
        } catch (Exception e) {
            // Handle any unexpected errors
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            response.sendRedirect("rooms.jsp");
        }
    }
}