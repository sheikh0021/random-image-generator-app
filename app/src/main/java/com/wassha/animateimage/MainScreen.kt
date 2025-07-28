package com.wassha.animateimage



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun MainScreen() {

    val animationOptions = listOf(
        "astronaut.json",
        "cat.json",
        "rocket.json",
        "star.json"

    )

    //random picked animation
    var selectedFile by remember { mutableStateOf(animationOptions.random()) }
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("lottie/$selectedFile"))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Imagify", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(280.dp)

        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            //Pick a random animation
            val next = animationOptions.random()
            selectedFile = next
        }) {
            Text("Generate your random Image")
        }

    }

}
