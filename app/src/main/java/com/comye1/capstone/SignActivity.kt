package com.comye1.capstone

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.comye1.capstone.screens.SplashScreen
import com.comye1.capstone.screens.signin.SignInScreen
import com.comye1.capstone.screens.signup.SignUpScreen
import com.comye1.capstone.ui.ExitDialog
import com.comye1.capstone.ui.theme.CapstoneTheme
import com.comye1.capstone.ui.theme.StatusBarColor
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class SignActivity : ComponentActivity() {

    private lateinit var mainIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainIntent = Intent().setClass(applicationContext, MainActivity::class.java)

        setContent {

            window.statusBarColor = StatusBarColor.toArgb()

            setContent {
                val (exitDialogShown, showExitDialog) = remember {
                    mutableStateOf(false)
                }

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    composable("splash") {
                        SplashScreen {
                            navController.navigate("sign_in") {
                                popUpTo("splash") {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    composable("sign_in") {
//                    requestForegroundPermission(this@MainActivity)
                        SignInScreen(
                            toMain = {
                                startActivity(mainIntent)
                            },
                            toSignUp = {
                                navController.navigate("sign_up")
                            }
                        )
                    }
                    composable("sign_up") {
                        SignUpScreen(
                            toLogIn = {
                                navController.navigate("sign_in") {
                                    popUpTo("sign_up") {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                }
                /*
            뒤로가기 동작 처리
             */
                BackHandler(
                    onBack = {
                        showExitDialog(true)
                    }
                )
                /*
                종료 다이얼로그
                 */
                if (exitDialogShown) {
                    ExitDialog(onDismiss = { showExitDialog(false) }) {
                        finishAffinity()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CapstoneTheme {
        Greeting("Android")
    }
}