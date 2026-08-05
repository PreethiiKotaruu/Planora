# Planora UI and Navigation

## Purpose

This document defines the main screens in Planora, what each
screen contains, and how users move through the application.

---

## 1. Public Screens

### Landing Page

Purpose:

- Explain what Planora does
- Encourage users to create an account
- Allow existing users to log in

Main actions:

- Create Account
- Log In

---

### Registration Page

Purpose:

Allow a new user to create an account.

Fields:

- Display name
- Email
- Password
- Confirm password

Main action:

- Create Account

Navigation:

- Successful registration → Login Page or Dashboard
- Existing user → Login Page

---

### Login Page

Purpose:

Allow registered users to access Planora.

Fields:

- Email
- Password

Main action:

- Log In

Navigation:

- Successful login → Dashboard
- New user → Registration Page

---

### Invitation Preview Page

Purpose:

Allow a user to preview a group invitation before joining.

Displays:

- Group name
- Group description
- Group owner
- Number of members
- Invitation status

Actions:

- Register to Join
- Log In to Join
- Join Group

Navigation:

- Successful join → Group Details Page

---

## 2. Authenticated Screens

### Dashboard

Purpose:

Provide an overview of the user's groups and active plans.

Displays:

- Groups created by the user
- Groups joined by the user
- Recently active groups
- Active plans

Actions:

- Create Group
- Open Group
- View Profile
- Log Out

---

### User Profile Page

Purpose:

Allow users to view and manage basic account information.

Displays:

- Display name
- Email
- Account creation date

Actions:

- Edit display name
- Log out

---

## 3. Group Screens

### Create Group Page

Purpose:

Allow a user to create a private planning group.

Fields:

- Group name
- Group description

Main action:

- Create Group

Navigation:

- Successful creation → Group Details Page

---

### Group Details Page

Purpose:

Provide the main workspace for a group.

Displays:

- Group name
- Description
- Owner
- Members
- Active plans
- Finalized plans
- Cancelled plans

Actions:

- Create Plan
- Invite Members
- View Members
- Open Plan
- Open Group Settings
- Leave Group

---

### Group Members Page

Purpose:

Display the people who belong to the group.

Displays:

- Member name
- Role
- Join date

Owner actions:

- Remove Member

---

### Group Settings Page

Purpose:

Allow the group owner to manage the group.

Actions:

- Edit group name and description
- Generate invitation
- Revoke invitation
- Remove members
- Delete group

---

## 4. Planning Screens

### Create Plan Page

Purpose:

Allow a group member to create a new plan.

Fields:

- Plan title
- Description
- Optional date and time

Main action:

- Create Plan

Navigation:

- Successful creation → Plan Details Page

---

### Plan Details Page

Purpose:

Provide one place for members to organize and finalize a plan.

Displays:

- Plan title
- Description
- Date and time
- Creator
- Status
- Suggestions
- Active poll
- Comments
- Final decision
- Activity history

Actions:

- Add Suggestion
- Create Poll
- Vote
- Comment
- Edit Plan
- Cancel Plan
- Finalize Decision

---

### Add Suggestion Form

Purpose:

Allow group members to suggest an option.

Fields:

- Suggestion title
- Description
- Estimated cost
- Location

Main action:

- Add Suggestion

---

### Create Poll Page

Purpose:

Allow the plan creator or group owner to create a poll.

Fields:

- Poll question
- Selected suggestions
- Optional deadline

Main action:

- Start Poll

---

### Poll Results Page

Purpose:

Display voting progress and results.

Displays:

- Poll options
- Vote count
- Vote percentage
- Current leader
- Total votes
- Poll status

Actions:

- Change Vote
- Close Poll
- Finalize Decision

---

## 5. Main Navigation Flow

```text
Landing Page
    ↓
Register or Log In
    ↓
Dashboard
    ↓
Create Group or Open Group
    ↓
Group Details
    ↓
Create Plan or Open Plan
    ↓
Plan Details
    ↓
Add Suggestions
    ↓
Create Poll
    ↓
Vote
    ↓
View Results
    ↓
Finalize Decision