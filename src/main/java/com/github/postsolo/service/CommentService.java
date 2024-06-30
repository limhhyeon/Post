package com.github.postsolo.service;

import com.github.postsolo.repository.Post.PostEntity;
import com.github.postsolo.repository.Post.PostJpa;
import com.github.postsolo.repository.comment.CommentEntity;
import com.github.postsolo.repository.comment.CommentJpa;
import com.github.postsolo.repository.userDeatail.CustomUserDetails;
import com.github.postsolo.repository.users.User;
import com.github.postsolo.repository.users.UserJpa;
import com.github.postsolo.service.exception.NotFoundException;
import com.github.postsolo.web.Dto.ResponseDto;
import com.github.postsolo.web.Dto.comment.CommentDto;
import com.github.postsolo.web.Dto.comment.CommentRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final UserJpa userJpa;
    private final PostJpa postJpa;
    private final CommentJpa commentJpa;
    public ResponseDto createCommentResult(CustomUserDetails customUserDetails, Integer postId,CommentRequest commentRequest) {
        Integer userId = customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()-> new NotFoundException("해당 유저를 찾을 수 없습니다."));
        PostEntity post = postJpa.findById(postId)
                .orElseThrow(()-> new NotFoundException("postId : "+postId+"에 해당하는 게시판을 찾을 수 없습니다."));
        CommentEntity createComment = CommentEntity.builder()
                .user(user)
                .post(post)
                .name(user.getName())
                .content(commentRequest.getContent())
                .createAt(LocalDateTime.now())
                .status(commentRequest.getStatus())
                .build();
        commentJpa.save(createComment);
        CommentDto commentDto = new CommentDto(createComment);
        return new ResponseDto(HttpStatus.OK.value(), "댓글 생성 성공", commentDto);

    }

    public ResponseDto changeCommentResult(CustomUserDetails customUserDetails, Integer commentId, CommentRequest commentRequest) {
        Integer userId = customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()-> new NotFoundException("해당 유저를 찾을 수 없습니다."));
        CommentEntity comment = commentJpa.findById(commentId)
                .orElseThrow(()-> new NotFoundException("commentId : "+commentId+"에 해당하는 comment가 없습니다."));
        if (!comment.getUser().equals(user)){
            throw new NotFoundException("user("+user.getName()+")가 작성한 댓글이 아닙니다.");
        }else {
            comment.setContent(commentRequest.getContent());
            comment.setStatus(commentRequest.getStatus());
            CommentDto commentDto= new CommentDto(comment);
            return new ResponseDto(HttpStatus.OK.value(), "댓글 수정에 성공",commentDto);
        }
    }

    public String deleteCommentResult(CustomUserDetails customUserDetails,Integer postId, Integer commentId) {
        Integer userId = customUserDetails.getUserId();
        User user =userJpa.findById(userId).orElseThrow(()->new NotFoundException("해당 유저를 찾을 수 없습니다."));
        CommentEntity commentById = commentJpa.findById(commentId).orElseThrow(()-> new NotFoundException("commentId : "+commentId+"에 해당하는 comment가 없습니다."));
        List<CommentEntity> commentEntitiesByUser = commentJpa.findAllByUser(user);

        PostEntity postById = postJpa.findById(postId).orElseThrow(()->new NotFoundException("postId : "+ postId+"에 해당하는 게시판이 없습니다."));
        List<CommentEntity> commentEntitiesByPost = commentJpa.findAllByPost(postById);
        if (!commentEntitiesByPost.contains(commentById)){
            throw new NotFoundException("해당 게시판의 댓글이 아닙니다.");
        }
        User userByPost= postById.getUser().orElseThrow(()->new NotFoundException("게시판 작성자에 해당하는 user가 없습니다."));
        //게시판 주인은 해당 게시판의 모든 댓글 삭제 가능
        if (userByPost.equals(user)){
            commentJpa.delete(commentById);
            return "댓글("+commentId+") 삭제에 성공하였습니다.";
        //게시판 주인이 아닌 사람은 자신의 댓글만 삭제 가능
        }else if (commentEntitiesByUser.contains(commentById)){
            commentJpa.delete(commentById);
            return "댓글("+commentId+") 삭제에 성공하였습니다.";
        //위에 조건도 해당하지 않는다면 유저의 댓글이 아니므로 삭제 실패
        }else return "해당 댓글은 "+user.getName()+"의 댓글이 아니라 삭제에 실패했습니다.";

    }
}
