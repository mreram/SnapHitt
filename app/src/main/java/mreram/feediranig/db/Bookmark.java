package mreram.feediranig.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Reza on 11/08/2017.
 */

@Entity(indices = {})
public class Bookmark {
    public Bookmark(int newsId, String imgUrl, String title, String description,String time) {
        this.newsId = newsId;
        this.imgUrl = imgUrl;
        this.description = description;
        this.title = title;
        this.time = time;

    }

    @PrimaryKey
    @ColumnInfo(name = "news_id")
    public int newsId;
    @ColumnInfo(name = "img_url")
    public String imgUrl;
    public String title;
    public String time;
    public String description;
    @Ignore
    public boolean isBookmark=true;
}
