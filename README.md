# KiebotBook Library Management System

A Spring Boot-based library management system that provides APIs for searching, managing, and tracking books, loans, and reservations.

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.8.x or higher
- PostgreSQL 12 or higher

### Building the Project
```bash
# Clone the repository
git clone <repository-url>
cd kiebotBook

# Build the project
mvn clean install
```

### Running the Application
```bash
# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### Search Books
Search for books with various filtering criteria.

1. **Basic Search by Title**:
```bash
curl -X GET 'http://localhost:8080/api/v1/books/search?title=Cosmos&page=0&size=0'
```
**Advanced Search with Multiple Criteria:**:
```bash
curl -X GET 'http://localhost:8080/api/v1/books/search?authorName=Sagan&genre=SCIENCE&publicationYearFrom=1970&publicationYearTo=1990&sort=publicationYear,asc'
```
**Search for Books Currently Loaned by a Specific Member**:
```bash
curl -X GET 'http://localhost:8080/api/v1/books/search?loanedByMemberId=MEM-AB1234&page=0&size=5'
```
**Search for Available Books**:
```bash
curl -X GET 'http://localhost:8080/api/v1/books/search?isAvailable=true&genre=TECHNOLOGY&sort=title,asc'
```

## Assumptions and Design Decisions

### Design Decisions

#### DTO Structure
1. **BookDTO**: 
   - Separates internal entity structure from API response
   - Includes nested DTOs for author and summaries
   - Calculates `availableCopies` dynamically based on active loans

2. **Summary DTOs**:
   - `LoanSummary` and `ReservationSummary` provide essential information without exposing full entity details
   - Helps reduce response payload size

#### Search Implementation
1. **JPQL Query Design**:
   - Uses dynamic query building with optional parameters
   - Implements case-insensitive search using ILIKE
   - Handles null/empty parameters with default values
   - Uses LEFT JOIN FETCH for efficient loading of related entities

2. **Pagination and Sorting**:
   - Implements Spring Data's Pageable interface
   - Supports dynamic sorting with field and direction
   - Default page size of 20 to balance performance and usability



