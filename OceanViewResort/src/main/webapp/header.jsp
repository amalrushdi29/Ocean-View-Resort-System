<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ocean View Resort - Header</title>
<style>
    .profile {
        cursor: pointer;
        transition: all 0.3s ease;
        padding: 8px 15px;
        border-radius: 8px;
        position: relative;
        background: rgba(255, 255, 255, 0.15);
    }
    
    .profile:hover {
        background: rgba(255, 255, 255, 0.15);
        transform: translateY(-2px);
    }
    
    .profile:active {
        transform: translateY(0);
    }
    
    .profile::after {
        content: '⚙️';
        position: absolute;
        right: -5px;
        top: -5px;
        font-size: 14px;
        opacity: 0;
        transition: opacity 0.3s ease;
    }
    
    .profile:hover::after {
        opacity: 1;
    }
</style>
</head>
<body>
<%
    // Check if user is logged in
    if(session.getAttribute("loggedin") == null || !(Boolean)session.getAttribute("loggedin")) {
        response.sendRedirect("login.jsp");
        return;
    }

    String displayName = (String) session.getAttribute("display_name");
    if (displayName == null) {
        // Fallback: if display_name is not in session, process from full_name
        String fullName = (String) session.getAttribute("full_name");
        if (fullName != null) {
            String[] nameParts = fullName.split("\\s+");
            if (nameParts.length >= 2) {
                displayName = nameParts[nameParts.length - 2] + " " + nameParts[nameParts.length - 1];
            } else {
                displayName = fullName;
            }
            session.setAttribute("display_name", displayName);
        } else {
            displayName = "Staff";
        }
    }
%>
<div class="header">
    <div class="system-name">
        <svg width="40" height="40" viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
            <!-- Ocean waves -->
            <path d="M5 25C7 23 9 23 11 25C13 27 15 27 17 25C19 23 21 23 23 25C25 27 27 27 29 25C31 23 33 23 35 25" stroke="white" stroke-width="2" stroke-linecap="round"/>
            <path d="M5 30C7 28 9 28 11 30C13 32 15 32 17 30C19 28 21 28 23 30C25 32 27 32 29 30C31 28 33 28 35 30" stroke="white" stroke-width="2" stroke-linecap="round"/>
            <!-- Sun -->
            <circle cx="20" cy="12" r="6" fill="white" opacity="0.9"/>
            <path d="M20 3V5M20 19V21M29 12H31M9 12H11M26.5 5.5L25 7M15 17L13.5 18.5M26.5 18.5L25 17M15 7L13.5 5.5" stroke="white" stroke-width="1.5" stroke-linecap="round" opacity="0.7"/>
        </svg>
        <div>
            <div style="font-size: 22px; font-weight: 700; line-height: 1.2;">Ocean View Resort</div>
            <div style="font-size: 11px; font-weight: 400; opacity: 0.9; letter-spacing: 0.3px;">Reservation System • Galle, Sri Lanka</div>
        </div>
    </div>
    <div class="profile" onclick="openProfileModal()" title="Click to view/edit your profile">
        <img src="https://ui-avatars.com/api/?name=<%= displayName.replace(" ", "+") %>&background=667eea&color=fff&size=128&bold=true" alt="Profile Picture" class="profile-pic">
        <span class="staff-name"><%= displayName %></span>
    </div>
</div>

<script>
function openProfileModal() {
    // Open profile.jsp in the same window
    window.location.href = 'profile.jsp';
}
</script>
</body>
</html>
