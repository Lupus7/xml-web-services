package team10.admin.models.dto;

import java.time.LocalDateTime;

public class CommentDTO {
    String carImage;
    String car;
    Long carId;
    Long commentId;
    String user;
    LocalDateTime commentCreated;
    String bodyComment;

    public CommentDTO() {
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getCommentCreated() {
        return commentCreated;
    }

    public void setCommentCreated(LocalDateTime commentCreated) {
        this.commentCreated = commentCreated;
    }

    public String getBodyComment() {
        return bodyComment;
    }

    public void setBodyComment(String bodyComment) {
        this.bodyComment = bodyComment;
    }
}
