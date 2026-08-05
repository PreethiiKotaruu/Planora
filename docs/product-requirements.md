# Planora Product Requirements

## 1. Product Goal

Planora helps friends organize group activities by keeping suggestions, discussions, voting, and final decisions in one place.

## 2. Main User Journey

1. A user registers or logs in.
2. The user creates a private group.
3. The group owner shares an invitation link.
4. Friends join the group.
5. A group member creates a plan.
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

A guest cannot access private group or plan information.

### Group Member

A group member can:

- Create groups
- Join groups
- View groups they belong to
- View group members
- Create plans
- Add suggestions
- Vote
- Comment
- View final decisions
- Leave a group
- Submit feedback

### Group Owner

A group owner can perform all group-member actions and can also:

- Edit group details
- Generate invitation links
- Revoke invitation links
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
- Protected frontend routes
- Protected backend endpoints
- Basic user profile
- View and update display name

### Group Management

- Create a group
- View owned and joined groups
- View group details
- Edit group details
- Generate invitation links
- Revoke invitation links
- Join using an invitation
- View group members
- Leave a group
- Remove group members
- Delete a group

### Plan Management

- Create a plan
- View plan details
- Edit an active plan
- Cancel a plan
- View active plans
- View finalized plans
- View cancelled plans

### Suggestions

- Add suggestions
- View suggestions
- Edit your own suggestion before voting begins
- Delete your own suggestion before voting begins
- Include suggestions as poll options

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
- Allow the group owner to remove inappropriate comments

### User Feedback

- Submit a rating
- Describe what the user liked
- Describe what was confusing
- Suggest improvements

### User Experience

- Mobile-responsive interface
- Loading states
- Empty states
- Clear validation messages
- Clear error messages
- Basic search and filtering
- Confirmation before destructive actions

## 5. Business Rules

### Group Rules

- Only authenticated users can create groups.
- Only active group members can access private group information.
- Every group must have exactly one owner.
- The user who creates a group becomes its owner.
- The group creator is automatically added as an active member.
- A user cannot join the same group twice.
- Only the group owner can edit or delete the group.
- Only the group owner can remove another member.
- A group owner cannot remove themselves.
- A regular member can leave a group.
- The group owner cannot leave without transferring ownership or deleting the group.
- Removed or departed members immediately lose access to private group information.

### Invitation Rules

- Only the group owner can generate or revoke invitation links.
- Invitation codes must be difficult to guess.
- Only active and unexpired invitation links can be used.
- Expired invitation links cannot be used.
- Revoked invitation links cannot be used.
- A user who already belongs to the group cannot join again.

### Plan Rules

- Every plan belongs to exactly one group.
- Only active group members can create plans.
- A new plan starts with an active status.
- Only the plan creator or group owner can edit or cancel a plan.
- Finalized or cancelled plans cannot be edited.
- Cancelled and finalized plans remain available as history.
- Suggestions and votes cannot be added after a plan is finalized.

### Suggestion Rules

- Only active group members can add suggestions.
- Suggestions can be added only to active plans.
- Only the suggestion creator can edit their suggestion.
- The suggestion creator or group owner can remove a suggestion.
- Suggestions included in an open poll cannot be edited or deleted.

### Poll and Voting Rules

- A poll must contain at least two options.
- Only one active poll is allowed per plan in the MVP.
- Poll options cannot be modified after voting begins.
- Only active group members can vote.
- A user can have only one active vote per poll.
- A user can change their vote before the poll closes.
- Voting stops when the deadline passes or the poll is manually closed.
- Closed polls cannot accept new or changed votes.
- Closed polls cannot be reopened in the MVP.
- Only the plan creator or group owner can close a poll.
- Only the group owner can finalize a decision.
- The finalized option must belong to the plan's poll.

### Comment Rules

- Only active group members can view or add comments.
- Empty comments are rejected.
- Users can delete only their own comments.
- The group owner can remove inappropriate comments.
- Comments cannot be added to cancelled plans.

### Feedback Rules

- A feedback rating must be between 1 and 5.
- Feedback text must follow maximum-length limits.
- Feedback must not expose another user's private information.

## 6. Features Excluded From the MVP

The following features are not part of the first usable release:

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
- Detailed group activity-history screen
- Advanced administrative dashboard

These features may be considered after the MVP is stable and tested by real users.

## 7. MVP Completion Criteria

The MVP is complete when a real user can:

- Register and log in
- View and update their basic profile
- Create a private group
- Generate and share an invitation
- Join a group through a valid invitation
- View group members
- Create a plan
- Add suggestions
- Create a poll with at least two options
- Vote and change their vote before the poll closes
- Comment on a plan
- Close a poll
- Finalize a decision
- View active, cancelled, and finalized plans
- Submit product feedback
- Use the deployed application through a public URL
- Complete the main workflow on a mobile-sized screen