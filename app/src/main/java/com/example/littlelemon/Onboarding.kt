package com.example.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.PrimaryGreen

@Composable
fun Onboarding(context: Context, navHostController: NavHostController) {
    val  sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    val firstName = remember {
        mutableStateOf("")
    }

    val lastName = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    val imeState = ImeState()
    val scrollState = rememberScrollState()


    LaunchedEffect(key1 = imeState.value){
        if (imeState.value){
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    fun validateRegData(firstName:String, lastName: String, email: String): Boolean{
        var validated = false

        if(firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()){
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                validated = true
        }

        return validated
    }


    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Row(Modifier.fillMaxWidth(0.6f)) {
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo")
        }
        Row(modifier = Modifier
            .height(150.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Let's get to know you",
                style = MaterialTheme.typography.h1,
                color = PrimaryGreen)
        }

        Text(text = "Personal Information",
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h3)
        OutlinedTextField(
            value = firstName.value,
            onValueChange ={
                firstName.value = it
            },
            label = { Text(text = "First Name")},
            singleLine = true,
            placeholder = { Text(text = "Mengnan")},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth())

        OutlinedTextField(
            value = lastName.value,
            onValueChange ={
                lastName.value = it
            },
            label = { Text(text = "Last Name")},
            singleLine = true,
            placeholder = { Text(text = "Xu")},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth())

        OutlinedTextField(
            value = email.value,
            onValueChange ={
                email.value = it
            },
            label = { Text(text = "Email")},
            singleLine = true,
            placeholder = { Text(text = "mnxu@gmail.com")},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.size(40.dp))

        Button(onClick = {
            if (validateRegData(
                    firstName.value,
                    lastName.value,
                    email.value)){
                sharedPreferences.edit()
                    .putString("firstName", firstName.value)
                    .putString("lastName", lastName.value)
                    .putString("email", email.value)
                    .putBoolean("userRegistered", true)
                    .apply()

                Toast.makeText(context,
                    "Registration Successful",
                    Toast.LENGTH_SHORT)
                    .show()


                navHostController.navigate(Home.route){
                    popUpTo(Onboarding.route){inclusive = true}
                    launchSingleTop = true
                }

            }
            else{
                Toast.makeText(context,
                    "Invalid Details, Please try again",
                    Toast.LENGTH_SHORT)
                    .show()
            }

        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
            Text(text = "Register")
        }
    }



}