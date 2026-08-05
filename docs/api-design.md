# Planora API Design

## Purpose

This document defines the initial REST API contract between the Planora React frontend and Spring Boot backend.

The API design may evolve during implementation, but major changes should be documented.

---

## 1. API Conventions

### Base URL

```text
/api/v1
```

Example:

```text
/api/v1/auth/register
/api/v1/groups
/api/v1/plans
```

The `/v1` portion represents the API version.

### Data Format

Requests and responses use JSON.

```http
Content-Type: application/json
```

### Authentication

Protected endpoints require a JWT access token.

```http
Authorization: Bearer <access-token>
```

### Identifiers

Planora uses UUID values for entity identifiers.

Example:

```text
8ae510cd-f948-44fe-9157-c92a6d28661a
```

### Date and Time Format

Dates and times use ISO 8601 format in UTC.

Example:

```text
2026-08-05T21:30:00Z
```

### Naming Style

JSON fields use camel case.

Example:

```json
{
  "displayName": "Preethi",
  "createdAt": "2026-08-05T21:30:00Z"
}
```

Database columns may use snake case:

```text
display_name
created_at
```

---

## 2. Standard Success Response

For endpoints returning one resource:

```json
{
  "data": {
    "id": "8ae510cd-f948-44fe-9157-c92a6d28661a",
    "name": "Weekend Crew"
  }
}
```

For list endpoints:

```json
{
  "data": [
    {
      "id": "8ae510cd-f948-44fe-9157-c92a6d28661a",
      "name": "Weekend Crew"
    }
  ],
  "page": {
    "number": 0,
    "size": 20,
    "totalElements": 1,
    "totalPages": 1
  }
}
```

---

## 3. Standard Error Response

All API errors should follow a consistent structure.

```json
{
  "timestamp": "2026-08-05T21:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "code": "VALIDATION_ERROR",
  "message": "The request contains invalid information.",
  "path": "/api/v1/auth/register",
  "fieldErrors": {
    "email": "Email must be valid.",
    "password": "Password must contain at least eight characters."
  }
}
```

Possible application error codes:

```text
VALIDATION_ERROR
INVALID_CREDENTIALS
ACCESS_DENIED
RESOURCE_NOT_FOUND
EMAIL_ALREADY_EXISTS
ALREADY_GROUP_MEMBER
INVALID_INVITATION
INVITATION_EXPIRED
INVITATION_REVOKED
POLL_CLOSED
DUPLICATE_VOTE
INVALID_POLL_OPTION
```

---

## 4. HTTP Status Codes

| Status | Meaning |
|---|---|
| `200 OK` | Request completed successfully |
| `201 Created` | New resource created |
| `204 No Content` | Request succeeded without response content |
| `400 Bad Request` | Invalid request data or business rule violation |
| `401 Unauthorized` | Authentication is missing or invalid |
| `403 Forbidden` | User is authenticated but lacks permission |
| `404 Not Found` | Requested resource does not exist |
| `409 Conflict` | Request conflicts with existing data |
| `500 Internal Server Error` | Unexpected server error |

Examples:

- Duplicate email → `409 Conflict`
- Invalid form input → `400 Bad Request`
- Missing JWT → `401 Unauthorized`
- Non-owner deleting a group → `403 Forbidden`
- Unknown group → `404 Not Found`

---

# 5. Authentication APIs

## Register User

```http
POST /api/v1/auth/register
```

### Access

Public

### Request

```json
{
  "displayName": "Preethi",
  "email": "preethi@example.com",
  "password": "SecurePass123",
  "confirmPassword": "SecurePass123"
}
```

### Success Response

```http
201 Created
```

```json
{
  "data": {
    "id": "8ae510cd-f948-44fe-9157-c92a6d28661a",
    "displayName": "Preethi",
    "email": "preethi@example.com",
    "createdAt": "2026-08-05T21:30:00Z"
  }
}
```

### Possible Errors

- Invalid email
- Weak password
- Passwords do not match
- Email already exists

---

## Log In

```http
POST /api/v1/auth/login
```

### Access

Public

### Request

```json
{
  "email": "preethi@example.com",
  "password": "SecurePass123"
}
```

### Success Response

```http
200 OK
```

```json
{
  "data": {
    "accessToken": "jwt-token-value",
    "tokenType": "Bearer",
    "expiresIn": 3600,
    "user": {
      "id": "8ae510cd-f948-44fe-9157-c92a6d28661a",
      "displayName": "Preethi",
      "email": "preethi@example.com"
    }
  }
}
```

### Possible Errors

