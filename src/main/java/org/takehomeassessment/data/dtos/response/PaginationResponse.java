package org.takehomeassessment.data.dtos.response;

import lombok.*;

@Setter @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> {
    private PageInfoDto meta;
    private T items;
}
