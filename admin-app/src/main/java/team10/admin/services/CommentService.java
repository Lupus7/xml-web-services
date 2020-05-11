package team10.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team10.admin.models.Comment;
import team10.admin.models.dto.CommentDTO;
import team10.admin.repositories.CommentRepository;
import team10.admin.util.CommentMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public List<CommentDTO> getAll() {
        return commentRepository.findAllByApproved(false)
                .stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean approve(Long id) {
        Comment comment = commentRepository.findOneById(id);
        if (comment == null)
            return false;

        comment.setApproved(true);
        commentRepository.save(comment);
        return true;
    }

    public boolean delete(Long id) {
        Comment comment = commentRepository.findOneById(id);
        if (comment == null)
            return false;

        commentRepository.delete(comment);
        return true;
    }
}
