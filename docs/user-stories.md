# Planora User Stories

## Epic 1: Authentication

### User Registration

As a guest, I want to create an account so that I can use Planora.

#### Acceptance Criteria

- The user provides a display name, email, password, and password confirmation.
- The display name is required.
- The email must be valid and unique.
- The password must meet the defined security requirements.
- The password and password confirmation must match.
- The password must be hashed before storage.
- The password must never be returned by the API.
- Clear validation messages must be displayed for invalid input.

### User Login

As a registered user, I want to log in securely so that I can access my groups and plans.

#### Acceptance Criteria

- The user can log in using a valid email and password.
- Valid credentials generate an authentication token.
- Invalid credentials display a general error message.
- The error message does not reveal whether the email or password was incorrect.
- The user is redirected to the dashboard after successful login.
- Deactivated accounts cannot log in.

### User Logout

As a logged-in user, I want to log out so that my account remains secure.

#### Acceptance Criteria

- Authentication information is removed from the frontend.
- The user is redirected to the login or landing page.
- Protected pages cannot be accessed after logout.
- Protected backend endpoints reject requests without a valid token.

### View and Update Profile

As a logged-in user, I want to view and update my basic profile information.

#### Acceptance Criteria

- The user can view their display name and email.
- The user can update their display name.
- Updated profile information must pass validation.
- The user cannot access another user's private profile.
- The user's password is never included in the profile response.

---

## Epic 2: Group Management

### Create a Group

As a registered user, I want to create a private group so that I can organize plans with friends.

#### Acceptance Criteria

- Only authenticated users can create groups.
- The group name is required.
- The group name must follow the defined length limits.
- The creator becomes the group owner.
- The creator is automatically added as an active group member.
- The created group appears on the user's dashboard.

### View My Groups

As a registered user, I want to view all groups I belong to so that I can access my plans.

#### Acceptance Criteria

- The dashboard displays groups owned and joined by the user.
- Private groups belonging to other users are not displayed.
- Each group displays the user's role.
- Deleted groups are not displayed.
- An empty state is displayed when the user does not belong to any groups.

### View Group Details

As a group member, I want to view group information so that I can understand the group's purpose.

#### Acceptance Criteria

- Only active group members can view private group details.
- The page displays the group name and description.
- The page identifies the group owner.
- Unauthorized users are denied access.

### Edit Group Details

As a group owner, I want to edit the group name and description.

#### Acceptance Criteria

- Only the group owner can edit group details.
- The updated name and description must pass validation.
- The updated information is visible to group members.
- Unauthorized requests are rejected.

### Invite Members

As a group owner, I want to create an invitation link so that friends can join my group.

#### Acceptance Criteria

- Only the group owner can create invitations.
- The invitation code must be difficult to guess.
- An invitation may have an expiration date.
- The generated invitation link can be copied and shared.
- Revoked or expired invitations cannot be used.

### Preview an Invitation

As a guest or registered user, I want to preview an invitation so that I know which group I am joining.

#### Acceptance Criteria

- A valid invitation displays the group name and basic group information.
- Private plans, polls, comments, and member information are not exposed.
- Expired invitations display an appropriate message.
- Revoked invitations display an appropriate message.

### Join a Group

As a registered user, I want to join a group through a valid invitation.

#### Acceptance Criteria

- The user must be logged in.
- The invitation must exist.
- The invitation must be active and unexpired.
- The user cannot join the same group twice.
- A group-membership record is created.
- The user is redirected to the group page after joining.

### Revoke an Invitation

As a group owner, I want to revoke an invitation so that it can no longer be used.

#### Acceptance Criteria

- Only the group owner can revoke an invitation.
- A revoked invitation cannot be used to join the group.
- Revoking an invitation does not remove existing group members.

### View Group Members

As a group member, I want to see who belongs to the group.

#### Acceptance Criteria

- Only active group members can view the member list.
- The list displays each member's display name and role.
- The group owner is clearly identified.
- Removed or departed users are not displayed as active members.

