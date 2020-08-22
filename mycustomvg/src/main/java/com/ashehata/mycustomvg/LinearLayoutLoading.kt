package com.ashehata.mycustomvg

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.ashehata.mycustomvg.base.DEFAULT_CONTAINER_ALPHA
import com.ashehata.mycustomvg.base.DEFAULT_PROGRESS_COLOR
import com.ashehata.mycustomvg.base.DEFAULT_PROGRESS_LOADING


class ConstraintLoading(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {


    /**
     * for views ref
     */
    private var parentLayout: FrameLayout? = null
    private var container: FrameLayout? = null
    //private lateinit var progress: ProgressBar

    /**
     * vars
     */
    private var progressColor = DEFAULT_PROGRESS_COLOR
    private var showLoading = DEFAULT_PROGRESS_LOADING
    private var containerAlpha = DEFAULT_CONTAINER_ALPHA


    init {
        setupAttributes(attrs)
        drawMyView()
    }


    private fun drawMyView() {
        val parentLayout =
            LayoutInflater.from(context).inflate(R.layout.background_progress, this)

        container = parentLayout.findViewById(R.id.fl_container)
        container?.apply {
            visibility = showLoading.viewVisibility()
        }

        // Set width & height for it
        parentLayout.apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            alpha = containerAlpha
        }

    }


    private fun setupAttributes(attrs: AttributeSet) {
        // Obtain a typed array of attributes

        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.RelativeLayoutLoading,
            0, 0
        )

        // Extract custom attributes into member variables
        showLoading =
            typedArray.getBoolean(R.styleable.RelativeLayoutLoading_show_loading, DEFAULT_PROGRESS_LOADING)

        progressColor =
            typedArray.getColor(R.styleable.RelativeLayoutLoading_progress_color, DEFAULT_PROGRESS_COLOR)

        containerAlpha =
            typedArray.getFloat(
                R.styleable.RelativeLayoutLoading_container_alpha,
                DEFAULT_CONTAINER_ALPHA
            )
        // access mpre and more attrs ..

        // Cuz of TypedArray objects are shared and must be recycled.
        typedArray.recycle()
    }

    fun loadingProgress(boolean: Boolean) {
        container?.visibility = boolean.viewVisibility()
        //startAnimations()
    }

    private fun startAnimations() {
        val animate = parentLayout?.width?.toFloat()?.let {
            TranslateAnimation(
                0F,
                it, 0F, 0F
            )
        }
        animate?.duration = 300
        animate?.fillAfter = true
        container?.startAnimation(animate)
        container?.visibility = GONE
    }


    fun setContainerAlpha(mAlpha: Float) {
        container?.alpha = mAlpha
    }

    private fun Boolean?.viewVisibility(): Int {
        if (this == null) return View.GONE
        return if (this) View.VISIBLE else View.GONE
    }
}