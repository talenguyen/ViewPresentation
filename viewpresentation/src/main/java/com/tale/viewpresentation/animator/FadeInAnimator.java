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

public class FadeInAnimator extends AnimatorExecutor {

  public FadeInAnimator(long duration) {
    super(duration);
  }

  @NonNull @Override protected Animator createAnimator(View target) {
    final ObjectAnimator animator =
        ObjectAnimator.ofFloat(target, View.ALPHA, 0f, 1f);
    animator.setInterpolator(new AccelerateDecelerateInterpolator());
    return animator;
  }
}
