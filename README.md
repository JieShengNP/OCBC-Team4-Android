# OCBC_TeamOwl

OCBC Apply done by Marcus Yeong Mun Hong (S10208495), Tan Jie Sheng (S10205049B), Cho Wei Lun Herman (S10206327K), Jarrell Soh Jit Kai (S10205047D) and Teo Wei Jie (S10208226H)
# Background
At OCBC Bank, online bank account
opening services for individual accounts are available, and we hope to expand this capability
to allow joint account opening.
However, making the service available for joint accounts is more complicated than individual
accounts. For example, how would the system verify both account holders during an
application and that it isn't just one person impersonating two people? When opening a joint
account in person, both account holders must be present so that the bank officer can verify
both identities simultaneously.

## Our Solution
OCBC Apply is created for working adults who wish to open a joint account with their close ones. It is a mobile application service that allow them to create their joint account online while keeping it safe and seamless. OCBC Apply is developed to be secure and allow user to be able to make a joint account anywhere and anytime!

## Logic flow
- 1st account holder will proceed on without application code <br/>
- Fill in the form <br/>
- After 1st account holder done filling their personal details <br/>
- System will generate an application code that 2nd account holder can use to join and fill in their personal details <br/>
- After 2nd account holder done filling their personal details <br/>
- System will notify 1st account holder to proceed on the review and confirmation of joint account creation <br/>

# Some of our features
## Application Room (Safe)
Application Form Page 1 (1st User)
:-------------------------:
 ![Screenshot 2021-11-19 195349](https://user-images.githubusercontent.com/74399276/142620578-b6e515ad-488b-4942-b80d-cef86c92a318.png)
 Application Form Page 2 (1st User) 
![Screenshot 2021-11-19 200749](https://user-images.githubusercontent.com/74399276/142620594-460a2718-ae6d-4380-aa9c-449bdd4d41bb.png)

This is where the 1st account holder will fill in all their personal details for the joint account application, when they proceed to the second page, an application code will be shown. This will be the code that allows the 2nd account holder to come in the application room and enter their own details. The 1st account holder can also choose to send the code to the other person via email. Then, while waiting for the 2nd account holder to fill in all their details, the first person can actually leave the application. When the 2nd account holder is done with their part, the system will notify the 1st account holder to proceed on to the review and confirmation.

Application Form Page 2 (2nd User)
:-------------------------:
![Screenshot 2021-11-19 200148](https://user-images.githubusercontent.com/74399276/142623013-89eb11da-c3e8-4c42-a7bb-ec55d9335a34.png)
 Review & Confirmation Page
 ![Screenshot 2021-11-19 200207](https://user-images.githubusercontent.com/74399276/142623028-1c2788e9-0702-43ec-b3a8-44d2f7ac0b2a.png)

 ## Many auto complete features (Seamless)
Able to apply using SingPass myInfo
:-------------------------:
![Screenshot 2021-11-19 194031](https://user-images.githubusercontent.com/74399276/142623048-0d8ba6d1-568c-4f62-a913-1c37e0be55d7.png)
Autocomplete text fields to help fill in all the details faster
![Screenshot 2021-11-19 194228](https://user-images.githubusercontent.com/74399276/142623384-0a675894-de9e-447b-8e17-0f7b7d591f01.png)
![Screenshot 2021-11-19 194316](https://user-images.githubusercontent.com/74399276/142623069-c477b9f3-9b6d-4ab4-9e78-01220dc64206.png)
![Screenshot 2021-11-19 194148](https://user-images.githubusercontent.com/74399276/142623076-5e3fcb68-2ce7-4d6b-bb4b-ea72e5f2511b.png)

Having all these autocomplete features can help to boost the user experience, while also making the filling form process faster and seamless.

# Contributions
Wei Jie
- Meeting minutes: Take and structure notes for discussions
- Presentation: Slides and Presenting
- Created basic views for some Layouts
- Created Trello Board

Jie Sheng
- Implemented Log-in function for users to view account information (Call from Firebase)
- Completed View for User dashboard and Accounts page
- Implemented dropdown lists for application form
- Completed Comfirmation Page
- Clean up of code (Bug fixes and views touch up)

Jarrell
- Implemented validation for application form
- Implemented Auto-fill for Singpass users, and for existing users (Call from firebase)
- Implemented Auto-filling of address using only postal code (Call API)
- Implemented DatePicker for Date-Of-Birth

Marcus 
- Created Wireframe for majority of the views' UI
- Implemented application Room feature (Generate and Validate application code)
- Implemented email functionality (send to User 2)
- Implemented DeepLink (link straight to application form)
- Managed Trello Board
- Created Inception Deck 

Herman
- Implemented sign up function (Create Account and Write to Firebase)
- Implemented integration of Camera App(Upload photo for verification)
- Implemented Firebase storage to store photos
- Implemented OCR (temporarily removed)
- Created Inception Deck

Everyone
- Did Research for Joint Account Creation
- Created Layouts for the app

# Language Used
1. Java

# Database Used
1. Firebase

# GitHub Link
- **[Github](https://github.com/JieShengNP/OCBC-Team4-Android)**

