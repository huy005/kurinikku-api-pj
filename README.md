This API application is used for the management of a clinic with interacting between entities[User ->(Patient, Doctor, Admin)].

\*Prerequisites:  
pre1/ Run Sql file: resources/static/sql/clinicpj_db.sql   
pre2/ Prepare support tool like Postman or Swagger (optional)  
pre3/ Set your real email and password into the following:  
pre3.1/ application.properties   
pre3.2/ BusinessComponent class   
pre3.3/ JasperReportsImp class -> Session and Message
---
I/Authentication Functions

1/Sign-up:

\*Note:
Role: USER -> Unauthorized.  
roleId(1:DOCTOR, 2:PATIENT, 3:ADMIN) -> Authorized.
isActive(0:Locked, 1:Unlocked)

HTTP method: POST,
URL: http://localhost:8005/auth/users  
Input:
{
"userName":"Nguyen Van Minh",
"email":"nguyenvanminh@email.com",
"password":"123456",
"confirmingPassword":"123456",
"userBirthDay":"19920725",
"userGender":"Male",
"userDescription":"Nice Person",
"userAddress":"855 nvm",
"userPhoneNumber":"090013013",
"isActive":1,
"role":{
"roleId":2,
"roleName":"PATIENT"
}
}  
Output:
{
"status": 200,
"responseContent": "Your account registered successfully!",
"timeStamp": 1714812234885
}
---
2/Login:
\*Note:
Role: PATIENT, DOCTOR, ADMIN  
Works with JWT(Token)  
A real email will be used for EmailConfirmation function

HTTP method: POST,
URL:http://localhost:8005/auth/session  
Input:
{
"email":"your real email",
"password":"123456"
}  
Output:
{
"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuZ3V5ZW52YW5taW5oQGVtYWlsLmNvbSIsImlhdCI6MTcxNDgxMjQ3OCwiZXhwIjoxNzE0ODE2MDc4fQ.hSKGbvJdKC8Q7xbOfx6VjaNuJgcTBUYVpLjIVuuWpJA"
}
---
3.1/EmailConfirmation
\*Note:
Role: PATIENT, DOCTOR, ADMIN  
Works with JWT(Token)  
A real email will be used for EmailConfirmation function  
This function just sends a URL with a token to your real email and associated to PasswordReset function below

HTTP method: POST  
Input: URL: http://localhost:8005/auth/users/emailConfirmation?email=yourRealEmail  
Output:
{
"status": 200,
"responseContent": "Sent to Email successfully!",
"timeStamp": 1714813200108
}
---
3.2/PasswordReset
*Note: Role: PATIENT, DOCTOR, ADMIN  
Works with JWT(Token)  
Need to get the token from email of EmailConfirmation function

HTTP method: POST  
URL:http://localhost:8005/auth/session  
Input:
{
"token":"The token that sent to email from the previous function",
"password":"123456",
"confirmingPassword":"123456"
}  
Output:
{
"status": 200,
"responseContent": "Password was updated successfully!",
"timeStamp": 1714813841997
}
---
II/Patient’s Functions

*Note: Role PATIENT

4/ Outstanding Specializations:

\*Note:
Shows the Outstanding Specializations of Clinics  
Works with JWT(Token)

HTTP method: GET  
URL: http://localhost:8005/patients/specializations/top  
Input:
{
"token":"The token that sent to email from the previous function",
"password":"123456",
"confirmingPassword":"123456"
}  
Output:
{
"status": 200,
"responseContent": [
{
"specializationId": 1,
"specializationName": "Cardiology",
"specializationDescription": "Positive",
"total": 1
},
{
"specializationId": 2,
"specializationName": "Ophthalmology",
"specializationDescription": "More Positive",
"total": 1
},
{
"specializationId": 3,
"specializationName": "Dermatology",
"specializationDescription": "More And More Positive",
"total": 1
},
{
"specializationId": 4,
"specializationName": "Respiratory",
"specializationDescription": "Positive",
"total": 1
},
{
"specializationId": 5,
"specializationName": "Neurosurgery",
"specializationDescription": "More Positive",
"total": 1
}
],
"timeStamp": 1714814587811
}
---
5/ Outstanding Clinics:

