// Generated by view binder compiler. Do not edit!
package com.example.cardgame.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cardgame.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentGameBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final ImageButton buttonFeed;

  @NonNull
  public final ImageView penguin;

  @NonNull
  public final TextView textFeed;

  @NonNull
  public final TextView textGame;

  private FragmentGameBinding(@NonNull CoordinatorLayout rootView, @NonNull ImageButton buttonFeed,
      @NonNull ImageView penguin, @NonNull TextView textFeed, @NonNull TextView textGame) {
    this.rootView = rootView;
    this.buttonFeed = buttonFeed;
    this.penguin = penguin;
    this.textFeed = textFeed;
    this.textGame = textGame;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentGameBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentGameBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_game, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentGameBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_feed;
      ImageButton buttonFeed = ViewBindings.findChildViewById(rootView, id);
      if (buttonFeed == null) {
        break missingId;
      }

      id = R.id.penguin;
      ImageView penguin = ViewBindings.findChildViewById(rootView, id);
      if (penguin == null) {
        break missingId;
      }

      id = R.id.text_feed;
      TextView textFeed = ViewBindings.findChildViewById(rootView, id);
      if (textFeed == null) {
        break missingId;
      }

      id = R.id.text_game;
      TextView textGame = ViewBindings.findChildViewById(rootView, id);
      if (textGame == null) {
        break missingId;
      }

      return new FragmentGameBinding((CoordinatorLayout) rootView, buttonFeed, penguin, textFeed,
          textGame);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
