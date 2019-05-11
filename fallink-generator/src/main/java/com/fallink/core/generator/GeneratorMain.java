/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved.
 */

/*
 * 修订记录：
 * woniu@yiji.com 2017年05月22日 16:12:45 创建
 */
package com.fallink.core.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.nio.cs.Surrogate;

import java.util.ArrayList;
import java.util.List;

public class GeneratorMain {

	private static final Logger logger = LoggerFactory.getLogger(GeneratorMain.class);

	public static void main(String[] args) {
		try {
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(
					Surrogate.Generator.class.getResourceAsStream("/generatorConfig.xml"));
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(new ProgressCallback() {
				@Override public void introspectionStarted(int totalTasks) {
				}

				@Override public void generationStarted(int totalTasks) {
				}

				@Override public void saveStarted(int totalTasks) {
				}

				@Override public void startTask(String taskName) {
					logger.info(taskName);
				}

				@Override public void done() {
					logger.info("生成完成!");
				}

				@Override public void checkCancel() throws InterruptedException {
				}
			});
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}