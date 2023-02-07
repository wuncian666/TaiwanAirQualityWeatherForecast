package notWorking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.airqualityindex.databinding.FragmentPerHourAQIDialogBinding

class PerHourAQIDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentPerHourAQIDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        this.binding = DataBindingUtil.inflate(
//            inflater,
//            R.layout.fragment_per_hour_a_q_i_dialog,
//            container,
//            false
//        )
//
//        val args: PerHourAQIDialogFragmentArgs by navArgs()
//        binding.textAqiResult.text = args.perHourRecord.aqi
//        binding.textStatusResult.text = args.perHourRecord.status
//        binding.textCountyResult.text = args.perHourRecord.county
//        binding.textPollutantResult.text = args.perHourRecord.pollutant
//        binding.textPm25Result.text = args.perHourRecord.pm25
//
//        binding.btnBack.setOnClickListener { dismiss() }

        return this.binding.root
    }

//    @SuppressLint("UseGetLayoutInflater")
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val inflater = requireActivity().layoutInflater
//
//        this.binding = DataBindingUtil.inflate(
//            inflater,
//            R.layout.fragment_per_hour_a_q_i_dialog,
//            null,
//            false
//        )
//
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//
//            builder.setView(binding.root)
//
//            //val args: PerHourAQIDialogFragmentArgs by navArgs()
//            binding.textAqiResult.text = args.perHourRecord.aqi
//            binding.textStatusResult.text = args.perHourRecord.status
//            binding.textCountyResult.text = args.perHourRecord.county
//
//            if (args.perHourRecord.pollutant == "") {
//                binding.textPollutantResult.text = getString(R.string.none)
//            } else {
//                binding.textPollutantResult.text = args.perHourRecord.pollutant
//            }
//
//            binding.textPm25Result.text = args.perHourRecord.pm25
//            binding.textSiteName.text = args.perHourRecord.siteName
//
//            binding.btnBack.setOnClickListener { dismiss() }
//
//            builder.create()
//        } ?: throw IllegalAccessException("Activity cannot be null")
//    }
}