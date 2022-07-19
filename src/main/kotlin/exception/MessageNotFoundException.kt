package exception

import java.lang.RuntimeException

class MessageNotFoundException(message: String = "Message not found") : RuntimeException(message)