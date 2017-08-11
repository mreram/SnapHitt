package mreram.feediranig.presenter;

import java.util.List;

import mreram.feediranig.BasePresenter;
import mreram.feediranig.db.AppDatabase;
import mreram.feediranig.db.Bookmark;
import mreram.feediranig.interfaces.NewsInteractor;
import rx.Observable;
import rx.schedulers.Schedulers;

import static mreram.feediranig.AppLoader.getContext;

/**
 * Created by Reza on 11/08/2017.
 */

public class BookmarkPresenter extends BasePresenter<NewsInteractor<List<Bookmark>>> {
    public BookmarkPresenter(NewsInteractor<List<Bookmark>> bookmarkNewsInteractor) {
        super(bookmarkNewsInteractor);
    }

    public void getBookmarkFromDBLocal() {
        Observable.create(new Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                getTarget().showProgress();
                getTarget().setData(AppDatabase.get(getContext()).bookmarkDAO().getAll());
                getTarget().hideProgress();
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}
