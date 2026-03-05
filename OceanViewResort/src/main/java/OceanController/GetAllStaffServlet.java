package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import OceanDAO.StaffDao;
import OceanModel.StaffBean;

/**
 * Servlet to get all staff members
 * PERMISSION: Admin only
 */
@WebServlet("/GetAllStaff")
public class GetAllStaffServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private StaffDao staffDao;
    
    public GetAllStaffServlet() {
        super();
        staffDao = new StaffDao();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
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
            
            // Get all staff using DAO
            List<StaffBean> staffList = staffDao.getAllStaff();
            
            // Build JSON array
            JSONArray staffArray = new JSONArray();
            
            for (StaffBean staff : staffList) {
                JSONObject staffObj = new JSONObject();
                staffObj.put("staff_id", staff.getStaffId());
                staffObj.put("username", staff.getUsername());
                staffObj.put("full_name", staff.getFullName());
                staffObj.put("contact_no", staff.getContactNo());
                staffArray.put(staffObj);
            }
            
            jsonResponse.put("success", true);
            jsonResponse.put("staff", staffArray);
            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error loading staff: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}