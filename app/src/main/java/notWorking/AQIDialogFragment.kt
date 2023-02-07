package notWorking

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.airqualityindex.databinding.DialogFragmentAQIDetailBinding

class AQIDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentAQIDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//
//        this.binding = DialogFragmentAQIDetailBinding.inflate(LayoutInflater.from(context))
//
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//
//            builder.setView(binding.root)
//
//            binding.textCity.text = arguments?.getString("name")
//            binding.textAqi.text = arguments?.getString("aqi")
//            binding.valueSo2.text = arguments?.getString("so2")
//            binding.valueCo.text = arguments?.getString("co")
//            binding.valuePm10.text = arguments?.getString("pm10")
//            binding.valueNo2.text = arguments?.getString("no2")
//            binding.valueO38.text = arguments?.getString("o38")
//            binding.valuePm25.text = arguments?.getString("pm25")
//
//            binding.btnBack.setOnClickListener { dismiss() }
//
//            builder.create()
//        } ?: throw IllegalAccessException("Activity cannot be null")
//    }
}