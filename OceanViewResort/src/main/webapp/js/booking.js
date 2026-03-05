// Booking.js - Hotel Reservation System

// Initialize page on load
document.addEventListener('DOMContentLoaded', function() {
    initializePage();
    
    // Allow Enter key to trigger search
    const guestSearchInput = document.getElementById('guestSearch');
    if (guestSearchInput) {
        guestSearchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                searchGuest();
            }
        });
    }
});

function initializePage() {
    // Set today's date as reservation date
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('reservationDate').value = today;
    
    // Set minimum check-in date to today
    document.getElementById('checkInDate').min = today;
    
    // Generate reservation number
    generateReservationNumber();
    
    // Add event listener for check-in date to update check-out minimum
    document.getElementById('checkInDate').addEventListener('change', function() {
        const checkInDate = this.value;
        document.getElementById('checkOutDate').min = checkInDate;
        
        // Clear check-out if it's before check-in
        const checkOutDate = document.getElementById('checkOutDate').value;
        if (checkOutDate && checkOutDate <= checkInDate) {
            document.getElementById('checkOutDate').value = '';
        }
    });
}

// Generate unique reservation number
function generateReservationNumber() {
    const prefix = 'RES';
    const timestamp = Date.now();
    const random = Math.floor(Math.random() * 1000).toString().padStart(3, '0');
    const reservationNo = `${prefix}${timestamp}${random}`;
    document.getElementById('reservationNo').value = reservationNo;
}

// Search for guests
function searchGuest() {
    const searchTerm = document.getElementById('guestSearch').value.trim();
    
    if (searchTerm === '') {
        showMessage('Please enter a search term', 'warning');
        return;
    }
    
    // Show loading
    const searchBtn = event.target;
    const originalText = searchBtn.innerHTML;
    searchBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Searching...';
    searchBtn.disabled = true;
    
    // AJAX request to search guests
    fetch('SearchGuest', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'searchTerm=' + encodeURIComponent(searchTerm)
    })
    .then(response => response.json())
    .then(data => {
        searchBtn.innerHTML = originalText;
        searchBtn.disabled = false;
        
        if (data.success) {
            displayGuestResults(data.guests);
        } else {
            showMessage(data.message || 'No guests found', 'info');
            document.getElementById('guestResults').style.display = 'none';
        }
    })
    .catch(error => {
        console.error('Error:', error);
        searchBtn.innerHTML = originalText;
        searchBtn.disabled = false;
        showMessage('Error searching guests. Please try again.', 'error');
    });
}

