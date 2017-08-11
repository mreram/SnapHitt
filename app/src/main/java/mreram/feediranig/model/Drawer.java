package mreram.feediranig.model;

import android.view.View;

/**
 * Created by m.eram on 8/10/2017.
 */

public class Drawer {
    public Type type;
    public View.OnClickListener onClickListener;
    public String name;

    public enum Type {
        header,item
    }

    public Drawer(Type type,View.OnClickListener onClickListener) {
        this.onClickListener=onClickListener;
    }

    public Drawer(Type type,String name,View.OnClickListener onClickListener) {
        this.onClickListener=onClickListener;
        this.name=name;
    }
}
