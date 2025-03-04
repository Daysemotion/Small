package com.test.small.Fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.small.Api.ApiInterface
import com.test.small.Api.RetrofitInstance
import com.test.small.ListAdapter
import com.test.small.R
import com.test.small.userInfoItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment: Fragment() {
    private lateinit var navController: NavController
    private lateinit var et_Get : EditText
    private lateinit var btn_Get : Button
    private lateinit var btn_Post : Button
    private lateinit var btn_Next : Button
    private lateinit var tv_State : TextView
    private lateinit var recycler_View_Getlist : RecyclerView

    private val userList = arrayListOf<userInfoItem>()
    val listAdapter = ListAdapter(userList)
    var input = HashMap<String, Any>()


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
        tv_State = view.findViewById(R.id.tv_state)
        recycler_View_Getlist = view.findViewById(R.id.recycler_view_getlist)

        recycler_View_Getlist.layoutManager = LinearLayoutManager(requireContext())
        recycler_View_Getlist.adapter = listAdapter

        btn_Get.setOnClickListener {
            retrofit_get()
        }

        btn_Post.setOnClickListener {
            retrofit_post()
        }

        btn_Next.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_myPageFragment)
        }
    }


    private fun retrofit_get() {
        val retrofitInstance = RetrofitInstance.getInstance().create(ApiInterface::class.java)
        retrofitInstance.getUserData(Integer.parseInt(et_Get.text.toString())).enqueue(object :
            Callback<List<userInfoItem>> {
            override fun onResponse(
                p0: Call<List<userInfoItem>>,
                p1: Response<List<userInfoItem>>
            ) {
                if (p1.isSuccessful) {
                    val users = p1.body() ?: emptyList()
                    userList.clear()
                    userList.addAll(users)
                    listAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(p0: Call<List<userInfoItem>>, p1: Throwable) {
                tv_State.text = "실패했습니다."
            }
        })
    }

    private fun retrofit_post(){
        val retrofitInstance = RetrofitInstance.getInstance().create(ApiInterface::class.java)
        retrofitInstance.getPostList(input).enqueue(object : Callback<userInfoItem>{
            override fun onResponse(p0: Call<userInfoItem>, p1: Response<userInfoItem>) {
                Log.d(TAG, "post ok")
                Log.d(TAG, "${p1.body()}")
                tv_State.text="post 성공"

            }

            override fun onFailure(p0: Call<userInfoItem>, p1: Throwable) {
                tv_State.text="post 실패"
            }
        })
    }
}
