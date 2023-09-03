package org.olegalimov.examples.social.network.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.core.cache.CacheService;
import org.olegalimov.examples.social.network.dao.PostsRepository;
import org.olegalimov.examples.social.network.dto.PostDto;
import org.olegalimov.examples.social.network.entity.Post;
import org.olegalimov.examples.social.network.mapper.PostMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.olegalimov.examples.social.network.constant.CommonConstant.POSTS_CACHE_NAME;
import static org.olegalimov.examples.social.network.constant.CommonConstant.WebSocket.WS_EXCHANGE_POST_PREFIX;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostsRepository postsRepository;
    private final PostMapper postMapper;
    private final CacheService<PostDto> cacheService;
    private final SimpMessagingTemplate messagingTemplate;

    public String createPost(String userId, String text) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException("Нельзя создать пустой пост от пользователя " + userId);
        }
        var postId = UUID.randomUUID().toString();
        log.info("Сохраняем пост с id = {} от пользователя с user_id = {}", postId, userId);
        log.debug("Текст поста: {}", text);

        var isPostCreated = postsRepository.createPost(postId, userId, text);
        if (!isPostCreated) {
            log.error("Не удалось сохранить пост");
            throw new IllegalStateException("Не удалось сохранить пост от пользователя " + userId);
        }
        if (postId != null) {
            var postDto = postMapper.toPostDto(postId, text, userId);
            var destination = WS_EXCHANGE_POST_PREFIX + userId;
            log.info("Рассылаем сообщение в канал подписчикам {}", destination);
            messagingTemplate.convertAndSend(destination, postDto);
        }
        return postId;
    }

    public boolean updatePost(String postId, String text) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException("Нельзя задать пустой текст для поста!");
        }
        if (!StringUtils.hasText(postId)) {
            throw new IllegalArgumentException("Не указан идентификатор поста!");
        }
        log.info("Обновляем пост с id = {}", postId);
        log.debug("Новый текст поста: {}", text);

        var isRecordUpdated = postsRepository.updatePost(postId, text, LocalDateTime.now());
        if (!isRecordUpdated) {
            log.error("Не удалось обновить пост");
            throw new IllegalStateException("Не удалось обновить пост!");
        }

        return true;
    }

    public boolean deletePost(String postId) {
        if (!StringUtils.hasText(postId)) {
            throw new IllegalArgumentException("Не указан идентификатор поста!");
        }
        log.info("Удаляем пост с id = {}", postId);

        var isRecordUpdated = postsRepository.deletePost(postId);
        if (!isRecordUpdated) {
            log.error("Не удалось удалить пост");
            throw new IllegalStateException("Не удалось удалить пост!");
        }

        return true;
    }

    public PostDto getPost(String postId) {
        log.info("Ищем пост с идентификатором {}", postId);
        var entity = postsRepository.findByPostId(postId);
        log.info("Найдена сущность с id={}", entity.getId());
        return postMapper.toPostDto(entity);
    }

    public Collection<PostDto> getFeedForUser(String userId, int offset, int limit) {
        log.info("Ищем {} постов (начиная с {}) для пользователя с идентификатором {}", limit, offset, userId);
        var cachedPosts = cacheService.getAllFromCache(POSTS_CACHE_NAME);
        if (CollectionUtils.isEmpty(cachedPosts)) {
            log.info("В кеше нет данных, запрашиваем данные в БД");
            var postList = selectPosts(userId, offset, limit).stream()
                    .sorted(Comparator.comparing(Post::getPostCreatedAt))
                    .map(postMapper::toPostDto)
                    .toList();
            log.info("Найдено в БД {} постов", postList.size());
            cacheService.putToCache(POSTS_CACHE_NAME, postList, PostDto::id);
            return postList;
        }
        return cachedPosts;
    }

    public void invalidatePostsCache() {
        cacheService.invalidateCache(POSTS_CACHE_NAME);
    }

    private List<Post> selectPosts(String userId, int offset, int limit) {
        return postsRepository.getFeedForUser(userId, offset, limit);
    }
}
