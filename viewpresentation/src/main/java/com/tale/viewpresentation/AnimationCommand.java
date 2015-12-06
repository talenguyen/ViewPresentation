package com.tale.viewpresentation;

import android.view.View;
import java.lang.ref.WeakReference;

public class AnimationCommand implements Runnable {

  private WeakReference<View> targetViewRef;
  private ViewAnimationExecutor animator;
  private WeakReference<Runnable> callbackRef;
  private Runnable callbackTask = new Runnable() {
    @Override public void run() {
      executeCallback();
    }
  };

  /**
   * Constructor to create an animation executor.
   *
   * @param targetView The target view which will run animation.
   * @param animator The animator.
   * @param callback The callback which will be called when animation is end.
   */
  public AnimationCommand(View targetView, ViewAnimationExecutor animator, Runnable callback) {
    this.targetViewRef = new WeakReference<>(targetView);
    this.animator = animator;
    this.callbackRef = new WeakReference<>(callback);
  }

  @Override public void run() {
    final View view = targetViewRef.get();
    if (view == null) {
      return;
    }

    if (animator == null) {
      // Execute callback in case there is no animation to run.
      executeCallback();
    } else {
      animator.setOnCompleted(callbackTask);
      animator.apply(view);
    }
  }

  private void executeCallback() {
    final Runnable callback = callbackRef.get();
    if (callback != null) {
      callback.run();
    }
  }
}