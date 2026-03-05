<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Check if user is logged in
    if(session.getAttribute("loggedin") == null || !(Boolean)session.getAttribute("loggedin")) {
        response.sendRedirect("login.jsp");
        return;
    }

    String displayName = (String) session.getAttribute("display_name");
    String fullName = (String) session.getAttribute("full_name");
    String username = (String) session.getAttribute("username");
    String contactNo = (String) session.getAttribute("contact_no");
    Integer staffId = (Integer) session.getAttribute("staff_id");

    // Get messages from request attributes
    String successMessage = (String) request.getAttribute("successMessage");
    String errorMessage = (String) request.getAttribute("errorMessage");
    String infoMessage = (String) request.getAttribute("infoMessage");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile Settings - Ocean View Resort</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: transparent;
        }

        /* Modal Overlay */
        .modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            backdrop-filter: blur(4px);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 9999;
            animation: fadeIn 0.3s ease;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        @keyframes slideUp {
            from {
                transform: translateY(30px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }

        /* Modal Container */
        .modal-container {
            background: white;
            border-radius: 16px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            max-width: 700px;
            width: 90%;
            max-height: 90vh;
            overflow-y: auto;
            animation: slideUp 0.3s ease;
            position: relative;
        }

        /* Modal Header */
        .modal-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 24px 30px;
            border-radius: 16px 16px 0 0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .modal-header h1 {
            font-size: 24px;
            font-weight: 600;
        }

        .close-btn {
            background: rgba(255, 255, 255, 0.2);
            border: none;
            color: white;
            width: 36px;
            height: 36px;
            border-radius: 50%;
            cursor: pointer;
            font-size: 24px;
            line-height: 1;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .close-btn:hover {
            background: rgba(255, 255, 255, 0.3);
            transform: rotate(90deg);
        }

        /* Modal Content */
        .modal-content {
            padding: 30px;
        }

        /* Message Styles - Improved */
        .message-container {
            margin-bottom: 24px;
        }

        .message {
            padding: 16px 20px;
            border-radius: 12px;
            font-size: 15px;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 12px;
            animation: slideDown 0.4s ease;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
        }

        @keyframes slideDown {
            from {
                transform: translateY(-20px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }

        .message svg {
            flex-shrink: 0;
            width: 24px;
            height: 24px;
        }

        .message.success {
            background: linear-gradient(135deg, #d4edda 0%, #c3e6cb 100%);
            color: #155724;
            border-left: 4px solid #28a745;
        }

        .message.error {
            background: linear-gradient(135deg, #f8d7da 0%, #f5c6cb 100%);
            color: #721c24;
            border-left: 4px solid #dc3545;
        }

        .message.info {
            background: linear-gradient(135deg, #d1ecf1 0%, #bee5eb 100%);
            color: #0c5460;
            border-left: 4px solid #17a2b8;
        }

        /* User Info Card */
        .user-info {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            padding: 20px;
            border-radius: 12px;
            margin-bottom: 28px;
            border: 1px solid #dee2e6;
        }

        .user-info h3 {
            color: #667eea;
            margin-bottom: 16px;
            font-size: 18px;
            font-weight: 600;
        }

        .info-row {
            display: grid;
            grid-template-columns: 140px 1fr;
            padding: 10px 0;
            border-bottom: 1px solid #dee2e6;
            gap: 16px;
        }

        .info-row:last-child {
            border-bottom: none;
        }

        .info-label {
            font-weight: 600;
            color: #495057;
            font-size: 14px;
        }

        .info-value {
            color: #212529;
            font-size: 14px;
        }

        /* Section Divider */
        .section {
            margin-bottom: 28px;
        }

        .section h3 {
            color: #333;
            margin-bottom: 18px;
            font-size: 18px;
            font-weight: 600;
            padding-bottom: 10px;
            border-bottom: 2px solid #667eea;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        /* Form Styles */
        .form-group {
            margin-bottom: 18px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #495057;
            font-weight: 600;
            font-size: 14px;
        }

        .form-group input {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #dee2e6;
            border-radius: 8px;
            font-size: 14px;
            transition: all 0.3s ease;
            font-family: inherit;
        }

        .form-group input:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
        }

        .form-group small {
            display: block;
            margin-top: 6px;
            color: #6c757d;
            font-size: 13px;
        }

        /* Buttons */
        .btn-group {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }

        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            font-family: inherit;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
        }

        .btn-secondary {
            background: #6c757d;
            color: white;
        }

        .btn-secondary:hover {
            background: #5a6268;
        }

        /* Password Requirements Box */
        .password-requirements {
            background: linear-gradient(135deg, #fff3cd 0%, #ffeaa7 100%);
            border: 1px solid #ffc107;
            border-left: 4px solid #ffc107;
            padding: 14px 16px;
            border-radius: 8px;
            margin-top: 12px;
            font-size: 13px;
            color: #856404;
        }

        .password-requirements strong {
            display: block;
            margin-bottom: 8px;
            color: #664d03;
        }

        .password-requirements ul {
            margin-left: 20px;
            margin-top: 6px;
        }

        .password-requirements li {
            margin: 4px 0;
        }

        /* Divider */
        .divider {
            height: 1px;
            background: linear-gradient(90deg, transparent, #dee2e6, transparent);
            margin: 28px 0;
        }

        /* Scrollbar Styling */
        .modal-container::-webkit-scrollbar {
            width: 8px;
        }

        .modal-container::-webkit-scrollbar-track {
            background: #f1f1f1;
        }

        .modal-container::-webkit-scrollbar-thumb {
            background: #667eea;
            border-radius: 4px;
        }

        .modal-container::-webkit-scrollbar-thumb:hover {
            background: #764ba2;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .modal-container {
                width: 95%;
                max-height: 95vh;
            }

            .modal-content {
                padding: 20px;
            }

            .modal-header {
                padding: 20px;
            }

            .info-row {
                grid-template-columns: 1fr;
                gap: 4px;
            }

            .btn-group {
                flex-direction: column;
            }

            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="modal-overlay" onclick="closeModalOnOutsideClick(event)">
        <div class="modal-container">
            <!-- Modal Header -->
            <div class="modal-header">
                <h1>⚙️ Profile Settings</h1>
                <button class="close-btn" onclick="closeModal()" title="Close">×</button>
            </div>

            <!-- Modal Content -->
            <div class="modal-content">
                <!-- Messages Section -->
                <% if (successMessage != null && !successMessage.isEmpty()) { %>
                    <div class="message-container">
                        <div class="message success" id="successMsg">
                            <svg viewBox="0 0 24 24" fill="currentColor">
                                <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"/>
                            </svg>
                            <span><%= successMessage %></span>
                        </div>
                    </div>
                <% } %>

                <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
                    <div class="message-container">
                        <div class="message error" id="errorMsg">
                            <svg viewBox="0 0 24 24" fill="currentColor">
                                <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z"/>
                            </svg>
                            <span><%= errorMessage %></span>
                        </div>
                    </div>
                <% } %>

                <% if (infoMessage != null && !infoMessage.isEmpty()) { %>
                    <div class="message-container">
                        <div class="message info" id="infoMsg">
                            <svg viewBox="0 0 24 24" fill="currentColor">
                                <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-6h2v6zm0-8h-2V7h2v2z"/>
                            </svg>
                            <span><%= infoMessage %></span>
                        </div>
                    </div>
                <% } %>

                <!-- Current User Information -->
                <div class="user-info">
                    <h3>📋 Current Information</h3>
                    <div class="info-row">
                        <div class="info-label">Username:</div>
                        <div class="info-value"><%= username %></div>
                    </div>
                    <div class="info-row">
                        <div class="info-label">Full Name:</div>
                        <div class="info-value"><%= fullName %></div>
                    </div>
                    <div class="info-row">
                        <div class="info-label">Contact Number:</div>
                        <div class="info-value"><%= contactNo %></div>
                    </div>
                </div>

                <!-- Update Password Section -->
                <div class="section">
                    <h3>🔒 Update Password</h3>
                    <form action="UpdateProfile" method="post" onsubmit="return validatePasswordForm()">
                        <input type="hidden" name="action" value="updatePassword">
                        
                        <div class="form-group">
                            <label for="currentPassword">Current Password *</label>
                            <input type="password" id="currentPassword" name="currentPassword" required 
                                   placeholder="Enter your current password">
                        </div>

                        <div class="form-group">
                            <label for="newPassword">New Password *</label>
                            <input type="password" id="newPassword" name="newPassword" required 
                                   placeholder="Enter your new password" minlength="6">
                        </div>

                        <div class="form-group">
                            <label for="confirmPassword">Confirm New Password *</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" required 
                                   placeholder="Re-enter your new password" minlength="6">
                        </div>

                        <div class="password-requirements">
                            <strong>Password Requirements:</strong>
                            <ul>
                                <li>Must be at least 6 characters long</li>
                                <li>Must be different from your current password</li>
                                <li>New password and confirmation must match</li>
                            </ul>
                        </div>

                        <div class="btn-group">
                            <button type="submit" class="btn btn-primary">Update Password</button>
                            <button type="reset" class="btn btn-secondary">Clear Form</button>
                        </div>
                    </form>
                </div>

                <div class="divider"></div>

                <!-- Update Contact Number Section -->
                <div class="section">
                    <h3>📱 Update Contact Number</h3>
                    <form action="UpdateProfile" method="post" onsubmit="return validateContactForm()">
                        <input type="hidden" name="action" value="updateContact">
                        
                        <div class="form-group">
                            <label for="newContactNo">New Contact Number *</label>
                            <input type="text" id="newContactNo" name="newContactNo" required 
                                   placeholder="Enter new contact number (e.g., 0771234567)" 
                                   pattern="[0-9]{10}" 
                                   title="Please enter a valid 10-digit phone number"
                                   value="<%= contactNo %>">
                            <small>Format: 10 digits (e.g., 0771234567)</small>
                        </div>

                        <div class="btn-group">
                            <button type="submit" class="btn btn-primary">Update Contact Number</button>
                            <button type="reset" class="btn btn-secondary" onclick="resetContact()">Reset</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Close modal and return to previous page
        function closeModal() {
            window.history.back();
        }

        // Close modal when clicking outside the modal container
        function closeModalOnOutsideClick(event) {
            if (event.target.classList.contains('modal-overlay')) {
                closeModal();
            }
        }

        // ESC key to close modal
        document.addEventListener('keydown', function(event) {
            if (event.key === 'Escape') {
                closeModal();
            }
        });

        // Prevent modal container clicks from closing
        document.querySelector('.modal-container').addEventListener('click', function(event) {
            event.stopPropagation();
        });

        function validatePasswordForm() {
            const currentPassword = document.getElementById('currentPassword').value;
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (newPassword !== confirmPassword) {
                alert('New password and confirmation password do not match!');
                return false;
            }

            if (currentPassword === newPassword) {
                alert('New password must be different from the old password!');
                return false;
            }

            if (newPassword.length < 6) {
                alert('Password must be at least 6 characters long!');
                return false;
            }

            return true;
        }

        function validateContactForm() {
            const newContactNo = document.getElementById('newContactNo').value;
            const currentContactNo = '<%= contactNo %>';

            if (newContactNo === currentContactNo) {
                alert('New contact number is the same as your current contact number. Please enter a different number.');
                return false;
            }

            const phoneRegex = /^[0-9]{10}$/;
            if (!phoneRegex.test(newContactNo)) {
                alert('Please enter a valid 10-digit phone number!');
                return false;
            }

            return true;
        }

        function resetContact() {
            document.getElementById('newContactNo').value = '<%= contactNo %>';
        }

        // Auto-hide messages after 6 seconds with fade out
        window.onload = function() {
            const messages = document.querySelectorAll('.message');
            messages.forEach(function(message) {
                setTimeout(function() {
                    message.style.transition = 'all 0.5s ease';
                    message.style.transform = 'translateY(-10px)';
                    message.style.opacity = '0';
                    setTimeout(function() {
                        message.parentElement.style.display = 'none';
                    }, 500);
                }, 6000);
            });
        };
    </script>
</body>
</html>
