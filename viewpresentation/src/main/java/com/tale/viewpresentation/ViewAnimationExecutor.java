/**
 * ViewPresentation
 *
 * Created by Giang Nguyen on 12/6/15.
 * Copyright (c) 2015 Umbala. All rights reserved.
 */

package com.tale.viewpresentation;

import android.view.View;
import java.lang.ref.WeakReference;

public abstract class ViewAnimationExecutor {

  private WeakReference<Runnable> callbackRef;

  public abstract void apply(View target);

  public void setOnCompleted(Runnable callback) {
    callbackRef = new WeakReference<>(callback);
  }

  protected void executeCallback() {
    final Runnable callback = callbackRef == null ? null : callbackRef.get();
    if (callback != null) {
      callback.run();
    }
  }

}
