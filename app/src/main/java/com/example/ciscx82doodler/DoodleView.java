package com.example.ciscx82doodler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DoodleView extends View {
    private Paint paint;
    private ArrayList<PathWithPaint> paths;
    private Path currentPath;
    private boolean isEraser = false;
    private float startX, startY, endX, endY;
    private String shapeMode = ""; // Shape mode: "", "Circle", "Rectangle", "Line"

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK); // Default brush color
        paint.setStrokeWidth(20);   // Default brush size
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        paths = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PathWithPaint pwp : paths) {
            canvas.drawPath(pwp.path, pwp.paint);
        }
        if (currentPath != null) {
            canvas.drawPath(currentPath, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                if (shapeMode.isEmpty()) {
                    currentPath = new Path();
                    currentPath.moveTo(x, y);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (shapeMode.isEmpty()) {
                    currentPath.lineTo(x, y);
                } else {
                    endX = x;
                    endY = y;
                }
                break;

            case MotionEvent.ACTION_UP:
                if (shapeMode.isEmpty()) {
                    paths.add(new PathWithPaint(currentPath, new Paint(paint)));
                    currentPath = null;
                } else {
                    Paint shapePaint = new Paint(paint);
                    Path shapePath = new Path();
                    if (shapeMode.equals("Circle")) {
                        float radius = (float) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
                        shapePath.addCircle(startX, startY, radius, Path.Direction.CW);
                    } else if (shapeMode.equals("Rectangle")) {
                        shapePath.addRect(startX, startY, endX, endY, Path.Direction.CW);
                    } else if (shapeMode.equals("Line")) {
                        shapePath.moveTo(startX, startY);
                        shapePath.lineTo(endX, endY);
                    }
                    paths.add(new PathWithPaint(shapePath, shapePaint));
                }
                break;
        }

        invalidate();
        return true;
    }

    public void setBrushColor(int color) {
        isEraser = false;
        paint.setColor(color);
    }

    public void setBrushSize(float size) {
        paint.setStrokeWidth(size);
    }

    public void setOpacity(int opacity) {
        paint.setAlpha(opacity);
    }

    public void toggleEraser() {
        isEraser = !isEraser;
        paint.setColor(isEraser ? Color.WHITE : paint.getColor());
    }

    public void setShapeMode(String mode) {
        shapeMode = mode; // Set the current shape mode
        isEraser = false; // Disable eraser when selecting a shape
    }

    public void clearCanvas() {
        paths.clear();
        invalidate();
    }

    private static class PathWithPaint {
        Path path;
        Paint paint;

        PathWithPaint(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }
}
