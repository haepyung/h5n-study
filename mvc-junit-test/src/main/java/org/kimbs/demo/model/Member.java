package org.kimbs.demo.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Name field is required")
    @Length(max = 36, message = "Should be name field length less than 36")
    private String name;

    @Email(message = "Wrong email format")
    private String email;

    @Min(value = 0, message = "Should be score field value more than 0")
    @Max(value = 100, message = "Should be score field value less than 100")
    private int score;
}
