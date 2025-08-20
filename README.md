# BETestTask

Two small Spring Boot services communicating via HTTP and running in Docker:

- **auth-api (Service A)** — user registration/login (JWT), protected `/api/process` endpoint which calls Service B and logs results into Postgres.  
- **data-api (Service B)** — `/api/transform` endpoint that validates an internal token and returns a transformed response.  
- **Postgres** — database for users and logs.

---

## Project Structure

- **/auth-api # Spring Boot project (Service A)**
- **/data-api # Spring Boot project (Service B)**
- **docker-compose.yml**
- **README.md**

---

## Environment Variables

Create a `.env` file in the project root with the following parameters:

```env
POSTGRES_USER
POSTGRES_PASSWORD
POSTGRES_DB
INTERNAL_TOKEN   # shared secret for auth-api -> data-api communication
```

## Build & Run

From the root of the project, run:

```bash
docker compose up -d
```

This will:

- Build auth-api and data-api JARs via `mvn clean package -DskipTests`
- Start all three containers (`postgres`, `auth-api`, `data-api`) running in same networks

## Testing

Once all containers are running, you can test using `curl`:

### 1. Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"a@a.com\",\"password\":\"pass\"}"
```

### 2. Login to get a JWT
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"a@a.com\",\"password\":\"pass\"}"
```
The response will be a JWT token

### 3. Call the protected endpoint on auth-api

Replace <token> with the JWT returned from login:

```bash
curl -X POST http://localhost:8081/api/process \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d "{\"text\":\"hello\"}"
```
The response will be a text converted to an upper case
