<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Login - Ocean View Resort</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <a href="index.jsp" class="back-home">
                <i class="fas fa-arrow-left"></i> Back to Home
            </a>
            <div class="login-icon">
                <i class="fas fa-umbrella-beach"></i>
            </div>
            <h1>Ocean View Resort</h1>
            <h2>Staff Portal Login</h2>
            <p>Access the Reservation Management System</p>
        </div>
        
        <div class="login-body">
            <!-- Success Message -->
            <% if (request.getAttribute("successMessage") != null) { %>
            <div class="alert alert-success" id="success-message">
                <i class="fas fa-check-circle"></i>
                <span><%= request.getAttribute("successMessage") %></span>
            </div>
            <% } %>
            
            <!-- Error Message -->
            <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-error" id="error-message">
                <i class="fas fa-exclamation-circle"></i>
                <span><%= request.getAttribute("errorMessage") %></span>
            </div>
            <% } %>
            
            <form id="login-form" method="POST" action="login">
                <div class="form-group">
                    <label for="username">
                        <i class="fas fa-user"></i>
                        <span>Username</span>
                    </label>
                    <input type="text" id="username" name="username" placeholder="Enter your staff username" required autofocus>
                </div>
                
                <div class="form-group">
                    <label for="password">
                        <i class="fas fa-key"></i>
                        <span>Password</span>
                    </label>
                    <div class="password-wrapper">
                        <input type="password" id="password" name="password" placeholder="Enter your secure password" required>
                        <button type="button" class="toggle-password" onclick="togglePassword()">
                            <i class="fas fa-eye" id="toggle-icon"></i>
                        </button>
                    </div>
                </div>
                
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-sign-in-alt"></i>
                    <span>Access Reservation System</span>
                </button>
                
                <a href="index.jsp" class="btn btn-secondary">
                    <i class="fas fa-home"></i>
                    <span>Return to Homepage</span>
                </a>
            </form>
            
            <!-- Security Note Section -->
            <div class="security-note">
                <div class="security-icon">
                    <i class="fas fa-shield-alt"></i>
                </div>
                <h3>Security Notice</h3>
                <p>This system contains confidential guest information. Only authorized Ocean View Resort staff members are permitted to access the reservation system. Unauthorized access attempts will be logged and reported.</p>
            </div>
        </div>
    </div>

    <script>
        // Toggle password visibility
        function togglePassword() {
            const passwordInput = document.getElementById('password');
            const toggleIcon = document.getElementById('toggle-icon');
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                toggleIcon.classList.remove('fa-eye');
                toggleIcon.classList.add('fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                toggleIcon.classList.remove('fa-eye-slash');
                toggleIcon.classList.add('fa-eye');
            }
        }
        
        // Auto-hide success/error messages after 5 seconds
        setTimeout(() => {
            const successMsg = document.getElementById('success-message');
            const errorMsg = document.getElementById('error-message');
            
            if (successMsg) {
                successMsg.style.animation = 'slideOut 0.5s ease';
                setTimeout(() => successMsg.remove(), 500);
            }
            
            if (errorMsg) {
                errorMsg.style.animation = 'slideOut 0.5s ease';
                setTimeout(() => errorMsg.remove(), 500);
            }
        }, 5000);
        
        // Form validation
        document.getElementById('login-form').addEventListener('submit', (event) => {
            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value;
            
            if (!username || !password) {
                event.preventDefault();
                showNotification('Please enter both username and password', 'error');
                return;
            }
            
            // Show loading state
            const submitBtn = event.target.querySelector('.btn-primary');
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i><span>Signing in...</span>';
            submitBtn.disabled = true;
        });
        
        // Function to show toast notifications
        function showNotification(message, type) {
            const notification = document.createElement('div');
            notification.className = `toast-notification toast-${type}`;
            
            const icon = type === 'error' ? 'fa-exclamation-circle' : 'fa-check-circle';
            
            notification.innerHTML = `
                <i class="fas ${icon}"></i>
                <span>${message}</span>
            `;
            
            document.body.appendChild(notification);
            
            // Trigger animation
            setTimeout(() => notification.classList.add('show'), 10);
            
            // Remove notification after 4 seconds
            setTimeout(() => {
                notification.classList.remove('show');
                setTimeout(() => notification.remove(), 300);
            }, 4000);
        }
        
        // Clear alerts when user starts typing
        ['username', 'password'].forEach(id => {
            document.getElementById(id).addEventListener('input', () => {
                const successMsg = document.getElementById('success-message');
                const errorMsg = document.getElementById('error-message');
                
                if (successMsg) successMsg.remove();
                if (errorMsg) errorMsg.remove();
            });
        });
    </script>
</body>
</html>
