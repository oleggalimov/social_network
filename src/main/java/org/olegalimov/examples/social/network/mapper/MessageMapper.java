package org.olegalimov.examples.social.network.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.olegalimov.examples.social.network.dto.MessageDto;
import org.olegalimov.examples.social.network.entity.Message;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class MessageMapper {

    @Mapping(target = "from", source = "fromUserId")
    @Mapping(target = "to", source = "toUserId")
    public abstract MessageDto toMessageDto(Message message);
}
