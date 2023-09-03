package org.oleggalimov.examples.social.network.counter.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.core.dto.CounterDto;
import org.oleggalimov.examples.social.network.counter.service.UnreadMessageCountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = UnreadMessageCountController.UNREAD_MSG_COUNT_URI)
public class UnreadMessageCountController {

    public static final String UNREAD_MSG_COUNT_URI = "/api/unread";

    private final UnreadMessageCountService countService;

    @GetMapping("/{userId}")
    public CounterDto getCounters(@PathVariable String userId) {
        return countService.getUnreadMsgCounter(userId);
    }
}
