package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.game.db.GameState
import com.example.game.viewmodel.GameViewModel
import com.example.game.ui.*
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun <T> kotlinx.coroutines.flow.StateFlow<T>.collectAsStateWithLifecycle(): androidx.compose.runtime.State<T> {
    return this.collectAsState()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val gameViewModel: GameViewModel = viewModel()
                QuantumEffectGameApp(gameViewModel)
            }
        }
    }
}

data class NavigationItem(val label: String, val iconSymbol: String)

@Composable
fun QuantumEffectGameApp(viewModel: GameViewModel = viewModel()) {
    val gameState by viewModel.gameStateFlow.collectAsStateWithLifecycle()
    var calibrationComplete by remember { mutableStateOf(false) }

    if (!calibrationComplete) {
        StartupCalibrationScreen(onComplete = {
            calibrationComplete = true
        })
    } else {
        GameInterfaceDashboard(viewModel, gameState)
    }
}

@Composable
fun StartupCalibrationScreen(onComplete: () -> Unit) {
    var logsText by remember { mutableStateOf("CONNECTING SIGNAL RECEPTOR...") }

    LaunchedEffect(Unit) {
        delay(600)
        logsText += "\nSECURE HANDSHAKE NOMINAL [UTC 2081]"
        delay(600)
        logsText += "\nLOADING SOLIS REGIONAL GEOMETRIC ARRAYS..."
        delay(600)
        logsText += "\nBIOMECHANICAL CORES PRE-CHARGED: 100%"
        delay(600)
        logsText += "\nDEUS EX AI STORYTELLER ALIGNED..."
        delay(500)
        logsText += "\n\nSYSTEM CALIBRATION READY. INITIALIZE LINK."
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(QuantumDarkBg)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "QUANTUM EFFECT",
                color = QuantumNeonPurple,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "FIRST EARTH // SECURE PARALLEL COORDS",
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp),
                color = QuantumNeonBlue,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.5f))
                    .border(1.dp, QuantumBorder, RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = logsText,
                    color = QuantumTerminalGreen,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            com.example.game.ui.CyberButton(
                onClick = onComplete,
                modifier = Modifier.fillMaxWidth(0.8f),
                text = "Initialize Cognitive Link"
            )
        }
    }
}

@Composable
fun GameInterfaceDashboard(viewModel: GameViewModel, gameState: GameState) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideScreen = maxWidth > 600.dp
        var selectedTab by remember { mutableIntStateOf(0) }

        Scaffold(
            topBar = {
                TopAppBarHeader(gameState)
            },
            bottomBar = {
                if (!isWideScreen) {
                    BottomNavigationBarHUD(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it }
                    )
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (isWideScreen) {
                    NavigationRailHUD(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it }
                    )
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    when (selectedTab) {
                        0 -> ExploreScreen(
                            viewModel = viewModel,
                            gameState = gameState,
                            onNavigateToCombat = { selectedTab = 4 }
                        )
                        1 -> AugmentScreen(viewModel, gameState)
                        2 -> FactionsScreen(viewModel, gameState)
                        3 -> CompanionsScreen(viewModel, gameState)
                        4 -> CombatScreen(
                            viewModel = viewModel,
                            gameState = gameState,
                            onNavigateBack = { selectedTab = 0 }
                        )
                        5 -> InventoryScreen(viewModel)
                        6 -> DeusExAiScreen(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBarHeader(gameState: GameState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(QuantumDarkBg)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = gameState.playerName.uppercase(),
                color = QuantumNeonPurple,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = "LEVEL ${gameState.level} // XP ${gameState.xp}",
                color = QuantumNeonBlue,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "CREDITS",
                    color = Color.Gray,
                    fontSize = 8.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "${gameState.credits} ₵",
                    color = QuantumTerminalGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "NANITES",
                    color = Color.Gray,
                    fontSize = 8.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "${gameState.nanites} N",
                    color = QuantumNeonBlue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "COGNITIVE HP",
                    color = Color.Gray,
                    fontSize = 8.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "${gameState.health}/${gameState.maxHealth}",
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBarHUD(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        NavigationItem("Explore", "🗺️"),
        NavigationItem("Augment", "⚙️"),
        NavigationItem("Factions", "🛡️"),
        NavigationItem("Companions", "👥"),
        NavigationItem("Combat", "⚔️"),
        NavigationItem("Inventory", "🎒"),
        NavigationItem("Deus Ex AI", "🧠")
    )

    NavigationBar(
        containerColor = QuantumDarkBg,
        tonalElevation = 8.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                icon = {
                    Text(text = item.iconSymbol, fontSize = 20.sp)
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = QuantumNeonPurple,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = QuantumNeonPurple,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = QuantumDarkBg
                )
            )
        }
    }
}

@Composable
fun NavigationRailHUD(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        NavigationItem("Explore", "🗺️"),
        NavigationItem("Augment", "⚙️"),
        NavigationItem("Factions", "🛡️"),
        NavigationItem("Companions", "👥"),
        NavigationItem("Combat", "⚔️"),
        NavigationItem("Inventory", "🎒"),
        NavigationItem("Deus Ex AI", "🧠")
    )

    NavigationRail(
        containerColor = QuantumDarkBg,
        header = {
            Text(
                text = "QE HUD",
                color = QuantumNeonPurple,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    ) {
        items.forEachIndexed { index, item ->
            NavigationRailItem(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                icon = {
                    Text(text = item.iconSymbol, fontSize = 20.sp)
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = QuantumNeonPurple,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = QuantumNeonPurple,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = QuantumDarkBg
                )
            )
        }
    }
}
