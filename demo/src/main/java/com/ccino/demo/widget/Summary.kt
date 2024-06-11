package com.ccino.demo.widget

class Summary {


   /*
   输入法显示发送，输入框高度 10行内自适应、10行后固定：必须xml结合代码才能实现，maxLines在xml中达不到效果
        binding.editText.setHorizontallyScrolling(false) //关键
        binding.editText.setMaxLines(10) //关键
        binding.editText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                sendMessage()
                return@setOnEditorActionListener true
            }
            false
        }
   <EditText
    android:id="@+id/editText"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginStart="10dp"
    android:layout_marginEnd="10dp"
    android:background="@drawable/chat_input_bg"
    android:imeOptions="actionSend"
    android:inputType="text"
    android:padding="10dp"
    android:lineHeight="21sp"
    android:textColor="@color/white"
    android:textColorHint="#66ffffff"
    android:textCursorDrawable="@drawable/chat_input_cursor"
    android:textSize="14sp"
    android:visibility="visible"
    app:layout_constraintBottom_toTopOf="@id/extContainerContainerLayout"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    tools:hint="发送给小溪" />*/

}