- Invalid email or password
- Deactivated account

The API should return a general message:

```text
Invalid email or password.
```

It should not reveal which field was incorrect.

---

## View Current User

```http
GET /api/v1/users/me
```

### Access

Authenticated user

### Success Response

```json
{
  "data": {
    "id": "8ae510cd-f948-44fe-9157-c92a6d28661a",
    "displayName": "Preethi",
    "email": "preethi@example.com",
    "createdAt": "2026-08-05T21:30:00Z"
  }
}
```

---

## Update Current User

```http
PATCH /api/v1/users/me
```

### Access

Authenticated user

### Request

```json
{
  "displayName": "Preethi Kotaru"
}
```

### Success Response

```http
200 OK
```

### Logout

For the initial JWT implementation, the frontend logs the user out by removing its locally stored authentication token.

A server-side logout or token-revocation system may be added later if refresh tokens are introduced.

---

# 6. Group APIs

## Create Group

```http
POST /api/v1/groups
```

### Access

Authenticated user

### Request

```json
{
  "name": "Weekend Crew",
  "description": "Planning weekend activities and trips."
}
```

### Success Response

```http
201 Created
```

```json
{
  "data": {
    "id": "e6fc6935-93bd-4acd-acd5-68cb11e542dc",
    "name": "Weekend Crew",
    "description": "Planning weekend activities and trips.",
    "role": "OWNER",
    "memberCount": 1,
    "createdAt": "2026-08-05T21:30:00Z"
  }
}
```

### Business Behavior

Creating a group must happen in one database transaction:

1. Create the planning group.
2. Assign the creator as owner.
3. Create the owner's group-membership record.

If any step fails, the entire operation should roll back.

---

## View My Groups

```http
GET /api/v1/groups
```

### Access

Authenticated user

### Optional Query Parameters

```text
?page=0
&size=20
&status=ACTIVE
&search=weekend
&sort=updatedAt,desc
```

Example:

```http
GET /api/v1/groups?page=0&size=20&search=weekend
```

---

## View Group Details

```http
GET /api/v1/groups/{groupId}
```

### Access

Active group member

### Possible Errors

- Group not found
- User is not a group member

---

## Update Group

```http
PATCH /api/v1/groups/{groupId}
```

### Access

Group owner

### Request

```json
{
  "name": "Weekend Planning Crew",
  "description": "Trips, dinners, and weekend activities."
}
```

---

## Delete Group

```http
DELETE /api/v1/groups/{groupId}
```

### Access

Group owner

### Behavior

The group is soft-deleted.

### Success Response

```http
204 No Content
```

---

## View Group Members

```http
GET /api/v1/groups/{groupId}/members
```

### Access

Active group member

---

## Remove Group Member

```http
DELETE /api/v1/groups/{groupId}/members/{userId}
```

### Access

Group owner

### Rules

- Owner cannot remove themselves.
- Removed user loses access immediately.
- An activity-log entry is created.

---

## Leave Group

```http
POST /api/v1/groups/{groupId}/leave
```

### Access

Regular group member

### Rules

- Group owner cannot leave without transferring ownership or deleting the group.
- The membership status becomes `LEFT`.

---

# 7. Invitation APIs

## Create Invitation

```http
POST /api/v1/groups/{groupId}/invitations
```

### Access

Group owner

### Request

```json
{
  "expiresAt": "2026-08-12T21:30:00Z"
}
```

### Success Response

```http
201 Created
```

```json
{
  "data": {
    "id": "4a2179d6-03a6-4f79-b436-94dc630c2604",
    "code": "Vt8xP2Qa7m",
    "inviteUrl": "https://planora.app/invite/Vt8xP2Qa7m",
    "status": "ACTIVE",
    "expiresAt": "2026-08-12T21:30:00Z"
  }
}
```

---

## Preview Invitation

```http
GET /api/v1/invitations/{code}
```

### Access

Public

### Response Information

- Group name
- Group description
- Owner display name
- Number of members
- Invitation status
- Expiration time

Private plans, polls, and comments must not be included.

---

## Join Group

```http
POST /api/v1/invitations/{code}/join
```

### Access

Authenticated user

### Validation

- Invitation exists
- Invitation is active
- Invitation is not expired
- Invitation is not revoked
- User is not already a member

### Possible Errors

- `INVALID_INVITATION`
- `INVITATION_EXPIRED`
- `INVITATION_REVOKED`
- `ALREADY_GROUP_MEMBER`

---

## Revoke Invitation

```http
POST /api/v1/groups/{groupId}/invitations/{invitationId}/revoke
```

