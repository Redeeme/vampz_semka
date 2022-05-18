package com.example.myapplication.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.profile.orders.OrderModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(private val db: FirebaseFirestore) : ViewModel() {

    private var profileData: MutableLiveData<ArrayList<OrderModelClass>> = MutableLiveData()
    val _profileData: LiveData<ArrayList<OrderModelClass>>
        get() = profileData

    suspend fun update() {
        profileData.value = (
                ArrayList(db.collection("${FirebaseAuth.getInstance().currentUser!!.uid}+")
                    .orderBy("date", Query.Direction.DESCENDING).get().await()
                    .documents.mapNotNull {
                        it.toObject(OrderModelClass::class.java)
                    })
                )
    }
}