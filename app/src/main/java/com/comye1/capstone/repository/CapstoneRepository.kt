package com.comye1.capstone.repository

import android.content.Context
import com.comye1.capstone.network.CapstoneApi
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CapstoneRepository @Inject constructor(
    api: CapstoneApi, context: Context
){

}