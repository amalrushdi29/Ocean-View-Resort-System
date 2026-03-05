<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ocean View Resort - Home</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/home.css">
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <jsp:include page="header.jsp">
        <jsp:param name="pageTitle" value="Home"/>
    </jsp:include>
    <jsp:include page="sidebar.jsp"/>

    <div class="main-content">

        <!-- Welcome Section -->
        <div class="welcome-section">
            <div class="welcome-content">
                <h1 id="greetingText"><i class="fas fa-umbrella-beach"></i> Welcome to Ocean View Resort</h1>
                <p class="welcome-subtitle">Manage your hotel operations efficiently and seamlessly</p>
                <div class="date-time">
                    <div class="date-time-item">
                        <i class="far fa-calendar"></i>
                        <span id="currentDate"></span>
                    </div>
                    <div class="date-time-item">
                        <i class="far fa-clock"></i>
                        <span id="currentTime"></span>
                    </div>
                </div>
            </div>
            <div class="welcome-graphic">
                <i class="fas fa-hotel"></i>
            </div>
        </div>

        <!-- Hotel Banner Image Slider -->
        <div class="hotel-banner-slider">
            <div class="slider-container">
                <div class="slide active">
                    <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?w=1200&h=400&fit=crop" alt="Luxury Hotel Exterior">
                    <div class="slide-overlay">
                        <h3>Luxury Accommodations</h3>
                    </div>
                </div>
                <div class="slide">
                    <img src="https://images.unsplash.com/photo-1615880484746-a134be9a6ecf?w=1200&h=400&fit=crop" alt="Ocean View Pool">
                    <div class="slide-overlay">
                        <h3>Premium Amenities</h3>
                    </div>
                </div>
                <div class="slide">
                    <img src="https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=1200&h=400&fit=crop" alt="Luxury Hotel Room">
                    <div class="slide-overlay">
                        <h3>Comfortable Rooms</h3>
                    </div>
                </div>
                <div class="slide">
                    <img src="https://images.unsplash.com/photo-1439130490301-25e322d88054?w=1200&h=400&fit=crop" alt="Breathtaking Ocean View">
                    <div class="slide-overlay">
                        <h3>Breathtaking Views</h3>
                    </div>
                </div>
            </div>
            <button class="slider-btn prev" onclick="changeSlide(-1)"><i class="fas fa-chevron-left"></i></button>
            <button class="slider-btn next" onclick="changeSlide(1)"><i class="fas fa-chevron-right"></i></button>
            <div class="slider-dots">
                <span class="dot active" onclick="currentSlide(0)"></span>
                <span class="dot" onclick="currentSlide(1)"></span>
                <span class="dot" onclick="currentSlide(2)"></span>
                <span class="dot" onclick="currentSlide(3)"></span>
            </div>
        </div>

        <!-- Summary Cards Top Row (2 Cards) -->
        <div class="summary-cards top-row">
            <div class="card card-blue">
                <div class="card-icon-wrapper">
                    <i class="fas fa-calendar-check"></i>
                </div>
                <div class="card-content">
                    <h3>Total Reservations</h3>
                    <p class="card-number" id="totalReservations">
                        <i class="fas fa-spinner fa-spin"></i>
                    </p>
                    <span class="card-label"><i class="fas fa-check-circle"></i> Confirmed + Checked In</span>
                </div>
            </div>

            <div class="card card-revenue">
                <div class="card-icon-wrapper">
                    <i class="fas fa-money-bill-wave"></i>
                </div>
                <div class="card-content">
                    <h3>Today's Revenue</h3>
                    <p class="card-number revenue-number" id="todayRevenue">
                        <i class="fas fa-spinner fa-spin"></i>
                    </p>
                    <span class="card-label"><i class="fas fa-chart-line"></i> Total bills generated today</span>
                </div>
            </div>
        </div>

        <!-- Summary Cards Row 2 (4 Cards - Check-ins) -->
        <div class="summary-cards">
            <div class="card card-green">
                <div class="card-icon-wrapper">
                    <i class="fas fa-user-check"></i>
                </div>
                <div class="card-content">
                    <h3>Already Checked In</h3>
                    <p class="card-number" id="alreadyCheckedIn">
                        <i class="fas fa-spinner fa-spin"></i>
                    </p>
                    <span class="card-label"><i class="fas fa-check-double"></i> Checked in today</span>
                </div>
            </div>

            <div class="card card-cyan">
                <div class="card-icon-wrapper">
                    <i class="fas fa-clock"></i>
                </div>
                <div class="card-content">
                    <h3>Pending Check-ins</h3>
                    <p class="card-number" id="confirmedCheckIns">
                        <i class="fas fa-spinner fa-spin"></i>
                    </p>
                    <span class="card-label"><i class="fas fa-hourglass-half"></i> Arriving today</span>
                </div>
            </div>

            <div class="card card-orange">
                <div class="card-icon-wrapper">
                    <i class="fas fa-door-open"></i>
                </div>
                <div class="card-content">
                    <h3>Already Checked Out</h3>
                    <p class="card-number" id="alreadyCheckedOut">
                        <i class="fas fa-spinner fa-spin"></i>
                    </p>
                    <span class="card-label"><i class="fas fa-check-circle"></i> Departed today</span>
                </div>
            </div>

            <div class="card card-amber">
                <div class="card-icon-wrapper">
                    <i class="fas fa-calendar-times"></i>
                </div>
                <div class="card-content">
                    <h3>Pending Check-outs</h3>
                    <p class="card-number" id="scheduledCheckOuts">
                        <i class="fas fa-spinner fa-spin"></i>
                    </p>
                    <span class="card-label"><i class="fas fa-hourglass-half"></i> Departing today</span>
                </div>
            </div>
        </div>

        <!-- Summary Cards Row 3 (4 Cards - Rooms & Guests) -->
        <div class="summary-cards">
            <div class="card card-purple">
                <div class="card-icon-wrapper">
                    <i class="fas fa-bed"></i>
                </div>
                <div class="card-content">
                    <h3>Available Rooms</h3>
                    <p class="card-number" id="availableRooms">
                        <i class="fas fa-spinner fa-spin"></i>
                    </p>
                    <span class="card-label"><i class="fas fa-check-circle"></i> Ready for booking</span>
                </div>
            </div>

            <div class="card card-teal">
                <div class="card-icon-wrapper">
                    <i class="fas fa-user-friends"></i>
                </div>
                <div class="card-content">
                    <h3>Total Guests</h3>
                    <p class="card-number" id="totalGuests">
                        <i class="fas fa-spinner fa-spin"></i>
                    </p>
                    <span class="card-label"><i class="fas fa-user-check"></i> Registered guests</span>
                </div>
            </div>

            <div class="card card-yellow">
                <div class="card-icon-wrapper">
                    <i class="fas fa-tools"></i>
                </div>
                <div class="card-content">
                    <h3>In Maintenance</h3>
                    <p class="card-number" id="maintenanceRooms">
                        <i class="fas fa-spinner fa-spin"></i>
                    </p>
                    <span class="card-label"><i class="fas fa-exclamation-circle"></i> Requires attention</span>
                </div>
            </div>

            <div class="card card-red">
                <div class="card-icon-wrapper">
                    <i class="fas fa-door-closed"></i>
                </div>
                <div class="card-content">
                    <h3>Rooms Occupied</h3>
                    <p class="card-number" id="roomsOccupied">
                        <i class="fas fa-spinner fa-spin"></i>
                    </p>
                    <span class="card-label"><i class="fas fa-lock"></i> Currently booked</span>
                </div>
            </div>
        </div>
        <!-- Quick Actions -->
        <div class="quick-actions-section">
            <h2><i class="fas fa-bolt"></i> Quick Actions</h2>
            <div class="action-buttons">
                <a href="reservation.jsp" class="action-btn btn-primary">
                    <i class="fas fa-plus-circle btn-icon"></i>
                    <span class="btn-text">New Reservation</span>
                    <i class="fas fa-arrow-right btn-arrow"></i>
                </a>
                <a href="ViewReservation.jsp" class="action-btn btn-secondary">
                    <i class="fas fa-list-alt btn-icon"></i>
                    <span class="btn-text">View Reservations</span>
                    <i class="fas fa-arrow-right btn-arrow"></i>
                </a>
                <a href="billing.jsp" class="action-btn btn-accent">
                    <i class="fas fa-file-invoice-dollar btn-icon"></i>
                    <span class="btn-text">Billing</span>
                    <i class="fas fa-arrow-right btn-arrow"></i>
                </a>
            </div>
        </div>
    </div>

    <script>
        // ============================================
        // DATE, TIME & GREETING
        // ============================================
        function updateDateTime() {
            const now = new Date();
            const hour = now.getHours();

            // Greeting based on time
            let greeting = '';
            let greetingIcon = '';
            if (hour >= 5 && hour < 12) {
                greeting = 'Good Morning';
                greetingIcon = 'fa-sun';
            } else if (hour >= 12 && hour < 17) {
                greeting = 'Good Afternoon';
                greetingIcon = 'fa-cloud-sun';
            } else {
                greeting = 'Good Evening';
                greetingIcon = 'fa-moon';
            }

            document.getElementById('greetingText').innerHTML =
                '<i class="fas ' + greetingIcon + '"></i> ' + greeting + ', Welcome to Ocean View Resort';

            // Format date
            const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
            document.getElementById('currentDate').textContent = now.toLocaleDateString('en-US', options);

            // Format time
            document.getElementById('currentTime').textContent = now.toLocaleTimeString('en-US', {
                hour: '2-digit',
                minute: '2-digit',
                hour12: true
            });
        }

        updateDateTime();
        setInterval(updateDateTime, 60000);

        // ============================================
        // LOAD REAL STATS
        // ============================================
