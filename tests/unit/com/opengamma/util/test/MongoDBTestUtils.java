/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.util.test;


/**
 * Utility methods for working with MongoDB in testing contexts.
 *
 * @author kirk
 */
public final class MongoDBTestUtils {
  public static final String TEST_DB_HOST = "mongodb-lx-1.hq.opengamma.com";
  //public static final String TEST_DB_HOST = "localhost";
  
  private MongoDBTestUtils() {
  }
  
  public static MongoDBConnectionSettings makeTestSettings(String testName, boolean makeUnique) {
    MongoDBConnectionSettings settings = new MongoDBConnectionSettings();
    settings.setHostName(TEST_DB_HOST);
    settings.setPort(27017);
    String dbName = System.getProperty("user.name") + "-unit";
    String collectionName = testName;
    if(makeUnique) {
      collectionName += "-" + System.currentTimeMillis();
    }
    settings.setDatabaseName(dbName);
    settings.setCollectionName(collectionName);
    return settings;
  }

}
