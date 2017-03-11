package com.yanyuqi.ctmeter.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.support.v4.view.ViewPager;

/**
 * 自定义滑动切换指示器
 * Created by yanyuqi on 2017/2/10.
 */

public class ScrollChangeIndicator extends View implements ViewPager.OnPageChangeListener {

    public static final int CLICK_LEFT = 0;
    public static final int CLICK_RIGHT = 1;
    public static final int INDICATE_LEFT = 0;
    public static final int INDICATE_RIGHT = 1;
    public static final int SPEED = 5;
    public static final int BEGIN_ALPHA = 255;
    private int rightColorValue;
    private int leftColorValue;
    private int mWidth;
    private int mHeight;
    private int START_POINT_X;
    private float scrollStartX;
    private int rightTextColor;
    private int leftTextColor;
    private int rightBackColor;
    private float angel = 26;
    private int leftBackColor;
    private float startX;
    private int clickPosition;
    private int indicatePosition;
    private android.support.v4.view.ViewPager viewPager;
    private boolean isClicked;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private float offset;
    private int alpha;
    private String textLeft;
    private String textRight;

    public ScrollChangeIndicator(Context context) {
        this(context, null);
    }

    public ScrollChangeIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        leftColorValue = Color.BLUE;
        rightColorValue = Color.WHITE;
    }

    public ScrollChangeIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getRightColorValue() {
        return rightColorValue;
    }

    public void setRightColorValue(int rightColorValue) {
        this.rightColorValue = rightColorValue;
        invalidate();
    }

    public int getLeftColorValue() {
        return leftColorValue;
    }

    public void setLeftColorValue(int leftColorValue) {
        this.leftColorValue = leftColorValue;
        invalidate();
    }

    public int getRightBackColor() {
        return rightBackColor;
    }

    public void setRightBackColor(int rightBackColor) {
        this.rightBackColor = rightBackColor;
        invalidate();
    }

    public int getLeftBackColor() {
        return leftBackColor;
    }

    public void setLeftBackColor(int leftBackColor) {
        this.leftBackColor = leftBackColor;
        invalidate();
    }

    public String getTextLeft() {
        return textLeft;
    }

    public void setTextLeft(String textLeft) {
        this.textLeft = textLeft;
        invalidate();
    }

    public String getTextRight() {
        return textRight;
    }

    public void setTextRight(String textRight) {
        this.textRight = textRight;
        invalidate();
    }

    public float getAngel() {
        return angel;
    }

    public void setAngel(float angel) {
        this.angel = angel;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        this.viewPager.addOnPageChangeListener(this);
    }

    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public int getClickPosition() {
        return clickPosition;
    }

    public void setClickPosition(int clickPosition) {
        this.clickPosition = clickPosition;
        switch (clickPosition) {
            case CLICK_LEFT:
                clickLeft();
                break;
            case CLICK_RIGHT:
                clickRight();
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasuredWidth(widthMeasureSpec), getMeasuredHeight(heightMeasureSpec));
    }

    private int getMeasuredHeight(int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                return height;
            case MeasureSpec.AT_MOST:
                return Math.min(height, dp2px(getContext(), 30));
            case MeasureSpec.EXACTLY:
                return Math.max(height, dp2px(getContext(), 20));
        }
        return 0;
    }

    private int getMeasuredWidth(int widthMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                return width;
            case MeasureSpec.AT_MOST:
                return Math.min(width, dp2px(getContext(), 200));
            case MeasureSpec.EXACTLY:
                return Math.max(width, dp2px(getContext(), 200));
        }
        return 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        START_POINT_X = -mWidth / 2;
        scrollStartX = START_POINT_X;
        leftTextColor = leftColorValue;
        rightTextColor = rightColorValue;
        alpha = BEGIN_ALPHA;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);
        drawBackground(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(leftBackColor);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL);

        RectF rectF = new RectF(scrollStartX, -mHeight / 2, scrollStartX + mWidth / 2, mHeight / 2);
        canvas.drawRoundRect(rectF, angel, angel, paint);
        drawText(canvas, textLeft, textRight);
    }

    private void drawText(Canvas canvas, String textLeft, String textRight) {
        Paint paint = new Paint();
        Rect rectF = new Rect();
        paint.setAntiAlias(true);
        paint.setTextSize(dp2px(getContext(), 16));
        paint.setTextScaleX(1.0f);
        paint.setFakeBoldText(true);
        paint.setColor(leftTextColor);
        paint.setAlpha(alpha);
        paint.getTextBounds(textLeft, 0, textLeft.length(), rectF);
        float leftTextWidth = rectF.width();
        float leftTextHeight = rectF.height();
        paint.getTextBounds(textRight, 0, textRight.length(), rectF);
        float rightTextWidth = rectF.width();
        float rightTextHeight = rectF.height();
        canvas.drawText(textLeft, -mWidth / 4 - leftTextWidth / 2, leftTextHeight / 4, paint);
        paint.setColor(rightTextColor);
        paint.setAlpha(alpha);
        canvas.drawText(textRight, mWidth / 4 - rightTextWidth / 2, rightTextHeight / 4, paint);
    }

    private void drawBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(rightBackColor);
        paint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(-mWidth / 2, -mHeight / 2, mWidth / 2, mHeight / 2);
        canvas.drawRoundRect(rectF, angel, angel, paint);
        paint.setAntiAlias(true);
        paint.setColor(leftBackColor);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        RectF rectF1 = new RectF(-mWidth / 2, -mHeight / 2, mWidth / 2, mHeight / 2);
        canvas.drawRoundRect(rectF1, angel, angel, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setUpViewPager();
                break;
            case MotionEvent.ACTION_UP:
                startX = event.getX();
                isClicked = true;
                if (startX > 0 && startX <= mWidth / 2) {
                    clickPosition = CLICK_LEFT;
                    clickLeft();
                } else if (startX > mWidth / 2 && startX <= mWidth) {
                    clickPosition = CLICK_RIGHT;
                    clickRight();
                }
                break;
        }
        return true;
    }

    private void clickRight() {
        if (indicatePosition == INDICATE_RIGHT) {
            return;
        }
        indicatePosition = INDICATE_RIGHT;
        changeTextColor(indicatePosition);
        viewPager.setCurrentItem(1, true);
    }

    private void clickLeft() {
        if (indicatePosition == INDICATE_LEFT) {
            return;
        }
        indicatePosition = INDICATE_LEFT;
        changeTextColor(indicatePosition);
        viewPager.setCurrentItem(0, true);
    }

    private void changeTextColor(int indicatePosition) {
        switch (indicatePosition) {
            case INDICATE_LEFT:
                leftTextColor = leftColorValue;
                rightTextColor = rightColorValue;
                break;
            case INDICATE_RIGHT:
                leftTextColor = rightColorValue;
                rightTextColor = leftColorValue;
                break;
        }
        invalidate();
    }

    private void setUpViewPager() {

        if (viewPager != null) {
            viewPager.getAdapter();
        } else {
            try {
                throw new Exception("There is no ViewPager been set with View!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int dp2px(Context context, int i) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (i * scale + 0.5f);
    }

    public int getCurrentItem(){
       return viewPager.getCurrentItem();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        offset = positionOffset;
        if (offset != 0) {
            scrollStartX = START_POINT_X + mWidth / 2 * offset;
            if (!isClicked) {
                setTextAlphaColor(offset);
            }
            invalidate();
        }
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    private void setTextAlphaColor(float offset) {
        if (offset <= 0.5) {
            alpha = (int) (BEGIN_ALPHA * (1 - offset));
            indicatePosition = INDICATE_LEFT;
        } else if (offset > 0.5) {
            alpha = (int) (BEGIN_ALPHA * offset);
            indicatePosition = INDICATE_RIGHT;
        }
        changeTextColor(indicatePosition);
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case CLICK_LEFT:
                indicatePosition = INDICATE_LEFT;
                break;
            case CLICK_RIGHT:
                indicatePosition = INDICATE_RIGHT;
                break;
        }
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (isClicked && state == 0) {
            isClicked = false;
        }
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
