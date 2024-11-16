# Stage 1: Build the frontend
FROM node:latest AS build

WORKDIR /app

# Copy package.json and package-lock.json separately
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the entire project and build the frontend
COPY . .
RUN npm run build

# Stage 2: Serve the frontend using Nginx
FROM nginx:stable-alpine

# Copy the Nginx configuration file
COPY nginx.conf /etc/nginx/nginx.conf

# Copy the built frontend files into the Nginx HTML directory
COPY --from=build /app/dist/conference-tool/browser /usr/share/nginx/html

# Set the entrypoint for the container
ENTRYPOINT ["nginx", "-g", "daemon off;"]
