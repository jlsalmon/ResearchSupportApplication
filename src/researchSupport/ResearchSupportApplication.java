package researchSupport;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 
 * @author jussy
 * 
 */
public class ResearchSupportApplication {

	PaperManager pm = new PaperManager();
	ResearchSupportTextInterface rsti = new ResearchSupportTextInterface();

	public ResearchSupportApplication() throws IOException {

		int action = -1;
		TestData td = new TestData();

		while (action != 0) {
			rsti.start();
			action = rsti.getAction();
			Paper thisPaper = rsti.getPaper();

			switch (action) {
			case 10: {
				td.loadData(this);
				break;
			}
			case 1: {
				addPaper(thisPaper);
				break;
			}
			case 2: {
				makeLinkToReference(rsti.getRefTitle(), rsti.getPaperTitle());
				break;
			}
			case 3: {
				listPaperDetails(rsti.getPaperTitle());
				break;
			}
			case 4: {
				listDirectCitations(rsti.getPaperTitle());
				break;
			}
			case 5: {
				listDirectReferences(rsti.getPaperTitle());
				break;
			}
			case 6: {
				listAllCitationChains(rsti.getPaperTitle());
				break;
			}
			case 7: {
				listAllReferenceChains(rsti.getPaperTitle());
				break;
			}
			case 8: {
				listNCitations(rsti.getPaperTitle(), rsti.getLevels());
				break;
			}
			case 9: {
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
			rsti.print(paper.getTitle() + " added successfully.");
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
			rsti.print(pm.getPaper(referrer).getTitle() + " -> "
					+ pm.getPaper(referee).getTitle() + " reference added");
		} else {
			rsti.print("Error: reference not added.");
		}
	}

	/**
	 * 
	 * @param title
	 */
	public void listPaperDetails(String title) {
		Paper p = pm.getPaper(title);
		if (p == null) {
			rsti.print("No papers with title " + title + " found.");
		} else {
			rsti.print(p.toString());
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
			rsti.print(references);
		}
	}

	public void listAllCitationChains(String title) {
		HashSet<Stack<Paper>> chains = pm.getAllCitationChains(title);
		if (pm.getDirectCitations(title).isEmpty() || chains.isEmpty()) {
			rsti.print("No citations found for " + title);
		} else {
			rsti.print(chains);
		}
	}

	public void listAllReferenceChains(String title) {
		HashSet<Stack<Paper>> chains = pm.getAllReferenceChains(title);
		if (pm.getDirectReferences(title).isEmpty() || chains.isEmpty()) {
			rsti.print("No references found for " + title);
		} else {
			rsti.print(chains);
		}
	}

	public void listNCitations(String title, int n) {
		HashSet<Stack<Paper>> chains = pm.getNCitations(title, n);
		if (pm.getDirectCitations(title).isEmpty() || chains.isEmpty()) {
			rsti.print("No citations found for " + title);
		} else {
			rsti.print(chains);
		}
	}

	public void listNReferences(String title, int n) {
		HashSet<Stack<Paper>> chains = pm.getNReferences(title, n);
		if (pm.getDirectReferences(title).isEmpty() || chains.isEmpty()) {
			rsti.print("No references found for " + title);
		} else {
			rsti.print(chains);
		}
	}
}
