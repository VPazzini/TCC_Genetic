import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetic {

	private ArrayList<String> sequences = new ArrayList<>();
	// private ArrayList<Individual> population = new ArrayList<>();
	private Population population;

	// private int generation = 0;
	
	public Genetic(){
		this.population = Population.getInstance();
	}
	
	public void readFile(String fileName) {
		try {
			File newFile = new File(fileName);
			FileReader fw = new FileReader(newFile.getAbsoluteFile());
			BufferedReader out = new BufferedReader(fw);
			String line;
			String tempSequence = "";
			while ((line = out.readLine()) != null) {
				if (line.length() > 0) {
					char c = line.charAt(0);
					if (c == 'A' || c == 'C' || c == 'T' || c == 'G') {
						tempSequence += line.trim();
					} else {
						if (!tempSequence.isEmpty()) {
							sequences.add(tempSequence);
							tempSequence = "";
						}
					}
				}
			}
			if (!tempSequence.isEmpty()) {
				sequences.add(tempSequence);
			}
			out.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	public void writeToFile() {
		try {
			File newFile = new File("saida.fasta");
			FileWriter fw = new FileWriter(newFile.getAbsoluteFile());
			BufferedWriter out = new BufferedWriter(fw);

			for (Individual ind : Population.getInstance().getPopulation()) {
				out.write(ind.toString() + "\n");

			}
			out.close();
		} catch (Exception e) {

		}

	}

	public ArrayList<String> getSequences() {
		return sequences;
	}

	public void setSequences(ArrayList<String> sequences) {
		this.sequences = sequences;
	}

	

	private float find(String motif, String seq) {
		if (motif.length() != seq.length()) {
			return 0;
		}
		float match = 0;
		for (int i = 0; i < motif.length(); i++) {
			if (motif.charAt(i) == seq.charAt(i)) {
				match++;
			}
		}
		return match / motif.length();
	}



	public Individual crossOver(Individual ind1, Individual ind2) {
		Random r = new Random();
		// System.out.println(ind1.getSequence().length());
		int divPoint = r.nextInt(ind1.getSequence().length() - 3) + 1;
		String seq = ind1.getSequence().substring(0, divPoint);
		seq += ind2.getSequence().substring(divPoint);

		if (r.nextFloat() <= 0.01) {
			String[] nucleotides = { "A", "C", "T", "G" };
			//String n = "ACTG";
			int pos = r.nextInt(ind1.getSequence().length());
			/*if (ind1.matrix()[n.lastIndexOf(ind1.getSequence().charAt(pos))][pos] != 1
					&& ind2.matrix()[n.lastIndexOf(ind2.getSequence().charAt(
							pos))][pos] != 1) {
			*/
				String p1 = seq.substring(0, pos);
				String p2 = seq.substring(pos + 1);
				seq = p1 + nucleotides[r.nextInt(4)] + p2;
			//}
		}

		Individual ind = new Individual(seq);

		//if (!population.contains(ind)) {
			return ind;
		/*}
		return new Individual(generateMotif(ind1.getSequence().length()));
		*/
	}


	public void run(int numGen, int size, int motifSize) {
		//population = new Population(size, motifSize);
		//population.generatePopulation(size, motifSize);
		population.rpsGeneratePopulation(size, motifSize, sequences);
		//population.getPopulation().add(new Individual("TTTACCCGGCC"));
		Selection select = new Selection();
		
		double threshold = 0.8;
		
		for (int gen = 0; gen < numGen; gen++) {

			population.calculateFitness(sequences);

			

			System.out.println("---------------Gen " + gen +  "----------------");
			for (int k = 0; k < 10; k++) {
				System.out.println(population.getPopulation().get(k));
			}
			
			if (gen == numGen - 1) {
				break;
			}
			
			ArrayList<Individual> newPopulation = new ArrayList<>();
			float i = 0;

			for (int j = 0; j < size; j++) {
				if (i / size <= 0.95) {
				//if (i <  (size - 2)) {
					Individual newInd1, newInd2;
					do {
						newInd1 = crossOver(select.roulletSelection(),
								select.roulletSelection());
					} while (population.presentInPopulation(newInd1,newPopulation) > threshold);

					do {
						newInd2 = crossOver(select.roulletSelection(),
								select.roulletSelection());
					} while (population.presentInPopulation(newInd2,newPopulation) > threshold);

					newPopulation.add(newInd1);
					newPopulation.add(newInd2);
					i += 2;
				} else {
					break;
				}
				
			}
			
			for (int j = 0; j < size - i; j++) {
				//if(population.presentInPopulation(population.getPopulation().get(j),newPopulation) <= 0.9){
					newPopulation.add(population.getPopulation().get(j));
				//}
			}
			
			population.setPopulation((ArrayList<Individual>) newPopulation.clone());
		}

		System.out.println("------------------------------------");

	}

	public void resetPopulation() {
		this.population.resetPopulation();
	}

	public static void main(String[] args) {
		Genetic g = new Genetic();
		g.readFile("input/YDR026c_YPD.fasta");
		//g.readFile("2p53.fasta");
		//g.readFile("hm20g.fasta");
		System.out.println(g.getSequences().size() + " Sequences");
		for (int k = 0; k < 1; k++) {
			g.run(50, 100, 8);
			
			System.out.println("finished");
			for (Individual ind : Population.getInstance().getPopulation()) {
				ind.writeToFile();
			}
			g.writeToFile();
		}
	}
	
	
}
