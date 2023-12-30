package com.noisy.noisy.Controller;

import com.noisy.noisy.Response.MessageResponse;
import com.noisy.noisy.model.Message;
import com.noisy.noisy.repository.MessageRepository;
import com.noisy.noisy.repository.UserLastReadMsgRepository;
import com.noisy.noisy.request.MessageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    final MessageRepository messageRepository;
    final UserLastReadMsgRepository userLastReadMsgRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository, UserLastReadMsgRepository userLastReadMsgRepository) {
        this.messageRepository = messageRepository;
        this.userLastReadMsgRepository = userLastReadMsgRepository;
    }

    @MessageMapping("/listen/{chat_name}")
    @SendTo("/broadcast/{chat_name}")
    public MessageResponse hello(
            @DestinationVariable String chat_name,
            MessageReq message
    ) {
        Message saved = messageRepository.save(new Message(message, chat_name));
        userLastReadMsgRepository.updateLastReadMsg(saved.getUser_name(), saved.getChat_name(), saved.getId());
        return new MessageResponse(message);
    }
}
