package org.olegalimov.examples.social.network.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.dao.FriendRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {

    private final FriendRepository friendRepository;

    public boolean setFriend(String userId, String friendId) {
        log.info("Добавляем друга с id = {} для {}", friendId, userId);
        var friendIsSet = friendRepository.setFriends(userId, friendId);
        if (!friendIsSet) {
            log.error("Не удалось добавить друга с id = {} для {}", friendId, userId);
        }
        return friendIsSet;
    }
}
