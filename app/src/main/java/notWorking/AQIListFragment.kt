package notWorking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.airqualityindex.adapters.AQIListAdapter
import com.example.airqualityindex.databinding.FragmentAQIListBinding
import com.example.airqualityindex.model.DailyRecord
import com.example.airqualityindex.viewmodels.AQIListViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get
import java.text.SimpleDateFormat
import java.util.*

class AQIListFragment : Fragment(), AQIListAdapter.OnItemClickListener {
    private val TAG = AQIListFragment::class.java.simpleName

    private val aqiListViewModel: AQIListViewModel = get()

    private lateinit var binding: FragmentAQIListBinding

    private lateinit var data: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentAQIListBinding.inflate(inflater, container, false)

        this.binding.back.setOnClickListener {
            Log.d(TAG, "onCreateView: click")
            findNavController().navigateUp()
        }

        this.requestApi()

        aqiListViewModel.getCity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    data = it
                    setRecyclerView(it)
                },
                { Log.e(TAG, "get city: " + it.message) }
            )

        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
    }

    private fun setRecyclerView(data: List<String>) {
        val adapter = AQIListAdapter(data, this)
        this.binding.recyclerView.adapter = adapter
        this.binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        this.binding.recyclerView.itemAnimator = null
        this.binding.recyclerView.setHasFixedSize(true)
    }

    private fun requestApi() {
        aqiListViewModel.getApiResponse()
            .concatMap {
                aqiListViewModel.insertDataInDatabase(it)
                Observable.just(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "onCreateView: next") },
                { Log.e(TAG, "onCreateView: " + it.message) })
    }

    override fun onItemClick(position: Int) {
        val yesterdayDate = getYesterdayDate()

        aqiListViewModel.getRecordByCityAndDate(data[position], yesterdayDate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { showDialog(it) },
                { Log.e(TAG, "onItemClick: " + it.message) }
            )
    }

    private fun showDialog(record: DailyRecord) {
        val dialog = AQIDialogFragment()
        Log.d(TAG, "showDialog: $record")

        val args = Bundle()
        args.putString("name", record.name)
        args.putString("aqi", record.aqi)
        args.putString("so2", record.so2)
        args.putString("co", record.co)
        args.putString("pm10", record.pm10)
        args.putString("no2", record.no2)
        args.putString("o38", record.o38)
        args.putString("pm25", record.pm25)
        dialog.arguments = args
        dialog.show(childFragmentManager, "my_dialog")
    }

    private fun getYesterdayDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        return dateFormat.format(calendar.time)
    }
}