\*Note:
Shows the Outstanding Clinics  
Works with JWT(Token)

HTTP method: GET  
URL: http://localhost:8005/patients/clinics/top  
Input:
{
"token":"The token that sent to email from the previous function",
"password":"123456",
"confirmingPassword":"123456"
}  
Output:
{
"status": 200,
"responseContent": [
{
"clinicId": 1,
"clinicName": "Clinic-BV001",
"clinicAddress": "001001",
"clinicDescription": "BV001 is the best",
"total": 1
},
{
"clinicId": 2,
"clinicName": "Clinic-BV002",
"clinicAddress": "002002",
"clinicDescription": "BV002 is the best",
"total": 1
},
{
"clinicId": 3,
"clinicName": "Clinic-BV001",
"clinicAddress": "001002",
"clinicDescription": "BV001 is the best",
"total": 1
},
{
"clinicId": 4,
"clinicName": "Clinic-BV003",
"clinicAddress": "003001",
"clinicDescription": "BV003 is the best",
"total": 1
},
{
"clinicId": 5,
"clinicName": "Clinic-BV004",
"clinicAddress": "004001",
"clinicDescription": "BV004 is the best",
"total": 1
}
],
"timeStamp": 1714817266542
}
---
6/Personal Info

\*Note:
Gets the present user’s personal information  
Works with JWT(Token)

HTTP method: GET  
URL: http://localhost:8005/patients/personal-info  
Input:
{
"token":"The token that sent to email from the previous function",
"password":"123456",
"confirmingPassword":"123456"
}  
Output:
{
"status": 200,
"responseContent": {
"userDetailsDto": {
"userName": "Nguyen Van Minh",
"email": "nguyenvanminh@funix.edu.vn",
"userBirthDay": "19920725",
"userGender": "Male",
"userAddress": "855 nvm",
"userPhoneNumber": "090013013",
"userDescription": "Nice Person"
},
"scheduleDetailsDtos": []
},
"timeStamp": 1714817409713
}
---
7/Searching By KeyWords
\*Note:
Searchs the information from input with the specified keywords  
Works with JWT(Token)

HTTP method: GET  
URL: http://localhost:8005/patients/clinic-information  
Input:
{
"clinicName":"Clinic-BV001",
"clinicAddress":"004001",
"clinicDescription":"BV003 is the best",
"clinicCost":"2000k",
"specializationName":"Cardiology"
}  
Output:
{
"status": 200,
"responseContent": [
{
"clinicName": "Clinic-BV001",
"clinicAddress": "001001",
"clinicDescription": "BV001 is the best",
"clinicCost": "200k",
"specializationName": "Cardiology"
},
{
"clinicName": "Clinic-BV002",
"clinicAddress": "002002",
"clinicDescription": "BV002 is the best",
"clinicCost": "2000k",
"specializationName": "Ophthalmology"
},
{
"clinicName": "Clinic-BV001",
"clinicAddress": "001002",
"clinicDescription": "BV001 is the best",
"clinicCost": "200k",
"specializationName": "Dermatology"
},
{
"clinicName": "Clinic-BV003",
"clinicAddress": "003001",
"clinicDescription": "BV003 is the best",
"clinicCost": "300k",
"specializationName": "Respiratory"
},
{
"clinicName": "Clinic-BV004",
"clinicAddress": "004001",
"clinicDescription": "BV004 is the best",
"clinicCost": "400k",
"specializationName": "Neurosurgery"
},
{
"clinicName": "Clinic-BV005",
"clinicAddress": "005001",
"clinicDescription": "BV005 is the best",
"clinicCost": "2000k",
"specializationName": "Gastroenterology"
},
{
"clinicName": "Clinic-BV006",
"clinicAddress": "006001",
"clinicDescription": "BV006 is the best",
"clinicCost": "2000k",
"specializationName": "Endoscopy"
}
],
"timeStamp": 1714817595457
}
---
8/Searching by Doctor’s Specialization

