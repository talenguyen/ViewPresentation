/**
 * ViewPresentation
 *
 * Created by Giang Nguyen on 12/6/15.
 * Copyright (c) 2015 Umbala. All rights reserved.
 */

package com.tale.viewpresentation.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.tale.viewpresentation.ViewAnimationExecutor;

public class FadeInAnimator extends ViewAnimationExecutor {
  private long duration = 0;

  public FadeInAnimator() {
  }

  public FadeInAnimator(long duration) {
    this.duration = duration;
  }

  @Override public void apply(View target) {
    final ObjectAnimator animator =
        ObjectAnimator.ofFloat(target, View.ALPHA, 0f, 1f);
    animator.setInterpolator(new AccelerateDecelerateInterpolator());
    if (duration > 0) {
      animator.setDuration(duration);
    }
    animator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        animator.removeAllListeners();
        executeCallback();
      }
    });
    animator.start();
  }
}
