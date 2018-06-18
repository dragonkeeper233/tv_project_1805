package com.wytv.cc.mytvapp.View;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wytv.cc.mytvapp.Object.DengerObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;
import com.wytv.cc.mytvapp.activity.MyMainActivity;

import java.util.ArrayList;

public class HomeDatabaseItemLy extends RelativeLayout implements View.OnClickListener {
    public String time;
    public String last;
    private ArrayList<View> viewItemList = new ArrayList<View>();
    private ArrayList<DengerObject.DangerData> items;
    public MyMainActivity myMainActivity;
    public String id;


    public HomeDatabaseItemLy(Context context) {
        super(context);
        setMyStyle();
    }

    public HomeDatabaseItemLy(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setMyStyle();
    }

    public HomeDatabaseItemLy(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMyStyle();
    }

    public void setData(final ArrayList<DengerObject.DangerData> items) {
        this.items = items;
        if (items != null && items.size() != 0) {
            for (int j = 0; j < items.size(); j++) {
                addItem(items.get(j));
            }
            if (items.size() > 1)
                startMyAnimation(0);
        }
    }

    public void addItem(Object data) {
        if (data == null)
            return;
        DataBaseItemView dataBaseItemView = new DataBaseItemView();
        View view = dataBaseItemView.init(getContext());
        addView(view);
        if (viewItemList.size() > 0)
            view.setTranslationY(500);
        viewItemList.add(view);
        dataBaseItemView.setUI(data, time, last);
    }

    private void setMyStyle() {
        setFocusable(true);
        setOnClickListener(this);
        int padding = CommonUtils.dip2px(getContext(), 0);
        setPadding(padding, padding, padding, padding);
    }

    public void startMyAnimation(final int count) {
        final ArrayList<View> list = viewItemList;
        if (list == null && list.size() < count + 1)
            return;
        final View currrentView = list.get(count);
        final View nextView = count == list.size() - 1 ? list.get(0) : list.get(count + 1);

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimatorB = ObjectAnimator.ofFloat(currrentView,
                "translationY", 0, currrentView.getHeight() == 0 ? -500 : -currrentView.getHeight());
        objectAnimatorB.setDuration(500);
        ObjectAnimator objectAnimatorA = ObjectAnimator.ofFloat(nextView,
                "translationY", nextView.getHeight() == 0 ? 500 : nextView.getHeight(), 0);
        objectAnimatorA.setDuration(500);
        objectAnimatorA.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                int nextcount = count == list.size() - 1 ? 0 : count + 1;
                startMyAnimation(nextcount);
                currentshow = count;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet.Builder builder = animatorSet.play(objectAnimatorB).with(objectAnimatorA);
        for (int j = 0; j < list.size(); j++) {
            if (!list.get(j).equals(currrentView) && !list.get(j).equals(nextView)) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(list.get(j),
                        "translationY", list.get(j).getHeight() == 0 ? 500 : list.get(j).getHeight(),
                        list.get(j).getHeight() == 0 ? 500 : list.get(j).getHeight());
                objectAnimatorA.setDuration(500);
                builder.with(objectAnimator);
            }
        }
        animatorSet.setStartDelay(5000);
        animatorSet.start();

    }

    private int currentshow;

    public int getCurrentShow() {
        return currentshow;
    }

    @Override
    public void onClick(View v) {
        if (myMainActivity != null && items != null && items.size() > 0) {
            final ArrayList<DengerObject.DangerData> dangerDatas = this.items;
            if (currentshow < dangerDatas.size()) {
                DengerObject.DangerData dangerData = dangerDatas.get(currentshow);
                if (dangerData != null) {
                    myMainActivity.showMyDialog("file", dangerData.getType(), id);
                }
            }
        }
    }
}
