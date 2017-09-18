package com.util;

@SuppressWarnings("all")
public class PageUtil {

	public static String rootPageTion(String targetUrl, int totalNumber, int currentPage, int pageSize, String id,
			String className) {
		int totalPage;
		if (totalNumber <= pageSize) {
			totalPage = 1;
		} else {
			totalPage = totalNumber % pageSize == 0 ? totalNumber / pageSize : totalNumber / pageSize + 1;
		}
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='" + targetUrl + "?page=1" + "'>首页</a><li>");
		if (currentPage == 1) {
			pageCode.append(
					"<li class='disabled'><a aria-label='Next'><span aria-hidden='true'>&laquo;</span></a></li>");
		} else {
			pageCode.append("<li><a aria-label='Next' href='" + targetUrl + "?page=" + (currentPage - 1)
					+ "'><span aria-hidden='true'>&laquo;</span></a></li>");
		}
		for (int i = currentPage - 2; i <= currentPage + 2; i++) {
			if (i < 1 || i > totalPage) {
				continue;
			}
			if (i == currentPage) {
				pageCode.append("<li class='active'><a>" + i + "&nbsp;</a></li>");
			} else {
				pageCode.append("<li><a href='" + targetUrl + "?page=" + i + "'>" + i + "</a></li>");
			}
		}
		if (currentPage == totalPage) {
			pageCode.append(
					"<li class='disabled'><a aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
		} else {
			pageCode.append("<li><a aria-label='Next' href='" + targetUrl + "?page=" + (currentPage + 1)
					+ "'><span aria-hidden='true'>&raquo;</span></a></li>");
		}
		pageCode.append("<li><a href='" + targetUrl + "?page=" + totalPage + "'>尾页</a></li>");
		return pageCode.toString();
	}
}
