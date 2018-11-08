package com.example.wx.myapplication.view;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class UpAndDownSlidinglayout extends RelativeLayout implements View.OnTouchListener {
    /**
     * 滚动显示和隐藏上侧布局时，手指滑动需要达到的速度。
     */
    public static final int SNAP_VELOCITY = 200;

    /**
     * 滑动状态的一种，表示未进行任何滑动。
     */
    public static final int DO_NOTHING = 0;
    /**
     * 滑动状态的一种，表示正在滑出底部界面。
     */
    public static final int SHOW_UP_MENU = 1;

    /**
     * 滑动状态的一种，表示正在隐藏底部界面。
     */
    public static final int HIDE_UP_MENU = 3;
    /**
     * 滑动状态的一种，表示底部界面显示的情况下，继续向下滑动。
     */
    public static final int SHOW_UP_MENU2 = 4;

    /**
     * 记录当前的滑动状态
     */
    private int slideState;

    /**
     * 屏幕宽度值。
     */
    private int screenWidth;
    private int screenHeight;

    /**
     * 在被判定为滚动之前用户手指可以移动的最大值。
     */
    private int touchSlop;

    /**
     * 记录手指按下时的横坐标。
     */
    private float xDown;

    /**
     * 记录手指按下时的纵坐标。
     */
    private float yDown;

    /**
     * 记录手指移动时的横坐标。
     */
    private float xMove;

    /**
     * 记录手指移动时的纵坐标。
     */
    private float yMove;

    /**
     * 记录手机抬起时的纵坐标。
     */
    private float yUp;
    /**
     * 底部界面当前是显示还是隐藏。只有完全显示或隐藏时才会更改此值，滑动过程中此值无效。
     */
    private boolean isUpMenuVisible;

    /**
     * 下侧菜单当前是显示还是隐藏。只有完全显示或隐藏时才会更改此值，滑动过程中此值无效。
     */
    private boolean isDownMenuVisible;

    /**
     * 是否正在滑动。
     */
    private boolean isSliding;

    /**
     * 底部界面布局对象。
     */
    private View upMenuLayout;

    /**
     * 内容布局对象。
     */
    private View contentLayout;

    /**
     * 用于监听滑动事件的View。
     */
    private View mBindView;

    /**
     * 底部界面布局的参数。
     */
    private MarginLayoutParams upMenuLayoutParams;

    /**
     * 内容布局的参数。
     */
    private RelativeLayout.LayoutParams contentLayoutParams;

    /**
     * 用于计算手指滑动的速度。
     */
    private VelocityTracker mVelocityTracker;

    public UpAndDownSlidinglayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 绑定监听滑动事件的View。
     *
     * @param bindView
     *            需要绑定的View对象。
     */
    public void setScrollEvent(View bindView) {
        mBindView = bindView;
        mBindView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时，记录按下时的坐标
                xDown = event.getRawX();
                yDown = event.getRawY();
                // 将滑动状态初始化为DO_NOTHING
                slideState = DO_NOTHING;
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                yMove = event.getRawY();
                int moveDistanceX = (int) (xMove - xDown);
                int moveDistanceY = (int) (yMove - yDown);
                // 检查当前的滑动状态
                checkSlideState(moveDistanceX, moveDistanceY);
                switch (slideState) {
                    case SHOW_UP_MENU:
                        contentLayoutParams.topMargin = moveDistanceY;
                        contentLayout.setLayoutParams(contentLayoutParams);
                        break;
                    case SHOW_UP_MENU2:
                    case HIDE_UP_MENU:
                        contentLayoutParams.topMargin = screenHeight / 2
                                + moveDistanceY;
                        contentLayout.setLayoutParams(contentLayoutParams);
                        break;
                    default:
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                yUp = event.getRawY();
                int upDistanceY = (int) (yUp - yDown);
                if (isSliding) {
                    // 手指抬起时，进行判断当前手势的意图
                    switch (slideState) {
                        case SHOW_UP_MENU:
                        case SHOW_UP_MENU2:
                            if (shouldScrollToUpMenu()) {
                                scrollToUpMenu();
                            } else {
                                scrollToContentFromUpMenu();
                            }
                            break;
                        case HIDE_UP_MENU:
                            if (shouldScrollToContentFromUpMenu()) {
                                scrollToContentFromUpMenu();
                            } else {
                                scrollToUpMenu();
                            }
                            break;
                        default:
                            break;
                    }
                } else if (upDistanceY < touchSlop && isUpMenuVisible) {
                    // 当底部界面显示时，如果用户点击一下内容部分，则直接滚动到内容界面
                    scrollToContentFromUpMenu();
                }else if (upDistanceY < touchSlop && !isUpMenuVisible) {
                    //当上册菜单隐藏时，如果用户点击一下内容部分，则直接显示底部界面
                    scrollToUpMenu();
                }
                recycleVelocityTracker();
                break;
        }
        return true;
    }

    /**
     * 创建VelocityTracker对象，并将触摸事件加入到VelocityTracker当中。
     *
     * @param event
     *
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 根据手指移动的距离，判断当前用户的滑动意图，然后给slideState赋值成相应的滑动状态值。
     *
     * @param moveDistanceX
     *            横向移动的距离
     * @param moveDistanceY
     *            纵向移动的距离
     */
    private void checkSlideState(int moveDistanceX, int moveDistanceY) {
        if (isUpMenuVisible) {
            if (!isSliding && Math.abs(moveDistanceY) >= touchSlop
                    && moveDistanceY < 0) {
                isSliding = true;
                slideState = HIDE_UP_MENU;
            } else if (!isSliding && Math.abs(moveDistanceY) >= touchSlop
                    && moveDistanceY > 0) {
                isSliding = true;
                slideState = SHOW_UP_MENU2;
            }
        } else {
            if (!isSliding && Math.abs(moveDistanceY) >= touchSlop
                    && moveDistanceY > 0 && Math.abs(moveDistanceX) < touchSlop) {
                isSliding = true;
                slideState = SHOW_UP_MENU;
                contentLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                contentLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                contentLayout.setLayoutParams(contentLayoutParams);
            }
        }
    }

    /**
     * 判断是否应该滚动将底部界面展示出来。如果手指移动距离大于屏幕宽度的1/4，或者手指移动速度大于SNAP_VELOCITY，
     * 就认为应该滚动将底部界面展示出来。
     *
     * @return 如果应该将底部界面展示出来返回true，否则返回false。
     */
    private boolean shouldScrollToUpMenu() {
        return yUp - yDown > upMenuLayoutParams.height / 4
                || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 判断是否应该从底部界面滚动到内容布局，如果手指移动距离大于屏幕宽度的1/4，或者手指移动速度大于SNAP_VELOCITY，
     * 就认为应该从底部界面滚动到内容布局。
     *
     * @return 如果应该从底部界面滚动到内容布局返回true，否则返回false。
     */
    private boolean shouldScrollToContentFromUpMenu() {
        return yDown - yUp > upMenuLayoutParams.height / 4
                || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 获取手指在绑定布局上的滑动速度。
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    class   UpMenuScrollTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... speed) {
            int topMargin = contentLayoutParams.topMargin;
            // 根据传入的速度来滚动界面，当滚动到达边界值时，跳出循环。
            while (true) {
                topMargin = topMargin - speed[0];
                if (topMargin > screenHeight / 2) {
                    topMargin = screenHeight / 2;
                    break;
                }
                if (topMargin < 0) {
                    topMargin = 0;
                    break;
                }
                publishProgress(topMargin);
                // 为了要有滚动效果产生，每次循环使线程睡眠一段时间，这样肉眼才能够看到滚动动画。
                sleep(20);
            }
            if (speed[0] > 0) {
                isUpMenuVisible = false;
            } else {
                isUpMenuVisible = true;
            }
            isSliding = false;
            return topMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            contentLayoutParams.topMargin = topMargin[0];
            contentLayout.setLayoutParams(contentLayoutParams);
            unFocusBindView();
        }

        @Override
        protected void onPostExecute(Integer topMargin) {
            contentLayoutParams.topMargin = topMargin;
            contentLayout.setLayoutParams(contentLayoutParams);
        }
    }

    /**
     * 使当前线程睡眠指定的毫秒数。
     *
     * @param millis
     *            指定当前线程睡眠多久，以毫秒为单位
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用可以获得焦点的控件在滑动的时候失去焦点。
     */
    private void unFocusBindView() {
        if (mBindView != null) {
            mBindView.setPressed(false);
            mBindView.setFocusable(false);
            mBindView.setFocusableInTouchMode(false);
        }
    }

    /**
     * 将界面滚动到底部界面界面，滚动速度设定为-30.
     */
    public void scrollToUpMenu() {
        new UpMenuScrollTask().execute(-30);
    }

    /**
     * 将界面从底部界面滚动到内容界面，滚动速度设定为30.
     */
    public void scrollToContentFromUpMenu() {
        new UpMenuScrollTask().execute(30);
    }

    /**
     * 在onLayout中重新设定底部界面、下侧菜单、以及内容布局的参数。
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 获取底部界面布局对象
            upMenuLayout = getChildAt(0);
            upMenuLayoutParams = (MarginLayoutParams) upMenuLayout
                    .getLayoutParams();
            // 获取内容布局对象
            contentLayout = getChildAt(1);
            contentLayoutParams = (RelativeLayout.LayoutParams) contentLayout
                    .getLayoutParams();
            contentLayoutParams.height = screenHeight;
            contentLayout.setLayoutParams(contentLayoutParams);
        }
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }
}
