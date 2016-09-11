package com.lib.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.xutils.x;

public class IOUtils {
	public static String getStringFromFile(String fileName) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(x.app().getAssets().open(fileName)));
			String line;
			while ((line = bf.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
}
