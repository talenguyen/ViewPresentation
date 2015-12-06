/**
 * ViewPresentation
 *
 * Created by Giang Nguyen on 12/6/15.
 * Copyright (c) 2015 Umbala. All rights reserved.
 */

package com.tale.viewpresentation.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.view.View;
import com.tale.viewpresentation.ViewAnimationExecutor;

public abstract class AnimatorExecutor extends ViewAnimationExecutor {

  public AnimatorExecutor(long duration) {
    super(duration);
  }

  @Override public void apply(View target) {
    final Animator animator = createAnimator(target);
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

  @NonNull protected abstract Animator createAnimator(View target);

}
