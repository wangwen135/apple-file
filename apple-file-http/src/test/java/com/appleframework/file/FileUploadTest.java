package com.appleframework.file;

import java.io.File;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.file.spring.FSProviderSpringFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/*.xml" })
public class FileUploadTest {
	
	@Resource
	private FSProviderSpringFacade fsProvider;

	@Test
	public void testAddOpinion1() {
		try {
			String fileName = "ipdata.dat";
			String filePath = "C:\\Work\\data\\tools\\ip\\ipdata.dat";
			String url = fsProvider.upload(fileName, new File(filePath));
			System.out.println(url);
			//System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}