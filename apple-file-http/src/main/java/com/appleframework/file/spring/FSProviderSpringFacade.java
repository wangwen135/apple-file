/**
 * 
 */
package com.appleframework.file.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.appleframework.file.provider.http.HttpProvider;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年1月7日
 */
public class FSProviderSpringFacade extends AbstractFSProviderSpringFacade implements InitializingBean, DisposableBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		fsProvider = new HttpProvider(urlprefix, groupName);
	}

}
