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

public class SlideOutTopAnimator extends RestoreableAnimatorExecutor {

  public SlideOutTopAnimator(long duration) {
    super(duration);
  }

  @Override protected void restore(View target) {
    target.setTranslationY(0);
  }

  @NonNull @Override protected Animator createAnimator(View target) {
    final ObjectAnimator animator =
        ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0, -target.getHeight());
    animator.setInterpolator(new AccelerateDecelerateInterpolator());
    return animator;
  }
}
