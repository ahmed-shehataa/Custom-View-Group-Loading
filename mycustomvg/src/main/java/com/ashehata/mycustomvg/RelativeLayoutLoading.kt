package com.ashehata.mycustomvg

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.ashehata.mycustomvg.base.DEFAULT_CONTAINER_ALPHA
import com.ashehata.mycustomvg.base.DEFAULT_ELEVATION
import com.ashehata.mycustomvg.base.DEFAULT_PROGRESS_COLOR
import com.ashehata.mycustomvg.base.DEFAULT_PROGRESS_LOADING


class RelativeLayoutLoading(context: Context, attrs: AttributeSet) :
    RelativeLayout(context, attrs), View.OnLayoutChangeListener {


    /**
     * for views ref
     */
    private var parentLayout: View? = null
    private var container: FrameLayout? = null
    private lateinit var progress: ProgressBar

    /**
     * vars
     */
    private var progressColor = DEFAULT_PROGRESS_COLOR
    private var showLoading = DEFAULT_PROGRESS_LOADING
    private var containerAlpha = DEFAULT_CONTAINER_ALPHA


    init {
        setupAttributes(attrs)
        drawMyView()
        addOnLayoutChangeListener(this)
    }


    private fun drawMyView() {
        inflateLayout()
    }

    private fun inflateLayout() {

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        parentLayout = inflater.inflate(R.layout.background_progress, this, true)

        // set up views ref
        container = (parentLayout as View).findViewById(R.id.fl_container)
        progress = (parentLayout as View).findViewById(R.id.fl_progress)

        container?.apply {
            visibility = showLoading.viewVisibility()
            alpha = containerAlpha
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            (container as FrameLayout).elevation = DEFAULT_ELEVATION
            //progress.setIndeterminateTintList(ColorStateList.valueOf(progressColor));

        } else {
            (container as FrameLayout).bringToFront()
        }

    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return isProgressNotLoading()
    }

    private fun isProgressNotLoading() =
        container?.visibility == View.VISIBLE && showLoading


    private fun setupAttributes(attrs: AttributeSet) {
        // Obtain a typed array of attributes
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.RelativeLayoutLoading,
            0, 0
        )

        // Extract custom attributes into member variables
        showLoading =
            typedArray.getBoolean(
                R.styleable.RelativeLayoutLoading_show_loading,
                DEFAULT_PROGRESS_LOADING
            )

        progressColor =
            typedArray.getColor(
                R.styleable.RelativeLayoutLoading_progress_color,
                DEFAULT_PROGRESS_COLOR
            )

        containerAlpha =
            typedArray.getFloat(
                R.styleable.RelativeLayoutLoading_container_alpha,
                DEFAULT_CONTAINER_ALPHA
            )
        // access more and more attrs ..

        // Cuz of TypedArray objects are shared and must be recycled.
        typedArray.recycle()
    }

    fun loadingProgress(boolean: Boolean) {
        showLoading = boolean
        handleAnimation()
        container?.visibility = boolean.viewVisibility()
        hideKeypad()
    }

    private fun handleAnimation() {
        if (showLoading) {
            startAnimations(false)
        } else {
            startAnimations(true)
        }
    }

    private fun hideKeypad() {
        val view = parentLayout
        if (view != null) {
            val imm: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    private fun endAnimations() {
        val anim = AlphaAnimation(containerAlpha, 0.0f)
        anim.duration = 500
        anim.repeatMode = Animation.RELATIVE_TO_SELF
        container?.startAnimation(anim)

    }

    private fun startAnimations(ifEnd: Boolean) {
        val anim = if (ifEnd) {
            AlphaAnimation(containerAlpha, 0F)
        } else {
            AlphaAnimation(0F, containerAlpha)
        }

        anim.apply {
            duration = 500
            repeatMode = Animation.INFINITE
            container?.startAnimation(this)
        }

    }

    fun setContainerAlpha(mAlpha: Float) {
        container?.alpha = mAlpha
    }

    private fun Boolean?.viewVisibility(): Int {
        if (this == null) return View.GONE
        return if (this) View.VISIBLE else View.GONE
    }

    override fun onLayoutChange(
        p0: View?,
        p1: Int,
        p2: Int,
        p3: Int,
        p4: Int,
        p5: Int,
        p6: Int,
        p7: Int,
        p8: Int
    ) {

    }

}