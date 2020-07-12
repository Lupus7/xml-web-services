package agentbackend.agentback.controller.dto;

public class ReCommentDTO {
    private String recomment;

    public ReCommentDTO(String recomment) {
        this.recomment = recomment;
    }

    public String getRecomment() {
        return recomment;
    }

    public void setRecomment(String recomment) {
        this.recomment = recomment;
    }
}
