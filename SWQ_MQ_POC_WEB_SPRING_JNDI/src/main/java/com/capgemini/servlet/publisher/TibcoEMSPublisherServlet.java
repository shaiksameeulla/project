package com.capgemini.servlet.publisher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.capgemini.tibco.JMSEMSTopicSender;

/**
 * Servlet implementation class TibcoEMSPublisherServlet
 */
@WebServlet(value="/TibcoEMSPublisherServlet",loadOnStartup=1)
public class TibcoEMSPublisherServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(TibcoEMSPublisherServlet.class);
	private static final long serialVersionUID = 1L;
	public static  WebApplicationContext springApplicationContext;
	public void init() throws ServletException {
		super.init();
		if(springApplicationContext==null){
			springApplicationContext = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext());
		}
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TibcoEMSPublisherServlet() {
        super();
        logger.debug("TibcoEMSPublisherServlet:: Constructor:");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Please Use UserInterface/JSp to publish the message");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 logger.debug("TibcoEMSPublisherServlet:: doPost:");
		 String messageInput=request.getParameter("messageInput");
		 logger.debug("TibcoEMSPublisherServlet:: doPost: Message"+messageInput);
		boolean isSuccess= publishTIBCOTopicMessage(messageInput);
		String message="";
		if(!isSuccess){
			 message=" NOT ";
		}
		 response.getWriter().append(" <html> <body> <form action='messagePublisher'>Message "+message+"Sent sucessfully <br><input type='submit' value='back'></form> </body></html>");
	}
	
	 
	private boolean publishTIBCOTopicMessage(String message) {
		
		JMSEMSTopicSender jmsEMSTopicSender=(JMSEMSTopicSender)springApplicationContext.getBean("jmsTopicSender");
		 try {
			jmsEMSTopicSender.sendMesage(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		 return true;
	}

}
