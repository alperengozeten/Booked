## Booked

### Group: G2G

### Title: Booked

Description: A mobile trading application for university students 

How to Run: One needs only Android Studio (we used version 4.1.3 which is the latest) to run our program. The suggested emulator device for our app is Pixel 4 XL API 30; you need to create a new
virtual device before running the app (this is just next to the run icon). We've uploaded our work with libraries (dependencies) included to make it possible to run directly and that made it a large program. If it doesn't run (probably won't happen), try File --> Sync Project with Gradle Files.
Also, our app only accepts e-mails with "edu.tr" domain. To run the program, click the green arrow at the top. When the emulator is currently
running, the run icon is changed. If you click these new run icons, the program will restart.

Code Organisation: Layout files are the design files of the pages and also other elements such as dialogs, recycler views etc.
                   We've put our model classes in a model package. Also, the adapters used in the recycler views are also in a package
                   called adapters. The other classes in the booked package are the activities and dialog box classes (only two of them).

Dependencies:      
* 'com.squareup.picasso:picasso:2.71828'  (Picasso Library)
* 'de.hdodenhof:circleimageview:3.1.0'    (CircleImageView Library)
* "androidx.recyclerview:recyclerview:1.2.0" (RecyclerView Library which is in the Android Studio)
* 'com.google.firebase:firebase-auth:20.0.4' (Firebase Authentication Library)
* 'com.google.firebase:firebase-database:19.7.0' (Firebase Database Library)
* 'com.google.firebase:firebase-storage:19.2.2'  (Firebase Storage Library)
* 'com.google.firebase:firebase-firestore:22.1.2' (Firebase Firestore Library)
* 'com.google.firebase:firebase-messaging:21.1.0' (Firebase Messaging Library)

So Far: Firstly, the user enters the app using their "edu.tr" mails. We used Firebase Auth to send verification emails and check the credentials of the user. We also allowed the 
user to reset their passwords, sign up, and sign in. After logging in, the user is on the Main Page. We have implemented the Showroom, where we list all the posts in our app, and also we can make detailed searches, filterings, and sortings in there. We also have user profiles (shows
detailed info about social media, contacts and also allow the user to surf through many pages) and other users' profiles. Also, the user can now pick an avatar using the "File Picker"
of Android. We also have a My Posts page where the user can see all of their posts and also a wishlist where users can add the books they desire. We also implemented a Book Profile 
that shows all the posts about a book and also evaluations of other people about it. Besides that, we have implemented the Post Page, where detailed information about a post can be seen. As for other improvements, we have Edit Profile, Add Post, and Edit Post pages.
Lastly, we have implemented a Settings page, which allows the user various actions to perform. We've connected all pages with Firebase Firestore (for managing data) and Firebase Storage (for managing images). Now, the users can create a new post, edit their posts, view their own posts in My Posts, view the all posts in the showroom. Also, post pages, book profile pages are also implemented.
Evaluations and all the posts can be seen about a book profile. The admin can now manage all the feedback and all the reported posts and can also create a new book through the Admin Panel. My Profile and Other Users Profile are now also fully functional with social media dialog boxes. Other User's posts can be seen in their profile. Also, now the user can manage all the information about the profile in Edit Profile page.
Settings are now also fully functional, including the change password utility. Also, general visual improvement is done for all pages.

Final Status: In our last few days, we managed to add the Messaging utility to allow more enhanced communication for the users of the app. Besides that, we also added notifications
for our app in the last few days so that user gets notified about certain actions in the app. We also made visual improvements in the app at the very last stage, and lastly, we've
tried all features of our app to ensure it's fully functional.

Halil Alperen Gözeten: Implementation and database connections of the Messaging pages with Safa. XML designs and Activity (Controller) implementations of Showroom, Main Activity, Profile, Other User's Profile, Wishlist, and My Posts pages. Also provided Recycler Views and adapters for Showroom, My Posts, Wishlist, and Other Users Profile Pages. Contributed to Model Classes also.
Firebase Storage implementation for all THE app and also helped on Firebase Firestore with Safa to manage all the data. Created Wishlist and Admin Feedback Pages and connected these pages with the database. Create a Book page for Admin and its connection with databases.

Berkay Akkuş: Berkay has implemented model classes with Safa, LoginActivity, SignUpActivity, EmailVerificationCheckActivity, ForgotPassword, and ChangePassword controller classes, has integrated Firebase Authentication, has customized email templates, has implemented notification channel and specified notification for activities.

Ömer Asım Doğan: XML classes of Login Page, Sign Up Page, Verification Pages. Finding icons and drawables for the app. Also, general management of all XML files of the application, including activities, dialog boxes, recycler views etc.

Safa Eren Kuday:  Activities: Book Profile page (including evaluations, comments, offers and offer filters); post page and report post dialog, edit profile page, admin panel (and its visibility), reported posts, myMessages, rcyclerview adapters of mymessage , comments, offers and reportedPost
                  Firebase Firestore: saving and retrieving of book, book Profile, user, post objects, and message rooms;
                  also implementation of model classes, comparators and global class

Hasan Yarkın Kurt: Implemented AddPost page, EditPost page, Settings page, ChangePassword page controller classes and their respective layouts. Also added comments to classes. Helped with visuals of the app and debugging
