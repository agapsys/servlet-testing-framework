/*
 * Copyright 2015 Agapsys Tecnologia Ltda-ME.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.agapsys.sevlet.test;

import com.agapsys.sevlet.test.utils.MyServlet;
import com.agapsys.sevlet.test.utils.MyFilter;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyFilterTest {
	private static final String CONTEXT = "/context";
	
	private ServletContainer sc;
	
	@Before
	public void setUp() {
		sc = new ServletContainer();
		
		ApplicationContext context = new ApplicationContext();
		context.registerServlet(MyServlet.class);
		context.registerFilter(MyFilter.class);
		
		sc.registerContext(context, CONTEXT);
		sc.startServer();
	}
	
	@After
	public void tearDown() {
		sc.stopServer();
	}

	@Test
	public void testFilterIntercept() throws IOException {
		String testUrl = CONTEXT + MyServlet.URL1;
		
		HttpResponse response = sc.doGet(new HttpGet(sc, testUrl));
		assertEquals(response.getStatusCode(), 200);

		String responseStr = response.getResponseBody();

		assertEquals(responseStr, "Filtered" + MyServlet.URL1);
	}
}
