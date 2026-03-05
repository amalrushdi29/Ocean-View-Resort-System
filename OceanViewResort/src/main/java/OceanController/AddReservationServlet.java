package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import OceanModel.GuestBean;
import OceanDAO.GuestDao;

/**
 * Servlet for handling guest registration
 * REUSES the shared GuestBean and GuestDao
 */
@WebServlet("/AddReservationServlet")
public class AddReservationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // DAO instance - REUSING the shared GuestDao!
    private GuestDao guestDao;
    
    public AddReservationServlet() {
        super();
        this.guestDao = new GuestDao();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("reservation.jsp");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // STEP 1: Get form parameters
        String guestName = request.getParameter("guestName");
        String guestAddress = request.getParameter("guestAddress");
        String contactNumber = request.getParameter("contactNumber");
        String email = request.getParameter("email");
        String nicPassport = request.getParameter("nicPassport");
        
        // STEP 2: Create GuestBean object (REUSING shared Bean!)
        GuestBean guest = new GuestBean();
        guest.setName(guestName);
        guest.setAddress(guestAddress);
        guest.setContactNo(contactNumber);
        guest.setEmail(email);
        guest.setNicPpt(nicPassport);
        
        // STEP 3: Check for duplicates using GuestDao
        String duplicateError = guestDao.checkDuplicateGuest(guest);
        
        if (duplicateError != null) {
            // Duplicates found - send error back to JSP
            request.setAttribute("errorMessage", duplicateError);
            request.setAttribute("messageType", "error");
            
            // Preserve form data
            request.setAttribute("guestName", guestName);
            request.setAttribute("guestAddress", guestAddress);
            request.setAttribute("contactNumber", contactNumber);
            request.setAttribute("email", email);
            request.setAttribute("nicPassport", nicPassport);
            
            request.getRequestDispatcher("reservation.jsp").forward(request, response);
            return;
        }
        
        // STEP 4: No duplicates - insert guest using GuestDao
        boolean isInserted = guestDao.addGuest(guest);
        
        if (isInserted) {
            request.setAttribute("successMessage", "Guest registered successfully!");
            request.setAttribute("messageType", "success");
        } else {
            request.setAttribute("errorMessage", "Failed to register guest. Please try again.");
            request.setAttribute("messageType", "error");
        }
        
        // STEP 5: Forward response to JSP
        request.getRequestDispatcher("reservation.jsp").forward(request, response);
    }
}