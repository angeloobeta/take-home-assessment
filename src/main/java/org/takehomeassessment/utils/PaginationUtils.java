package org.takehomeassessment.utils;

import org.takehomeassessment.data.dtos.response.PageInfoDto;
import org.takehomeassessment.data.dtos.response.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtils {



    public PaginationResponse<Object> mapPaginationResponseToDto(Page pageInfo, Object data){
        // pageInfo builder
        PageInfoDto page = PageInfoDto.builder()
                .pageNo(pageInfo.getNumber())
                .totalElements(pageInfo.getTotalElements())
                .pageSize(pageInfo.getSize())
                .totalPages(pageInfo.getTotalPages())
                .isLastPage(pageInfo.isLast())
                .build();

        return PaginationResponse.builder()
                .meta(page)
                .items(data)
                .build();
    }
}
