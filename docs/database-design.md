# Planora Database Design

## Purpose

This document defines the initial PostgreSQL database structure for Planora, including tables, relationships, constraints, indexes, and important business rules.

This is an initial design and may evolve during implementation.

---

## 1. Main Entities

Planora initially requires the following entities:

1. User
2. Planning Group
3. Group Membership
4. Invitation
5. Plan
6. Suggestion
7. Poll
8. Poll Option
9. Vote
10. Comment
11. Activity Log
12. User Feedback

---

## 2. Relationship Overview

A user can belong to multiple planning groups, and a planning group can contain multiple users.

This many-to-many relationship is managed through the `GroupMembership` entity.

```text
User
  |
  v
GroupMembership
  |
  v
PlanningGroup
```

A planning group contains plans, and each plan can contain suggestions, polls, votes, and comments.

```text
PlanningGroup
      |
      v
     Plan
      |
      +-------------------+
      |                   |
      v                   v
 Suggestion              Poll
                          |
                          v
                     PollOption
                          |
                          v
                         Vote
```

---

## 3. Users Table

### Table Name

```text
users
```

### Purpose

Stores registered Planora accounts.

### Suggested Fields

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key |
| `display_name` | VARCHAR(100) | Name displayed in the application |
| `email` | VARCHAR(255) | User's unique login email |
| `password_hash` | VARCHAR(255) | Hashed password |
| `status` | VARCHAR(30) | `ACTIVE` or `DEACTIVATED` |
| `created_at` | TIMESTAMP | Account creation time |
| `updated_at` | TIMESTAMP | Last account update time |

### Constraints

- `id` must be unique.
- `display_name` cannot be null.
- `email` cannot be null.
- `email` must be unique.
- `password_hash` cannot be null.
- Passwords must never be stored as plain text.

---

## 4. Planning Groups Table

### Table Name

```text
planning_groups
```

The name `planning_groups` is used instead of `groups` because `GROUP` is a reserved SQL keyword.

### Purpose

Stores private groups created for planning activities.

### Suggested Fields

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key |
| `name` | VARCHAR(60) | Group name |
| `description` | VARCHAR(500) | Optional group description |
| `owner_id` | UUID | References `users.id` |
| `status` | VARCHAR(30) | `ACTIVE` or `DELETED` |
| `created_at` | TIMESTAMP | Group creation time |
| `updated_at` | TIMESTAMP | Last group update time |

### Constraints

- Every group must have exactly one owner.
- `owner_id` cannot be null.
- `name` cannot be null.
- Group name must contain between 3 and 60 characters.

### Business Rules

- The user who creates the group becomes its owner.
- Only the owner can edit or delete the group.
- Deleted groups should not appear in normal user searches.

---

## 5. Group Memberships Table

### Table Name

```text
group_memberships
```

### Purpose

Connects users to the planning groups they belong to.

### Suggested Fields

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key |
| `user_id` | UUID | References `users.id` |
| `group_id` | UUID | References `planning_groups.id` |
| `role` | VARCHAR(30) | `OWNER` or `MEMBER` |
| `status` | VARCHAR(30) | `ACTIVE`, `LEFT`, or `REMOVED` |
| `joined_at` | TIMESTAMP | Membership creation time |
| `left_at` | TIMESTAMP | Optional membership ending time |

### Important Constraint

A user cannot have duplicate membership in the same group.

```text
UNIQUE(user_id, group_id)
```

### Business Rules

- The group creator receives the `OWNER` role.
- The creator is automatically added as a group member.
- A group must have exactly one active owner.
- A regular member can leave the group.
- The owner cannot leave without transferring ownership or deleting the group.
- Removed or departed users lose access to private group information.

---

## 6. Invitations Table

### Table Name

```text
invitations
```

### Purpose

Stores secure invitation links that allow users to join groups.

### Suggested Fields

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key |
| `group_id` | UUID | References `planning_groups.id` |
| `code` | VARCHAR(100) | Unique invitation code |
| `created_by` | UUID | References `users.id` |
| `status` | VARCHAR(30) | `ACTIVE`, `REVOKED`, or `EXPIRED` |
| `expires_at` | TIMESTAMP | Invitation expiration time |
| `created_at` | TIMESTAMP | Invitation creation time |

### Constraints

- `code` must be unique.
- `group_id` cannot be null.
- `created_by` cannot be null.
- The invitation code must be difficult to guess.

### Business Rules

- Only the group owner can generate or revoke invitations.
- Expired invitations cannot be used.
- Revoked invitations cannot be used.
- A valid invitation allows a logged-in user to join the associated group.
- A user who already belongs to the group cannot join again.

---

## 7. Plans Table

### Table Name

```text
plans
```

### Purpose

Stores activities or decisions that a group wants to organize.

