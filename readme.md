# ShortLinkService

ShortLinkService is a URL shortening service implemented using Java and Spring Boot. It allows you to encode long URLs into shorter ones and decode shortened URLs back to their original form.

## Features

- Encode a long URL to a shortened URL.
- Decode a shortened URL back to its original URL.
- Simple in-memory storage for URL mappings.
- Exception handling for invalid shortened URLs.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/Krieit/shortlink.git
    cd shortlink
    ```

2. Build the project using Maven:
    ```bash
    mvn clean install
    ```
3. Run the application using Maven:
    The service will be available at http://localhost:8080
    ```bash
    mvn spring-boot:run
    ```
4. Run the tests using Maven:
    ```bash
    mvn test
    ```

### Configuration

External configurations such as `BASE_URL` and `SHORT_URL_LENGTH` are specified in the `application.properties` file.

```properties
shortlink.base-url=http://short.est/
shortlink.short-url-length=6
```




## API Documentation

### Endpoints

#### Encode URL

- **Endpoint**: `/api/encode`
- **Method**: `POST`
- **Headers**:
   `Content-Type: application/json`
  
- **Request Body**:
    ```json
    {
      "url": "https://example.com/library/react"
    }
    ```
- **Response**:
    ```json
    {
      "url": "http://short.est/GeAi9K"
    }
    ```

#### Decode URL

- **Endpoint**: `/api/decode`
- **Method**: `POST`
- **Headers**:
   `Content-Type: application/json`
  
- **Request Body**:
    ```json
    {
      "url": "http://short.est/GeAi9K"
    }
    ```
- **Response**:
    ```json
    {
      "url": "https://example.com/library/react"
    }
    ```