### Manage Group Members

As a group owner, I want to remove members so that I can control access to my group.

#### Acceptance Criteria

- Only the group owner can remove another member.
- The owner cannot remove themselves.
- Removed members immediately lose access to private group content.
- Removing one member does not affect other members.

### Leave a Group

As a group member, I want to leave a group that I no longer participate in.

#### Acceptance Criteria

- A regular member can leave the group.
- The member's status changes from active to left.
- The member immediately loses access to private group content.
- The group disappears from the former member's active-group list.
- The group owner cannot leave without transferring ownership or deleting the group.

### Delete a Group

As a group owner, I want to delete a group that is no longer needed.

#### Acceptance Criteria

- Only the group owner can delete the group.
- The user must confirm the deletion.
- The deleted group no longer appears on member dashboards.
- Group members can no longer access its private content.
- The group is preserved according to the project's soft-deletion strategy.

---

## Epic 3: Plans and Suggestions

### Create a Plan

As a group member, I want to create a plan so that the group can organize an activity.

#### Acceptance Criteria

- Only active group members can create plans.
- A title is required.
- A plan belongs to exactly one group.
- The creator and creation time are recorded.
- A new plan starts with an active status.
- The new plan appears on the group page.

### View Plan Details

As a group member, I want to view a plan so that I can participate in planning.

#### Acceptance Criteria

- Only members of the related group can view the plan.
- The page displays the title, description, creator, proposed date, and status.
- Suggestions, polls, and comments related to the plan are displayed.
- Unauthorized users are denied access.

### Edit a Plan

As the plan creator or group owner, I want to update an active plan.

#### Acceptance Criteria

- Only the plan creator or group owner can edit the plan.
- The plan must have an active status.
- Updated values must pass validation.
- Finalized or cancelled plans cannot be edited.

### Cancel a Plan

As the plan creator or group owner, I want to cancel a plan that will no longer continue.

#### Acceptance Criteria

- Only the plan creator or group owner can cancel the plan.
- The user must confirm the cancellation.
- The plan status changes to cancelled.
- Cancelled plans remain visible as history.
- New suggestions and votes are rejected after cancellation.

### View Plans by Status

As a group member, I want to view active, finalized, and cancelled plans.

#### Acceptance Criteria

- Only members of the related group can view its plans.
- Plans can be filtered by status.
- Active, finalized, and cancelled plans are clearly identified.
- An appropriate empty state is shown when no plans match the selected filter.

### Add a Suggestion

As a group member, I want to suggest an option so that it can be considered by the group.

#### Acceptance Criteria

- Only active group members can add suggestions.
- The plan must be active.
- The suggestion must contain a title.
- The suggestion creator and creation time are recorded.
- Suggestions cannot be added after the plan is finalized or cancelled.

### View Suggestions

As a group member, I want to view all suggestions for a plan.

#### Acceptance Criteria

- Only members of the related group can view suggestions.
- Each suggestion displays its creator and details.
- Removed suggestions are not displayed as active suggestions.
- An empty state is displayed when the plan has no suggestions.

### Edit a Suggestion

As the suggestion creator, I want to edit my suggestion before voting begins.

#### Acceptance Criteria

- Only the suggestion creator can edit the suggestion.
- The plan must still be active.
- The updated information must pass validation.
- Suggestions cannot be edited after they are included in an open poll.

### Delete a Suggestion

As the suggestion creator, I want to remove my suggestion before voting begins.

#### Acceptance Criteria

- The suggestion creator or group owner can remove the suggestion.
- The plan must still be active.
- Suggestions included in an open poll cannot be removed.
- Removed suggestions no longer appear as active suggestions.

---

## Epic 4: Polls and Voting

### Create a Poll

As a plan creator or group owner, I want to create a poll so that members can vote on suggestions.

#### Acceptance Criteria

