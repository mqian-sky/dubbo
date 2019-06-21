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

import java.util.List;

/**
 * RegistryService. (SPI, Prototype, ThreadSafe)
 *
 * @see org.apache.dubbo.registry.Registry
 * @see org.apache.dubbo.registry.RegistryFactory#getRegistry(URL)
 */
public interface RegistryService {

    /**
     * Register data, such as : provider service, consumer address, route rule, override rule and other data.
     * <p>
     * Registering is required to support the contract:<br>
     * 1. When the URL sets the check=false parameter. When the registration fails,
     *      the exception is not thrown and retried in the background. Otherwise, the exception will be thrown.<br>
     * 2. When URL sets the dynamic=false parameter, it needs to be stored persistently, otherwise,
     *      it should be deleted automatically when the registrant has an abnormal exit.<br>
     * 3. When the URL sets category=routers, it means classified storage, the default category is providers,
     *      and the data can be notified by the classified section. <br>
     * 4. When the registry is restarted, network jitter, data can not be lost,
     *      including automatically deleting data from the broken line.<br>
     * 5. Allow URLs which have the same URL but different parameters to coexist,they can't cover each other.<br>
     *
     * @param url  Registration information , is not allowed to be empty,
     *             e.g: dubbo://10.20.153.10/org.apache.dubbo.foo.BarService?version=1.0.0&application=kylin
     *
     * *注册数据，例如：提供者服务、使用者地址、路由规则、覆盖规则和其他数据。
     * *需要注册才能支持合同：<br>
     * * 1。当URL设置check=false参数时。当注册失败时，不会在后台引发并重试异常。否则，将引发异常。<br>
     * * 2。当url设置dynamic=false参数时，需要持久保存，否则注册者异常退出时，应该自动删除该参数。<br>
     * * 3。当url设置category=routers时，表示分类存储，默认分类为providers，分类部分可以通知数据。<BR>
     * * 4。当注册表重新启动时，网络抖动，数据不能丢失，包括自动从断线删除数据。<br>
     * * 5。允许具有相同URL但参数不同的URL共存，它们不能相互覆盖。<br>
     * *@参数url注册信息，不允许为空，例如：dubbo://10.20.153.10/org.apache.dubbo.foo.barservice?version=1.0.0&application=Kylin
     *
     */
    void register(URL url);

    /**
     * Unregister
     * <p>
     * Unregistering is required to support the contract:<br>
     * 1. If it is the persistent stored data of dynamic=false, the registration data can not be found, then the IllegalStateException is thrown, otherwise it is ignored.<br>
     * 2. Unregister according to the full url match.<br>
     *
     * @param url Registration information , is not allowed to be empty, e.g: dubbo://10.20.153.10/org.apache.dubbo.foo.BarService?version=1.0.0&application=kylin
     *
     * *注销
     * *需要注销才能支持合同：<br>
     * * 1。如果是dynamic=false的持久存储数据，则找不到注册数据，则引发illegalStateException，否则忽略它。<br>
     * * 2。根据完整的URL匹配取消注册。<br>
     * *@参数url注册信息，不允许为空，例如：dubbo://10.20.153.10/org.apache.dubbo.foo.barservice?version=1.0.0&application=Kylin
     */
    void unregister(URL url);

    /**
     * Subscribe to eligible registered data and automatically push when the registered data is changed.
     * <p>
     * Subscribing need to support contracts:<br>
     * 1. When the URL sets the check=false parameter. When the registration fails, the exception is not thrown and retried in the background. <br>
     * 2. When URL sets category=routers, it only notifies the specified classification data. Multiple classifications are separated by commas, and allows asterisk to match, which indicates that all categorical data are subscribed.<br>
     * 3. Allow interface, group, version, and classifier as a conditional query, e.g.: interface=org.apache.dubbo.foo.BarService&version=1.0.0<br>
     * 4. And the query conditions allow the asterisk to be matched, subscribe to all versions of all the packets of all interfaces, e.g. :interface=*&group=*&version=*&classifier=*<br>
     * 5. When the registry is restarted and network jitter, it is necessary to automatically restore the subscription request.<br>
     * 6. Allow URLs which have the same URL but different parameters to coexist,they can't cover each other.<br>
     * 7. The subscription process must be blocked, when the first notice is finished and then returned.<br>
     *
     * @param url      Subscription condition, not allowed to be empty, e.g. consumer://10.20.153.10/org.apache.dubbo.foo.BarService?version=1.0.0&application=kylin
     * @param listener A listener of the change event, not allowed to be empty
     *
     * *订阅符合条件的注册数据，并在注册数据更改时自动推送。
     * *订阅需要支持合同：<br>
     * * 1。当URL设置check=false参数时。当注册失败时，不会在后台引发并重试异常。<BR>
     * * 2。当url设置category=routers时，它只通知指定的分类数据。多个分类用逗号分隔，并允许星号匹配，这表示订阅了所有分类数据。<br>
     * * 3。允许将接口、组、版本和分类器作为条件查询，例如：interface=org.apache.dubbo.foo.barservice&version=1.0.0<br>
     * * 4。查询条件允许星号匹配，订阅所有接口所有包的所有版本，如：interface=*&group=*&version=*&classifier=*<br>
     * * 5。重新启动注册表和网络抖动时，需要自动恢复订阅请求。<br>
     * * 6。允许具有相同URL但参数不同的URL共存，它们不能相互覆盖。<br>
     * * 7。当第一个通知完成后，必须阻止订阅过程，然后返回。<br>
     *
     * *@参数url订阅条件，不允许为空，如consumer://10.20.153.10/org.apache.dubbo.foo.barservice?version=1.0.0&application=Kylin
     * *@param listener更改事件的侦听器，不允许为空
     */
    void subscribe(URL url, NotifyListener listener);

    /**
     * Unsubscribe
     * <p>
     * Unsubscribing is required to support the contract:<br>
     * 1. If don't subscribe, ignore it directly.<br>
     * 2. Unsubscribe by full URL match.<br>
     * * 1。如果不订阅，直接忽略它。<br>
     * * 2。通过完全URL匹配取消订阅。<br>
     *
     * @param url      Subscription condition, not allowed to be empty, e.g. consumer://10.20.153.10/org.apache.dubbo.foo.BarService?version=1.0.0&application=kylin
     * @param listener A listener of the change event, not allowed to be empty
     */
    void unsubscribe(URL url, NotifyListener listener);

    /**
     * Query the registered data that matches the conditions. Corresponding to the push mode of the subscription, this is the pull mode and returns only one result.
     *
     * @param url Query condition, is not allowed to be empty, e.g. consumer://10.20.153.10/org.apache.dubbo.foo.BarService?version=1.0.0&application=kylin
     * @return The registered information list, which may be empty, the meaning is the same as the parameters of {@link org.apache.dubbo.registry.NotifyListener#notify(List<URL>)}.
     * @see org.apache.dubbo.registry.NotifyListener#notify(List)
     *
     * *查询符合条件的注册数据。对应于订阅的推送模式，这是拉模式，只返回一个结果。
     * *@参数url查询条件，不允许为空，如consumer://10.20.153.10/org.apache.dubbo.foo.barservice?version=1.0.0&application=Kylin
     * *@返回注册信息列表，该列表可能为空，其含义与@link org.apache.dubbo.registry.notifyListener notify（list<url>）的参数相同。
     * *@见org.apache.dubbo.registry.notifyListener notify（列表）
     *
     */
    List<URL> lookup(URL url);

}