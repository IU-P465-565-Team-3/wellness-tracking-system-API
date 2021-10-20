package com.wellness.tracking.model;
import lombok.*;
import lombok.experimental.Accessors;
import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Role {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column
    private String name;
}
