package com.freenow.blogservice.service;

import com.freenow.blogservice.models.Comment;
import com.freenow.blogservice.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
public class TestValidationService {

    @Autowired
    private PostSearchService postSearchService;

    @Autowired
    private CommentSearchService commentSearchService;

    @Autowired
    private UserSearchService userSearchService;

    @Autowired
    private ValidationService validationService;

    Function<List<Post>, Set<Long>> collectPostsByPostId;
    Function<Set<Long>, List<Comment>> findCommentsInPost;
    Function<Long, List<Post>> findPostsByUser;
    Function<String, Long> findUser;
    Function<List<Comment>, List<String>> extractEmailsFromComment;
    Function<List<String>, List<String>> findInvalidEmails;

    @PostConstruct
    public void init() {
        collectPostsByPostId = postSearchService::findPostIdsFromCollectedPosts;
        findCommentsInPost = commentSearchService::findCommentsByPostIds;
        findPostsByUser = postSearchService::findPostsByUserId;
        findUser = userSearchService::findUserIdByUserName;
        extractEmailsFromComment = commentSearchService::findEmailsFromComments;
        findInvalidEmails = validationService::findInvalidEmailAddresses;
    }

    public List<String> findInvalidEmails(String userName) {
        return findUser
                .andThen(findPostsByUser)
                .andThen(collectPostsByPostId)
                .andThen(findCommentsInPost)
                .andThen(extractEmailsFromComment)
                .andThen(findInvalidEmails)
                .apply(userName);
    }
}
