# wc-winner

Spring Boot REST API for World Cup 2026 teams — seed the team list, fetch all teams, or pick a random team.

## Stack

- Java 21, Spring Boot 3.5
- Spring Data JPA + PostgreSQL
- Spring Security + JWT auth
- springdoc-openapi (Swagger UI)
- Lombok

## API

| Method | Path                  | Auth | Description                              |
|--------|-----------------------|------|-------------------------------------------|
| POST   | `/api/auth/signup`    | No   | Create user, returns JWT                 |
| POST   | `/api/auth/login`     | No   | Login, returns JWT                        |
| GET    | `/api/teams`          | Yes  | List all teams                            |
| GET    | `/api/teams/predict`  | Yes  | Get one random team                       |
| POST   | `/api/teams/seed`     | Yes  | Seed DB with WC 2026 teams (idempotent)   |

Everything except `/api/auth/**` requires `Authorization: Bearer <token>`.

Swagger UI: `http://localhost:8080/swagger-ui.html`

## Setup

### Prerequisites

- Java 21
- PostgreSQL running locally with a `wc-predict` database

### Configure

App reads DB creds from env vars (fallback `postgres`/`postgres`):

```
spring.datasource.username=${DB_USER:postgres}
spring.datasource.password=${DB_PASS:postgres}
```

Create a `.env` file (gitignored) at the project root:

```
DB_USER=postgres
DB_PASS=postgres
API_FOOTBALL_KEY=
JWT_SECRET=
```

Export before running, e.g.:

```bash
export $(grep -v '^#' .env | xargs)
```

### Run

```bash
./mvnw spring-boot:run
```

App starts on `http://localhost:8080`. Tables are auto-created/updated via `spring.jpa.hibernate.ddl-auto=update`.

Sign up to get a token:

```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H 'Content-Type: application/json' \
  -d '{"username":"alice","password":"password123"}'
```

Use the returned token for everything else:

```bash
curl -X POST http://localhost:8080/api/teams/seed \
  -H 'Authorization: Bearer <token>'
```

### Test

```bash
./mvnw test
```
