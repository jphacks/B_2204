// Generated by view binder compiler. Do not edit!
package com.example.penguinstudy.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.penguinstudy.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRetagBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final Button btReColorDialog;

  @NonNull
  public final Button buttonBackTag;

  @NonNull
  public final Button buttonReturn;

  @NonNull
  public final EditText tagRename;

  private ActivityRetagBinding(@NonNull CoordinatorLayout rootView, @NonNull Button btReColorDialog,
      @NonNull Button buttonBackTag, @NonNull Button buttonReturn, @NonNull EditText tagRename) {
    this.rootView = rootView;
    this.btReColorDialog = btReColorDialog;
    this.buttonBackTag = buttonBackTag;
    this.buttonReturn = buttonReturn;
    this.tagRename = tagRename;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRetagBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRetagBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_retag, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRetagBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bt_re_color_dialog;
      Button btReColorDialog = ViewBindings.findChildViewById(rootView, id);
      if (btReColorDialog == null) {
        break missingId;
      }

      id = R.id.button_back_tag;
      Button buttonBackTag = ViewBindings.findChildViewById(rootView, id);
      if (buttonBackTag == null) {
        break missingId;
      }

      id = R.id.button_return;
      Button buttonReturn = ViewBindings.findChildViewById(rootView, id);
      if (buttonReturn == null) {
        break missingId;
      }

      id = R.id.tag_rename;
      EditText tagRename = ViewBindings.findChildViewById(rootView, id);
      if (tagRename == null) {
        break missingId;
      }

      return new ActivityRetagBinding((CoordinatorLayout) rootView, btReColorDialog, buttonBackTag,
          buttonReturn, tagRename);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}