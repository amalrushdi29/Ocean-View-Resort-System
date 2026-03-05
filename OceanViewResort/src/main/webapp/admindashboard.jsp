<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Ocean View Resort</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="adminstyle.css">
    <link rel="stylesheet" type="text/css" href="css/admindashboard.css">
</head>
<body>
    <!-- Include Header -->
    <%@ include file="adminheader.jsp" %>
    
    <!-- Include Sidebar -->
    <%@ include file="adminsidebar.jsp" %>

    <!-- Main Content Area -->
    <div class="main-content">
        <!-- Dashboard Content -->
        <div class="dashboard-content">
            <!-- Welcome Card -->
            <section class="welcome-card">
                <h2>Welcome Back, Administrator!</h2>
                <p>Manage your resort operations efficiently from this dashboard. Monitor staff activities, view reports, and ensure smooth functioning of Ocean View Resort's reservation system.</p>
                <div class="date-time">
                    <div><i class="far fa-calendar"></i> <span id="currentDate"></span></div>
                    <div><i class="far fa-clock"></i> <span id="currentTime"></span></div>
                </div>
            </section>

			<!-- Stats Cards -->
			<section class="stats-grid">
			    <div class="stat-card blue">
			        <div class="stat-header">
			            <div>
			                <div class="stat-value" id="totalStaff">
			                    <i class="fas fa-spinner fa-spin"></i>
			                </div>
			                <div class="stat-title">Total Staff Members</div>
			            </div>
			            <div class="stat-icon">
			                <i class="fas fa-users"></i>
			            </div>
			        </div>
			        <div class="stat-change positive">
			            <i class="fas fa-users"></i> Registered staff accounts
			        </div>
			    </div>
			    
			    <div class="stat-card green">
			        <div class="stat-header">
			            <div>
			                <div class="stat-value" id="activeReservations">
			                    <i class="fas fa-spinner fa-spin"></i>
			                </div>
			                <div class="stat-title">Active Reservations</div>
			            </div>
			            <div class="stat-icon">
			                <i class="fas fa-calendar-check"></i>
			            </div>
			        </div>
			        <div class="stat-change positive">
			            <i class="fas fa-check-circle"></i> Confirmed + Checked In
			        </div>
			    </div>
			    
			    <div class="stat-card orange">
			        <div class="stat-header">
			            <div>
			                <div class="stat-value" id="maintenanceRooms">
			                    <i class="fas fa-spinner fa-spin"></i>
			                </div>
			                <div class="stat-title">Rooms in Maintenance</div>
			            </div>
			            <div class="stat-icon">
			                <i class="fas fa-tools"></i>
			            </div>
			        </div>
			        <div class="stat-change negative">
			            <i class="fas fa-exclamation-circle"></i> Requires attention
			        </div>
			    </div>
			</section>
			
			<!-- Stats Cards Row 2 -->
			<section class="stats-grid">
			    <div class="stat-card purple">
			        <div class="stat-header">
			            <div>
			                <div class="stat-value" id="totalGuests">
			                    <i class="fas fa-spinner fa-spin"></i>
			                </div>
			                <div class="stat-title">Total Guests</div>
			            </div>
			            <div class="stat-icon">
			                <i class="fas fa-user-friends"></i>
			            </div>
			        </div>
			        <div class="stat-change positive">
			            <i class="fas fa-user-check"></i> Registered guests
			        </div>
			    </div>
			
			    <div class="stat-card teal">
			        <div class="stat-header">
			            <div>
			                <div class="stat-value" id="totalRooms">
			                    <i class="fas fa-spinner fa-spin"></i>
			                </div>
			                <div class="stat-title">Total Rooms</div>
			            </div>
			            <div class="stat-icon">
			                <i class="fas fa-bed"></i>
			            </div>
			        </div>
			        <div class="stat-change positive">
			            <i class="fas fa-hotel"></i> All room inventory
			        </div>
			    </div>
			
			    <div class="stat-card red">
			        <div class="stat-header">
			            <div>
			                <div class="stat-value" id="roomsBooked">
			                    <i class="fas fa-spinner fa-spin"></i>
			                </div>
			                <div class="stat-title">Rooms Booked</div>
			            </div>
			            <div class="stat-icon">
			                <i class="fas fa-door-closed"></i>
			            </div>
			        </div>
			        <div class="stat-change negative">
			            <i class="fas fa-lock"></i> Currently occupied
			        </div>
			    </div>
			</section>

            <!-- Quick Actions -->
            <section class="quick-actions">
                <h2 class="section-title"><i class="fas fa-bolt"></i> Quick Actions</h2>
                <div class="actions-grid">
                    <div class="action-card" onclick="window.location.href='admindashboard.jsp'">
                        <div class="action-icon">
                            <i class="fas fa-tachometer-alt"></i>
                        </div>
                        <h3>Dashboard</h3>
                        <p>View system overview and key metrics</p>
                    </div>
                    
                    <div class="action-card" onclick="window.location.href='addstaff.jsp'">
                        <div class="action-icon">
                            <i class="fas fa-user-plus"></i>
                        </div>
                        <h3>Add Staff</h3>
                        <p>Create new staff accounts with role-based permissions</p>
                    </div>
                    
                    <div class="action-card" onclick="window.location.href='viewstaff.jsp'">
                        <div class="action-icon">
                            <i class="fas fa-users-cog"></i>
                        </div>
                        <h3>View Staff</h3>
                        <p>View, edit, or deactivate staff member accounts</p>
                    </div>
                    
                    <div class="action-card" onclick="window.location.href='rooms.jsp'">
                        <div class="action-icon">
                            <i class="fas fa-door-open"></i>
                        </div>
                        <h3>Manage Rooms</h3>
                        <p>Update room availability and maintenance status</p>
                    </div>
                </div>
            </section>
        </div>
    </div>

    <script>
        // DOM Elements
        const currentDate = document.getElementById('currentDate');
        const currentTime = document.getElementById('currentTime');

        // Update Date and Time
        function updateDateTime() {
            const now = new Date();
            
            // Format date
            const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
            currentDate.textContent = now.toLocaleDateString('en-US', options);
            
            // Format time
            currentTime.textContent = now.toLocaleTimeString('en-US', { 
                hour: '2-digit', 
                minute: '2-digit',
                hour12: true 
            });
        }

        // Update date/time initially and every minute
        updateDateTime();
        setInterval(updateDateTime, 60000);

        // Add hover effect to cards
        document.querySelectorAll('.action-card').forEach(card => {
            card.addEventListener('mouseenter', function() {
                const icon = this.querySelector('.action-icon i');
                icon.style.transform = 'scale(1.2)';
                icon.style.transition = 'transform 0.3s ease';
            });
            
            card.addEventListener('mouseleave', function() {
                const icon = this.querySelector('.action-icon i');
                icon.style.transform = 'scale(1)';
            });
        });

        // Add animation styles
        const style = document.createElement('style');
        style.textContent = `
            @keyframes pulse {
                0% { transform: scale(1); }
                50% { transform: scale(1.02); }
                100% { transform: scale(1); }
            }
            
            .welcome-card {
                animation: pulse 10s ease-in-out infinite;
            }
        `;
        document.head.appendChild(style);
        
        
     // Load real dashboard stats
        function loadDashboardStats() {
            fetch('GetDashboardStats')
            .then(response => response.json())
            .then(data => {
            	if (data.success) {
            	    // Row 1 stats
            	    document.getElementById('totalStaff').textContent = data.totalStaff;
            	    document.getElementById('activeReservations').textContent = data.activeReservations;
            	    document.getElementById('maintenanceRooms').textContent = data.maintenanceRooms;
            	    
            	    // Row 2 stats
            	    document.getElementById('totalGuests').textContent = data.totalGuests;
            	    document.getElementById('totalRooms').textContent = data.totalRooms;
            	    document.getElementById('roomsBooked').textContent = data.roomsBooked;

            	    // Update maintenance card color based on count
            	    if (data.maintenanceRooms > 0) {
            	        document.querySelector('.stat-card.orange .stat-change').innerHTML =
            	            '<i class="fas fa-exclamation-circle"></i> ' +
            	            data.maintenanceRooms + ' room(s) require attention';
            	    } else {
            	        document.querySelector('.stat-card.orange .stat-change').innerHTML =
            	            '<i class="fas fa-check-circle"></i> No rooms in maintenance';
            	        document.querySelector('.stat-card.orange .stat-change').className =
            	            'stat-change positive';
            	    }

            	    // Update booked rooms card color based on count
            	    if (data.roomsBooked > 0) {
            	        document.querySelector('.stat-card.red .stat-change').innerHTML =
            	            '<i class="fas fa-lock"></i> ' +
            	            data.roomsBooked + ' room(s) currently occupied';
            	    } else {
            	        document.querySelector('.stat-card.red .stat-change').innerHTML =
            	            '<i class="fas fa-unlock"></i> No rooms currently booked';
            	        document.querySelector('.stat-card.red .stat-change').className =
            	            'stat-change positive';
            	    }
            	} else {
            	    // Show dashes if error
            	    document.getElementById('totalStaff').textContent = '-';
            	    document.getElementById('activeReservations').textContent = '-';
            	    document.getElementById('maintenanceRooms').textContent = '-';
            	    document.getElementById('totalGuests').textContent = '-';
            	    document.getElementById('totalRooms').textContent = '-';
            	    document.getElementById('roomsBooked').textContent = '-';
            	}
            })
            .catch(error => {
                console.error('Error loading stats:', error);
                document.getElementById('totalStaff').textContent = '-';
                document.getElementById('activeReservations').textContent = '-';
                document.getElementById('maintenanceRooms').textContent = '-';
            });
        }

        // Load stats on page load
        loadDashboardStats();
    </script>
</body>
</html>
