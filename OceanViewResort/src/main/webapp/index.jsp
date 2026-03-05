<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ocean View Resort</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>
    <!-- Navigation Bar -->
    <nav>
        <div class="nav-container">
            <a href="index.jsp" class="logo">
                <div class="logo-icon">🏨</div>
                <div class="logo-text">
                    <span class="logo-main">Ocean View Resort</span>
                    <span class="logo-sub">GALLE • SRI LANKA</span>
                </div>
            </a>
            <ul class="nav-menu">
                <li><a href="index.jsp" class="active" id="home-link"><i class="fas fa-home"></i> Home</a></li>
                <li><a href="login.jsp" id="login-link"><i class="fas fa-sign-in-alt"></i> Login</a></li>
            </ul>
        </div>
    </nav>

    <!-- Hero Section with Slideshow Background -->
    <section class="hero">
        <!-- Slideshow Container -->
        <div class="hero-slideshow">
            <div class="hero-slide active" style="background-image: url('https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=2070&q=80');"></div>
            <div class="hero-slide" style="background-image: url('https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=2070&q=80');"></div>
            <div class="hero-slide" style="background-image: url('https://images.unsplash.com/photo-1571003123894-1f0594d2b5d9?ixlib=rb-4.0.3&auto=format&fit=crop&w=2049&q=80');"></div>
            <div class="hero-slide" style="background-image: url('https://images.unsplash.com/photo-1445019980597-93fa8acb246c?ixlib=rb-4.0.3&auto=format&fit=crop&w=2074&q=80');"></div>
            <div class="hero-slide" style="background-image: url('https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&auto=format&fit=crop&w=2070&q=80');"></div>
        </div>

        <!-- Hero Content -->
        <div class="hero-content">
            <h1>Ocean View Resort Reservation System</h1>
            <p>Experience luxury beachside management with our state-of-the-art reservation platform. Streamline operations, enhance guest experiences, and optimize hotel management.</p>
            <div class="hero-buttons">
                <button class="btn btn-primary" id="get-started-btn"><i class="fas fa-play-circle"></i> Get Started</button>
                <button class="btn btn-secondary" id="learn-more-btn"><i class="fas fa-info-circle"></i> Learn More</button>
            </div>
        </div>

        <!-- Slideshow Navigation Dots -->
        <div class="slideshow-dots">
            <span class="dot active" data-slide="0"></span>
            <span class="dot" data-slide="1"></span>
            <span class="dot" data-slide="2"></span>
            <span class="dot" data-slide="3"></span>
            <span class="dot" data-slide="4"></span>
        </div>
    </section>

    <!-- Main Content -->
    <div class="container">
        <!-- Features Section -->
        <h2 class="section-title">System Features</h2>
        <p class="section-subtitle">Our comprehensive reservation system provides everything you need to manage Ocean View Resort efficiently</p>
        
        <div class="features">
            <div class="feature-card floating">
                <div class="feature-icon"><i class="fas fa-calendar-plus"></i></div>
                <h3>Add New Reservations</h3>
                <p>Create bookings with unique reservation IDs, guest details, room preferences, and special requests. Automated conflict detection ensures no double bookings.</p>
            </div>
            <div class="feature-card floating" style="animation-delay: 0.5s;">
                <div class="feature-icon"><i class="fas fa-search"></i></div>
                <h3>View & Manage Bookings</h3>
                <p>Access complete guest information, booking history, and room status. Filter by date, guest name, or reservation number for quick access.</p>
            </div>
            <div class="feature-card floating" style="animation-delay: 1s;">
                <div class="feature-icon"><i class="fas fa-calculator"></i></div>
                <h3>Automated Billing</h3>
                <p>Generate accurate bills based on room rates, duration, and additional services. Support for multiple payment methods and invoice printing.</p>
            </div>
        </div>

        <!-- Instructions Section -->
        <div class="instructions">
            <h2 class="section-title" style="text-align: left; border-bottom: none;">Getting Started Guide</h2>
            <p style="margin-bottom: 3rem; color: #6c757d;">Follow these simple steps to efficiently use the reservation system</p>
            
            <div class="steps">
                <div class="step">
                    <div class="step-number">1</div>
                    <div class="step-content">
                        <h3>Secure Login</h3>
                        <p>Access the system with your unique staff credentials. All actions are logged for security and accountability.</p>
                    </div>
                </div>
                <div class="step">
                    <div class="step-number">2</div>
                    <div class="step-content">
                        <h3>Create Reservation</h3>
                        <p>Enter guest details, select room type (Standard, Deluxe, Suite), and specify check-in/check-out dates.</p>
                    </div>
                </div>
                <div class="step">
                    <div class="step-number">3</div>
                    <div class="step-content">
                        <h3>Manage & Finalize</h3>
                        <p>View, modify, or cancel reservations as needed. Generate invoices and process check-outs with automated billing.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer>
        <div class="footer-content">
            <div class="footer-section">
                <h3>Ocean View Resort</h3>
                <p>Luxury beachside accommodation in Galle, Sri Lanka. Offering premium hospitality experiences with state-of-the-art management systems.</p>
                <p><i class="fas fa-star"></i> 4.8/5 Guest Rating</p>
                <p><i class="fas fa-award"></i> Hospitality Excellence Award 2023</p>
            </div>
            <div class="footer-section">
                <h3>Contact Information</h3>
                <ul>
                    <li><i class="fas fa-map-marker-alt"></i> Beach Road, Galle, Sri Lanka</li>
                    <li><i class="fas fa-phone"></i> +94 91 222 3333</li>
                    <li><i class="fas fa-envelope"></i> reservations@oceanviewresort.lk</li>
                </ul>
            </div>
            <div class="footer-section">
                <h3>System Support</h3>
                <p>For technical assistance with the reservation system:</p>
                <p><i class="fas fa-headset"></i> IT Help Desk: Ext. 101</p>
                <p><i class="fas fa-envelope"></i> systemadmin@oceanviewresort.lk</p>
            </div>
        </div>
        <div class="footer-bottom">
            <p>Ocean View Resort &copy; 2023 | Reservation System v3.0 | Designed for Excellence in Hospitality Management</p>
        </div>
    </footer>

    <script>
        // Slideshow functionality
        let currentSlide = 0;
        const slides = document.querySelectorAll('.hero-slide');
        const dots = document.querySelectorAll('.dot');
        const totalSlides = slides.length;

        // Function to show specific slide
        function showSlide(index) {
            // Remove active class from all slides and dots
            slides.forEach(slide => slide.classList.remove('active'));
            dots.forEach(dot => dot.classList.remove('active'));
            
            // Add active class to current slide and dot
            slides[index].classList.add('active');
            dots[index].classList.add('active');
            
            currentSlide = index;
        }

        // Function to show next slide
        function nextSlide() {
            let next = (currentSlide + 1) % totalSlides;
            showSlide(next);
        }

        // Auto advance slides every 5 seconds
        setInterval(nextSlide, 5000);

        // Add click event to dots
        dots.forEach((dot, index) => {
            dot.addEventListener('click', () => {
                showSlide(index);
            });
        });

        // DOM Elements
        const loginLink = document.getElementById('login-link');
        const homeLink = document.getElementById('home-link');
        const getStartedBtn = document.getElementById('get-started-btn');
        const learnMoreBtn = document.getElementById('learn-more-btn');

        // Event Listeners
        loginLink.addEventListener('click', (e) => {
            e.preventDefault();
            window.location.href = 'login.jsp';
        });

        getStartedBtn.addEventListener('click', () => {
            window.location.href = 'login.jsp';
        });

        learnMoreBtn.addEventListener('click', () => {
            // Smooth scroll to features section
            document.querySelector('.features').scrollIntoView({ 
                behavior: 'smooth',
                block: 'start'
            });
        });

        // Update active navigation link
        function updateActiveNav(activeId) {
            document.querySelectorAll('.nav-menu a').forEach(link => {
                link.classList.remove('active');
            });
            document.getElementById(activeId).classList.add('active');
        }

        // Add hover effect to feature cards
        document.querySelectorAll('.feature-card').forEach(card => {
            card.addEventListener('mouseenter', function() {
                const icon = this.querySelector('.feature-icon i');
                icon.style.transform = 'scale(1.2) rotate(5deg)';
                icon.style.transition = 'transform 0.3s ease';
            });
            
            card.addEventListener('mouseleave', function() {
                const icon = this.querySelector('.feature-icon i');
                icon.style.transform = 'scale(1) rotate(0deg)';
            });
        });

        // Add scroll effect to navbar
        window.addEventListener('scroll', function() {
            const nav = document.querySelector('nav');
            if (window.scrollY > 50) {
                nav.style.padding = '0.8rem 0';
                nav.style.boxShadow = '0 5px 20px rgba(0, 0, 0, 0.2)';
            } else {
                nav.style.padding = '1.2rem 0';
                nav.style.boxShadow = '0 5px 20px rgba(0, 0, 0, 0.15)';
            }
        });
    </script>
</body>
</html>
