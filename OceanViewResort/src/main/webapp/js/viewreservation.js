// ViewReservation.js - Reservation Management System

let currentReservation = null;
let isEditMode = false;
let allReservations = []; // Store all reservations for pagination
let currentPage = 1;
const rowsPerPage = 10;

// Initialize page on load
document.addEventListener('DOMContentLoaded', function() {
    // Add event listener for search type change
    document.getElementById('searchType').addEventListener('change', function() {
        toggleSearchInput();
    });
    
    // Load all reservations by default
    loadAllReservations();
});

// Toggle search input based on search type
function toggleSearchInput() {
    const searchType = document.getElementById('searchType').value;
    const searchValue = document.getElementById('searchValue');
    const searchDate = document.getElementById('searchDate');
    
    if (searchType === 'reservation_date') {
        searchValue.style.display = 'none';
        searchDate.style.display = 'block';
    } else {
        searchValue.style.display = 'block';
        searchDate.style.display = 'none';
    }
}

// Search reservation
function searchReservation() {
    const searchType = document.getElementById('searchType').value;
    let searchValue;
    
    if (searchType === 'reservation_date') {
        searchValue = document.getElementById('searchDate').value;
    } else {
        searchValue = document.getElementById('searchValue').value.trim();
    }
    
    if (!searchValue) {
        showMessage('Please enter a search value', 'warning');
        return;
    }
    
    // Fetch reservations
    fetch('SearchReservation', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `searchType=${searchType}&searchValue=${encodeURIComponent(searchValue)}`
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            allReservations = data.reservations;
            currentPage = 1;
            displayReservations();
        } else {
            showMessage(data.message || 'No reservations found', 'info');
            document.getElementById('reservationResults').style.display = 'none';
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showMessage('Error searching reservations. Please try again.', 'error');
    });
}

// Load today's check-ins
function loadTodayCheckIns() {
    fetch('GetTodayReservations?type=checkin')
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            allReservations = data.reservations;
            currentPage = 1;
            displayReservations();
            showMessage(`${data.reservations.length} check-in(s) scheduled for today`, 'info');
        } else {
            showMessage('No check-ins scheduled for today', 'info');
            document.getElementById('reservationResults').style.display = 'none';
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showMessage('Error loading today\'s check-ins', 'error');
    });
}

// Load today's check-outs
function loadTodayCheckOuts() {
    fetch('GetTodayReservations?type=checkout')
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            allReservations = data.reservations;
            currentPage = 1;
            displayReservations();
            showMessage(`${data.reservations.length} check-out(s) scheduled for today`, 'info');
        } else {
            showMessage('No check-outs scheduled for today', 'info');
            document.getElementById('reservationResults').style.display = 'none';
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showMessage('Error loading today\'s check-outs', 'error');
    });
}

// Load all reservations
function loadAllReservations() {
    fetch('GetAllReservations')
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            allReservations = data.reservations;
            currentPage = 1;
            displayReservations();
        } else {
            showMessage('No reservations found', 'info');
            document.getElementById('reservationResults').style.display = 'none';
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showMessage('Error loading reservations', 'error');
    });
}

