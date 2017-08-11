package mreram.feediranig;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Reza on 25/08/2016.
 */
public class CActivity extends AppCompatActivity {
    public int resId;
    Handler handler;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLoader.setContext(this);
        if (resId != 0) {
            setContentView(resId);
            ButterKnife.bind(this);
        }
        handler = new Handler();
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable ex) {
//
//                ex.printStackTrace();
//                System.exit(2);
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLoader.setContext(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


}