### Suggested Fields

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key |
| `group_id` | UUID | References `planning_groups.id` |
| `created_by` | UUID | References `users.id` |
| `title` | VARCHAR(100) | Plan title |
| `description` | VARCHAR(1000) | Optional plan description |
| `proposed_at` | TIMESTAMP | Optional proposed date and time |
| `status` | VARCHAR(30) | `ACTIVE`, `FINALIZED`, or `CANCELLED` |
| `finalized_option_id` | UUID | Optional reference to the selected poll option |
| `created_at` | TIMESTAMP | Plan creation time |
| `updated_at` | TIMESTAMP | Last update time |

### Constraints

- `group_id` cannot be null.
- `created_by` cannot be null.
- `title` cannot be null.
- The title must contain between 3 and 100 characters.

### Business Rules

- Every plan belongs to one planning group.
- Only active group members can create plans.
- Only the plan creator or group owner can edit or cancel a plan.
- Finalized or cancelled plans cannot be edited.
- Cancelled and finalized plans remain available as history.
- Suggestions and votes cannot be added after finalization.

---

## 8. Suggestions Table

### Table Name

```text
suggestions
```

### Purpose

Stores options proposed by group members for a plan.

### Suggested Fields

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key |
| `plan_id` | UUID | References `plans.id` |
| `created_by` | UUID | References `users.id` |
| `title` | VARCHAR(100) | Suggestion title |
| `description` | VARCHAR(500) | Optional description |
| `location` | VARCHAR(255) | Optional location |
| `estimated_cost` | DECIMAL(10,2) | Optional estimated cost |
| `status` | VARCHAR(30) | `ACTIVE` or `REMOVED` |
| `created_at` | TIMESTAMP | Suggestion creation time |
| `updated_at` | TIMESTAMP | Last update time |

### Constraints

- `plan_id` cannot be null.
- `created_by` cannot be null.
- `title` cannot be null.
- Estimated cost cannot be negative.

### Business Rules

- Only group members can add suggestions.
- Suggestions can be added only while the plan is active.
- Only the suggestion creator can edit their suggestion.
- The suggestion creator or group owner can remove a suggestion.
- Suggestions included in an open poll cannot be edited or removed.

---

## 9. Polls Table

### Table Name

```text
polls
```

### Purpose

Stores voting sessions created for plans.

### Suggested Fields

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key |
| `plan_id` | UUID | References `plans.id` |
| `created_by` | UUID | References `users.id` |
| `question` | VARCHAR(255) | Poll question |
| `status` | VARCHAR(30) | `DRAFT`, `OPEN`, or `CLOSED` |
| `deadline` | TIMESTAMP | Optional voting deadline |
| `created_at` | TIMESTAMP | Poll creation time |
| `opened_at` | TIMESTAMP | Optional opening time |
| `closed_at` | TIMESTAMP | Optional closing time |

### Constraints

- `plan_id` cannot be null.
- `created_by` cannot be null.
- `question` cannot be null.
- The deadline must be in the future when the poll is created.

### Business Rules

- The MVP allows only one active poll per plan.
- A poll must contain at least two options before it can be opened.
- Poll options cannot change after voting begins.
- Voting stops after the deadline.
- Voting also stops when the poll is manually closed.
- Closed polls cannot be reopened in the MVP.

---

## 10. Poll Options Table

### Table Name

```text
poll_options
```

### Purpose

Stores the choices available inside a poll.

### Suggested Fields

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key |
| `poll_id` | UUID | References `polls.id` |
| `suggestion_id` | UUID | Optional reference to `suggestions.id` |
| `label` | VARCHAR(150) | Text shown as the poll option |
| `created_at` | TIMESTAMP | Option creation time |

### Constraints

- `poll_id` cannot be null.
- `label` cannot be null.
- A poll option must belong to exactly one poll.

### Business Rules

- A poll must contain at least two options.
- An option may be created from an existing suggestion.
- Poll options cannot be edited after the poll opens.

---

## 11. Votes Table

### Table Name

```text
votes
```

### Purpose

Stores votes submitted by group members.

### Suggested Fields

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key |
| `poll_id` | UUID | References `polls.id` |
| `option_id` | UUID | References `poll_options.id` |
| `user_id` | UUID | References `users.id` |
| `created_at` | TIMESTAMP | Original voting time |
| `updated_at` | TIMESTAMP | Time the vote was last changed |

### Important Constraint

A user can have only one vote in each poll.

```text
UNIQUE(user_id, poll_id)
```

### Business Rules

- Only group members can vote.
- The selected option must belong to the specified poll.
- Open polls can accept votes.
- Closed or expired polls cannot accept votes.
- When a user changes their vote, the existing vote record is updated instead of inserting a second record.

---

## 12. Comments Table

### Table Name

```text
comments
```

### Purpose

Stores discussion messages attached to plans.

