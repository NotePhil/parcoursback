# Use a secure Ubuntu base image
FROM ubuntu:20.04 AS downloader

# Set the working directory
WORKDIR /app

# Install curl and other necessary packages
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*  # Clean up to reduce image size

# Download the JAR file from GitHub Packages using the secret token
ARG PARCOURS_PACKAGES_URL
ARG PARCOURS_MICROSERVICE_VERSION
ARG MANAGE_PACKAGE_60J

RUN echo "${MANAGE_PACKAGE_60J}"

RUN curl -v -H "Authorization: Bearer ${MANAGE_PACKAGE_60J}" \
     -L -o document-service.jar \
     "${PARCOURS_PACKAGES_URL}/cmr/notep/document-service/${PARCOURS_MICROSERVICE_VERSION}/document-service-${PARCOURS_MICROSERVICE_VERSION}.jar"

# Use OpenJDK as the base image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory in the final image
WORKDIR /app

# Copy the downloaded JAR from the previous stage
COPY --from=downloader /app/document-service.jar .

# Command to run the application
ENTRYPOINT ["java", "-jar", "document-service.jar"]

# Expose the application port
EXPOSE 8685
