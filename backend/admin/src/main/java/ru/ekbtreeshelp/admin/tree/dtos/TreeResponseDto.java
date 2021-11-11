package ru.ekbtreeshelp.admin.tree.dtos;

import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TreeResponseDto {
    private Long id;
    private Date creationDate;
    private String species;
}
