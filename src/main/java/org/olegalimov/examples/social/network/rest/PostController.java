package org.olegalimov.examples.social.network.rest;

import lombok.RequiredArgsConstructor;
import org.olegalimov.examples.social.network.dto.CreatePostRequest;
import org.olegalimov.examples.social.network.dto.PostDto;
import org.olegalimov.examples.social.network.dto.UpdatePostRequest;
import org.olegalimov.examples.social.network.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.olegalimov.examples.social.network.utils.SecurityUtils.getUserFromContext;

@RequestMapping("/post")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public String createPost(@RequestBody CreatePostRequest request) {
        return  postService.createPost(getUserFromContext(), request.text());
    }

    @GetMapping("/get/feed")
    public Collection<PostDto> getPost(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        return postService.getFeedForUser(getUserFromContext(), offset, limit);
    }

    @GetMapping("/get/{postId}")
    public PostDto getPost(@PathVariable String postId) {
        return postService.getPost(postId);
    }

    @PutMapping("/update")
    public void updatePost(@RequestBody UpdatePostRequest request) {
        postService.updatePost(request.id(), request.text());
    }

    @PutMapping("/delete/{postId}")
    public void deletePost(@PathVariable String postId) {
        postService.deletePost(postId);
    }

    @GetMapping("/invalidate")
    public void invalidateCache() {
        postService.invalidatePostsCache();
    }
}
