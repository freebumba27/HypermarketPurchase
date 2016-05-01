// Generated code from Butter Knife. Do not modify!
package com.example.hypermarketpurchase;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BranchActivity$$ViewBinder<T extends com.example.hypermarketpurchase.BranchActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427434, "field 'btnlogout' and method 'logout'");
    target.btnlogout = finder.castView(view, 2131427434, "field 'btnlogout'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.logout();
        }
      });
    view = finder.findRequiredView(source, 2131427435, "field 'branchList'");
    target.branchList = finder.castView(view, 2131427435, "field 'branchList'");
  }

  @Override public void unbind(T target) {
    target.btnlogout = null;
    target.branchList = null;
  }
}
