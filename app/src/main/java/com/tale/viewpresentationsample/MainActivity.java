package com.tale.viewpresentationsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.tale.viewpresentation.ScreenPresenter;
import com.tale.viewpresentation.animator.SlideInBottomAnimator;
import com.tale.viewpresentation.animator.SlideInLeftAnimator;
import com.tale.viewpresentation.animator.SlideInRightAnimator;
import com.tale.viewpresentation.animator.SlideInTopAnimator;
import com.tale.viewpresentation.animator.SlideOutBottomAnimator;
import com.tale.viewpresentation.animator.SlideOutLeftAnimator;
import com.tale.viewpresentation.animator.SlideOutRightAnimator;
import com.tale.viewpresentation.animator.SlideOutTopAnimator;

public class MainActivity extends AppCompatActivity {

  private static final int ANIMATION_DURATION = 400;
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
    screenPresenter.show(screenView, null, new SlideOutTopAnimator(ANIMATION_DURATION),
        new SlideInTopAnimator(ANIMATION_DURATION), null, true);
  }

  public void showLeft(View view) {
    final ScreenView screenView = new ScreenView(this);
    screenView.setName("Left");
    screenView.setBackgroundColor(Color.GREEN);
    screenPresenter.show(screenView, new SlideInLeftAnimator(ANIMATION_DURATION), new SlideOutRightAnimator(ANIMATION_DURATION),
        new SlideInRightAnimator(ANIMATION_DURATION), new SlideOutLeftAnimator(ANIMATION_DURATION), true);
  }

  public void showRight(View view) {
    final ScreenView screenView = new ScreenView(this);
    screenView.setName("Right");
    screenView.setBackgroundColor(Color.YELLOW);
    screenPresenter.show(screenView, new SlideInRightAnimator(ANIMATION_DURATION), new SlideOutLeftAnimator(ANIMATION_DURATION),
        new SlideInLeftAnimator(ANIMATION_DURATION), new SlideOutRightAnimator(ANIMATION_DURATION), true);
  }

  public void showBottom(View view) {
    final ScreenView screenView = new ScreenView(this);
    screenView.setName("Bottom");
    screenView.setBackgroundColor(Color.MAGENTA);
    screenPresenter.show(screenView, new SlideInBottomAnimator(ANIMATION_DURATION), null,
        null, new SlideOutBottomAnimator(ANIMATION_DURATION), true);
  }

  @Override public void onBackPressed() {
    if (!screenPresenter.back()) {
      super.onBackPressed();
    }
  }
}
