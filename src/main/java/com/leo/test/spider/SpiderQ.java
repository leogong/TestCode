package com.leo.test.spider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.NodeList;

import com.leo.test.htmlparser.EmTag;
import com.leo.test.htmlparser.HtmlUtils;

public class SpiderQ {
	public static void main(String[] args) throws Exception {
		BufferedReader bf = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream("hostAll.txt")));
		String allLine = null;
		allLine = bf.readLine();
		String[] urlList = allLine.split(" ");
		NodeFilter divFilter = new TagNameFilter("div");
		NodeFilter classFilter = new HasAttributeFilter("class", "list_box_c");
		int m = 0;
		System.out.println(urlList.length);
		for (String url : urlList) {
			System.out.println("-------------------" + url);
			m++;
			try {
				NodeList divList = HtmlUtils.getNodeListByFilter("http://" + url, new AndFilter(new NodeFilter[] { divFilter, classFilter }), null);
				for (int i = 0; i < divList.size(); i++) {
					Div div = (Div) divList.elementAt(i);
					EmTag em = (EmTag) HtmlUtils.getNodeListByFilter(new TagNameFilter("em"), div.toHtml()).elementAt(0);
					if (em == null || "".equals(em.getStringText()))
						throw new RuntimeException("名字为空，当前域名为：" + url);
					NodeList pList = HtmlUtils.getNodeListByFilter(new TagNameFilter("p"), div.toHtml());
					ParagraphTag ptag = (ParagraphTag) pList.elementAt(0);
					if ("城市：".equals(ptag.getStringText())) {
						throw new RuntimeException("城市为空，当前域名为：" + url);
					}
					String cityNname = ptag.getStringText();
					ptag = (ParagraphTag) pList.elementAt(1);
					if ("地址：".equals(ptag.getStringText())) {
						throw new RuntimeException("地址为空，当前域名为：" + url);
					}
					System.out.println(" " + m + "  酒店名称：" + em.getStringText() + ", " + cityNname + ", " + ptag.getStringText() + " 大小 :" + divList.size());
				}
			} catch (RuntimeException e) {
				System.out.println("++++++++++++++++++出错啦！！！！！！！" + url);
			} catch (UnknownHostException e) {
				System.out.println("++++++++++++++++++出错啦！！！！！！！,无法解析网址" + url);
			}
		}
		bf.close();
	}
}
