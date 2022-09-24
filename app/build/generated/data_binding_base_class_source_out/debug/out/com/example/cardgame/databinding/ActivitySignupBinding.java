// Generated by view binder compiler. Do not edit!
package com.example.cardgame.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cardgame.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignupBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button buttonSign;

  @NonNull
  public final EditText userConfilm;

  @NonNull
  public final EditText userName;

  @NonNull
  public final EditText userPass;

  private ActivitySignupBinding(@NonNull LinearLayout rootView, @NonNull Button buttonSign,
      @NonNull EditText userConfilm, @NonNull EditText userName, @NonNull EditText userPass) {
    this.rootView = rootView;
    this.buttonSign = buttonSign;
    this.userConfilm = userConfilm;
    this.userName = userName;
    this.userPass = userPass;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_signup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_sign;
      Button buttonSign = ViewBindings.findChildViewById(rootView, id);
      if (buttonSign == null) {
        break missingId;
      }

      id = R.id.user_confilm;
      EditText userConfilm = ViewBindings.findChildViewById(rootView, id);
      if (userConfilm == null) {
        break missingId;
      }

      id = R.id.user_name;
      EditText userName = ViewBindings.findChildViewById(rootView, id);
      if (userName == null) {
        break missingId;
      }

      id = R.id.user_pass;
      EditText userPass = ViewBindings.findChildViewById(rootView, id);
      if (userPass == null) {
        break missingId;
      }

      return new ActivitySignupBinding((LinearLayout) rootView, buttonSign, userConfilm, userName,
          userPass);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
