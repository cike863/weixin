package cike.util;

import cike.po.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_LOCATION="location";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="CLICK";
	public static final String MESSAGE_VIEW="VIEW";
	
	/**
	 * xml转换map方法
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> xmlToMessage(HttpServletRequest request) throws IOException, DocumentException {
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		InputStream ins =request.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element e : list){
			map.put(e.getName(), e.getText());
		}
		return map;
		}
	/**
	 * 将文本消息对象转换为xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageXml(TextMessage textMessage){
		XStream xStream = new XStream();
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}
	/**
	 * 回复的信息初始化
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	public static String initText(String fromUserName,String toUserName,String content){
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setMsgType(MESSAGE_TEXT);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setContent(content);
		return MessageUtil.textMessageXml(textMessage);
	}
	/**
	 * 主菜单
	 * @return
	 */
	public static String menuTexe(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎你的关注，请按照菜单提示进行操作：\n\n");
		sb.append("1：关于我\n");
		sb.append("回复？调出此菜单。");
		return sb.toString();
	}
	public static String fristMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("已转行IT的机械狗，正在向逗比、段子手的路上前行，奉行的原则是：死磕自己，娱乐大家");
		return sb.toString();
	}
}
