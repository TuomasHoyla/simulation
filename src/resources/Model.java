package resources;

public class Model {
	public String totalfile="result/totalfile.txt";
	public String datafile = "result/datafile.txt";
	public String logfile="result/logfile.txt";
	public String caseheader ="Case";
	public String instanssi;
	public String narrative;
//simulation related parameters	
	public String allocationScheme;
	public String evaluationErrorModel;
	public double arviointiVirhe=0.;
	public double overhead = 0; // 0 - 1
	public double kuinkaPaljonJaetaanTasan = 0; //0 -1
	public double maksimiTutkimusResurssi = 0.63; //0.63
	public double kuinkaPaljonMaksimiTutkimisResurssistaHalutaan = 1.;	
	public int PopulationSize = 100;
	public int[] PositionLevels = new int[4];
	public double AllocatableResource = 35.;
	public double odotusResurssi;
	public String promotionModel;
	public double promotionTreshold;
	public String publishingModel;
	public double publishingScale;
	public double publishingOffset;
	public double productivityCoefficient=0.;
	public String paperQualityModel;

// researcher related parameters
	public double oletusTutkimusAika = 0; //0.2325; //aika joka kaikilla k�yt�ss�
	public double neededToBeMotivated= 1; //pidet��n vakiona (k�ytet��n vain konstruktorissa nyt)
	public double hakemisenOsuus=0.5; // hakemisen aika osuutena oletustutkimusajasta 
	public double defaultMonetaryFrustration = 0;
	public String monetaryFrustrationModel;
	public double frustrationGrowthRate =0.1;
	public double[] frustrationRate = new double[4];
	public double secondaryFrustrationRate= 0.2;
	public double defaultPromotionalFrustration = 0;
	public String promotionalFrustrationModel;
	public int[] promotionalFrustrationAge = new int[4];
	public int[] sackingAge = new int[4];
	public double[] promotionalFrustrationrate = new double[4];
	public double sackingResistance=0.8;
	public double monetaryFrustrationWeight;
	public double promotionalFrustrationWeight;
	public String productivityModel;
	public double frustrationProductivityWeight;
	public double monetaryProductivityWeight;
	public String applicationQualityModel;
	public double aq1=1.;
	
	public String citationModel; // for Paper
	
	public String skillModel; // for random generators
	public double skillParameter;
	public String researchSkillModel;
	public double fitnessVariance;
	public double fitnessSkillFactor;
	public double researchSkillParameter;
	public double paperQualityParameter;



