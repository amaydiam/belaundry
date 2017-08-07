package com.akabetech.belaundryondemand.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by akbar.pambudi on 8/3/2016.
 */
public class Animation {


    public static Animator showDelayedAnimation(final View view){
        if(Build.VERSION.SDK_INT>=21) {

            // get the center for the clipping circle
            int cx = view.getWidth() / 2;
            int cy = view.getHeight() / 2;

            // get the initial radius for the clipping circle
            float initialRadius = (float) Math.hypot(cx, cy);

            // create the animation (the final radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, initialRadius);

            anim.setDuration(1000);
            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.VISIBLE);
                }
            });
            return anim;
        }
        return null;
    }
}
