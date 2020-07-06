package com.example.paydaybanktesttask.ui.information.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paydaybanktesttask.R
import com.example.paydaybanktesttask.domain.repository.model.Transaction


class TransactionViewAdapter : RecyclerView.Adapter<TransactionViewAdapter.TransactionViewHolder>() {

    var transactionList: List<Transaction> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewAdapter.TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_view_holder, parent, false)

        return TransactionViewHolder(view)      }

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(
        holder: TransactionViewAdapter.TransactionViewHolder,
        position: Int
    ) {
        holder.bind(transactionList[position])
    }

    inner class TransactionViewHolder(private val transactionView: View) : RecyclerView.ViewHolder(transactionView) {

        fun bind(transaction: Transaction) {
            (transactionView as TransactionView).setTransaction(transaction)
        }
    }
}