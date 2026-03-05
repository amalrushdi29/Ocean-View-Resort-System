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
import OceanDAO.GuestDao;
import OceanModel.GuestBean;

/**
 * Servlet for searching guests
 * This is the CONTROLLER in MVC - it handles requests and coordinates between View (JSP) and Model (DAO/Bean)
 */
@WebServlet("/SearchGuest")
public class SearchGuestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // DAO instance - handles all database operations
    private GuestDao guestDao;
    
    /**
     * Constructor - initialize DAO
     */
    public SearchGuestServlet() {
        super();
        guestDao = new GuestDao();
    }
    
    /**
     * Handle POST requests
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // Get search term from request
        String searchTerm = request.getParameter("searchTerm");
        
        // Create JSON response object
        JSONObject jsonResponse = new JSONObject();
        JSONArray guestsArray = new JSONArray();
        
        try {
            // Call DAO method to search guests
            List<GuestBean> guestList = guestDao.searchGuests(searchTerm);
            
            // Convert GuestBean objects to JSON
            for (GuestBean guest : guestList) {
                JSONObject guestJson = new JSONObject();
                guestJson.put("guest_id", guest.getGuestId());
                guestJson.put("name", guest.getName());
                guestJson.put("address", guest.getAddress());
                guestJson.put("contact_no", guest.getContactNo());
                guestJson.put("email", guest.getEmail());
                guestJson.put("nic_ppt", guest.getNicPpt());
                guestsArray.put(guestJson);
            }
            
            // Build response
            if (guestsArray.length() > 0) {
                jsonResponse.put("success", true);
                jsonResponse.put("guests", guestsArray);
                jsonResponse.put("message", guestsArray.length() + " guest(s) found");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "No guests found matching your search");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error searching guests: " + e.getMessage());
        }
        
        // Send JSON response
        out.print(jsonResponse.toString());
        out.flush();
    }
    
    /**
     * Handle GET requests - redirect to POST
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
}