### Access

Group owner

---

# 8. Plan APIs

## Create Plan

```http
POST /api/v1/groups/{groupId}/plans
```

### Access

Active group member

### Request

```json
{
  "title": "Saturday Evening Outing",
  "description": "Let us decide what to do this weekend.",
  "proposedAt": "2026-08-15T19:00:00Z"
}
```

### Success Response

```http
201 Created
```

---

## View Group Plans

```http
GET /api/v1/groups/{groupId}/plans
```

### Access

Active group member

### Optional Query Parameters

```text
?page=0
&size=20
&status=ACTIVE
&search=saturday
&sort=updatedAt,desc
```

---

## View Plan Details

```http
GET /api/v1/plans/{planId}
```

### Access

Member of the plan's group

---

## Update Plan

```http
PATCH /api/v1/plans/{planId}
```

### Access

Plan creator or group owner

### Rules

- Plan must be active.
- Finalized or cancelled plans cannot be updated.

---

## Cancel Plan

```http
POST /api/v1/plans/{planId}/cancel
```

### Access

Plan creator or group owner

### Behavior

The plan status changes to `CANCELLED`.

---

# 9. Suggestion APIs

## Add Suggestion

```http
POST /api/v1/plans/{planId}/suggestions
```

### Access

Active group member

### Request

```json
{
  "title": "Bowling",
  "description": "Two games followed by food.",
  "location": "Main Event",
  "estimatedCost": 25.00
}
```

---

## View Suggestions

```http
GET /api/v1/plans/{planId}/suggestions
```

### Access

Active group member

---

## Update Suggestion

```http
PATCH /api/v1/suggestions/{suggestionId}
```

### Access

Suggestion creator

### Rules

- Suggestion cannot be changed after it is added to an open poll.
- Plan must still be active.

---

## Remove Suggestion

```http
DELETE /api/v1/suggestions/{suggestionId}
```

### Access

Suggestion creator or group owner

### Behavior

The suggestion is soft-deleted.

---

# 10. Poll APIs

## Create Poll

```http
POST /api/v1/plans/{planId}/polls
```

### Access

Plan creator or group owner

### Request

```json
{
  "question": "What should we do Saturday?",
  "suggestionIds": [
    "6b98a40d-f0a2-4dfb-83a3-b7d98ec6d1b0",
    "359d1ce1-ff63-4d48-8fc7-7d057859e866"
  ],
  "deadline": "2026-08-14T23:00:00Z"
}
```

### Validation

- Plan must be active.
- At least two options are required.
- Suggestions must belong to the same plan.
- Only one active poll is permitted per plan in the MVP.
- Deadline must be in the future.

---

## View Poll

```http
GET /api/v1/polls/{pollId}
```

### Access

Member of the related group

---

## Submit or Change Vote

```http
PUT /api/v1/polls/{pollId}/vote
```

### Access

Active group member

### Request

```json
{
  "optionId": "b60f30f4-6531-4bde-a1cb-39af17d4797b"
}
```

`PUT` is used because submitting again updates the user's existing vote instead of creating duplicate votes.

### Validation

- Poll must be open.
- Poll deadline must not have passed.
- User must belong to the group.
- Option must belong to the specified poll.

---

## View Poll Results

```http
GET /api/v1/polls/{pollId}/results
```

### Access

Active group member

### Example Response

```json
{
  "data": {
    "pollId": "ad9c9a22-c2bb-4127-bd71-e16487567a34",
    "question": "What should we do Saturday?",
    "status": "OPEN",
    "totalVotes": 5,
    "options": [
      {
        "optionId": "b60f30f4-6531-4bde-a1cb-39af17d4797b",
        "label": "Bowling",
        "voteCount": 3,
        "percentage": 60.0
      },
      {
        "optionId": "6b784db1-eaaf-4948-9c3d-d93add43c67c",
        "label": "Movie",
        "voteCount": 2,
        "percentage": 40.0
      }
    ]
  }
}
```

---

## Close Poll

```http
POST /api/v1/polls/{pollId}/close
```

### Access

Plan creator or group owner

### Behavior

- Poll status changes to `CLOSED`.
- Further votes are rejected.
- Closing time is recorded.

---

## Finalize Plan Decision

```http
POST /api/v1/plans/{planId}/finalize
```

### Access

Group owner

### Request

```json
{
  "optionId": "b60f30f4-6531-4bde-a1cb-39af17d4797b",
  "reason": "Bowling received the highest number of votes."
}
```

### Behavior

