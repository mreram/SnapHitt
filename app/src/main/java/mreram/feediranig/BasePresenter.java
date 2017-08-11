package mreram.feediranig;
/**
 * Created by Reza on 26/08/2016.
 */
public class BasePresenter<T> {
    T t;
    public BasePresenter(T t){
        this.t =t;
    }

    public T getTarget(){
        return t;
    }

    public void onFinish(){};


}
