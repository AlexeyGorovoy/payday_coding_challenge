package com.example.paydaybanktesttask.ui.information.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_transactions.*
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.java.KoinJavaComponent

class TransactionsFragment : BaseFragment() {

    private val scope = KoinJavaComponent.getKoin().getScope(Scopes.INFORMATION_ACTIVITY.name)
    private val viewModel: TransactionsViewModel by scope.viewModel(this)
    lateinit var adapter: TransactionViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dividerItemDecoration =
            DividerItemDecoration(activity, LinearLayoutManager.VERTICAL).apply {
                activity?.let {
                    ContextCompat.getDrawable(it, R.drawable.item_divider_transaction)
                        ?.let { drawable -> setDrawable(drawable) }
                }
            }
        adapter = TransactionViewAdapter()
        transactionsRecyclerView.let {
            it.layoutManager = LinearLayoutManager(activity)
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
        viewModel.callTransactionsListGetting((activity as InformationActivity).getCustomerId())
    }

    override fun provideViewModel() = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun showProgress() {
        transactionsProgressBar.visible()
        transactionsRecyclerView.gone()
    }

    override fun hideProgress() {
        transactionsProgressBar.gone()
        transactionsRecyclerView.visible()
    }

    private fun observeViewModel() {
        viewModel.eventGetTransactionList.observe(
            viewLifecycleOwner,
            Observer { it.getContentIfNotHandled()?.let { adapter.transactionList = it } })
    }
}