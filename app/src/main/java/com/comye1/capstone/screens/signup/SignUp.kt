package com.comye1.capstone.screens.signup

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.comye1.capstone.ui.theme.CapstoneTheme
import kotlinx.coroutines.flow.collect

@Composable
fun SignUpScreen(
    toLogIn: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val subNavController = rememberNavController()
    val context = LocalContext.current

    CapstoneTheme {
        Box() {
            NavHost(navController = subNavController, startDestination = "email") {
                composable("email") {
                    SignUpContainer {
                        SignUpIdScreen(viewModel) {
                            // next
                            subNavController.navigate("user_name")
                        }
                    }
                }

                composable("user_name") {
                    SignUpContainer {
                        SignUpUserNameScreen(
                            userName = viewModel.userName,
                            setUserName = { viewModel.userName = it },
                        ) {
                            subNavController.navigate("password")
                        }
                    }
                }

                composable("password") {
                    SignUpContainer {
                        SignUpPasswordScreen(
                            password = viewModel.password,
                            setPassword = { viewModel.password = it }
                        ) {
                            // create
                            viewModel.signUp()
                        }
                    }
                    if (viewModel.signUpResult) {
                        toLogIn()
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { toLogIn() }) {
                    Text(text = "취소", style = MaterialTheme.typography.subtitle1)
                }
            }
        }


    }

    LaunchedEffect(true) {
        viewModel.messageFlow.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun SignUpContainer(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(.8f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
//            LogoSection()
//            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "회원가입",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h3
            )

            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}