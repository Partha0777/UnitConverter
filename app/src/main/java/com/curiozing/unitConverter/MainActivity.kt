package com.curiozing.unitConverter

import android.os.Bundle
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.materialIcon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curiozing.unitConverter.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
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
    var inputValue by remember { mutableStateOf(String()) }
    var selectedInputConverter by remember { mutableStateOf("Select") }

    fun handleInputDropDownClick(value: String){
       inputExpand = false
        selectedInputConverter = value
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Unit Converter", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = inputValue, onValueChange = {
                inputValue = it
            }, modifier = Modifier
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box {
                TextButton(onClick = {
                    inputExpand = true
                }, modifier = Modifier.border(BorderStroke(width = 1.dp, color = Color.Gray), shape = RoundedCornerShape(8.dp))) {
                    Text(text = selectedInputConverter)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = inputExpand, onDismissRequest = {

                }) {
                    DropdownMenuItem(text = { Text(text = "Millimetre") }, onClick = {
                        handleInputDropDownClick("Millimetre")
                    })
                    DropdownMenuItem(text = { Text(text = "Centimetre") }, onClick = {
                        handleInputDropDownClick("Centimetre")
                    })
                    DropdownMenuItem(text = { Text(text ="Feet") }, onClick = {
                        handleInputDropDownClick("Feet")

                    })
                    DropdownMenuItem(text = { Text(text = "Metre") }, onClick = {
                        handleInputDropDownClick("Metre")
                    })

                }
            }
            Spacer(modifier = Modifier.width(100.dp))
            TextButton(onClick = {
                inputExpand = true
            }, modifier = Modifier.border(BorderStroke(width = 1.dp, color = Color.Gray), shape = RoundedCornerShape(8.dp))) {
                Text(text = selectedInputConverter)
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }        }


    }


}

fun handleInputDropDownClick(onExpandedStateChanged: (Boolean) -> Unit){
    onExpandedStateChanged(false)
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        UnitConverterUi()
    }
}