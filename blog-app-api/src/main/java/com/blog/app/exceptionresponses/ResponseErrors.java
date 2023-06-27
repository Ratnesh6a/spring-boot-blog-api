package com.blog.app.exceptionresponses;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ResponseErrors {

    private String message;
    private HttpStatus status;

    private String statusCode;



    public  String toString(){
        return  "Response{"+"statusCode='"+statusCode+'\''+
                ",Http status="+status+", Message='"+message+'\''+'}';
    }


}
