package com.ff.web.inbound.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cg.lbs.bcun.constant.BcunConstant;

/**
 * @author mohammes
 * 
 */
public class InboundDownloadServiceAction extends DownloadAction {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(InboundDownloadServiceAction.class);

	@Override
    protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            {
		String url = request.getSession(false)!=null? (String)request.getSession(false).getAttribute(BcunConstant.DUMP_URL_SESSION):null;
        // You need to provide the content type of file.
        String contentType=null;
		File file=null;
		try {
			contentType = "application/zip";
			file = new File(url);
			LOGGER.info("InboundDownloadServiceAction :: getStreamInfo :: file Name "+url);
			response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
		} catch (Exception e) {
			LOGGER.error("InboundDownloadServiceAction :: getStreamInfo :: ERROR :",e);
		}
        return new FileStreamInfo(contentType, file);
    }
}
