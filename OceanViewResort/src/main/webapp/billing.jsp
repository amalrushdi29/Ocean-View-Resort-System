<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ocean View Resort - Billing</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/billing.css">
<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<!-- Font Awesome for icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <%
        // Check if user is logged in
        if(session.getAttribute("loggedin") == null || !(Boolean)session.getAttribute("loggedin")) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>

    <%-- Include header and sidebar --%>
    <jsp:include page="header.jsp">
        <jsp:param name="pageTitle" value="Billing"/>
    </jsp:include>
    <jsp:include page="sidebar.jsp"/>

    <div class="main-content">
    <!-- Welcome Section -->
    <div class="welcome-section">
        <div class="welcome-content">
            <h1><i class="fas fa-file-invoice-dollar"></i> Billing & Payment</h1>
            <p class="welcome-subtitle">Generate bills and process payments for guest reservations</p>
        </div>
        <div class="welcome-graphic">
            <i class="fas fa-receipt"></i>
        </div>
    </div>

    <div class="billing-container">
            
            <!-- Message Display Area -->
            <div id="messageArea" class="message-area" style="display: none;"></div>
            
            <!-- Search Section -->
            <div class="section-card">
                <h2><i class="fas fa-search"></i> Search Reservation</h2>
                <div class="search-section">
                   <div class="search-bar">
   					<input type="text" id="reservationNumber" class="form-control" placeholder="Enter reservation number" style="flex: 1;">
    				<button onclick="searchReservation()" class="btn btn-primary">
        				<i class="fas fa-search"></i> Search
    				</button>
				</div>
                </div>
            </div>

            <!-- Billing Details Section (Hidden by default) -->
            <div class="section-card" id="billingDetailsSection" style="display: none;">
                <h2><i class="fas fa-receipt"></i> Billing Details</h2>
                
                <!-- Hidden fields -->
                <input type="hidden" id="reservationId">
                <input type="hidden" id="billId">
                
                <div class="details-grid">
                    <!-- Guest Information -->
                    <div class="detail-group">
                        <h3><i class="fas fa-user"></i> Guest Information</h3>
                        <div class="form-group">
                            <label>Guest Name</label>
                            <input type="text" id="guestName" class="form-control" readonly>
                        </div>
                    </div>

                    <!-- Room Information -->
                    <div class="detail-group">
                        <h3><i class="fas fa-bed"></i> Room Information</h3>
                        <div class="form-group">
                            <label>Room Number</label>
                            <input type="text" id="roomNo" class="form-control" readonly>
                        </div>
                        <div class="form-group">
                            <label>Room Type</label>
                            <input type="text" id="roomType" class="form-control" readonly>
                        </div>
                    </div>

                    <!-- Stay Details -->
                    <div class="detail-group">
                        <h3><i class="fas fa-calendar-alt"></i> Stay Details</h3>
                        <div class="form-group">
                            <label>Check-in Date</label>
                            <input type="text" id="checkInDate" class="form-control" readonly>
                        </div>
                        <div class="form-group">
                            <label>Check-out Date</label>
                            <input type="text" id="checkOutDate" class="form-control" readonly>
                        </div>
                        <div class="form-group">
                            <label>Number of Nights</label>
                            <input type="text" id="nights" class="form-control" readonly>
                        </div>
                    </div>

                    <!-- Rate Information -->
                    <div class="detail-group">
                        <h3><i class="fas fa-money-bill-wave"></i> Rate Information</h3>
                        <div class="form-group">
                            <label>Rate per Night</label>
                            <input type="text" id="ratePerNight" class="form-control" readonly>
                        </div>
                    </div>
                </div>

                <!-- Charges Section -->
                <div class="charges-section">
                    <h3><i class="fas fa-calculator"></i> Charges Breakdown</h3>
                    <div class="charges-table">
                        <div class="charge-row">
                            <span class="charge-label">Room Charges:</span>
                            <span class="charge-value" id="roomCharges">LKR 0.00</span>
                        </div>
                        <div class="charge-row total-row">
                            <span class="charge-label">Total Amount:</span>
                            <span class="charge-value" id="totalAmount">LKR 0.00</span>
                        </div>
                    </div>
                </div>

                <!-- Payment Section -->
                <div class="payment-section">
                    <h3><i class="fas fa-credit-card"></i> Payment Method</h3>
                    <div class="payment-methods">
                        <label class="payment-option">
                            <input type="radio" name="paymentMethod" value="Cash" checked>
                            <span class="payment-icon"><i class="fas fa-money-bill-wave"></i></span>
                            <span class="payment-text">Cash</span>
                        </label>
                        <label class="payment-option">
                            <input type="radio" name="paymentMethod" value="Card">
                            <span class="payment-icon"><i class="fas fa-credit-card"></i></span>
                            <span class="payment-text">Card</span>
                        </label>
                        <label class="payment-option">
                            <input type="radio" name="paymentMethod" value="Online Transfer">
                            <span class="payment-icon"><i class="fas fa-exchange-alt"></i></span>
                            <span class="payment-text">Online Transfer</span>
                        </label>
                    </div>
                </div>

                <!-- Action Buttons -->
                <div class="action-buttons">
                    <button onclick="generateBill()" class="btn btn-success btn-lg" id="generateBtn">
                        <i class="fas fa-file-invoice"></i> Generate Bill
                    </button>
                    <button onclick="printReceipt()" class="btn btn-info btn-lg" id="printBtn" style="display: none;">
                        <i class="fas fa-print"></i> Print Receipt
                    </button>
                    <button onclick="clearForm()" class="btn btn-secondary btn-lg">
                        <i class="fas fa-times"></i> Cancel
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script src="js/billing.js"></script>
</body>
</html>