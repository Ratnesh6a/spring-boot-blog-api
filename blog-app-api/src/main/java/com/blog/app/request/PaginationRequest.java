package com.blog.app.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Setter
@AllArgsConstructor

public class PaginationRequest {



    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
    public PaginationRequest() {
        this.pageNumber = 0; // Set default value for pageNumber
        this.pageSize = 1; // Set default value for pageSize
    }
}
