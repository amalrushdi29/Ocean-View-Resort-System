// guests.js - Guest Management System

let allGuests = []; // Store all guests for client-side pagination
let currentPage = 1;
const rowsPerPage = 10;

// ============================================
// INITIALIZE ON PAGE LOAD
// ============================================
document.addEventListener('DOMContentLoaded', function() {
    // Store all table rows
    const tableBody = document.getElementById('guestsTableBody');
    const rows = Array.from(tableBody.querySelectorAll('tr'));
    allGuests = rows;
    
    initializeSearch();
    initializeFilters();
    initializeModals();
    initializeFormValidation();
    displayPage(1);
});

// ============================================
// FORM VALIDATION
// ============================================
function initializeFormValidation() {
    const contactInput = document.getElementById('editContact');
    
    if (contactInput) {
        // Remove HTML5 validation pattern
        contactInput.removeAttribute('pattern');
        
        // Format phone number on input (add leading 0 for display)
        contactInput.addEventListener('input', function(e) {
            let value = e.target.value.replace(/\D/g, ''); // Remove non-digits
            
            // Limit to 10 digits
            if (value.length > 10) {
                value = value.substring(0, 10);
            }
            
            e.target.value = value;
        });
        
        // Add leading 0 if needed when displaying
        contactInput.addEventListener('blur', function(e) {
            let value = e.target.value.trim();
            
            // If 9 digits and doesn't start with 0, add 0
            if (value.length === 9 && !value.startsWith('0')) {
                e.target.value = '0' + value;
            }
        });
    }
    
    // Form submit validation
    const editForm = document.getElementById('editForm');
    if (editForm) {
        editForm.addEventListener('submit', function(e) {
            const contact = document.getElementById('editContact').value.trim();
            const email = document.getElementById('editEmail').value.trim();
            const address = document.getElementById('editAddress').value.trim();
            
            // Validate contact (must be 9 or 10 digits)
            if (contact.length !== 9 && contact.length !== 10) {
                e.preventDefault();
                showNotification('Contact number must be 9 or 10 digits', 'error');
                return false;
            }
            
            // Validate email
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                e.preventDefault();
                showNotification('Please enter a valid email address', 'error');
                return false;
            }
            
            // Validate address
            if (address.length < 5) {
                e.preventDefault();
                showNotification('Address must be at least 5 characters', 'error');
                return false;
            }
            
            return true;
        });
    }
}

// ============================================
// CLIENT-SIDE PAGINATION
// ============================================
function displayPage(page) {
    currentPage = page;
    
    // Get visible guests (after filter/search)
    const visibleGuests = allGuests.filter(row => row.style.display !== 'none');
    
    const startIndex = (page - 1) * rowsPerPage;
    const endIndex = startIndex + rowsPerPage;
    
    // Hide all guests first
    allGuests.forEach(row => row.classList.add('pagination-hidden'));
    
    // Show only current page guests
    for (let i = 0; i < visibleGuests.length; i++) {
        if (i >= startIndex && i < endIndex) {
            visibleGuests[i].classList.remove('pagination-hidden');
        }
    }
    
    renderPagination(visibleGuests.length);
}

function renderPagination(totalGuests) {
    const totalPages = Math.ceil(totalGuests / rowsPerPage);
    const paginationDiv = document.querySelector('.pagination');
    
    if (!paginationDiv || totalPages <= 1) {
        if (paginationDiv) paginationDiv.style.display = 'none';
        return;
    }
    
    paginationDiv.style.display = 'flex';
    
    // Update info
    const startNum = totalGuests === 0 ? 0 : ((currentPage - 1) * rowsPerPage) + 1;
    const endNum = Math.min(currentPage * rowsPerPage, totalGuests);
    
    paginationDiv.innerHTML = `
        <div class="pagination-info">
            Showing ${startNum} to ${endNum} of ${totalGuests} guests
        </div>
        <div class="page-numbers">
            <button class="page-btn" ${currentPage === 1 ? 'disabled' : ''} onclick="changePage(${currentPage - 1})">
                <i class="fas fa-chevron-left"></i> Previous
            </button>
            ${generatePageNumbers(totalPages)}
            <button class="page-btn" ${currentPage === totalPages ? 'disabled' : ''} onclick="changePage(${currentPage + 1})">
                Next <i class="fas fa-chevron-right"></i>
            </button>
        </div>
    `;
}

function generatePageNumbers(totalPages) {
    let html = '';
    const startPage = Math.max(1, currentPage - 2);
    const endPage = Math.min(totalPages, currentPage + 2);
    
    if (startPage > 1) {
        html += `<button class="page-num" onclick="changePage(1)">1</button>`;
        if (startPage > 2) html += `<span style="padding: 0 0.5rem;">...</span>`;
    }
    
    for (let i = startPage; i <= endPage; i++) {
        html += `<button class="page-num ${i === currentPage ? 'active' : ''}" onclick="changePage(${i})">${i}</button>`;
    }
    
    if (endPage < totalPages) {
        if (endPage < totalPages - 1) html += `<span style="padding: 0 0.5rem;">...</span>`;
        html += `<button class="page-num" onclick="changePage(${totalPages})">${totalPages}</button>`;
    }
    
    return html;
}

