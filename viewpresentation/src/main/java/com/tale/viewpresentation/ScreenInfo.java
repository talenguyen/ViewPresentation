/**
 * ViewPresentation
 *
 * Created by Giang Nguyen on 12/6/15.
 * Copyright (c) 2015 Umbala. All rights reserved.
 */

package com.tale.viewpresentation;

import android.view.View;

public class ScreenInfo {
  public final View view;
  public final ViewAnimationExecutor enterAnimator;
  public final ViewAnimationExecutor exitAnimator;

  public ScreenInfo(View view, ViewAnimationExecutor enterAnimator,
      ViewAnimationExecutor exitAnimator) {
    this.view = view;
    this.enterAnimator = enterAnimator;
    this.exitAnimator = exitAnimator;
  }
}
