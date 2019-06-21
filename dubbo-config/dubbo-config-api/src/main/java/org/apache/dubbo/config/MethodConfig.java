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
package org.apache.dubbo.config;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.support.Parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.dubbo.config.Constants.ON_INVOKE_INSTANCE_KEY;
import static org.apache.dubbo.config.Constants.ON_INVOKE_METHOD_KEY;
import static org.apache.dubbo.config.Constants.ON_RETURN_INSTANCE_KEY;
import static org.apache.dubbo.config.Constants.ON_RETURN_METHOD_KEY;
import static org.apache.dubbo.config.Constants.ON_THROW_INSTANCE_KEY;
import static org.apache.dubbo.config.Constants.ON_THROW_METHOD_KEY;

/**
 * The method configuration
 * 方法配置
 *
 * @export
 */
public class MethodConfig extends AbstractMethodConfig {

    private static final long serialVersionUID = 884908855422675941L;

    /**
     * The method name
     * 方法名
     */
    private String name;

    /**
     * Stat
     * 统计
     */
    private Integer stat;

    /**
     * Whether to retry
     * 是否重试
     */
    private Boolean retry;

    /**
     * If it's reliable
     * 是否可靠的
     */
    private Boolean reliable;

    /**
     * Thread limits for method invocations
     * 方法调用的线程限制
     */
    private Integer executes;

    /**
     * If it's deprecated
     * 是否已弃用
     */
    private Boolean deprecated;

    /**
     * Whether to enable sticky
     * 是否需要粘性的
     */
    private Boolean sticky;

    /**
     * Whether need to return
     * 是否需要返回
     */
    private Boolean isReturn;

    /**
     * Callback instance when async-call is invoked
     * 调用异步调用时的回调实例
     */
    private Object oninvoke;

    /**
     * Callback method when async-call is invoked
     * 调用异步调用时的回调方法
     */
    private String oninvokeMethod;

    /**
     * Callback instance when async-call is returned
     * 返回异步调用时的回调实例
     */
    private Object onreturn;

    /**
     * Callback method when async-call is returned
     * 返回异步调用时的回调方法
     */
    private String onreturnMethod;

    /**
     * Callback instance when async-call has exception thrown
     * 异步调用引发异常时的回调实例
     */
    private Object onthrow;

    /**
     * Callback method when async-call has exception thrown
     * 异步调用引发异常时的回调方法
     */
    private String onthrowMethod;

    /**
     * The method arguments
     * 方法参数集合
     */
    private List<ArgumentConfig> arguments;

    /**
     * These properties come from MethodConfig's parent Config module,
     * they will neither be collected directly from xml or API nor be delivered to url
     * 这些属性来自methodconfig的父配置模块，
     * 它们既不会直接从XML或API收集，也不会传递到URL。
     */
    private String service;
    private String serviceId;

    @Parameter(excluded = true)
    public String getName() {
        return name;
    }

    public MethodConfig() {
    }

    public MethodConfig(Method method) {
        appendAnnotation(Method.class, method);

        this.setReturn(method.isReturn());

        if(!"".equals(method.oninvoke())){
            this.setOninvoke(method.oninvoke());
        }
        if(!"".equals(method.onreturn())){
            this.setOnreturn(method.onreturn());
        }
        if(!"".equals(method.onthrow())){
            this.setOnthrow(method.onthrow());
        }

        if (method.arguments() != null && method.arguments().length != 0) {
            List<ArgumentConfig> argumentConfigs = new ArrayList<ArgumentConfig>(method.arguments().length);
            this.setArguments(argumentConfigs);
            for (int i = 0; i < method.arguments().length; i++) {
                ArgumentConfig argumentConfig = new ArgumentConfig(method.arguments()[i]);
                argumentConfigs.add(argumentConfig);
            }
        }
    }

    public static List<MethodConfig> constructMethodConfig(Method[] methods) {
        if (methods != null && methods.length != 0) {
            List<MethodConfig> methodConfigs = new ArrayList<MethodConfig>(methods.length);
            for (int i = 0; i < methods.length; i++) {
                MethodConfig methodConfig = new MethodConfig(methods[i]);
                methodConfigs.add(methodConfig);
            }
            return methodConfigs;
        }
        return Collections.emptyList();
    }

    public void setName(String name) {
        checkMethodName("name", name);
        this.name = name;
        if (StringUtils.isEmpty(id)) {
            id = name;
        }
    }

    public Integer getStat() {
        return stat;
    }

    @Deprecated
    public void setStat(Integer stat) {
        this.stat = stat;
    }

    @Deprecated
    public Boolean isRetry() {
        return retry;
    }

    @Deprecated
    public void setRetry(Boolean retry) {
        this.retry = retry;
    }

    @Deprecated
    public Boolean isReliable() {
        return reliable;
    }

    @Deprecated
    public void setReliable(Boolean reliable) {
        this.reliable = reliable;
    }

    public Integer getExecutes() {
        return executes;
    }

    public void setExecutes(Integer executes) {
        this.executes = executes;
    }

    public Boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public List<ArgumentConfig> getArguments() {
        return arguments;
    }

    @SuppressWarnings("unchecked")
    public void setArguments(List<? extends ArgumentConfig> arguments) {
        this.arguments = (List<ArgumentConfig>) arguments;
    }

    public Boolean getSticky() {
        return sticky;
    }

    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    @Parameter(key = ON_RETURN_INSTANCE_KEY, excluded = true, attribute = true)
    public Object getOnreturn() {
        return onreturn;
    }

    public void setOnreturn(Object onreturn) {
        this.onreturn = onreturn;
    }

    @Parameter(key = ON_RETURN_METHOD_KEY, excluded = true, attribute = true)
    public String getOnreturnMethod() {
        return onreturnMethod;
    }

    public void setOnreturnMethod(String onreturnMethod) {
        this.onreturnMethod = onreturnMethod;
    }

    @Parameter(key = ON_THROW_INSTANCE_KEY, excluded = true, attribute = true)
    public Object getOnthrow() {
        return onthrow;
    }

    public void setOnthrow(Object onthrow) {
        this.onthrow = onthrow;
    }

    @Parameter(key = ON_THROW_METHOD_KEY, excluded = true, attribute = true)
    public String getOnthrowMethod() {
        return onthrowMethod;
    }

    public void setOnthrowMethod(String onthrowMethod) {
        this.onthrowMethod = onthrowMethod;
    }

    @Parameter(key = ON_INVOKE_INSTANCE_KEY, excluded = true, attribute = true)
    public Object getOninvoke() {
        return oninvoke;
    }

    public void setOninvoke(Object oninvoke) {
        this.oninvoke = oninvoke;
    }

    @Parameter(key = ON_INVOKE_METHOD_KEY, excluded = true, attribute = true)
    public String getOninvokeMethod() {
        return oninvokeMethod;
    }

    public void setOninvokeMethod(String oninvokeMethod) {
        this.oninvokeMethod = oninvokeMethod;
    }

    public Boolean isReturn() {
        return isReturn;
    }

    public void setReturn(Boolean isReturn) {
        this.isReturn = isReturn;
    }

    @Parameter(excluded = true)
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Parameter(excluded = true)
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * service and name must not be null.
     *
     * @return
     */
    @Override
    @Parameter(excluded = true)
    public String getPrefix() {
        return CommonConstants.DUBBO + "." + service
                + (StringUtils.isEmpty(serviceId) ? "" : ("." + serviceId))
                + "." + getName();
    }
}
