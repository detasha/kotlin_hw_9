package exception

import java.lang.RuntimeException

class ChatNotFoundException(message: String = "Chat not found") : RuntimeException(message)