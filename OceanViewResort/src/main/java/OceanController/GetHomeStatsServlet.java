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
 * Servlet to fetch real-time home dashboard statistics
 * PERMISSION: Staff 
 */
@WebServlet("/GetHomeStats")
public class GetHomeStatsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        JSONObject jsonResponse = new JSONObject();

        try {
            HttpSession session = request.getSession(false);

            // PERMISSION CHECK: Staff
            if (session == null || session.getAttribute("loggedin") == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Access denied.");
                out.print(jsonResponse.toString());
                out.flush();
                return;
            }

            Connection conn = DBConnection.getConnection();

            // STAT 1: Total Reservations (Confirmed + CheckedIn)
            int totalReservations = 0;
            PreparedStatement resStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM reservations " +
                "WHERE reservation_status IN ('Confirmed', 'CheckedIn')"
            );
            ResultSet resRs = resStmt.executeQuery();
            if (resRs.next()) totalReservations = resRs.getInt("count");
            resRs.close();
            resStmt.close();

            // STAT 2: Already Checked In Today
            // Guests whose check-in date is TODAY and status is CheckedIn
            int alreadyCheckedIn = 0;
            PreparedStatement alreadyCheckInStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM reservations " +
                "WHERE check_in_date = CURDATE() " +
                "AND reservation_status = 'CheckedIn'"
            );
            ResultSet alreadyCheckInRs = alreadyCheckInStmt.executeQuery();
            if (alreadyCheckInRs.next()) alreadyCheckedIn = alreadyCheckInRs.getInt("count");
            alreadyCheckInRs.close();
            alreadyCheckInStmt.close();

            // STAT 3: Confirmed Check-ins for Today (Not yet checked in)
            // Guests whose check-in date is TODAY and status is Confirmed
            int confirmedCheckIns = 0;
            PreparedStatement confirmedCheckInStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM reservations " +
                "WHERE check_in_date = CURDATE() " +
                "AND reservation_status = 'Confirmed'"
            );
            ResultSet confirmedCheckInRs = confirmedCheckInStmt.executeQuery();
            if (confirmedCheckInRs.next()) confirmedCheckIns = confirmedCheckInRs.getInt("count");
            confirmedCheckInRs.close();
            confirmedCheckInStmt.close();

            // STAT 4: Already Checked Out Today
            // Guests whose check-out date is TODAY and status is CheckedOut
            int alreadyCheckedOut = 0;
            PreparedStatement alreadyCheckOutStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM reservations " +
                "WHERE check_out_date = CURDATE() " +
                "AND reservation_status = 'CheckedOut'"
            );
            ResultSet alreadyCheckOutRs = alreadyCheckOutStmt.executeQuery();
            if (alreadyCheckOutRs.next()) alreadyCheckedOut = alreadyCheckOutRs.getInt("count");
            alreadyCheckOutRs.close();
            alreadyCheckOutStmt.close();

            // STAT 5: Check-outs Scheduled for Today (Not yet checked out)
            // Guests whose check-out date is TODAY and status is CheckedIn
            int scheduledCheckOuts = 0;
            PreparedStatement scheduledCheckOutStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM reservations " +
                "WHERE check_out_date = CURDATE() " +
                "AND reservation_status = 'CheckedIn'"
            );
            ResultSet scheduledCheckOutRs = scheduledCheckOutStmt.executeQuery();
            if (scheduledCheckOutRs.next()) scheduledCheckOuts = scheduledCheckOutRs.getInt("count");
            scheduledCheckOutRs.close();
            scheduledCheckOutStmt.close();

            // STAT 6: Available Rooms
            int availableRooms = 0;
            PreparedStatement availStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM rooms WHERE status = 'Available'"
            );
            ResultSet availRs = availStmt.executeQuery();
            if (availRs.next()) availableRooms = availRs.getInt("count");
            availRs.close();
            availStmt.close();

            // STAT 7: Total Guests
            int totalGuests = 0;
            PreparedStatement guestStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM guests"
            );
            ResultSet guestRs = guestStmt.executeQuery();
            if (guestRs.next()) totalGuests = guestRs.getInt("count");
            guestRs.close();
            guestStmt.close();

            // STAT 8: Rooms in Maintenance
            int maintenanceRooms = 0;
            PreparedStatement maintStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM rooms WHERE status = 'Maintenance'"
            );
            ResultSet maintRs = maintStmt.executeQuery();
            if (maintRs.next()) maintenanceRooms = maintRs.getInt("count");
            maintRs.close();
            maintStmt.close();

            // STAT 9: Rooms Occupied
            int roomsOccupied = 0;
            PreparedStatement occupiedStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM rooms WHERE status = 'Booked'"
            );
            ResultSet occupiedRs = occupiedStmt.executeQuery();
            if (occupiedRs.next()) roomsOccupied = occupiedRs.getInt("count");
            occupiedRs.close();
            occupiedStmt.close();

            // STAT 10: Today's Revenue
            double todayRevenue = 0;
            PreparedStatement revenueStmt = conn.prepareStatement(
                "SELECT COALESCE(SUM(total_amount), 0) AS revenue " +
                "FROM billing WHERE bill_date = CURDATE()"
            );
            ResultSet revenueRs = revenueStmt.executeQuery();
            if (revenueRs.next()) todayRevenue = revenueRs.getDouble("revenue");
            revenueRs.close();
            revenueStmt.close();

            conn.close(); 

            // Build response
            jsonResponse.put("success", true);
            jsonResponse.put("totalReservations", totalReservations);
            jsonResponse.put("alreadyCheckedIn", alreadyCheckedIn);
            jsonResponse.put("confirmedCheckIns", confirmedCheckIns);
            jsonResponse.put("alreadyCheckedOut", alreadyCheckedOut);
            jsonResponse.put("scheduledCheckOuts", scheduledCheckOuts);
            jsonResponse.put("availableRooms", availableRooms);
            jsonResponse.put("totalGuests", totalGuests);
            jsonResponse.put("maintenanceRooms", maintenanceRooms);
            jsonResponse.put("roomsOccupied", roomsOccupied);
            jsonResponse.put("todayRevenue", todayRevenue);

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error fetching stats: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}