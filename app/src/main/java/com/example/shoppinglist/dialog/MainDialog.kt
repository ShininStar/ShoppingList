package com.example.shoppinglist.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.GreenLight


//диалог который используем для удаления, редактирования и сохранения записей
@Composable
fun MainDialog(
    dialogController: DialogController
) {
    if (dialogController.openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                dialogController.onDialogEvent(DialogEvent.OnCancel)
            },
            title = null,
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = dialogController.dialogTitle.value,
                        style = TextStyle(
                            color = DarkText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (dialogController.showEditableText.value) TextField(
                        value = dialogController.editableText.value,
                        onValueChange = {
                            dialogController.onDialogEvent(DialogEvent.OnTextChange(it))
                        },
                        label = {
                            Text(text = "List name:")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = GrayLight,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(9.dp),
                        singleLine = true,
                        textStyle = TextStyle(
                            DarkText,
                            fontSize = 16.sp
                        )

                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    dialogController.onDialogEvent(DialogEvent.OnConfirm)
                }) {
                    Text(text ="OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dialogController.onDialogEvent(DialogEvent.OnCancel)
                }) {
                    Text(text ="Cancel")
                }
            }
        )
    }
}