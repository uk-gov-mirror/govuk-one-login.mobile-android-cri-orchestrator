package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController

/**
 * Demo for how state restoration behaves differently depending on how dialogs are implemented.
 *
 * * At route "b" dialogs are implemented using the nav graph using
 *   the [NavGraphBuilder.dialog] route.
 * * At route "c" the dialog is implemented using a regular
 *   [NavGraphBuilder.composable] route which contains a Dialog composable.
 *
 *
 *
 * TODO: This is just for handover and demo purposes.
 *   Remove after a fix is implemented.
 */
@Composable
fun ReproDialogStateRestoration() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "a"
    ) {
        composable("a") {
            Column {
                Text("At route a - start")
                ButtonTo("b", navController)
                ButtonTo("c", navController)
                DebugCheckBox(label = "a state")
            }
        }

        dialog("b") {
            Surface(
                color = MaterialTheme.colorScheme.background,
            ) {
                Column {
                    Text("At route b - dialog nav graph destination")
                    Text("State restoration should work here")
                    DebugCheckBox(label = "b state")
                    Text(text = "Below is a nested nav host")
                    val subNavController = rememberNavController()
                    NavHost(
                        navController = subNavController,
                        startDestination = "b-a"
                    ) {
                        composable("b-a") {
                            Text(" -> at route b-a")
                            Column {
                                ButtonTo("b-b", subNavController)
                                DebugCheckBox(label = "b-a state")
                            }
                        }

                        dialog("b-b") {
                            Column {
                                Text("At route b-b - nested dialog nav graph destination")
                                Text("State restoration should work here")
                                DebugCheckBox(label = "b-b state")
                            }
                        }
                    }
                }
            }
        }

        composable ("c") {
            Dialog(
                onDismissRequest = {
                    navController.navigate("a")
                }
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column {
                        Text("At route c - Dialog composable")
                        Text("State restoration doesn't work here")
                        DebugCheckBox(label = "c state")
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonTo(
    route: String,
    navController: NavHostController,
) {
    Button(
        onClick = {
            navController.navigate(route)
        }
    ) {
        Text(text = "Open route $route ")
    }
}

@Composable
fun DebugCheckBox(
    label: String,
) {
    var checked by rememberSaveable { mutableStateOf(false) }
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Debug $label")
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
            )
        }
    }
}

