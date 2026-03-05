package OceanController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import org.json.JSONObject;
import OceanDAO.BillingDao;
import OceanModel.BillingBean;

/**
 * Servlet to generate a bill
 * Creates bill record in database
 */
@WebServlet("/GenerateBill")
public class GenerateBillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private BillingDao billingDao;
    
    public GenerateBillServlet() {
        super();
        billingDao = new BillingDao();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // Read JSON data
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        
        JSONObject jsonRequest = new JSONObject(sb.toString());
        JSONObject jsonResponse = new JSONObject();
        
        try {
            int reservationId = jsonRequest.getInt("reservationId");
            double roomCharges = jsonRequest.getDouble("roomCharges");
            double totalAmount = jsonRequest.getDouble("totalAmount");
            String paymentMethod = jsonRequest.getString("paymentMethod");
            
            // Check if bill already exists for this reservation
            if (billingDao.billExistsForReservation(reservationId)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Bill already exists for this reservation.");
                out.print(jsonResponse.toString());
                out.flush();
                return;
            }
            
            // Create billing bean
            BillingBean billing = new BillingBean();
            billing.setReservationId(reservationId);
            billing.setRoomCharges(roomCharges);
            billing.setTotalAmount(totalAmount);
            billing.setPaymentMethod(paymentMethod);
            
            // Create bill in database
            int billId = billingDao.createBill(billing);
            
            if (billId > 0) {
                // Get the generated bill to retrieve bill number
                BillingBean generatedBill = billingDao.getBillById(billId);
                
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Bill generated successfully!");
                jsonResponse.put("billId", billId);
                jsonResponse.put("billNo", generatedBill.getBillNo());
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to generate bill. Please try again.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error generating bill: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}