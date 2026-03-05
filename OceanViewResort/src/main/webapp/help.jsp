<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ocean View Resort - Help & User Manual</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/help.css">
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
        <jsp:param name="pageTitle" value="Help & User Manual"/>
    </jsp:include>
    <jsp:include page="sidebar.jsp"/>

    <div class="main-content">
        <!-- Welcome Section -->
        <div class="welcome-section">
            <div class="welcome-content">
                <h1><i class="fas fa-question-circle"></i> Staff User Manual & Help Guide</h1>
                <p class="welcome-subtitle">Complete guide to using the Ocean View Resort Management System</p>
            </div>
            <div class="welcome-graphic">
                <i class="fas fa-book-open"></i>
            </div>
        </div>

        <!-- Search Bar -->
        <div class="help-search-bar">
            <div class="search-container">
                <i class="fas fa-search"></i>
                <input type="text" id="helpSearch" placeholder="Search help topics...">
            </div>
            <button class="btn-print" onclick="window.print()">
                <i class="fas fa-print"></i> Print Manual
            </button>
        </div>

        <!-- Quick Navigation -->
        <div class="quick-nav">
            <h3><i class="fas fa-compass"></i> Quick Navigation</h3>
            <div class="nav-buttons">
                <a href="#home" class="nav-btn"><i class="fas fa-home"></i> Home Dashboard</a>
                <a href="#reservation" class="nav-btn"><i class="fas fa-user-plus"></i> Add Guest</a>
                <a href="#booking" class="nav-btn"><i class="fas fa-bed"></i> Create Booking</a>
                <a href="#view-reservations" class="nav-btn"><i class="fas fa-list-alt"></i> Manage Reservations</a>
                <a href="#billing" class="nav-btn"><i class="fas fa-file-invoice-dollar"></i> Billing</a>
                <a href="#rooms" class="nav-btn"><i class="fas fa-door-open"></i> Room Manager</a>
                <a href="#guests" class="nav-btn"><i class="fas fa-users"></i> Guest Manager</a>
            </div>
        </div>

        <!-- Help Sections -->
        <div class="help-container">
            
            <!-- MODULE 1: HOME DASHBOARD -->
            <div class="help-section" id="home" data-keywords="home dashboard statistics stats revenue checkin checkout">
                <div class="section-header" onclick="toggleSection('home')">
                    <div class="header-left">
                        <i class="fas fa-home module-icon"></i>
                        <h2>Home Dashboard</h2>
                    </div>
                    <i class="fas fa-chevron-down toggle-icon"></i>
                </div>
                
                <div class="section-content">
                    <div class="info-box">
                        <i class="fas fa-info-circle"></i>
                        <p>The home dashboard provides a real-time overview of all hotel operations with 10 key statistics and quick action buttons.</p>
                    </div>

                    <h3><i class="fas fa-chart-bar"></i> What You'll See</h3>
                    <div class="feature-grid">
                        <div class="feature-item">
                            <i class="fas fa-calendar-check"></i>
                            <strong>Total Reservations</strong>
                            <span>Confirmed + Checked In guests</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-money-bill-wave"></i>
                            <strong>Today's Revenue</strong>
                            <span>Bills generated today</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-user-check"></i>
                            <strong>Already Checked In</strong>
                            <span>Guests who arrived today</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-clock"></i>
                            <strong>Pending Check-ins</strong>
                            <span>Guests arriving today</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-door-open"></i>
                            <strong>Already Checked Out</strong>
                            <span>Guests who left today</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-calendar-times"></i>
                            <strong>Pending Check-outs</strong>
                            <span>Guests leaving today</span>
                        </div>
                    </div>

                    <h3><i class="fas fa-tasks"></i> How to Use</h3>
                    <ol class="step-list">
                        <li>View the <strong>dynamic greeting</strong> at the top (changes based on time of day)</li>
                        <li>Check the <strong>current date and time</strong> (updates automatically)</li>
                        <li>Review the <strong>10 stat cards</strong> for real-time data</li>
                        <li>Use <strong>Quick Actions</strong> buttons to navigate to other modules</li>
                    </ol>

                    <div class="tip-box">
                        <i class="fas fa-lightbulb"></i>
                        <strong>Pro Tips:</strong>
                        <ul>
                            <li>Check the dashboard first thing every morning</li>
                            <li>Stats auto-refresh every 30 seconds</li>
                            <li>Monitor "Pending Check-ins" and "Pending Check-outs" for daily tasks</li>
                            <li>Today's revenue updates when you generate bills</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- MODULE 2: ADD NEW GUEST -->
            <div class="help-section" id="reservation" data-keywords="add new guest reservation register nic passport contact email">
                <div class="section-header" onclick="toggleSection('reservation')">
                    <div class="header-left">
                        <i class="fas fa-user-plus module-icon"></i>
                        <h2>Add New Guest (Reservation)</h2>
                    </div>
                    <i class="fas fa-chevron-down toggle-icon"></i>
                </div>
                
                <div class="section-content">
                    <div class="info-box">
                        <i class="fas fa-info-circle"></i>
                        <p>Register new guests in the system before creating their reservations. This is the first step in the booking process.</p>
                    </div>

                    <h3><i class="fas fa-list-ol"></i> Step-by-Step Instructions</h3>
                    <ol class="step-list">
                        <li>
                            <strong>Navigate to Reservation Page</strong>
                            <p>Click "New Reservation" from the home dashboard or sidebar</p>
                        </li>
                        <li>
                            <strong>Fill in Guest Details</strong>
                            <ul>
                                <li><strong>Full Name:</strong> Enter guest's complete name</li>
                                <li><strong>Address:</strong> Enter full residential address</li>
                                <li><strong>Contact Number:</strong> Enter 10-digit phone number (e.g., 0771234567)</li>
                                <li><strong>Email:</strong> Enter valid email address</li>
                                <li><strong>NIC/Passport:</strong> Enter unique identification number</li>
                            </ul>
                        </li>
                        <li>
                            <strong>Submit Form</strong>
                            <p>Click "Submit" button to save the guest</p>
                        </li>
                        <li>
                            <strong>Confirmation</strong>
                            <p>Success message appears and guest is added to the system</p>
                        </li>
                    </ol>

                    <h3><i class="fas fa-shield-alt"></i> Validation Rules</h3>
                    <div class="validation-box">
                        <div class="validation-item">
                            <i class="fas fa-check-circle"></i>
                            <strong>Contact Number:</strong> Must be 10 digits (stored as 9 without leading 0)
                        </div>
                        <div class="validation-item">
                            <i class="fas fa-check-circle"></i>
                            <strong>Email:</strong> Must be valid format (name@domain.com)
                        </div>
                        <div class="validation-item">
                            <i class="fas fa-check-circle"></i>
                            <strong>NIC/Passport:</strong> Must be unique (no duplicates allowed)
                        </div>
                        <div class="validation-item">
                            <i class="fas fa-check-circle"></i>
                            <strong>All Fields:</strong> Required (cannot be empty)
                        </div>
                    </div>

                    <h3><i class="fas fa-exclamation-triangle"></i> Common Errors & Solutions</h3>
                    <div class="error-solutions">
                        <div class="error-item">
                            <div class="error-msg">❌ "Contact number already exists"</div>
                            <div class="solution">✅ Guest is already registered. Search for existing guest instead.</div>
                        </div>
                        <div class="error-item">
                            <div class="error-msg">❌ "Email already exists"</div>
                            <div class="solution">✅ Use a different email address or find the existing guest.</div>
                        </div>
                        <div class="error-item">
                            <div class="error-msg">❌ "NIC/Passport already exists"</div>
                            <div class="solution">✅ This guest is already in the system. Use Guest Manager to find them.</div>
                        </div>
                    </div>

                    <div class="warning-box">
                        <i class="fas fa-exclamation-circle"></i>
                        <strong>Important Notes:</strong>
                        <ul>
                            <li>Always verify guest details before submitting</li>
                            <li>Phone numbers are stored without the leading 0 (system handles this automatically)</li>
                            <li>Once submitted, name and NIC/Passport cannot be changed (only contact, email, and address)</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- MODULE 3: CREATE BOOKING -->
            <div class="help-section" id="booking" data-keywords="booking reservation room checkin checkout availability">
                <div class="section-header" onclick="toggleSection('booking')">
                    <div class="header-left">
                        <i class="fas fa-bed module-icon"></i>
                        <h2>Create Reservation (Booking)</h2>
                    </div>
                    <i class="fas fa-chevron-down toggle-icon"></i>
                </div>
                
                <div class="section-content">
                    <div class="info-box">
                        <i class="fas fa-info-circle"></i>
                        <p>Book rooms for registered guests. This module handles room selection, availability checking, and reservation creation.</p>
                    </div>

                    <h3><i class="fas fa-list-ol"></i> Complete Booking Process</h3>
                    
                    <div class="process-step">
                        <div class="step-number">1</div>
                        <div class="step-content">
                            <h4><i class="fas fa-search"></i> Search for Guest</h4>
                            <ul>
                                <li>Enter guest name, contact, email, or NIC in search box</li>
                                <li>Click "Search Guest" button</li>
                                <li>View search results in table</li>
                                <li>Click "Select" button next to the guest</li>
                            </ul>
                        </div>
                    </div>

                    <div class="process-step">
                        <div class="step-number">2</div>
                        <div class="step-content">
                            <h4><i class="fas fa-calendar-alt"></i> Select Dates & Room Type</h4>
                            <ul>
                                <li><strong>Check-in Date:</strong> Select today or future date</li>
                                <li><strong>Check-out Date:</strong> Must be after check-in date</li>
                                <li><strong>Room Type:</strong> Choose from dropdown (Single, Double, Suite, etc.)</li>
                                <li>Click "Check Availability" button</li>
                            </ul>
                        </div>
                    </div>

                    <div class="process-step">
                        <div class="step-number">3</div>
                        <div class="step-content">
                            <h4><i class="fas fa-door-open"></i> Choose Room</h4>
                            <ul>
                                <li>View list of available rooms with details:
                                    <ul>
                                        <li>Room Number</li>
                                        <li>Floor Number</li>
                                        <li>View Type (Ocean, Garden, City)</li>
                                        <li>Rate per Night</li>
                                        <li>Maximum Capacity</li>
                                    </ul>
                                </li>
                                <li>Click "Select" button on your preferred room</li>
                            </ul>
                        </div>
                    </div>

                    <div class="process-step">
                        <div class="step-number">4</div>
                        <div class="step-content">
                            <h4><i class="fas fa-check-circle"></i> Confirm Booking</h4>
                            <ul>
                                <li>Review all booking details:
                                    <ul>
                                        <li>Guest information</li>
                                        <li>Selected room details</li>
                                        <li>Check-in and check-out dates</li>
                                    </ul>
                                </li>
                                <li>Click "Confirm Booking" button</li>
                                <li>Confirm the booking in the popup dialog</li>
                            </ul>
                        </div>
                    </div>

                    <div class="success-box">
                        <i class="fas fa-check-circle"></i>
                        <strong>After Successful Booking:</strong>
                        <ul>
                            <li>Unique reservation number is generated (Format: RES{timestamp}{random})</li>
                            <li>Reservation status is set to "Confirmed"</li>
                            <li>Room status changes to "Booked"</li>
                            <li>Success message displays the reservation number</li>
                        </ul>
                    </div>

                    <h3><i class="fas fa-exclamation-triangle"></i> Common Issues</h3>
                    <div class="error-solutions">
                        <div class="error-item">
                            <div class="error-msg">❌ "No rooms available for the selected dates and type"</div>
                            <div class="solution">✅ Try different dates or different room type</div>
                        </div>
                        <div class="error-item">
                            <div class="error-msg">❌ "Check-out date must be after check-in date"</div>
                            <div class="solution">✅ Select valid date range (minimum 1 night stay)</div>
                        </div>
                        <div class="error-item">
                            <div class="error-msg">❌ "Please select a guest"</div>
                            <div class="solution">✅ Search and select a guest before proceeding</div>
                        </div>
                    </div>

                    <div class="tip-box">
                        <i class="fas fa-lightbulb"></i>
                        <strong>Best Practices:</strong>
                        <ul>
                            <li>Always verify guest details before creating reservation</li>
                            <li>Double-check dates to avoid mistakes</li>
                            <li>Note down the reservation number for future reference</li>
                            <li>Inform guest of their reservation number</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- MODULE 4: VIEW & MANAGE RESERVATIONS -->
            <div class="help-section" id="view-reservations" data-keywords="view manage search checkin checkout cancel edit reservation">
                <div class="section-header" onclick="toggleSection('view-reservations')">
                    <div class="header-left">
                        <i class="fas fa-list-alt module-icon"></i>
                        <h2>View & Manage Reservations</h2>
                    </div>
                    <i class="fas fa-chevron-down toggle-icon"></i>
                </div>
                
                <div class="section-content">
                    <div class="info-box">
                        <i class="fas fa-info-circle"></i>
                        <p>Comprehensive reservation management including search, view details, edit dates, check-in, check-out, and cancellation.</p>
                    </div>

                    <h3><i class="fas fa-search"></i> Search & Filter Options</h3>
                    <div class="feature-grid">
                        <div class="feature-item">
                            <i class="fas fa-hashtag"></i>
                            <strong>By Reservation Number</strong>
                            <span>Enter exact reservation no.</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-user"></i>
                            <strong>By Guest Name</strong>
                            <span>Search by guest name</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-calendar"></i>
                            <strong>By Date</strong>
                            <span>Select reservation date</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-filter"></i>
                            <strong>Quick Filters</strong>
                            <span>Today's check-ins/outs</span>
                        </div>
                    </div>

                    <h3><i class="fas fa-eye"></i> View Reservation Details</h3>
                    <ol class="step-list">
                        <li>Search for a reservation using any method above</li>
                        <li>Click the <strong>"View"</strong> button on any reservation in the table</li>
                        <li>Complete details appear including:
                            <ul>
                                <li>Reservation number and date</li>
                                <li>Reservation status</li>
                                <li>Guest information (name, contact, email)</li>
                                <li>Room details (number, type, rate)</li>
                                <li>Check-in and check-out dates</li>
                                <li>Total number of nights</li>
                            </ul>
                        </li>
                    </ol>

                    <h3><i class="fas fa-edit"></i> Edit Reservation Dates</h3>
                    <div class="process-step">
                        <div class="step-number">📝</div>
                        <div class="step-content">
                            <h4>For "Confirmed" Status</h4>
                            <ul>
                                <li>Click "Edit" button</li>
                                <li>Can change BOTH check-in and check-out dates</li>
                                <li>System verifies room availability for new dates</li>
                                <li>Click "Save" to update</li>
                            </ul>
                        </div>
                    </div>

                    <div class="process-step">
                        <div class="step-number">📝</div>
                        <div class="step-content">
                            <h4>For "CheckedIn" Status</h4>
                            <ul>
                                <li>Click "Edit" button</li>
                                <li>Can only change check-out date (to extend stay)</li>
                                <li>Check-in date is locked (guest already checked in)</li>
                                <li>System shows message: "Can only extend check-out date"</li>
                                <li>Click "Save" to update</li>
                            </ul>
                        </div>
                    </div>

                    <h3><i class="fas fa-sign-in-alt"></i> Check-In Process</h3>
                    <div class="instruction-box">
                        <strong>When Available:</strong> Only on the check-in date (when check-in date = today)
                        
                        <ol class="step-list">
                            <li>Verify it's the correct guest and reservation</li>
                            <li>Click "Check In" button</li>
                            <li>Confirm the action in popup dialog</li>
                            <li>Status changes: <span class="status-badge status-confirmed">Confirmed</span> → <span class="status-badge status-checkedin">CheckedIn</span></li>
                            <li>Room status updates to "Booked"</li>
                        </ol>
                    </div>

                    <h3><i class="fas fa-sign-out-alt"></i> Check-Out Process</h3>
                    <div class="instruction-box">
                        <strong>Three Possible Scenarios:</strong>
                        
                        <div class="scenario-box">
                            <strong>Scenario 1: On-Time Checkout</strong>
                            <p>Guest checks out on the scheduled check-out date</p>
                            <ul>
                                <li>Click "Check Out" button</li>
                                <li>Confirm action</li>
                                <li>System automatically redirects to billing page</li>
                            </ul>
                        </div>

                        <div class="scenario-box">
                            <strong>Scenario 2: Early Checkout</strong>
                            <p>Guest checks out before scheduled date</p>
                            <ul>
                                <li>Click "Check Out" button</li>
                                <li>System shows: "Guest is checking out early. Please update check-out date first."</li>
                                <li>Click "Edit" button (auto-enabled)</li>
                                <li>Change check-out date to today</li>
                                <li>Click "Save"</li>
                                <li>Click "Check Out" again</li>
                                <li>Proceeds to billing</li>
                            </ul>
                        </div>

                        <div class="scenario-box">
                            <strong>Scenario 3: Late Checkout</strong>
                            <p>Guest checks out after scheduled date</p>
                            <ul>
                                <li>Click "Check Out" button</li>
                                <li>System shows: "Guest is checking out late. Please update check-out date first."</li>
                                <li>Click "Edit" button (auto-enabled)</li>
                                <li>Change check-out date to today</li>
                                <li>Click "Save"</li>
                                <li>Click "Check Out" again</li>
                                <li>Proceeds to billing</li>
                            </ul>
                        </div>
                    </div>

                    <h3><i class="fas fa-times-circle"></i> Cancel Reservation</h3>
                    <div class="warning-box">
                        <i class="fas fa-exclamation-triangle"></i>
                        <strong>Cancellation Rules:</strong>
                        <ul>
                            <li>Only available for "Confirmed" reservations</li>
                            <li>Cannot cancel if guest is already checked in</li>
                            <li>Action cannot be undone</li>
                        </ul>
                    </div>

                    <ol class="step-list">
                        <li>View the reservation details</li>
                        <li>Click "Cancel" button (red button)</li>
                        <li>Confirm: "Are you sure you want to cancel this reservation?"</li>
                        <li>Status changes to <span class="status-badge status-cancelled">Cancelled</span></li>
                        <li>Room becomes "Available" again</li>
                    </ol>

                    <h3><i class="fas fa-palette"></i> Status Colors Guide</h3>
                    <div class="status-guide">
                        <div class="status-item">
                            <span class="status-badge status-confirmed">Confirmed</span>
                            <span>Reservation is booked, guest hasn't arrived yet</span>
                        </div>
                        <div class="status-item">
                            <span class="status-badge status-checkedin">CheckedIn</span>
                            <span>Guest has checked in and is currently staying</span>
                        </div>
                        <div class="status-item">
                            <span class="status-badge status-checkedout">CheckedOut</span>
                            <span>Guest has checked out, ready for billing</span>
                        </div>
                        <div class="status-item">
                            <span class="status-badge status-cancelled">Cancelled</span>
                            <span>Reservation was cancelled</span>
                        </div>
                    </div>

                    <div class="tip-box">
                        <i class="fas fa-lightbulb"></i>
                        <strong>Pro Tips:</strong>
                        <ul>
                            <li>Use "Today's Check-ins" filter at the start of each day</li>
                            <li>Use "Today's Check-outs" filter to prepare for departures</li>
                            <li>Search works on all reservations, not just the current page</li>
                            <li>Table shows 10 reservations per page</li>
                            <li>Messages auto-hide after 5 seconds</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- MODULE 5: BILLING -->
            <div class="help-section" id="billing" data-keywords="billing payment invoice receipt pdf cash card">
                <div class="section-header" onclick="toggleSection('billing')">
                    <div class="header-left">
                        <i class="fas fa-file-invoice-dollar module-icon"></i>
                        <h2>Billing & Payments</h2>
                    </div>
                    <i class="fas fa-chevron-down toggle-icon"></i>
                </div>
                
                <div class="section-content">
                    <div class="info-box">
                        <i class="fas fa-info-circle"></i>
                        <p>Generate bills, process payments, and print professional receipts for checked-out guests.</p>
                    </div>

                    <h3><i class="fas fa-list-ol"></i> Complete Billing Process</h3>
                    
                    <div class="process-step">
                        <div class="step-number">1</div>
                        <div class="step-content">
                            <h4><i class="fas fa-search"></i> Search Reservation</h4>
                            <ul>
                                <li>Enter reservation number in search box</li>
                                <li>Click "Search" button</li>
                                <li><strong>OR</strong> Click "Proceed to Billing" from View Reservations (auto-fills)</li>
                            </ul>
                            <div class="note-box">
                                <strong>Note:</strong> Only checked-out reservations can be billed
                            </div>
                        </div>
                    </div>

                    <div class="process-step">
                        <div class="step-number">2</div>
                        <div class="step-content">
                            <h4><i class="fas fa-file-alt"></i> Review Billing Details</h4>
                            <p>System automatically displays:</p>
                            <ul>
                                <li><strong>Guest Information:</strong> Name</li>
                                <li><strong>Room Details:</strong> Number and Type</li>
                                <li><strong>Stay Details:</strong>
                                    <ul>
                                        <li>Check-in Date</li>
                                        <li>Check-out Date</li>
                                        <li>Number of Nights</li>
                                    </ul>
                                </li>
                                <li><strong>Rate Information:</strong> Rate per Night</li>
                            </ul>
                        </div>
                    </div>

                    <div class="process-step">
                        <div class="step-number">3</div>
                        <div class="step-content">
                            <h4><i class="fas fa-calculator"></i> Verify Charges</h4>
                            <div class="formula-box">
                                <strong>Calculation Formula:</strong>
                                <div class="formula">
                                    <p>Room Charges = Rate per Night × Number of Nights</p>
                                    <p>Total Amount = Room Charges</p>
                                </div>
                            </div>
                            <p>System calculates automatically and displays in blue card</p>
                        </div>
                    </div>

                    <div class="process-step">
                        <div class="step-number">4</div>
                        <div class="step-content">
                            <h4><i class="fas fa-credit-card"></i> Select Payment Method</h4>
                            <p>Choose one option:</p>
                            <div class="payment-methods">
                                <div class="payment-option">
                                    <i class="fas fa-money-bill-wave"></i>
                                    <strong>Cash</strong>
                                </div>
                                <div class="payment-option">
                                    <i class="fas fa-credit-card"></i>
                                    <strong>Card</strong>
                                </div>
                                <div class="payment-option">
                                    <i class="fas fa-exchange-alt"></i>
                                    <strong>Online Transfer</strong>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="process-step">
                        <div class="step-number">5</div>
                        <div class="step-content">
                            <h4><i class="fas fa-check-circle"></i> Generate Bill</h4>
                            <ul>
                                <li>Click "Generate Bill" button</li>
                                <li>Confirm action</li>
                                <li>Bill is saved to database</li>
                                <li>Unique bill number is created</li>
                                <li>Success message displays</li>
                                <li>"Print Receipt" button appears</li>
                            </ul>
                        </div>
                    </div>

                    <div class="process-step">
                        <div class="step-number">6</div>
                        <div class="step-content">
                            <h4><i class="fas fa-print"></i> Print Receipt</h4>
                            <ul>
                                <li>Click "Print Receipt" button</li>
                                <li>PDF downloads automatically</li>
                                <li>Professional receipt with:
                                    <ul>
                                        <li>Blue and gold hotel branding</li>
                                        <li>Hotel logo and details</li>
                                        <li>Guest and reservation information</li>
                                        <li>Itemized charges breakdown</li>
                                        <li>Payment method</li>
                                        <li>Bill number and date</li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <div class="success-box">
                        <i class="fas fa-check-circle"></i>
                        <strong>After Successful Billing:</strong>
                        <ul>
                            <li>Today's revenue on home dashboard updates</li>
                            <li>Bill is permanently saved in system</li>
                            <li>Receipt can be reprinted anytime</li>
                        </ul>
                    </div>

                    <h3><i class="fas fa-exclamation-triangle"></i> Important Rules</h3>
                    <div class="warning-box">
                        <i class="fas fa-exclamation-circle"></i>
                        <ul>
                            <li>Can only generate bill AFTER guest has checked out</li>
                            <li>Payment method must be selected</li>
                            <li>Bill number format: BILL{timestamp}</li>
                            <li>PDF receipt includes all necessary tax information</li>
                        </ul>
                    </div>

                    <div class="tip-box">
                        <i class="fas fa-lightbulb"></i>
                        <strong>Best Practices:</strong>
                        <ul>
                            <li>Always double-check the total amount before generating bill</li>
                            <li>Print receipt immediately after billing</li>
                            <li>Keep record of bill number for reference</li>
                            <li>Verify payment method is correct</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- MODULE 6: ROOM MANAGEMENT -->
            <div class="help-section" id="rooms" data-keywords="room status available booked maintenance floor view type">
                <div class="section-header" onclick="toggleSection('rooms')">
                    <div class="header-left">
                        <i class="fas fa-door-open module-icon"></i>
                        <h2>Room Management</h2>
                    </div>
                    <i class="fas fa-chevron-down toggle-icon"></i>
                </div>
                
                <div class="section-content">
                    <div class="info-box">
                        <i class="fas fa-info-circle"></i>
                        <p>View all rooms and manage their availability status. Monitor room conditions and update status as needed.</p>
                    </div>

                    <h3><i class="fas fa-eye"></i> Room Information Display</h3>
                    <p>Each room shows the following details:</p>
                    <div class="feature-grid">
                        <div class="feature-item">
                            <i class="fas fa-hashtag"></i>
                            <strong>Room Number</strong>
                            <span>Unique identifier</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-bed"></i>
                            <strong>Room Type</strong>
                            <span>Single, Double, Suite, etc.</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-money-bill-wave"></i>
                            <strong>Rate per Night</strong>
                            <span>In LKR</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-info-circle"></i>
                            <strong>Status</strong>
                            <span>Current availability</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-users"></i>
                            <strong>Capacity</strong>
                            <span>Maximum guests</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-building"></i>
                            <strong>Floor Number</strong>
                            <span>Location in hotel</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-eye"></i>
                            <strong>View Type</strong>
                            <span>Ocean, Garden, City</span>
                        </div>
                    </div>

                    <h3><i class="fas fa-search"></i> Search & Filter Rooms</h3>
                    
                    <div class="instruction-box">
                        <strong>Search:</strong>
                        <ul>
                            <li>Type in the search box to find rooms by number, type, etc.</li>
                            <li>Search works on ALL rooms (not just current page)</li>
                            <li>Results update in real-time</li>
                        </ul>

                        <strong>Filter by Type:</strong>
                        <ul>
                            <li>All Types</li>
                            <li>Single Room</li>
                            <li>Double Room</li>
                            <li>Suite</li>
                            <li>Luxury Suite</li>
                            <li>Deluxe Room</li>
                            <li>Presidential Suite</li>
                        </ul>

                        <strong>Filter by Status:</strong>
                        <ul>
                            <li>All Status</li>
                            <li>Available</li>
                            <li>Booked</li>
                            <li>Maintenance</li>
                        </ul>

                        <strong>Sort Options:</strong>
                        <ul>
                            <li>Sort by Room No.</li>
                            <li>Sort by Type</li>
                            <li>Sort by Rate</li>
                            <li>Sort by Status</li>
                        </ul>
                    </div>

                    <h3><i class="fas fa-sync"></i> Change Room Status</h3>
                    
                    <div class="process-step">
                        <div class="step-number">🔄</div>
                        <div class="step-content">
                            <h4>How to Change Status</h4>
                            <ol class="step-list">
                                <li>Find the room you want to update</li>
                                <li>Click the "Change Status" button (🔄 icon)</li>
                                <li>Select new status from dropdown:
                                    <ul>
                                        <li>Available</li>
                                        <li>Maintenance</li>
                                    </ul>
                                </li>
                                <li>Click "Update Status"</li>
                                <li>Status changes immediately</li>
                            </ol>
                        </div>
                    </div>

                    <h3><i class="fas fa-traffic-light"></i> Room Status Guide</h3>
                    <div class="status-guide">
                        <div class="status-item">
                            <span class="room-status available">Available</span>
                            <span>Room is clean, ready, and can be booked</span>
                        </div>
                        <div class="status-item">
                            <span class="room-status booked">Booked</span>
                            <span>Room has active reservation (guest is staying)</span>
                        </div>
                        <div class="status-item">
                            <span class="room-status maintenance">Maintenance</span>
                            <span>Room is under repair or deep cleaning</span>
                        </div>
                    </div>

                    <h3><i class="fas fa-ban"></i> Status Change Restrictions</h3>
                    <div class="warning-box">
                        <i class="fas fa-exclamation-triangle"></i>
                        <strong>Important Rules:</strong>
                        <ul>
                            <li><strong>Cannot change status if room is Booked</strong>
                                <ul>
                                    <li>Booked rooms have active reservations</li>
                                    <li>Status button is disabled</li>
                                    <li>Message: "Cannot change status - Room is booked"</li>
                                    <li>Must wait until guest checks out</li>
                                </ul>
                            </li>
                            <li><strong>Can only toggle between:</strong>
                                <ul>
                                    <li>Available ↔ Maintenance</li>
                                    <li>Cannot manually set to "Booked" (system does this automatically)</li>
                                </ul>
                            </li>
                        </ul>
                    </div>

                    <h3><i class="fas fa-chart-bar"></i> Room Statistics</h3>
                    <div class="info-box">
                        <i class="fas fa-info-circle"></i>
                        <p>At the top of the page, view real-time counts:</p>
                        <ul>
                            <li><strong>Available:</strong> Rooms ready for booking</li>
                            <li><strong>Occupied:</strong> Rooms currently booked</li>
                            <li><strong>Maintenance:</strong> Rooms under repair</li>
                        </ul>
                    </div>

                    <div class="tip-box">
                        <i class="fas fa-lightbulb"></i>
                        <strong>Best Practices:</strong>
                        <ul>
                            <li>Set room to "Maintenance" immediately when repairs are needed</li>
                            <li>Change back to "Available" only when room is fully ready</li>
                            <li>Use filters to quickly find rooms needing attention</li>
                            <li>Check maintenance rooms daily</li>
                            <li>Table shows 10 rooms per page with full pagination</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- MODULE 7: GUEST MANAGEMENT -->
            <div class="help-section" id="guests" data-keywords="guest edit update contact email address phone">
                <div class="section-header" onclick="toggleSection('guests')">
                    <div class="header-left">
                        <i class="fas fa-users module-icon"></i>
                        <h2>Guest Management</h2>
                    </div>
                    <i class="fas fa-chevron-down toggle-icon"></i>
                </div>
                
                <div class="section-content">
                    <div class="info-box">
                        <i class="fas fa-info-circle"></i>
                        <p>View all registered guests and update their contact information. Maintain accurate guest records.</p>
                    </div>

                    <h3><i class="fas fa-table"></i> Guest Information Display</h3>
                    <p>View all guests with the following details:</p>
                    <div class="feature-grid">
                        <div class="feature-item">
                            <i class="fas fa-hashtag"></i>
                            <strong>Guest ID</strong>
                            <span>Unique system identifier</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-user"></i>
                            <strong>Full Name</strong>
                            <span>Guest's complete name</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-id-card"></i>
                            <strong>NIC/Passport</strong>
                            <span>Identification number</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-phone"></i>
                            <strong>Contact Number</strong>
                            <span>Phone number</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-envelope"></i>
                            <strong>Email</strong>
                            <span>Email address</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-map-marker-alt"></i>
                            <strong>Address</strong>
                            <span>Residential address</span>
                        </div>
                    </div>

                    <h3><i class="fas fa-search"></i> Search & Sort Guests</h3>
                    <div class="instruction-box">
                        <strong>Search Function:</strong>
                        <ul>
                            <li>Type in search box to find guests by:
                                <ul>
                                    <li>Name</li>
                                    <li>Contact number</li>
                                    <li>Email</li>
                                    <li>NIC/Passport</li>
                                </ul>
                            </li>
                            <li>Search works on ALL guests (not just current page)</li>
                            <li>Results update in real-time</li>
                        </ul>

                        <strong>Sort Options:</strong>
                        <ul>
                            <li>Sort by Name (A-Z)</li>
                            <li>Sort by Email</li>
                            <li>Sort by Contact</li>
                        </ul>
                    </div>

                    <h3><i class="fas fa-edit"></i> Edit Guest Information</h3>
                    
                    <div class="process-step">
                        <div class="step-number">1</div>
                        <div class="step-content">
                            <h4>Open Edit Modal</h4>
                            <ul>
                                <li>Find the guest in the table</li>
                                <li>Click the "Edit" button (✏️ icon)</li>
                                <li>Edit modal opens with current information</li>
                            </ul>
                        </div>
                    </div>

                    <div class="process-step">
                        <div class="step-number">2</div>
                        <div class="step-content">
                            <h4>Review Fields</h4>
                            <div class="field-types">
                                <div class="read-only-fields">
                                    <strong>🔒 Read-Only Fields (Cannot Edit):</strong>
                                    <ul>
                                        <li>Guest Name - Permanent</li>
                                        <li>NIC/Passport - Permanent</li>
                                    </ul>
                                </div>
                                <div class="editable-fields">
                                    <strong>✏️ Editable Fields:</strong>
                                    <ul>
                                        <li>Contact Number - Can update</li>
                                        <li>Email Address - Can update</li>
                                        <li>Address - Can update</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="process-step">
                        <div class="step-number">3</div>
                        <div class="step-content">
                            <h4>Update Information</h4>
                            <ul>
                                <li>Modify contact number, email, or address</li>
                                <li>Click "Update Guest" button</li>
                                <li>System validates information</li>
                                <li>Changes save immediately</li>
                                <li>Success message appears</li>
                            </ul>
                        </div>
                    </div>

                    <h3><i class="fas fa-phone"></i> Phone Number Format</h3>
                    <div class="info-box">
                        <i class="fas fa-info-circle"></i>
                        <strong>How Phone Numbers Work:</strong>
                        <ul>
                            <li><strong>When You Enter:</strong> 0771234567 (10 digits with leading 0)</li>
                            <li><strong>System Stores:</strong> 771234567 (9 digits without 0)</li>
                            <li><strong>System Displays:</strong> 0771234567 (10 digits with 0)</li>
                            <li><strong>Why?</strong> Sri Lankan mobile numbers start with 0, but databases typically store without the leading 0</li>
                            <li><strong>You Don't Need to Worry:</strong> System handles conversion automatically</li>
                        </ul>
                    </div>

                    <h3><i class="fas fa-shield-alt"></i> Validation Rules</h3>
                    <div class="validation-box">
                        <div class="validation-item">
                            <i class="fas fa-check-circle"></i>
                            <strong>Contact Number:</strong> Must be 9 or 10 digits
                        </div>
                        <div class="validation-item">
                            <i class="fas fa-check-circle"></i>
                            <strong>Email:</strong> Must be valid email format (name@domain.com)
                        </div>
                        <div class="validation-item">
                            <i class="fas fa-check-circle"></i>
                            <strong>Address:</strong> Minimum 5 characters
                        </div>
                        <div class="validation-item">
                            <i class="fas fa-check-circle"></i>
                            <strong>Duplicates:</strong> Contact and email must be unique
                        </div>
                    </div>

                    <h3><i class="fas fa-exclamation-triangle"></i> Common Errors & Solutions</h3>
                    <div class="error-solutions">
                        <div class="error-item">
                            <div class="error-msg">❌ "Contact number already exists for another guest"</div>
                            <div class="solution">✅ Another guest has this number. Use a different contact number.</div>
                        </div>
                        <div class="error-item">
                            <div class="error-msg">❌ "Email address already exists for another guest"</div>
                            <div class="solution">✅ Another guest has this email. Use a different email address.</div>
                        </div>
                        <div class="error-item">
                            <div class="error-msg">❌ "Contact number must be 9 or 10 digits"</div>
                            <div class="solution">✅ Enter a valid phone number with 10 digits (e.g., 0771234567)</div>
                        </div>
                        <div class="error-item">
                            <div class="error-msg">❌ "Please enter a valid email address"</div>
                            <div class="solution">✅ Check email format. Must include @ and domain (e.g., guest@email.com)</div>
                        </div>
                    </div>

                    <div class="warning-box">
                        <i class="fas fa-exclamation-circle"></i>
                        <strong>Important Notes:</strong>
                        <ul>
                            <li>Name and NIC/Passport <strong>cannot</strong> be changed after guest registration</li>
                            <li>If guest information is completely wrong, contact system administrator</li>
                            <li>Always verify information with guest before updating</li>
                            <li>System checks for duplicate contact/email across all guests</li>
                        </ul>
                    </div>

                    <div class="tip-box">
                        <i class="fas fa-lightbulb"></i>
                        <strong>Best Practices:</strong>
                        <ul>
                            <li>Keep guest information up-to-date</li>
                            <li>Verify phone numbers before saving</li>
                            <li>Update addresses when guests inform you of changes</li>
                            <li>Use search to quickly find guests</li>
                            <li>Table shows 10 guests per page with pagination</li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>

        <!-- Back to Top Button -->
        <button class="back-to-top" id="backToTop" onclick="scrollToTop()">
            <i class="fas fa-arrow-up"></i>
        </button>
    </div>

    <script src="js/help.js"></script>
</body>
</html>