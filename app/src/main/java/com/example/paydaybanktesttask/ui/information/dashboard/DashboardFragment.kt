package com.example.paydaybanktesttask.ui.information.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paydaybanktesttask.R
import com.example.paydaybanktesttask.di.Scopes
import com.example.paydaybanktesttask.ui.BaseFragment
import com.example.paydaybanktesttask.ui.information.InformationActivity
import com.example.paydaybanktesttask.util.gone
import com.example.paydaybanktesttask.util.visible
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.java.KoinJavaComponent

class DashboardFragment : BaseFragment() {

    private val scope = KoinJavaComponent.getKoin().getScope(Scopes.INFORMATION_ACTIVITY.name)
    private val viewModel: DashboardViewModel by scope.viewModel(this)
    lateinit var mAdapter: DashboardViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dividerItemDecoration =
            DividerItemDecoration(activity, LinearLayoutManager.VERTICAL).apply {
                activity?.let {
                    ContextCompat.getDrawable(it, R.drawable.item_divider_dashboard)
                        ?.let { drawable -> setDrawable(drawable) }
                }
            }

        mAdapter = DashboardViewAdapter()
        with(dashboardRecyclerView) {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(dividerItemDecoration)
            adapter = mAdapter
        }
    }

    override fun provideViewModel() = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initSpinner()
        initLoading()
    }

    override fun showProgress() {
        dashboardProgressBar.visible()
        dashboardStartMonth.gone()
        dashboardDividerView.gone()
        dashboardRecyclerView.gone()
    }

    override fun hideProgress() {
        dashboardProgressBar.gone()
        dashboardStartMonth.visible()
        dashboardDividerView.visible()
        dashboardRecyclerView.visible()
    }

    private fun observeViewModel() {
        viewModel.getEventGetDashboardList.observe(
            viewLifecycleOwner,
            Observer { eventIt ->
                eventIt?.let {
                    mAdapter.dashboardList = it
                }
            })
    }

    private fun initLoading() {
        viewModel.callDashboardListPreparing((activity as InformationActivity).getCustomerId())
    }

    private fun initSpinner() {
        val periodsList = arrayOf(
            resources.getString(R.string.last_select_month),
            resources.getString(R.string.last_three_month),
            resources.getString(R.string.last_six_month),
            resources.getString(R.string.last_nine_month),
            resources.getString(R.string.last_year),
            resources.getString(R.string.last_year_and_half),
            resources.getString(R.string.last_two_years)
        )
        val mAdapter = activity?.baseContext?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                periodsList
            )
        }
        mAdapter?.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        with(dashboardStartMonth) {
            adapter = mAdapter;
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val periodMonths = when (position) {
                        0 -> 0
                        1 -> 3
                        2 -> 6
                        3 -> 9
                        4 -> 12
                        5 -> 18
                        6 -> 24
                        else -> 0
                    }
                    viewModel.callDashboardFilteringByPeriod(periodMonths)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }
}