data class Chats(
    var chatId:Int,
    val userFirst:Int,
    val userSecond:Int,
    val message:List<Message>,
    val isRead:Boolean = false
)

