// viewstaff.js - View Staff Management

let allStaff = [];

// Load staff on page load
document.addEventListener('DOMContentLoaded', function() {
    loadAllStaff();
});

// Load all staff members
function loadAllStaff() {
    showLoading(true);
    
    fetch('GetAllStaff')
    .then(response => response.json())
    .then(data => {
        showLoading(false);
        
        if (data.success) {
            allStaff = data.staff;
            displayStaff(allStaff);
            document.getElementById('totalStaff').textContent = allStaff.length;
        } else {
            showAlert(data.message || 'Error loading staff members.', 'error');
        }
    })
    .catch(error => {
        showLoading(false);
        console.error('Error:', error);
        showAlert('Error loading staff members. Please try again.', 'error');
    });
}

// Display staff in table
function displayStaff(staffList) {
    const tableBody = document.getElementById('staffTableBody');
    const emptyState = document.getElementById('emptyState');
    const showingCount = document.getElementById('showingCount');
    
    tableBody.innerHTML = '';
    
    if (staffList.length === 0) {
        emptyState.style.display = 'block';
        showingCount.textContent = 'No staff members found';
        return;
    }
    
    emptyState.style.display = 'none';
    showingCount.textContent = `Showing ${staffList.length} staff member${staffList.length > 1 ? 's' : ''}`;
    
    staffList.forEach(staff => {
        const row = `
            <tr id="row-${staff.staff_id}">
                <td>
                    <span class="id-badge">${staff.staff_id}</span>
                </td>
                <td>
                    <span class="username-badge">
                        <i class="fas fa-at"></i> ${staff.username}
                    </span>
                </td>
                <td>
                    <span class="full-name">${staff.full_name}</span>
                </td>
                <td>
                    <span class="contact-no">
                        <i class="fas fa-phone" style="color: #94a3b8; margin-right: 6px; font-size: 0.85rem;"></i>
                        ${staff.contact_no}
                    </span>
                </td>
                <td>
                    <button class="btn-remove" onclick="openDeleteModal(${staff.staff_id}, '${staff.full_name}')">
                        <i class="fas fa-trash"></i> Remove
                    </button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}

// Filter staff by name or username
function filterStaff() {
    const searchInput = document.getElementById('searchInput');
    const searchTerm = searchInput.value.trim().toLowerCase();
    const clearBtn = document.getElementById('clearSearch');
    
    // Show/hide clear button
    clearBtn.style.display = searchTerm ? 'flex' : 'none';
    
    if (!searchTerm) {
        displayStaff(allStaff);
        return;
    }
    
    // Filter by name or username
    const filtered = allStaff.filter(staff => 
        staff.full_name.toLowerCase().includes(searchTerm) ||
        staff.username.toLowerCase().includes(searchTerm)
    );
    
    displayStaff(filtered);
}

// Clear search input
function clearSearchInput() {
    document.getElementById('searchInput').value = '';
    document.getElementById('clearSearch').style.display = 'none';
    displayStaff(allStaff);
}

// Open delete modal
function openDeleteModal(staffId, staffName) {
    document.getElementById('deleteStaffId').value = staffId;
    document.getElementById('deleteStaffName').textContent = staffName;
    document.getElementById('deleteModal').style.display = 'flex';
}

// Close delete modal
function closeDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none';
    document.getElementById('deleteStaffId').value = '';
    document.getElementById('deleteStaffName').textContent = '';
}

// Confirm delete
function confirmDelete() {
    const staffId = document.getElementById('deleteStaffId').value;
    
    fetch('DeleteStaff', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `staffId=${staffId}`
    })
    .then(response => response.json())
    .then(data => {
        closeDeleteModal();
        
        if (data.success) {
            // Remove row from table with animation
            const row = document.getElementById('row-' + staffId);
            if (row) {
                row.style.transition = 'all 0.3s ease';
                row.style.opacity = '0';
                row.style.transform = 'translateX(20px)';
                setTimeout(() => {
                    row.remove();
                    // Update allStaff array
                    allStaff = allStaff.filter(s => s.staff_id != staffId);
                    document.getElementById('totalStaff').textContent = allStaff.length;
                    document.getElementById('showingCount').textContent = 
                        `Showing ${allStaff.length} staff member${allStaff.length !== 1 ? 's' : ''}`;
                    
                    // Show empty state if no staff left
                    if (allStaff.length === 0) {
                        document.getElementById('emptyState').style.display = 'block';
                    }
                }, 300);
            }
            showAlert(data.message, 'success');
        } else {
            showAlert(data.message, 'error');
        }
    })
    .catch(error => {
        closeDeleteModal();
        console.error('Error:', error);
        showAlert('Error removing staff member. Please try again.', 'error');
    });
}

// Show loading state
function showLoading(show) {
    document.getElementById('loadingState').style.display = show ? 'block' : 'none';
    document.getElementById('staffTable').style.display = show ? 'none' : 'table';
}

// Show alert message
function showAlert(message, type) {
    const alertArea = document.getElementById('alertArea');
    alertArea.innerHTML = `
        <div class="alert alert-${type}">
            <i class="fas fa-${type === 'success' ? 'check-circle' : 'exclamation-circle'}"></i>
            ${message}
            <button class="alert-close" onclick="this.parentElement.remove()">
                <i class="fas fa-times"></i>
            </button>
        </div>
    `;
    alertArea.style.display = 'block';
    
    // Auto dismiss success after 5 seconds
    if (type === 'success') {
        setTimeout(() => {
            alertArea.style.display = 'none';
        }, 5000);
    }
    
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// Close modal on outside click
document.getElementById('deleteModal').addEventListener('click', function(e) {
    if (e.target === this) {
        closeDeleteModal();
    }
});

// ESC key to close modal
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        closeDeleteModal();
    }
});