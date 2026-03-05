<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Staff - Ocean View Resort</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="adminstyle.css">
    <link rel="stylesheet" type="text/css" href="css/addstaff.css">
</head>
<body>
    <%@ include file="adminheader.jsp" %>
    <%@ include file="adminsidebar.jsp" %>

    <div class="main-content">
        <div class="dashboard-content">

            <!-- Page Header -->
            <section class="page-header">
                <div class="page-header-left">
                    <h2><i class="fas fa-user-plus"></i> Add New Staff Member</h2>
                    <p>Create a new staff account with role-based access to the Ocean View Resort system.</p>
                </div>
                <div class="breadcrumb">
                    <a href="admindashboard.jsp"><i class="fas fa-home"></i> Dashboard</a>
                    <span><i class="fas fa-chevron-right"></i></span>
                    <span class="active">Add Staff</span>
                </div>
            </section>

            <%-- SUCCESS banner: shown after redirect from servlet (?success=true&name=...) --%>
            <%
                String successFlag = request.getParameter("success");
                String addedName   = request.getParameter("name");
                if ("true".equals(successFlag) && addedName != null && !addedName.isEmpty()) {
            %>
            <div class="alert alert-success" id="successAlert">
                <i class="fas fa-check-circle"></i>
                Staff member <strong><%= java.net.URLDecoder.decode(addedName, "UTF-8") %></strong> was added successfully!
                <button class="alert-close" onclick="this.parentElement.remove()"><i class="fas fa-times"></i></button>
            </div>
            <% } %>

            <%-- ERROR banner: shown after forward from servlet (request attribute) --%>
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null && !errorMessage.isEmpty()) {
            %>
            <div class="alert alert-error" id="errorAlert">
                <i class="fas fa-exclamation-circle"></i>
                <%= errorMessage %>
                <button class="alert-close" onclick="this.parentElement.remove()"><i class="fas fa-times"></i></button>
            </div>
            <% } %>

            <%-- Restore entered values so the user doesn't retype everything --%>
            <%
                String prevFullName  = (String) request.getAttribute("fullName");
                String prevContactNo = (String) request.getAttribute("contactNo");
                String prevUsername  = (String) request.getAttribute("username");
                if (prevFullName  == null) prevFullName  = "";
                if (prevContactNo == null) prevContactNo = "";
                if (prevUsername  == null) prevUsername  = "";
            %>

            <!-- Form Card -->
            <section class="form-card">
                <div class="form-card-header">
                    <div class="form-card-title">
                        <div class="form-card-icon">
                            <i class="fas fa-id-card"></i>
                        </div>
                        <div>
                            <h3>Staff Information</h3>
                            <p>Fill in the details below to register a new staff member.</p>
                        </div>
                    </div>
                </div>

                <form id="addStaffForm" action="AddStaff" method="post" novalidate>
                    <div class="form-grid">

                        <!-- Full Name -->
                        <div class="form-group">
                            <label for="fullName" class="form-label">
                                <i class="fas fa-user"></i> Full Name
                                <span class="required-star">*</span>
                            </label>
                            <div class="input-wrapper">
                                <input type="text" id="fullName" name="full_name"
                                    class="form-input"
                                    placeholder="e.g. Saleem Mohamed Amal Rushdi"
                                    value="<%= prevFullName %>"
                                    required autocomplete="off" />
                                <span class="input-icon"><i class="fas fa-user"></i></span>
                            </div>
                            <span class="form-error" id="fullNameError">Please enter the full name.</span>
                        </div>

                        <!-- Contact Number -->
                        <div class="form-group">
                            <label for="contactNo" class="form-label">
                                <i class="fas fa-phone"></i> Contact Number
                                <span class="required-star">*</span>
                            </label>
                            <div class="input-wrapper">
                                <input type="tel" id="contactNo" name="contact_no"
                                    class="form-input"
                                    placeholder="e.g. 0764385666"
                                    value="<%= prevContactNo %>"
                                    required autocomplete="off" maxlength="15" />
                                <span class="input-icon"><i class="fas fa-phone"></i></span>
                            </div>
                            <span class="form-error" id="contactNoError">Please enter a valid contact number.</span>
                        </div>

                        <!-- Username -->
                        <div class="form-group">
                            <label for="username" class="form-label">
                                <i class="fas fa-at"></i> Username
                                <span class="required-star">*</span>
                            </label>
                            <div class="input-wrapper">
                                <input type="text" id="username" name="username"
                                    class="form-input"
                                    placeholder="e.g. ovr_amal"
                                    value="<%= prevUsername %>"
                                    required autocomplete="off" />
                                <span class="input-icon"><i class="fas fa-at"></i></span>
                            </div>
                            <span class="form-error" id="usernameError">Please enter a username.</span>
                        </div>

                        <!-- Password -->
                        <div class="form-group">
                            <label for="password" class="form-label">
                                <i class="fas fa-lock"></i> Password
                                <span class="required-star">*</span>
                            </label>
                            <div class="input-wrapper">
                                <input type="password" id="password" name="password"
                                    class="form-input"
                                    placeholder="Enter a secure password"
                                    required autocomplete="new-password" />
                                <span class="input-icon"><i class="fas fa-lock"></i></span>
                                <button type="button" class="toggle-password"
                                        onclick="togglePassword()" title="Show/Hide Password">
                                    <i class="fas fa-eye" id="eyeIcon"></i>
                                </button>
                            </div>
                            <span class="form-error" id="passwordError">Please enter a password.</span>
                        </div>

                    </div><!-- end form-grid -->

                    <div class="form-actions">
                        <button type="button" class="btn btn-secondary" onclick="resetForm()">
                            <i class="fas fa-undo"></i> Reset
                        </button>
                        <button type="submit" class="btn btn-primary" id="submitBtn">
                            <i class="fas fa-user-plus"></i> Add Staff Member
                        </button>
                    </div>
                </form>
            </section>

            <!-- Info Note -->
            <section class="info-card">
                <div class="info-icon"><i class="fas fa-info-circle"></i></div>
                <div class="info-content">
                    <h4>Account Information</h4>
                    <p>The new staff member will be able to log in using their username and password.
                       Share credentials securely. Manage or deactivate accounts from
                       <a href="viewstaff.jsp">View Staff</a>.</p>
                </div>
            </section>

        </div>
    </div>

    <script>
        function togglePassword() {
            const pw = document.getElementById('password');
            const icon = document.getElementById('eyeIcon');
            if (pw.type === 'password') {
                pw.type = 'text';
                icon.classList.replace('fa-eye', 'fa-eye-slash');
            } else {
                pw.type = 'password';
                icon.classList.replace('fa-eye-slash', 'fa-eye');
            }
        }

        function resetForm() {
            document.getElementById('addStaffForm').reset();
            document.querySelectorAll('.form-input').forEach(i => i.classList.remove('input-error', 'input-success'));
            document.querySelectorAll('.form-error').forEach(e => e.classList.remove('visible'));
            const pw = document.getElementById('password');
            const icon = document.getElementById('eyeIcon');
            pw.type = 'password';
            icon.classList.replace('fa-eye-slash', 'fa-eye');
        }

        function showError(inputId, errorId, msg) {
            const input = document.getElementById(inputId);
            const err   = document.getElementById(errorId);
            input.classList.add('input-error');
            input.classList.remove('input-success');
            err.textContent = msg;
            err.classList.add('visible');
        }

        function showSuccess(inputId, errorId) {
            document.getElementById(inputId).classList.remove('input-error');
            document.getElementById(inputId).classList.add('input-success');
            document.getElementById(errorId).classList.remove('visible');
        }

        // Live blur validation
        document.getElementById('fullName').addEventListener('blur', function () {
            const v = this.value.trim();
            if (!v) showError('fullName', 'fullNameError', 'Please enter the full name.');
            else if (v.length < 3) showError('fullName', 'fullNameError', 'At least 3 characters required.');
            else showSuccess('fullName', 'fullNameError');
        });

        document.getElementById('contactNo').addEventListener('blur', function () {
            const v = this.value.trim();
            if (!v) showError('contactNo', 'contactNoError', 'Please enter a contact number.');
            else if (!/^[0-9+\-\s]{7,15}$/.test(v)) showError('contactNo', 'contactNoError', 'Valid phone: 7–15 digits.');
            else showSuccess('contactNo', 'contactNoError');
        });

        document.getElementById('username').addEventListener('blur', function () {
            const v = this.value.trim();
            if (!v) showError('username', 'usernameError', 'Please enter a username.');
            else if (!/^[a-zA-Z0-9_]{3,20}$/.test(v)) showError('username', 'usernameError', '3–20 chars, letters/numbers/underscores.');
            else showSuccess('username', 'usernameError');
        });

        document.getElementById('password').addEventListener('blur', function () {
            const v = this.value;
            if (!v) showError('password', 'passwordError', 'Please enter a password.');
            else if (v.length < 6) showError('password', 'passwordError', 'At least 6 characters required.');
            else showSuccess('password', 'passwordError');
        });

        // Submit validation
        document.getElementById('addStaffForm').addEventListener('submit', function (e) {
            let valid = true;
            const fn = document.getElementById('fullName').value.trim();
            const cn = document.getElementById('contactNo').value.trim();
            const un = document.getElementById('username').value.trim();
            const pw = document.getElementById('password').value;

            if (!fn || fn.length < 3) { showError('fullName',  'fullNameError',  'Full name: at least 3 characters.'); valid = false; }
            else showSuccess('fullName', 'fullNameError');

            if (!cn || !/^[0-9+\-\s]{7,15}$/.test(cn)) { showError('contactNo', 'contactNoError', 'Valid phone: 7–15 digits.'); valid = false; }
            else showSuccess('contactNo', 'contactNoError');

            if (!un || !/^[a-zA-Z0-9_]{3,20}$/.test(un)) { showError('username',  'usernameError',  '3–20 chars, letters/numbers/underscores.'); valid = false; }
            else showSuccess('username', 'usernameError');

            if (!pw || pw.length < 6) { showError('password',  'passwordError',  'At least 6 characters required.'); valid = false; }
            else showSuccess('password', 'passwordError');

            if (!valid) {
                e.preventDefault();
                const first = document.querySelector('.input-error');
                if (first) { first.scrollIntoView({ behavior: 'smooth', block: 'center' }); first.focus(); }
            } else {
                const btn = document.getElementById('submitBtn');
                btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Adding Staff...';
                btn.disabled = true;
            }
        });

        // Auto-dismiss success alert after 5 s
        const sa = document.getElementById('successAlert');
        if (sa) {
            setTimeout(() => {
                sa.style.transition = 'opacity 0.5s';
                sa.style.opacity = '0';
                setTimeout(() => sa.remove(), 500);
            }, 5000);
        }
    </script>
</body>
</html>
