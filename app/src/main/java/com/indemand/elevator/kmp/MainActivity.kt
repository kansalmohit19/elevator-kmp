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
import androidx.compose.ui.graphics.Color
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
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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
        text = "Elevator Algorithm",
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun ElevatorStateView(elevators: List<Elevator>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        elevators.forEach { elevator ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = elevator.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(90.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = elevator.level.toString() +
                                if (elevator.isMoving)
                                    if (elevator.isGoingUp) " ↑" else " ↓"
                                else "",
                        style = MaterialTheme.typography.headlineMedium,
                        color = if (elevator.isMoving)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun ElevatorButtonView(
    buttons: List<List<Int>>,
    onButtonClick: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxWidth()
    ) {
        buttons.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth()
            ) {
                row.forEach { item ->
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable { onButtonClick(item) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
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