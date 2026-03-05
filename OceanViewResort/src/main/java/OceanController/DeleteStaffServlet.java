package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;
import OceanDAO.StaffDao;

/**
 * Servlet to delete a staff member
 * PERMISSION: Admin only
 */
@WebServlet("/DeleteStaff")
public class DeleteStaffServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private StaffDao staffDao;
    
    public DeleteStaffServlet() {
        super();
        staffDao = new StaffDao();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        JSONObject jsonResponse = new JSONObject();
        
        try {
            HttpSession session = request.getSession(false);
            
            // PERMISSION CHECK: Admin only
            if (session == null || !"admin".equals(session.getAttribute("user_role"))) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Access denied. Admin only.");
                out.print(jsonResponse.toString());
                out.flush();
                return;
            }
            
            String staffIdStr = request.getParameter("staffId");
            
            if (staffIdStr == null || staffIdStr.isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Staff ID is required.");
                out.print(jsonResponse.toString());
                out.flush();
                return;
            }
            
            int staffId = Integer.parseInt(staffIdStr);
            
            // SAFETY CHECK: Prevent admin from deleting themselves
            Integer adminId = (Integer) session.getAttribute("admin_id");
            if (adminId != null && adminId == staffId) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Cannot delete your own account.");
                out.print(jsonResponse.toString());
                out.flush();
                return;
            }
            
            // Delete staff using DAO
            boolean isDeleted = staffDao.deleteStaff(staffId);
            
            if (isDeleted) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Staff member removed successfully!");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to remove staff member. Please try again.");
            }
            
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Invalid staff ID.");
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error removing staff: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}