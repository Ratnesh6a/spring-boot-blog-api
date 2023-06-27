package com.blog.app.payloads;

import com.blog.app.entity.Post;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    private int comments_id;

    private String content;

}
