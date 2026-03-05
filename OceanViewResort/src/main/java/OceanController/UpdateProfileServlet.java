package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import OceanDAO.StaffDao;

/**
 * Servlet for updating staff profile
 * Handles password and contact number updates
 */
@WebServlet("/UpdateProfile")
public class UpdateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private StaffDao staffDao;
    
    public UpdateProfileServlet() {
        super();
        staffDao = new StaffDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("profile.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // Check if user is logged in
        if (session == null || session.getAttribute("loggedin") == null || 
            !(Boolean)session.getAttribute("loggedin")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get the action parameter
        String action = request.getParameter("action");
        
        if ("updatePassword".equals(action)) {
            handlePasswordUpdate(request, response, session);
        } else if ("updateContact".equals(action)) {
            handleContactUpdate(request, response, session);
        } else {
            request.setAttribute("errorMessage", "Invalid action specified.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }

    /**
     * Handle password update request
     */
    private void handlePasswordUpdate(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
            throws ServletException, IOException {
        
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        Integer staffId = (Integer) session.getAttribute("staff_id");

        // Server-side validation
        if (currentPassword == null || newPassword == null || confirmPassword == null ||
            currentPassword.trim().isEmpty() || newPassword.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All password fields are required.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        // Check if new password matches confirmation
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "New password and confirmation password do not match.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        // Check if new password is different from current password
        if (currentPassword.equals(newPassword)) {
            request.setAttribute("errorMessage", "New password must be different from the old password.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        // Check minimum password length
        if (newPassword.length() < 6) {
            request.setAttribute("errorMessage", "Password must be at least 6 characters long.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        // Verify current password using DAO
        boolean isPasswordValid = staffDao.verifyPassword(staffId, currentPassword);
        
        if (!isPasswordValid) {
            request.setAttribute("errorMessage", "Current password is incorrect. Please try again.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        // Update password using DAO
        boolean isUpdated = staffDao.updatePassword(staffId, newPassword);
        
        if (isUpdated) {
            request.setAttribute("successMessage", "Password changed successfully.");
        } else {
            request.setAttribute("errorMessage", "Failed to update password. Please try again.");
        }
        
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    /**
     * Handle contact number update request
     */
    private void handleContactUpdate(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
            throws ServletException, IOException {
        
        String newContactNo = request.getParameter("newContactNo");
        Integer staffId = (Integer) session.getAttribute("staff_id");
        String currentContactNo = (String) session.getAttribute("contact_no");

        // Server-side validation
        if (newContactNo == null || newContactNo.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Contact number is required.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        newContactNo = newContactNo.trim();

        // Validate phone number format (10 digits)
        if (!newContactNo.matches("^[0-9]{10}$")) {
            request.setAttribute("errorMessage", "Please enter a valid 10-digit contact number.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        // Check if new contact number is same as current
        if (newContactNo.equals(currentContactNo)) {
            request.setAttribute("infoMessage", "The new contact number is the same as your current contact number. No changes were made.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        // Update contact number using DAO
        boolean isUpdated = staffDao.updateContactNo(staffId, newContactNo);
        
        if (isUpdated) {
            // Update session with new contact number
            session.setAttribute("contact_no", newContactNo);
            request.setAttribute("successMessage", "Contact number updated successfully to " + newContactNo + ".");
        } else {
            request.setAttribute("errorMessage", "Failed to update contact number. Please try again.");
        }
        
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}