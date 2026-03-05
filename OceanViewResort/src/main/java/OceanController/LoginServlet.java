package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import OceanDAO.AdminDao;
import OceanDAO.StaffDao;
import OceanModel.AdminBean;
import OceanModel.StaffBean;

/**
 * Servlet for handling login (both Admin and Staff)
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private AdminDao adminDao;
    private StaffDao staffDao;

    public LoginServlet() {
        super();
        adminDao = new AdminDao();
        staffDao = new StaffDao();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        boolean loginSuccess = false;
        boolean isAdmin = false;

        try {
            // Step 1: Try to authenticate as Admin
            AdminBean admin = adminDao.authenticateAdmin(username, password);
            
            if (admin != null) {
                // Admin login successful
                isAdmin = true;
                loginSuccess = true;
                
                // Create session and store admin info
                HttpSession session = request.getSession();
                session.setAttribute("admin_id", admin.getAdminId());
                session.setAttribute("username", admin.getUsername());
                session.setAttribute("full_name", admin.getFullName());
                session.setAttribute("display_name", admin.getDisplayName());
                session.setAttribute("contact_no", admin.getContactNo());
                session.setAttribute("loggedin", true);
                session.setAttribute("user_role", "admin");
                
            } else {
                // Step 2: Try to authenticate as Staff
                StaffBean staff = staffDao.authenticateStaff(username, password);
                
                if (staff != null) {
                    // Staff login successful
                    loginSuccess = true;
                    
                    // Create session and store staff info
                    HttpSession session = request.getSession();
                    session.setAttribute("staff_id", staff.getStaffId());
                    session.setAttribute("username", staff.getUsername());
                    session.setAttribute("full_name", staff.getFullName());
                    session.setAttribute("display_name", staff.getDisplayName());
                    session.setAttribute("contact_no", staff.getContactNo());
                    session.setAttribute("loggedin", true);
                    session.setAttribute("user_role", "staff");
                }
            }

            // Step 3: Redirect based on role
            if (loginSuccess) {
                if (isAdmin) {
                    response.sendRedirect("admindashboard.jsp");
                } else {
                    response.sendRedirect("home.jsp");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "System error. Please try again later.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}