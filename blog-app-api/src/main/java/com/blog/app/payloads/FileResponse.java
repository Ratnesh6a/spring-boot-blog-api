package com.blog.app.payloads;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileResponse {
   private String fileName;
    private String message;

}
