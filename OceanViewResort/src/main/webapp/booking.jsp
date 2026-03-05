<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ocean View Resort - Booking</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/booking.css">
<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<!-- Font Awesome for icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <%-- Include header and sidebar --%>
    <jsp:include page="header.jsp">
        <jsp:param name="pageTitle" value="Room Booking"/>
    </jsp:include>
    <jsp:include page="sidebar.jsp"/>

    <div class="main-content">
    <!-- Welcome Section -->
    <div class="welcome-section">
        <div class="welcome-content">
            <h1><i class="fas fa-calendar-check"></i> New Reservation</h1>
            <p class="welcome-subtitle">Book rooms for guests with ease</p>
        </div>
        <div class="welcome-graphic">
            <i class="fas fa-bed"></i>
        </div>
    </div>

    <div class="booking-container">            
            <!-- Message Display Area -->
            <div id="messageArea" class="message-area" style="display: none;"></div>
            
            <!-- Step 1: Guest Search Section -->
            <div class="section-card">
                <h2><i class="fas fa-user-search"></i> Step 1: Search Guest</h2>
                <div class="search-section">
                    <div class="search-bar">
                        <input type="text" id="guestSearch" placeholder="Search by Name, Contact Number, NIC, or Passport">
                        <button onclick="searchGuest()" class="btn btn-primary">
                            <i class="fas fa-search"></i> Search
                        </button>
                    </div>
                    
                    <div id="guestResults" class="results-table" style="display: none;">
                        <h3>Search Results</h3>
                        <table id="guestTable">
                            <thead>
                                <tr>
                                    <th>Guest ID</th>
                                    <th>Name</th>
                                    <th>Contact No</th>
                                    <th>Email</th>
                                    <th>NIC/Passport</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody id="guestTableBody">
                                <!-- Results will be populated here -->
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Step 2: Selected Guest Details -->
            <div class="section-card" id="selectedGuestSection" style="display: none;">
                <h2><i class="fas fa-user-check"></i> Step 2: Guest Details</h2>
                <div class="guest-details-grid">
                    <div class="form-group">
                        <label>Guest ID</label>
                        <input type="text" id="selectedGuestId" readonly>
                    </div>
                    <div class="form-group">
                        <label>Guest Name</label>
                        <input type="text" id="selectedGuestName" readonly>
                    </div>
                    <div class="form-group">
                        <label>Contact Number</label>
                        <input type="text" id="selectedGuestContact" readonly>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="text" id="selectedGuestEmail" readonly>
                    </div>
                </div>
            </div>

            <!-- Step 3: Reservation Details -->
            <div class="section-card" id="reservationDetailsSection" style="display: none;">
                <h2><i class="fas fa-clipboard-list"></i> Step 3: Reservation Details</h2>
                <div class="reservation-details-grid">
                    <div class="form-group">
                        <label>Reservation Number</label>
                        <input type="text" id="reservationNo" readonly>
                    </div>
                    <div class="form-group">
                        <label>Reservation Date</label>
                        <input type="date" id="reservationDate" readonly>
                    </div>
                    <div class="form-group">
                        <label>Check-in Date *</label>
                        <input type="date" id="checkInDate" required>
                    </div>
                    <div class="form-group">
                        <label>Check-out Date *</label>
                        <input type="date" id="checkOutDate" required>
                    </div>
                    <div class="form-group">
                        <label>Room Type *</label>
                        <select id="roomType" required>
                            <option value="">Select Room Type</option>
                            <option value="Single">Single Room</option>
                            <option value="Double">Double Room</option>
                            <option value="Suite">Suite</option>
                            <option value="Luxury">Luxury Suite</option>
                            <option value="Deluxe">Deluxe Room</option>
                            <option value="Presidential">Presidential Suite</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <button onclick="checkAvailability()" class="btn btn-success">
                            <i class="fas fa-search"></i> Check Availability
                        </button>
                    </div>
                </div>
            </div>

            <!-- Step 4: Available Rooms Section -->
            <div class="section-card" id="availableRoomsSection" style="display: none;">
                <h2><i class="fas fa-door-open"></i> Step 4: Available Rooms</h2>
                <div id="roomResults" class="results-table">
                    <table id="roomTable">
                        <thead>
                            <tr>
                                <th>Room No</th>
                                <th>Floor</th>
                                <th>View</th>
                                <th>Rate per Night</th>
                                <th>Max Capacity</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody id="roomTableBody">
                            <!-- Results will be populated here -->
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Step 5: Selected Room Details -->
            <div class="section-card" id="selectedRoomSection" style="display: none;">
                <h2><i class="fas fa-bed"></i> Step 5: Selected Room Details</h2>
                <div class="room-details-grid">
                    <div class="form-group">
                        <label>Room ID</label>
                        <input type="text" id="selectedRoomId" readonly>
                    </div>
                    <div class="form-group">
                        <label>Room Number</label>
                        <input type="text" id="selectedRoomNo" readonly>
                    </div>
                    <div class="form-group">
                        <label>Room Type</label>
                        <input type="text" id="selectedRoomType" readonly>
                    </div>
                    <div class="form-group">
                        <label>Rate per Night</label>
                        <input type="text" id="selectedRoomRate" readonly>
                    </div>
                    <div class="form-group">
                        <label>Floor</label>
                        <input type="text" id="selectedRoomFloor" readonly>
                    </div>
                    <div class="form-group">
                        <label>View</label>
                        <input type="text" id="selectedRoomView" readonly>
                    </div>
                </div>
            </div>

            <!-- Action Buttons -->
            <div class="action-buttons" id="actionButtons" style="display: none;">
                <button onclick="confirmBooking()" class="btn btn-primary btn-lg">
                    <i class="fas fa-check-circle"></i> Confirm Booking
                </button>
                <button onclick="resetForm()" class="btn btn-warning btn-lg">
                    <i class="fas fa-redo"></i> Reset
                </button>
                <button onclick="cancelBooking()" class="btn btn-danger btn-lg">
                    <i class="fas fa-times-circle"></i> Cancel
                </button>
            </div>
        </div>
    </div>

    <script src="js/booking.js"></script>
</body>
</html>
