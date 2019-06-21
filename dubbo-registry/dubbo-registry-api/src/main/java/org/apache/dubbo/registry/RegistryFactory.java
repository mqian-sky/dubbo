/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.registry;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * RegistryFactory. (SPI, Singleton, ThreadSafe)
 *
 * @see org.apache.dubbo.registry.support.AbstractRegistryFactory
 */
@SPI("dubbo")
public interface RegistryFactory {

    /**
     * Connect to the registry
     * <p>
     * Connecting the registry needs to support the contract: <br>
     * 1. When the check=false is set, the connection is not checked, otherwise the exception is thrown when disconnection <br>
     * 2. Support username:password authority authentication on URL.<br>
     * 3. Support the backup=10.20.153.10 candidate registry cluster address.<br>
     * 4. Support file=registry.cache local disk file cache.<br>
     * 5. Support the timeout=1000 request timeout setting.<br>
     * 6. Support session=60000 session timeout or expiration settings.<br>
     *
     * @param url Registry address, is not allowed to be empty
     * @return Registry reference, never return empty value
     *  连接到注册表
     * *连接注册表需要支持合同：<br>
     * * 1。如果设置check=false，则不检查连接，否则在断开连接时引发异常<br>
     * * 2。支持用户名：URL上的密码授权验证。<br>
     * * 3。支持备份=10.20.153.10候选注册表群集地址。<br>
     * * 4。支持文件=registry.cache本地磁盘文件缓存。<br>
     * * 5。支持超时=1000请求超时设置。<br>
     * * 6。支持会话=60000会话超时或过期设置。<br>
     * *@参数url注册表地址，不允许为空
     * *@返回注册表引用，从不返回空值
     */
    @Adaptive({"protocol"})
    Registry getRegistry(URL url);

}