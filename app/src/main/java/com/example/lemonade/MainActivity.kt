package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   LemonadeApp()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun LemonadeApp() {
    // Track the current step
    var currentStep by remember { mutableIntStateOf(1) }

    // Track the number of squeezes when at step 2
    var squeezeCount by remember { mutableIntStateOf(0) }

    // Determine the current image and text based on the current step
    val currentImage = when (currentStep) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val currentText = when (currentStep) {
        1 -> R.string.tap_tree
        2 -> R.string.tap_lemon
        3 -> R.string.tap_drink
        else -> R.string.tap_empty
    }

    // Handle the click event to update the current step
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                when (currentStep) {
                    1 -> {
                        // Move to step 2 and randomly set squeezeCount between 2 and 4
                        currentStep = 2
                        squeezeCount = (2..4).random()
                    }
                    2 -> {
                        // Decrease squeezeCount on each click and move to step 3 if it reaches 0
                        squeezeCount--
                        if (squeezeCount == 0) {
                            currentStep = 3
                        }
                    }
                    3 -> {
                        // Move to step 4
                        currentStep = 4
                    }
                    else -> {
                        // Reset to step 1
                        currentStep = 1
                    }
                }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageRender(currentImage)
        Spacer(modifier = Modifier.height(10.dp))
        TextInstruction(currentText)
    }
}

@Composable
fun ImageRender(currentImage: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = currentImage),
        contentDescription = "Lemonade Image",
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(40.dp)) // Round the corners
            .background(Color.Cyan) // Background color with rounded corners
            .border(2.dp, Color.Transparent, RoundedCornerShape(40.dp)) // Transparent border
    )
}

@Composable
fun TextInstruction(currentText: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = currentText),
        fontSize = 18.sp,
        modifier = modifier
    )
}

