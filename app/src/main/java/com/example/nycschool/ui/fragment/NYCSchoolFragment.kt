package com.example.nycschool.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nycschool.ui.MainActivity
import com.example.nycschool.R
import com.example.nycschool.ui.adapter.SchoolAdapter
import com.example.nycschool.databinding.FragmentNYCSchoolBinding
import com.example.nycschool.model.NYCSchoolResponseItem
import com.example.nycschool.utils.ApiStatus
import com.example.nycschool.utils.Constants.Companion.DB_REFERENCE
import com.example.nycschool.utils.Constants.Companion.SCHOOL_ADDRESS_LINE
import com.example.nycschool.utils.Constants.Companion.SCHOOL_CITY
import com.example.nycschool.utils.Constants.Companion.SCHOOL_EMAIL
import com.example.nycschool.utils.Constants.Companion.SCHOOL_LATITUDE
import com.example.nycschool.utils.Constants.Companion.SCHOOL_LONGITUDE
import com.example.nycschool.utils.Constants.Companion.SCHOOL_NAME
import com.example.nycschool.utils.Constants.Companion.SCHOOL_PHONE_NUM
import com.example.nycschool.utils.Constants.Companion.SCHOOL_WEBSITE
import com.example.nycschool.viewmodel.NYCSchoolViewModel
import java.util.*

/**
 * Home fragment to display the list of NYC Schools
 */
class NYCSchoolFragment : Fragment(), SchoolAdapter.OnSchoolItemClickListener {

    private lateinit var responseList: List<NYCSchoolResponseItem>
    private var filteredList: MutableList<NYCSchoolResponseItem> =
        emptyList<NYCSchoolResponseItem>().toMutableList()
    lateinit var viewModel: NYCSchoolViewModel
    private lateinit var binding: FragmentNYCSchoolBinding
    private var schoolAdapter: SchoolAdapter = SchoolAdapter(this)
    lateinit var dialog: AlertDialog
    private var search: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_n_y_c_school, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        showLoading()
        setUpRecyclerView()

        /**
         * View model call to fetch list of schools and populate in recycler view,
         * show loading alert dialog till response is captured for this call
         */
        viewModel.getNYCSchools()
        viewModel.schoolData.observe(this) { response ->
            dialog.dismiss()
            handleResponse(response.status)
            if (response?.isSuccess() == true && !response.apiResponse.isNullOrEmpty()) {
                responseList = response.apiResponse
                val schoolList = mutableListOf<String>()
                for (item in responseList) {
                    schoolList.add(item.school_name)
                }
                schoolList.sort()
                Log.d("SChool", "school list $schoolList")
                schoolAdapter.setSchoolList(responseList)
            }
        }
    }

    /**
     * method to show loading alert dialog till response is captured for api call
     */
    private fun showLoading() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
        val inflater: LayoutInflater? = activity?.layoutInflater
        builder.setView(inflater!!.inflate(R.layout.loading_dialog, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    /**
     * method to handle api response based on status & update ui
     */
    private fun handleResponse(status: ApiStatus) {
        when (status) {
            ApiStatus.SUCCESS -> {
                binding.noListText.visibility = View.GONE
                binding.searchEditText.visibility = View.VISIBLE
                listenSearchEditText()
            }
            ApiStatus.FAILURE,
            ApiStatus.EXCEPTION -> {
                binding.noListText.visibility = View.VISIBLE
                binding.noListText.text = getString(R.string.school_detail_error_msg)
            }
        }

    }

    /**
     * method to listen to editText/ search box changes
     */
    private fun listenSearchEditText() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchItem = text.toString().lowercase(Locale.getDefault())
                filterListWithSearchItem(searchItem)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    /**
     * method to filter search box entry from the api response and updates recycler view with
     * filtered list
     */
    private fun filterListWithSearchItem(searchItem: String) {
        if (searchItem.isNotEmpty()) {
            filteredList.clear()
            for (i in responseList.indices) {
                if (responseList[i].school_name.lowercase(Locale.getDefault())
                        .contains(searchItem)
                ) {
                    filteredList.add(responseList[i])
                    schoolAdapter.setSchoolList(filteredList)
                    search = true
                }
            }
        } else search = false

    }

    /**
     * method to set up recycler view
     */
    private fun setUpRecyclerView() {
        binding.nycSchoolList.adapter = schoolAdapter
        binding.nycSchoolList.layoutManager = LinearLayoutManager(this.context)
        binding.nycSchoolList.setHasFixedSize(true)
    }

    /**
     * method to handle click of recycler view items, launch corresponding school detail page
     */
    override fun onSchoolItemClick(position: Int) {
        val list = if (search) filteredList else responseList
        val bundle = Bundle()
        bundle.putString(DB_REFERENCE, list[position].dbn)
        bundle.putString(SCHOOL_NAME, list[position].school_name)
        bundle.putString(SCHOOL_ADDRESS_LINE, list[position].primary_address_line_1)
        bundle.putString(SCHOOL_CITY, list[position].city)
        bundle.putString(SCHOOL_PHONE_NUM, list[position].phone_number)
        bundle.putString(SCHOOL_EMAIL, list[position].school_email)
        bundle.putString(SCHOOL_WEBSITE, list[position].website)
        bundle.putString(SCHOOL_LATITUDE, list[position].latitude)
        bundle.putString(SCHOOL_LONGITUDE, list[position].longitude)
        findNavController().navigate(R.id.action_NYCSchoolFragment_to_NYCSchoolDetail, bundle)
    }

    override fun onPause() {
        super.onPause()
        binding.searchEditText.text.clear()
    }
}
