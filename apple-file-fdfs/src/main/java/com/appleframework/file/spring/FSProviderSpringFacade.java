/**
 * 
 */
package com.appleframework.file.spring;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.appleframework.file.provider.fdfs.FdfsProvider;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年1月7日
 */
public class FSProviderSpringFacade extends AbstractFSProviderSpringFacade implements InitializingBean, DisposableBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		Validate.isTrue(servers != null && servers.matches("^.+[:]\\d{1,5}\\s*$"), "[servers] is not valid");
		String[] serversArray = servers.split(",|;");
		fsProvider = new FdfsProvider(urlprefix, groupName, serversArray, connectTimeout, maxThreads);
	}

}