\*Note:
Searchs the information of the specialization with the corresponding doctor’s Id  
Works with JWT(Token)

HTTP method: GET  
Input: URL: http://localhost:8005/patients/doctor-information?id=1  
Output:
{
"status": 200,
"responseContent": [
{
"userName": "Tran Van Minh",
"email": "tranvanminh@email.com",
"userAddress": "123 tvm",
"userPhoneNumber": "090001001",
"specializationName": "Cardiology",
"specializationDescription": "Positive"
}
],
"timeStamp": 1714817808754
}
---
9/Making a schedule:

\*Note:
Make a schedule with the specified doctor  
Works with JWT(Token)

HTTP method: GET  
URL: http://localhost:8005/patients/new-schedule  
Input:
{
"userId":1,
"scheduleDate":"20240108",
"scheduleTime":"15h19'"
}  
Output:
{
"status": 200,
"responseContent": "A new schedule updated successfully!",
"timeStamp": 1714818202942
}
---
III/Doctor’s Functions

\*Note: Role DOCTOR


10/ Patient List:

\*Note:
Gets the patient list with the corresponding doctor’s Id  
Works with JWT(Token)  
status:(1,"Waiting"), (2,"Processing"), (3,"Ending")

HTTP method: GET  
Input: URL: http://localhost:8005/doctors/user-information  
Output:
{
"status": 200,
"responseContent": [
{
"userName": "Tran Minh Tuan",
"email": "tranminhtuan@email.com",
"userBirthDay": "19960918",
"userGender": "Male",
"userAddress": "4454 tmt",
"historyBreath": 1,
"moreInfo": "Not Bad"
},
{
"userName": "Nguyen Nam Thu",
"email": "nguyennamthu@email.com",
"userBirthDay": "19960918",
"userGender": "Female",
"userAddress": "3534 nnt",
"historyBreath": 0,
"moreInfo": "Little Bad"
},
{
"userName": "Tran Minh Tuan",
"email": "tranminhtuan@email.com",
"userBirthDay": "19960918",
"userGender": "Male",
"userAddress": "4454 tmt",
"historyBreath": 1,
"moreInfo": "Quite Good"
},
{
"userName": "Nguyen Nam Thu",
"email": "nguyennamthu@email.com",
"userBirthDay": "19960918",
"userGender": "Female",
"userAddress": "3534 nnt",
"historyBreath": 1,
"moreInfo": "Really Good"
}
],
"timeStamp": 1714818814995
}
---
11/Schedule Acceptance Or Cancellation:

\*Note:
Accepts or cancels the schedule with the corresponding doctor   
Works with JWT(Token)  
isReservedOrCancellation: 1 update(accept), 0 delete(cancel)

HTTP method: PUT  
URL: http://localhost:8005/doctors/new-info-schedule  
Input:
{
"isReservedOrCancellation":1,
"scheduleId":"6"
}  
Output:
{
"status": 200,
"responseContent": "Data updated successfully!",
"timeStamp": 1714819295705
}
or  
Input2:
{
"isReservedOrCancellation":0,
"scheduleId":"6"
}  
Output2:
{
"status": 200,
"responseContent": "Data deleted successfully!",
"timeStamp": 1714819380275
}
---
12/Sending to Email

\*Note:
Sends Patient’s Info to Patient’s email by PDF file  
Sends Patient’s Id  
A PDF file will be sent to email as a patient extra info report  
Works with JWT(Token)

HTTP method: PUT  
Input: URL: http://localhost:8005/doctors/jasper/patient-extra-info?id=8  
Output:
{
"status": 200,
"responseContent": "PDF file sent to the specified email.",
"timeStamp": 1714819590835
}
---
IV/Admin’s Functions

\*Note: Role ADMIN

