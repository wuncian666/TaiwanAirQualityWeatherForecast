package notWorking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentSearchSensorThingsBinding
import com.example.airqualityindex.viewmodels.SearchSensorThingsViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get

class SearchSensorThingsFragment : Fragment() {
    private lateinit var binding: FragmentSearchSensorThingsBinding

    private val viewModel: SearchSensorThingsViewModel = get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchSensorThingsBinding.inflate(inflater, container, false)

        this.requestApi()

        return binding.root
    }

    private fun requestApi() {
        viewModel.getApiResponse()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}