package coursework.cashflow.models.dto;

import lombok.*;
import coursework.cashflow.models.dao.Category;
import coursework.cashflow.models.dao.Tag;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoriesTagsDTO {
    private List<Category> categories;
    private List<Tag> tags;
}
