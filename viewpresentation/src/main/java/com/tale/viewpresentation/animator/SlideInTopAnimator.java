/**
 * ViewPresentation
 *
 * Created by Giang Nguyen on 12/6/15.
 * Copyright (c) 2015 Umbala. All rights reserved.
 */

package com.tale.viewpresentation.animator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class SlideInTopAnimator extends AnimatorExecutor {

  public SlideInTopAnimator(long duration) {
    super(duration);
  }

  @NonNull @Override protected Animator createAnimator(View target) {
    final ObjectAnimator animator =
        ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, -target.getHeight(), 0);
    animator.setInterpolator(new AccelerateDecelerateInterpolator());
    return animator;
  }
}
