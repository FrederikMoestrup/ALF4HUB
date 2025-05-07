# Test Page

This page was created to test basic features.

## Features Implemented:

1. **Team List** (Liste med hold):
   - Shows a list of teams with their names and number of members
   - Clearly indicates which teams are full
   
2. **Application Button** (Knap for at anmode om deltagelse):
   - Each team has a button to request participation
   - Button is disabled if the team is full
   - Once applied, the button changes to show "Applied" status
   - Only one application can be made at a time
   
3. **Success Message**:
   - Shows a confirmation message when application is sent
   - Message disappears after 3 seconds

## How to Access

Visit `/test` in the application to view this test page.

## Usage Notes

- The data is mock data handled in local state
- In a production app, these actions would be connected to API calls 