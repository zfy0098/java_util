package com.rom.util.htmlutil;

import java.io.IOException; 

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupUtil {

	public Elements getHtmlReourceByUrl(String url , String tagName){
		try {
			Document document = Jsoup.connect(url).get();
			return document.getElementsByTag(tagName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
