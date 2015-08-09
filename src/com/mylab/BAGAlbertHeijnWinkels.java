package com.mylab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BAGAlbertHeijnWinkels {

	static List<String> winkels = new ArrayList<String>();

	String[] ahwinkels = new String[4];

	public List<String> process_winkels() {
		BufferedReader br = null;
		try {
			String line;
			br = new BufferedReader(new FileReader(
					"/home/jan/Downloads/AlbertHeijn"));
			while ((line = br.readLine()) != null) {
				ahwinkels = line.split(",");
				if (ahwinkels.length == 4) {
					if ((ahwinkels[2] != null) && (ahwinkels[2].length() > 0)
							&& (ahwinkels[3] != null)
							&& (ahwinkels[3].length() > 0)) {
						winkels.add(line);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(winkels.size());
		return winkels;
	}
}
