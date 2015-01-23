package com.cuber.camerademo;

import android.app.Activity;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;


public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    private Camera camera;
    // views
    private SeekBar seekbarXRotate;
    private SeekBar seekbarYRotate;
    private SeekBar seekbarZRotate;
    private SeekBar seekbarXSkew;
    private SeekBar seekbarYSkew;
    private SeekBar seekbarZTranslate;
    private ImageView imgResult;
    // integer params
    private int rotateX, rotateY, rotateZ;
    private float skewX, skewY;
    private int translateZ;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // camera
        camera = new Camera();
        // initViews
        // rotate
        seekbarXRotate = (SeekBar) findViewById(R.id.rotateX_seekBar);
        seekbarXRotate.setOnSeekBarChangeListener(this);
        seekbarYRotate = (SeekBar) findViewById(R.id.rotateY_seekBar);
        seekbarYRotate.setOnSeekBarChangeListener(this);
        seekbarZRotate = (SeekBar) findViewById(R.id.rotateZ_seekBar);
        seekbarZRotate.setOnSeekBarChangeListener(this);

        // translate
        seekbarXSkew = (SeekBar) findViewById(R.id.skewX_seekBar);
        seekbarXSkew.setOnSeekBarChangeListener(this);
        seekbarYSkew = (SeekBar) findViewById(R.id.skewY_seekBar);
        seekbarYSkew.setOnSeekBarChangeListener(this);
        seekbarZTranslate = (SeekBar) findViewById(R.id.translateZ_seekBar);
        seekbarZTranslate.setOnSeekBarChangeListener(this);
        imgResult = (ImageView) findViewById(R.id.imageView);
        imgResult.setScaleType(ImageView.ScaleType.MATRIX);

        refreshImage();
    }

    private void refreshImage() {

        camera.save();

        Matrix matrix = new Matrix();
        // rotate
        camera.rotateX(rotateX);
        camera.rotateY(rotateY);
        camera.rotateZ(rotateZ);
        // translate
        camera.translate(0, 0, translateZ);
        camera.getMatrix(matrix);

        camera.restore();

        //center
        matrix.preTranslate(-(imgResult.getWidth() >> 1), -(imgResult.getHeight() >> 1));
        matrix.postTranslate(imgResult.getWidth() >> 1, imgResult.getHeight() >> 1);

        matrix.preSkew(skewX, skewY);

        imgResult.setImageMatrix(matrix);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == seekbarXRotate) {
            rotateX = progress;
        } else if (seekBar == seekbarYRotate) {
            rotateY = progress;
        } else if (seekBar == seekbarZRotate) {
            rotateZ = progress;
        } else if (seekBar == seekbarXSkew) {
            skewX = (progress - 100) * 1.0f / 100;
        } else if (seekBar == seekbarYSkew) {
            skewY = (progress - 100) * 1.0f / 100;
        } else if (seekBar == seekbarZTranslate) {
            translateZ = progress - 100;
        }
        refreshImage();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
