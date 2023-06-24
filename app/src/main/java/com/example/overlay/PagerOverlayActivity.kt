package com.example.overlay

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.drawToBitmap
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.overlay.databinding.ActivityViewpagerBinding

class PagerOverlayActivity : AppCompatActivity() {

    private val vb by lazy {
        ActivityViewpagerBinding.inflate(layoutInflater)
    }

    private val pageOverlayView by lazy { PageOverlayView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        vb.viewpager.also { viewpager ->
            viewpager.adapter = PageAdapter(
                listOf(
                    PageItem("img_0", AppCompatResources.getDrawable(this, R.drawable.img_0)),
                    PageItem("img_1", AppCompatResources.getDrawable(this, R.drawable.img_1)),
                    PageItem("img_2", AppCompatResources.getDrawable(this, R.drawable.img_2)),
                    PageItem("img_3", AppCompatResources.getDrawable(this, R.drawable.img_3)),
                )
            )
            viewpager.setPageTransformer { page, position ->
                Log.d(
                    "ViewPagerActivity",
                    "setPageTransformer pos: $position, currentPos: ${viewpager.currentItem}"
                )
                if (position < 0f) {
                    page.translationX = 0f
                    page.translationZ = 0f
                } else {
                    page.translationX = page.width * -position
                    page.translationZ = -position
                }

            }
            viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    pageOverlayView.layout(
                        viewpager.left,
                        viewpager.top,
                        viewpager.right,
                        viewpager.bottom
                    )
                    Log.d("ViewPagerActivity", "onPageSelected: $position")
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    if (position != pageOverlayView.currentPosition) {
                        pageOverlayView.overlay =
                            viewpager.recyclerView.findViewHolderForAdapterPosition(position)?.itemView?.drawToBitmap()
                    }
                    pageOverlayView.currentPosition = position
                    pageOverlayView.currentPositionOffsetPx = positionOffsetPixels
                    Log.d(
                        "ViewPagerActivity",
                        "onPageScrolled: $position, positionOffsetPixels: $positionOffsetPixels"
                    )
                }
            })
            viewpager.overlay.add(pageOverlayView)
        }
    }


    private val ViewPager2.recyclerView: RecyclerView
        get() = this[0] as RecyclerView

}