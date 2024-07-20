package com.tut.domain.activity.service;

import com.tut.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

/**
 * @author zsj
 * @description
 * @date 2024/7/20 13:01
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity{

    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }
}
