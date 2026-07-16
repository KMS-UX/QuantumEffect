package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.game.ui.*
import com.example.game.viewmodel.GameViewModel
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        QuantumEffectGameApp()
      }
    }
  }
}

@Composable
fun QuantumEffectGameApp(
  viewModel: GameViewModel = viewModel()
) {
  val gameState by viewModel.gameStateFlow.collectAsStateWithLifecycle()
  var calibrationComplete by remember { mutableStateOf(false) }

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = QuantumDarkBg
  ) {
    if (!calibrationComplete) {
      StartupCalibrationScreen(onComplete = { calibrationComplete = true })
    } else {
      GameInterfaceDashboard(viewModel = viewModel, gameState = gameState)
    }
  }
}

@Composable
fun StartupCalibrationScreen(onComplete: () -> Unit) {
  var logsText by remember { mutableStateOf("CONNECTING SIGNAL RECEPTOR...") }

  LaunchedEffect(Unit) {
    kotlinx.coroutines.delay(600)
    logsText += "\nSECURE HANDSHAKE NOMINAL [UTC 2081]"
    kotlinx.coroutines.delay(600)
    logsText += "\nLOADING SOLIS REGIONAL GEOMETRIC ARRAYS..."
    kotlinx.coroutines.delay(600)
    logsText += "\nBIOMECHANICAL CORES PRE-CHARGED: 100%"
    kotlinx.coroutines.delay(600)
    logsText += "\nDEUS EX AI STORYTELLER ALIGNED..."
    kotlinx.coroutines.delay(500)
    logsText += "\n\nSYSTEM CALIBRATION READY. INITIALIZE LINK."
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp)
      .background(QuantumDarkBg),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "QUANTUM EFFECT",
      fontSize = 32.sp,
      fontFamily = FontFamily.Monospace,
      fontWeight = FontWeight.Bold,
      color = QuantumNeonPurple,
      letterSpacing = 2.sp,
      textAlign = TextAlign.Center
    )
    Text(
      text = "FIRST EARTH // SECURE PARALLEL COORDS",
      fontSize = 11.sp,
      fontFamily = FontFamily.Monospace,
      color = QuantumNeonBlue,
      letterSpacing = 1.sp,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
    )

    // Log terminal box
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
        fontFamily = FontFamily.Monospace,
        fontSize = 12.sp,
        lineHeight = 16.sp
      )
    }

    Spacer(modifier = Modifier.height(30.dp))

    CyberButton(
      onClick = onComplete,
      text = "Initialize Cognitive Link",
      color = QuantumNeonPurple,
      modifier = Modifier.fillMaxWidth(0.8f)
    )
  }
}

