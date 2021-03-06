package com.tencent.jungle.db.bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.guice.transactional.Isolation;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.tencent.jungle.db.JungleDatabaseModule;
import com.tencent.jungle.db.mapper.TestMapper;
import com.tencent.jungle.db.transaction.DBTransactional;

public class BootstrapTest {

	@Inject
	private TestMapper test;

	public static void main(String[] args) {
		System.out.println("hello,world");
		List<Module> modules = loadModule();
		Injector injector = Guice.createInjector(modules);

		BootstrapTest app = injector.getInstance(BootstrapTest.class);
		long start = System.currentTimeMillis();
		app.dotest();
	}

	private static List<Module> loadModule() {
		List<Module> modules = new ArrayList<Module>();
		modules.add(new JungleDatabaseModule());
		return modules;
	}

	@DBTransactional(isolation = Isolation.READ_COMMITTED, executorType = ExecutorType.SIMPLE)
	public void dotest() {
		TestMapper test = this.test;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("name", "test");
		m.put("author", "ewanzhao");
		test.insert(m);
		System.out.println("sdfsdsddd:" + m.get("id"));

	}
}