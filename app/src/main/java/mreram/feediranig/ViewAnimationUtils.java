package mreram.feediranig;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * Created by Reza on 10/08/2017.
 */

public class ViewAnimationUtils {

    //expand view animation
    public static void expand(final View v) {
//        TranslateAnimation anim = null;
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        v.requestLayout();

        v.measure(View.MeasureSpec.makeMeasureSpec(
                ((LinearLayout) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        final int targetHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) Math.ceil(targetHeight * interpolatedTime);

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

        final int targetHeight = 1;
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