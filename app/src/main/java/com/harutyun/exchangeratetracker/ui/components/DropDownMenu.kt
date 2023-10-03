package com.harutyun.exchangeratetracker.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.harutyun.exchangeratetracker.R
import com.harutyun.exchangeratetracker.ui.theme.LightPrimaryBlue
import com.harutyun.exchangeratetracker.util.dpToPx
import com.harutyun.exchangeratetracker.util.pxToDp

@Composable
fun DropDownMenu(
    items: List<String>,
    initialText: String,
    onItemSelected: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(initialText) }

    var width by remember { mutableStateOf(IntSize.Zero) }


    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .onSizeChanged {
                width = it
            }
    ) {
        Row(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = MaterialTheme.colorScheme.primary)
                ) { isExpanded = !isExpanded }
                .fillMaxWidth()
                .background(Color.White, MaterialTheme.shapes.medium)
                .height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedText,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .wrapContentWidth(),
                style = MaterialTheme.typography.bodyMedium
            )

            IconButton(
                onClick = {
                    isExpanded = !isExpanded
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

    }

    DropDownPopup(
        items = items,
        selectedText = selectedText,
        onDismissRequest = { isExpanded = false },
        expanded = isExpanded,
        offset = IntOffset(-16.dp.dpToPx().toInt(), -16.dp.dpToPx().toInt()),
        width = width.width,
        onItemSelected = { selectedItem ->
            selectedText = selectedItem
            onItemSelected(selectedText)
        },
        modifier = Modifier
            .width(width.width.pxToDp())
            .padding(top = 48.dp)
    )
}



@Composable
private fun DropDownPopup(
    items: List<String>,
    selectedText: String,
    expanded: Boolean,
    width: Int,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopStart,
    offset: IntOffset = IntOffset(0, 0),
    onDismissRequest: (() -> Unit),
    enter: EnterTransition = fadeIn() + expandVertically(),
    exit: ExitTransition = fadeOut() + shrinkVertically(),
) {
    val popupPositionProvider = remember(alignment, offset) {
        AlignmentOffsetPositionProvider(
            alignment,
            offset
        )
    }

    val expandedState = remember {
        MutableTransitionState(false)
    }
    expandedState.targetState = expanded

    val itemHeight = 54
    val expandedHeight = if (items.size < 4) itemHeight * items.size + 48 else 4 * itemHeight + 48


    val elevation = animateDpAsState(
        targetValue = if (expandedState.targetState) 8.dp else 0.dp,
        label = "elevation"
    )

    val height = animateDpAsState(
        targetValue = if (expandedState.targetState) expandedHeight.dp else 48.dp,
        label = "height"
    )


    if (expandedState.currentState || expandedState.targetState || !expandedState.isIdle) {

        Popup(
            popupPositionProvider = popupPositionProvider,
            onDismissRequest = onDismissRequest,
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize()
            ) {
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation.value,
                            MaterialTheme.shapes.medium
                        )
                        .background(
                            Color.White,
                            MaterialTheme.shapes.medium
                        )
                        .height(height.value)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.shapes.medium
                        )
                        .width(width.pxToDp())
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedText,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    expandedState.targetState = !expandedState.targetState
                                }
                                .padding(horizontal = 16.dp)
                                .wrapContentWidth(),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        IconButton(onClick = {
                            onDismissRequest()
                        }) {
                            Icon(
                                painter = painterResource(id = if (expandedState.targetState) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    AnimatedVisibility(
                        visibleState = expandedState,
                        enter = enter,
                        exit = exit,
                        modifier = modifier
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .animateContentSize(),
                            content = {
                                items(items.size) { index ->
                                    val item = items[index]
                                    Text(
                                        text = item,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(if (selectedText == items[index]) LightPrimaryBlue else Color.White)
                                            .clickable {
                                                onItemSelected(item)
                                                expandedState.targetState = false
                                                onDismissRequest()
                                            }
                                            .padding(16.dp),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }

}


class AlignmentOffsetPositionProvider(
    private val alignment: Alignment,
    private val offset: IntOffset
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        var popupPosition = IntOffset(0, 0)

        val parentAlignmentPoint = alignment.align(
            IntSize.Zero,
            IntSize(anchorBounds.width, anchorBounds.height),
            layoutDirection
        )
        val relativePopupPos = alignment.align(
            IntSize.Zero,
            IntSize(popupContentSize.width, popupContentSize.height),
            layoutDirection
        )

        popupPosition += IntOffset(anchorBounds.left, anchorBounds.top)

        popupPosition += parentAlignmentPoint

        popupPosition -= IntOffset(relativePopupPos.x, relativePopupPos.y)

        val resolvedOffset = IntOffset(
            offset.x * (if (layoutDirection == LayoutDirection.Ltr) 1 else -1),
            offset.y
        )
        popupPosition += resolvedOffset

        return popupPosition
    }
}