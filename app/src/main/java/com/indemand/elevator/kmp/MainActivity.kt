package com.indemand.elevator.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.indemand.elevator.kmp.ui.theme.ElevatorkmpTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.example.Elevator
import org.example.ElevatorUseCase

class MainActivity : ComponentActivity() {

    val elevatorUseCase by lazy {
        ElevatorUseCase(CoroutineScope(Dispatchers.IO), 4, 11)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElevatorkmpTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ElevatorScreen(innerPadding, elevatorUseCase)
                }
            }
        }
    }
}

@Composable
fun ElevatorScreen(paddingValues: PaddingValues, elevatorUseCase: ElevatorUseCase) {
    val elevators by elevatorUseCase.elevators.collectAsState()
    val buttons by elevatorUseCase.buttons.collectAsState()

    Column(modifier = Modifier.background(Color.Black)) {
        HeaderView(modifier = Modifier.padding(paddingValues))
        ElevatorStateView(elevators)
        ElevatorButtonView(buttons) { floor ->
            elevatorUseCase.handleInput(floor)
        }
    }
}


@Composable
fun HeaderView(modifier: Modifier = Modifier) {
    Text(
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = 24.sp),
        text = "Elevator Algorithm",
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
    )
}

@Composable
fun ElevatorStateView(elevators: List<Elevator>) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(
            space = 6.dp, alignment = Alignment.CenterHorizontally
        )
    ) {
        elevators.forEach { elevator ->
            //Log.d("Elevator")
            Column {
                Text(
                    modifier = Modifier.width(80.dp),
                    text = elevator.displayName,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                )
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(10))
                        .background(Color.LightGray), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = elevator.level.toString() + if (elevator.isMoving) if (elevator.isGoingUp) "↑" else "↓" else "",
                        style = TextStyle(fontSize = 24.sp, color = Color.White),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
fun ElevatorButtonView(
    buttons: List<List<Int>>, onButtonClick: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 6.dp),
        modifier = Modifier.padding(top = 50.dp)
    ) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(
                    space = 6.dp, alignment = Alignment.CenterHorizontally
                )
            ) {
                row.forEach { item ->
                    Box(
                        modifier = Modifier
                            .clickable { onButtonClick(item) }
                            .size(40.dp)
                            .clip(RoundedCornerShape(10))
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center) {
                        Text(
                            text = item.toString(),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SanityPreview() {

}