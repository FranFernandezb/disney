package com.alkemy.disney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieJsonResponse {

    private String image;

    private String tittle;

    private String creationDate;
}
