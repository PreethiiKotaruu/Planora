# Planora Product Requirements

## 1. Product Goal

Planora helps friends organize group activities by keeping suggestions, discussions, voting, and final decisions in one place.

## 2. Main User Journey

1. A user registers or logs in.
2. The user creates a private group.
3. The group owner shares an invitation link.
4. Friends join the group.
5. A member creates a plan.
6. Members add suggestions.
7. A poll is created.
8. Members vote and comment.
9. The poll is closed.
10. The group owner finalizes the decision.

## 3. User Roles

### Guest

A guest can:

- View the landing page
- Register
- Log in
- Open an invitation link

### Group Member

A group member can:

- Create groups
- Join groups
- View group members
- Create plans
- Add suggestions
- Vote
- Comment
- View final decisions
- Leave a group

### Group Owner

A group owner can perform all member actions and can also:

- Edit group details
- Generate or revoke invitations
- Remove members
- Close polls
- Finalize decisions
- Delete the group

## 4. MVP Features

### Authentication

- User registration
- User login
- User logout
- Secure password storage
- JWT authentication
- Protected frontend and backend routes
- Basic user profile

### Group Management

- Create a group
- View joined groups
- Edit group details
- Generate invitation links
- Join using an invitation
- View group members
- Leave a group
- Remove members
- Delete a group

### Plan Management

- Create a plan
- View plan details
- Edit an active plan
- Cancel a plan
- View active, finalized, and cancelled plans

### Suggestions

- Add suggestions
- View suggestions
- Edit your own suggestion before voting begins
- Delete your own suggestion before voting begins

### Polls and Voting

- Create a poll
- Add multiple poll options
- Set an optional deadline
- Submit a vote
- Change a vote before the poll closes
- Prevent duplicate votes
- View poll results
- Close a poll
- Finalize an option

### Comments

- Add comments
- View comments
- Delete your own comments

### User Experience

- Mobile-responsive interface
- Loading states
- Empty states
- Clear validation messages
- Clear error messages
- Basic search and filtering

## 5. Business Rules

- Only group members can access private group information.
- Every group must have exactly one owner.
- A user cannot join the same group twice.
- Expired or revoked invitations cannot be used.
- A user can have only one active vote per poll.
- Voting stops when the deadline passes or the poll is closed.
- Closed polls cannot be modified.
- Only authorized users can remove members or delete groups.
- Only the group owner can finalize a decision.
- Cancelled and finalized plans remain available as history.

## 6. Features Excluded From the MVP

- AI Smart Planner
- Expense splitting
- Availability calendar
- Task assignments
- Real-time chat
- Push notifications
- Calendar integration
- Location recommendations
- Payments
- Kafka
- Microservices
- Native mobile application

These features may be added after the MVP is stable.

## 7. MVP Completion Criteria

The MVP is complete when a real user can:

1. Register and log in
2. Create a private group
3. Invite friends
4. Join a group
5. Create a plan
6. Add suggestions
7. Create a poll
8. Vote and comment
9. Finalize a decision
10. Use the deployed application through a public link