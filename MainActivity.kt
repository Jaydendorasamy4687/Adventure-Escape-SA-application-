package com.adventureescapesa.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color Palette
val DarkGreen = Color(0xFF1B4D3E)
val AccentOrange = Color(0xE05315FF)
val LightBg = Color(0xFFF8F9FA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdventureEscapeApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdventureEscapeApp() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adventure Escape SA", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkGreen)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = DarkGreen) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    label = { Text("Home", color = Color.White) },
                    icon = {}
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    label = { Text("Activities", color = Color.White) },
                    icon = {}
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    label = { Text("Calculator", color = Color.White) },
                    icon = {}
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(LightBg)
        ) {
            when (selectedTab) {
                0 -> HomeScreen()
                1 -> ActivitiesScreen()
                2 -> CalculatorScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkGreen),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Escape into the Wild", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Book premier eco-tourism adventures across South Africa.", color = Color.White)
            }
        }

        Text("Featured Activities", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
        Spacer(modifier = Modifier.height(8.dp))

        ActivityCard("Ziplining Canopy Tour", "R400", "2 Hours | Moderate")
        ActivityCard("River Kayaking", "R350", "3 Hours | Easy-Moderate")
        ActivityCard("Rock Climbing", "R500", "4 Hours | Advanced")
    }
}

@Composable
fun ActivitiesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("All Eco-Adventures", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
        Spacer(modifier = Modifier.height(12.dp))

        ActivityCard("Ziplining Canopy Tour", "R400", "Soar through forest canopies with accredited guides.")
        ActivityCard("River Kayaking", "R350", "Paddle along scenic river bends and moderate rapids.")
        ActivityCard("Rock Climbing", "R500", "Conquer natural cliff faces with top-rope safety systems.")
    }
}

@Composable
fun CalculatorScreen() {
    var zipSelected by remember { mutableStateOf(false) }
    var kayakSelected by remember { mutableStateOf(false) }
    var climbSelected by remember { mutableStateOf(false) }
    var guests by remember { mutableStateOf(1) }

    val basePrice = (if (zipSelected) 400 else 0) +
                    (if (kayakSelected) 350 else 0) +
                    (if (climbSelected) 500 else 0)

    val subtotal = basePrice * guests
    val discount = if (guests >= 5) subtotal * 0.10 else 0.0
    val total = subtotal - discount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Fee Calculator Engine", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
        Spacer(modifier = Modifier.height(12.dp))

        Text("1. Select Activities:", fontWeight = FontWeight.SemiBold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = zipSelected, onCheckedChange = { zipSelected = it })
            Text("Ziplining Canopy Tour (R400)")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = kayakSelected, onCheckedChange = { kayakSelected = it })
            Text("River Kayaking (R350)")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = climbSelected, onCheckedChange = { climbSelected = it })
            Text("Rock Climbing (R500)")
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text("2. Number of Participants:", fontWeight = FontWeight.SemiBold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { if (guests > 1) guests-- }, colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)) {
                Text("-")
            }
            Text("  $guests Guests  ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Button(onClick = { guests++ }, colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)) {
                Text("+")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Calculation Breakdown", fontWeight = FontWeight.Bold, color = DarkGreen, fontSize = 16.sp)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text("Subtotal: R${"%.2f".format(subtotal.toDouble())}")
                Text("Group Discount (10% for 5+): -R${"%.2f".format(discount)}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Final Total: R${"%.2f".format(total)}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = AccentOrange)
            }
        }
    }
}

@Composable
fun ActivityCard(title: String, price: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DarkGreen)
            Text(description, color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(price, fontWeight = FontWeight.Bold, color = AccentOrange, fontSize = 16.sp)
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = AccentOrange)
                ) {
                    Text("Book Now", color = Color.White)
                }
            }
        }
    }
}
