/**
 * 
 */
package com.zaxxer.hikari;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Copyright (C) 2013 Brett Wooldridge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class HikariConfig
{
    private int minPoolSize;
    private int maxPoolSize;
    private String connectionUrl;
    private int acquireIncrement;
    private int acquireRetries;
    private int acquireRetryDelay;
    private int connectionTimeout;
    private String connectionTestQuery;
    private String dataSourceClassName;
    private String proxyFactoryType;
    private boolean isJdbc4connectionTest;
    private int maxLifetime;
    private int leakDetectionThreshold;
    private int idleTimeout;

    /**
     * Default constructor
     */
    public HikariConfig()
    {
        acquireIncrement = 1;
        maxPoolSize = 1;
        connectionTimeout = Integer.MAX_VALUE;
        idleTimeout = (int) TimeUnit.MINUTES.toMillis(30);
        proxyFactoryType = "auto";
    }

    public int getAcquireIncrement()
    {
        return acquireIncrement;
    }

    public void setAcquireIncrement(int acquireIncrement)
    {
        if (acquireIncrement < 1)
        {
            throw new IllegalArgumentException("acquireRetries cannot be less than 1");
        }
        this.acquireIncrement = acquireIncrement;
    }

    public int getAcquireRetries()
    {
        return acquireRetries;
    }

    public void setAcquireRetries(int acquireRetries)
    {
        if (acquireRetries < 0)
        {
            throw new IllegalArgumentException("acquireRetries cannot be negative");
        }
        this.acquireRetries = acquireRetries;
    }

    public int getAcquireRetryDelay()
    {
        return acquireRetryDelay;
    }

    public void setAcquireRetryDelay(int acquireRetryDelayMs)
    {
        if (acquireRetryDelayMs < 0)
        {
            throw new IllegalArgumentException("acquireRetryDelay cannot be negative");
        }
        this.acquireRetryDelay = acquireRetryDelayMs;
    }

    public String getConnectionTestQuery()
    {
        return connectionTestQuery;
    }

    public void setConnectionTestQuery(String connectionTestQuery)
    {
        this.connectionTestQuery = connectionTestQuery;
    }

    public int getConnectionTimeout()
    {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeoutMs)
    {
        if (connectionTimeoutMs < 0)
        {
            throw new IllegalArgumentException("connectionTimeout cannot be negative");
        }
        this.connectionTimeout = connectionTimeoutMs;
    }

    public String getConnectionUrl()
    {
        return connectionUrl;
    }

    public void setConnectionUrl(String url)
    {
        this.connectionUrl = url;
    }

    public String getDataSourceClassName()
    {
        return dataSourceClassName;
    }

    public void setDataSourceClassName(String className)
    {
        this.dataSourceClassName = className;
    }

    public int getIdleTimeout()
    {
        return idleTimeout;
    }

    public void setIdleTimeout(int idleTimeoutMs)
    {
        this.idleTimeout = idleTimeoutMs;
    }

    public boolean isJdbc4ConnectionTest()
    {
        return isJdbc4connectionTest;
    }

    public void setJdbc4ConnectionTest(boolean useIsValid)
    {
        this.isJdbc4connectionTest = useIsValid;
    }

    public int getLeakDetectionThreshold()
    {
        return leakDetectionThreshold;
    }

    public void setLeakDetectionThreshold(int leakDetectionThresholdMs)
    {
        this.leakDetectionThreshold = leakDetectionThresholdMs; 
    }

    public int getMaxLifetime()
    {
        return maxLifetime;
    }

    public void setMaxLifetime(int maxLifetimeMs)
    {
        this.maxLifetime = maxLifetimeMs;
    }

    public int getMinimumPoolSize()
    {
        return minPoolSize;
    }

    public void setMinimumPoolSize(int minPoolSize)
    {
        if (minPoolSize < 0)
        {
            throw new IllegalArgumentException("minPoolSize cannot be negative");
        }
        this.minPoolSize = minPoolSize;
    }

    public int getMaximumPoolSize()
    {
        return maxPoolSize;
    }

    public void setMaximumPoolSize(int maxPoolSize)
    {
        if (maxPoolSize < 0)
        {
            throw new IllegalArgumentException("maxPoolSize cannot be negative");
        }
        this.maxPoolSize = maxPoolSize;
    }

    public String getProxyFactoryType()
    {
        return proxyFactoryType;
    }

    public void setProxyFactoryType(String proxyFactoryClassName)
    {
        this.proxyFactoryType = proxyFactoryClassName;
    }

    public void validate()
    {
        Logger logger = LoggerFactory.getLogger(getClass());

        if (!isJdbc4connectionTest && connectionTestQuery == null)
        {
            logger.error("Either jdbc4ConnectionTest must be enabled or a connectionTestQuery must be specified.");
            throw new IllegalStateException("Either jdbc4ConnectionTest must be enabled or a connectionTestQuery must be specified.");
        }

        if (maxPoolSize < minPoolSize)
        {
            logger.warn("maxPoolSize is less than minPoolSize, forcing them equal.");
            maxPoolSize = minPoolSize;
        }

        if (proxyFactoryType == null)
        {
            logger.error("proxyFactoryType cannot be null");
            throw new IllegalStateException("proxyFactoryType cannot be null");
        }
    }
}