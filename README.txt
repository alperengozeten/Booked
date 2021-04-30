# Booked

Group: G2G

Title: Booked

Description: A mobile trading application between university students 

Status: Working, but needs a few visual and systematic improvements

So Far: Firstly, the user enters the app using their "edu.tr" mails. We used Firebase Auth to send verification mails and check the credentials of the user. We also allowed the 
user to reset their passwords, sign up, and sign in. After logging in the user is in the Main Page We have implemented the Showroom where we list all the posts in our app and also we can make detailed searchs, filterings, and sortings in there. We also have user profiles (shows
detailed info about social media, contact and also allows the user to surf through many pages) and other user's profile. Also, the user now can pick an avatar using the "File Picker"
of Android. We also have My Posts page where the user can see all of their posts and also a wishlist where user can add the books they desire. We also implemented Book Profile 
that shows all the posts about a book and also evaluations of other people to it. Besides that, we have implemented the Post Page where detailed information about a post can be seen. As other improvements, we have Edit Profile, Add Post, and Edit Post pages. Lastly, we have implemented Settings page which allows the user various actions to perform. We've connected all pages with Firebase Firestore (for managing data) and Firebase Storage (for managing images). Now, the users can create a new post, edit their posts, view their own posts in My Posts, view the all posts in the showroom. Also, post pages, book profile pages are also implemented. Evaluations and all the posts can be seen about a book profile. The admin now can manage all the feedbacks, all the reported post and also can create a new book through the Admin Panel. My Profile and Other Users Profile are now also fully functional with social media dialog boxes. Other User's posts can be seen in their profile. Also, now the user can manage all the information about the profile in Edit Profile page. Settings are now also fully functional, including change password utility. Also, general visual improvement is done for all pages.

What's Left: Admin identification when entered to the app so that Admin Panel is only visible to them. XML changes for some pages. Delete option for the post. Notifications about some actions in the app.

Halil Alperen Gözeten: XML designs and Activity (Controller) implementations of Showroom, Main Activity, Profile, Other User's Profile, Wishlist, and My Posts pages. Also provided Recycler Views and adapters for Showroom, My Posts, Wishlist, and Other Users Profile Pages. Contributed to Model Classes also. Firebase Storage implementation for all app and also helped on Firebase Firestore with Safa to manage all the data. Created Wishlist, Admin Feedback Pages and connected these pages with database. Create Book page for Admin and its connection 

Berkay Akkuş: Berkay has implemented model classes with Safa, LoginActivity, SignUpActivity, EmailVerificationCheckActivity, ForgotPassword and ChangePassword controller classes, has integrated Firebase Authentication, has customized email templates.

Ömer Asım Doğan: XML classes of Login Page, Sign Up Page, Verification Pages. Finding icons and drawables for the app. Also making general management of all XML files of the application including activities, dialog boxes, recycler views etc.

Safa Eren Kuday:  Written down the Model Classes. XML designs and Activity (Controller) implementations of Book Profile, Edit Profile, Post Page, Admin Page pages. Also provided 
Recycler Views and adapters for Book Profile. Firebase Firestore connections for all pages including Book Profile Report Post , Evaluate Book, My Posts, make comments Wishlist, Admin's Reported Posts, Edit Profile and Edit Post, Add book and Book Profile with Alperen. Admin Panel and the Reported Post Page implementations and database connections. 

Hasan Yarkın Kurt: Implemented AddPost page, EditPost page, Settings page, ChangePassword page controller classes and their respective layouts.
