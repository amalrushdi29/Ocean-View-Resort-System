package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import OceanDAO.StaffDao;
import OceanModel.StaffBean;

/**
 * Servlet for loading header with staff information
 */
@WebServlet("/header")
public class HeaderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private StaffDao staffDao;
    
    public HeaderServlet() {
        super();
        staffDao = new StaffDao();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session != null && session.getAttribute("loggedin") != null && 
            (Boolean) session.getAttribute("loggedin")) {
            
            // Get display name from session (already processed in login)
            String displayName = (String) session.getAttribute("display_name");
            
            // If display_name is not set, get it from StaffBean
            if (displayName == null || displayName.isEmpty()) {
                Integer staffId = (Integer) session.getAttribute("staff_id");
                
                if (staffId != null) {
                    // Fetch staff details from database using DAO
                    StaffBean staff = staffDao.getStaffById(staffId);
                    
                    if (staff != null) {
                        // Use StaffBean's helper method to get display name
                        displayName = staff.getDisplayName();
                        
                        // Update session with all staff info
                        session.setAttribute("display_name", displayName);
                        session.setAttribute("full_name", staff.getFullName());
                        session.setAttribute("username", staff.getUsername());
                        session.setAttribute("contact_no", staff.getContactNo());
                    } else {
                        displayName = "Staff";
                    }
                } else {
                    displayName = "Staff";
                }
            }
            
            // Set attributes for the header
            request.setAttribute("displayName", displayName);
            request.setAttribute("pageTitle", "Dashboard"); // Can be changed per page
            
            // Forward to header JSP
            request.getRequestDispatcher("/header.jsp").forward(request, response);
        } else {
            // If not logged in, redirect to login
            response.sendRedirect("login.jsp");
        }
    }
}