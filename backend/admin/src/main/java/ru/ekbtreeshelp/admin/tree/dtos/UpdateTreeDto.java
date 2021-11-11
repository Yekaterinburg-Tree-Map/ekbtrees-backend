package ru.ekbtreeshelp.admin.tree.dtos;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateTreeDto {
    private Long id;
    private String species;
    private String status;
    private Integer age;
}