function loadHomeStats() {
    fetch('GetHomeStats')
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // Top Row
            document.getElementById('totalReservations').textContent = data.totalReservations;
            const revenue = parseFloat(data.todayRevenue);
            document.getElementById('todayRevenue').textContent = 
                revenue > 0 ? 'LKR ' + revenue.toLocaleString('en-US', {minimumFractionDigits: 2, maximumFractionDigits: 2}) : 'LKR 0.00';

            // Row 2 - Check-ins/outs
            document.getElementById('alreadyCheckedIn').textContent = data.alreadyCheckedIn;
            document.getElementById('confirmedCheckIns').textContent = data.confirmedCheckIns;
            document.getElementById('alreadyCheckedOut').textContent = data.alreadyCheckedOut;
            document.getElementById('scheduledCheckOuts').textContent = data.scheduledCheckOuts;

            // Row 3 - Rooms & Guests
            document.getElementById('availableRooms').textContent = data.availableRooms;
            document.getElementById('totalGuests').textContent = data.totalGuests;
            document.getElementById('maintenanceRooms').textContent = data.maintenanceRooms;
            document.getElementById('roomsOccupied').textContent = data.roomsOccupied;
        } else {
            ['totalReservations', 'todayRevenue', 'alreadyCheckedIn', 'confirmedCheckIns',
             'alreadyCheckedOut', 'scheduledCheckOuts', 'availableRooms', 'totalGuests', 
             'maintenanceRooms', 'roomsOccupied']
            .forEach(id => {
                document.getElementById(id).textContent = '-';
            });
        }
    })
    .catch(error => {
        console.error('Error loading stats:', error);
        ['totalReservations', 'todayRevenue', 'alreadyCheckedIn', 'confirmedCheckIns',
         'alreadyCheckedOut', 'scheduledCheckOuts', 'availableRooms', 'totalGuests', 
         'maintenanceRooms', 'roomsOccupied']
        .forEach(id => {
            document.getElementById(id).textContent = '-';
        });
    });
}

        loadHomeStats();

        // ============================================
        // IMAGE SLIDER
        // ============================================
        let currentSlideIndex = 0;
        let autoSlideInterval;

        function showSlide(index) {
            const slides = document.querySelectorAll('.slide');
            const dots = document.querySelectorAll('.dot');

            if (index >= slides.length) currentSlideIndex = 0;
            if (index < 0) currentSlideIndex = slides.length - 1;

            slides.forEach(slide => slide.classList.remove('active'));
            dots.forEach(dot => dot.classList.remove('active'));

            slides[currentSlideIndex].classList.add('active');
            dots[currentSlideIndex].classList.add('active');
        }

        function changeSlide(direction) {
            currentSlideIndex += direction;
            showSlide(currentSlideIndex);
            resetAutoSlide();
        }

        function currentSlide(index) {
            currentSlideIndex = index;
            showSlide(currentSlideIndex);
            resetAutoSlide();
        }

        function autoSlide() {
            currentSlideIndex++;
            showSlide(currentSlideIndex);
        }

        function resetAutoSlide() {
            clearInterval(autoSlideInterval);
            autoSlideInterval = setInterval(autoSlide, 5000);
        }

        autoSlideInterval = setInterval(autoSlide, 5000);
    </script>
</body>
</html>