- Selected option must belong to the plan's poll.
- Plan status changes to `FINALIZED`.
- Further suggestions, votes, and plan changes are rejected.
- Final decision appears on the plan page.

---

# 11. Comment APIs

## Add Comment

```http
POST /api/v1/plans/{planId}/comments
```

### Access

Active group member

### Request

```json
{
  "content": "Bowling works well for me."
}
```

---

## View Comments

```http
GET /api/v1/plans/{planId}/comments
```

### Access

Active group member

### Optional Query Parameters

```text
?page=0
&size=20
&sort=createdAt,asc
```

---

## Delete Comment

```http
DELETE /api/v1/comments/{commentId}
```

### Access

Comment author or group owner

### Behavior

The comment is soft-deleted.

---

# 12. Activity History API

## View Group Activity

```http
GET /api/v1/groups/{groupId}/activities
```

### Access

Active group member

### Optional Query Parameters

```text
?page=0
&size=20
&actionType=MEMBER_JOINED
```

---

## View Plan Activity

```http
GET /api/v1/plans/{planId}/activities
```

### Access

Active group member

---

# 13. Feedback API

## Submit Feedback

```http
POST /api/v1/feedback
```

### Access

Authenticated user or anonymous user, depending on the final design

### Request

```json
{
  "rating": 4,
  "liked": "The voting flow was easy.",
  "confusing": "I did not immediately understand how to join a group.",
  "suggestion": "Make the invitation button more visible."
}
```

### Validation

- Rating must be between 1 and 5.
- Text fields have maximum-length limits.

---

# 14. System APIs

## Application Health Check

```http
GET /api/v1/health
```

### Access

Public or monitoring service

### Example Response

```json
{
  "status": "UP"
}
```

Spring Boot Actuator may later provide the production health endpoint.

---

# 15. Authorization Summary

| Action | Guest | Member | Plan Creator | Group Owner |
|---|:---:|:---:|:---:|:---:|
| Register or log in | ✅ | ✅ | ✅ | ✅ |
| Preview invitation | ✅ | ✅ | ✅ | ✅ |
| Create group | ❌ | ✅ | ✅ | ✅ |
| View private group | ❌ | ✅ | ✅ | ✅ |
| Edit group | ❌ | ❌ | ❌ | ✅ |
| Invite members | ❌ | ❌ | ❌ | ✅ |
| Create plan | ❌ | ✅ | ✅ | ✅ |
| Edit plan | ❌ | ❌ | ✅ | ✅ |
| Add suggestion | ❌ | ✅ | ✅ | ✅ |
| Create poll | ❌ | ❌ | ✅ | ✅ |
| Vote | ❌ | ✅ | ✅ | ✅ |
| Close poll | ❌ | ❌ | ✅ | ✅ |
| Finalize decision | ❌ | ❌ | ❌ | ✅ |
| Add comment | ❌ | ✅ | ✅ | ✅ |
| Remove member | ❌ | ❌ | ❌ | ✅ |
| Delete group | ❌ | ❌ | ❌ | ✅ |

Authentication alone is not enough. The backend must verify group membership and ownership for every protected operation.

---

# 16. Pagination and Sorting

List endpoints should support pagination.

Example:

```http
GET /api/v1/groups?page=0&size=20&sort=updatedAt,desc
```

Parameters:

| Parameter | Meaning |
|---|---|
| `page` | Page number, starting from 0 |
| `size` | Number of records per page |
| `sort` | Field and direction |
| `search` | Optional text search |
| `status` | Optional status filter |

The backend must set a maximum allowed page size to prevent very large responses.

---

# 17. API Security Requirements

- Passwords must never appear in responses.
- Passwords and tokens must never be written to application logs.
- Protected APIs require a valid JWT.
- Group membership must be checked on the backend.
- Owner permissions must be checked on the backend.
- User input must be validated.
- Database queries must use safe parameter binding.
- Secrets must be stored in environment variables.
- API error responses must not expose internal stack traces.
- Rate limiting may be added to login, registration, invitation, and AI endpoints.

Frontend restrictions are for user experience only. They do not replace backend authorization.

---

# 18. Open API Decisions

The following decisions will be finalized during implementation:

- Whether registration automatically logs in the user
- Whether JWT tokens will use refresh tokens
- Where the frontend safely stores authentication state
- Whether invitation links are single-use or reusable
- Whether poll results remain visible while voting is open
- Whether anonymous feedback is allowed
- Whether finalized decisions require an explanation
- Whether activity-log descriptions are stored or generated dynamically

These decisions should be recorded before implementing the affected feature.