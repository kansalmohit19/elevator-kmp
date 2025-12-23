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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.indemand.elevator.kmp.ui.theme.ElevatorkmpTheme
import com.indemand.elevator.kmp.ui.theme.LocalAppColors
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
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
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
    val queueCount by elevatorUseCase.queue.collectAsState()

    val appColors = LocalAppColors.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = appColors.background)
    ) {
        HeaderView(modifier = Modifier.padding(paddingValues))
        ElevatorStateView(elevators)
        QueueView(modifier = Modifier.padding(paddingValues), queueCount.size)
        ElevatorButtonView(buttons) { floor ->
            elevatorUseCase.handleInput(floor)
        }
    }
}


@Composable
fun HeaderView(modifier: Modifier = Modifier) {
    val appColors = LocalAppColors.current
    Text(
        text = "Elevator Algorithm",
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Medium,
            color = appColors.title,
            textDecoration = TextDecoration.Underline
        ),
    )
}

@Composable
fun ElevatorStateView(elevators: List<Elevator>) {
    val appColors = LocalAppColors.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        elevators.forEach { elevator ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = elevator.displayName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = appColors.elevatorLabelColor
                )

                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(appColors.elevatorBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = elevator.level.toString() + if (elevator.isMoving) if (elevator.isGoingUp) " ↑" else " ↓"
                        else "",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = when (elevator.isMoving) {
                            false -> appColors.elevatorIdle
                            else -> if (elevator.isGoingUp) {
                                appColors.elevatorMovingUp
                            } else {
                                appColors.elevatorMovingDown
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun QueueView(modifier: Modifier = Modifier, count: Int) {
    val appColors = LocalAppColors.current
    Text(
        text = "Queue: $count",
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Normal,
            color = appColors.title
        ),
    )
}

@Composable
fun ElevatorButtonView(
    buttons: List<List<Int>>, onButtonClick: (Int) -> Unit
) {
    val appColors = LocalAppColors.current
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        buttons.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth(),
            ) {
                row.forEach { item ->
                    Box(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(60.dp)
                            //.clip(RoundedCornerShape(12.dp))
                            .background(appColors.buttonDefault)
                            .clickable {
                                onButtonClick(item)
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            color = appColors.buttonTextColor
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
    val elevators = listOf(
        Elevator(1, "A", level = 2),
        Elevator(2, "B", level = 5),
        Elevator(3, "C", level = 1),
        Elevator(4, "D", level = 1),
    )

    val buttons = listOf(
        listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9)
    )

    ElevatorkmpTheme {
        val appColors = LocalAppColors.current
        Scaffold(
            containerColor = appColors.background, modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                HeaderView(modifier = Modifier.padding(paddingValues))
                ElevatorStateView(elevators)
                QueueView(modifier = Modifier.padding(paddingValues), 1)
                ElevatorButtonView(buttons) { floor ->
                    //no op
                }
            }
        }
    }
}