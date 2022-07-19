import exception.ChatNotFoundException
import exception.MessageNotFoundException
import org.junit.Assert.*
import org.junit.Test

class Test {
    @Test
    fun createMessage() {
        val service = ChatService
        service.sendMessage(12, 25, "text")
        assertNotNull(service.sendMessage(senderId = 12, receiverId = 25, text = "text"))
    }

    @Test
    fun deleteMessage() {
        val service = ChatService
        val mes1 = service.sendMessage(12, 25, "text")
        val mes2 = service.sendMessage(1, 25, "no")
        var message = mutableListOf<Message>()
        message.add(mes1)
        message.add(mes2)
        val result = service.deleteMessage(mes1.messageId, message)
        assertTrue(result)
    }

    @Test
    fun deleteChat() {
        val service = ChatService
        val chat1 = service.createChat(12, 15)
        var chats = mutableListOf<Chats>()
        chats.add(chat1)
        val result = service.deleteChat(chat1.chatId, chats)
        assertTrue(result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun deleteChatNotFound() {
        val service = ChatService
        val chat1 = service.createChat(12, 15)
        var chats = mutableListOf<Chats>()
        chats.add(chat1)
        service.deleteChat(Int.MAX_VALUE, chats)
    }

    @Test(expected = MessageNotFoundException::class)
    fun deleteMessageNotFound() {
        val service = ChatService
        val mes1 = service.sendMessage(12, 15, "text")
        var message = mutableListOf<Message>()
        message.add(mes1)
        service.deleteMessage(Int.MAX_VALUE, message)
    }
}


