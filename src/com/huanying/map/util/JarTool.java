package com.huanying.map.util;

import java.io.File;

public class JarTool {

	// ��ȡjar����·��
	public static String getJarPath() {
		File file = getFile();
		String jarPath = null;
		if (file != null) {
			// ��·���еķ�б���滻�ɷ�б��
			jarPath = file.getAbsolutePath().replaceAll("\\\\", "/");
			// System.out.println("Path: " + jarPath);
			int end = jarPath.indexOf("file:/");
			if (end > 1) {
				String[] filePath = jarPath.split("file:/");
				jarPath = filePath[1].split("!/main/main.jar")[0];
				jarPath = jarPath.substring(0, jarPath.lastIndexOf("/"));
				if (filePath[0].startsWith("/")) {
					jarPath = "/" + jarPath;
				}
				jarPath += "/";
			} else if ((end = jarPath.indexOf("WEB-INF/classes")) > 1) {
				end += 15;
				jarPath = jarPath.substring(0, end);
			} else {
				jarPath = jarPath.substring(0, jarPath.indexOf("bin"));
			}
		}
		return jarPath;
	}

	private static File getFile() {
		String path = JarTool.class.getProtectionDomain().getCodeSource()
				.getLocation().getFile();
		try {
			path = java.net.URLDecoder.decode(path, "UTF-8"); // ת���������ļ��ո�
		} catch (java.io.UnsupportedEncodingException e) {
			return null;
		}
		return new File(path);
	}

	public static void main(String[] args) {
		System.out.println(JarTool.getJarPath());
	}
}