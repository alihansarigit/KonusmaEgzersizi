package com.yuhdeveloper.konusmaegzersizi.viewPagerSample

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.preference.PreferenceManager
import android.support.v4.view.PagerAdapter
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.yuhdeveloper.konusmaegzersizi.R

class ViewPagerAdapter(private val context: Context) : PagerAdapter() {


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.viewholder_image_card, container, false)
        val photoItem = PhotoItem.values()[position]

        view.findViewById<ImageView>(R.id.card_imageview).setImageResource(photoItem.photoId)
        view.findViewById<TextView>(R.id.card_title).setText(photoItem.nameId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.findViewById<TextView>(R.id.card_location).setText(Html.fromHtml(photoItem.locationId, Html.FROM_HTML_MODE_LEGACY))

        } else {
            view.findViewById<TextView>(R.id.card_location).setText(Html.fromHtml(photoItem.locationId))
        }
//        view.findViewById<Button>(R.id.card_see_original_button).setOnClickListener({ openLink(photoItem.link) })

        container?.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container?.removeView(`object` as View)
    }


    override fun isViewFromObject(p0: View, `object`: Any): Boolean = p0 == `object`

    override fun getCount(): Int = PreferenceManager.getDefaultSharedPreferences(context).
            getInt(PagerNumberPickerDialogPreference.KEY_NUM_PAGES, PagerNumberPickerDialogPreference.DEFAULT_PAGES)

    private fun openLink(link: Uri) {
        context.startActivity(Intent(Intent.ACTION_VIEW, link))
    }
}