package com.example.animationsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animationsdemo.ui.theme.AnimationsDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimationsDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnimationDemoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AnimationDemoScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            DemoSection(title = "animateDpAsState (taille)") {
                TailleAnimee()
            }
        }
        item {
            DemoSection(title = "animateColorAsState (couleur)") {
                CouleurAnimee()
            }
        }
        item {
            DemoSection(title = "AnimatedVisibility") {
                DetailsAnimes()
            }
        }
        item {
            DemoSection(title = "AnimatedVisibility (avancé)") {
                DetailsAnimes2()
            }
        }
        item {
            DemoSection(title = "Crossfade") {
                CrossfadeDemo()
            }
        }
        item {
            DemoSection(title = "animateContentSize") {
                TailleAuto()
            }
        }
    }
}

@Composable
fun DemoSection(title: String, content: @Composable () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}


@Composable
fun TailleAnimee() {
    var grand by remember { mutableStateOf(false) }

    val taille by animateDpAsState(
        targetValue = if (grand) 96.dp else 48.dp,
        label = "taille"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { grand = !grand }) { Text("Changer la taille") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(taille)
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
fun CouleurAnimee() {
    var actif by remember { mutableStateOf(false) }

    val couleur by animateColorAsState(
        targetValue = if (actif) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.secondary,
        label = "couleur"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { actif = !actif }) { Text("Changer la couleur") }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(couleur)
        )
    }
}

@Composable
fun DetailsAnimes() {
    var show by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { show = !show }) { Text("Afficher / Masquer") }

        AnimatedVisibility(visible = show) {
            Text(
                "Voici des détails qui apparaissent.",
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Composable
fun DetailsAnimes2() {
    var show by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { show = !show }) { Text("Afficher / Masquer (Slide + Fade)") }

        AnimatedVisibility(
            visible = show,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Text("Bloc animé", modifier = Modifier.padding(top = 12.dp).background(Color.LightGray).padding(8.dp))
        }
    }
}

@Composable
fun CrossfadeDemo() {
    var ok by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { ok = !ok }) { Text("Changer d'état") }
        Spacer(modifier = Modifier.height(12.dp))

        Crossfade(targetState = ok, label = "crossfade") { etat ->
            if (etat) {
                Text("État: OK", color = Color(0xFF4CAF50))
            } else {
                Text("État: NON", color = Color(0xFFF44336))
            }
        }
    }
}

@Composable
fun TailleAuto() {
    var long by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { long = !long }) { Text("Afficher plus/moins") }
            Spacer(modifier = Modifier.height(12.dp))

            if (long) {
                Text("Ce texte est beaucoup plus long pour démontrer comment le conteneur s'adapte en douceur à sa nouvelle taille. L'animation rend la transition beaucoup moins saccadée et plus agréable pour l'utilisateur.")
            } else {
                Text("Texte court.")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimationDemoScreenPreview() {
    AnimationsDemoTheme {
        AnimationDemoScreen()
    }
}
