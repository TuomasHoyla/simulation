package resources;

/** TODO
 * 
 * 

 * 
 */



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import main.simulation;

public class Organisation {
	
	public int PositionLevels[] = new int[8];
	public int sackingAge[] = new int[8];
	public double promotionTreshold;
	public ArrayList<Researcher> researcherArray = new ArrayList<>();
	ArrayList<Researcher> TempResearchersToBeAdded = new ArrayList<>();
	ArrayList<Researcher> TempResearchersToBeRemoved = new ArrayList<>();
//	public ArrayList<Integer> cumuPaperCounter = new ArrayList<Integer>();
//	public ArrayList<Integer> cumuCitationCounter = new ArrayList<Integer>();
	public ArrayList<Paper> oldPapers = new ArrayList<Paper>();
	public ArrayList<Paper> removedPapers = new ArrayList<Paper>();
	int PopulationSize;
	double sackingResistance;
	int addableCitations;
	int addablePapers;
	int totalCitations;
	int totalPapers;
	int year;
	String promotionModel;
	public int citationsFromRemovedResearchers;
	public int papersFromRemovedResearchers;
	public int[] ResearchersByLevels= new int[8];
	public int[] PapersByLevels= new int[8];
	public int[] CurrentPapers = new int[8];
	public int[] CurrentCitations = new int[8];	
	public int[] CitationsByLevels= new int[8];
	public int[] ResignByLevels= new int[8];
	public int[] PromoteByLevels =new int[8];
	public int[] RetirementAge = new int[8];
	public int[] PromotionAge = new int[8];
	public double[] SkillByLevels = new double[8];
	public double[] FrustrationByLevels = new double[8];
	
	Comparator<Researcher> vertaaja = new Comparator<Researcher>(){
		public int compare(Researcher p1, Researcher p2) {
			if (p1.getQualityOfApplication() < p2.getQualityOfApplication()) return -1;
			if (p1.getQualityOfApplication() > p2.getQualityOfApplication()) return 1;
			return 0;
		}
	};
	Comparator<Researcher> vertaaja2 = new Comparator<Researcher>(){
		public int compare(Researcher p1, Researcher p2) {
			if (p1.getPositionInOrganization() < p2.getPositionInOrganization()) return -1;
			if (p1.getPositionInOrganization() > p2.getPositionInOrganization()) return 1;
			if (p1.getCitations() < p2.getCitations()) return -1;
			if (p1.getCitations() > p2.getCitations()) return 1;
			return 0;
		}
	};
	
	public void initialize()
	{
	for(int i=0; i<simulation.M.levelCount;i++){
		PositionLevels[i] = simulation.M.PositionLevels[i];
		sackingAge[i] = simulation.M.sackingAge[i];
	}
	promotionTreshold= simulation.M.promotionTreshold;
	PopulationSize = simulation.M.PopulationSize;
	sackingResistance = simulation.M.sackingResistance;
	promotionModel = simulation.M.promotionModel;

	// Initialize a researcher array (with random research skills and right amount of  instances to each level
	for (int i=0; i<simulation.M.levelCount;i++){		
		for (int j=0; j<main.simulation.M.PositionLevels[i];j++){
			researcherArray.add(new Researcher(RandomGenerator.nextSessionId() , 0, main.simulation.randomGenerator.createResSkill(), main.simulation.randomGenerator.createSkill(), 0, 0, i+1));	
		}
	}
	}
	

	public void RemoveResearchers() 
	{
		for (int i=0;i<simulation.M.levelCount;i++) {ResignByLevels[i]=0;
		RetirementAge[i]=0;}
		for (Researcher doc : researcherArray) 
		{
			doc.consumeMoney();	
			doc.addYear();
			if (doc.getYearsInAcademia() >= sackingAge[simulation.M.levelCount-1] && main.simulation.randomGenerator.createRandomDouble() >= sackingResistance) 
			{
				doc.setSackingProbability(1.);
			}
			if (doc.getLeavingOrganization()) 
				{ 
				TempResearchersToBeRemoved.add(doc); 
				ResignByLevels[doc.getPositionInOrganization()-1]++;
				RetirementAge[doc.getPositionInOrganization()-1]+=doc.getYearsInAcademia();
				}		
		}
		for (Researcher doc: TempResearchersToBeRemoved)
		{
			for(Paper article: doc.papers)
			{
				oldPapers.add(article); // collect for bibliometric statistics
			}
			researcherArray.remove(doc);
		}
		TempResearchersToBeRemoved.clear();
	}
	
	public void CountByLevels()
	{
	for (int i=0;i<simulation.M.levelCount; i++)
	{
		ResearchersByLevels[i]=0;
		PapersByLevels[i]=0;
		CitationsByLevels[i]=0;
		SkillByLevels[i]=0.;
		FrustrationByLevels[i]=0.;
	}
	for (Researcher doc : researcherArray) 
	{
		ResearchersByLevels[doc.getPositionInOrganization()-1]++;	
		PapersByLevels[doc.getPositionInOrganization()-1]+=doc.getPapers();	
		CitationsByLevels[doc.getPositionInOrganization()-1]+=doc.getCitations();	
		SkillByLevels[doc.getPositionInOrganization()-1]+=doc.getResearchSkill();
		FrustrationByLevels[doc.getPositionInOrganization()-1]+=doc.getFrustration();
	}
	}