	public void resetGrant(){
		double resource =50;
		PopulationSize = 100;
		maksimiTutkimusResurssi = 1; 
		PositionLevels[0] = 25; PositionLevels[1]=25; PositionLevels[2]=25; PositionLevels[3]=25;
		allocationScheme="Grant";
		oletusTutkimusAika=0.*resource/PopulationSize;
		AllocatableResource = resource-PopulationSize*oletusTutkimusAika;
		publishingScale=PopulationSize/resource; 
		odotusResurssi=AllocatableResource/PopulationSize;
		kuinkaPaljonMaksimiTutkimisResurssistaHalutaan = 1-oletusTutkimusAika;	

		kuinkaPaljonJaetaanTasan = 0; //0 -1
		overhead = 0; // 0 - 100%

		publishingModel="Flatrate";
		productivityCoefficient=0.5;
		publishingOffset=1.;

		evaluationErrorModel="Blended"; 
		arviointiVirhe=0.;
		paperQualityModel = "Random_Skill_based";

	// researcher related parameters
		hakemisenOsuus=0.; // hakemisen aika osuutena oletustutkimusajasta 
		
		defaultMonetaryFrustration = 0;
//		monetaryFrustrationModel="Scaled";
		monetaryFrustrationModel="Tuned";
		frustrationGrowthRate =0.05/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[0]=0.08/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[1]=0.08/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[2]=0.05/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[3]=0.05/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		secondaryFrustrationRate=0.0;

//		promotionModel = "Citation_based";
		promotionModel = "Position_based";
		promotionTreshold = 1.5; //1. = average performance
		sackingAge[0]=4;
		sackingAge[1]=4;
		sackingAge[2]=8;
		sackingAge[3]=32;
		sackingResistance=0.75;

		defaultPromotionalFrustration = 0;		
		promotionalFrustrationModel="Time_in_Position";
		promotionalFrustrationAge[0] = 4;
		promotionalFrustrationAge[1] = 4;
		promotionalFrustrationAge[2] = 8;
		promotionalFrustrationAge[3] = 33;
		promotionalFrustrationrate[0] = 0.2; //k�yt�nn�ss� puolet tahdista toteutuu
		promotionalFrustrationrate[1] = 0.2;
		promotionalFrustrationrate[2] = 0.1;
		promotionalFrustrationrate[3] = 0.0;

		monetaryFrustrationWeight=1.0;
		promotionalFrustrationWeight=1.0;

		productivityModel="Summative";
		frustrationProductivityWeight=1.;
		monetaryProductivityWeight=0.0; //no monetary productivity

		applicationQualityModel="CombinedNormalized";
		aq1=1.;
		
		citationModel="WangPoisson";
		
		researchSkillModel="LogNormal";
		fitnessVariance=0.5*0.5;
		fitnessSkillFactor=0.5;
		researchSkillParameter=Math.sqrt(fitnessVariance*fitnessSkillFactor);// 0.2 + random kerroin Tuomaksella
		paperQualityParameter=Math.sqrt(fitnessVariance*(1-fitnessSkillFactor));
		skillModel="LogNormal"; // for random generators
		skillParameter=researchSkillParameter; //sama jakauma molemmille taidoille
	
	}

	
	public void configure(int malli){
		
		resetGrant(); //aluksi oletuksiin
//		instanssi=malli;
		productivityCoefficient=1.;
		monetaryFrustrationModel="Tuned";
		frustrationProductivityWeight=1.;
		promotionModel = "Position_based";
		promotionTreshold = 2.;

		switch(malli){
		// 1-12 hakemuksettomat skeemat perusvertailu
		case 0: {
			// initialize the headings
			logfile="result/logfile_test.txt";
			totalfile="result/totalfile_test.txt";
			datafile="result/datafile_test.txt";
			caseheader="Case; Frustration; Promotion";
			break;
		}
		case 1:{ //tasajako
			allocationScheme="Lottery";	
			kuinkaPaljonJaetaanTasan = 1; //0-1
			promotionTreshold=1.;
			secondaryFrustrationRate=0.0;
			instanssi="Communism; frus1;";
			break;
		}
		case 2:{//lotto
			allocationScheme="Lottery";
			kuinkaPaljonJaetaanTasan = 0; //0-1
			promotionTreshold=1.;
			secondaryFrustrationRate=0.0;
			instanssi="Lottery; frus1;";
			break;
		}
		case 3:{//oraakkeli
			allocationScheme="Grant";
			kuinkaPaljonJaetaanTasan = 0; //0-1
			promotionTreshold=1.;
			secondaryFrustrationRate=0.0;
			instanssi="Ideal; frus1;";
			break;
		}
		case 4:{// erehtyv� oraakkeli
			allocationScheme="Grant";
			arviointiVirhe=1.;
			kuinkaPaljonJaetaanTasan = 0; //0-1	
			promotionTreshold=1.;
			secondaryFrustrationRate=0.0;
			instanssi="Imperfect; frus1;";
			break;
		}
		case 5:{ //tasajako
			allocationScheme="Lottery";	
			kuinkaPaljonJaetaanTasan = 1; //0-1
			instanssi="Communism; frus2;";
			promotionTreshold=1.;
			secondaryFrustrationRate=0.1;
			promotionModel = "Position_based";
			break;
		}
		case 6:{//lotto
			allocationScheme="Lottery";
			kuinkaPaljonJaetaanTasan = 0; //0-1	
			instanssi="Lottery; frus2;";
			promotionTreshold=1.;
			secondaryFrustrationRate=0.1;
			promotionModel = "Position_based";
			break;
		}
		case 7:{//oraakkeli
			allocationScheme="Grant";
			kuinkaPaljonJaetaanTasan = 0; //0-1
			instanssi="Ideal; frus2;";
			promotionTreshold=1.;
			secondaryFrustrationRate=0.1;
			promotionModel = "Position_based";
			break;
		}
		case 8:{// erehtyv� oraakkeli
			allocationScheme="Grant";
			arviointiVirhe=.5;
			kuinkaPaljonJaetaanTasan = 0; //0-1
			instanssi="Imperfect; frus2;";
			promotionTreshold=1.;
			promotionModel = "Position_based";
			secondaryFrustrationRate=0.1;
			break;
		}
		case 9:{ //tasajako
			allocationScheme="Lottery";	
			kuinkaPaljonJaetaanTasan = 1; //0-1
			instanssi="Communism; frus2; selective";
			secondaryFrustrationRate=0.1;
			promotionTreshold=1.5;
			break;
		}
		case 10:{//lotto
			allocationScheme="Lottery";
			kuinkaPaljonJaetaanTasan = 0; //0-1	
			instanssi="Lottery; frus2; selective";
			secondaryFrustrationRate=0.1;
			promotionTreshold=1.5;
			break;
		}
		case 11:{//oraakkeli
			allocationScheme="Grant";
			kuinkaPaljonJaetaanTasan = 0; //0-1
			instanssi="Ideal; frus2; selective";
			secondaryFrustrationRate=0.1;
			promotionTreshold=1.5;
			break;
		}
		case 12:{// erehtyv� oraakkeli
			allocationScheme="Grant";
			arviointiVirhe=.5;
			kuinkaPaljonJaetaanTasan = 0; //0-1
			instanssi="Imperfect; frus2; selective";
			secondaryFrustrationRate=0.1;
			promotionTreshold=1.5;
			break;
		}
		
		
		} //end case
	updateNarrative(instanssi);

	}

	

