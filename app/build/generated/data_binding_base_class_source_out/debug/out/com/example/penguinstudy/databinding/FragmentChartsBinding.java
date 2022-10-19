// Generated by view binder compiler. Do not edit!
package com.example.penguinstudy.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.penguinstudy.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentChartsBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final ToggleButton btToCharts;

  @NonNull
  public final ToggleButton btToRank;

  private FragmentChartsBinding(@NonNull CoordinatorLayout rootView,
      @NonNull ToggleButton btToCharts, @NonNull ToggleButton btToRank) {
    this.rootView = rootView;
    this.btToCharts = btToCharts;
    this.btToRank = btToRank;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentChartsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentChartsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_charts, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentChartsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bt_to_charts;
      ToggleButton btToCharts = ViewBindings.findChildViewById(rootView, id);
      if (btToCharts == null) {
        break missingId;
      }

      id = R.id.bt_to_rank;
      ToggleButton btToRank = ViewBindings.findChildViewById(rootView, id);
      if (btToRank == null) {
        break missingId;
      }

      return new FragmentChartsBinding((CoordinatorLayout) rootView, btToCharts, btToRank);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}