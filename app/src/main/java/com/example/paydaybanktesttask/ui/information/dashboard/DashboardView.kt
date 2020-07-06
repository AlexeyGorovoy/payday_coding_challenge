package com.example.paydaybanktesttask.ui.information.dashboard

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.paydaybanktesttask.R
import com.example.paydaybanktesttask.domain.repository.model.Dashboard
import kotlinx.android.synthetic.main.view_dashboard.view.*

class DashboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_dashboard, this)
    }

    fun setDashboard(dashboard: Dashboard) {
        dashboardItemCircleTextView.text = dashboard.category[0].toString()
        dashboardItemCategoryTextView.text = dashboard.category
        dashboardItemDescriptionTextView.text = dashboard.description
    }
}

