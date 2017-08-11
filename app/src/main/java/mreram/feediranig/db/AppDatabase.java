package mreram.feediranig.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.util.List;

/**
 * Created by Reza on 11/08/2017.
 */
@Database(entities = {Bookmark.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {


    private static final String DB_NAME = "news.db";
    private static volatile AppDatabase INSTANCE = null;

    //get instance database
    public synchronized static AppDatabase get(Context ctxt) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(ctxt.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .build();
        }

        return (INSTANCE);
    }


    public abstract BookmarkDao bookmarkDAO();

    @Dao
    public interface BookmarkDao {
        @Query("select * from bookmark")
        List<Bookmark> getAll();

        @Insert
        void insertAll(Bookmark... bookmark);

        @Delete
        void delete(Bookmark bookmark);

        @Query("delete from bookmark where news_id=:newsId")
        void delete(int newsId);



    }
}
