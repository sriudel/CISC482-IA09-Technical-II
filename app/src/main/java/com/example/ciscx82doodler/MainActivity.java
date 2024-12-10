package com.example.ciscx82doodler;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends Activity {
    private DoodleView doodleView;
    private SeekBar brushSize;
    private SeekBar opacityControl;
    private LinearLayout colorPalette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doodleView = findViewById(R.id.doodleView);
        brushSize = findViewById(R.id.brush_size);
        opacityControl = findViewById(R.id.opacity_control);
        colorPalette = findViewById(R.id.color_palette);

        brushSize.setProgress(20); // Default brush size

        Button brushButton = findViewById(R.id.brush_button);
        brushButton.setOnClickListener(v -> {
            doodleView.setShapeMode(""); // Switch to brush mode
            toggleVisibility(brushSize);
        });

        brushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                doodleView.setBrushSize(progress * 2); // Scale size
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        Button opacityButton = findViewById(R.id.opacity_button);
        opacityButton.setOnClickListener(v -> toggleVisibility(opacityControl));

        opacityControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                doodleView.setOpacity(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        Button colorButton = findViewById(R.id.color_button);
        colorButton.setOnClickListener(v -> toggleVisibility(colorPalette));

        Button clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(v -> doodleView.clearCanvas());

        Button eraserButton = findViewById(R.id.eraser_button);
        eraserButton.setOnClickListener(v -> doodleView.toggleEraser());

        Button circleButton = findViewById(R.id.circle_button);
        circleButton.setOnClickListener(v -> doodleView.setShapeMode("Circle"));

        Button rectangleButton = findViewById(R.id.rectangle_button);
        rectangleButton.setOnClickListener(v -> doodleView.setShapeMode("Rectangle"));

        Button lineButton = findViewById(R.id.line_button);
        lineButton.setOnClickListener(v -> doodleView.setShapeMode("Line"));

        findViewById(R.id.color_violet).setOnClickListener(v -> doodleView.setBrushColor(Color.parseColor("#8B00FF")));
        findViewById(R.id.color_indigo).setOnClickListener(v -> doodleView.setBrushColor(Color.parseColor("#4B0082")));
        findViewById(R.id.color_blue).setOnClickListener(v -> doodleView.setBrushColor(Color.BLUE));
        findViewById(R.id.color_green).setOnClickListener(v -> doodleView.setBrushColor(Color.GREEN));
        findViewById(R.id.color_yellow).setOnClickListener(v -> doodleView.setBrushColor(Color.YELLOW));
        findViewById(R.id.color_orange).setOnClickListener(v -> doodleView.setBrushColor(Color.parseColor("#FF7F00")));
        findViewById(R.id.color_red).setOnClickListener(v -> doodleView.setBrushColor(Color.RED));
    }

    private void toggleVisibility(View view) {
        view.setVisibility(view.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
}
