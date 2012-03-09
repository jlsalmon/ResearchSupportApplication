package researchSupport;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * ResearchSupportApplication.java
 * 
 * @author Justin Lewis Salmon
 * @student_id 10000937
 * 
 *             Description..
 */
public class ResearchSupportApplication {

	private static final int ADD_PAPER = 1, MAKE_REFERENCE = 2,
			DISPLAY_PAPER = 3, LIST_DIRECT_CITATIONS = 4,
			LIST_DIRECT_REFERENCES = 5, LIST_ALL_CITATIONS = 6,
			LIST_ALL_REFERENCES = 7, LIST_N_CITATIONS = 8,
			LIST_N_REFERENCES = 9, LOAD_DATA = 10, EXIT = 0;

	PaperManager pm = new PaperManager();
	ResearchSupportTextInterface rsti = new ResearchSupportTextInterface();

	/**
	 * 
	 * @throws IOException
	 */
	public ResearchSupportApplication() throws IOException {

		int action = -1;
		TestData td = new TestData();

		while (action != EXIT) {
			rsti.start();
			action = rsti.getAction();
			Paper thisPaper = rsti.getPaper();

			switch (action) {
			case LOAD_DATA: {
				td.loadData(this);
				break;
			}
			case ADD_PAPER: {
				addPaper(thisPaper);
				break;
			}
			case MAKE_REFERENCE: {
				makeLinkToReference(rsti.getRefTitle(), rsti.getPaperTitle());
				break;
			}
			case DISPLAY_PAPER: {
				listPaperDetails(rsti.getPaperTitle());
				break;
			}
			case LIST_DIRECT_CITATIONS: {
				listDirectCitations(rsti.getPaperTitle());
				break;
			}
			case LIST_DIRECT_REFERENCES: {
				listDirectReferences(rsti.getPaperTitle());
				break;
			}
			case LIST_ALL_CITATIONS: {
				listAllCitationChains(rsti.getPaperTitle());
				break;
			}
			case LIST_ALL_REFERENCES: {
				listAllReferenceChains(rsti.getPaperTitle());
				break;
			}
			case LIST_N_CITATIONS: {
				listNCitations(rsti.getPaperTitle(), rsti.getLevels());
				break;
			}
			case LIST_N_REFERENCES: {
				listNReferences(rsti.getPaperTitle(), rsti.getLevels());
				break;

			}
			}
		}
	}

	/**
	 * 
	 * @param paper
	 */
	public void addPaper(Paper paper) {
		if (pm.addPaper(paper)) {
			rsti.print("Paper " + paper.getTitle() + " added successfully.");
		} else {
			rsti.print("Error: " + paper.getTitle() + " not added.");
		}
	}

	/**
	 * !!IMPORTANT. Incorrect case for paper names is allowed here due to
	 * laziness. In practice this is VERY inefficient, as the entire set of
	 * papers is searched twice more than it needs to be.
	 * 
	 * @param referrer
	 * @param referee
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
	 * 
	 * @param title
	 */
	public void listPaperDetails(String title) {
		if (!pm.isExistingPaper(title)) {
			rsti.print("No papers with title " + title + " found.");
		} else {
			rsti.print(pm.getPaper(title).toString());
		}
	}

	/**
	 * 
	 * @param title
	 */
	public void listDirectCitations(String title) {
		Set<Paper> citations = pm.getDirectCitations(title);
		if (citations.isEmpty()) {
			rsti.print("No citations found for " + title);
		} else {
			rsti.print("\nDirect citations for "
					+ pm.getPaper(title).getTitle() + ":");
			rsti.print(citations);
		}
	}

	/**
	 * 
	 * @param title
	 */
	public void listDirectReferences(String title) {
		Set<Paper> references = pm.getDirectReferences(title);
		if (references.isEmpty()) {
			rsti.print("No references found for " + title);
		} else {
			rsti.print("\nDirect references for "
					+ pm.getPaper(title).getTitle() + ":");
			rsti.print(references);
		}
	}

	/**
	 * 
	 * @param title
	 */
	public void listAllCitationChains(String title) {
		HashSet<Stack<Paper>> chains = pm.getAllCitationChains(title);
		if (chains == null || chains.isEmpty()) {
			rsti.print("No citations found for " + title);
		} else {
			rsti.print("\nAll citation chains for "
					+ pm.getPaper(title).getTitle() + ":");
			rsti.print(chains);
		}
	}

	/**
	 * 
	 * @param title
	 */
	public void listAllReferenceChains(String title) {
		HashSet<Stack<Paper>> chains = pm.getAllReferenceChains(title);
		if (chains == null || chains.isEmpty()) {
			rsti.print("No references found for " + title);
		} else {
			rsti.print("\nAll reference chains for "
					+ pm.getPaper(title).getTitle() + ":");
			rsti.print(chains);
		}
	}

	/**
	 * 
	 * @param title
	 * @param n
	 */
	public void listNCitations(String title, int n) {
		HashSet<Stack<Paper>> chains = pm.getNCitations(title, n);
		if (chains == null || chains.isEmpty()) {
			rsti.print("No citations found for " + title);
		} else {
			rsti.print("\nCitation chains to " + n + " levels for "
					+ pm.getPaper(title).getTitle() + ":");
			rsti.print(chains);
		}
	}

	/**
	 * 
	 * @param title
	 * @param n
	 */
	public void listNReferences(String title, int n) {
		HashSet<Stack<Paper>> chains = pm.getNReferences(title, n);
		if (chains == null || chains.isEmpty()) {
			rsti.print("No references found for " + title);
		} else {
			rsti.print("\nReference chains to " + n + " levels for "
					+ pm.getPaper(title).getTitle() + ":");
			rsti.print(chains);
		}
	}
}
