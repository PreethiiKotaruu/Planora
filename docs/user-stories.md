# Planora User Stories

## Epic 1: Authentication

### User Registration

As a guest, I want to create an account so that I can use Planora.

#### Acceptance Criteria

- The user provides a name, email, password, and password confirmation.
- The email must be valid and unique.
- The password must meet security requirements.
- The password must be hashed before storage.
- Clear validation messages must be displayed for invalid input.

### User Login

As a registered user, I want to log in securely so that I can access my groups and plans.

#### Acceptance Criteria

- The user can log in using a valid email and password.
- Valid credentials generate an authentication token.
- Invalid credentials display a general error message.
- The user is redirected to the dashboard after login.

### User Logout

As a logged-in user, I want to log out so that my account remains secure.

#### Acceptance Criteria

- Authentication information is removed.
- The user is redirected to the login or landing page.
- Protected pages cannot be accessed after logout.

---

## Epic 2: Group Management

### Create a Group

As a registered user, I want to create a private group so that I can organize plans with friends.

#### Acceptance Criteria

- Only authenticated users can create groups.
- The group name is required.
- The creator becomes the group owner.
- The creator is automatically added as a group member.

### Invite Members

As a group owner, I want to create an invitation link so that friends can join my group.

#### Acceptance Criteria

- Only the group owner can create invitations.
- The invitation code must be difficult to guess.
- An invitation may have an expiration date.
- Revoked or expired invitations cannot be used.

### Join a Group

As a registered user, I want to join a group through a valid invitation.

#### Acceptance Criteria

- The user must be logged in.
- The invitation must be valid, active, and not expired.
- The user cannot join the same group twice.
- The user is redirected to the group page after joining.

### Manage Group Members

As a group owner, I want to manage members so that I can control access to my group.

#### Acceptance Criteria

- The owner can view and remove members.
- The owner cannot remove themselves.
- Removed members immediately lose access to private group content.

---

## Epic 3: Plans and Suggestions

### Create a Plan

As a group member, I want to create a plan so that the group can organize an activity.

#### Acceptance Criteria

- Only group members can create plans.
- A title is required.
- A plan belongs to exactly one group.
- A new plan starts with an active status.

### Add a Suggestion

As a group member, I want to suggest an option so that it can be considered by the group.

#### Acceptance Criteria

- The plan must be active.
- The suggestion must contain a title.
- The suggestion creator and creation time are recorded.
- Suggestions cannot be added after the plan is finalized or cancelled.

### Edit or Cancel a Plan

As the plan creator or group owner, I want to update or cancel a plan.

#### Acceptance Criteria

- Only authorized users can edit or cancel the plan.
- Finalized or cancelled plans cannot be edited.
- Cancelled plans remain visible as history.

---

## Epic 4: Polls and Voting

### Create a Poll

As a plan creator or group owner, I want to create a poll so that members can vote on suggestions.

#### Acceptance Criteria

- The poll must contain at least two options.
- The plan must be active.
- Only one active poll is allowed per plan in the MVP.
- Poll options cannot be changed after voting begins.

### Submit a Vote

As a group member, I want to vote for my preferred option.

#### Acceptance Criteria

- Only group members can vote.
- A user can have only one active vote per poll.
- A user may change their vote before the poll closes.
- Voting is rejected after the deadline or after the poll closes.

### View Poll Results

As a group member, I want to view poll results so that I can understand the group's preference.

#### Acceptance Criteria

- Results display vote counts and percentages.
- The total number of votes is shown.
- Only group members can view results.

### Finalize a Decision

As a group owner, I want to finalize an option so that everyone knows the confirmed plan.

#### Acceptance Criteria

- Only the group owner can finalize a decision.
- The selected option is clearly displayed.
- The plan status changes to finalized.
- No additional voting or suggestions are accepted.

---

## Epic 5: Comments

### Add a Comment

As a group member, I want to comment on a plan so that I can participate in the discussion.

#### Acceptance Criteria

- Only group members can comment.
- The comment displays the author and creation time.
- Empty comments are rejected.

### Delete a Comment

As a user, I want to delete my own comment.

#### Acceptance Criteria

- Users can delete only their own comments.
- The group owner may remove inappropriate comments.
- Unauthorized deletion requests are rejected.

---

## Epic 6: User Feedback

### Submit Feedback

As a user, I want to submit feedback so that the Planora experience can be improved.

#### Acceptance Criteria

- The user can provide a rating.
- The user can describe what they liked or found confusing.
- The user can submit feature suggestions.
- Feedback is stored for later review.

---

## Epic 7: AI Smart Planner

This epic is planned for after the MVP.

### Generate Plan Suggestions

As a group member, I want AI-generated suggestions based on the group's budget, time, and preferences.

#### Acceptance Criteria

- AI suggestions are optional.
- The response is returned in a structured format.
- Users can accept, reject, or edit suggestions.
- Manual planning continues to work when the AI service is unavailable.
- AI failures display a clear fallback message.