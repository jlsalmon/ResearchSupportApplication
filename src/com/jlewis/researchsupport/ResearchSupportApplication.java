package com.jlewis.researchsupport;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

/**
 * ResearchSupportApplication.java
 * 
 * @author Justin Lewis Salmon
 * @student_id 10000937
 * 
 *             Main controlling class for this application. Initiates all
 *             behaviours and operations, including instantiating test data.
 *             Communicates with the underlying data structure through the
 *             PaperManager class, and with the user through the
 *             ResearchSupportTextInterface class.
 */
public class ResearchSupportApplication {

	private static final int ADD_PAPER = 1, MAKE_REFERENCE = 2,
			DISPLAY_PAPER = 3, LIST_DIRECT_CITATIONS = 4,
			LIST_DIRECT_REFERENCES = 5, LIST_ALL_CITATIONS = 6,
			LIST_ALL_REFERENCES = 7, LIST_N_CITATIONS = 8,
			LIST_N_REFERENCES = 9, LOAD_DATA = 10, EXIT = 0;

	private PaperManager pm = new PaperManager();
	private ResearchSupportTextInterface rsti = new ResearchSupportTextInterface();

	/**
	 * Launches the user interface and begins the user input loop.
	 * 
	 * @throws IOException
	 */
	public void run() throws IOException {
		int action = -1;
		TestData td = new TestData();

		while (action != EXIT) {
			rsti.start();
			action = rsti.getAction();
			Paper thisPaper = rsti.getPaper();

			switch (action) {
			case LOAD_DATA:
				td.loadData(this);
				break;
			case ADD_PAPER:
				addPaper(thisPaper);
				break;
			case MAKE_REFERENCE:
				makeLinkToReference(rsti.getRefTitle(), rsti.getPaperTitle());
				break;
			case DISPLAY_PAPER:
				listPaperDetails(rsti.getPaperTitle());
				break;
			case LIST_DIRECT_CITATIONS:
				listDirectCitations(rsti.getPaperTitle());
				break;
			case LIST_DIRECT_REFERENCES:
				listDirectReferences(rsti.getPaperTitle());
				break;
			case LIST_ALL_CITATIONS:
				listAllCitationChains(rsti.getPaperTitle());
				break;
			case LIST_ALL_REFERENCES:
				listAllReferenceChains(rsti.getPaperTitle());
				break;
			case LIST_N_CITATIONS:
				listNCitations(rsti.getPaperTitle(), rsti.getLevels());
				break;
			case LIST_N_REFERENCES:
				listNReferences(rsti.getPaperTitle(), rsti.getLevels());
				break;
			}
		}
	}

	/**
	 * Attempts to add a paper to the application.
	 * 
	 * @param paper
	 *            the paper to be added.
	 */
	public void addPaper(Paper paper) {
		if (pm.addPaper(paper)) {
			rsti.print("Paper " + paper.getTitle() + " added successfully.");
		} else {
			rsti.print("Error: " + paper.getTitle() + " not added.");
		}
	}

	/**
	 * Attempts to create a reference link between two papers.
	 * 
	 * !!IMPORTANT. Incorrect case for paper names is allowed here due to
	 * laziness. In practice this is VERY inefficient, as the entire set of
	 * papers is searched twice more than it needs to be.
	 * 
	 * @param referrer
	 *            the paper making the reference.
	 * @param referee
	 *            the paper being referenced.
	 */
	public void makeLinkToReference(String referrer, String referee) {
		if (pm.makeReference(referrer, referee)) {
			rsti.print("Reference " + pm.getPaper(referrer).getTitle() + " -> "
					+ pm.getPaper(referee).getTitle() + " added successfully.");
		} else {
			rsti.print("Error: reference not added.");
		}
	}

	/**
	 * Attempts to list information for a particular paper.
	 * 
	 * @param title
	 *            the title of the paper to display.
	 */
	public void listPaperDetails(String title) {
		if (!pm.isExistingPaper(title)) {
			rsti.print("No papers with title " + title + " found.");
		} else {
			rsti.print(pm.getPaper(title).toString());
		}
	}

	/**
	 * Attempts to list all direct citations for a paper.
	 * 
	 * @param title
	 *            the title of the paper to get citations for.
	 */
	public void listDirectCitations(String title) {
		Set<Paper> citations = pm.getDirectCitations(title);
		if (citations.isEmpty()) {
			rsti.print("No citations found for " + title + ".");
		} else {
			rsti.print(citations.size() + " citation(s) found for "
					+ pm.getPaper(title).getTitle() + ":");
			rsti.print(citations);
		}
	}

	/**
	 * Attempts to list all the direct references of a paper.
	 * 
	 * @param title
	 *            the title of the paper to get references of.
	 */
	public void listDirectReferences(String title) {
		Set<Paper> references = pm.getDirectReferences(title);
		if (references.isEmpty()) {
			rsti.print("No references found for " + title + ".");
		} else {
			rsti.print(references.size() + " reference(s) found for "
					+ pm.getPaper(title).getTitle() + ":");
			rsti.print(references);
		}
	}

	/**
	 * Attempts to list all citation chains down to a leaf node for a given
	 * paper.
	 * 
	 * @param title
	 *            the title of the paper to get citation chains for.
	 */
	public void listAllCitationChains(String title) {
		LinkedHashSet<Stack<Paper>> chains = pm.getAllCitationChains(title);
		if (chains == null || chains.isEmpty()) {
			rsti.print("No citations found for " + title + ".");
		} else {
			rsti.print(chains.size() + " citation chain(s) found for "
					+ pm.getPaper(title).getTitle() + ":");
			rsti.print(chains);
		}
	}

	/**
	 * Attempts to list all reference chains down to a leaf node for a given
	 * paper.
	 * 
	 * @param title
	 *            the title of the paper to get reference chains for.
	 */
	public void listAllReferenceChains(String title) {
		LinkedHashSet<Stack<Paper>> chains = pm.getAllReferenceChains(title);
		if (chains == null || chains.isEmpty()) {
			rsti.print("No references found for " + title + ".");
		} else {
			rsti.print(chains.size() + " reference chain(s) found for "
					+ pm.getPaper(title).getTitle() + ":");
			rsti.print(chains);
		}
	}

	/**
	 * Attempts to list all citation chains down to a specified depth limit for
	 * a given paper.
	 * 
	 * @param title
	 *            the title of the paper to get citation chains for.
	 */
	public void listNCitations(String title, int n) {
		LinkedHashSet<Stack<Paper>> chains = pm.getNCitations(title, n);
		if (chains == null || chains.isEmpty()) {
			rsti.print("No citations found for " + title + ".");
		} else {
			rsti.print(chains.size() + " citation chain(s) to " + n
					+ " levels found for " + pm.getPaper(title).getTitle()
					+ ":");
			rsti.print(chains);
		}
	}

	/**
	 * Attempts to list all reference chains down to a specified depth limit for
	 * a given paper.
	 * 
	 * @param title
	 *            the title of the paper to get reference chains for.
	 */
	public void listNReferences(String title, int n) {
		LinkedHashSet<Stack<Paper>> chains = pm.getNReferences(title, n);
		if (chains == null || chains.isEmpty()) {
			rsti.print("No references found for " + title + ".");
		} else {
			rsti.print(chains.size() + " reference chain(s) to " + n
					+ " levels found for " + pm.getPaper(title).getTitle()
					+ ":");
			rsti.print(chains);
		}
	}
}
