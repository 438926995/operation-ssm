package com.eleme.test.com.eleme.test.dao;

/**
 * Created by sunwei on 16/1/15.
 */

import com.eleme.domain.mart.mail.MailSendQueueEntity;
import com.eleme.mapper.mart.mail.MailSendQueueMapper;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;

import javax.inject.Inject;

@DataSet
public class MailQueueDaoTest  extends UnitilsJUnit4{
    @Inject
    private MailSendQueueMapper mailSendQueueMapper;

    @Test
    public void testFindByName() throws Exception {

        MailSendQueueEntity sendQueueEntity = mailSendQueueMapper.selectMailSendQueueById(124);
        System.out.println("sendQueueEntity:"+sendQueueEntity.getMailContent());


    }
}
