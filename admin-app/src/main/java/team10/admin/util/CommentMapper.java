package team10.admin.util;

import team10.admin.models.Comment;
import team10.admin.models.dto.CommentDTO;

public class CommentMapper {
    public static CommentDTO toDTO(Comment comment) {
        CommentDTO retVal = new CommentDTO();
        retVal.setUser(comment.getUser().getEmail());
        retVal.setCar(comment.getCar().getBrand().getName() + " " + comment.getCar().getModel().getName());
        retVal.setCarImage(comment.getCar().getImages().get(0).getBase64());
        retVal.setCarId(comment.getCar().getId());
        retVal.setCommentId(comment.getId());
        retVal.setBodyComment(comment.getBody());
        retVal.setCommentCreated(comment.getCreated());
        return retVal;
    }
}
