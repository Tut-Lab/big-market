package com.tut.infrastructure.persistent.mapper;

import com.tut.domain.award.model.entity.UserAwardRecordEntity;
import com.tut.domain.task.model.entity.TaskEntity;
import com.tut.infrastructure.persistent.po.Task;
import com.tut.infrastructure.persistent.po.UserAwardRecord;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author zsj
 * @description
 * @date 2024/7/27 16:20
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface TaskMapStructMapper {
    TaskMapStructMapper INSTANCE = Mappers.getMapper( TaskMapStructMapper.class );

    List<TaskEntity> TaskToTaskEntity(List<Task> tasks);
}
