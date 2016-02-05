package com.eleme.test;

import javax.inject.Inject;

import org.junit.Test;

import com.eleme.util.mail.CustomMailSender;

public class TestSendEmail extends BaseTest {

  @Inject
  CustomMailSender customMailSender;

  @Test
  public void test() {
    customMailSender.sendTextMail("wei.sun02@ele.me", "主题", "内容");
  }

}
