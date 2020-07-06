package com.example.paydaybanktesttask.ui.information.transaction

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.paydaybanktesttask.R
import com.example.paydaybanktesttask.domain.repository.model.Transaction
import com.example.paydaybanktesttask.util.toUiString
import kotlinx.android.synthetic.main.view_transaction.view.*

class TransactionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_transaction, this)
    }

    fun setTransaction(transaction: Transaction) {
        with(transaction) {
            transactionItemDateTextView.text =
                resources.getString(R.string.transactions_item_date, date.toUiString())

            transactionItemAmountTextView.text =
                resources.getString(R.string.transactions_item_amount, amount)
            transactionItemAmountTextView.setTextAppearance(
                if (amount > 0) R.style.TextApp_Money_Positive else R.style.TextApp_Money_Negative
            )

            transactionItemCircleTextView.text = vendor[0].toString()

            transactionItemVendorTextView.text = vendor

            transactionItemDescriptionTextView.text = resources.getString(
                R.string.transactions_item_description, category, accountId.toString()
            )
        }
    }
}

