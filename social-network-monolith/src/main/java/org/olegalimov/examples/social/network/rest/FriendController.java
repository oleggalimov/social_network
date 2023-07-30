package org.olegalimov.examples.social.network.rest;

import lombok.RequiredArgsConstructor;
import org.olegalimov.examples.social.network.service.FriendService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.olegalimov.examples.social.network.utils.SecurityUtils.getUserFromContext;

@RequestMapping("/friend")
@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FriendService friendService;

    @PutMapping("/set/{userId}")
    public void setFriend(@PathVariable String userId) {
        friendService.setFriend(getUserFromContext(), userId);
    }
}
