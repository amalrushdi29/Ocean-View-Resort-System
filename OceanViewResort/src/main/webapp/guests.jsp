<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="OceanDAO.GuestDao" %>
<%@ page import="OceanModel.GuestBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ocean View Resort - Guest Management</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/guests.css">
<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<!-- Font Awesome for icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <%-- Include header and sidebar --%>
    <jsp:include page="header.jsp">
        <jsp:param name="pageTitle" value="Guest Management"/>
    </jsp:include>
    <jsp:include page="sidebar.jsp"/> 

    <div class="main-content">
        <!-- Success/Error Messages -->
        <% 
            String successMessage = (String) session.getAttribute("successMessage");
            String errorMessage = (String) session.getAttribute("errorMessage");
            
            if (successMessage != null) {
                session.removeAttribute("successMessage");
        %>
            <script>
                document.addEventListener('DOMContentLoaded', function() {
                    showNotification('<%= successMessage %>', 'success');
                });
            </script>
        <% } %>
        
        <% if (errorMessage != null) { 
            session.removeAttribute("errorMessage");
        %>
            <script>
                document.addEventListener('DOMContentLoaded', function() {
                    showNotification('<%= errorMessage %>', 'error');
                });
            </script>
        <% } %>

        <!-- Welcome Section -->
        <div class="welcome-section">
            <div class="welcome-content">
                <h1><i class="fas fa-users"></i> Guest Management</h1>
                <p class="welcome-subtitle">View and manage guest information</p>
            </div>
            <div class="welcome-graphic">
                <i class="fas fa-address-book"></i>
            </div>
        </div>

        <!-- All Guests Section -->
        <div class="all-guests-section">
            <div class="section-header">
                <div>
                    <h2><i class="fas fa-list"></i> All Guests</h2>
                </div>
                <div class="header-actions">
                    <div class="search-box">
                        <i class="fas fa-search"></i>
                        <input type="text" id="searchGuests" placeholder="Search guests...">
                    </div>
                    <button class="btn-icon" title="Refresh" onclick="location.reload()">
                        <i class="fas fa-sync-alt"></i>
                    </button>
                </div>
            </div>

            <!-- Filters -->
            <div class="filters-section">
                <div class="filter-group">
                    <label><i class="fas fa-filter"></i> Filter by:</label>
                    <select id="sortBy" class="filter-select">
                        <option value="name">Sort by Name</option>
                        <option value="email">Sort by Email</option>
                        <option value="contact">Sort by Contact</option>
                    </select>
                </div>
            </div>

            <!-- Guests Table -->
            <div class="table-container">
                <table class="guests-table" id="guestsTable">
                    <thead>
                        <tr>
                            <th><i class="fas fa-hashtag"></i> Guest ID</th>
                            <th><i class="fas fa-user"></i> Name</th>
                            <th><i class="fas fa-id-card"></i> NIC/Passport</th>
                            <th><i class="fas fa-phone"></i> Contact</th>
                            <th><i class="fas fa-envelope"></i> Email</th>
                            <th><i class="fas fa-map-marker-alt"></i> Address</th>
                            <th><i class="fas fa-cog"></i> Actions</th>
                        </tr>
                    </thead>
                    <tbody id="guestsTableBody">
                        <%
                            try {
                                // Get all guests using DAO
                                GuestDao guestDao = new GuestDao();
                                List<GuestBean> allGuests = guestDao.searchGuests("");
                                
                                // Display ALL guests (pagination handled by JavaScript)
                                if (allGuests.isEmpty()) {
                        %>
                                    <tr>
                                        <td colspan="7" style="text-align: center; padding: 3rem; color: #718096;">
                                            <i class="fas fa-inbox" style="font-size: 3rem; margin-bottom: 1rem; display: block;"></i>
                                            <p style="font-size: 1.1rem; margin: 0;">No guests found in the system.</p>
                                        </td>
                                    </tr>
                        <%
                                } else {
                                    for (GuestBean guest : allGuests) {
                                        int guestId = guest.getGuestId();
                                        String name = guest.getName();
                                        String nicPpt = guest.getNicPpt();
                                        String contactNo = guest.getContactNo();
                                        String email = guest.getEmail();
                                        String address = guest.getAddress();
                        %>
                        <tr data-guest-id="<%= guestId %>">
                            <td><strong><%= guestId %></strong></td>
                            <td><%= (name != null && !name.isEmpty()) ? name : "-" %></td>
                            <td><%= (nicPpt != null && !nicPpt.isEmpty()) ? nicPpt : "-" %></td>
                            <td><%= (contactNo != null && !contactNo.isEmpty()) ? contactNo : "-" %></td>
                            <td><%= (email != null && !email.isEmpty()) ? email : "-" %></td>
                            <td><%= (address != null && !address.isEmpty()) ? address : "-" %></td>
                            <td class="actions-cell">
                                <button class="btn-action btn-edit" title="Edit Guest" 
                                        onclick='openEditModal(<%= guestId %>, "<%= name %>", "<%= nicPpt %>", "<%= contactNo %>", "<%= email %>", "<%= address != null ? address.replace("\"", "&quot;").replace("\n", " ").replace("\r", " ") : "" %>")'>
                                    <i class="fas fa-edit"></i>
                                </button>
                            </td>
                        </tr>
                        <%
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                        %>
                                <tr>
                                    <td colspan="7" style="text-align: center; color: #ef4444; padding: 2rem;">
                                        <i class="fas fa-exclamation-triangle"></i> Error loading guests: <%= e.getMessage() %>
                                    </td>
                                </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
            
            <!-- Pagination (Handled by JavaScript) -->
            <div class="pagination" style="display: none;">
                <div class="pagination-info"></div>
                <div class="page-numbers"></div>
            </div>
        </div>
    </div>

    <!-- Edit Guest Modal -->
    <div id="editModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2><i class="fas fa-user-edit"></i> Edit Guest Information</h2>
                <button class="modal-close" onclick="closeEditModal()">&times;</button>
            </div>
            <form id="editForm" class="guest-form" action="UpdateGuest" method="post">
                <input type="hidden" id="editGuestId" name="guestId">
                
                <div class="form-grid">
                    <!-- Name (Read-only) -->
                    <div class="form-group">
                        <label for="editName">
                            <i class="fas fa-user"></i> Guest Name
                        </label>
                        <input type="text" id="editName" name="name" readonly>
                    </div>

                    <!-- NIC/Passport (Read-only) -->
                    <div class="form-group">
                        <label for="editNicPpt">
                            <i class="fas fa-id-card"></i> NIC/Passport
                        </label>
                        <input type="text" id="editNicPpt" name="nicPpt" readonly>
                    </div>

                    <!-- Contact (Editable) -->
                    <div class="form-group">
                        <label for="editContact">
                            <i class="fas fa-phone"></i> Contact Number <span class="required">*</span>
                        </label>
                        <input type="tel" id="editContact" name="contactNo" required 
       							maxlength="10" 
       							placeholder="0771234567">
                        <span class="field-hint">10-digit phone number</span>
                    </div>

                    <!-- Email (Editable) -->
                    <div class="form-group">
                        <label for="editEmail">
                            <i class="fas fa-envelope"></i> Email Address <span class="required">*</span>
                        </label>
                        <input type="email" id="editEmail" name="email" required 
                               placeholder="guest@example.com">
                    </div>
                </div>

                <!-- Address (Editable, Full Width) -->
                <div class="form-group">
                    <label for="editAddress">
                        <i class="fas fa-map-marker-alt"></i> Address <span class="required">*</span>
                    </label>
                    <textarea id="editAddress" name="address" rows="3" required 
                              placeholder="Enter full address"></textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> Update Guest
                    </button>
                    <button type="button" class="btn btn-secondary" onclick="closeEditModal()">
                        <i class="fas fa-times"></i> Cancel
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script src="js/guests.js"></script>
</body>
</html>