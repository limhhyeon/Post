package com.github.postsolo.service;

import com.github.postsolo.repository.Post.PostEntity;
import com.github.postsolo.repository.Post.PostJpa;
import com.github.postsolo.repository.comment.CommentEntity;
import com.github.postsolo.repository.comment.CommentJpa;
import com.github.postsolo.repository.like.LikeEntity;
import com.github.postsolo.repository.like.LikeJpa;
import com.github.postsolo.repository.userDeatail.CustomUserDetails;
import com.github.postsolo.repository.users.User;
import com.github.postsolo.repository.users.UserJpa;
import com.github.postsolo.service.exception.NotFoundException;
import com.github.postsolo.web.Dto.ResponseDto;
import com.github.postsolo.web.Dto.comment.CommentDto;
import com.github.postsolo.web.Dto.post.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.control.MappingControl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final UserJpa userJpa;
    private final PostJpa postJpa;
    private final CommentJpa commentJpa;
    private final LikeJpa likeJpa;


    public ResponseDto createPostResult(CustomUserDetails customUserDetails, PostCreateDto postCreateDto) {
        // 유저를 찾는다
        Integer userId =customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()-> new NotFoundException("해당 유저는 찾을 수 없습니다."));
        try {
            // 찾은 유저로 포스트 생성
            PostEntity post = PostEntity.builder()
                    .user(user)
                    .title(postCreateDto.getTitle())
                    .name(user.getName())
                    .content(postCreateDto.getContent())
                    .createAt(LocalDateTime.now())
                    .likenCnt(0)
                    .build();
            postJpa.save(post);
            PostResponse postResponse = new PostResponse(post);
            return new ResponseDto(HttpStatus.OK.value(), "게시판이 성공적으로 생성되었습니다.",postResponse);
        }catch (Exception e){
            return new ResponseDto(HttpStatus.BAD_REQUEST.value(), "게시판 생성에 실패했습니다.");
        }

    }

    public ResponseDto getAllPostResult() {
        List<PostEntity> postEntities =postJpa.findAll();
        List<PostAllResponse> postAllResponses =postEntities.stream().map(PostAllResponse::new).collect(Collectors.toList());
        return new ResponseDto(HttpStatus.OK.value(), "게시판 조회 성공",postAllResponses);
    }

    public ResponseDto getPostByPostIdResult(CustomUserDetails customUserDetails,Integer postId) {
        Integer userId =customUserDetails.getUserId();
        User user =userJpa.findById(userId).orElseThrow(()->new NotFoundException("해당하는 유저가 없습니다."));
        List<PostEntity> postEntitiesByUser = postJpa.findAllByUser(user);
        PostEntity post = postJpa.findById(postId)
                .orElseThrow(()-> new NotFoundException("해당 게시판을 찾을 수 없습니다."));
        List<CommentEntity> commentEntityList = commentJpa.findAllByPost(post);
        if (postEntitiesByUser.contains(post)){
            List<CommentDto> commentDtos=commentEntityList.stream().map(CommentDto::new).collect(Collectors.toList());
            PostDetailResponse postDetailService = PostDetailResponse.builder()
                    .title(post.getTitle())
                    .name(post.getName())
                    .content(post.getContent())
                    .createAt(post.getCreateAt())
                    .likeCnt(post.getLikenCnt())
                    .comments(commentDtos)
                    .build();
            return new ResponseDto(HttpStatus.OK.value(), " 게시글 : "+post.getTitle()+" 조회 성공",postDetailService);
        } else {
            List<CommentDto> commentDtos=commentEntityList.stream().map(CommentDto::new).toList();
            List<CommentDto> commentDtosByUser =commentEntityList.stream().filter((comment)->comment.getUser().equals(user)).map(CommentDto::new).toList();
            List<CommentDto> commentDtosByStatus=commentDtos.stream().map((comment)->
                    {if (!comment.getStatus()&&!commentDtosByUser.contains(comment)){
                        comment.setContent("비공개 댓글입니다.");
                        return comment;
                    }else {
                        return comment;
                    }
                    }).toList();
            PostDetailResponse postDetailService = PostDetailResponse.builder()
                    .title(post.getTitle())
                    .name(post.getName())
                    .content(post.getContent())
                    .createAt(post.getCreateAt())
                    .likeCnt(post.getLikenCnt())
                    .comments(commentDtosByStatus)
                    .build();
            return new ResponseDto(HttpStatus.OK.value(), " 게시글 : "+post.getTitle()+" 조회 성공",postDetailService);
        }



    }

    public ResponseDto getPostAllByKeywordResult(String keyword) {
       String keywordLowerCase = keyword.toLowerCase();

      List<PostEntity> postEntities =postJpa.findByKeyword(keywordLowerCase);
      if (postEntities.isEmpty()){
          throw new NotFoundException("키워드에 대한 게시물 결과가 없습니다.");
      }
      List<PostAllResponse> postAllResponses =postEntities.stream().map(PostAllResponse::new).collect(Collectors.toList());

      return new ResponseDto(HttpStatus.OK.value(),keyword+"에 대한 게시글 조회 성공", postAllResponses);
    }

    public ResponseDto updatePostResult(CustomUserDetails customUserDetails, Integer postId, PostCreateDto postCreateDto) {
        Integer userId = customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당하는 유저가 없습니다."));
        PostEntity post = postJpa.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시판 Id: " + postId + "에 해당하는 게시판이 존재하지 않습니다."));
        //유저의 게시판이어야만 변경 가능
        List<PostEntity> postEntityListByUser = postJpa.findAllByUser(user);
        if (!postEntityListByUser.contains(post)) {
            throw new NotFoundException("게시판의 주인이 아니라 수정이 불가능합니다.");
        } else {
            try {
                post.setTitle(postCreateDto.getTitle());
                post.setContent(postCreateDto.getContent());

                PostResponse postResponse = new PostResponse(post);
                return new ResponseDto(HttpStatus.OK.value(), "게시판 수정이 성공했습니다.", postResponse);
            } catch (Exception e) {
                return new ResponseDto(HttpStatus.BAD_REQUEST.value(), "게시판 수정에 실패했습니다.");
            }

        }
    }

    public ResponseDto deletePostResult(CustomUserDetails customUserDetails, Integer postId) {
        Integer userId =customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()-> new NotFoundException("userId: " +userId+"에 해당하는 유저를 찾을 수 없습니다."));
        PostEntity post = postJpa.findById(postId)
                .orElseThrow(()-> new NotFoundException("postId: "+postId+"에 해당하는 게시판이 존재하지 않습니다."));
        List<PostEntity> postEntityListByUser = postJpa.findAllByUser(user);
        //찾은 post가 해당 user의 post여야만 삭제 가능
        if (!postEntityListByUser.contains(post)){
            throw new NotFoundException("해당 게시판은 user("+user.getName()+")의 게시판이 아닙니다.");
        }else {
            try{
                List<CommentEntity> commentEntityListByPost = commentJpa.findAllByPost(post);
                commentJpa.deleteAll(commentEntityListByPost);
                postJpa.delete(post);
                return new ResponseDto(HttpStatus.OK.value(), "게시판: "+post.getTitle()+"이 삭제되었습니다.");

            }catch (Exception e){
                return new ResponseDto(HttpStatus.BAD_REQUEST.value(), "게시판 삭제에 실패하셨습니다.");
            }

        }
    }
    @Transactional
    public String likePostResult(CustomUserDetails customUserDetails, Integer postId) {
        Integer userId = customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()-> new NotFoundException("해당하는 유저를 찾을 수 없습니다."));
        PostEntity post= postJpa.findById(postId)
                .orElseThrow(()->new NotFoundException("해당 게시판을 찾을 수 없습니다."));
        Optional<LikeEntity> likeEntity=likeJpa.findByUserAndPost(user,post);
        if (!likeEntity.isPresent()){
            post.setLikenCnt(post.getLikenCnt()+1);
            LikeEntity like = LikeEntity.builder()
                    .post(post)
                    .user(user)
                    .build();
            likeJpa.save(like);
            return "게시판("+postId+") 좋아요 누르기 성공하였습니다.";
        }else if (likeEntity.isPresent()){
            post.setLikenCnt(post.getLikenCnt()-1);
            LikeEntity like = likeEntity.get();
            likeJpa.delete(like);
            return "게시판("+postId+") 좋아요 취소하였습니다.";
        }else return "게시판("+postId+") 좋아요 누르기 실패하였습니다.";
    }
}
