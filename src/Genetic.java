import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class Genetic {

	private ArrayList<Sequence> sequences = new ArrayList<>();
	private Population population;

	public Genetic() {
		this.population = Population.getInstance();
	}

	public void readFile(String fileName) {
		try {
			File newFile = new File(fileName);
			FileReader fw = new FileReader(newFile.getAbsoluteFile());
			BufferedReader out = new BufferedReader(fw);
			String line;
			String tempSequence = "";
			String seqName = "";
			while ((line = out.readLine()) != null) {
				if (line.length() > 0) {
					char c = line.charAt(0);
					if (c == 'A' || c == 'C' || c == 'T' || c == 'G') {
						tempSequence += line.trim();
					} else if (c == '>') {
						if(!seqName.equals("")){
							sequences.add(new Sequence(seqName, tempSequence));
							tempSequence = "";
						}
						seqName = line.split(" ")[0].substring(1);
					} else {
						if (!tempSequence.isEmpty()) {
							sequences.add(new Sequence(seqName, tempSequence));
							tempSequence = "";
						}
					}
				}
			}
			if (!tempSequence.isEmpty()) {
				sequences.add(new Sequence(seqName, tempSequence));
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

	public ArrayList<Sequence> getSequences() {
		return sequences;
	}

	/*public void setSequences(ArrayList<String> sequences) {
		this.sequences = sequences;
	}*/

	@SuppressWarnings("unchecked")
	public void run(int numGen, int size, int motifSize) {
		double t1 = System.currentTimeMillis();
		
		population.generatePopulation(size, motifSize);
		//population.rpsGeneratePopulation(size, motifSize, sequences);
		Selection select = new Selection();
		CrossOver crossOver = new CrossOver();

		double threshold = 0.85;
		//HashMap<String, Integer> history = new HashMap<>();
		
		for (int gen = 0; gen < numGen; gen++) {

			population.calculateFitness(sequences);

			System.out
					.println("Generation " + gen + " | time " +  (System.currentTimeMillis() - t1)/1000);
			t1 = System.currentTimeMillis();
			for (int k = 0; k < 10; k++) {
				System.out.println(population.getPopulation().get(k));
			}

			if (gen == numGen - 1) {
				break;
			}

			ArrayList<Individual> newPopulation = new ArrayList<>();
			int remaining = 0;

			// for (int j = 0; j <= size * 0.02; j++) {
			for (int j = 0; j <= 0; j++) {
				newPopulation.add(population.getPopulation().get(j));
				remaining++;
			}

			/*
			 * int popSize = newPopulation.size(); for (int j = 1; j < popSize;
			 * j++) {
			 * newPopulation.add(crossOver.bestOfEach(newPopulation.get(0)
			 * ,newPopulation.get(j))); remaining++; }
			 */

			for (int j = 0; j < size - remaining; j += 3) {

				Individual newInd1, newInd2, newInd3;
				Individual temp1, temp2;
				do {
					temp1 = select.roulletSelection();
					temp2 = select.roulletSelection();
					newInd1 = crossOver.twoPointCO(temp1, temp2);
					newInd2 = crossOver.twoPointCO(temp2, temp1);
					newInd3 = crossOver.bestOfEach(temp1, temp2);
				} while (population.presentInPopulation(newInd1, newPopulation) > threshold
						&& population.presentInPopulation(newInd2,
								newPopulation) > threshold
						&& population.presentInPopulation(newInd3,
								newPopulation) > threshold);

				newPopulation.add(newInd1);
				newPopulation.add(newInd2);
				newPopulation.add(newInd3);

			}

			/*
			 * for(Individual ind : population.getPopulation()){
			 * if(!history.containsKey(ind.getSequence())){
			 * history.put(ind.getSequence(), 1); }else{ int v =
			 * history.get(ind.getSequence()); history.put(ind.getSequence(),
			 * v+1); } }
			 */

			population.setPopulation((ArrayList<Individual>) newPopulation
					.clone());
		}

		System.out.println("------------------------------------");
		/*
		 * int sum = 0; for(String key : history.keySet()){
		 * System.out.println(key + " - " + history.get(key)); sum +=
		 * history.get(key); } System.out.println("Number of sequences tested "
		 * + history.size()); System.out.println("Number of tests " + sum);
		 */
	}

	public void resetPopulation() {
		this.population.resetPopulation();
	}

	public static void main(String[] args) {
		Genetic g = new Genetic();
		g.readFile("input/YDR026c_YPD.fasta");
		// g.readFile("input/ABF1_YPD.fsa");
		System.out.println(g.getSequences().size() + " Sequences");
		for (int k = 0; k < 1; k++) {
			g.run(10, 5000, 11);

			System.out.println("finished");
			for (Individual ind : Population.getInstance().getPopulation()) {
				ind.writeToFile();
			}
			g.writeToFile();
		}
	}

}
