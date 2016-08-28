package com.capgemini.servlet.consumer;

import java.io.IOException;

import javax.jms.JMSException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.capgemini.mq.JMSMQReceiver;

/**
 * Servlet implementation class MQMessageReaderServlet
 */
@WebServlet("/MQMessageReaderServlet")
public class MQMessageReaderServlet extends HttpServlet {
	
	
	private static final Logger logger = LoggerFactory.getLogger(MQMessageReaderServlet.class);
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
    public MQMessageReaderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("MQMessageReaderServlet :: Get::START");
		 String message=null;
		 try {
			message=getMessageFromMQ();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message="Please check the logs for error";
		}
		if(message==null || message.isEmpty()){
			message=" There are no Messages to Display";
		}else{
			message="<br><br> Received Messages are : <br><br>"+message;
		}
		logger.debug("MQMessageReaderServlet :: Available Messages"+message);
		response.getWriter().append(message);
		logger.debug("MQMessageReaderServlet ::GET END ");
	}

	/**
	 * @throws JMSException 
	 * 
	 */
	private String getMessageFromMQ() throws JMSException {
		
		JMSMQReceiver jmsMQReceiver=(JMSMQReceiver)springApplicationContext.getBean("jmsReceiver");
		return jmsMQReceiver.getJMSMQMessage();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	

}
