/**
 * 
 */
package com.appleframework.file.spring;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.appleframework.file.provider.qiniu.QiniuProvider;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年1月7日
 */
public class FSProviderSpringFacade extends AbstractFSProviderSpringFacade implements InitializingBean, DisposableBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		Validate.notBlank(accessKey, "[accessKey] not defined");
		Validate.notBlank(secretKey, "[secretKey] not defined");
		fsProvider = new QiniuProvider(urlprefix, groupName, accessKey, secretKey, privated);
	}

}
