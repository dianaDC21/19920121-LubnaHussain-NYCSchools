package com.example.nycschool.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nycschool.MainActivity
import com.example.nycschool.R
import com.example.nycschool.adapter.SchoolAdapter
import com.example.nycschool.databinding.FragmentNYCSchoolBinding
import com.example.nycschool.viewmodel.NYCSchoolViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [NYCSchoolFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NYCSchoolFragment : Fragment() {
    lateinit var viewModel: NYCSchoolViewModel
    lateinit var binding: FragmentNYCSchoolBinding
    private var schoolAdapter: SchoolAdapter = SchoolAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_n_y_c_school, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()

        viewModel.schoolData.observe(this) {
            removeLoading()
            schoolAdapter.setMovieList(it)
        }
    }

    private fun removeLoading() {
        binding.loadingText.visibility = View.GONE
    }

    private fun setUpRecyclerView(/*list: List<NYCSchoolResponseItem>*/) {
        binding.nycSchoolList.adapter = schoolAdapter
        binding.nycSchoolList.layoutManager = LinearLayoutManager(this.context)
        binding.nycSchoolList.setHasFixedSize(true)
    }
}
