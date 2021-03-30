package com.cls.toyDApp.common.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

public class HelperUtil {

	
	@Autowired
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();
	/**
	 * src/main/resources гою╖ url path return
	 */
	public static Path resourcesUrlPath(String fileName) { //throws Exception
		try {
			//ClassPathResource resource = new ClassPathResource(fileName);
			System.out.println("====> 11");
			//Path filePath = Paths.get(resource.getPath());\
			System.out.println("resource path: "+ resourceLoader.getResource("classpath:connection.yaml").exists());
			Path filePath = Paths.get(resourceLoader.getResource("classpath:connection.yaml").getURI());
			return filePath;
		} catch (Exception e) {
			System.out.println("resourcesUrlPath " + e.toString());
			return null;
		}
	}
}
