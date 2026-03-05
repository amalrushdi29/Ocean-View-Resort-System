<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ocean View Resort - View Reservations</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/viewreservation.css">
<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<!-- Font Awesome for icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <script src="js/reservation.js"></script>
    <%-- Include header and sidebar --%>
    <jsp:include page="header.jsp">
        <jsp:param name="pageTitle" value="View Reservations"/>
    </jsp:include>
    <jsp:include page="sidebar.jsp"/>

    <div class="main-content">
    <!-- Welcome Section -->
    <div class="welcome-section">
        <div class="welcome-content">
            <h1><i class="fas fa-list-alt"></i> Reservation Management</h1>
            <p class="welcome-subtitle">Search, view, and manage all guest reservations</p>
        </div>
        <div class="welcome-graphic">
            <i class="fas fa-clipboard-list"></i>
        </div>
    </div>

    <div class="reservation-container">
            
            <!-- Message Display Area -->
            <div id="messageArea" class="message-area" style="display: none;"></div>
            
            <!-- Quick Filters -->
            <div class="quick-filters">
                <button onclick="loadTodayCheckIns()" class="btn btn-info">
                    <i class="fas fa-sign-in-alt"></i> Today's Check-ins
                </button>
                <button onclick="loadTodayCheckOuts()" class="btn btn-warning">
                    <i class="fas fa-sign-out-alt"></i> Today's Check-outs
                </button>
                <button onclick="loadAllReservations()" class="btn btn-secondary">
                    <i class="fas fa-list"></i> All Reservations
                </button>
            </div>

            <!-- Search Section -->
            <div class="section-card">
                <h2><i class="fas fa-search"></i> Search Reservation</h2>
                <div class="search-section">
                    <div class="search-bar">
                        <select id="searchType">
                            <option value="reservation_no">Reservation Number</option>
                            <option value="reservation_date">Reservation Date</option>
                            <option value="guest_name">Guest Name</option>
                        </select>
                        <input type="text" id="searchValue" placeholder="Enter search value">
                        <input type="date" id="searchDate" style="display: none;">
                        <button onclick="searchReservation()" class="btn btn-primary">
                            <i class="fas fa-search"></i> Search
                        </button>
                    </div>
                </div>
            </div>

            <!-- Reservation Results Table -->
            <div class="section-card" id="reservationResults" style="display: none;">
                <h2><i class="fas fa-table"></i> Reservation Results</h2>
                <div class="table-responsive">
                    <table id="reservationTable">
                        <thead>
                            <tr>
                                <th>Res. No</th>
                                <th>Guest Name</th>
                                <th>Room No</th>
                                <th>Check-in</th>
                                <th>Check-out</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody id="reservationTableBody">
                            <!-- Results will be populated here -->
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Reservation Details Section -->
            <div class="section-card" id="reservationDetailsSection" style="display: none;">
                <h2><i class="fas fa-info-circle"></i> Reservation Details</h2>
                
                <!-- Hidden field for reservation ID -->
                <input type="hidden" id="reservationId">
                
                <div class="details-grid">
                    <!-- Reservation Info -->
                    <div class="detail-group">
                        <h3>Reservation Information</h3>
                        <div class="form-group">
                            <label>Reservation Number</label>
                            <input type="text" id="detailReservationNo" readonly>
                        </div>
                        <div class="form-group">
                            <label>Reservation Date</label>
                            <input type="text" id="detailReservationDate" readonly>
                        </div>
                        <div class="form-group">
                            <label>Status</label>
                            <input type="text" id="detailStatus" readonly class="status-input">
                        </div>
                    </div>

                    <!-- Guest Info -->
                    <div class="detail-group">
                        <h3>Guest Information</h3>
                        <div class="form-group">
                            <label>Guest Name</label>
                            <input type="text" id="detailGuestName" readonly>
                        </div>
                        <div class="form-group">
                            <label>Contact Number</label>
                            <input type="text" id="detailGuestContact" readonly>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="text" id="detailGuestEmail" readonly>
                        </div>
                    </div>

                    <!-- Room Info -->
                    <div class="detail-group">
                        <h3>Room Information</h3>
                        <div class="form-group">
                            <label>Room Number</label>
                            <input type="text" id="detailRoomNo" readonly>
                        </div>
                        <div class="form-group">
                            <label>Room Type</label>
                            <input type="text" id="detailRoomType" readonly>
                        </div>
                        <div class="form-group">
                            <label>Rate per Night</label>
                            <input type="text" id="detailRoomRate" readonly>
                        </div>
                    </div>

                    <!-- Stay Dates -->
                    <div class="detail-group">
                        <h3>Stay Duration</h3>
                        <div class="form-group">
                            <label>Check-in Date <span id="editCheckInLabel" style="display: none;">(Editable)</span></label>
                            <input type="date" id="detailCheckInDate" readonly>
                        </div>
                        <div class="form-group">
                            <label>Check-out Date <span id="editCheckOutLabel" style="display: none;">(Editable)</span></label>
                            <input type="date" id="detailCheckOutDate" readonly>
                        </div>
                        <div class="form-group">
                            <label>Total Nights</label>
                            <input type="text" id="detailTotalNights" readonly>
                        </div>
                    </div>
                </div>

                <!-- Action Buttons -->
                <div class="action-buttons" id="actionButtons">
                    <button onclick="enableEdit()" class="btn btn-info" id="editBtn">
                        <i class="fas fa-edit"></i> Update Dates
                    </button>
                    <button onclick="saveUpdates()" class="btn btn-success" id="saveBtn" style="display: none;">
                        <i class="fas fa-save"></i> Save Changes
                    </button>
                    <button onclick="cancelEdit()" class="btn btn-secondary" id="cancelEditBtn" style="display: none;">
                        <i class="fas fa-times"></i> Cancel Edit
                    </button>
                    <button onclick="checkInGuest()" class="btn btn-primary" id="checkInBtn" style="display: none;">
                        <i class="fas fa-sign-in-alt"></i> Check-In
                    </button>
                    <button onclick="checkOutGuest()" class="btn btn-warning" id="checkOutBtn" style="display: none;">
                        <i class="fas fa-sign-out-alt"></i> Check-Out
                    </button>
                    <button onclick="cancelReservation()" class="btn btn-danger" id="cancelBtn" style="display: none;">
                        <i class="fas fa-ban"></i> Cancel Reservation
                    </button>
                    <button onclick="proceedToBilling()" class="btn btn-success btn-lg" id="billingBtn" style="display: none;">
                        <i class="fas fa-file-invoice-dollar"></i> Proceed to Billing
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script src="js/viewreservation.js"></script>
</body>
</html>
