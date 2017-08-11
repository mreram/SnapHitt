package mreram.feediranig.interfaces;

/**
 * Created by m.eram on 8/10/2017.
 */

public interface NewsInteractor<T> {
    public void showProgress();

    public void hideProgress();

    public void setData(T data);

    public void setError(Throwable throwable);

}