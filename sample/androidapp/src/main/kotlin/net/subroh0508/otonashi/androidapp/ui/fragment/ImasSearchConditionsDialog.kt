package net.subroh0508.otonashi.androidapp.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_imas_search_conditions.view.*
import net.subroh0508.otonashi.R
import net.subroh0508.otonashi.androidapp.ui.viewmodel.ImasViewModel

class ImasSearchConditionsDialog : BottomSheetDialogFragment() {
    companion object {
        const val REQUEST_CODE = 111

        operator fun invoke(target: Fragment): ImasSearchConditionsDialog {
            return ImasSearchConditionsDialog().also {
                it.setTargetFragment(target, REQUEST_CODE)
            }
        }
    }

    private val imasViewModel: ImasViewModel by lazy {
        ViewModelProviders.of(
            targetFragment ?: throw IllegalStateException()
        )[ImasViewModel::class.java]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
        = super.onCreateDialog(savedInstanceState).also {
            it.setContentView(bindView())
        }

    private fun bindView(): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_imas_search_conditions, null, false)
        val contents = imasViewModel.contents
        val idolName = imasViewModel.idolName

        return view.apply {
            check765.isChecked = contents.contains(check765.text.toString())
            checkCinderella.isChecked = contents.contains(checkCinderella.text.toString())
            checkMillion.isChecked = contents.contains(checkMillion.text.toString())
            checkShiny.isChecked = contents.contains(checkShiny.text.toString())
            check315.isChecked = contents.contains(check315.text.toString())
            checkStaff.isChecked = contents.contains(checkStaff.text.toString())
            checkOthers.isChecked = contents.contains(checkOthers.text.toString())

            name.setText(idolName)

            cancel.setOnClickListener { cancel() }
            search.setOnClickListener { search() }
        }
    }

    private fun View.cancel() {
        targetFragment?.onActivityResult(REQUEST_CODE, AppCompatActivity.RESULT_CANCELED, null)
        dismiss()
    }

    private fun View.search() {
        imasViewModel.contents = listOf(
            check765, checkCinderella, checkMillion, checkShiny,
            check315, checkStaff, checkOthers
        ).filter(CheckBox::isChecked).map { it.text.toString() }
        imasViewModel.idolName = name.text.toString()

        targetFragment?.onActivityResult(REQUEST_CODE, AppCompatActivity.RESULT_OK, null)
        dismiss()
    }
}