	public void configureLottery16(int malli){
		resetGrant(); //aluksi oletuksiin
//		malli kulkee 0-15;
		int x1= malli/8;
		int x2 =(malli- x1*8)/4;
		int x3 = (malli - x1*8 -x2*4)/2;
		int x4 = malli- x1*8- x2*4 -x3*2;

		if(malli==0) {
			logfile="result/logfile_lottery16.txt";
			totalfile="result/totalfile_lottery16.txt";
			datafile="result/datafile_lottery16.txt";
			caseheader="Case; Flatfunding; Promotion; Resource; Productivity";

		}
		allocationScheme="Lottery";
		kuinkaPaljonJaetaanTasan=x1;
		promotionTreshold=1.+ x2*0.2;
		AllocatableResource=25+x3*8;
		publishingScale=4-x3;
		productivityCoefficient=x4*0.5;
//		instanssi= ""+ malli;
		instanssi=malli+"; "+100*x1+";"+promotionTreshold+";"+AllocatableResource+";"+productivityCoefficient+";";
		
		updateNarrative(instanssi);
	}	

	public void configureLottery32(int malli){
		resetGrant(); //aluksi oletuksiin
//		malli kulkee 0-31;
		int x1= 0;
		int x2 =(malli- x1*32)/16;
		int x3 = (malli - x1*32 -x2*16)/8;
		int x4 = (malli- x1*32- x2*16 -x3*8)/4;
		int x5 = (malli- x1*32- x2*16 -x3*8-x4*4)/2;
		int x6 = malli- x1*32- x2*16 -x3*8-x4*4-x5*2;
		
		if(malli==0) {
			logfile="result/logfile_lottery3235.txt";
			totalfile="result/totalfile_lottery3235.txt";
			datafile="result/datafile_lottery3235.txt";
			caseheader="Case; Flatfunding; Promotion; Resource; Productivity;SecondaryFrustrationRate";

		}
		allocationScheme="Lottery";
		kuinkaPaljonJaetaanTasan=x2;
		promotionTreshold=1.+ x3*0.2;
		AllocatableResource=25+x4*8;
		publishingScale=4-x4;
		productivityCoefficient=x5*0.5;
		secondaryFrustrationRate=x6*0.2;
		
		instanssi=malli+"; "+100*x2+";"+promotionTreshold+";"+AllocatableResource+";"+productivityCoefficient+";"+secondaryFrustrationRate;
		
		updateNarrative(instanssi);
	}	
	public void configureFulltest64b(int malli){
		resetGrant(); //aluksi oletuksiin
//		malli kulkee 0-63;
//		int x1= malli/32;
		int x2 =(malli)/16;
		int x3 = (malli -x2*16)/8;
		int x4 = (malli - x2*16 -x3*8)/4;
		int x5 = (malli - x2*16 -x3*8-x4*4)/2;
		int x6 = malli - x2*16 -x3*8-x4*4-x5*2;
		double resource;
		if(malli==0) {
			logfile="result/logfile_full64extreme.txt";
			totalfile="result/totalfile_full64extreme.txt";
			datafile="result/datafile_full64extreme.txt";
			caseheader="Case; Promotion; Resource; Frustratingspeed; SkillParameter";
		}
		resource=50+x4*10;
		publishingScale=100/resource;
		AllocatableResource = resource;
		odotusResurssi=AllocatableResource/PopulationSize;
		

		switch(x2) {
		case 0: {
			instanssi="Communism;";
			kuinkaPaljonJaetaanTasan=1;
			break;
		}
		case 1: {
			instanssi="Lottery;";
			kuinkaPaljonJaetaanTasan=0.;
			arviointiVirhe=1.;
			break;
		}
		case 2: {
			instanssi="Realistic;";
			kuinkaPaljonJaetaanTasan=0.0;//jaetaan oletustutkimusaikana
			aq1=0.5;
			arviointiVirhe=0.5;
			oletusTutkimusAika=0.5*resource/PopulationSize;
			AllocatableResource = resource-PopulationSize*oletusTutkimusAika;
			publishingScale=PopulationSize/resource; 
			odotusResurssi=AllocatableResource/PopulationSize;
			kuinkaPaljonMaksimiTutkimisResurssistaHalutaan = 1-oletusTutkimusAika;	
			hakemisenOsuus=0.2; // eli 10% kokonaisresurssista

			overhead=0.05;
			break;
		}
		case 3: {
			instanssi="Ideal;";
			kuinkaPaljonJaetaanTasan=0.;
			aq1=1.;
			arviointiVirhe=0.;
			break;
		}
		}
		monetaryProductivityWeight=0.; 
		frustrationProductivityWeight=1.;
		monetaryFrustrationModel="Tuned";
		frustrationGrowthRate =0.05/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[0]=0.08/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[1]=0.08/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[2]=0.05/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[3]=0.05/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);