// Display guest search results
function displayGuestResults(guests) {
    const tableBody = document.getElementById('guestTableBody');
    tableBody.innerHTML = '';
    
    if (guests.length === 0) {
        showMessage('No guests found matching your search', 'info');
        document.getElementById('guestResults').style.display = 'none';
        return;
    }
    
    guests.forEach(guest => {
        const row = `
            <tr>
                <td>${guest.guest_id}</td>
                <td>${guest.name}</td>
                <td>${guest.contact_no}</td>
                <td>${guest.email}</td>
                <td>${guest.nic_ppt}</td>
                <td>
                    <button onclick='selectGuest(${JSON.stringify(guest)})' class="btn btn-primary btn-sm">
                        <i class="fas fa-check"></i> Select
                    </button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
    
    document.getElementById('guestResults').style.display = 'block';
}

// Select a guest
function selectGuest(guest) {
    document.getElementById('selectedGuestId').value = guest.guest_id;
    document.getElementById('selectedGuestName').value = guest.name;
    document.getElementById('selectedGuestContact').value = guest.contact_no;
    document.getElementById('selectedGuestEmail').value = guest.email;
    
    // Show selected guest section
    document.getElementById('selectedGuestSection').style.display = 'block';
    document.getElementById('reservationDetailsSection').style.display = 'block';
    
    // Hide guest results
    document.getElementById('guestResults').style.display = 'none';
    document.getElementById('guestSearch').value = '';
    
    showMessage('Guest selected successfully. Please enter reservation details.', 'success');
    
    // Scroll to reservation details
    document.getElementById('reservationDetailsSection').scrollIntoView({ behavior: 'smooth', block: 'start' });
}

// Check room availability
function checkAvailability() {
    const checkInDate = document.getElementById('checkInDate').value;
    const checkOutDate = document.getElementById('checkOutDate').value;
    const roomType = document.getElementById('roomType').value;
    
    // Validation
    if (!checkInDate) {
        showMessage('Please select check-in date', 'warning');
        return;
    }
    
    if (!checkOutDate) {
        showMessage('Please select check-out date', 'warning');
        return;
    }
    
    if (!roomType) {
        showMessage('Please select room type', 'warning');
        return;
    }
    
    if (checkOutDate <= checkInDate) {
        showMessage('Check-out date must be after check-in date', 'warning');
        return;
    }
    
    // Show loading
    const availBtn = event.target;
    const originalText = availBtn.innerHTML;
    availBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Checking...';
    availBtn.disabled = true;
    
    // AJAX request to check availability
    fetch('CheckAvailability', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `checkInDate=${checkInDate}&checkOutDate=${checkOutDate}&roomType=${encodeURIComponent(roomType)}`
    })
    .then(response => response.json())
    .then(data => {
        availBtn.innerHTML = originalText;
        availBtn.disabled = false;
        
        if (data.success) {
            displayAvailableRooms(data.rooms);
        } else {
            showMessage(data.message || 'No rooms available for the selected dates and type', 'info');
            document.getElementById('availableRoomsSection').style.display = 'none';
        }
    })
    .catch(error => {
        console.error('Error:', error);
        availBtn.innerHTML = originalText;
        availBtn.disabled = false;
        showMessage('Error checking availability. Please try again.', 'error');
    });
}

// Display available rooms
function displayAvailableRooms(rooms) {
    const tableBody = document.getElementById('roomTableBody');
    tableBody.innerHTML = '';
    
    if (rooms.length === 0) {
        showMessage('No rooms available for the selected dates and type', 'info');
        document.getElementById('availableRoomsSection').style.display = 'none';
        return;
    }
    
    rooms.forEach(room => {
        const row = `
            <tr>
                <td>${room.room_no}</td>
                <td>${room.floor_no}</td>
                <td>${room.view}</td>
                <td>Rs. ${room.rate_per_night}</td>
                <td>${room.max_capacity}</td>
                <td>
                    <button onclick='selectRoom(${JSON.stringify(room)})' class="btn btn-success btn-sm">
                        <i class="fas fa-check"></i> Select
                    </button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
    
    document.getElementById('availableRoomsSection').style.display = 'block';
    showMessage(`${rooms.length} room(s) available`, 'success');
    
    // Scroll to available rooms
    document.getElementById('availableRoomsSection').scrollIntoView({ behavior: 'smooth', block: 'start' });
}

// Select a room
function selectRoom(room) {
    document.getElementById('selectedRoomId').value = room.room_id;
    document.getElementById('selectedRoomNo').value = room.room_no;
    document.getElementById('selectedRoomType').value = room.room_type;
    document.getElementById('selectedRoomRate').value = 'Rs. ' + room.rate_per_night;
    document.getElementById('selectedRoomFloor').value = room.floor_no;
    document.getElementById('selectedRoomView').value = room.view;
    
    // Show selected room section and action buttons
    document.getElementById('selectedRoomSection').style.display = 'block';
    document.getElementById('actionButtons').style.display = 'flex';
    
    // Hide available rooms
    document.getElementById('availableRoomsSection').style.display = 'none';
    
    showMessage('Room selected successfully. Review details and confirm booking.', 'success');
    
    // Scroll to selected room
    document.getElementById('selectedRoomSection').scrollIntoView({ behavior: 'smooth', block: 'start' });
}

// Confirm booking
function confirmBooking() {
    // Validate all required fields
    const guestId = document.getElementById('selectedGuestId').value;
    const roomId = document.getElementById('selectedRoomId').value;
    
    if (!guestId) {
        showMessage('Please select a guest', 'warning');
        document.getElementById('guestSearch').focus();
        return;
    }
    
    if (!roomId) {
        showMessage('Please select a room', 'warning');
        return;
    }
    
    // Confirm with user
    if (!confirm('Are you sure you want to confirm this booking?')) {
        return;
    }
    
    // Prepare booking data
    const bookingData = {
        reservationNo: document.getElementById('reservationNo').value,
        guestId: guestId,
        roomId: roomId,
        checkInDate: document.getElementById('checkInDate').value,
        checkOutDate: document.getElementById('checkOutDate').value,
        reservationDate: document.getElementById('reservationDate').value,
        reservationStatus: 'Confirmed'
    };
    
    // Show loading
    const confirmBtn = event.target;
    const originalText = confirmBtn.innerHTML;
    confirmBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
    confirmBtn.disabled = true;
    
    // AJAX request to save reservation
    fetch('ConfirmBooking', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(bookingData)
    })
    .then(response => response.json())
    .then(data => {
        confirmBtn.innerHTML = originalText;
        confirmBtn.disabled = false;
        
        if (data.success) {
            showMessage('Reservation created successfully! Reservation No: ' + bookingData.reservationNo, 'success');
            
            // Show success and option to print or create new
            setTimeout(() => {
                if (confirm('Reservation confirmed! Would you like to create another reservation?')) {
                    resetForm();
                } else {
                    window.location.href = 'home.jsp';
                }
            }, 1500);
        } else {
            showMessage(data.message || 'Error creating reservation. Please try again.', 'error');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        confirmBtn.innerHTML = originalText;
        confirmBtn.disabled = false;
        showMessage('Error creating reservation. Please try again.', 'error');
    });
}

// Reset form
function resetForm() {
    // Clear all input fields
    document.getElementById('guestSearch').value = '';
    document.getElementById('selectedGuestId').value = '';
    document.getElementById('selectedGuestName').value = '';
    document.getElementById('selectedGuestContact').value = '';
    document.getElementById('selectedGuestEmail').value = '';
    document.getElementById('checkInDate').value = '';
    document.getElementById('checkOutDate').value = '';
    document.getElementById('roomType').value = '';
    document.getElementById('selectedRoomId').value = '';
    document.getElementById('selectedRoomNo').value = '';
    document.getElementById('selectedRoomType').value = '';
    document.getElementById('selectedRoomRate').value = '';
    document.getElementById('selectedRoomFloor').value = '';
    document.getElementById('selectedRoomView').value = '';
    
    // Hide sections
    document.getElementById('guestResults').style.display = 'none';
    document.getElementById('selectedGuestSection').style.display = 'none';
    document.getElementById('reservationDetailsSection').style.display = 'none';
    document.getElementById('availableRoomsSection').style.display = 'none';
    document.getElementById('selectedRoomSection').style.display = 'none';
    document.getElementById('actionButtons').style.display = 'none';
    
    // Reinitialize
    initializePage();
    
    // Clear messages
    hideMessage();
    
    // Scroll to top
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// Cancel booking
function cancelBooking() {
    if (confirm('Are you sure you want to cancel? All entered data will be lost.')) {
        window.location.href = 'home.jsp';
    }
}

// Show message (FIXED - No double icon, auto-hide)
function showMessage(message, type) {
    const messageArea = document.getElementById('messageArea');
    messageArea.className = 'message-area ' + type;
    messageArea.textContent = message; // Changed from innerHTML to textContent - NO ICON
    messageArea.style.display = 'block';
    
    // Auto-hide success, info, and warning messages after 5 seconds
    if (type === 'success' || type === 'info' || type === 'warning') {
        setTimeout(() => {
            hideMessage();
        }, 5000);
    }
    
    // Scroll to message
    messageArea.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

// Hide message
function hideMessage() {
    const messageArea = document.getElementById('messageArea');
    messageArea.style.display = 'none';
}