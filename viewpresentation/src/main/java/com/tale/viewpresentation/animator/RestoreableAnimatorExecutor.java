/**
 * ViewPresentation
 *
 * Created by Giang Nguyen on 12/6/15.
 * Copyright (c) 2015 Umbala. All rights reserved.
 */

package com.tale.viewpresentation.animator;

import android.view.View;
import java.lang.ref.WeakReference;

public abstract class RestoreableAnimatorExecutor extends AnimatorExecutor {

  private WeakReference<View> targetRef;

  public RestoreableAnimatorExecutor(long duration) {
    super(duration);
  }

  @Override public void apply(final View target) {
    targetRef = new WeakReference<>(target);
    super.apply(target);
  }

  @Override protected void executeCallback() {
    super.executeCallback();
    final View target = targetRef.get();
    if (target != null) {
      restore(target);
    }
  }

  protected abstract void restore(View target);

}
