package com.kingof9x.lomocam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kingof9x.lomocam.ui.theme.LomoCamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LomoCamTheme {
                PlaceholderScreen(version = BuildInfo.versionLabel(packageManager, packageName))
            }
        }
    }
}

@Composable
fun PlaceholderScreen(version: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("LomoCam", style = MaterialTheme.typography.displayMedium, color = Color(0xFFF2E6D0))
        Text(version, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF9A9A9A))
    }
}

@Preview
@Composable
private fun PlaceholderPreview() {
    LomoCamTheme { PlaceholderScreen(version = "v0.1.1") }
}
