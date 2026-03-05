package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import OceanDAO.DBConnection;

@WebServlet("/UpdateGuest")
public class UpdateGuestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // Check if user is logged in
        if (session == null || session.getAttribute("loggedin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Get form parameters
            int guestId = Integer.parseInt(request.getParameter("guestId"));
            String contactNo = request.getParameter("contactNo").trim();
            String email = request.getParameter("email").trim();
            String address = request.getParameter("address").trim();
            
            // Remove leading zero if contact number starts with 0 (for Sri Lankan numbers)
            if (contactNo.startsWith("0") && contactNo.length() == 10) {
                contactNo = contactNo.substring(1); // Remove first character (0)
            }
            
            conn = DBConnection.getConnection();
            
            // Check for duplicates (excluding current guest)
            String checkSql = "SELECT " +
                             "SUM(CASE WHEN contact_no = ? AND guest_id != ? THEN 1 ELSE 0 END) as contact_exists, " +
                             "SUM(CASE WHEN email = ? AND guest_id != ? THEN 1 ELSE 0 END) as email_exists " +
                             "FROM guests";
            
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, contactNo);
            pstmt.setInt(2, guestId);
            pstmt.setString(3, email);
            pstmt.setInt(4, guestId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int contactExists = rs.getInt("contact_exists");
                int emailExists = rs.getInt("email_exists");
                
                StringBuilder errorMsg = new StringBuilder();
                
                if (contactExists > 0) {
                    errorMsg.append("Contact number already exists for another guest. ");
                }
                if (emailExists > 0) {
                    errorMsg.append("Email address already exists for another guest. ");
                }
                
                if (errorMsg.length() > 0) {
                    session.setAttribute("errorMessage", errorMsg.toString().trim());
                    rs.close();
                    pstmt.close();
                    conn.close();
                    response.sendRedirect("guests.jsp");
                    return;
                }
            }
            
            rs.close();
            pstmt.close();
            
            // Update guest in database
            String sql = "UPDATE guests SET contact_no = ?, email = ?, address = ? WHERE guest_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, contactNo);
            pstmt.setString(2, email);
            pstmt.setString(3, address);
            pstmt.setInt(4, guestId);
            
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                session.setAttribute("successMessage", "Guest information updated successfully!");
            } else {
                session.setAttribute("errorMessage", "Failed to update guest information.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error updating guest: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        
        response.sendRedirect("guests.jsp");
    }
}