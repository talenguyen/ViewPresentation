/**
 * ViewPresentation
 *
 * Created by Giang Nguyen on 12/6/15.
 * Copyright (c) 2015 Umbala. All rights reserved.
 */

package com.tale.viewpresentationsample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ScreenView extends FrameLayout {

  private TextView tvName;
  private CharSequence name;

  public ScreenView(Context context) {
    this(context, null, 0);
  }

  public ScreenView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    inflate(context, R.layout.view_screen, this);
  }

  public void setName(CharSequence name) {
    this.name = name;
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    tvName = ((TextView) findViewById(R.id.tvName));
    tvName.setText(name);
  }

}
