package org.example.models.entity;

import lombok.Data;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@Data
@MappedSuperclass
public class BaseEntity {
    @Transient
    private boolean edit;
    @Transient
    private boolean deleted;
}
