package org.olegalimov.examples.social.network.rest;


import lombok.RequiredArgsConstructor;
import org.olegalimov.examples.social.network.dto.MessageDto;
import org.olegalimov.examples.social.network.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/dialog")
@RequiredArgsConstructor
@RestController
public class DialogsController {

    private final MessageService messageService;

    @PostMapping("/{userId}/send")
    public void sendMessage(
            @PathVariable(name = "userId") String fromUserId,
            @RequestParam(name = "user_id") String toUserId,
            @RequestBody MessageDto dto) {
        messageService.saveMessage(fromUserId, toUserId, dto.text());
    }

    @GetMapping("/{userId}/list")
    public List<MessageDto> getMessages(
            @PathVariable(name = "userId") String fromUserId,
            @RequestParam(name = "user_id") String toUserId) {
        return messageService.getMessages(fromUserId, toUserId);
    }
}
