package org.example.dto;

import lombok.Data;
import java.util.List;


@Data
public class Response<T, E> {
    private T request;
    private List<Info> infos;
    private List<Error> errors;
    private List<E> data;
}
