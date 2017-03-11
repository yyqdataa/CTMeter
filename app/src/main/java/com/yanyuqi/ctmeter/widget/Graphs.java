package com.yanyuqi.ctmeter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.bean.LCQXBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanyuqi on 2017/2/22.
 */

public class Graphs extends View {

    public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 350;
    private int mWidth;
    private int mHeight;
    private List resultData;
    private List<Point> points;
    private float xTotalLength;
    private float yTotalLength;
    private boolean hasGDPoint;
    private Point gdPoint;
    private String grapsType = "";
    private String xName = "";
    private String yName = "";


    public Graphs(Context context) {
        this(context, null);
    }

    public Graphs(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Graphs);
        grapsType = a.getString(R.styleable.Graphs_grapsType);
        xName = a.getString(R.styleable.Graphs_xName);
        yName = a.getString(R.styleable.Graphs_yName);
        a.recycle();
    }

    public Graphs(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasuredWidth(widthMeasureSpec), getMeasuredHeight(heightMeasureSpec));
    }

    private int getMeasuredHeight(int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                return Math.min(height, dp2px(DEFAULT_HEIGHT));
            case MeasureSpec.EXACTLY:
                return height;
            case MeasureSpec.UNSPECIFIED:
                return Math.max(height, dp2px(DEFAULT_HEIGHT));
        }

        return 0;
    }

    private int getMeasuredWidth(int widthMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                return Math.min(width, dp2px(DEFAULT_WIDTH));
            case MeasureSpec.EXACTLY:
                return width;
            case MeasureSpec.UNSPECIFIED:
                return Math.max(width, dp2px(DEFAULT_WIDTH));
        }

        return 0;
    }

    public void setResultData(List resultData) {
        this.resultData = resultData;
        points = createPoints(resultData);
        invalidate();
    }

    /**
     * 连接数据点
     *
     * @param canvas
     * @param points
     */
    private void connectPoints(Canvas canvas, List<Point> points) {
        Path path = new Path();
        path.moveTo(0, 0);
        for (Point p : points) {
            path.lineTo(x2XValue(p.x), -y2YValue(p.y));
        }
        Point point = points.get(points.size() - 1);
        path.setLastPoint(x2XValue(point.x), -y2YValue(point.y));
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#FF146C59"));
        paint.setStrokeWidth(2);
        canvas.drawPath(path, paint);
        paint.reset();
    }

    /**
     * x值大小转换横坐标
     *
     * @param x
     * @return
     */
    private float x2XValue(float x) {
        return x * (mWidth - dp2px(25)) / 6;
    }

    /**
     * y值大小转换纵坐标
     *
     * @param y
     * @return
     */
    private float y2YValue(float y) {
        return y * (mHeight - dp2px(25)) / 160;
    }

    private List<Point> createPoints(List resultData) {
        List points = new ArrayList();
        for (Object o : resultData) {
            LCQXBean lcqxBean = (LCQXBean) o;
            float x = lcqxBean.getE_fi();
            float y = lcqxBean.getE_fu();
            Point p = new Point(x, y);
            points.add(p);
        }
        return points;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        xTotalLength = mWidth - dp2px(35);
        yTotalLength = mHeight - dp2px(25);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(0 + dp2px(30), yTotalLength);
        canvas.drawColor(Color.parseColor("#ffffff"));
        drawTitle(grapsType, canvas);
        drawCoorSystem(canvas);
        if (points != null && points.size() > 0) {
            connectPoints(canvas, points);
            points.clear();
        }
        if (hasGDPoint) {
            drawGDInfo(canvas);
        }
    }

    /**
     * 绘制拐点信息
     *
     * @param canvas
     */
    private void drawGDInfo(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ff0000"));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8);
        paint.setTextSize(dp2px(12));
        canvas.drawPoint(x2XValue(gdPoint.x), -y2YValue(gdPoint.y), paint);
        String gdStr = "实验点数：" + points.size();
        String gdStr2 = "拐点：" + "[" + gdPoint.x + "A, " + gdPoint.y + "V]";
        Rect rect = new Rect();
        paint.getTextBounds(gdStr2, 0, gdStr2.length(), rect);
        int w = rect.width();
        int h = rect.height();
        RectF rectf = new RectF();
        rectf.left = xTotalLength - w - dp2px(10);
        rectf.top = -2 * h - dp2px(12);
        rectf.right = xTotalLength;
        rectf.bottom = -dp2px(5);
        canvas.drawRoundRect(rectf, 5, 5, paint);
//        canvas.drawRoundRect(xTotalLength - w - dp2px(10), -2 * h - dp2px(12), xTotalLength, -dp2px(5), 5, 5, paint);
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawText(gdStr, xTotalLength - w - dp2px(8), -h - dp2px(10), paint);
        canvas.drawText(gdStr2, xTotalLength - w - dp2px(8), -dp2px(10), paint);
        hasGDPoint = false;
    }

    /**
     * 绘制曲线类型名
     *
     * @param grapsType
     * @param canvas
     */
    private void drawTitle(String grapsType, Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor("#000000"));
        paint.setAntiAlias(true);
        paint.setTextSize(dp2px(14));
        Rect rect = new Rect();
        paint.getTextBounds(grapsType, 0, grapsType.length(), rect);
        int w = rect.width();
        int h = rect.height();
        canvas.drawText(grapsType, xTotalLength / 2 - w / 2, -yTotalLength + h, paint);
    }

    /**
     * 设置拐点数据
     *
     * @param x
     * @param y
     */
    public void setGDPoint(float x, float y) {
        hasGDPoint = true;
        gdPoint = new Point(x, y);
        invalidate();
    }

    /**
     * 绘制坐标系
     *
     * @param canvas
     */
    private void drawCoorSystem(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#000000"));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(dp2px(12));

        //Y轴箭头
        Path path1 = new Path();
        path1.moveTo(0, -yTotalLength);
        path1.lineTo(dp2px(5), -(yTotalLength - dp2px(12)));
        path1.lineTo(-dp2px(5), -(yTotalLength - dp2px(12)));

        //X轴箭头
        Path path2 = new Path();
        path2.moveTo(xTotalLength, 0);
        path2.lineTo(xTotalLength - dp2px(12), dp2px(5));
        path2.lineTo(xTotalLength - dp2px(12), -dp2px(5));

        canvas.drawLine(0, 0, xTotalLength, 0, paint);//X轴
        drawXText(paint, xName, canvas);
        drawXKeDu(canvas);
        canvas.drawLine(0, 0, 0, -yTotalLength, paint);//Y轴
        drawYText(paint, yName, canvas);
        drawYKeDu(canvas);
        canvas.drawPath(path1, paint);
        canvas.drawPath(path2, paint);
    }

    /**
     * 绘制Y轴刻度
     *
     * @param canvas
     */
    private void drawYKeDu(Canvas canvas) {
        int count = 8;
        float totalLen = yTotalLength;
        float dis = totalLen / count;
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#000000"));
        paint.setStrokeWidth(2);
        paint.setTextSize(dp2px(12));
        paint.setAntiAlias(true);
        for (int i = 0; i < count; i++) {
            if (i % 2 == 1) {
                canvas.drawLine(0, 0 - i * dis, -dp2px(2), 0 - i * dis, paint);
                drawDashLine(canvas, 0, -i * dis, xTotalLength, -i * dis);
            } else {
                canvas.drawLine(0, 0 - i * dis, -dp2px(5), 0 - i * dis, paint);
                drawYKDValue(paint, i * 20 + "", canvas, -i * dis);
            }
        }
    }

    /**
     * 绘制X轴刻度
     *
     * @param canvas
     */
    private void drawXKeDu(Canvas canvas) {
        int count = 14;
        float totalLen = xTotalLength;
        float dis = totalLen / count;
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#000000"));
        paint.setStrokeWidth(2);
        paint.setTextSize(dp2px(12));
        paint.setAntiAlias(true);
        for (int i = 0; i < count; i++) {
            if (i % 2 == 1) {
                canvas.drawLine(0 + i * dis, 0, 0 + i * dis, dp2px(2), paint);
                drawDashLine(canvas, i * dis, 0, i * dis, -yTotalLength);
            } else {
                canvas.drawLine(0 + i * dis, 0, 0 + i * dis, dp2px(5), paint);
                drawXKDValue(paint, i * 0.5 + "", canvas, i * dis);
            }
        }
    }

    /**
     * 绘制虚线
     *
     * @param canvas
     * @param xValue
     * @param yValue
     * @param xEnd
     * @param yEnd
     */
    private void drawDashLine(Canvas canvas, float xValue, float yValue, float xEnd, float yEnd) {
        PathEffect effect = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        Paint paint = new Paint();
        paint.reset();
        paint.setStyle(Paint.Style.STROKE);//必须设置，否则虚线无法显示
        paint.setColor(Color.parseColor("#FF9F9E9E"));
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setPathEffect(effect);
        Path path = new Path();
        path.moveTo(xValue, yValue);
        path.lineTo(xEnd, yEnd);
        canvas.drawPath(path, paint);
    }

    /**
     * 绘制Y轴刻度值
     *
     * @param paint
     * @param text
     * @param canvas
     * @param yValue
     */
    private void drawYKDValue(Paint paint, String text, Canvas canvas, float yValue) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int h = rect.height();
        int w = rect.width();
        canvas.drawText(text, -dp2px(7) - w, yValue + h / 2, paint);
    }

    /**
     * 绘制X轴刻度值
     *
     * @param paint
     * @param text
     * @param canvas
     * @param xValue
     */
    private void drawXKDValue(Paint paint, String text, Canvas canvas, float xValue) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int w = rect.width();
        int h = rect.height();
        canvas.drawText(text, xValue - w / 2, dp2px(7) + h, paint);
    }

    private void drawXText(Paint paint, String text, Canvas canvas) {
        if (text == null) {
            text = "";
        }
        Rect rectF = new Rect();
        paint.getTextBounds(text, 0, text.length(), rectF);
        int w = rectF.width();
        int h = rectF.height();
        canvas.drawText(text, xTotalLength - w, h, paint);
    }

    private void drawYText(Paint paint, String text, Canvas canvas) {
        if (text == null) {
            text = "";
        }
        Rect rectF = new Rect();
        paint.getTextBounds(text, 0, text.length(), rectF);
        int w = rectF.width();
        int h = rectF.height();
        canvas.drawText(text, w / 3, -yTotalLength + h, paint);
    }

    private int dp2px(int i) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (i * scale + 0.5f);
    }

    class Point {
        float x;
        float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
