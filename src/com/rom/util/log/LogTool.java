package com.rom.util.log;

import java.text.SimpleDateFormat; 
import java.util.Date;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日志工具包
 * 
 * @author wangyue
 * 
 */
public class LogTool {

	String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

	String randStr = "";

	public LogTool() {
		randStr = GetCode();
	}

	// 获取随机数
	private String GetCode() {
		Random random = new Random();
		StringBuffer randBuffer = new StringBuffer();
		char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'a', 'b', 'c', 'd', 'e', 'f' };

		for (int i = 0; i < 6; i++) {
			randBuffer.append(String.valueOf(codeSequence[random.nextInt(16)]));
		}
		return randBuffer.toString();

	}

	/**
	 * 打印日志 trade
	 * 
	 * @param title
	 * @param name
	 * @param value
	 * @return
	 */
	public int WriteTrade(String title, String name, String value) {
		Log log = LogFactory.getLog("Trade");
		String str = String.format("%s####%s,[%s]=[%s]    ", randStr, title, name, value);
		log.fatal(str);
		return 0;
	}

	/**
	 * 打印日志trade
	 * 
	 * @param value
	 * @return
	 */
	public int WriteTrade(String value) {
		Log log = LogFactory.getLog("Trade");
		String str = String.format("%s#%s####%s", time, randStr, value);
		log.fatal(str + "\n");
		return 0;
	}

	/**
	 * 打印日志trade
	 * 
	 * @param value
	 * @return
	 */
	public int WriteTermMsg(String value) {
		Log log = LogFactory.getLog("TermMsg");
		String str = String.format("%s", value);
		log.fatal(str + "\n");
		return 0;
	}

	/**
	 * 打印日志trade
	 * 
	 * @param title
	 * @param name
	 * @param value
	 * @return
	 */
	public int WriteTrade(String title, String name, int value) {
		Log log = LogFactory.getLog("Trade");
		String str = String.format("%s###%s,[%s]=[%s]    ", randStr, title,
				name, value);
		log.fatal(str + "\n");
		return 0;
	}

	/**
	 * UnionPayNotify 打印日志trade
	 * 
	 * @param title
	 * @param name
	 * @param value
	 * @return
	 */
	public int WriteTermNotify(String title, String body) {
		Log log = LogFactory.getLog("TermNotify");
		String str = String.format("%s###%s[%s]=[%s]", time, randStr, title,
				body);
		log.fatal(str + "\n");
		return 0;
	}

	/**
	 * UnionPayNotify 打印日志trade
	 * 
	 * @param title
	 * @param name
	 * @param value
	 * @return
	 */
	public int WriteUnionPayNotify(String body) {
		Log log = LogFactory.getLog("UnionPayNotify");
		String str = String.format("%s###%s[通知]=[%s]", time, randStr, body);
		log.fatal(str + "\n");
		return 0;
	}

	/**
	 * 打印日志error
	 * 
	 * @param value
	 * @return
	 */
	public int error(String value) {
		Log log = LogFactory.getLog("error");
		String str = String.format("%s%s", randStr, value);
		log.fatal(str + "\n");
		return 0;
	}

	// 是否打印
	public static boolean isPrint(byte a) {

		int chr = a;
		if (chr < 32 || chr > 126)
			return true;
		else
			return false;
	}

	public int errorDB(String value) {
		Log log = LogFactory.getLog("DB");
		String str = String.format("%s%s", randStr, value);
		log.fatal(str);
		return 0;
	}
}