13/Locked or Unlocked Users:

\*Note: Accepts or cancels the schedule with the corresponding doctor   
Works with JWT(Token)  
isActive(0:Locked, 1:Unlocked)

HTTP method: PUT  
URL: http://localhost:8005/admins/status-activation  
Input1:
{
"email":"lucngocthu@email.com",
"isActive":0,
"reason": "You violated the rules."
}  
Output1:
{
"status": 200,
"responseContent": "This user is locked with the following reason: You violated the rules.",
"timeStamp": 1714823389692
}
or
Input2:
{
"email":"lucngocthu@email.com",
"isActive":1
}
Output2:
{
"status": 200,
"responseContent": "This user is unlocked",
"timeStamp": 1714823930628
}
---
14/ Doctor’s Additional Info

\*Note: Adds a new doctor with the additional fields  
Works with JWT(Token)  
roleId: 1->DOCTOR  
isActive(0:Locked, 1:Unlocked)

HTTP method: PUT  
URL: http://localhost:8005/admins/status-activation  
Input:
{
"userName":"Hoang Thi Mai",
"email":"hoangthimai@email.com",
"password":"123456",
"confirmingPassword":"123456",
"userBirthDay":"19901101",
"userGender":"Female",
"userAddress":"HTMMMMMM0009998888",
"userPhoneNumber":"0922883399",
"userDescription":"Pretty",
"role":{
"roleId":1
},
"isActive":1,
"generalIntro":"World-class Doctor",
"trainingProcessing":"World-class tranning course",
"achievement":"World-class Gold Badge",
"department":"one of the most World-class departments "
}  
Output:
{
"status": 200,
"responseContent": "A new user registered.",
"timeStamp": 1714831461202
}
---
15/Patient’s Details

\*Note:
Shows patient’s details with specified patient’s id  
Works with JWT(Token)

HTTP method: PUT  
Input: URL: http://localhost:8005/admins/patient-schedule-extraInfo-details?id=8  
Output:
{
"status": 200,
"responseContent": {
"patientDetailsDto": {
"userName": "Tran Minh Tuan",
"userBirthDay": "19960918",
"userGender": "Male",
"email": "tranminhtuan@email.com",
"userAddress": "4454 tmt"
},
"scheduleDetailsDtoList": [
{
"scheduleDate": "20240103",
"scheduleTime": "18h"
},
{
"scheduleDate": "202404",
"scheduleTime": "13h"
},
{
"scheduleDate": "20240208",
"scheduleTime": "16h30"
}
],
"extraInfoRepository": [
{
"historyBreath": 1,
"moreInfo": "Not Bad",
"createdAt": "2024-01-02T09:00:00"
},
{
"historyBreath": 1,
"moreInfo": "Quite Good",
"createdAt": "2024-01-05T09:00:00"
},
{
"historyBreath": 1,
"moreInfo": "Good",
"createdAt": "2024-02-06T09:00:00"
},
{
"historyBreath": 1,
"moreInfo": "As Usual",
"createdAt": "2024-02-11T09:00:00"
},
{
"historyBreath": 0,
"moreInfo": "Awfully Bad",
"createdAt": "2024-02-26T09:00:00"
}
]
},
"timeStamp": 1714831883350
}
---
16/Doctor’s Details

\*Note:
Shows doctor’s details with specified doctor’s id  
Works with JWT(Token)

HTTP method: PUT  
Input: URL: http://localhost:8005/admins/doctor-schedule-details?id=2  
Output:
{
"doctorDetailsDto": {
"userName": "Ngo Thi Nam",
"userBirthDay": "19910210",
"userGender": "Female",
"email": "ngothinam@email.com",
"userAddress": "234 ntn"
},
"scheduleDetailsDtoList": [
{
"scheduleDate": "20240128",
"scheduleTime": "10h30"
},
{
"scheduleDate": "20240103",
"scheduleTime": "18h"
},
{
"scheduleDate": "20240218",
"scheduleTime": "08h"
}
]
}
