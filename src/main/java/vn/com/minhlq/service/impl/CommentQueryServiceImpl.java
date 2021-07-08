package vn.com.minhlq.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import vn.com.minhlq.common.CursorPageParameter;
import vn.com.minhlq.common.CursorPager;
import vn.com.minhlq.dto.CommentData;
import vn.com.minhlq.model.User;
import vn.com.minhlq.mybatis.service.CommentReadService;
import vn.com.minhlq.mybatis.service.UserRelationshipQueryService;
import vn.com.minhlq.service.CommentQueryService;

@Service
@AllArgsConstructor
public class CommentQueryServiceImpl implements CommentQueryService {

    private CommentReadService commentReadService;

    private UserRelationshipQueryService userRelationshipQueryService;

    @Override
    public Optional<CommentData> findById(Long id, User user) {
        CommentData commentData = commentReadService.findById(id);
        if (commentData == null) {
            return Optional.empty();
        } else {
            commentData.getProfileData().setFollowing(
                    userRelationshipQueryService.isUserFollowing(user.getId(), commentData.getProfileData().getId()));
        }

        return Optional.ofNullable(commentData);
    }

    @Override
    public List<CommentData> findByArticleId(Long articleId, User user) {
        List<CommentData> comments = commentReadService.findByArticleId(articleId);
        if (comments.size() > 0 && user != null) {
            Set<Long> followingAuthors = userRelationshipQueryService.followingAuthors(user.getId(), comments.stream()
                    .map(commentData -> commentData.getProfileData().getId()).collect(Collectors.toList()));
            comments.forEach(commentData -> {
                if (followingAuthors.contains(commentData.getProfileData().getId())) {
                    commentData.getProfileData().setFollowing(true);
                }
            });
        }

        return comments;
    }

    @Override
    public CursorPager<CommentData> findByArticleIdWithCursor(Long articleId, User user,
            CursorPageParameter<DateTime> page) {
        List<CommentData> comments = commentReadService.findByArticleIdWithCursor(articleId, page);
        if (comments.isEmpty()) {
            return new CursorPager<>(new ArrayList<>(), page.getDirection(), false);
        }
        if (user != null) {
            Set<Long> followingAuthors = userRelationshipQueryService.followingAuthors(user.getId(), comments.stream()
                    .map(commentData -> commentData.getProfileData().getId()).collect(Collectors.toList()));
            comments.forEach(commentData -> {
                if (followingAuthors.contains(commentData.getProfileData().getId())) {
                    commentData.getProfileData().setFollowing(true);
                }
            });
        }
        boolean hasExtra = comments.size() > page.getLimit();
        if (hasExtra) {
            comments.remove(page.getLimit());
        }
        if (!page.isNext()) {
            Collections.reverse(comments);
        }

        return new CursorPager<>(comments, page.getDirection(), hasExtra);
    }

}
