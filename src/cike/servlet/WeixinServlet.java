package cike.servlet;

import cike.util.CheckUtil;
import cike.util.MessageUtil;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class WeixinServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Logger log = Logger.getLogger(WeixinServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doGet开始");
		String signature=req.getParameter("signature");
		String timestamp=req.getParameter("timestamp");
		String nonce=req.getParameter("nonce");
		String echostr=req.getParameter("echostr");

		System.out.println("doGet开始,signature="+signature);
		System.out.println("doGet开始,timestamp="+timestamp);
		System.out.println("doGet开始,nonce="+nonce);
		System.out.println("doGet开始,echostr="+echostr);

		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
		out.close();
		System.out.println("doGet结束");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//log.info("doPost开始");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String,String> map =MessageUtil.xmlToMessage(req);
			System.out.println(map);
			
			String toUserName=map.get("ToUserName");
			String fromUserName=map.get("FromUserName");
			String createTime=map.get("CreateTime");
			String msgType=map.get("MsgType");
			String content=map.get("Content");
			String msgId=map.get("MsgId");
			String message ="";
			System.out.println("msgType："+msgType);
			//log.info(msgType);
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					message = MessageUtil.initText(fromUserName, toUserName, MessageUtil.fristMenu());
				}else if("？".equals(content)||("?".equals(content))){
					message = MessageUtil.initText(fromUserName, toUserName, MessageUtil.menuTexe());
				}else{
					message = MessageUtil.initText(fromUserName, toUserName, "你说的是火星文么，我看不懂啦");
				}
				System.out.println("message:"+message);
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String event = map.get("EVENT");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(event)){
					message = MessageUtil.initText(fromUserName, toUserName, MessageUtil.menuTexe());
				}
			}	
			out.print(message);
			out.close();
			//log.info("doPost结束");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