function changePage(page) {
    const visibleGuests = allGuests.filter(row => row.style.display !== 'none');
    const totalPages = Math.ceil(visibleGuests.length / rowsPerPage);
    
    if (page < 1 || page > totalPages) return;
    
    displayPage(page);
    
    // Scroll to table
    document.querySelector('.all-guests-section').scrollIntoView({ behavior: 'smooth', block: 'start' });
}

// ============================================
// EDIT GUEST FUNCTIONALITY
// ============================================
function openEditModal(guestId, name, nicPpt, contactNo, email, address) {
    document.getElementById('editGuestId').value = guestId;
    document.getElementById('editName').value = name;
    document.getElementById('editNicPpt').value = nicPpt;
    
    // Format contact number for display (add 0 if 9 digits)
    let formattedContact = contactNo;
    if (contactNo && contactNo.length === 9 && !contactNo.startsWith('0')) {
        formattedContact = '0' + contactNo;
    }
    document.getElementById('editContact').value = formattedContact;
    
    document.getElementById('editEmail').value = email;
    document.getElementById('editAddress').value = address;
    
    document.getElementById('editModal').classList.add('active');
}

function closeEditModal() {
    document.getElementById('editModal').classList.remove('active');
}

// ============================================
// MODAL FUNCTIONALITY
// ============================================
function initializeModals() {
    const modals = document.querySelectorAll('.modal');
    modals.forEach(function(modal) {
        modal.addEventListener('click', function(e) {
            if (e.target === modal) {
                modal.classList.remove('active');
            }
        });
    });
    
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            const activeModal = document.querySelector('.modal.active');
            if (activeModal) {
                activeModal.classList.remove('active');
            }
        }
    });
}

// ============================================
// SEARCH FUNCTIONALITY (Search ALL guests)
// ============================================
function initializeSearch() {
    const searchInput = document.getElementById('searchGuests');
    if (searchInput) {
        searchInput.addEventListener('input', function(e) {
            const searchTerm = e.target.value.toLowerCase();
            
            allGuests.forEach(function(row) {
                const rowText = row.textContent.toLowerCase();
                if (rowText.includes(searchTerm)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
            
            // Reset to page 1 and update pagination
            displayPage(1);
        });
    }
}

// ============================================
// FILTER FUNCTIONALITY
// ============================================
function initializeFilters() {
    const sortBy = document.getElementById('sortBy');
    
    if (sortBy) {
        sortBy.addEventListener('change', handleSort);
    }
}

// ============================================
// SORT FUNCTIONALITY
// ============================================
function handleSort(e) {
    const sortValue = e.target.value;
    const tableBody = document.querySelector('#guestsTable tbody');
    
    allGuests.sort(function(a, b) {
        let aValue, bValue;
        
        switch(sortValue) {
            case 'name':
                aValue = a.querySelector('td:nth-child(2)').textContent.trim();
                bValue = b.querySelector('td:nth-child(2)').textContent.trim();
                return aValue.localeCompare(bValue);
                
            case 'email':
                aValue = a.querySelector('td:nth-child(5)').textContent.trim();
                bValue = b.querySelector('td:nth-child(5)').textContent.trim();
                return aValue.localeCompare(bValue);
                
            case 'contact':
                aValue = a.querySelector('td:nth-child(4)').textContent.trim();
                bValue = b.querySelector('td:nth-child(4)').textContent.trim();
                return aValue.localeCompare(bValue);
                
            default:
                return 0;
        }
    });
    
    // Re-append sorted rows
    allGuests.forEach(function(row) {
        tableBody.appendChild(row);
    });
    
    // Update display
    displayPage(currentPage);
}

// ============================================
// NOTIFICATION SYSTEM
// ============================================
function showNotification(message, type) {
    if (!type) type = 'info';
    
    const notification = document.createElement('div');
    notification.className = 'notification notification-' + type;
    
    var bgColor = '#3b82f6';
    if (type === 'success') bgColor = '#10b981';
    if (type === 'error') bgColor = '#ef4444';
    
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
        'gap: 0.8rem;';
    
    var icon = 'fa-info-circle';
    if (type === 'success') icon = 'fa-check-circle';
    if (type === 'error') icon = 'fa-exclamation-circle';
    
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

// ============================================
// ANIMATIONS
// ============================================
const style = document.createElement('style');
style.textContent = 
    '@keyframes slideInRight {' +
    '    from { transform: translateX(400px); opacity: 0; }' +
    '    to { transform: translateX(0); opacity: 1; }' +
    '}' +
    '@keyframes slideOutRight {' +
    '    from { transform: translateX(0); opacity: 1; }' +
    '    to { transform: translateX(400px); opacity: 0; }' +
    '}' +
    '.pagination-hidden { display: none !important; }';
document.head.appendChild(style);