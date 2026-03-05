package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import OceanDAO.StaffDao;
import OceanModel.StaffBean;

/**
 * Servlet to handle adding a new staff member.
 * Mapped to /AddStaff (matches addstaff.jsp form action="AddStaff")
 */
@WebServlet("/AddStaff")
public class AddStaffServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * GET: Redirect to the Add Staff page.
     * Handles direct URL access gracefully.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/addstaff.jsp");
    }

    /**
     * POST: Process the Add Staff form submission.
     * 1. Read and trim form parameters
     * 2. Server-side validation
     * 3. Check for duplicate username
     * 4. Insert into DB via StaffDao
     * 5. Redirect with success or error message
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ── 1. Read form parameters ──────────────────────────────────────────
        String fullName  = trim(request.getParameter("full_name"));
        String contactNo = trim(request.getParameter("contact_no"));
        String username  = trim(request.getParameter("username"));
        String password  = request.getParameter("password"); // do NOT trim passwords

        // ── 2. Server-side validation ────────────────────────────────────────
        String validationError = validate(fullName, contactNo, username, password);

        if (validationError != null) {
            // Send back to the form with an error message
            request.setAttribute("errorMessage", validationError);
            request.setAttribute("fullName",  fullName);
            request.setAttribute("contactNo", contactNo);
            request.setAttribute("username",  username);
            request.getRequestDispatcher("/addstaff.jsp").forward(request, response);
            return;
        }

        // ── 3. Check for duplicate username ──────────────────────────────────
        StaffDao staffDao = new StaffDao();

        if (staffDao.isUsernameTaken(username)) {
            request.setAttribute("errorMessage",
                    "Username '" + username + "' is already taken. Please choose a different username.");
            request.setAttribute("fullName",  fullName);
            request.setAttribute("contactNo", contactNo);
            request.setAttribute("username",  username);
            request.getRequestDispatcher("/addstaff.jsp").forward(request, response);
            return;
        }

        // ── 4. Build StaffBean and insert ────────────────────────────────────
        StaffBean newStaff = new StaffBean();
        newStaff.setFullName(fullName);
        newStaff.setContactNo(contactNo);
        newStaff.setUsername(username);
        newStaff.setPassword(password);   // plain-text for now; hash here if needed

        boolean success = staffDao.addStaff(newStaff);

        // ── 5. Redirect with result ───────────────────────────────────────────
        if (success) {
            // PRG pattern: redirect so a browser refresh won't resubmit the form
            response.sendRedirect(request.getContextPath()
                    + "/addstaff.jsp?success=true&name="
                    + java.net.URLEncoder.encode(fullName, "UTF-8"));
        } else {
            request.setAttribute("errorMessage",
                    "A database error occurred while adding the staff member. Please try again.");
            request.setAttribute("fullName",  fullName);
            request.setAttribute("contactNo", contactNo);
            request.setAttribute("username",  username);
            request.getRequestDispatcher("/addstaff.jsp").forward(request, response);
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * Null-safe trim helper.
     */
    private String trim(String value) {
        return (value != null) ? value.trim() : "";
    }

    /**
     * Server-side validation.
     * Returns an error message string if invalid, or null if all fields are OK.
     */
    private String validate(String fullName, String contactNo,
                             String username,  String password) {

        if (fullName.isEmpty()) {
            return "Full name is required.";
        }
        if (fullName.length() < 3 || fullName.length() > 100) {
            return "Full name must be between 3 and 100 characters.";
        }

        if (contactNo.isEmpty()) {
            return "Contact number is required.";
        }
        if (!contactNo.matches("[0-9+\\-\\s]{7,15}")) {
            return "Contact number must be 7–15 characters (digits, +, -, spaces only).";
        }

        if (username.isEmpty()) {
            return "Username is required.";
        }
        if (!username.matches("[a-zA-Z0-9_]{3,20}")) {
            return "Username must be 3–20 characters (letters, numbers, underscores only).";
        }

        if (password == null || password.isEmpty()) {
            return "Password is required.";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters.";
        }

        return null; // all valid
    }
}