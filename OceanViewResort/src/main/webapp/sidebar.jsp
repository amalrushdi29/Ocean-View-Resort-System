<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sidebar Navigation</title>
<!-- Font Awesome for icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="sidebar" id="sidebar">
    <ul>
        <li>
            <a href="home.jsp">
                <i class="fas fa-home"></i>
                <span class="nav-text">Home</span>
            </a>
        </li>
        <li>
            <a href="reservation.jsp">
                <i class="fas fa-calendar-check"></i>
                <span class="nav-text">Reservation</span>
            </a>
        </li>
        <li>
            <a href="booking.jsp">
                <i class="fas fa-book"></i>
                <span class="nav-text">Booking</span>
            </a>
        </li>
        <li>
            <a href="ViewReservation.jsp">
                <i class="fas fa-list-alt"></i>
                <span class="nav-text">View Reservations</span>
            </a>
        </li>
        <li>
            <a href="billing.jsp">
                <i class="fas fa-file-invoice-dollar"></i>
                <span class="nav-text">Billing</span>
            </a>
        </li>

        <!-- Management Group -->
        <li class="nav-group">
            <a href="javascript:void(0)" class="group-toggle" onclick="toggleGroup(this)">
                <i class="fas fa-cog"></i>
                <span class="nav-text">Management</span>
                <i class="fas fa-chevron-down group-arrow"></i>
            </a>
            <ul class="sub-menu">
                <li>
                    <a href="staffrooms.jsp">
                        <i class="fas fa-door-open"></i>
                        <span class="nav-text">Room Manager</span>
                    </a>
                </li>
                <li>
                    <a href="guests.jsp">
                        <i class="fas fa-users"></i>
                        <span class="nav-text">Guest Manager</span>
                    </a>
                </li>
            </ul>
        </li>

        <li>
            <a href="help.jsp">
                <i class="fas fa-question-circle"></i>
                <span class="nav-text">Help</span>
            </a>
        </li>
        <li>
            <a href="index.jsp">
                <i class="fas fa-sign-out-alt"></i>
                <span class="nav-text">Logout</span>
            </a>
        </li>
    </ul>
</div>

<script>
function toggleGroup(element) {
    const subMenu = element.nextElementSibling;
    const isActive = element.classList.contains('active');

    // Close all other groups
    const allGroups = document.querySelectorAll('.group-toggle');
    allGroups.forEach(group => {
        if (group !== element) {
            group.classList.remove('active');
            group.nextElementSibling.style.maxHeight = null;
        }
    });

    // Toggle current group
    if (isActive) {
        element.classList.remove('active');
        subMenu.style.maxHeight = null;
    } else {
        element.classList.add('active');
        subMenu.style.maxHeight = subMenu.scrollHeight + "px";
    }
}

// Function to set active link based on current page
function setActiveLink() {
    const currentPage = window.location.pathname.split('/').pop();
    const links = document.querySelectorAll('.sidebar a:not(.group-toggle)');
    
    links.forEach(link => {
        link.classList.remove('active');
        const href = link.getAttribute('href');
        if (href === currentPage) {
            link.classList.add('active');
            
            // If it's a submenu item, open its parent group
            const parentGroup = link.closest('.sub-menu');
            if (parentGroup) {
                const groupToggle = parentGroup.previousElementSibling;
                groupToggle.classList.add('active');
                parentGroup.style.maxHeight = parentGroup.scrollHeight + "px";
            }
        }
    });
}

// Responsive sidebar toggle for mobile
function initMobileSidebar() {
    if (window.innerWidth <= 768) {
        document.getElementById('sidebar').classList.add('mobile');
    } else {
        document.getElementById('sidebar').classList.remove('mobile');
    }
}

window.addEventListener('resize', initMobileSidebar);
window.addEventListener('load', () => {
    initMobileSidebar();
    setActiveLink();
});
</script>

</body>
</html>
