/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package researchSupport;

import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author Jane
 */
public class TestData {

	final String RESOURCE_LOCATION = "/researchSupport/resources/";

	public void loadData(ResearchSupportApplication rsa) throws IOException {

		Scanner pFile, cFile, lineScan;
		String entry, paperRating = null, paperName = null;
		String refName = null;
		int intPaperRating = 0;

		// read file Papers.txt and create new instance of Paper for each one
		InputStream instream = getClass().getResourceAsStream(
				RESOURCE_LOCATION + "Papers.txt");
		pFile = new Scanner(instream);
		while (pFile.hasNext()) {
			entry = pFile.nextLine();
			lineScan = new Scanner(entry);
			while (lineScan.hasNext()) {
				paperName = lineScan.next();
				paperRating = lineScan.next();
				intPaperRating = Integer.parseInt(paperRating);
			}
			if ((paperName != null) && (paperRating != null)) {
				Paper aPaper = new Paper(paperName, intPaperRating);
				rsa.addPaper(aPaper);
			}
		}
		System.out.println();

		// read reference file and create the link
		instream = getClass().getResourceAsStream(
				RESOURCE_LOCATION + "References.txt");
		cFile = new Scanner(instream);
		while (cFile.hasNext()) {
			entry = cFile.nextLine();
			lineScan = new Scanner(entry);
			while (lineScan.hasNext()) {
				paperName = lineScan.next();
				refName = lineScan.next();
			}
			if ((paperName != null) && (refName != null)) {
				rsa.makeLinkToReference(paperName, refName);
			}
		}
	}
}
