package com.test.small.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.small.ListAdapter
import com.test.small.R
import com.test.small.viewModel.UserViewModel

class MainFragment: Fragment() {
    private lateinit var navController: NavController
    private lateinit var et_Get : EditText
    private lateinit var btn_Get : Button
    private lateinit var btn_Post : Button
    private lateinit var btn_Next : Button
    private lateinit var recycler_View_Getlist : RecyclerView
    private lateinit var progressBar : ProgressBar

    private val viewModel: UserViewModel by viewModels()
    private val listAdapter = ListAdapter(arrayListOf())

    private var isLoading = false //로딩상태확인
    private var currentPage = 1 //현재 페이지


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        et_Get = view.findViewById(R.id.et_get)
        btn_Get = view.findViewById(R.id.btn_get)
        btn_Post = view.findViewById(R.id.btn_post)
        btn_Next = view.findViewById(R.id.btn_next)
        recycler_View_Getlist = view.findViewById(R.id.recycler_view_getlist)
        progressBar = view.findViewById(R.id.progress_bar)

        recycler_View_Getlist.layoutManager = LinearLayoutManager(requireContext())
        recycler_View_Getlist.adapter = listAdapter


        //인피니티 스크롤 리스너
        recycler_View_Getlist.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recycler_View_Getlist.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                //마지막 항목에 도달하면 추가 데이터 로드
                if(!viewModel.isLoding.value!! && lastVisibleItemPosition >= totalItemCount - 1){
                    viewModel.fetchAllData()
                }
            }
        })

        //데이터 관찰 및 UI업데이트
        viewModel.userList.observe(viewLifecycleOwner){ users ->
            listAdapter.updateList(users)
        }

        viewModel.isLoding.observe(viewLifecycleOwner){ loading ->
            progressBar.visibility = if(loading) View.VISIBLE else View.GONE
        }

        viewModel.postResult.observe(viewLifecycleOwner){ result ->
            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
        }

        btn_Get.setOnClickListener {
            /*val page = et_Get.text.toString().toIntOrNull()
            if(page != null){
                viewModel.fetchUserData(page)
            } else{
                tv_State.text = "올바른 숫자를 입력하세요."
            }*/
            viewModel.fetchAllData()
        }

        btn_Post.setOnClickListener {
            val input = HashMap<String, Any>().apply {
                put("id", "11")
                put("phone", "1123123123")
                put("email", "1wkdjv@cv.dd")
            }
            viewModel.postUserData(input)
        }

        btn_Next.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_myPageFragment)
        }
    }
}
