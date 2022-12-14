// Generated by view binder compiler. Do not edit!
package com.example.penguinstudy.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.penguinstudy.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class TextRowItemBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final View pallete;

  @NonNull
  public final ImageButton tagSetting;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView2;

  private TextRowItemBinding(@NonNull FrameLayout rootView, @NonNull View pallete,
      @NonNull ImageButton tagSetting, @NonNull TextView textView, @NonNull TextView textView2) {
    this.rootView = rootView;
    this.pallete = pallete;
    this.tagSetting = tagSetting;
    this.textView = textView;
    this.textView2 = textView2;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static TextRowItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TextRowItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.text_row_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TextRowItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.pallete;
      View pallete = ViewBindings.findChildViewById(rootView, id);
      if (pallete == null) {
        break missingId;
      }

      id = R.id.tag_setting;
      ImageButton tagSetting = ViewBindings.findChildViewById(rootView, id);
      if (tagSetting == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      return new TextRowItemBinding((FrameLayout) rootView, pallete, tagSetting, textView,
          textView2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
