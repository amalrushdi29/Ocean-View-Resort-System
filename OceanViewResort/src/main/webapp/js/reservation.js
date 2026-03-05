// Guest Registration Form Validation and Functionality

function validateForm() {
    let isValid = true;
    clearErrors();

    // Validate Guest Name
    const guestName = document.getElementById('guestName');
    if (guestName.value.trim() === '') {
        showError('nameError', 'Guest name is required');
        guestName.classList.add('error');
        isValid = false;
    } else if (guestName.value.trim().length < 3) {
        showError('nameError', 'Name must be at least 3 characters long');
        guestName.classList.add('error');
        isValid = false;
    } else if (!/^[a-zA-Z\s.]+$/.test(guestName.value)) {
        showError('nameError', 'Name should contain only letters');
        guestName.classList.add('error');
        isValid = false;
    }

    // Validate Guest Address
    const guestAddress = document.getElementById('guestAddress');
    if (guestAddress.value.trim() === '') {
        showError('addressError', 'Address is required');
        guestAddress.classList.add('error');
        isValid = false;
    } else if (guestAddress.value.trim().length < 10) {
        showError('addressError', 'Please enter a complete address');
        guestAddress.classList.add('error');
        isValid = false;
    }

    // Validate Contact Number
    const contactNumber = document.getElementById('contactNumber');
    if (contactNumber.value.trim() === '') {
        showError('contactError', 'Contact number is required');
        contactNumber.classList.add('error');
        isValid = false;
    } else if (!/^[0-9]{10}$/.test(contactNumber.value)) {
        showError('contactError', 'Contact number must be exactly 10 digits');
        contactNumber.classList.add('error');
        isValid = false;
    } else if (!contactNumber.value.startsWith('0')) {
        showError('contactError', 'Contact number should start with 0');
        contactNumber.classList.add('error');
        isValid = false;
    }

    // Validate Email
    const email = document.getElementById('email');
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (email.value.trim() === '') {
        showError('emailError', 'Email is required');
        email.classList.add('error');
        isValid = false;
    } else if (!emailPattern.test(email.value)) {
        showError('emailError', 'Please enter a valid email address');
        email.classList.add('error');
        isValid = false;
    }

    // Validate NIC/Passport
    const nicPassport = document.getElementById('nicPassport');
    if (nicPassport.value.trim() === '') {
        showError('nicError', 'NIC/Passport number is required');
        nicPassport.classList.add('error');
        isValid = false;
    } else if (!validateNICPassport(nicPassport.value)) {
        showError('nicError', 'Please enter a valid NIC or Passport number');
        nicPassport.classList.add('error');
        isValid = false;
    }

    if (!isValid) {
        showMessage('Please correct the errors before submitting', 'error');
    }

    return isValid;
}

function validateNICPassport(value) {
    // Sri Lankan NIC format: Old (9 digits + V/X) or New (12 digits)
    const oldNICPattern = /^[0-9]{9}[VvXx]$/;
    const newNICPattern = /^[0-9]{12}$/;
    // Passport format: 1-2 letters followed by 6-9 digits
    const passportPattern = /^[A-Z]{1,2}[0-9]{6,9}$/i;

    return oldNICPattern.test(value) || newNICPattern.test(value) || passportPattern.test(value);
}

function showError(elementId, message) {
    const errorElement = document.getElementById(elementId);
    if (errorElement) {
        errorElement.textContent = message;
    }
}

function clearErrors() {
    const errorMessages = document.querySelectorAll('.error-message');
    errorMessages.forEach(error => error.textContent = '');

    const errorInputs = document.querySelectorAll('.error');
    errorInputs.forEach(input => input.classList.remove('error'));

    hideMessage();
}

function showMessage(message, type) {
    const messageContainer = document.getElementById('messageContainer');
    const messageText = document.getElementById('messageText');
    
    if (messageContainer && messageText) {
        messageText.textContent = message;
        messageContainer.className = 'message-container ' + type;
        messageContainer.style.display = 'block';

        // Auto-hide after 5 seconds
        setTimeout(() => {
            hideMessage();
        }, 5000);
    }
}

function hideMessage() {
    const messageContainer = document.getElementById('messageContainer');
    if (messageContainer) {
        messageContainer.style.display = 'none';
    }
}

// Real-time validation for contact number (numbers only)
const contactInput = document.getElementById('contactNumber');
if (contactInput) {
    contactInput.addEventListener('input', function(e) {
        this.value = this.value.replace(/[^0-9]/g, '');
    });
}

// Real-time validation for NIC/Passport (alphanumeric only)
const nicInput = document.getElementById('nicPassport');
if (nicInput) {
    nicInput.addEventListener('input', function(e) {
        this.value = this.value.replace(/[^a-zA-Z0-9]/g, '');
    });
}

// Clear error on input focus
document.querySelectorAll('input, textarea').forEach(element => {
    element.addEventListener('focus', function() {
        this.classList.remove('error');
        const errorId = this.id + 'Error';
        const errorElement = document.getElementById(errorId);
        if (errorElement) {
            errorElement.textContent = '';
        }
    });
});

// Clear form function for reset button
function clearForm() {
    clearErrors();
    const form = document.getElementById('guestRegistrationForm');
    if (form) {
        form.reset();
    }
}
