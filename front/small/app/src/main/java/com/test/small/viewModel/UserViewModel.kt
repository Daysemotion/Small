package com.test.small.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.small.repository.UserRepository
import com.test.small.repository.userInfoItem
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlinx.coroutines.delay

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _userList = MutableLiveData<List<userInfoItem>>() //UI에 나오는 리스트
    val userList: LiveData<List<userInfoItem>> get() = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoding: LiveData<Boolean> get() = _isLoading


    private var currentPage = 1 //현재 페이지
    private val pageSize = 20 //한번에 10개씩
    private var allUser = mutableListOf<userInfoItem>()//전체 데이터 저장

    private val _postResult = MutableLiveData<String>()
    val postResult: LiveData<String> get() = _postResult

    //private var currentList = mutableListOf<userInfoItem>()

    fun fetchAllData(){
        if(_isLoading.value == true) return //로딩중 중복 요청 방지

        _isLoading.value = true
        viewModelScope.launch {
            try {

                val response: Response<List<userInfoItem>> = repository.getUserData(currentPage, pageSize)
                if(response.isSuccessful){
                    val newList = response.body()?: emptyList()

                    if(newList.isNotEmpty()){
                        allUser.addAll(newList) //기존 리스트 추가
                        _userList.value = allUser
                        currentPage++ //다음 페이지 요청
                    }
                }
            }catch (e: Exception){
                //네트워크 에러 처리
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun postUserData(input: HashMap<String, Any>){
        viewModelScope.launch {
            try {
                val response: Response<userInfoItem> = repository.postUserData(input)
                if(response.isSuccessful){
                    _postResult.value = "Post 설공"
                } else{
                    _postResult.value = "Post 실패: ${response.code()}"
                }
            }catch (e: Exception){
                _postResult.value = "Post 실패: ${e.message}"
            }
        }
    }
}