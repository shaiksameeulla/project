package com.capgemini.lbs.framework.utils;

import org.springframework.web.context.WebApplicationContext;

/**
 * The Class SpringContext.
 * @author narmdr
 */
public class SpringContext {

	public static WebApplicationContext springApplicationContext = null;

	/**
	 * @return the springApplicationContext
	 */
	public static WebApplicationContext getSpringApplicationContext() {
		return springApplicationContext;
	}

	/**
	 * @param springApplicationContext the springApplicationContext to set
	 */
	public static void setSpringApplicationContext(
			WebApplicationContext springApplicationContext) {
		SpringContext.springApplicationContext = springApplicationContext;
	}
	
	public static Object getBean(String beanName) {
		if (beanName == null) {
			return null;
		} else if (springApplicationContext != null) {
			return springApplicationContext.getBean(beanName);
		} else {
			return null;
		}
	}

}
