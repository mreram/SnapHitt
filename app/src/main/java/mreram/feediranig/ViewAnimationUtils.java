package mreram.feediranig;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Reza on 10/08/2017.
 */

public class ViewAnimationUtils {

    //expand view animation
    public static void expand(final View v) {
//        TranslateAnimation anim = null;


        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
//        if ( Build.VERSION.SDK_INT > 14) {
//            v.getLayoutParams().height = MyCustomAnimation2.mHeightSize;
//        } else {
//            v.getLayoutParams().height = 0;
//        }
//        if(Build.VERSION.SDK_INT>14) {
//            v.getLayoutParams().height = 0;
//        } else {
//            v.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        }
        v.getLayoutParams().height =50;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime) +50;

                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(200);

        v.startAnimation(a);
    }

    //collapse view animation
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(200);
        v.startAnimation(a);
    }


}