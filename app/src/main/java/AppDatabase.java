import android.content.Context;
/**
 * Name: Morgan Beebe
 * Date: 2025-12-06
 * Explanation: This is my class for the database
 */
public class AppDatabase {
    //DAO Getters
    public abstract UserDao userDao();
    public abstract CourseDao courseDao();
    public abstract FlashcardDao flashcardDao();

    //Single Instance
    public static AppDatabase getInstance(Context context){

    }
}
