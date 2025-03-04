package com.example.segurapp.Login.ui

import android.preference.PreferenceActivity.Header
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.segurapp.R

@Composable
fun loginScreen(){
    Box(
        Modifier.fillMaxSize().padding(16.dp)){
        Login(Modifier.align(alignment = Alignment.Center))
    }
}

@Composable
fun Login(modifier: Modifier){
    Column (modifier = Modifier){
        headerImage()
        Spacer(Modifier.padding(16.dp))
        emailFiel()
    }
}

@Composable
fun headerImage() {
    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Header ")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun emailFiel() {
    TextField(value = "" , onValueChange ={},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        //KeyboardOptions = KeyboardOptions(KeyboardType = KeyboardType.Email),
        //singleLine = true,
        //maxline = 1,
        //colors = textFieldColors(textColor = Color(0xfffffff), background)

    )
}