// Display reservations in table with pagination
function displayReservations() {
    const tableBody = document.getElementById('reservationTableBody');
    tableBody.innerHTML = '';
    
    if (allReservations.length === 0) {
        showMessage('No reservations found', 'info');
        document.getElementById('reservationResults').style.display = 'none';
        return;
    }
    
    // Calculate pagination
    const startIndex = (currentPage - 1) * rowsPerPage;
    const endIndex = Math.min(startIndex + rowsPerPage, allReservations.length);
    const pageReservations = allReservations.slice(startIndex, endIndex);
    
    pageReservations.forEach(res => {
        const statusClass = res.reservation_status.toLowerCase().replace(/\s/g, '');
        const row = `
            <tr>
                <td>${res.reservation_no}</td>
                <td>${res.guest_name}</td>
                <td>${res.room_no}</td>
                <td>${res.check_in_date}</td>
                <td>${res.check_out_date}</td>
                <td><span class="status-badge ${statusClass}">${res.reservation_status}</span></td>
                <td>
                    <button onclick='viewReservationDetails(${res.reservation_id})' class="btn btn-primary btn-sm">
                        <i class="fas fa-eye"></i> View
                    </button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
    
    document.getElementById('reservationResults').style.display = 'block';
    renderPagination();
}

// Render pagination controls
function renderPagination() {
    const totalPages = Math.ceil(allReservations.length / rowsPerPage);
    
    // Remove existing pagination if any
    const existingPagination = document.querySelector('.pagination-controls');
    if (existingPagination) {
        existingPagination.remove();
    }
    
    if (totalPages <= 1) return; // No pagination needed
    
    const reservationResults = document.getElementById('reservationResults');
    const paginationDiv = document.createElement('div');
    paginationDiv.className = 'pagination-controls';
    
    let paginationHTML = '<div class="pagination-info">Showing ' + 
        ((currentPage - 1) * rowsPerPage + 1) + ' to ' + 
        Math.min(currentPage * rowsPerPage, allReservations.length) + 
        ' of ' + allReservations.length + ' reservations</div><div class="pagination-buttons">';
    
    // Previous button
    paginationHTML += '<button onclick="changePage(' + (currentPage - 1) + ')" ' +
        'class="btn btn-sm btn-secondary" ' +
        (currentPage === 1 ? 'disabled' : '') + '>' +
        '<i class="fas fa-chevron-left"></i> Previous</button>';
    
    // Page numbers
    const startPage = Math.max(1, currentPage - 2);
    const endPage = Math.min(totalPages, currentPage + 2);
    
    if (startPage > 1) {
        paginationHTML += '<button onclick="changePage(1)" class="btn btn-sm btn-secondary">1</button>';
        if (startPage > 2) paginationHTML += '<span class="pagination-dots">...</span>';
    }
    
    for (let i = startPage; i <= endPage; i++) {
        paginationHTML += '<button onclick="changePage(' + i + ')" ' +
            'class="btn btn-sm ' + (i === currentPage ? 'btn-primary' : 'btn-secondary') + '">' +
            i + '</button>';
    }
    
    if (endPage < totalPages) {
        if (endPage < totalPages - 1) paginationHTML += '<span class="pagination-dots">...</span>';
        paginationHTML += '<button onclick="changePage(' + totalPages + ')" class="btn btn-sm btn-secondary">' + 
            totalPages + '</button>';
    }
    
    // Next button
    paginationHTML += '<button onclick="changePage(' + (currentPage + 1) + ')" ' +
        'class="btn btn-sm btn-secondary" ' +
        (currentPage === totalPages ? 'disabled' : '') + '>' +
        'Next <i class="fas fa-chevron-right"></i></button>';
    
    paginationHTML += '</div>';
    paginationDiv.innerHTML = paginationHTML;
    reservationResults.appendChild(paginationDiv);
}

// Change page
function changePage(page) {
    const totalPages = Math.ceil(allReservations.length / rowsPerPage);
    if (page < 1 || page > totalPages) return;
    currentPage = page;
    displayReservations();
    
    // Scroll to table
    document.getElementById('reservationResults').scrollIntoView({ behavior: 'smooth', block: 'start' });
}

// View reservation details
function viewReservationDetails(reservationId) {
    fetch(`GetReservationDetails?reservationId=${reservationId}`)
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            displayReservationDetails(data.reservation);
        } else {
            showMessage('Error loading reservation details', 'error');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showMessage('Error loading reservation details', 'error');
    });
}

// Display reservation details
function displayReservationDetails(reservation) {
    currentReservation = reservation;
    
    // Populate fields
    document.getElementById('reservationId').value = reservation.reservation_id;
    document.getElementById('detailReservationNo').value = reservation.reservation_no;
    document.getElementById('detailReservationDate').value = reservation.reservation_date;
    document.getElementById('detailStatus').value = reservation.reservation_status;
    document.getElementById('detailGuestName').value = reservation.guest_name;
    document.getElementById('detailGuestContact').value = reservation.contact_no;
    document.getElementById('detailGuestEmail').value = reservation.email;
    document.getElementById('detailRoomNo').value = reservation.room_no;
    document.getElementById('detailRoomType').value = reservation.room_type;
    document.getElementById('detailRoomRate').value = 'Rs. ' + reservation.rate_per_night;
    document.getElementById('detailCheckInDate').value = reservation.check_in_date;
    document.getElementById('detailCheckOutDate').value = reservation.check_out_date;
    
    // Calculate total nights
    const checkIn = new Date(reservation.check_in_date);
    const checkOut = new Date(reservation.check_out_date);
    const nights = Math.ceil((checkOut - checkIn) / (1000 * 60 * 60 * 24));
    document.getElementById('detailTotalNights').value = nights + ' night(s)';
    
    // Show/hide buttons based on status
    updateButtonVisibility(reservation.reservation_status, reservation.check_in_date, reservation.check_out_date);
    
    // Show details section
    document.getElementById('reservationDetailsSection').style.display = 'block';
    
    // Scroll to details
    document.getElementById('reservationDetailsSection').scrollIntoView({ behavior: 'smooth', block: 'start' });
}

// Update button visibility based on reservation status
function updateButtonVisibility(status, checkInDate, checkOutDate) {
    const today = new Date().toISOString().split('T')[0];
    
    // Hide all buttons first
    document.getElementById('editBtn').style.display = 'none';
    document.getElementById('checkInBtn').style.display = 'none';
    document.getElementById('checkOutBtn').style.display = 'none';
    document.getElementById('cancelBtn').style.display = 'none';
    document.getElementById('billingBtn').style.display = 'none';
    
    if (status === 'Confirmed') {
        document.getElementById('editBtn').style.display = 'inline-flex';
        document.getElementById('cancelBtn').style.display = 'inline-flex';
        
        if (checkInDate === today) {
            document.getElementById('checkInBtn').style.display = 'inline-flex';
        }
    } else if (status === 'CheckedIn') {
        document.getElementById('checkOutBtn').style.display = 'inline-flex';
        document.getElementById('editBtn').style.display = 'inline-flex';
    } else if (status === 'CheckedOut') {
        document.getElementById('billingBtn').style.display = 'inline-flex';
    }
}

// Enable edit mode
function enableEdit() {
    const status = document.getElementById('detailStatus').value.trim();
    const checkInInput = document.getElementById('detailCheckInDate');
    const checkOutInput = document.getElementById('detailCheckOutDate');
    
    if (status === 'CheckedIn') {
        checkInInput.readOnly = true;
        checkInInput.style.backgroundColor = '#f3f4f6';
        checkInInput.style.cursor = 'not-allowed';
        
        checkOutInput.readOnly = false;
        checkOutInput.style.backgroundColor = 'white';
        checkOutInput.style.cursor = 'text';
        
        document.getElementById('editCheckInLabel').style.display = 'none';
        document.getElementById('editCheckOutLabel').style.display = 'inline';
        document.getElementById('editCheckOutLabel').textContent = '(Extendable)';
        
        showNotification('You can only extend the check-out date. Check-in date cannot be changed after guest has checked in.', 'info');
    } 
    else if (status === 'Confirmed') {
        checkInInput.readOnly = false;
        checkInInput.style.backgroundColor = 'white';
        checkInInput.style.cursor = 'text';
        
        checkOutInput.readOnly = false;
        checkOutInput.style.backgroundColor = 'white';
        checkOutInput.style.cursor = 'text';
        
        document.getElementById('editCheckInLabel').style.display = 'inline';
        document.getElementById('editCheckOutLabel').style.display = 'inline';
    }
    else {
        showNotification('Cannot edit dates for ' + status + ' reservations.', 'error');
        return;
    }
    
    document.getElementById('editBtn').style.display = 'none';
    document.getElementById('saveBtn').style.display = 'inline-flex';
    document.getElementById('cancelEditBtn').style.display = 'inline-flex';
}

// Cancel edit mode
function cancelEdit() {
    isEditMode = false;
    
    document.getElementById('detailCheckInDate').value = currentReservation.check_in_date;
    document.getElementById('detailCheckOutDate').value = currentReservation.check_out_date;
    
    document.getElementById('detailCheckInDate').readOnly = true;
    document.getElementById('detailCheckOutDate').readOnly = true;
    
    document.getElementById('detailCheckInDate').style.backgroundColor = '#e9ecef';
    document.getElementById('detailCheckOutDate').style.backgroundColor = '#e9ecef';
    
    document.getElementById('editCheckInLabel').style.display = 'none';
    document.getElementById('editCheckOutLabel').style.display = 'none';
    
    updateButtonVisibility(currentReservation.reservation_status, currentReservation.check_in_date, currentReservation.check_out_date);
    document.getElementById('saveBtn').style.display = 'none';
    document.getElementById('cancelEditBtn').style.display = 'none';
    
    hideMessage();
}

// Save updated dates
function saveUpdates() {
    const reservationId = document.getElementById('reservationId').value;
    const newCheckInDate = document.getElementById('detailCheckInDate').value;
    const newCheckOutDate = document.getElementById('detailCheckOutDate').value;
    
    const roomId = currentReservation.room_id;
    const oldCheckIn = currentReservation.check_in_date;
    const oldCheckOut = currentReservation.check_out_date;
    const reservationStatus = document.getElementById('detailStatus').value.trim();
    
    if (!newCheckInDate || !newCheckOutDate) {
        showNotification('Please select both check-in and check-out dates', 'error');
        return;
    }
    
    if (new Date(newCheckOutDate) <= new Date(newCheckInDate)) {
        showNotification('Check-out date must be after check-in date', 'error');
        return;
    }
    
    const data = {
        reservationId: parseInt(reservationId),
        checkInDate: newCheckInDate,
        checkOutDate: newCheckOutDate,
        roomId: roomId,
        oldCheckIn: oldCheckIn,
        oldCheckOut: oldCheckOut,
        reservationStatus: reservationStatus
    };
    
    fetch('UpdateReservationDates', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            showNotification(data.message, 'success');
            setTimeout(() => location.reload(), 1500);
        } else {
            showNotification(data.message, 'error');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showNotification('Failed to update reservation dates', 'error');
    });
}

// Check-in guest
function checkInGuest() {
    const reservationId = document.getElementById('reservationId').value;
    const roomId = currentReservation.room_id;
    
    if (!confirm('Confirm check-in for this guest?')) {
        return;
    }
    
    fetch('CheckInGuest', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            reservationId: parseInt(reservationId),
            roomId: roomId
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            showMessage('Guest checked in successfully!', 'success');
            setTimeout(() => viewReservationDetails(reservationId), 1000);
        } else {
            showMessage(data.message || 'Error checking in guest', 'error');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showMessage('Error checking in guest', 'error');
    });
}

// Check-out guest
function checkOutGuest() {
    const reservationId = document.getElementById('reservationId').value;
    const roomId = currentReservation.room_id;
    
    if (!confirm('Confirm check-out for this guest?')) {
        return;
    }
    
    fetch('CheckOutGuest', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            reservationId: parseInt(reservationId),
            roomId: roomId
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            if (data.action === 'proceed_to_billing') {
                showNotification(data.message, 'success');
                setTimeout(() => {
                    proceedToBilling();
                }, 1500);
            } else {
                showNotification(data.message, 'success');
                setTimeout(() => viewReservationDetails(reservationId), 1000);
            }
        } else if (data.action === 'update_required') {
            showNotification(data.message, 'warning');
            
            setTimeout(() => {
                enableEdit();
            }, 2000);
        } else {
            showNotification(data.message || 'Error checking out guest', 'error');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showNotification('Error checking out guest', 'error');
    });
}

// Cancel reservation
function cancelReservation() {
    const reservationId = document.getElementById('reservationId').value;
    
    if (!confirm('Are you sure you want to cancel this reservation? This action cannot be undone.')) {
        return;
    }
    
    fetch('CancelReservation', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            reservationId: parseInt(reservationId)
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            showMessage('Reservation cancelled successfully', 'success');
            setTimeout(() => viewReservationDetails(reservationId), 1000);
        } else {
            showMessage(data.message || 'Error cancelling reservation', 'error');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showMessage('Error cancelling reservation', 'error');
    });
}

// Proceed to billing
function proceedToBilling() { 
    const reservationNo = document.getElementById('detailReservationNo').value;
    window.location.href = `billing.jsp?reservationNo=${encodeURIComponent(reservationNo)}`;
}

// Show notification
function showNotification(message, type) {
    if (!type) type = 'info';
    
    const notification = document.createElement('div');
    notification.className = 'notification notification-' + type;
    
    var bgColor = '#3b82f6';
    if (type === 'success') bgColor = '#10b981';
    if (type === 'error') bgColor = '#ef4444';
    if (type === 'warning') bgColor = '#f59e0b';
    if (type === 'info') bgColor = '#3b82f6';
    
    notification.style.cssText = 
        'position: fixed;' +
        'top: 20px;' +
        'right: 20px;' +
        'background: ' + bgColor + ';' +
        'color: white;' +
        'padding: 1rem 1.5rem;' +
        'border-radius: 8px;' +
        'box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);' +
        'z-index: 9999;' +
        'animation: slideInRight 0.3s ease;' +
        'font-family: Poppins, sans-serif;' +
        'display: flex;' +
        'align-items: center;' +
        'gap: 0.8rem;' +
        'max-width: 450px;';
    
    var icon = 'fa-info-circle';
    if (type === 'success') icon = 'fa-check-circle';
    if (type === 'error') icon = 'fa-exclamation-circle';
    if (type === 'warning') icon = 'fa-exclamation-triangle';
    if (type === 'info') icon = 'fa-info-circle';
    
    notification.innerHTML = 
        '<i class="fas ' + icon + '"></i>' +
        '<span>' + message + '</span>';
    
    document.body.appendChild(notification);
    
    const duration = (type === 'info') ? 5000 : 3000;
    
    setTimeout(function() {
        notification.style.animation = 'slideOutRight 0.3s ease';
        setTimeout(function() { 
            notification.remove(); 
        }, 300);
    }, duration);
}

// Show message in message area (NO ICON - FIX FOR DOUBLE ICON)
function showMessage(message, type) {
    const messageArea = document.getElementById('messageArea');
    messageArea.className = 'message-area ' + type;
    messageArea.textContent = message;
    messageArea.style.display = 'block';
    
    // Auto-hide success and info messages after 5 seconds
    if (type === 'success' || type === 'info') {
        setTimeout(() => {
            hideMessage();
        }, 5000);
    }
    
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// Hide message
function hideMessage() {
    const messageArea = document.getElementById('messageArea');
    messageArea.style.display = 'none';
}

// Animation styles
const style = document.createElement('style');
style.textContent = `
    @keyframes slideInRight {
        from {
            transform: translateX(400px);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    @keyframes slideOutRight {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(400px);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);