package com.tale.viewpresentationsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.tale.viewpresentation.ScreenPresenter;
import com.tale.viewpresentation.animator.SlideInBottomAnimator;
import com.tale.viewpresentation.animator.SlideInLeftAnimator;
import com.tale.viewpresentation.animator.SlideInTopAnimator;
import com.tale.viewpresentation.animator.SlideOutBottomAnimator;
import com.tale.viewpresentation.animator.SlideOutRightAnimator;
import com.tale.viewpresentation.animator.SlideOutTopAnimator;

public class MainActivity extends AppCompatActivity {

  private ScreenPresenter screenPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    screenPresenter = new ScreenPresenter();
    screenPresenter.setRootView((ViewGroup) findViewById(R.id.contentView));
    final ScreenView screenView = new ScreenView(this);
    screenView.setName("Root");
    screenView.setBackgroundColor(Color.CYAN);
    screenPresenter.show(screenView);
  }

  @Override protected void onDestroy() {
    screenPresenter.onDestroy();
    screenPresenter = null;
    super.onDestroy();
  }

  public void showTop(View view) {
    final ScreenView screenView = new ScreenView(this);
    screenView.setName("Top");
    screenView.setBackgroundColor(Color.BLUE);
    screenPresenter.show(screenView, new SlideInTopAnimator(1000), new SlideOutBottomAnimator(1000),
        new SlideInBottomAnimator(1000), new SlideOutTopAnimator(1000), true);
  }

  public void showLeft(View view) {
    final ScreenView screenView = new ScreenView(this);
    screenView.setName("Left");
    screenView.setBackgroundColor(Color.GREEN);
    screenPresenter.show(screenView, new SlideInLeftAnimator(1000), new SlideOutRightAnimator(1000),
        null, null, true);
  }

  public void showRight(View view) {
  }

  public void showBottom(View view) {
  }

  @Override public void onBackPressed() {
    if (!screenPresenter.back()) {
      super.onBackPressed();
    }
  }
}
