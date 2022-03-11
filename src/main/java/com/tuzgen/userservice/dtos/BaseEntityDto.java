package com.tuzgen.userservice.dtos;

import com.tuzgen.userservice.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseEntityDto {
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public BaseEntityDto(BaseEntity entity) {
        id = entity.getId();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
    }
}
