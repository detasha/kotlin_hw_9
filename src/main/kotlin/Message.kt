data class Message(
    val messageId: Int,
    val senderId: Int,
    val message: String = "text",
    val isRead: Boolean = false
)