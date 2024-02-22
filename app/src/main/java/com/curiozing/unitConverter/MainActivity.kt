package com.curiozing.unitConverter

import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.curiozing.unitConverter.ui.theme.MyApplicationTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.pow
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val historyList = "historylist";

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "unitController", builder = {
        composable("unitController") {
            UnitConverterUi {
                val historylist = it
                navController.navigate("HistoryScreen/$historylist/hello")
            }
        }
        composable("HistoryScreen/{$historyList}/{message}") {
            val list = it.arguments?.getString(historyList) ?: ""
            HistoryScreen(list) {
                navController.navigate("unitController")
            }
        }
    })


}

@Composable
fun HistoryScreen(list: String?, navigateToHome: () -> Unit) {
    val gson = Gson()
    val type = object : TypeToken<List<String>>() {}.type

    val historyList: List<String> = gson.fromJson(list, type)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(content = {
            items(historyList) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = it, fontSize = 18.sp)
            }
        })
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = navigateToHome) {
            Text(text = "Go to Home")
        }
    }

}

val historyList = mutableListOf<String>()

@Composable
fun UnitConverterUi(onNavigation: (String) -> Unit) {

    var inputExpand by remember { mutableStateOf(false) }
    var outputExpand by remember { mutableStateOf(false) }
    var inputValue by remember { mutableStateOf(String()) }
    var selectedInputConverter by remember { mutableStateOf(Constants.dropDownDefaultValue) }
    var selectedOutputConverter by remember { mutableStateOf(Constants.dropDownDefaultValue) }
    var resultText by remember { mutableStateOf("") }
    var resultUnit by remember { mutableStateOf("") }
    //val historyList = remember { mutableStateListOf<String>() }

    fun roundToDecimalPlaces(value: Double, decimalPlaces: Int): Double {
        val factor = 10.0.pow(decimalPlaces.toDouble())
        return round(value * factor) / factor
    }


    fun converterCalculations() {
        val value = if (inputValue.isEmpty()) 0.0 else inputValue.toDouble()
        resultText = "${
            roundToDecimalPlaces(
                Utils.convertTheUnit(
                    selectedInputConverter, selectedOutputConverter, value
                ), 2
            )
        }"
        resultUnit = selectedOutputConverter
        historyList.add("$inputValue $selectedInputConverter  --->  $resultText $selectedOutputConverter")
        println("test $historyList")

    }


    fun handleInputDropDownClick(value: String) {
        inputExpand = false
        selectedInputConverter = value
    }

    fun handleOutputDropDownClick(value: String) {
        outputExpand = false
        selectedOutputConverter = value
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Unit Converter", fontSize = 20.sp, fontWeight = FontWeight(500))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = resultText, fontSize = 28.sp, fontWeight = FontWeight(700))
        Text(text = resultUnit, fontSize = 14.sp, fontWeight = FontWeight(700))
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            placeholder = {
                Text(text = Constants.inputHint, color = Color.Gray)
            },
            value = inputValue,
            onValueChange = {
                inputValue = it
            },
            modifier = Modifier.padding(12.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Box {
                TextButton(
                    border = BorderStroke(width = 1.dp, Color.Gray),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        inputExpand = true
                    },
                ) {
                    Text(text = selectedInputConverter)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = inputExpand, onDismissRequest = {
                    inputExpand = false
                }) {
                    DropdownMenuItem(
                        text = { Text(text = Units.Millimetre.toString()) },
                        onClick = {
                            handleInputDropDownClick(Units.Millimetre.toString())
                        })
                    DropdownMenuItem(
                        text = { Text(text = Units.Centimetre.toString()) },
                        onClick = {
                            handleInputDropDownClick(Units.Centimetre.toString())
                        })
                    DropdownMenuItem(text = { Text(text = Units.Feet.toString()) }, onClick = {
                        handleInputDropDownClick(Units.Feet.toString())

                    })
                    DropdownMenuItem(text = { Text(text = Units.Metre.toString()) }, onClick = {
                        handleInputDropDownClick(Units.Metre.toString())
                    })

                }
            }
            Spacer(modifier = Modifier.width(70.dp))
            Box {
                TextButton(
                    border = BorderStroke(width = 1.dp, Color.Gray),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        outputExpand = true
                    },
                ) {
                    Text(text = selectedOutputConverter)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = outputExpand, onDismissRequest = {
                    outputExpand = false
                }) {
                    DropdownMenuItem(
                        text = { Text(text = Units.Millimetre.toString()) },
                        onClick = {
                            handleOutputDropDownClick(Units.Millimetre.toString())
                        })
                    DropdownMenuItem(
                        text = { Text(text = Units.Centimetre.toString()) },
                        onClick = {
                            handleOutputDropDownClick(Units.Centimetre.toString())
                        })
                    DropdownMenuItem(text = { Text(text = Units.Feet.toString()) }, onClick = {
                        handleOutputDropDownClick(Units.Feet.toString())

                    })
                    DropdownMenuItem(text = { Text(text = Units.Metre.toString()) }, onClick = {
                        handleOutputDropDownClick(Units.Metre.toString())
                    })

                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            converterCalculations()
        }) {
            Text(text = "Convert")
        }
        Button(onClick = {
            val list = Gson().toJson(historyList)
            onNavigation.invoke(list)
        }) {
            Text(text = "History")
        }


    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        UnitConverterUi {}
    }
}

data class CustomClass(val id: Int, var name: String) {}

var list = listOf(
    CustomClass(1, "Partha"), CustomClass(2, "Kan"), CustomClass(3, "Sarathi")
)
lateinit var tempList: List<CustomClass>;

var hello = "434"


fun main() {
    //   var item = list[1]
    /*
        list.find { it.id == 1 }.let {
            it?.name = "Rock"
        }
    */
    var data = hello
    println(hello);
    data = "djdj"
    println(hello);

    println(list[1])

    // tempList = list.map { it.copy() }
    tempList = list

    changeName(tempList[1])

}

fun changeName(item: CustomClass) {

    var originalList = listOf("a", "b", "c")
    println(originalList[0])
    var copiedList = originalList.toMutableList()
    copiedList[0] = "modified"
    println(originalList[0])


    item.let {
        it.name = "It's Changed!"
    }
    println(list[1])

}
