# Conference Tool - Project Documentation

## Overview

This project is developed by a team of three third-year students from the **Applied Informatics** program at **Karel de Grote University of Applied Sciences** in Antwerp. Our focus for this project is on **Full Stack Development**. We have selected a new and exciting technology stack for the Conference Tool, a web application developed for **Axxes**, which aims to streamline the conference management process.

Having worked extensively with **Java Spring Boot** (for backend) and **React** (for frontend) in previous projects, we decided to venture into **Angular** for the frontend of this project. This allowed us to expand our skill set and deepen our understanding of a different technology while tackling the needs of the Conference Tool.

## Technology Stack

### Backend

For the backend, we chose **Java Spring Boot**, a stable and platform-independent framework. This decision was driven by the need for a robust backend environment that integrates seamlessly with the chosen frontend and database, offering scalability and flexibility.

### Frontend

The frontend is built using **Angular 17.2**, a modern framework for building dynamic web applications. While we were new to Angular, we undertook individual training through an "Angular For Beginners" course by **Angular University** to familiarize ourselves with the framework. We found that transitioning from **React** to Angular was relatively smooth due to shared concepts like components, services, and reactive programming.

Angular provides a more structured and organized development experience compared to React, which we appreciate, especially with its recent changes in version 17.2. This version introduces the **standalone component** architecture, eliminating the need for an `app.module.ts` file, streamlining the app's structure.

### Database

For data storage, we chose **MySQL**. As an open-source and well-documented relational database management system, MySQL provided a cost-effective solution with cross-platform compatibility. MySQL integrates seamlessly with Java Spring Boot and is simple to set up and configure, which was crucial for our development process, as we are primarily using **MacOS**.

### Security

Given that the Conference Tool will be used internally, securing user data and application access is of utmost importance. We implemented **Keycloak** as our Identity Provider (IDP). Keycloak offers robust features for:

- **Authentication and Authorization**  
- **Single Sign-On (SSO)**
- **Federated Identity**

By using Keycloak, we are able to manage user identities securely, enforce access controls, and support external identity providers such as Google, Facebook, or LDAP, if necessary. This provides a secure and seamless authentication experience for users while ensuring compliance with security standards.

## Key Features

- **Secure User Authentication**: With Keycloak as the IDP, we ensure that only authorized users can access the application, and sensitive data is protected.
- **Database Management**: All necessary data is stored in a **MySQL** database, which is reliable, cost-effective, and easy to manage.
- **Frontend with Angular**: The user interface is built using **Angular 17.2**, offering a modern, organized, and scalable front-end structure.
- **Backend with Java Spring Boot**: The backend is powered by **Java Spring Boot**, ensuring a strong, stable environment for the server-side logic.

## Development Process

1. **Research and Technology Selection**  
   After careful consideration of various options, we chose the technologies that best suited the project’s needs: **Angular** for the frontend, **Java Spring Boot** for the backend, and **MySQL** for data storage.

2. **Learning Angular**  
   Since none of us had experience with Angular, we followed an in-depth course on Angular fundamentals. We found that learning Angular was easier with our existing knowledge of React, as many concepts overlap between the two frameworks.

3. **Database Setup**  
   We set up **MySQL** to store all necessary data for the Conference Tool. The database was easily integrated with **Spring Boot** and worked well within our cross-platform environment.

4. **Security Implementation**  
   Implementing **Keycloak** for authentication and authorization was a priority. We configured it to manage user sessions, access rights, and integrated it for a seamless login experience.

## Conclusion

Through careful selection of technologies and thoughtful implementation, we have successfully developed a **Conference Tool** for **Axxes** that is secure, efficient, and easy to use. By leveraging **Java Spring Boot** for the backend, **Angular** for the frontend, **MySQL** for data storage, and **Keycloak** for authentication, we have created a robust and scalable solution that aligns with the client’s needs.

We believe this project not only highlights our ability to adapt to new technologies but also delivers a high-quality tool that enhances knowledge sharing and innovation within the IT sector.
