package org.olegalimov.examples.social.network.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.olegalimov.examples.social.network.dto.PostDto;
import org.olegalimov.examples.social.network.entity.Post;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class PostMapper {
    public abstract PostDto toPostDto(Post postEntity);

}
