package com.pressfforrespect.codenamespictures.Animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.pressfforrespect.codenamespictures.R;
import com.pressfforrespect.codenamespictures.game.Team;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FlipAnimation extends Animation {


    private Camera camera;

    private View fromView;
    private Team team;

    private float centerX;
    private float centerY;

    private boolean forward = true;

    private Drawable image;


    /**
     * Creates a 3D flip animation between two views.
     *
     * @param fromView First view in the transition.
     * @param team   team color the transition.
     */
    public FlipAnimation(View fromView, Team team) {
        this.fromView = fromView;

        setDuration(500);
        setFillAfter(false);
        setInterpolator(new AccelerateDecelerateInterpolator());

        switch (team){
            case RED:
                image = fromView.getContext().getDrawable(R.drawable.red_team);
                break;
            case BLUE:
                image = fromView.getContext().getDrawable(R.drawable.blue_team);
                break;
            case ASSASSIN:
                image = fromView.getContext().getDrawable(R.drawable.assasin);
                break;
            case BYSTANDER:
                image = fromView.getContext().getDrawable(R.drawable.bystander);
                break;
            default:
                image = fromView.getContext().getDrawable(R.drawable.bystander);
        }

    }


    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        centerX = width / 2;
        centerY = height / 2;
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        // Angle around the y-axis of the rotation at the given time
        // calculated both in radians and degrees.
        final double radians = Math.PI * interpolatedTime;
        float degrees = (float) (180.0 * radians / Math.PI);

        // Once we reach the midpoint in the animation, we need to hide the
        // source view and show the destination view. We also need to change
        // the angle by 180 degrees so that the destination does not come in
        // flipped around
        if (interpolatedTime >= 0.5f) {
            degrees -= 180.f;

            ((ImageView) ((FrameLayout) ((CardView) fromView).getChildAt(0)).getChildAt(0)).setImageDrawable(image);

        }

        if (forward)
            degrees = -degrees; //determines direction of rotation when flip begins

        final Matrix matrix = t.getMatrix();
        camera.save();
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
