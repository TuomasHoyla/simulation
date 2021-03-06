package resources;
import main.*;
import java.io.*;


public class Monitor {
	private static int[] CumResearchersByLevels= new int[8];
	private static int[] CumPapersByLevels= new int[8];
	private static int[] CumCurrentPapers = new int[8];
	private static int[] CumCurrentCitations = new int[8];	
	private static int[] CumCitationsByLevels= new int[8];
	private static double[] CumSkillByLevels = new double[8];
	private static double[] CumFrustrationByLevels = new double[8];
	private static int[] CumResignByLevels = new int[8];
	private int cumResign;
	private static int[] CumPromoteByLevels = new int[8];
	private static int[] CumRetirementAge = new int[8];
	private static int[] CumPromotionAge = new int[8];
	private int cumCitationsFromRemoved;
	private int cumPapersFromRemoved;
	private int TotalCurrentCitations;
	private int TotalCurrentPapers;
	private double TotalSkill;
	private double TotalFrustration;
	public double res;
	public double cumRes;


public void resetCounters()
{
	for (int i=0; i<simulation.M.levelCount;i++)
	{
		CumResearchersByLevels[i]=0;
		CumCitationsByLevels[i]=0;
		CumPapersByLevels[i]=0;
		CumCurrentPapers[i]=0;
		CumCurrentCitations[i]=0;
		CumSkillByLevels[i]=0;
		CumResignByLevels[i]=0;
		CumPromoteByLevels[i]=0;
		CumFrustrationByLevels[i]=0;
		CumRetirementAge[i]=0;
		CumPromotionAge[i]=0;
	}
	cumCitationsFromRemoved=0;
	cumPapersFromRemoved=0;
	cumResign=0;
	cumRes=0;

}
public void updateCounters()
{
	for (int i=0;i<simulation.M.levelCount; i++)
	{
		CumResearchersByLevels[i]+=simulation.O.ResearchersByLevels[i];
		CumPapersByLevels[i]+=simulation.O.PapersByLevels[i];
		CumCitationsByLevels[i]+=simulation.O.CitationsByLevels[i];
		CumSkillByLevels[i]+=simulation.O.SkillByLevels[i];
		CumCurrentCitations[i]+=simulation.O.CurrentCitations[i];
		CumCurrentPapers[i]+=simulation.O.CurrentPapers[i];
		CumResignByLevels[i]+=simulation.O.ResignByLevels[i];
		CumPromoteByLevels[i]+=simulation.O.PromoteByLevels[i];
		CumFrustrationByLevels[i]+=simulation.O.FrustrationByLevels[i];
		CumRetirementAge[i]+=simulation.O.RetirementAge[i];
		CumPromotionAge[i]+=simulation.O.PromotionAge[i];
	}
	cumCitationsFromRemoved+=simulation.O.citationsFromRemovedResearchers;
	cumPapersFromRemoved+=simulation.O.papersFromRemovedResearchers;
	cumRes+=res;
}
public void reportHeadings() {
	System.out.println(simulation.M.narrative);
}
public void setHeadings() {
	try {
		FileRead.writeLines3(simulation.M.caseheader+"; Ladder; HeadCount; Skill; YearlyPapers; YearlyCitations; Resignations; Promotions; Frustration; RetirementAge; PromotionAge",simulation.M.datafile);		
	} catch (FileNotFoundException | UnsupportedEncodingException e) {
		e.printStackTrace();
	}	
	try {
		FileRead.writeLines3(simulation.M.caseheader+"; Skill; Frustration; YearlyPapers; YearlyCitations; OldCitations; OldPapers; Recruitments",simulation.M.totalfile);		
	} catch (FileNotFoundException | UnsupportedEncodingException e) {
		e.printStackTrace();
	}	
	
}
public void logNarrative() {
	try {
		FileRead.writeLines3(simulation.M.narrative,simulation.M.logfile);
	} catch (FileNotFoundException | UnsupportedEncodingException e) {
		e.printStackTrace();
	}	
}
public void report(int y){
	double yd = y;
	for (int i=0;i<simulation.M.levelCount;i++) {
		System.out.print(simulation.M.instanssi +"; "+(i+1)+"; "+CumResearchersByLevels[i]/yd+"; "+CumSkillByLevels[i]/CumResearchersByLevels[i]);
		System.out.print("; "+CumCurrentPapers[i]/CumResearchersByLevels[i]+"; "+CumCurrentCitations[i]/CumResearchersByLevels[i]);
		System.out.println("; "+CumResignByLevels[i]/CumResearchersByLevels[i]+"; "+CumPromoteByLevels[i]/CumResearchersByLevels[i]+"; "+CumFrustrationByLevels[i]/CumResearchersByLevels[i] +"; "+CumRetirementAge[i]/CumResignByLevels[i]+"; "+CumPromotionAge[i]/(CumPromoteByLevels[i]+0.0001));		
	}
}

public void logReport(int y){
	double yd = y;
	String line=" ";
	for (int i=0;i<simulation.M.levelCount;i++) {
		line=simulation.M.instanssi +"; "+(i+1)+"; "+CumResearchersByLevels[i]/yd+"; "+CumSkillByLevels[i]/CumResearchersByLevels[i];
		line+="; "+(CumCurrentPapers[i]+0.00001)/CumResearchersByLevels[i]+"; "+(CumCurrentCitations[i]+.00001)/CumResearchersByLevels[i];
		line+="; "+(CumResignByLevels[i]+.000001)/CumResearchersByLevels[i]+"; "+(CumPromoteByLevels[i]+.00001)/CumResearchersByLevels[i]+"; "+CumFrustrationByLevels[i]/CumResearchersByLevels[i] +"; "+(CumRetirementAge[i]+.00001)/CumResignByLevels[i]+"; "+CumPromotionAge[i]/(CumPromoteByLevels[i]+0.00001);
		try {
			FileRead.writeLines3(line,simulation.M.datafile);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	countTotals();
	line= simulation.M.instanssi +"; "+TotalSkill/yd+"; "+TotalFrustration/yd+"; "+TotalCurrentPapers/yd+"; "+TotalCurrentCitations/yd+"; "+cumCitationsFromRemoved/yd+"; "+cumPapersFromRemoved/yd+"; "+cumResign/yd;
		try {
			FileRead.writeLines3(line,simulation.M.totalfile);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	

	

}

public void logReport2(int y, String instance){
	double yd = y;
	String line=" ";
	for (int i=0;i<simulation.M.levelCount;i++) {
		line=instance +"; "+(i+1)+"; "+CumResearchersByLevels[i]/yd+"; "+CumSkillByLevels[i]/CumResearchersByLevels[i];
		line+="; "+(CumCurrentPapers[i]+0.00001)/CumResearchersByLevels[i]+"; "+(CumCurrentCitations[i]+.00001)/CumResearchersByLevels[i];
		line+="; "+(CumResignByLevels[i]+.000001)/CumResearchersByLevels[i]+"; "+(CumPromoteByLevels[i]+.00001)/CumResearchersByLevels[i]+"; "+CumFrustrationByLevels[i]/CumResearchersByLevels[i] +"; "+(CumRetirementAge[i]+.00001)/CumResignByLevels[i]+"; "+CumPromotionAge[i]/(CumPromoteByLevels[i]+0.00001);
		try {
			FileRead.writeLines3(line,simulation.M.datafile);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	countTotals();
	line= instance +"; "+TotalSkill/yd+"; "+TotalFrustration/yd+"; "+TotalCurrentPapers/yd+"; "+TotalCurrentCitations/yd+"; "+cumCitationsFromRemoved/yd+"; "+cumPapersFromRemoved/yd+"; "+cumResign/yd;
		try {
			FileRead.writeLines3(line,simulation.M.totalfile);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
}

public void countTotals(){
	TotalCurrentPapers=0;
	TotalCurrentCitations=0;
	TotalSkill=0;
	TotalFrustration=0;
	cumResign=0;
	
	for (int i=0;i<simulation.M.levelCount; i++) {
		TotalCurrentPapers+=CumCurrentPapers[i];
		TotalCurrentCitations+=CumCurrentCitations[i];
		TotalSkill+=CumSkillByLevels[i];
		TotalFrustration+=CumFrustrationByLevels[i];
		cumResign+=CumResignByLevels[i];
	}
}
}
