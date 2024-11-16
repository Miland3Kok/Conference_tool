# Conference Tool

## Overview

Welcome to the **Conference Tool** project! This tool has been developed to streamline conference management for **Axxes**, providing a seamless, user-friendly platform for internal use. The tool integrates modern technologies to ensure a robust, secure, and efficient experience for users.

**Axxes** specializes in providing cutting-edge IT solutions, and this tool aims to enhance their internal processes for managing conferences, from registration to data handling and security. With this tool, Axxes can easily manage users, track conference details, and ensure secure access to all participants.

## Technologies

- **Spring Boot** for backend development
- **Angular 17.2** for frontend development
- **MySQL** for database storage
- **Keycloak** for identity and access management

## User Profiles

1. **Admin (Conference Organizer)**
   The Admin is responsible for managing the conference tool. They handle user access, manage conference details, and ensure that all participants have the necessary permissions. They use Keycloak for managing authentication and authorization.

2. **Speaker (Presenter)**
   Speakers are registered users who give presentations at the conference. They are able to access session schedules, upload presentation materials, and interact with attendees through the platform.

3. **Attendee**
   Attendees are conference participants who have access to session schedules, can register for events, and interact with other participants. They also have access to past session materials and conference-related content.

4. **External Integrator (API User)**
   External partners or systems can interact with the Conference Tool via the exposed APIs, allowing them to integrate with third-party systems for automated data exchange or analysis.

## Functionality

The **Conference Tool** consists of three main components, each with a unique set of responsibilities:

### 1. Backend (API Endpoints)
The backend, built with **Java Spring Boot**, provides the necessary API endpoints for interaction:

- User registration and authentication through **Keycloak**
- Conference schedule management
- User role management (Admin, Speaker, Attendee)
- Session tracking, including attendance and materials
- Integration with external services for data sharing
- Real-time notifications and updates to attendees and speakers

### 2. Frontend (Web Application)
The frontend, developed with **Angular 17.2**, offers a modern, user-friendly interface with the following features:

- Responsive design for desktop and mobile devices
- Conference schedule display with session details
- User login and role-based access
- Event registration and material download options
- Speaker and attendee profiles with interaction capabilities

### 3. Database (MySQL)
The backend is supported by a **MySQL** database that stores:

- User data and authentication information
- Conference schedules and session details
- Event registration and attendance information
- User roles and permissions

### 4. Identity and Access Management (Keycloak)
**Keycloak** is used to manage user identities and access control:

- Single Sign-On (SSO) functionality for users
- Role-based access control (RBAC) for different user types
- Integration with external identity providers (optional)
- Secure token-based authentication

## Testing

Testing is a key focus of the Conference Tool project. We ensure that the application is thoroughly tested through:

- **Unit Tests**: For backend logic like user authentication, data processing, and session management.
- **Integration Tests**: For API endpoints, database interactions, and end-to-end user flows.
- **UI Tests**: For verifying frontend interactions and ensuring responsiveness across devices.

## Configuration and Logging

### Configuration
The Conference Tool is highly configurable, allowing for customization of various aspects of the application, including:

- Database connections
- **Keycloak** configuration for user authentication
- User roles and permissions
- Conference settings such as session timeouts and registration cut-offs

### Logging
Detailed logging is implemented to track errors and debug the application. Key logging features include:

- API request and response logging for error tracking
- Database interaction logs for troubleshooting
- Real-time event logging for session updates and notifications

## Infrastructure

The application can be run locally or deployed in a production environment. To make the development process easier, we have created a **Docker-Compose** setup to run the entire application locally. This setup includes:

- **MySQL** database
- **Keycloak** for identity management
- **Angular** frontend
- **Spring Boot** backend

You can find the `docker-compose.yml` file here: `docker-compose.yml`

## Conclusion

By leveraging the latest technologies such as **Spring Boot**, **Angular**, **MySQL**, and **Keycloak**, we have created a powerful **Conference Tool** that meets the needs of **Axxes**. This project not only strengthens our understanding of modern full-stack development but also ensures a secure and scalable solution for conference management. We hope this tool will contribute significantly to Axxes' internal operations and improve the conference experience for all users.

