package mreram.feediranig.presenter;

import java.util.List;
import java.util.concurrent.Callable;

import mreram.feediranig.AppLoader;
import mreram.feediranig.BasePresenter;
import mreram.feediranig.CActivity;
import mreram.feediranig.db.AppDatabase;
import mreram.feediranig.db.Bookmark;
import mreram.feediranig.interfaces.NewsInteractor;
import mreram.feediranig.model.News;
import mreram.feediranig.network.FeedAPI;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static mreram.feediranig.AppLoader.getContext;

/**
 * Created by m.eram on 8/10/2017.
 */

public class NewsPresenter extends BasePresenter<NewsInteractor<List<News>>> {
    private final AppLoader appLoader;


    public NewsPresenter(NewsInteractor<List<News>> newsNewsInteractor) {
        super(newsNewsInteractor);
        appLoader = (AppLoader) ((CActivity) getContext()).getApplication();
    }



    private void requestGetNewsFromServer(final List<Bookmark> bookmarkList) {
        FeedAPI feedAPI = appLoader.getFeedApi();
        Observable<List<News>> observable = feedAPI.getNews();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<News>>() {
                    @Override
                    public void call(List<News> news) {
                        //finding bookmark item from news list
                        for (News item : news) {
                            for (Bookmark bookmark : bookmarkList)
                                if (item.id==bookmark.newsId)
                                    item.isBookmark=true;
                        }
                        //send data to ui
                        getTarget().setData(news);
                        getTarget().hideProgress();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        getTarget().setError(throwable);
                        getTarget().hideProgress();
                    }
                });
    }

    public void getNewsData() {
        (getTarget()).showProgress();
         Observable.fromCallable(new Callable<List<Bookmark>>() {
            @Override
            public List<Bookmark> call() throws Exception {
                //get bookmark from database
                return AppDatabase.get(getContext()).bookmarkDAO().getAll();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Bookmark>>() {
                    @Override
                    public void call(List<Bookmark> list) {
                        requestGetNewsFromServer(list);
                    }
                });
    }


    public void insertBookmark(final Bookmark bookmark) {
        Observable.create(new Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                AppDatabase.get(getContext()).bookmarkDAO().insertAll(bookmark);
            }
        }).subscribeOn(Schedulers.io()).subscribe();

    }

    public void deleteBookmarkWithId(final int id) {
        Observable.create(new Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                AppDatabase.get(getContext()).bookmarkDAO().delete(id);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}
