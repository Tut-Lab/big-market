package com.tut.infrastructure.persistent.mapper;

import com.tut.domain.award.model.entity.UserAwardRecordEntity;
import com.tut.infrastructure.persistent.po.UserAwardRecord;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author zsj
 * @description
 * @date 2024/7/27 16:20
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface AwardMapStructMapper {
    AwardMapStructMapper INSTANCE = Mappers.getMapper( AwardMapStructMapper.class );

    UserAwardRecord UserAwardRecordEntityToUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);
}
