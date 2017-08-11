package mreram.feediranig.network;

import java.util.List;

import mreram.feediranig.model.News;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by m.eram on 8/10/2017.
 */

public interface FeedAPI {

    @GET("news")
    Observable<List<News>> getNews();
}
