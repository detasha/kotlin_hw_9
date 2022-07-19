import exception.ChatNotFoundException
import exception.MessageNotFoundException

object ChatService {
    private val messages = mutableListOf<Message>()
    private val chats = mutableListOf<Chats>()
    private var mesId = 1
    private var chId = 1

    fun sendMessage(senderId: Int, receiverId: Int, text: String): Message {
        mesId++
        val message = Message(
            messageId = mesId,
            senderId = senderId,
            message = text,
            isRead = false
        )

        val chat: Chats = chats
            .ifEmpty { createChat(userFirst = senderId, userSecond = receiverId) }
            .let {
                chats.find { it.userFirst == senderId || it.userFirst == receiverId && it.userSecond == receiverId || it.userSecond == senderId }
                    ?: createChat(userFirst = senderId, userSecond = receiverId)
            }
            .let { chat ->
                chat.copy(message = (chat.message + message) as MutableList<Message>)
            }
        return message
    }

    private fun updatedChat(chat: Chats, message: Message) {
        chats
            .also { if (!it.contains(chat) || it.indexOf(chat) > it.lastIndex) it += chat }
            .let {
                it.set(
                    index = it.indexOf(chat),
                    element = chat.copy(message = (chat.message + message.messageId) as List<Message>, isRead = false)
                )
            }
    }

    fun createChat(userFirst: Int, userSecond: Int): Chats {
        val newChat = Chats(
            chatId = chId,
            userFirst = userFirst,
            userSecond = userSecond,
            message = messages
        )
        chId++
        chats.forEachIndexed { chatId, chat ->
            if (chat.chatId == newChat.chatId)
                chats[chatId] = newChat

        }
        return newChat
    }

    fun getAllMessages(id: Int): List<Message>? {
        val chat = chats.firstOrNull() { it.chatId == id } ?: throw ChatNotFoundException()
        val updatedContent = chat.message.map { it.copy(isRead = true) }
        val updatedChat = chat.copy(message = updatedContent)
        chats.removeIf { updatedChat.chatId == it.chatId }
        chats.add(updatedChat)
        return updatedContent
    }

    fun getLastMessage(id: Int, lastMessageId: Int): List<Message>? {
       val chat = chats.firstOrNull() { it.chatId == id } ?: return emptyList()
        val updatedContent = chat.message
            .filter { it.messageId > lastMessageId }
           .map { it.copy(isRead = true) }
        val updatedChat = chat.copy(message = updatedContent)
       chats.removeIf { updatedChat.chatId == it.chatId }
        chats.add(updatedChat)
        return updatedContent
  }

   fun getQtyMessage(id: Int, qtyMessage: Int, chats: MutableList<Chats>): List<Message>?
   { val chat = chats.firstOrNull() { it.chatId == id }?:return emptyList()
       val updatedContent = chat.message
            .take(qtyMessage)
            .map { it.copy(isRead = true) }
       val updatedChat = chat.copy(message = updatedContent)
     chats.removeIf { updatedChat.chatId == it.chatId }
       chats.add(updatedChat)
      return updatedContent
   }

    fun deleteChat(chatId: Int, chats: MutableList<Chats>): Boolean {
        val deletingChat =chats.find { it.chatId == chatId }?: throw ChatNotFoundException()
        return chats.remove(deletingChat)
    }

    fun deleteMessage(messageId:Int,messages: MutableList<Message>):Boolean{
       val deletingMessage = messages.find { it.messageId == messageId }?:throw MessageNotFoundException()
        return messages.remove(deletingMessage)
    }
}