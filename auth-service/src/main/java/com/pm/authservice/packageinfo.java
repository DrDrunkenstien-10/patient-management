/**
 * Package: com.pm.authservice
 *
 * This package handles authentication and authorization in the application using 
 * stateless JWT access tokens and stateful refresh tokens stored in the database.
 * 
 * 🔐 Authentication Flow Overview:
 * 
 * 1. 🔑 Login - POST /api/v1/auth/login
 *    - Client submits email and password.
 *    - Server authenticates user via hashed password (BCrypt).
 *    - If valid:
 *        • Generates a short-lived access token (JWT with email, role, expiry).
 *        • Generates a long-lived refresh token (signed JWT).
 *        • Stores refresh token in DB table `refresh_tokens` with:
 *              - UUID (primary key)
 *              - User (foreign key)
 *              - Token (string)
 *              - Expiry (timestamp)
 *    - Returns: accessToken + refreshToken.
 *
 * 2. 🔐 Access Protected Endpoints
 *    - Client includes `Authorization: Bearer <accessToken>` header.
 *    - Server validates JWT signature, expiry, and claims (email/role).
 *
 * 3. 🔁 Refresh Access Token - POST /api/v1/auth/refresh
 *    - Client sends refreshToken.
 *    - Server validates:
 *        • Token signature and expiry (JWT verification).
 *        • Token presence in the database (i.e. it hasn’t been revoked).
 *        • Extracts email claim and loads user.
 *    - Generates and returns new accessToken (does NOT rotate refreshToken).
 *
 * 4. ✅ Validate Access Token - GET /api/v1/auth/validate
 *    - Accepts Authorization header.
 *    - Parses and validates JWT structure, signature, and expiry.
 *    - Returns 200 OK if valid; 401 Unauthorized if invalid.
 *
 * 5. 🚪 Logout - POST /api/v1/auth/logout
 *    - Client sends refreshToken to be revoked.
 *    - Server checks if token exists in DB.
 *    - If exists, deletes the token (effectively revoking it).
 *    - Further use of the same refreshToken will fail.
 *
 * 📦 Components Summary:
 * - AuthController: Exposes endpoints `/login`, `/refresh`, `/logout`, `/validate`.
 * - AuthService: Coordinates authentication logic, token issuance, DB operations.
 * - JWTUtil: Responsible for generating and verifying JWT tokens.
 * - RefreshToken (Entity): Maps to DB table for managing issued refresh tokens.
 * - RefreshTokenRepository: Spring Data JPA interface for token CRUD operations.
 *
 * ✅ Security Notes:
 * - Access tokens are short-lived and stateless.
 * - Refresh tokens are stored and validated server-side for added control.
 * - Logout ensures server-side invalidation of tokens.
 * - Passwords are hashed using BCrypt and never exposed. 
 */
package com.pm.authservice;
