# Important Information

##      TRMS

* Reimbursements =< 1000
* Resets on Jan 1
* Coverage :
  * University - 80%
  * Seminars - 60%
  * Cert Prep - 75%
  * Cert - 100%
  * Tech Training - 90%
  * Other - 30%
* Available Reimbursement formula
  * AvailableReimbursement  = TotalReimbirsement(1000) - PendingReimbursement  - Awarded Reimbursement
  * If Projected Reimbursement > Available Reimbursement
    * Projected Reimbursement  = Available Reimbursement
* **Does Not Cover Course Materials**

## Tuition Reimbursement Form

* Must be completed  1 week before the event
* Requires:
  * Basic Employee Info
  * Date
  * Time
  * Location
  * Description
  * Cost
  * Grading Format
  * Type
* Optional
  * Event related attachment
    * pdf, png, jpeg, txt, doc, 
  * Attachment of approvals
    * msg
    * type of approval
  * Work time that will be missed
* Projected reimbursement should be a read only field
* Grading formats
  * pulled from a reference table
    * Perform a presentation to management after event's completion
  * Passing grade is needed for reimbursement
  * Employee must provide passing grade cut off for the course
  * choose to use a default passing grade if unknown
* Approval email
  * Approval steps will be skipped if given an approval email
  * cannot skip Benefits Coordinator Approval
* If course is < 2 weeks will be marked as urgent

## Direct Supervisor Approval

* Must provide approval for tuition approval
  * can request additional information before approval
* Denied approval
  * Supervisor must provide a reason
* If also the department head
  * DH approval is skipped
* If supervisor does not complete document in time
  * auto approved

## Department Head Approval

* Must provide approval for tuition approval
  * can request additional information before approval

* If DH does not complete document in time
  * auto approved

## Benefits Coordinator Approval

* Must provide approval for tuition approval
  * can request additional information before approval from
    1. Employee
    2. Direct Supervisor
    3. Department Head
* Not Skippable
* If BenCo does not complete document in time
  * Escalation email sent to BenCo Supervisor
* Allowed to award a larger amount than the available amount for the employee
  * BenCo must provide reason
  * reimbursement must be marked as exceeding available funds

## Grade/Presentation Upload

* Employee should attach either the grade or presentation upon completion of event
* After upload of a grade,
  * BenCo confirms that the grade is passing
* After upload of a presentation
  * Direct Supervisor must confirm: 
    1. Presentation was satisfactory
    2. Presented to the appropriate parties
* Upon Confirmation
  * Amount is awarded to the employee
* Only Interested parties should be able to access the grades/presentations
  * Includes:
    1. Requestor
    2. Approver

# User Stories

## Requestor

1. ~~AAU I am able to send the request to my supervisor~~
2. ~~AAU I am able to attach additional information to the form~~
3. ~~AAU I am only allowed to request my available amount~~

## DS

1. ~~AAU I am able to approve/deny the reimbursement request~~
2. ~~AAU I have to comment on the reason of denial~~
3. AAU I can request for more information from the requestor
4. ~~AAU I can to confirm that the requestor has presented to the appropriate parties~~

## DH

1. ~~AAU I am able to approve/deny the reimbursement request~~
2. ~~AAU I have to comment on the reason of denial~~

## BenCo

1. ~~AAU I am able to approve/deny the reimbursement request~~
2. ~~AAU I have to comment on the reason of denial~~
3. ~~AAU I am able to request for more information from all interested parties~~

# Objects

## Models/Beans

* User

  * Department
  * Position
  * User ID
  * User First Name
  * User Last Name
  * User Address
  * User State
  * User Zip
  * User Whacky 3 numbers idk
  * Available Reimbursement Amount
* Department Enum
* Form 

  * User ID

    * __CLUSTER KEY__

  * Coverage Type

    * __PARTITION KEY__

  * Date Submitted

    * __CLUSTER KEY__

  * Form Status

  * Form Comment

  * Grading Format

    1. Presentation

    2. Grade Percentage

  * Grade Status
* Event

  * Date
  * Time
  * Location
    * __PARTITION KEY__
  * Description
* FileSubmission

  * File Extension
    * __PARTITION KEY__
  * File Byte Array
* Log

  *  
* Notification
  * mngrID __PARTITION KEY__
    * UserID
  * Notification Message

## DAOs

* User Dao
* Form Dao
* Event Dao
* Log Dao

## Services

* User Services
* Form Services
* Event Services
* Log Services

## Controllers

* User Controller
* Form Controller

