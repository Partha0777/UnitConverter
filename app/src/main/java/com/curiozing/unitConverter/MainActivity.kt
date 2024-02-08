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
import com.curiozing.unitConverter.ui.theme.MyApplicationTheme
import kotlin.math.pow
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverterUi()
                }
            }
        }
    }
}

@Composable
fun UnitConverterUi() {

    var inputExpand by remember { mutableStateOf(false) }
    var outputExpand by remember { mutableStateOf(false) }
    var inputValue by remember { mutableStateOf(String()) }
    var selectedInputConverter by remember { mutableStateOf("Select") }
    var selectedOutputConverter by remember { mutableStateOf("Select") }
    var resultText by remember { mutableStateOf("") }
    var resultUnit by remember { mutableStateOf("") }

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
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Unit Converter", fontSize = 20.sp, fontWeight = FontWeight(500))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = resultText, fontSize = 28.sp, fontWeight = FontWeight(700))
        Text(text = resultUnit, fontSize = 14.sp, fontWeight = FontWeight(700))
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            placeholder = {
                Text(text = "Enter the value here...", color = Color.Gray)
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
                    DropdownMenuItem(text = { Text(text = "Millimetre") }, onClick = {
                        handleInputDropDownClick("Millimetre")
                    })
                    DropdownMenuItem(text = { Text(text = "Centimetre") }, onClick = {
                        handleInputDropDownClick("Centimetre")
                    })
                    DropdownMenuItem(text = { Text(text = "Feet") }, onClick = {
                        handleInputDropDownClick("Feet")

                    })
                    DropdownMenuItem(text = { Text(text = "Metre") }, onClick = {
                        handleInputDropDownClick("Metre")
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
                    DropdownMenuItem(text = { Text(text = "Millimetre") }, onClick = {
                        handleOutputDropDownClick("Millimetre")
                    })
                    DropdownMenuItem(text = { Text(text = "Centimetre") }, onClick = {
                        handleOutputDropDownClick("Centimetre")
                    })
                    DropdownMenuItem(text = { Text(text = "Feet") }, onClick = {
                        handleOutputDropDownClick("Feet")

                    })
                    DropdownMenuItem(text = { Text(text = "Metre") }, onClick = {
                        handleOutputDropDownClick("Metre")
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


    }


}

fun handleInputDropDownClick(onExpandedStateChanged: (Boolean) -> Unit) {
    onExpandedStateChanged(false)
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        UnitConverterUi()
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