### Suggested Fields

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key |
| `plan_id` | UUID | References `plans.id` |
| `author_id` | UUID | References `users.id` |
| `content` | VARCHAR(1000) | Comment text |
| `status` | VARCHAR(30) | `ACTIVE` or `DELETED` |
| `created_at` | TIMESTAMP | Comment creation time |
| `updated_at` | TIMESTAMP | Last edit time |

### Constraints

- `plan_id` cannot be null.
- `author_id` cannot be null.
- `content` cannot be empty.

### Business Rules

- Only group members can comment.
- Users can delete only their own comments.
- Group owners can remove inappropriate comments.
- Deleted comments are hidden but may be retained for moderation history.
- Comments cannot be added to cancelled plans.

---

## 13. Activity Logs Table

### Table Name

```text
activity_logs
```

### Purpose

Stores important actions performed inside a group or plan.

### Suggested Fields

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key |
| `group_id` | UUID | References `planning_groups.id` |
| `plan_id` | UUID | Optional reference to `plans.id` |
| `actor_id` | UUID | References `users.id` |
| `action_type` | VARCHAR(100) | Type of action performed |
| `description` | VARCHAR(500) | Human-readable action description |
| `created_at` | TIMESTAMP | Action time |

### Example Action Types

```text
GROUP_CREATED
GROUP_UPDATED
MEMBER_JOINED
MEMBER_LEFT
MEMBER_REMOVED
PLAN_CREATED
PLAN_CANCELLED
SUGGESTION_ADDED
POLL_OPENED
VOTE_SUBMITTED
POLL_CLOSED
PLAN_FINALIZED
```

### Business Rules

- Important actions should generate an activity record.
- Activity records should not be editable by normal users.
- Only members of the related group can view its activity history.

---

## 14. User Feedback Table

### Table Name

```text
user_feedback
```

### Purpose

Stores feedback collected from real users after deployment.

### Suggested Fields

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key |
| `user_id` | UUID | Optional reference to `users.id` |
| `rating` | INTEGER | Rating between 1 and 5 |
| `liked` | VARCHAR(1000) | What the user liked |
| `confusing` | VARCHAR(1000) | What the user found confusing |
| `suggestion` | VARCHAR(1000) | Suggested improvement |
| `created_at` | TIMESTAMP | Feedback submission time |

### Constraints

- Rating must be between 1 and 5.
- Feedback may optionally be submitted anonymously.

---

## 15. Main Relationships

```text
User 1 ─────── * PlanningGroup
                 through owner_id

User * ─────── * PlanningGroup
                 through GroupMembership

PlanningGroup 1 ─────── * GroupMembership

PlanningGroup 1 ─────── * Invitation

PlanningGroup 1 ─────── * Plan

Plan 1 ─────── * Suggestion

Plan 1 ─────── 0..1 active Poll

Poll 1 ─────── * PollOption

User 1 ─────── * Vote

Poll 1 ─────── * Vote

PollOption 1 ─────── * Vote

Plan 1 ─────── * Comment

PlanningGroup 1 ─────── * ActivityLog

User 1 ─────── * UserFeedback
```

### Meaning of the Symbols

```text
1       → one record
*       → many records
0..1    → zero or one record
```

Example:

```text
PlanningGroup 1 ─────── * Plan
```

This means:

> One planning group can contain many plans, while each plan belongs to one planning group.

---

## 16. Initial Indexes

Indexes improve searches but also increase storage and update cost. We will add them only where they support real queries.

Possible initial indexes:

```text
users.email
planning_groups.owner_id
group_memberships.user_id
group_memberships.group_id
invitations.code
invitations.group_id
plans.group_id
plans.status
suggestions.plan_id
polls.plan_id
votes.poll_id
votes.user_id
comments.plan_id
activity_logs.group_id
activity_logs.plan_id
```

We will verify index usefulness later using PostgreSQL query plans and performance benchmarks.

---

## 17. Deletion Strategy

Planora will use soft deletion for important business records where preserving history is useful.

Instead of immediately removing a record, its status changes:

```text
ACTIVE → DELETED
```

Soft deletion may be used for:

- Planning groups
- Suggestions
- Comments
- User accounts

Plans will use statuses such as:

```text
ACTIVE
FINALIZED
CANCELLED
```

### Why Preserve History?

Preserving records helps with:

- Activity history
- Debugging
- Auditing
- User support
- Understanding previous decisions

Permanent deletion requirements for personal user data will be designed separately before production release.

---

## 18. Open Design Decisions

The following decisions will be confirmed during implementation:

- Whether group ownership is stored only in `planning_groups.owner_id`, only in `group_memberships.role`, or consistently in both
- Whether invitations can be used once or by multiple users
- Whether poll results are visible before voting closes
- Whether a plan can contain more than one poll in future versions
- Whether comments can be edited
- How long soft-deleted data should be retained
- Which foreign-key deletion behaviors should use `CASCADE`, `RESTRICT`, or `SET NULL`

These decisions will be recorded before implementing the affected feature.