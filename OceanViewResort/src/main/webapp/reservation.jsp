<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ocean View Resort - Guest Registration</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/reservation.css">
<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<!-- Font Awesome for icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<script src="js/reservation.js"></script>
    <%-- Include header and sidebar --%>
    <jsp:include page="header.jsp">
        <jsp:param name="pageTitle" value="Guest Registration"/>
    </jsp:include>
    <jsp:include page="sidebar.jsp"/> 

    <div class="main-content">
        <!-- Welcome Section -->
        <div class="welcome-section">
            <div class="welcome-content">
                <h1><i class="fas fa-user-plus"></i> Guest Registration</h1>
            </div>
            <div class="welcome-graphic">
                <i class="fas fa-users"></i>
            </div>
        </div>

        <!-- Success/Error Messages -->
        <%
            String successMessage = (String) request.getAttribute("successMessage");
            String errorMessage = (String) request.getAttribute("errorMessage");
            String messageType = (String) request.getAttribute("messageType");
            
            if (successMessage != null || errorMessage != null) {
        %>
            <div id="messageContainer" class="message-container <%= messageType %>" style="display: block;">
                <span id="messageText">
                    <%= successMessage != null ? successMessage : errorMessage %>
                </span>
            </div>
        <%
            }
        %>

        <!-- Registration Form Container -->
        <div class="registration-container">
            <form id="guestRegistrationForm" action="AddReservationServlet" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="add">

                <div class="form-grid">
                    <!-- Guest Name -->
                    <div class="form-group full-width">
                        <label for="guestName">
                            <i class="fas fa-user"></i>
                            Guest Name <span class="required">*</span>
                        </label>
                        <input 
                            type="text" 
                            id="guestName" 
                            name="guestName" 
                            placeholder="Enter full name"
                            required
                            maxlength="100"
                            value="<%= request.getAttribute("guestName") != null ? request.getAttribute("guestName") : "" %>">
                        <span class="error-message" id="nameError"></span>
                    </div>

                    <!-- Guest Address -->
                    <div class="form-group full-width">
                        <label for="guestAddress">
                            <i class="fas fa-map-marker-alt"></i>
                            Guest Address <span class="required">*</span>
                        </label>
                        <textarea 
                            id="guestAddress" 
                            name="guestAddress" 
                            rows="3"
                            placeholder="Enter complete address"
                            required
                            maxlength="250"><%= request.getAttribute("guestAddress") != null ? request.getAttribute("guestAddress") : "" %></textarea>
                        <span class="error-message" id="addressError"></span>
                    </div>

                    <!-- Contact Number -->
                    <div class="form-group">
                        <label for="contactNumber">
                            <i class="fas fa-phone"></i>
                            Contact Number <span class="required">*</span>
                        </label>
                        <input 
                            type="tel" 
                            id="contactNumber" 
                            name="contactNumber" 
                            placeholder="e.g., 0771234567"
                            required
                            pattern="[0-9]{10}"
                            maxlength="10"
                            value="<%= request.getAttribute("contactNumber") != null ? request.getAttribute("contactNumber") : "" %>">
                        <span class="error-message" id="contactError"></span>
                    </div>

                    <!-- Email -->
                    <div class="form-group">
                        <label for="email">
                            <i class="fas fa-envelope"></i>
                            Email Address <span class="required">*</span>
                        </label>
                        <input 
                            type="email" 
                            id="email" 
                            name="email" 
                            placeholder="guest@example.com"
                            required
                            maxlength="100"
                            value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>">
                        <span class="error-message" id="emailError"></span>
                    </div>

                    <!-- NIC / Passport Number -->
                    <div class="form-group full-width">
                        <label for="nicPassport">
                            <i class="fas fa-id-card"></i>
                            NIC / Passport Number <span class="required">*</span>
                        </label>
                        <input 
                            type="text" 
                            id="nicPassport" 
                            name="nicPassport" 
                            placeholder="e.g., 199512345678 or P1234567"
                            required
                            maxlength="20"
                            value="<%= request.getAttribute("nicPassport") != null ? request.getAttribute("nicPassport") : "" %>">
                        <span class="error-message" id="nicError"></span>
                    </div>
                </div>

                <!-- Form Actions -->
                <div class="form-actions">
                    <button type="submit" class="action-btn btn-primary">
                        <i class="fas fa-save btn-icon"></i>
                        <span class="btn-text">Save Guest</span>
                        <i class="fas fa-arrow-right btn-arrow"></i>
                    </button>
                    <button type="reset" class="action-btn btn-secondary" onclick="clearErrors()">
                        <i class="fas fa-eraser btn-icon"></i>
                        <span class="btn-text">Clear Form</span>
                        <i class="fas fa-arrow-right btn-arrow"></i>
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script src="js/guest-registration.js"></script>
    <script>
        // Auto-hide message after 5 seconds
        window.onload = function() {
            const messageContainer = document.getElementById('messageContainer');
            if (messageContainer && messageContainer.style.display === 'block') {
                setTimeout(() => {
                    messageContainer.style.display = 'none';
                }, 5000);
            }
        };
    </script>
</body>
</html>
