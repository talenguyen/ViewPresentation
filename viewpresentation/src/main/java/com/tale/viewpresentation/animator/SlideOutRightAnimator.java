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

public class SlideOutRightAnimator extends RestoreableAnimatorExecutor {

  public SlideOutRightAnimator(long duration) {
    super(duration);
  }

  @NonNull @Override protected Animator createAnimator(View target) {
    final ObjectAnimator animator =
        ObjectAnimator.ofFloat(target, View.TRANSLATION_X, 0, target.getWidth());
    animator.setInterpolator(new AccelerateDecelerateInterpolator());
    return animator;
  }

  @Override protected void restore(View target) {
    target.setTranslationX(0f);
  }
}