- Only the plan creator or group owner can create a poll.
- The plan must be active.
- The poll must contain at least two options.
- Poll options must belong to the same plan.
- Only one active poll is allowed per plan in the MVP.
- An optional deadline may be provided.
- The deadline must be in the future.
- Poll options cannot be changed after voting begins.

### View a Poll

As a group member, I want to view the active poll so that I can choose an option.

#### Acceptance Criteria

- Only members of the related group can view the poll.
- The poll displays its question, options, status, and deadline.
- The user's current vote is identified when one exists.
- Unauthorized users are denied access.

### Submit a Vote

As a group member, I want to vote for my preferred option.

#### Acceptance Criteria

- Only active group members can vote.
- The poll must be open.
- The selected option must belong to the poll.
- A user can have only one active vote per poll.
- Submitting a vote records the selected option.
- Voting is rejected after the deadline or after the poll closes.

### Change a Vote

As a group member, I want to change my vote before the poll closes.

#### Acceptance Criteria

- The poll must still be open.
- The deadline must not have passed.
- The user's existing vote is updated.
- A second vote record is not created.
- The updated results reflect the changed vote.

### View Poll Results

As a group member, I want to view poll results so that I can understand the group's preference.

#### Acceptance Criteria

- Only members of the related group can view results.
- Results display vote counts for each option.
- Results display percentages for each option.
- The total number of votes is shown.
- The percentage calculation handles a poll with zero votes.

### Close a Poll

As a plan creator or group owner, I want to close a poll so that voting ends.

#### Acceptance Criteria

- Only the plan creator or group owner can close the poll.
- The poll must currently be open.
- The poll status changes to closed.
- The closing time is recorded.
- Closed polls reject new or changed votes.
- Closed polls cannot be reopened in the MVP.

### Finalize a Decision

As a group owner, I want to finalize an option so that everyone knows the confirmed plan.

#### Acceptance Criteria

- Only the group owner can finalize a decision.
- The selected option must belong to the plan's poll.
- The selected option is clearly displayed.
- The plan status changes to finalized.
- No additional voting or suggestions are accepted.
- The finalized plan remains available as history.

---

## Epic 5: Comments

### Add a Comment

As a group member, I want to comment on a plan so that I can participate in the discussion.

#### Acceptance Criteria

- Only active group members can comment.
- The plan must not be cancelled.
- The comment displays the author and creation time.
- Empty comments are rejected.
- Comments exceeding the maximum length are rejected.

### View Comments

As a group member, I want to view comments so that I can follow the discussion.

#### Acceptance Criteria

- Only members of the related group can view comments.
- Comments display the author's name and creation time.
- Comments are displayed in a consistent order.
- Deleted comments are not shown as active comments.

### Delete a Comment

As a user, I want to delete my own comment.

#### Acceptance Criteria

- Users can delete only their own comments.
- The group owner may remove inappropriate comments.
- Unauthorized deletion requests are rejected.
- Deleted comments no longer appear as active comments.

---

## Epic 6: User Feedback

### Submit Feedback

As a user, I want to submit feedback so that the Planora experience can be improved.

#### Acceptance Criteria

- The user can provide a rating between 1 and 5.
- The user can describe what they liked.
- The user can describe what they found confusing.
- The user can submit feature suggestions.
- Text fields follow maximum-length limits.
- Valid feedback is stored for later review.
- A success message is displayed after submission.

---

## Epic 7: AI Smart Planner

This epic is planned for after the MVP is stable.

### Generate Plan Suggestions

As a group member, I want AI-generated suggestions based on the group's budget, time, and preferences.

#### Acceptance Criteria

- AI suggestions are optional.
- Manual planning remains available.
- The AI response is returned in a structured format.
- Users can accept, reject, or edit suggestions.
- AI-generated suggestions are clearly identified.
- Manual planning continues to work when the AI service is unavailable.
- AI failures display a clear fallback message.
- Sensitive user information is not unnecessarily sent to the AI provider.