<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Sidebar Navigation</title>
<link rel="stylesheet" type="text/css" href="css/adminstyle.css">
<!-- Font Awesome for icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="sidebar" id="sidebar">
    <ul>
        <li>
            <a href="admindashboard.jsp" class="active">
                <i class="fas fa-th-large"></i>
                <span class="nav-text">Dashboard</span>
            </a>
        </li>
        <li>
            <a href="addstaff.jsp">
                <i class="fas fa-user-plus"></i>
                <span class="nav-text">Add Staff</span>
            </a>
        </li>
        <li>
            <a href="viewstaff.jsp">
                <i class="fas fa-users"></i>
                <span class="nav-text">View Staff</span>
            </a>
        </li>
        <li>
            <a href="rooms.jsp">
                <i class="fas fa-door-open"></i>
                <span class="nav-text">Manage Rooms</span>
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
// Function to set active link based on current page
function setActiveLink() {
    const currentPage = window.location.pathname.split('/').pop();
    const links = document.querySelectorAll('.sidebar a');
    
    links.forEach(link => {
        link.classList.remove('active');
        const href = link.getAttribute('href');
        if (href === currentPage) {
            link.classList.add('active');
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
