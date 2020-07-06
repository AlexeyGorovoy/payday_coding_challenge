package com.example.paydaybanktesttask.ui.information.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paydaybanktesttask.R
import com.example.paydaybanktesttask.domain.repository.model.Dashboard
import com.example.paydaybanktesttask.domain.repository.model.Transaction


class DashboardViewAdapter : RecyclerView.Adapter<DashboardViewAdapter.DashboardViewHolder>() {

    var dashboardList: List<Dashboard> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardViewAdapter.DashboardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dashboard_view_holder, parent, false)

        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int = dashboardList.size

    override fun onBindViewHolder(holder: DashboardViewAdapter.DashboardViewHolder, position: Int) {
        holder.bind(dashboardList[position])
    }

    inner class DashboardViewHolder(private val dashboardView: View) :
        RecyclerView.ViewHolder(dashboardView) {

        fun bind(dashboard: Dashboard) {
            (dashboardView as DashboardView).setDashboard(dashboard)
        }
    }
}