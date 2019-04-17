package net.subroh0508.otonashi.androidapp.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_city_search_conditions.view.*
import net.subroh0508.otonashi.R
import net.subroh0508.otonashi.androidapp.ui.viewmodel.CityViewModel

class CitySearchConditionsDialog : BottomSheetDialogFragment() {
    companion object {
        const val REQUEST_CODE = 222

        operator fun invoke(target: Fragment): CitySearchConditionsDialog {
            return CitySearchConditionsDialog().also {
                it.setTargetFragment(target, REQUEST_CODE)
            }
        }
    }

    private val cityViewModel: CityViewModel by lazy {
        ViewModelProviders.of(
            targetFragment ?: throw IllegalStateException()
        )[CityViewModel::class.java]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
        = super.onCreateDialog(savedInstanceState).also {
            it.setContentView(bindView())
        }

    private fun bindView(): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_city_search_conditions, null, false)

        return view.apply {
            prefectureName.setText(cityViewModel.prefectureName)
            cityName.setText(cityViewModel.cityName)

            cancel.setOnClickListener { cancel() }
            search.setOnClickListener { search() }
        }
    }

    private fun View.cancel() {
        targetFragment?.onActivityResult(REQUEST_CODE, AppCompatActivity.RESULT_CANCELED, null)
        dismiss()
    }

    private fun View.search() {
        cityViewModel.prefectureName = prefectureName.text.toString()
        cityViewModel.cityName = cityName.text.toString()

        targetFragment?.onActivityResult(REQUEST_CODE, AppCompatActivity.RESULT_OK, null)
        dismiss()
    }
}