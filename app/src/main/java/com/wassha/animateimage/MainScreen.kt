package com.wassha.animateimage



import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import android.net.Uri



@Composable
fun MainScreen() {
    val context= LocalContext.current

    val animationOptions = listOf(
        "astronaut.json",
        "cat.json",
        "rocket.json",
        "star.json"

    )
    var selectedAsset by remember { mutableStateOf(animationOptions.random()) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }

    //Launcher to pick a json file
    val filePickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        uri ->
        uri.let{
            selectedUri = it
        }
    }

    //Load the appropriate LottieComposition
val composition by rememberLottieComposition(
    spec = selectedUri?.let {
        val inputStream = context.contentResolver.openInputStream(it)
        val json = inputStream?.bufferedReader()?.use { reader -> reader.readText() }
        inputStream?.close()
        if (json !=null) LottieCompositionSpec.JsonString(json)
        else LottieCompositionSpec.Asset("lottie/$selectedAsset")

    }?: LottieCompositionSpec.Asset("lottie/$selectedAsset")
)

    val progress by animateLottieCompositionAsState(composition = composition,
        iterations = LottieConstants.IterateForever)





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

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            selectedUri = null //clear custom file
            selectedAsset = animationOptions.random()
        }) {
            Text("Generate your random Image")
        }
        Spacer(modifier = Modifier.height(16.dp))

        //Button to choose user's own lottie file
        Button(onClick = {
            filePickerLauncher.launch("application/json")
        }) {
            Text("Select Your Own Lottie File")
        }


    }

}