	public void AddResearchers()
	{
		CountByLevels();
		int temp= ResearchersByLevels[0];
		for (int i=1; i<simulation.M.levelCount; i++) {temp+=ResearchersByLevels[i];}
		for (int i=0;i< PopulationSize-temp;i++)
		{
			researcherArray.add(new Researcher(RandomGenerator.nextSessionId() , 0, main.simulation.randomGenerator.createResSkill(), main.simulation.randomGenerator.createSkill(), 0, 0, 1));
		}
	}
	
	public void promote() {

		double[] citationaverage = new double[8]; 
		CountByLevels();
		for (int i=0;i<simulation.M.levelCount;i++){
			citationaverage[i]= CitationsByLevels[i]/((double) ResearchersByLevels[i]);
			PromoteByLevels[i]=0;
			PromotionAge[i]=0;
		}
		switch(promotionModel) {
		case("Citation_based"): {
			for(Researcher doc : researcherArray) {
				int i= doc.getPositionInOrganization();
				if (doc.getYearsInAcademia() >= sackingAge[i-1] && (doc.getCitations() >= promotionTreshold*citationaverage[i-1])) 
					{
					doc.setPositionInOrganization(i+1);
					PromoteByLevels[i-1]++;
					PromotionAge[i-1]+=doc.getYearsInAcademia();
					}
			}				
			break;	
		}
		
		case("Position_based"):{
			for (int i=simulation.M.levelCount-1;i>0; i--)
			{
				for(Researcher doc : researcherArray) {
					if (doc.getPositionInOrganization() == i &&  doc.getYearsInPosition() >= sackingAge[i-1] && (doc.getCitations() >= promotionTreshold*citationaverage[i-1])) 
					{
						doc.setPositionInOrganization(i+1);
						PromoteByLevels[i-1]++;
						PromotionAge[i-1]+=doc.getYearsInAcademia();
					}
				}				
			}
			break;
		}
			
		case("Citation_only"): {
			for (int i=simulation.M.levelCount-1;i>0; i--){
				for(Researcher doc : researcherArray) {
					if (doc.getPositionInOrganization() == i && (doc.getCitations() >= promotionTreshold*citationaverage[i-1])) {
						doc.setPositionInOrganization(i+1);
						PromoteByLevels[i-1]++;
						PromotionAge[i-1]+=doc.getYearsInAcademia();
					}
				}			
			}
			break;
		}
			
		case("FixedHC"): {
			for (int i=0;i<simulation.M.levelCount;i++)
			{
				PromoteByLevels[i]=simulation.M.PositionLevels[i]-ResearchersByLevels[i];// n�in monta tarvitaan lis�� alemmilta tasoilta
				PromotionAge[i]=0;
			}
			Collections.sort(researcherArray, vertaaja2);
			Collections.reverse(researcherArray);
			int ii=simulation.M.levelCount-1;
			int jj=0;
			
			for(Researcher doc : researcherArray) {
				if(doc.getPositionInOrganization() < ii) {
					if(jj< PromoteByLevels[ii]) {
						System.out.println(jj+" "+PromoteByLevels[ii]);
						PromoteByLevels[ii]=jj;
						}
					PromoteByLevels[ii-1]+=PromoteByLevels[ii];
					ii--;
					jj=0;
				}
				if(doc.getPositionInOrganization() == ii && jj < PromoteByLevels[ii])
				{
					doc.setPositionInOrganization(ii+1);
					jj++;
					PromotionAge[ii-1]+=doc.getYearsInAcademia();
				}
			}
			for (int i=0; i<simulation.M.levelCount-1; i++){
			PromoteByLevels[i]=PromoteByLevels[i+1];
			}
			PromoteByLevels[simulation.M.levelCount-1]=0;
			break;
		}
			
		default: {
			System.out.println("Organisation: promotion model "+promotionModel+" not known");
		}
		}
				
	}
		


public void publish(int currentYear) {
	
	for (int i=0;i<simulation.M.levelCount;i++)
	{
		CurrentPapers[i]=0;
		CurrentCitations[i]=0;
	}
		for(Researcher researcher : researcherArray) {
			researcher.publish(currentYear);
		}
		
		citationsFromRemovedResearchers=0;	
		papersFromRemovedResearchers=0;
		for(Paper article:oldPapers) {
			citationsFromRemovedResearchers+=article.updateCitationsTT(currentYear);
			papersFromRemovedResearchers++;
			if(article.isDead()) {removedPapers.add(article);}
		}
		for(Paper article:removedPapers) {
			oldPapers.remove(article);
		}
		removedPapers.clear();
	}

	public void compareReceivedMoney() {
		main.simulation.mon.res=0;
		for (Researcher researcher : researcherArray) {
			main.simulation.mon.res+=researcher.getResourcesForResearch();
			researcher.setTotalFrustration();
			researcher.setProductivity();
		}
	}
	
	public int getTotalCitations() {
		totalCitations = 0;
		for (Researcher doc : researcherArray) {
			totalCitations += doc.getCitations();
		}
		return totalCitations;
	}

	public int getTotalPapers() {
		totalPapers = 0;
		for (Researcher doc : researcherArray) {
			totalPapers += doc.getPapers();
		}
		return totalPapers;
	}


}