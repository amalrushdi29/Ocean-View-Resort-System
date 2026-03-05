// Billing.js - Billing Management System

let currentReservation = null;

// Initialize page on load
document.addEventListener('DOMContentLoaded', function() {
    // Check if coming from ViewReservation with reservationNo
    const urlParams = new URLSearchParams(window.location.search);
    const reservationNo = urlParams.get('reservationNo');
    
    if (reservationNo) {
        // Auto-fill reservation number field
        document.getElementById('reservationNumber').value = reservationNo;
        
        // Auto-search after a short delay to ensure page is ready
        setTimeout(function() {
            searchByReservationNumber(reservationNo);
        }, 300);
    }
});

// Search reservation
function searchReservation() {
    const reservationNumber = document.getElementById('reservationNumber').value.trim();
    
    if (!reservationNumber) {
        showMessage('Please enter a reservation number', 'warning');
        return;
    }
    
    searchByReservationNumber(reservationNumber);
}

// Search by reservation number
function searchByReservationNumber(reservationNumber) {
    // Fetch reservation details
    fetch('SearchReservationForBilling', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `searchType=reservationNo&searchValue=${encodeURIComponent(reservationNumber)}`
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            displayBillingDetails(data.reservation);
            hideMessage();
        } else {
            showMessage(data.message, 'error');
            document.getElementById('billingDetailsSection').style.display = 'none';
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showMessage('Error searching reservation. Please try again.', 'error');
    });
}

// Display billing details
function displayBillingDetails(reservation) {
    currentReservation = reservation;
    
    // Populate fields
    document.getElementById('reservationId').value = reservation.reservationId;
    document.getElementById('guestName').value = reservation.guestName;
    document.getElementById('roomNo').value = reservation.roomNo;
    document.getElementById('roomType').value = reservation.roomType;
    document.getElementById('checkInDate').value = reservation.checkInDate;
    document.getElementById('checkOutDate').value = reservation.checkOutDate;
    document.getElementById('nights').value = reservation.nights + ' night(s)';
    document.getElementById('ratePerNight').value = 'LKR ' + formatCurrency(reservation.ratePerNight);
    
    // Update charges
    document.getElementById('roomCharges').textContent = 'LKR ' + formatCurrency(reservation.roomCharges);
    document.getElementById('totalAmount').textContent = 'LKR ' + formatCurrency(reservation.totalAmount);
    
    // Show billing details section
    document.getElementById('billingDetailsSection').style.display = 'block';
    
    // Reset buttons
    document.getElementById('generateBtn').style.display = 'inline-flex';
    document.getElementById('printBtn').style.display = 'none';
    
    // Scroll to billing details
    document.getElementById('billingDetailsSection').scrollIntoView({ behavior: 'smooth', block: 'start' });
}

// Generate bill
function generateBill() {
    const reservationId = document.getElementById('reservationId').value;
    const roomCharges = currentReservation.roomCharges;
    const totalAmount = currentReservation.totalAmount;
    
    // Get selected payment method
    const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
    
    if (!paymentMethod) {
        showMessage('Please select a payment method', 'warning');
        return;
    }
    
    if (!confirm('Generate bill for this reservation?')) {
        return;
    }
    
    const data = {
        reservationId: parseInt(reservationId),
        roomCharges: roomCharges,
        totalAmount: totalAmount,
        paymentMethod: paymentMethod
    };
    
    fetch('GenerateBill', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            showMessage(data.message + ' Bill No: ' + data.billNo, 'success');
            
            // Store bill ID for printing
            document.getElementById('billId').value = data.billId;
            
            // Hide generate button, show print button
            document.getElementById('generateBtn').style.display = 'none';
            document.getElementById('printBtn').style.display = 'inline-flex';
        } else {
            showMessage(data.message, 'error');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showMessage('Error generating bill. Please try again.', 'error');
    });
}

// Print receipt - UPDATED (no new tab)
function printReceipt() {
    const billId = document.getElementById('billId').value;
    
    if (!billId) {
        showMessage('No bill found to print', 'error');
        return;
    }
    
    // Create invisible download link
    const downloadUrl = 'PrintReceipt?billId=' + billId;
    const link = document.createElement('a');
    link.href = downloadUrl;
    link.download = 'Receipt_' + billId + '.pdf';
    link.style.display = 'none';
    
    // Add to page, click, and remove
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    
    // Show success message
    showNotification('Receipt downloaded successfully!', 'success');
}

// Clear form and return to home
function clearForm() {
    if (confirm('Are you sure you want to cancel? All unsaved data will be lost.')) {
        window.location.href = 'home.jsp';
    }
}

// Format currency
function formatCurrency(amount) {
    return parseFloat(amount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
}

// Show message (FIXED - No icon, auto-hide all types)
function showMessage(message, type) {
    const messageArea = document.getElementById('messageArea');
    messageArea.className = 'message-area ' + type;
    messageArea.textContent = message; // Changed from innerHTML to textContent - NO ICON
    messageArea.style.display = 'block';
    
    // Auto-hide all message types after 5 seconds
    setTimeout(() => {
        hideMessage();
    }, 5000);
    
    // Scroll to message
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// Hide message
function hideMessage() {
    const messageArea = document.getElementById('messageArea');
    messageArea.style.display = 'none';
}

// Show notification (toast style)
function showNotification(message, type) {
    if (!type) type = 'info';
    
    const notification = document.createElement('div');
    notification.className = 'notification notification-' + type;
    
    var bgColor = '#3b82f6';
    if (type === 'success') bgColor = '#10b981';
    if (type === 'error') bgColor = '#ef4444';
    if (type === 'warning') bgColor = '#f59e0b';
    
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
        'max-width: 400px;';
    
    var icon = 'fa-info-circle';
    if (type === 'success') icon = 'fa-check-circle';
    if (type === 'error') icon = 'fa-exclamation-circle';
    if (type === 'warning') icon = 'fa-exclamation-triangle';
    
    notification.innerHTML = 
        '<i class="fas ' + icon + '"></i>' +
        '<span>' + message + '</span>';
    
    document.body.appendChild(notification);
    
    setTimeout(function() {
        notification.style.animation = 'slideOutRight 0.3s ease';
        setTimeout(function() { 
            notification.remove(); 
        }, 300);
    }, 3000);
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