@Composable
fun GameInterfaceDashboard(
  viewModel: GameViewModel,
  gameState: com.example.game.db.GameState
) {
  var selectedTab by remember { mutableStateOf(0) }

  // Detect wide screen aspect ratios to dynamically use NavigationRail (Z Fold 6 / OnePlus 15)
  BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
    val isWideScreen = maxWidth > 600.dp

    Scaffold(
      modifier = Modifier.fillMaxSize(),
      containerColor = QuantumDarkBg,
      contentWindowInsets = WindowInsets.safeDrawing,
      topBar = {
        TopAppBarHeader(gameState = gameState)
      },
      bottomBar = {
        if (!isWideScreen) {
          BottomNavigationBarHUD(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
        }
      }
    ) { innerPadding ->
      Row(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        if (isWideScreen) {
          NavigationRailHUD(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
          VerticalDivider(color = QuantumBorder)
        }

        Box(modifier = Modifier.fillMaxSize()) {
          when (selectedTab) {
            0 -> ExploreScreen(
              viewModel = viewModel,
              gameState = gameState,
              onNavigateToCombat = { selectedTab = 4 }
            )
            1 -> AugmentScreen(viewModel = viewModel, gameState = gameState)
            2 -> FactionsScreen(viewModel = viewModel, gameState = gameState)
            3 -> CompanionsScreen(viewModel = viewModel, gameState = gameState)
            4 -> CombatScreen(viewModel = viewModel, gameState = gameState, onNavigateBack = { selectedTab = 0 })
            5 -> DeusExAiScreen(viewModel = viewModel)
          }
        }
      }
    }
  }
}

@Composable
fun TopAppBarHeader(gameState: com.example.game.db.GameState) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(QuantumDarkBg)
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "QUANTUM EFFECT",
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          color = QuantumNeonPurple,
          letterSpacing = 1.sp
        )
        Text(
          text = "CHASSIS PORT: UTC-2081",
          fontSize = 9.sp,
          color = QuantumGrayText,
          fontFamily = FontFamily.Monospace
        )
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Level badge
        Text(
          text = "LVL ${gameState.level}",
          fontSize = 11.sp,
          color = QuantumNeonBlue,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Bold,
          modifier = Modifier
            .background(QuantumNeonBlue.copy(alpha = 0.12f))
            .border(1.dp, QuantumNeonBlue.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp)
        )

        // Credits badge
        Text(
          text = "CRD: ${gameState.credits}",
          fontSize = 11.sp,
          color = QuantumNeonGreen,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Bold,
          modifier = Modifier
            .background(QuantumNeonGreen.copy(alpha = 0.12f))
            .border(1.dp, QuantumNeonGreen.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp)
        )
      }
    }
    Spacer(modifier = Modifier.height(6.dp))
    HorizontalDivider(color = QuantumBorder, thickness = 1.dp)
  }
}

// Bottom navigation design for smartphones
@Composable
fun BottomNavigationBarHUD(
  selectedTab: Int,
  onTabSelected: (Int) -> Unit
) {
  NavigationBar(
    containerColor = QuantumCardBg,
    tonalElevation = 8.dp,
    modifier = Modifier.border(width = 1.dp, color = QuantumBorder, shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
  ) {
    val items = listOf(
      NavigationItem("Explore", "🗺️"),
      NavigationItem("Augment", "⚙️"),
      NavigationItem("Factions", "🛡️"),
      NavigationItem("Companions", "👥"),
      NavigationItem("Combat", "⚔️"),
      NavigationItem("Deus Ex AI", "🧠")
    )

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
            fontWeight = FontWeight.Bold
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = QuantumNeonPurple,
          unselectedIconColor = Color.Gray,
          selectedTextColor = QuantumNeonPurple,
          unselectedTextColor = Color.Gray,
          indicatorColor = QuantumNeonPurple.copy(alpha = 0.15f)
        )
      )
    }
  }
}

// Navigation Rail design for foldables and high-end tablets
@Composable
fun NavigationRailHUD(
  selectedTab: Int,
  onTabSelected: (Int) -> Unit
) {
  NavigationRail(
    containerColor = QuantumCardBg,
    modifier = Modifier.fillMaxHeight()
  ) {
    Spacer(modifier = Modifier.height(16.dp))
    val items = listOf(
      NavigationItem("Explore", "🗺️"),
      NavigationItem("Augment", "⚙️"),
      NavigationItem("Factions", "🛡️"),
      NavigationItem("Companions", "👥"),
      NavigationItem("Combat", "⚔️"),
      NavigationItem("Deus Ex AI", "🧠")
    )

    items.forEachIndexed { index, item ->
      NavigationRailItem(
        selected = selectedTab == index,
        onClick = { onTabSelected(index) },
        icon = {
          Text(text = item.iconSymbol, fontSize = 24.sp)
        },
        label = {
          Text(
            text = item.label,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
          )
        },
        colors = NavigationRailItemDefaults.colors(
          selectedIconColor = QuantumNeonPurple,
          unselectedIconColor = Color.Gray,
          selectedTextColor = QuantumNeonPurple,
          unselectedTextColor = Color.Gray,
          indicatorColor = QuantumNeonPurple.copy(alpha = 0.15f)
        ),
        modifier = Modifier.padding(vertical = 8.dp)
      )
    }
  }
}

data class NavigationItem(val label: String, val iconSymbol: String)

