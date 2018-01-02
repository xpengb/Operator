/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.nxt.xpengb.common.widget.photoviewer.photoview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.nxt.xpengb.common.widget.photoviewer.TransitionImageView;
import com.nxt.xpengb.common.widget.photoviewer.Utils;

/**
 * A zoomable {@link ImageView}. See {@link PhotoViewAttacher} for most of the details on how the zooming
 * is accomplished
 */
public class PhotoView extends AppCompatImageView {

    private PhotoViewAttacher attacher;
    private ScaleType pendingScaleType;
    private float PULL_ALPHA_DISTANCE;
    private TransitionImageView transitionImageView;
    private int bgAlpha; // 0..255
    private Context context;

    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public PhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        this.context = context;
        init();
    }

    public void setTransitionImageView(TransitionImageView imageView) {
        transitionImageView = imageView;
    }

    public int getBgAlpha() {
        return bgAlpha;
    }

    private void init() {
        attacher = new PhotoViewAttacher(this);
        //We always pose as a Matrix scale type, though we can change to another scale type
        //via the attacher
        super.setScaleType(ScaleType.MATRIX);
        //apply the previously applied scale type
        if (pendingScaleType != null) {
            setScaleType(pendingScaleType);
            pendingScaleType = null;
        }
        PULL_ALPHA_DISTANCE = Utils.dip2px(getContext(), 300);
        attacher.setOnPullCloseListener(new PhotoViewAttacher.OnPullCloseListener() {
            @Override
            public void onPullClose(float dx, float dy) {
                bgAlpha = (int) (255 * (1 - Math.abs(dy) / PULL_ALPHA_DISTANCE));
                changeBackgroundColorAlpha(bgAlpha);
            }

            @Override
            public void onClose(float dy) {
                if (transitionImageView != null) {
                    transitionImageView.setEndColor(Color.argb(bgAlpha, 0, 0, 0));
                    float[] values = new float[9];
                    transitionImageView.getFullMatrix().getValues(values);
                    values[Matrix.MTRANS_Y] += dy;
                    /*float[] internalValues = new float[9];
                    transitionImageView.getFullMatrix().getValues(values);
                    attacher.getSuppMatrix().getValues(internalValues);
                    values[Matrix.MTRANS_Y] = dy + internalValues[Matrix.MTRANS_Y];*/
                    transitionImageView.setFullMatrix(values);
                }
                if (context instanceof Activity) {
                    ((Activity) context).onBackPressed();
                }
            }
        });
    }

    public void changeBackgroundColorAlpha(int alpha) {
        if (alpha < 0) {
            alpha = 0;
        }
        if (alpha > 255) {
            alpha = 255;
        }
        int color = Color.argb(alpha, 0, 0, 0);
        //setBackgroundColor(color);
        if (getParent() != null && getParent() instanceof View) {
            ((View) getParent()).setBackgroundColor(color);
        }
    }

    /**
     * Get the current {@link PhotoViewAttacher} for this view. Be wary of holding on to references
     * to this attacher, as it has a reference to this view, which, if a reference is held in the
     * wrong place, can cause memory leaks.
     *
     * @return the attacher.
     */
    public PhotoViewAttacher getAttacher() {
        return attacher;
    }

    @Override
    public ScaleType getScaleType() {
        return attacher.getScaleType();
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (attacher == null) {
            pendingScaleType = scaleType;
        } else {
            attacher.setScaleType(scaleType);
        }
    }

    @Override
    public Matrix getImageMatrix() {
        return attacher.getImageMatrix();
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        attacher.setOnLongClickListener(l);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        attacher.setOnClickListener(l);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        // setImageBitmap calls through to this method
        if (attacher != null) {
            attacher.update();
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if (attacher != null) {
            attacher.update();
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (attacher != null) {
            attacher.update();
        }
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        boolean changed = super.setFrame(l, t, r, b);
        if (changed) {
            attacher.update();
        }
        return changed;
    }

    public void setRotationTo(float rotationDegree) {
        attacher.setRotationTo(rotationDegree);
    }

    public void setRotationBy(float rotationDegree) {
        attacher.setRotationBy(rotationDegree);
    }

    @Deprecated
    public boolean isZoomEnabled() {
        return attacher.isZoomEnabled();
    }

    public boolean isZoomable() {
        return attacher.isZoomable();
    }

    public void setZoomable(boolean zoomable) {
        attacher.setZoomable(zoomable);
    }

    public RectF getDisplayRect() {
        return attacher.getDisplayRect();
    }

    public void getDisplayMatrix(Matrix matrix) {
        attacher.getDisplayMatrix(matrix);
    }

    public boolean setDisplayMatrix(Matrix finalRectangle) {
        return attacher.setDisplayMatrix(finalRectangle);
    }

    public void getSuppMatrix(Matrix matrix) {
        attacher.getSuppMatrix(matrix);
    }

    public boolean setSuppMatrix(Matrix matrix) {
        return attacher.setDisplayMatrix(matrix);
    }

    public float getMinimumScale() {
        return attacher.getMinimumScale();
    }

    public void setMinimumScale(float minimumScale) {
        attacher.setMinimumScale(minimumScale);
    }

    public float getMediumScale() {
        return attacher.getMediumScale();
    }

    public void setMediumScale(float mediumScale) {
        attacher.setMediumScale(mediumScale);
    }

    public float getMaximumScale() {
        return attacher.getMaximumScale();
    }

    public void setMaximumScale(float maximumScale) {
        attacher.setMaximumScale(maximumScale);
    }

    public float getScale() {
        return attacher.getScale();
    }

    public void setScale(float scale) {
        attacher.setScale(scale);
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        attacher.setAllowParentInterceptOnEdge(allow);
    }

    public void setScaleLevels(float minimumScale, float mediumScale, float maximumScale) {
        attacher.setScaleLevels(minimumScale, mediumScale, maximumScale);
    }

    public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
        attacher.setOnMatrixChangeListener(listener);
    }

    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        attacher.setOnPhotoTapListener(listener);
    }

    public void setOnOutsidePhotoTapListener(OnOutsidePhotoTapListener listener) {
        attacher.setOnOutsidePhotoTapListener(listener);
    }

    public void setOnViewTapListener(OnViewTapListener listener) {
        attacher.setOnViewTapListener(listener);
    }

    public void setOnViewDragListener(OnViewDragListener listener) {
        attacher.setOnViewDragListener(listener);
    }

    public void setScale(float scale, boolean animate) {
        attacher.setScale(scale, animate);
    }

    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        attacher.setScale(scale, focalX, focalY, animate);
    }

    public void setZoomTransitionDuration(int milliseconds) {
        attacher.setZoomTransitionDuration(milliseconds);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        attacher.setOnDoubleTapListener(onDoubleTapListener);
    }

    public void setOnScaleChangeListener(OnScaleChangedListener onScaleChangedListener) {
        attacher.setOnScaleChangeListener(onScaleChangedListener);
    }

    public void setOnSingleFlingListener(OnSingleFlingListener onSingleFlingListener) {
        attacher.setOnSingleFlingListener(onSingleFlingListener);
    }
}
