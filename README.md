# Space Weather Application 🌞🌍

## Overview
The Space Weather Application is a modern web application designed to track and analyze real-time space weather data. Built on the Spring Boot framework, this application offers a comprehensive suite of features for monitoring and analyzing space weather phenomena.

## Features 🚀
- Real-time space weather monitoring
- User authentication and authorization
- WebSocket support for live updates
- RESTful API endpoints
- Interactive web interface using Thymeleaf
- Secure data storage with H2 database
- Advanced data visualization
- Customizable alert system
- Historical data analysis
- User preference management

## Technical Stack 💻
- **Java Version:** 21
- **Framework:** Spring Boot 3.2.2
- **Database:** H2 Database
- **Frontend:** Thymeleaf
- **Real-time Updates:** WebSocket
- **API Client:** WebFlux WebClient
- **Build Tool:** Maven

## Prerequisites 📋
- Java Development Kit (JDK) 21
- Maven 3.x
- Your favorite IDE (IntelliJ IDEA recommended)
- Git for version control

## Installation and Setup 🛠️

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Kanhaiya1610/Space-Weather-WebApp.git
   cd Space-Weather-WebApp
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

The application will start running on `http://localhost:8080`

## Project Structure 📁
```
src/
├── main/
|   ├── java/
|   │   └── com/spaceweather/
|   │       ├── config/        # Configuration classes
|   │       ├── controller/    # REST controllers
|   │       ├── model/        # Entity classes
|   │       ├── repository/   # Data repositories
|   │       ├── service/      # Business logic
|   |       └── SpaceWeatherApplication.java
|   │       
|   └── resources/
|       ├── static/          # Static resources
|       ├── templates/       # Thymeleaf templates
|       └── application.properties
|
├── README.md
└── pom.xml
```

## API Documentation 📚
Base URL: `http://localhost:8080/api`

### Main Endpoints:
- `/api/auth/*` - Authentication endpoints
  - POST /login - User login
  - POST /register - New user registration
  - POST /refresh - Refresh token
- `/api/weather/*` - Space weather data endpoints
  - GET /current - Current weather conditions
  - GET /forecast - Weather forecasts
  - GET /historical - Historical data
- `/api/alerts/*` - Space weather alerts
  - GET /active - Active alerts
  - POST /subscribe - Subscribe to alerts
  - GET /history - Alert history

## Contributing 🤝
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Development Guidelines
- Follow Java code conventions
- Write unit tests for new features
- Update documentation for significant changes
- Follow Git commit message conventions
- Review code before submitting PRs

## Contact 📧
Project Maintainer - Kanhaiya Lal
Project Link: https://github.com/Kanhaiya1610/Space-Weather-WebApp.git

## Acknowledgments
- Spring Boot Team
- Space Weather Data Providers
- Open Source Community 
