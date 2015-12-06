/**
 * ViewPresentation
 *
 * Created by Giang Nguyen on 12/5/15.
 * Copyright (c) 2015 Umbala. All rights reserved.
 */

package com.tale.viewpresentation;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class ScreenPresenter {

  private static final String TAG = "ScreenPresenter";
  private Stack<ScreenInfo> screenViewStack = new Stack<>();
  private List<Runnable> commands;
  private Runnable pendingCommand;
  private ViewGroup rootView;
  private int initialChildCount;
  private boolean running;
  private AtomicInteger waitingQueue = new AtomicInteger();

  /**
   * Set the rootView which will be used to display content.
   * @param rootView The root view.
   */
  public void setRootView(ViewGroup rootView) {
    this.rootView = rootView;
    initialChildCount = rootView.getChildCount();
  }

  /**
   * The callback which should be call on {@link Activity##onDestroy()} or {@link Fragment##onDestroyView()}
   * to release current views in stack.
   */
  public void onDestroy() {
    screenViewStack.clear();
    screenViewStack = null;
    // Restore rootView state.
    restoreRootView();
    rootView = null; // Release view.
    pendingCommand = null;
    clearCommands(commands);
  }

  public void show(@LayoutRes int layoutId, ViewAnimationExecutor enterAnimator,
      ViewAnimationExecutor exitAnimator, ViewAnimationExecutor popEnterAnimator,
      ViewAnimationExecutor popExitAnimator, boolean addToBackStack) {
    if (running) {
      return;
    }
    final View view = LayoutInflater.from(rootView.getContext()).inflate(layoutId, rootView, false);
    show(view, enterAnimator, exitAnimator, popEnterAnimator, popExitAnimator, addToBackStack);
  }

  public void show(View view) {
    show(view, null, null, null, null, null);
  }

  public void show(View view, ViewAnimationExecutor enterAnimator,
      ViewAnimationExecutor exitAnimator, boolean addToBackStack) {
    show(view, enterAnimator, exitAnimator, null, null, addToBackStack);
  }

  public void show(View view, ViewAnimationExecutor enterAnimator,
      ViewAnimationExecutor exitAnimator, ViewAnimationExecutor popEnterAnimator,
      ViewAnimationExecutor popExitAnimator, boolean addToBackStack) {
    if (running) {
      return;
    }
    final ScreenInfo visibleScreen = addToBackStack ? peekScreen() : popScreen();
    show(view, visibleScreen == null ? null : visibleScreen.view, enterAnimator, exitAnimator,
        popEnterAnimator, popExitAnimator);
  }

  public boolean back() {
    if (running) {
      return true;
    }
    final ScreenInfo visibleScreen = popScreen();
    final ScreenInfo parentScreen = popScreen();
    if (visibleScreen == null || parentScreen == null) {
      // This visible screen is the first screen then does not handle.
      return false;
    }
    show(parentScreen.view, visibleScreen.view, visibleScreen.enterAnimator,
        visibleScreen.exitAnimator, parentScreen.enterAnimator, parentScreen.exitAnimator);
    return true;
  }

  private void show(View enterView, View exitView, ViewAnimationExecutor enterAnimator,
      ViewAnimationExecutor exitAnimator, ViewAnimationExecutor popEnterAnimator,
      ViewAnimationExecutor popExitAnimator) {
    if (running) {
      return;
    }
    if (exitView != null) {
      prepareExitCommand(exitAnimator, exitView);
    }
    prepareEnterCommand(enterAnimator, enterView);
    if (enterAnimator == null) {
      rootView.addView(enterView, 0);
    } else {
      rootView.addView(enterView);
    }
    pushScreen(new ScreenInfo(enterView, popEnterAnimator, popExitAnimator));
  }

  private void clearCommands(List<?> commands) {
    if (commands != null && commands.size() > 0) {
      commands.clear();
    }
  }

  private void prepareEnterCommand(ViewAnimationExecutor animator, final View view) {
    addCommand(new AnimationCommand(view, animator, new Runnable() {
      @Override public void run() {
        Log.d(TAG, "run Enter");
        executePendingCommands();
      }
    }));
    view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      @Override public boolean onPreDraw() {
        view.getViewTreeObserver().removeOnPreDrawListener(this);
        executeCommands();
        return true;
      }
    });
  }

  private void prepareExitCommand(ViewAnimationExecutor animator, final View visibleView) {
    if (visibleView != null) {
      setPendingCommand(new Runnable() {
        @Override public void run() {
          rootView.removeView(visibleView);
        }
      });
      // Add run exit command.
      addCommand(new AnimationCommand(visibleView, animator, new Runnable() {
        @Override public void run() {
          executePendingCommands();
        }
      }));
    }
  }

  private void pushScreen(ScreenInfo screen) {
    screenViewStack.push(screen);
    Log.d(TAG, "pushScreen StackSize: " + screenViewStack.size());
  }

  @Nullable private ScreenInfo popScreen() {
    return
        screenViewStack.isEmpty() ? null : screenViewStack.pop();
  }

  @Nullable private ScreenInfo peekScreen() {
    return
        screenViewStack.isEmpty() ? null : screenViewStack.peek();
  }

  /**
   * Restore root view state by remove all added views
   */
  private void restoreRootView() {
    if (initialChildCount > 0) {
      while (rootView.getChildCount() > initialChildCount) {
        rootView.removeViewAt(rootView.getChildCount() - 1);
      }
    }
  }

  private void addCommand(Runnable command) {
    if (commands == null) {
      commands = new ArrayList<>();
    }
    commands.add(command);
  }

  private void executeCommands() {
    if (commands != null && commands.size() > 0) {
      running = true;
      waitingQueue.set(commands.size());
      for (Runnable command : commands) {
        Log.d(TAG, "executeCommands");
        command.run();
      }
      commands.clear();
    }
  }

  private void setPendingCommand(Runnable command) {
    this.pendingCommand = command;
  }

  private void executePendingCommands() {
    if (waitingQueue.decrementAndGet() > 0) {
      return;
    }
    if (pendingCommand != null) {
      pendingCommand.run();
      pendingCommand = null;
    }
    running = false;
    Log.d(TAG, "executePendingCommands() called with: childCount " + rootView.getChildCount());
  }

}
