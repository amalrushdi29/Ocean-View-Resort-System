package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.JSONObject;
import OceanDAO.DBConnection;

/**
 * Servlet to fetch real-time dashboard statistics
 * PERMISSION: Admin only
 */
@WebServlet("/GetDashboardStats")
public class DashboardStatsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

            Connection conn = DBConnection.getConnection();

            // STAT 1: Total Staff Members
            int totalStaff = 0;
            PreparedStatement staffStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM staff"
            );
            ResultSet staffRs = staffStmt.executeQuery();
            if (staffRs.next()) {
                totalStaff = staffRs.getInt("count");
            }
            staffRs.close();
            staffStmt.close();

            // STAT 2: Active Reservations (Confirmed + CheckedIn)
            int activeReservations = 0;
            PreparedStatement resStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM reservations " +
                "WHERE reservation_status IN ('Confirmed', 'CheckedIn')"
            );
            ResultSet resRs = resStmt.executeQuery();
            if (resRs.next()) {
                activeReservations = resRs.getInt("count");
            }
            resRs.close();
            resStmt.close();

            // STAT 3: Rooms in Maintenance
            int maintenanceRooms = 0;
            PreparedStatement roomStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM rooms " +
                "WHERE status = 'Maintenance'"
            );
            ResultSet roomRs = roomStmt.executeQuery();
            if (roomRs.next()) {
                maintenanceRooms = roomRs.getInt("count");
            }
            roomRs.close();
            roomStmt.close();

         // STAT 4: Total Guests
            int totalGuests = 0;
            PreparedStatement guestStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM guests"
            );
            ResultSet guestRs = guestStmt.executeQuery();
            if (guestRs.next()) {
                totalGuests = guestRs.getInt("count");
            }
            guestRs.close();
            guestStmt.close();

            // STAT 5: Total Rooms
            int totalRooms = 0;
            PreparedStatement totalRoomStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM rooms"
            );
            ResultSet totalRoomRs = totalRoomStmt.executeQuery();
            if (totalRoomRs.next()) {
                totalRooms = totalRoomRs.getInt("count");
            }
            totalRoomRs.close();
            totalRoomStmt.close();

            // STAT 6: Total Rooms Booked
            int roomsBooked = 0;
            PreparedStatement bookedStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM rooms " +
                "WHERE status = 'Booked'"
            );
            ResultSet bookedRs = bookedStmt.executeQuery();
            if (bookedRs.next()) {
                roomsBooked = bookedRs.getInt("count");
            }
            bookedRs.close();
            bookedStmt.close();

            conn.close();

            // Build response
            jsonResponse.put("success", true);
            jsonResponse.put("totalStaff", totalStaff);
            jsonResponse.put("activeReservations", activeReservations);
            jsonResponse.put("maintenanceRooms", maintenanceRooms);
            jsonResponse.put("totalGuests", totalGuests);
            jsonResponse.put("totalRooms", totalRooms);
            jsonResponse.put("roomsBooked", roomsBooked);

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error fetching stats: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}