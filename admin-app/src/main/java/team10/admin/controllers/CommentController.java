package team10.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.admin.models.dto.ClientDTO;
import team10.admin.models.dto.CommentDTO;
import team10.admin.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> retVal = commentService.getAll();
        if (retVal != null)
            return ResponseEntity.ok(retVal);
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> approveComment(@PathVariable("id") Long id) {
        if (commentService.approve(id))
            return ResponseEntity.ok("Comment approved");
        return ResponseEntity.badRequest().body("Invalid request");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id) {
        if (commentService.delete(id))
            return ResponseEntity.ok("Comment deleted");
        return ResponseEntity.badRequest().body("Invalid request");
    }
}
