// help.js - Help System

// ============================================
// INITIALIZE ON PAGE LOAD
// ============================================
document.addEventListener('DOMContentLoaded', function() {
    initializeSearch();
    initializeScrollSpy();
    initializeBackToTop();
});

// ============================================
// ACCORDION TOGGLE
// ============================================
function toggleSection(sectionId) {
    const section = document.getElementById(sectionId);
    section.classList.toggle('active');
}

// ============================================
// SEARCH FUNCTIONALITY
// ============================================
function initializeSearch() {
    const searchInput = document.getElementById('helpSearch');
    
    if (searchInput) {
        searchInput.addEventListener('input', function(e) {
            const searchTerm = e.target.value.toLowerCase().trim();
            const sections = document.querySelectorAll('.help-section');
            
            if (searchTerm === '') {
                // Show all sections
                sections.forEach(section => {
                    section.classList.remove('hidden');
                });
                return;
            }
            
            // Search through sections
            sections.forEach(section => {
                const keywords = section.getAttribute('data-keywords') || '';
                const headerText = section.querySelector('h2').textContent.toLowerCase();
                const contentText = section.querySelector('.section-content').textContent.toLowerCase();
                
                const matches = 
                    keywords.includes(searchTerm) ||
                    headerText.includes(searchTerm) ||
                    contentText.includes(searchTerm);
                
                if (matches) {
                    section.classList.remove('hidden');
                    section.classList.add('active'); // Auto-expand matching sections
                } else {
                    section.classList.add('hidden');
                }
            });
        });
    }
}

// ============================================
// SCROLL SPY (Smooth scroll to sections)
// ============================================
function initializeScrollSpy() {
    const navLinks = document.querySelectorAll('.nav-btn');
    
    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const targetId = this.getAttribute('href').substring(1);
            const targetSection = document.getElementById(targetId);
            
            if (targetSection) {
                // Open section if closed
                targetSection.classList.add('active');
                
                // Scroll to section
                targetSection.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });
}

// ============================================
// BACK TO TOP BUTTON
// ============================================
function initializeBackToTop() {
    const backToTopBtn = document.getElementById('backToTop');
    
    if (backToTopBtn) {
        // Show/hide button on scroll
        window.addEventListener('scroll', function() {
            if (window.pageYOffset > 300) {
                backToTopBtn.classList.add('visible');
            } else {
                backToTopBtn.classList.remove('visible');
            }
        });
    }
}

function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}