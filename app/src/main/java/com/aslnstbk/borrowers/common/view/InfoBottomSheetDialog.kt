package com.aslnstbk.borrowers.common.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.aslnstbk.borrowers.R
import com.aslnstbk.borrowers.common.data.models.Borrower
import com.aslnstbk.borrowers.main.presentation.viewModel.MainViewModel
import com.aslnstbk.borrowers.utils.CalendarParser
import com.google.android.material.bottomsheet.BottomSheetDialog

class InfoBottomSheetDialog(
    private val mainViewModel: MainViewModel,
    private val calendarParser: CalendarParser
) {

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private lateinit var paidDateView: View
    private lateinit var notPaidDateView: View

    private lateinit var nameTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var paidTextView: TextView
    private lateinit var debtTextView: TextView
    private lateinit var deleteButton: Button
    private lateinit var approveButton: Button

    fun show(
        context: Context,
        borrower: Borrower
    ){
        bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)

        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_info_bottom_sheet, null)
        initViews(
            context = context,
            view = view,
            borrower = borrower
        )
        initListeners(borrower = borrower)

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    private fun initViews(
        context: Context,
        view: View,
        borrower: Borrower
    ){
        paidDateView = view.findViewById(R.id.layout_info_bottom_sheet_dates_off)
        notPaidDateView = view.findViewById(R.id.layout_info_bottom_sheet_dates_on)
        nameTextView = view.findViewById(R.id.layout_info_bottom_sheet_name)
        dateTextView = view.findViewById(R.id.layout_info_bottom_sheet_date)
        paidTextView = view.findViewById(R.id.layout_info_bottom_sheet_paid_date)
        debtTextView = view.findViewById(R.id.layout_info_bottom_sheet_debt)
        deleteButton = view.findViewById(R.id.layout_add_bottom_sheet_button_cancel)
        approveButton = view.findViewById(R.id.layout_add_bottom_sheet_button_add)

        nameTextView.text = borrower.fullName
        dateTextView.text = calendarParser.getParsedDate(borrower.date)
        debtTextView.text = DEBT_TEXT_FORMAT.format(borrower.debt)
        notPaidDateView.visibility = View.GONE

        if(borrower.isPaid){
            paidTextView.text = calendarParser.getParsedDate(borrower.paidDate)
            debtTextView.setTextColor(ContextCompat.getColor(context, R.color.green))
            debtTextView.text = PAID_DEBT_TEXT_FORMAT.format(borrower.debt)
            approveButton.visibility = View.GONE
            notPaidDateView.visibility = View.VISIBLE
        }
    }

    private fun initListeners(borrower: Borrower){
        deleteButton.setOnClickListener {
            mainViewModel.deleteBorrower(borrower)
            bottomSheetDialog.cancel()
        }

        approveButton.setOnClickListener {
            mainViewModel.approveBorrower(borrower)
            bottomSheetDialog.cancel()
        }
    }
}