		if( x3==1) {promotionModel="Fixed_HC";}
		secondaryFrustrationRate=0.+0.05*x5;
		fitnessSkillFactor=0.35+0.3*x6;
		researchSkillParameter=Math.sqrt(fitnessVariance*fitnessSkillFactor);// 0.2 + random kerroin Tuomaksella
		paperQualityParameter=Math.sqrt(fitnessVariance*(1-fitnessSkillFactor));
		skillParameter=researchSkillParameter; //sama jakauma molemmille taidoille

//		instanssi= ""+ malli;
		instanssi+=x3+";"+resource+";"+secondaryFrustrationRate+";"+fitnessSkillFactor;
		
		updateNarrative(instanssi);
	}	
	public void configureResource(int malli){
		resetGrant(); //aluksi oletuksiin
//		malli kulkee 0-99;
		int x1 = malli/20;
		int x2 =(malli-x1*20)/5;
		int x3 = (malli -x1*20-x2*5);
		double resource;
		if(malli==0) {
			logfile="result/logfile_resource_nonmonetary.txt";
			totalfile="result/totalfile_resource_nonmonetary.txt";
			datafile="result/datafile_resource_nonmonetary.txt";
			caseheader="Case; Even allocation;Resource";
		}

		resource=10*(x3+2);
		publishingScale=100/resource;
		AllocatableResource = resource;
		odotusResurssi=AllocatableResource/PopulationSize;
		

		switch(x1) {
		case 0: {
			instanssi="Communism;";
			allocationScheme="Communism";	
			kuinkaPaljonJaetaanTasan=1;
			break;
		}
		case 1: {
			instanssi="Lottery;";
			allocationScheme="Lottery";	
			kuinkaPaljonJaetaanTasan=0.25*x2;
			arviointiVirhe=1.;
			break;
		}
		case 2: {
			instanssi="Realistic;";
			oletusTutkimusAika=0.25*x2*resource/PopulationSize;
			aq1=0.5;
			arviointiVirhe=0.5;
			AllocatableResource = resource-PopulationSize*oletusTutkimusAika;
			publishingScale=PopulationSize/resource; 
			odotusResurssi=AllocatableResource/PopulationSize;
			kuinkaPaljonMaksimiTutkimisResurssistaHalutaan = 1-oletusTutkimusAika;	
			hakemisenOsuus=0.2; // eli 10% kokonaisresurssista

			overhead=0.05;
			break;
		}
		case 3: {
			kuinkaPaljonJaetaanTasan=0.25*x2;
			instanssi="Ideal; ";
			aq1=1.;
			arviointiVirhe=0.;
			break;
		}
		}
		monetaryProductivityWeight=0.; 
		frustrationProductivityWeight=1.;
		monetaryFrustrationModel="Tuned";
		frustrationGrowthRate =0.05/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[0]=0.08/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[1]=0.08/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[2]=0.05/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[3]=0.05/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		
		secondaryFrustrationRate=0.;

//		instanssi= ""+ malli;
		instanssi+=x2+"; "+resource;
		
		updateNarrative(instanssi);
	}	
	

	public void configureProductivity(int malli){
		resetGrant(); //aluksi oletuksiin
//		malli kulkee 0-99;
		int x1 = malli/25;
		int x2 =(malli-x1*25)/5;
		int x3 = (malli -x1*25-x2*5);
		if(malli==0) {
			logfile="result/logfile_productivity1.txt";
			totalfile="result/totalfile_productivity1.txt";
			datafile="result/datafile_productivity1.txt";
			caseheader="Case; monetary frustration;frustration productivity";
		}
		productivityCoefficient=1.;
		monetaryFrustrationModel="Tuned";
		frustrationProductivityWeight=0.2*x3;
		promotionModel = "Position_based";
		monetaryProductivityWeight=0.2*x2; 


		switch(x1) {
		case 0: {
			instanssi="Communism;";
			allocationScheme="Grant";	
			kuinkaPaljonJaetaanTasan=1;
			break;
		}
		case 1: {
			instanssi="Lottery;";
			allocationScheme="Grant";	
			kuinkaPaljonJaetaanTasan=0.5;
			arviointiVirhe=1.;
			break;
		}
		case 2: {
			instanssi="Realistic;";
			aq1=0.5;
			kuinkaPaljonJaetaanTasan=0.5;
			arviointiVirhe=0.5;

			overhead=0.05;
			break;
		}
		case 3: {
			instanssi="Ideal; ";
			kuinkaPaljonJaetaanTasan=0.5;
			aq1=1.;
			arviointiVirhe=0.;
			break;
		}
		}


//		instanssi= ""+ malli;
		instanssi+=x2+"; "+x3;
		
		updateNarrative(instanssi);
	}	
	

	
	public void configureWriting(int koe) {
		if(koe==0) {
			logfile="result/logfile_writing.txt";
			totalfile="result/totalfile_writing.txt";
			datafile="result/datafile_writing.txt";
		}	
		resetGrant();
		applicationQualityModel="CombinedNormalized";
		aq1=0.1*koe;
		instanssi= "R"+koe*10;
	
		updateNarrative(instanssi);
	}
	public void configureEvaluation(int koe) {
		if(koe==0) {
			logfile="result/logfile_evaluation.txt";
			totalfile="result/totalfile_evaluation.txt";
			datafile="result/datafile_evaluation.txt";
		}	
		resetGrant();
		applicationQualityModel="CombinedNormalized";
		aq1=1.; // 
		evaluationErrorModel="Blended";
		arviointiVirhe=0.1*koe;
		instanssi="E"+koe*10;
	
		updateNarrative(instanssi);
	}
	public void configureWritingTime(int koe) {
		if(koe==0) {
			logfile="result/logfile_writingtime65.txt";
			totalfile="result/totalfile_writingtime65.txt";
			datafile="result/datafile_writingtime65.txt";
		}	
		double resource=50;//kokonaisresurssi (oletus + jaettava)
		resetGrant();
		applicationQualityModel="CombinedNormalized";
		aq1=0.5;
		evaluationErrorModel="Blended";
		arviointiVirhe=0.5;
		oletusTutkimusAika=0.5*resource/PopulationSize;//puolet
		AllocatableResource = resource-PopulationSize*oletusTutkimusAika;
		publishingScale=PopulationSize/resource; 
		odotusResurssi=AllocatableResource/PopulationSize;
		kuinkaPaljonMaksimiTutkimisResurssistaHalutaan = 1-oletusTutkimusAika;	
		hakemisenOsuus=0.1*koe;
		monetaryProductivityWeight=0.; 
		frustrationProductivityWeight=1.;
		monetaryFrustrationModel="Tuned";
		frustrationGrowthRate =0.05/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[0]=0.08/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[1]=0.08/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[2]=0.05/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		frustrationRate[3]=0.05/(1-odotusResurssi/kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
		secondaryFrustrationRate=0.;
		fitnessSkillFactor=0.65; //!!!!
		researchSkillParameter=Math.sqrt(fitnessVariance*fitnessSkillFactor);// 0.2 + random kerroin Tuomaksella
		paperQualityParameter=Math.sqrt(fitnessVariance*(1-fitnessSkillFactor));
		skillParameter=researchSkillParameter; //sama jakauma molemmille taidoille
		
		instanssi="T"+koe*10;
		updateNarrative(instanssi);
	}

	public void configureCapitalism(int koe) {
		if(koe==0) {
			logfile="result/logfile_capitalism.txt";
			totalfile="result/totalfile_capitalism.txt";
			datafile="result/datafile_capitalism.txt";
			caseheader="Promotion; SkillWeight; EvaluationError";
		}
		// runs from 0 to 49
		int x0=koe/25;
		int x1=(koe-25*x0)/5;
		int x2 = koe-25*x0-5*x1;
				
		resetGrant();
		double resource=50;
//		instanssi="Realistic;";
		monetaryFrustrationModel="Tuned";
		monetaryProductivityWeight=0.; 
		frustrationProductivityWeight=1.;
		secondaryFrustrationRate=0.;
		oletusTutkimusAika=0.5*resource/PopulationSize;//puolet
		AllocatableResource = resource-PopulationSize*oletusTutkimusAika;
		publishingScale=PopulationSize/resource; 
		odotusResurssi=AllocatableResource/PopulationSize;
		kuinkaPaljonMaksimiTutkimisResurssistaHalutaan = 1-oletusTutkimusAika;	
		hakemisenOsuus=0.2;

		overhead=0.05;
		fitnessSkillFactor=0.35;
		researchSkillParameter=Math.sqrt(fitnessVariance*fitnessSkillFactor);// 0.2 + random kerroin Tuomaksella
		paperQualityParameter=Math.sqrt(fitnessVariance*(1-fitnessSkillFactor));
		skillParameter=researchSkillParameter; //sama jakauma molemmille taidoille

		aq1=1-x1*0.2;
		evaluationErrorModel="Blended";
		arviointiVirhe=x2*0.2;
		if(x0==1) {promotionModel="Fixed_HC";}
		instanssi=x0+"; "+aq1+"; "+arviointiVirhe;
		updateNarrative(instanssi);
	}

	

	public void setNarrative(){
		narrative="Default values \n";
		narrative+="Population size = "+ PopulationSize + " allocatable resource = "+AllocatableResource;
		narrative+=" Allocation Scheme "+allocationScheme+ "\n";
		narrative+="Promotion model is "+promotionModel+ " "+promotionTreshold+" Publishing model is "+publishingModel+" "+publishingScale+"\n";
		narrative+="Evaluation error model is "+evaluationErrorModel+"\n";
		narrative+="Paper quality model "+paperQualityModel+" Productivity model "+productivityModel+"\n";
		narrative+="Application quality model "+applicationQualityModel+"\n";
		narrative+="Skill model "+skillModel+" "+skillParameter;
		narrative+=" Research skill model "+researchSkillModel+" "+researchSkillParameter+"\n";
		narrative+="Citation model "+citationModel+"\n";
		narrative+="Frustration model "+monetaryFrustrationModel+" "+frustrationGrowthRate+" "+secondaryFrustrationRate+"\n";
		narrative+="Frustration rates "+frustrationRate[0]+" "+frustrationRate[1]+" "+frustrationRate[2]+" "+frustrationRate[3]+"\n";
		narrative+="Promotional frustration ages "+promotionalFrustrationAge[0]+" "+promotionalFrustrationAge[1]+" "+promotionalFrustrationAge[2]+" "+promotionalFrustrationAge[3]+"\n";
		narrative+="Promotional frustration rates "+promotionalFrustrationrate[0]+" "+promotionalFrustrationrate[1]+" "+promotionalFrustrationrate[2]+" "+promotionalFrustrationrate[3]+"\n";
		narrative+="Retirement starting age "+sackingAge[3]+" resistance rate "+sackingResistance+"\n";

		}
	public void updateNarrative(String text){
	narrative+=text+"\n";
	}
}