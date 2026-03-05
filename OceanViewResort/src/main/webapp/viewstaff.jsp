<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Staff - Ocean View Resort</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="adminstyle.css">
    <link rel="stylesheet" type="text/css" href="css/viewstaff.css">
</head>
<body>
    <%@ include file="adminheader.jsp" %>
    <%@ include file="adminsidebar.jsp" %>

    <div class="main-content">
        <div class="dashboard-content">

            <!-- Page Header -->
            <section class="page-header">
                <div class="page-header-left">
                    <h2><i class="fas fa-users"></i> Staff Members</h2>
                    <p>View and manage all registered staff members of Ocean View Resort.</p>
                </div>
                <div class="breadcrumb">
                    <a href="admindashboard.jsp"><i class="fas fa-home"></i> Dashboard</a>
                    <span><i class="fas fa-chevron-right"></i></span>
                    <span class="active">View Staff</span>
                </div>
            </section>

            <!-- Success/Error Messages -->
            <div id="alertArea" style="display: none;"></div>

            <!-- Stats Bar -->
            <section class="stats-bar">
                <div class="stat-card">
                    <div class="stat-icon">
                        <i class="fas fa-users"></i>
                    </div>
                    <div class="stat-info">
                        <span class="stat-number" id="totalStaff">0</span>
                        <span class="stat-label">Total Staff</span>
                    </div>
                </div>
            </section>

            <!-- Table Card -->
            <section class="table-card">
                <!-- Table Header -->
                <div class="table-card-header">
                    <div class="table-card-title">
                        <div class="table-card-icon">
                            <i class="fas fa-list"></i>
                        </div>
                        <div>
                            <h3>All Staff Members</h3>
                            <p>Manage registered staff accounts</p>
                        </div>
                    </div>

                    <!-- Search & Actions -->
                    <div class="table-actions">
                        <div class="search-box">
                            <i class="fas fa-search search-icon"></i>
                            <input type="text" id="searchInput" 
                                   placeholder="Search by name or username..." 
                                   oninput="filterStaff()">
                            <button class="search-clear" id="clearSearch" 
                                    onclick="clearSearchInput()" style="display: none;">
                                <i class="fas fa-times"></i>
                            </button>
                        </div>
                        <a href="addstaff.jsp" class="btn btn-primary">
                            <i class="fas fa-user-plus"></i> Add Staff
                        </a>
                        <button class="btn btn-refresh" onclick="location.reload()" title="Refresh">
                            <i class="fas fa-sync-alt"></i>
                        </button>
                    </div>
                </div>

                <!-- Table -->
                <div class="table-responsive">
                    <table class="staff-table" id="staffTable">
                        <thead>
                            <tr>
                                <th><i class="fas fa-hashtag"></i> ID</th>
                                <th><i class="fas fa-at"></i> Username</th>
                                <th><i class="fas fa-user"></i> Full Name</th>
                                <th><i class="fas fa-phone"></i> Contact No</th>
                                <th><i class="fas fa-cog"></i> Action</th>
                            </tr>
                        </thead>
                        <tbody id="staffTableBody">
                            <!-- Populated by JavaScript -->
                        </tbody>
                    </table>

                    <!-- Empty State -->
                    <div class="empty-state" id="emptyState" style="display: none;">
                        <i class="fas fa-users-slash"></i>
                        <h3>No Staff Found</h3>
                        <p>No staff members match your search criteria.</p>
                    </div>

                    <!-- Loading State -->
                    <div class="loading-state" id="loadingState">
                        <div class="spinner"></div>
                        <p>Loading staff members...</p>
                    </div>
                </div>

                <!-- Table Footer -->
                <div class="table-footer">
                    <span id="showingCount">Showing 0 staff members</span>
                </div>
            </section>

        </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div class="modal-overlay" id="deleteModal" style="display: none;">
        <div class="modal-box">
            <div class="modal-icon">
                <i class="fas fa-exclamation-triangle"></i>
            </div>
            <h3>Remove Staff Member?</h3>
            <p>You are about to remove <strong id="deleteStaffName"></strong>.</p>
            <p class="modal-warning">This action cannot be undone!</p>
            <input type="hidden" id="deleteStaffId">
            <div class="modal-actions">
                <button class="btn btn-secondary" onclick="closeDeleteModal()">
                    <i class="fas fa-times"></i> Cancel
                </button>
                <button class="btn btn-danger" onclick="confirmDelete()">
                    <i class="fas fa-trash"></i> Yes, Remove
                </button>
            </div>
        </div>
    </div>

    <script src="js/viewstaff.js"></script>
</body>
</html>