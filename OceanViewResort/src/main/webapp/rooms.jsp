<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="OceanDAO.RoomDao" %>
<%@ page import="OceanModel.RoomBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ocean View Resort - Room Management</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/rooms.css">
<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<!-- Font Awesome for icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <!-- Include Header -->
    <%@ include file="adminheader.jsp" %>
    
    <!-- Include Sidebar -->
    <%@ include file="adminsidebar.jsp" %>

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

        <!-- Page Header -->
        <div class="page-header">
            <div class="header-content">
                <h1><i class="fas fa-bed"></i> Room Management</h1>
                <p class="header-subtitle">Manage hotel rooms, rates, and availability</p>
            </div>
            <div class="header-stats">
                <%
                    RoomDao roomDao = new RoomDao();
                    int[] stats = roomDao.getRoomStatistics();
                    int availableCount = stats[0];
                    int bookedCount = stats[1];
                    int maintenanceCount = stats[2];
                %>
                <div class="stat-item available">
                    <i class="fas fa-door-open"></i>
                    <div>
                        <span class="stat-number"><%= availableCount %></span>
                        <span class="stat-label">Available</span>
                    </div>
                </div>
                <div class="stat-item occupied">
                    <i class="fas fa-door-closed"></i>
                    <div>
                        <span class="stat-number"><%= bookedCount %></span>
                        <span class="stat-label">Occupied</span>
                    </div>
                </div>
                <div class="stat-item maintenance">
                    <i class="fas fa-tools"></i>
                    <div>
                        <span class="stat-number"><%= maintenanceCount %></span>
                        <span class="stat-label">Maintenance</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- Add Room Section -->
        <div class="add-room-section">
            <div class="section-header">
                <div>
                    <h2><i class="fas fa-plus-circle"></i> Add New Room</h2>
                    <p>Fill in the details to add a new room to the system</p>
                </div>
            </div>
            
            <form id="addRoomForm" class="room-form" action="AddRooms" method="post">
                <div class="form-grid">
                    <div class="form-group">
                        <label for="roomNumber">
                            <i class="fas fa-hashtag"></i> Room Number
                            <span class="required">*</span>
                        </label>
                        <input type="text" id="roomNumber" name="roomNumber" 
                               placeholder="e.g., 101 - 1010" required>
                        <span class="field-hint">Enter unique room identifier</span>
                    </div>

                    <div class="form-group">
                        <label for="roomType">
                            <i class="fas fa-layer-group"></i> Room Type
                            <span class="required">*</span>
                        </label>
                        <select id="roomType" name="roomType" required>
                            <option value="">Select Room Type</option>
                            <option value="Single">Single Room</option>
                            <option value="Double">Double Room</option>
                            <option value="Suite">Suite</option>
                            <option value="Luxury">Luxury Suite</option>
                            <option value="Deluxe">Deluxe Room</option>
                            <option value="Presidential">Presidential Suite</option>
                        </select>
                        <span class="field-hint">Choose the room category</span>
                    </div>

                    <div class="form-group">
                        <label for="ratePerNight">
                            <i class="fas fa-money-bill-wave"></i> Rate per Night
                            <span class="required">*</span>
                        </label>
                        <div class="input-with-icon">
                            <span class="input-icon">LKR</span>
                            <input type="number" id="ratePerNight" name="ratePerNight" 
                                   placeholder="0.00" step="0.01" min="0" required>
                        </div>
                        <span class="field-hint">Price per night in LKR</span>
                    </div>

                    <div class="form-group">
                        <label for="maxCapacity">
                            <i class="fas fa-users"></i> Max Capacity
                        </label>
                        <input type="number" id="maxCapacity" name="maxCapacity" 
                               placeholder="e.g., 2" min="1" max="10">
                        <span class="field-hint">Maximum number of guests</span>
                    </div>

                    <div class="form-group">
                        <label for="floorNumber">
                            <i class="fas fa-building"></i> Floor Number
                        </label>
                        <input type="number" id="floorNumber" name="floorNumber" 
                               placeholder="e.g., 1" min="1" max="10">
                        <span class="field-hint">Floor location (1-10)</span>
                    </div>

                    <div class="form-group">
                        <label for="view">
                            <i class="fas fa-eye"></i> View
                        </label>
                        <select id="view" name="view">
                            <option value="">Select View</option>
                            <option value="Ocean View">Ocean View</option>
                            <option value="City View">City View</option>
                            <option value="Garden View">Garden View</option>
                            <option value="Pool View">Pool View</option>
                            <option value="Mountain View">Mountain View</option>
                        </select>
                        <span class="field-hint">Room view type</span>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-plus-circle"></i> Add Room
                    </button>
                    <button type="reset" class="btn btn-secondary">
                        <i class="fas fa-eraser"></i> Clear Form
                    </button>
                </div>
            </form>
        </div>

        <!-- All Rooms Section -->
        <div class="all-rooms-section">
            <div class="section-header">
                <div>
                    <h2><i class="fas fa-list"></i> All Rooms</h2>
                </div>
                <div class="header-actions">
                    <div class="search-box">
                        <i class="fas fa-search"></i>
                        <input type="text" id="searchRooms" placeholder="Search rooms...">
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
                    <select id="filterType" class="filter-select">
                        <option value="">All Types</option>
                        <option value="Single">Single Room</option>
                        <option value="Double">Double Room</option>
                        <option value="Suite">Suite</option>
                        <option value="Luxury">Luxury Suite</option>
                        <option value="Deluxe">Deluxe Room</option>
                        <option value="Presidential">Presidential Suite</option>
                    </select>
                </div>
                <div class="filter-group">
                    <select id="filterStatus" class="filter-select">
                        <option value="">All Status</option>
                        <option value="Available">Available</option>
                        <option value="Booked">Booked</option>
                        <option value="Maintenance">Maintenance</option>
                    </select>
                </div>
                <div class="filter-group">
                    <select id="sortBy" class="filter-select">
                        <option value="room">Sort by Room No.</option>
                        <option value="type">Sort by Type</option>
                        <option value="rate">Sort by Rate</option>
                        <option value="status">Sort by Status</option>
                    </select>
                </div>
            </div>

            <!-- Rooms Table -->
            <div class="table-container">
                <table class="rooms-table" id="roomsTable">
                    <thead>
                        <tr>
                            <th><i class="fas fa-hashtag"></i> Room No.</th>
                            <th><i class="fas fa-layer-group"></i> Type</th>
                            <th><i class="fas fa-money-bill-wave"></i> Rate/Night (LKR)</th>
                            <th><i class="fas fa-info-circle"></i> Status</th>
                            <th><i class="fas fa-users"></i> Capacity</th>
                            <th><i class="fas fa-building"></i> Floor</th>
                            <th><i class="fas fa-eye"></i> View</th>
                            <th><i class="fas fa-cog"></i> Actions</th>
                        </tr>
                    </thead>
                    <tbody id="roomsTableBody">
                        <%
                            try {
                                // Get ALL rooms — no server-side pagination
                                List<RoomBean> allRooms = roomDao.getAllRooms();
                                
                                if (allRooms.isEmpty()) {
                        %>
                                    <tr class="empty-row">
                                        <td colspan="8" style="text-align: center; padding: 3rem; color: #718096;">
                                            <i class="fas fa-inbox" style="font-size: 3rem; margin-bottom: 1rem; display: block;"></i>
                                            <p style="font-size: 1.1rem; margin: 0;">No rooms found. Add your first room to get started!</p>
                                        </td>
                                    </tr>
                        <%
                                } else {
                                    for (RoomBean room : allRooms) {
                                        int roomId = room.getRoomId();
                                        String roomNo = room.getRoomNo();
                                        String roomType = room.getRoomType();
                                        String ratePerNight = room.getRatePerNight();
                                        String status = room.getStatus();
                                        if (status == null || status.isEmpty()) status = "Available";
                                        String maxCapacity = room.getMaxCapacity();
                                        String floorNo = room.getFloorNo();
                                        String viewType = room.getView();
                                        
                                        boolean isBooked = roomDao.isRoomBooked(roomId);
                                        
                                        String statusClass = "";
                                        String statusIcon = "";
                                        if ("Available".equals(status)) {
                                            statusClass = "status-available";
                                            statusIcon = "fa-check-circle";
                                        } else if ("Booked".equals(status)) {
                                            statusClass = "status-booked";
                                            statusIcon = "fa-door-closed";
                                        } else if ("Maintenance".equals(status)) {
                                            statusClass = "status-maintenance";
                                            statusIcon = "fa-tools";
                                        }
                                        
                                        double rate = 0;
                                        try {
                                            rate = Double.parseDouble(ratePerNight);
                                        } catch (Exception e) {
                                            rate = 0;
                                        }
                                        String formattedRate = String.format("%,.2f", rate);
                        %>
                        <tr data-room-id="<%= roomId %>" 
                            data-status="<%= status.toLowerCase() %>" 
                            data-room-type="<%= roomType %>"
                            data-is-booked="<%= isBooked %>">
                            <td><strong><%= roomNo %></strong></td>
                            <td><span class="badge badge-type"><%= roomType %></span></td>
                            <td>LKR <%= formattedRate %></td>
                            <td><span class="status-badge <%= statusClass %>"><i class="fas <%= statusIcon %>"></i> <%= status %></span></td>
                            <td><%= (maxCapacity != null && !maxCapacity.isEmpty()) ? maxCapacity : "-" %></td>
                            <td><%= (floorNo != null && !floorNo.isEmpty()) ? floorNo : "-" %></td>
                            <td><%= (viewType != null && !viewType.isEmpty()) ? viewType : "-" %></td>
                            <td class="actions-cell">
                                <% if (isBooked) { %>
                                    <button class="btn-action btn-edit" title="Cannot edit - Room is booked" disabled style="opacity: 0.5; cursor: not-allowed;">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                    <button class="btn-action btn-status" title="Cannot change status - Room is booked" disabled style="opacity: 0.5; cursor: not-allowed;">
                                        <i class="fas fa-sync"></i>
                                    </button>
                                <% } else { %>
                                    <button class="btn-action btn-edit" title="Edit Room" onclick="openEditModal(this)">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                    <button class="btn-action btn-status" title="Change Status" onclick="openStatusModal(this)">
                                        <i class="fas fa-sync"></i>
                                    </button>
                                <% } %>
                            </td>
                        </tr>
                        <%
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                        %>
                                <tr class="empty-row">
                                    <td colspan="8" style="text-align: center; color: #ef4444; padding: 2rem;">
                                        <i class="fas fa-exclamation-triangle"></i> Error loading rooms: <%= e.getMessage() %>
                                    </td>
                                </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <!-- Pagination — fully JS-driven, no page reload -->
            <div class="pagination" id="paginationContainer">
                <div class="pagination-info" id="paginationInfo"></div>
                <div class="page-numbers" id="pageNumbers"></div>
            </div>
        </div>
    </div>

    <!-- Edit Room Modal -->
    <div id="editRoomModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2><i class="fas fa-edit"></i> Edit Room</h2>
                <button class="modal-close" onclick="closeEditModal()">&times;</button>
            </div>
            <form id="editRoomForm" class="room-form" action="EditRoom" method="post">
                <input type="hidden" id="editRoomId" name="roomId">
                
                <div class="form-grid">
                    <div class="form-group">
                        <label for="editRoomNumber">
                            <i class="fas fa-hashtag"></i> Room Number
                            <span class="required">*</span>
                        </label>
                        <input type="text" id="editRoomNumber" name="roomNumber" required>
                    </div>

                    <div class="form-group">
                        <label for="editRoomType">
                            <i class="fas fa-layer-group"></i> Room Type
                            <span class="required">*</span>
                        </label>
                        <select id="editRoomType" name="roomType" required>
                            <option value="Single">Single Room</option>
                            <option value="Double">Double Room</option>
                            <option value="Suite">Suite</option>
                            <option value="Luxury">Luxury Suite</option>
                            <option value="Deluxe">Deluxe Room</option>
                            <option value="Presidential">Presidential Suite</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="editRatePerNight">
                            <i class="fas fa-money-bill-wave"></i> Rate per Night
                            <span class="required">*</span>
                        </label>
                        <div class="input-with-icon">
                            <span class="input-icon">LKR</span>
                            <input type="number" id="editRatePerNight" name="ratePerNight" 
                                   step="0.01" min="0" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editMaxCapacity">
                            <i class="fas fa-users"></i> Max Capacity
                        </label>
                        <input type="number" id="editMaxCapacity" name="maxCapacity" min="1" max="10">
                    </div>

                    <div class="form-group">
                        <label for="editFloorNumber">
                            <i class="fas fa-building"></i> Floor Number
                        </label>
                        <input type="number" id="editFloorNumber" name="floorNumber" min="1" max="10">
                    </div>

                    <div class="form-group">
                        <label for="editView">
                            <i class="fas fa-eye"></i> View
                        </label>
                        <select id="editView" name="view">
                            <option value="">Select View</option>
                            <option value="Ocean View">Ocean View</option>
                            <option value="City View">City View</option>
                            <option value="Garden View">Garden View</option>
                            <option value="Pool View">Pool View</option>
                            <option value="Mountain View">Mountain View</option>
                        </select>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> Update Room
                    </button>
                    <button type="button" class="btn btn-secondary" onclick="closeEditModal()">
                        <i class="fas fa-times"></i> Cancel
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Change Status Modal -->
    <div id="statusModal" class="modal">
        <div class="modal-content modal-small">
            <div class="modal-header">
                <h2><i class="fas fa-sync"></i> Change Room Status</h2>
                <button class="modal-close" onclick="closeStatusModal()">&times;</button>
            </div>
            <form id="statusForm" class="room-form" action="UpdateRoomStatus" method="post">
                <input type="hidden" id="statusRoomId" name="roomId">
                
                <div class="form-group">
                    <label for="newStatus">
                        <i class="fas fa-info-circle"></i> Select New Status
                    </label>
                    <select id="newStatus" name="status" required>
                        <option value="Available">Available</option>
                        <option value="Maintenance">Maintenance</option>
                    </select>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-check"></i> Update Status
                    </button>
                    <button type="button" class="btn btn-secondary" onclick="closeStatusModal()">
                        <i class="fas fa-times"></i> Cancel
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- JavaScript -->
    <script>
        // ============================================
        // GLOBAL STATE
        // ============================================
        const ROWS_PER_PAGE = 10;
        let currentPage = 1;
        let activeRows = [];

        // Cache all data rows once (excludes empty/error placeholder rows)
        const allDataRows = Array.from(
            document.querySelectorAll('#roomsTableBody tr:not(.empty-row)')
        );

        // ============================================
        // INITIALIZE ON PAGE LOAD
        // ============================================
        document.addEventListener('DOMContentLoaded', function() {
            initializeSearch();
            initializeFilters();
            initializeModals();
            rebuildView();
        });

        // ============================================
        // CORE: rebuild active rows → paginate → render
        // ============================================
        function rebuildView(resetPage) {
            if (resetPage) currentPage = 1;

            const searchTerm   = document.getElementById('searchRooms').value.toLowerCase();
            const filterType   = document.getElementById('filterType').value;
            const filterStatus = document.getElementById('filterStatus').value;
            const sortValue    = document.getElementById('sortBy').value;

            // 1. Filter
            activeRows = allDataRows.filter(function(row) {
                const text = row.textContent.toLowerCase();
                if (searchTerm && !text.includes(searchTerm)) return false;

                const typeText   = row.querySelector('td:nth-child(2)').textContent.trim();
                const statusText = row.querySelector('td:nth-child(4)').textContent.trim().split(' ').pop();

                if (filterType   && typeText   !== filterType)   return false;
                if (filterStatus && statusText !== filterStatus) return false;

                return true;
            });

            // 2. Sort
            activeRows.sort(function(a, b) {
                switch(sortValue) {
                    case 'room':
                        return a.querySelector('td:nth-child(1)').textContent.trim()
                                .localeCompare(b.querySelector('td:nth-child(1)').textContent.trim(), undefined, {numeric: true});
                    case 'type':
                        return a.querySelector('td:nth-child(2)').textContent.trim()
                                .localeCompare(b.querySelector('td:nth-child(2)').textContent.trim());
                    case 'rate':
                        return parseFloat(a.querySelector('td:nth-child(3)').textContent.replace(/LKR |,/g, ''))
                             - parseFloat(b.querySelector('td:nth-child(3)').textContent.replace(/LKR |,/g, ''));
                    case 'status':
                        return a.querySelector('td:nth-child(4)').textContent.trim()
                                .localeCompare(b.querySelector('td:nth-child(4)').textContent.trim());
                    default:
                        return 0;
                }
            });

            // 3. Clamp currentPage
            const totalPages = Math.max(1, Math.ceil(activeRows.length / ROWS_PER_PAGE));
            if (currentPage > totalPages) currentPage = totalPages;

            // 4. Show / hide rows
            const startIdx = (currentPage - 1) * ROWS_PER_PAGE;
            const endIdx   = startIdx + ROWS_PER_PAGE;

            allDataRows.forEach(function(row) { row.style.display = 'none'; });
            activeRows.slice(startIdx, endIdx).forEach(function(row) { row.style.display = ''; });

            // 5. "No results" feedback
            let noResultRow = document.getElementById('noResultRow');
            if (activeRows.length === 0 && allDataRows.length > 0) {
                if (!noResultRow) {
                    noResultRow = document.createElement('tr');
                    noResultRow.id = 'noResultRow';
                    noResultRow.innerHTML =
                        '<td colspan="8" style="text-align:center;padding:2rem;color:#718096;">' +
                        '<i class="fas fa-search" style="font-size:2rem;margin-bottom:.5rem;display:block;"></i>' +
                        'No rooms match your search or filter.</td>';
                    document.getElementById('roomsTableBody').appendChild(noResultRow);
                }
                noResultRow.style.display = '';
            } else if (noResultRow) {
                noResultRow.style.display = 'none';
            }

            // 6. Render pagination controls
            renderPagination(totalPages);
        }

        // ============================================
        // PAGINATION RENDERER (no page reload)
        // ============================================
        function renderPagination(totalPages) {
            const container = document.getElementById('paginationContainer');
            const infoEl    = document.getElementById('paginationInfo');
            const numbersEl = document.getElementById('pageNumbers');
            const total     = activeRows.length;

            if (total === 0) {
                container.style.display = 'none';
                return;
            }
            container.style.display = '';

            const startIdx = (currentPage - 1) * ROWS_PER_PAGE + 1;
            const endIdx   = Math.min(currentPage * ROWS_PER_PAGE, total);
            infoEl.textContent = 'Showing ' + startIdx + ' to ' + endIdx + ' of ' + total + ' rooms';

            numbersEl.innerHTML = '';

            // Previous button
            const prevBtn = document.createElement('button');
            prevBtn.className = 'page-btn';
            prevBtn.innerHTML = '<i class="fas fa-chevron-left"></i> Previous';
            prevBtn.disabled = (currentPage === 1);
            prevBtn.addEventListener('click', function() {
                if (currentPage > 1) { currentPage--; rebuildView(); }
            });
            numbersEl.appendChild(prevBtn);

            // Page number buttons
            const startPage = Math.max(1, currentPage - 2);
            const endPage   = Math.min(totalPages, currentPage + 2);

            if (startPage > 1) {
                numbersEl.appendChild(makePageBtn(1));
                if (startPage > 2) {
                    const ellipsis = document.createElement('span');
                    ellipsis.textContent = '...';
                    ellipsis.style.padding = '0 0.5rem';
                    numbersEl.appendChild(ellipsis);
                }
            }

            for (let p = startPage; p <= endPage; p++) {
                numbersEl.appendChild(makePageBtn(p));
            }

            if (endPage < totalPages) {
                if (endPage < totalPages - 1) {
                    const ellipsis = document.createElement('span');
                    ellipsis.textContent = '...';
                    ellipsis.style.padding = '0 0.5rem';
                    numbersEl.appendChild(ellipsis);
                }
                numbersEl.appendChild(makePageBtn(totalPages));
            }

            // Next button
            const nextBtn = document.createElement('button');
            nextBtn.className = 'page-btn';
            nextBtn.innerHTML = 'Next <i class="fas fa-chevron-right"></i>';
            nextBtn.disabled = (currentPage === totalPages);
            nextBtn.addEventListener('click', function() {
                if (currentPage < totalPages) { currentPage++; rebuildView(); }
            });
            numbersEl.appendChild(nextBtn);
        }

        function makePageBtn(p) {
            const btn = document.createElement('button');
            btn.className = 'page-num' + (p === currentPage ? ' active' : '');
            btn.textContent = p;
            btn.addEventListener('click', function() {
                currentPage = p;
                rebuildView();
            });
            return btn;
        }

        // ============================================
        // SEARCH
        // ============================================
        function initializeSearch() {
            document.getElementById('searchRooms').addEventListener('input', function() {
                rebuildView(true);
            });
        }

        // ============================================
        // FILTERS & SORT
        // ============================================
        function initializeFilters() {
            document.getElementById('filterType').addEventListener('change',   function() { rebuildView(true); });
            document.getElementById('filterStatus').addEventListener('change', function() { rebuildView(true); });
            document.getElementById('sortBy').addEventListener('change',       function() { rebuildView(); });
        }

        // ============================================
        // EDIT ROOM FUNCTIONALITY
        // ============================================
        let currentEditRow = null;

        function openEditModal(button) {
            const row = button.closest('tr');
            const isBooked = row.getAttribute('data-is-booked') === 'true';
            
            if (isBooked) {
                showNotification('Cannot edit room - Room has active reservations', 'error');
                return;
            }
            
            currentEditRow = row;
            const roomId = row.getAttribute('data-room-id');
            
            const cells = row.querySelectorAll('td');
            const roomNumber = cells[0].textContent.trim();
            const roomType   = cells[1].querySelector('.badge').textContent.trim();
            const rate       = cells[2].textContent.replace('LKR ', '').replace(/,/g, '').trim();
            const capacity   = cells[4].textContent.trim();
            const floor      = cells[5].textContent.trim();
            const view       = cells[6].textContent.trim();
            
            document.getElementById('editRoomId').value       = roomId;
            document.getElementById('editRoomNumber').value   = roomNumber;
            document.getElementById('editRoomType').value     = roomType;
            document.getElementById('editRatePerNight').value = rate;
            document.getElementById('editMaxCapacity').value  = capacity !== '-' ? capacity : '';
            document.getElementById('editFloorNumber').value  = floor    !== '-' ? floor    : '';
            document.getElementById('editView').value         = view     !== '-' ? view     : '';
            
            document.getElementById('editRoomModal').classList.add('active');
        }

        function closeEditModal() {
            document.getElementById('editRoomModal').classList.remove('active');
            currentEditRow = null;
        }

        // ============================================
        // STATUS CHANGE FUNCTIONALITY
        // ============================================
        let currentStatusRow = null;

        function openStatusModal(button) {
            const row = button.closest('tr');
            const isBooked = row.getAttribute('data-is-booked') === 'true';
            
            if (isBooked) {
                showNotification('Cannot change status - Room has active reservations', 'error');
                return;
            }
            
            currentStatusRow = row;
            const roomId = row.getAttribute('data-room-id');
            
            const statusCell  = row.querySelector('.status-badge');
            const currentStatus = statusCell.textContent.trim().split(' ').pop();
            
            document.getElementById('statusRoomId').value = roomId;
            document.getElementById('newStatus').value    = currentStatus;
            
            document.getElementById('statusModal').classList.add('active');
        }

        function closeStatusModal() {
            document.getElementById('statusModal').classList.remove('active');
            currentStatusRow = null;
        }

        // ============================================
        // MODALS
        // ============================================
        function initializeModals() {
            document.querySelectorAll('.modal').forEach(function(modal) {
                modal.addEventListener('click', function(e) {
                    if (e.target === modal) modal.classList.remove('active');
                });
            });
            document.addEventListener('keydown', function(e) {
                if (e.key === 'Escape') {
                    const active = document.querySelector('.modal.active');
                    if (active) active.classList.remove('active');
                }
            });
        }

        // ============================================
        // NOTIFICATION SYSTEM
        // ============================================
        function showNotification(message, type) {
            if (!type) type = 'info';

            const notification = document.createElement('div');
            notification.className = 'notification notification-' + type;

            var bgColor = '#3b82f6';
            if (type === 'success') bgColor = '#10b981';
            if (type === 'error')   bgColor = '#ef4444';

            notification.style.cssText =
                'position:fixed;top:20px;right:20px;background:' + bgColor + ';color:white;' +
                'padding:1rem 1.5rem;border-radius:8px;box-shadow:0 10px 25px rgba(0,0,0,.2);' +
                'z-index:9999;animation:slideInRight .3s ease;font-family:Poppins,sans-serif;' +
                'display:flex;align-items:center;gap:.8rem;';

            var icon = 'fa-info-circle';
            if (type === 'success') icon = 'fa-check-circle';
            if (type === 'error')   icon = 'fa-exclamation-circle';

            notification.innerHTML = '<i class="fas ' + icon + '"></i><span>' + message + '</span>';
            document.body.appendChild(notification);

            setTimeout(function() {
                notification.style.animation = 'slideOutRight .3s ease';
                setTimeout(function() { notification.remove(); }, 300);
            }, 3000);
        }

        // ============================================
        // ANIMATIONS
        // ============================================
        const style = document.createElement('style');
        style.textContent =
            '@keyframes slideInRight{from{transform:translateX(400px);opacity:0}to{transform:translateX(0);opacity:1}}' +
            '@keyframes slideOutRight{from{transform:translateX(0);opacity:1}to{transform:translateX(400px);opacity:0}}';
        document.head.appendChild(style);
    </script>
</body>
</html>
