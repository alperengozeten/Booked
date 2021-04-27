package com.example.booked;

import android.app.Application;
import android.net.Uri;

import com.example.booked.models.Book;
import com.example.booked.models.BookProfile;
import com.example.booked.models.Evaluation;
import com.example.booked.models.Post;
import com.example.booked.models.User;

public class Booked extends Application {

    private static User currentUser;
    private static Post currentPost;
    static BookProfile currentBookProfile; // = new BookProfile(currentBook);
    static Book currentBook;// = new Book("Example Book","picbook");
    private static User currentSeller;

    private static String examplePath;

    public static String getExamplePath() {
        return examplePath;
    }

    public static void setExamplePath(String examplePath) {
        Booked.examplePath = examplePath;
    }

    public Booked() {
    }

    public static  Book getCurrentBook() {
        return currentBook;
    }


    public static com.example.booked.models.BookProfile getCurrentBookProfile() {
        return currentBookProfile;
    }
    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Booked.currentUser = currentUser;
    }

    public static Post getCurrentPost() {
        return currentPost;
    }

    public static void setCurrentPost(Post currentPost) {
        Booked.currentPost = currentPost;
    }

    public static void setCurrentSeller(User currentSeller) {
        Booked.currentSeller = currentSeller;
    }

    public static User getCurrentSeller() {
        return currentSeller;
    }

    public static void setExample()
    {
        currentUser = new User("eren", "atmaziya6@gmail.com", "None", "05443332211", "Bilo University");

        currentBook = new Book(" book1","pic1");
        currentBookProfile = new BookProfile(currentBook);
        currentPost = new Post("Old book", "iyi durumda iş görür ", "Cs-115",70, "No pic", currentBook, currentUser);

        currentBookProfile.addEvalution(new Evaluation("coh iyi",3,new User("Alperen", "alperengozeten@gmail.com", "None", "05392472224", "Bilkent University")));
        currentBookProfile.addEvalution(new Evaluation("daha da iyi",4, currentUser));
        currentBookProfile.addPost(new Post("B book", "A In good state", "Cs-115",70,"No pic", currentBook,
                new User("berkay", "berkay@gmail.com", "None", "05443332211", "Bilk University")));

        currentBookProfile.addPost(new Post("A book", "iyidir", "Cs-115",40,"No pic", currentBook,
                new User("omer", "ömer@gmail.com", "None", "05443332211", "Bilke University")));

        currentBookProfile.addPost(new Post("C book", "iyidir", "Cs-115",50,"No pic", currentBook,
                new User("yarkın", "ömer@gmail.com", "None", "05443332211", "Bilke University")));
    }

}
