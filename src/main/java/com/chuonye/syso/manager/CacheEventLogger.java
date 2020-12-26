package com.chuonye.syso.manager;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存事件日志记录
 * 
 * @author chuonye@foxmail.com
 */
public class CacheEventLogger implements CacheEventListener<Object, Object> {

  final static Logger logger = LoggerFactory.getLogger(CacheEventLogger.class);

  @Override
  public void onEvent(CacheEvent<? extends Object, ? extends Object> event) {
      if (logger.isDebugEnabled()) {
          logger.debug("===Event: " + event.getType() + " Key: " + event.getKey() + " old value: " + event.getOldValue()
              + " new value: " + event.getNewValue());
      }
  }

}
