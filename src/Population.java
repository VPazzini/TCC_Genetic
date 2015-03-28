import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

public class Population {

	private static Population population;
	private int totalFitness = 0;
	private ArrayList<Individual> individuals = new ArrayList<>();

	public Population() {
		this.individuals = new ArrayList<>();
		totalFitness = 0;
	}

	public Population(int size, int motifSize) {
		for (int i = 0; i < size; i++) {
			Individual ind = new Individual(generateMotif(motifSize));
			this.individuals.add(ind);
		}
		totalFitness = 0;
	}

	public ArrayList<Individual> getPopulation() {
		return individuals;
	}

	public void setPopulation(ArrayList<Individual> clone) {
		this.individuals = clone;
		totalFitness = 0;
	}

	public void resetPopulation() {
		this.individuals = new ArrayList<>();
		totalFitness = 0;
	}

	private String reverse(String sequence) {
		String rev = "";
		for (int i = sequence.length() - 1; i >= 0; i--) {
			if (sequence.charAt(i) == 'A') {
				rev += 'T';
			}
			if (sequence.charAt(i) == 'C') {
				rev += 'G';
			}
			if (sequence.charAt(i) == 'T') {
				rev += 'A';
			}
			if (sequence.charAt(i) == 'G') {
				rev += 'C';
			}
		}
		return rev;
	}

	public void generatePopulation(int size, int motifSize) {
		for (int i = 0; i < size; i++) {
			Individual ind = new Individual(generateMotif(motifSize));
			individuals.add(ind);
		}
		totalFitness = 0;
	}

	public void rpsGeneratePopulation(int size, int motifSize,
			ArrayList<Sequence> sequences) {

		// int hashSize = (motifSize / 2);
		int hashSize = (int) Math.ceil((Math.log(size) / Math.log(4)));
		int pos;
		ArrayList<Integer> positions = new ArrayList<>();
		Random r = new Random();

		ArrayList<String> seqs = new ArrayList<>();

		for (Sequence s : sequences) {
			for (int i = 0; i < s.lenght() - motifSize; i++) {
				seqs.add(s.getSequence().substring(i, i + motifSize));
			}
		}

		for (int i = 0; i < hashSize; i++) {
			do {
				pos = r.nextInt(motifSize);
			} while (positions.contains(pos));
			positions.add(pos);
		}

		HashMap<String, ArrayList<String>> buckets = new HashMap<>();

		for (String seq : seqs) {
			String hash = "";
			for (int i : positions) {
				hash += seq.charAt(i);
			}

			if (!buckets.containsKey(hash)) {
				buckets.put(hash, new ArrayList<>());
			}
			buckets.get(hash).add(seq);
		}

		for (String hash : buckets.keySet()) {
			Individual ind = new Individual(Util.consensus(buckets.get(hash)));
			individuals.add(ind);

		}

	}

	public String generateMotif(int size) {
		Random r = new Random();
		String[] nucleotides = { "A", "C", "T", "G" };
		String motif = "";
		for (int i = 0; i < size; i++) {
			motif += nucleotides[r.nextInt(4)];
		}
		return motif;
	}

	public float presentInPopulation(Individual ind) {
		float biggest = -1;
		for (Individual i : individuals) {
			if (i != ind) {
				float f = similarity(i.getSequence(), ind.getSequence());
				if (f > biggest) {
					biggest = f;
				}
			}
		}

		return biggest;
	}

	public float presentInPopulation(Individual ind, ArrayList<Individual> l) {
		if (l.isEmpty()) {
			return 0;
		}
		float biggest = -1;
		for (Individual i : l) {
			if (i != ind) {
				float f = similarity(i.getSequence(), ind.getSequence());
				if (f > biggest) {
					biggest = f;
				}
			}
		}

		return biggest;
	}


	public void findInAllSequences(ArrayList<Sequence> sequences,
			Individual ind, boolean verb) {
		if (ind.getFitness() == 1) {
			int i = 0;
			for (Sequence seq : sequences) {
				// System.out.println("seq" + i++);
				seq.findInSequence(ind, verb);
				// findInSequence(ind, seq, verb);
			}
		}
	}

	public void calculateFitness(ArrayList<Sequence> sequences) {
		for (Individual ind : individuals) {
			findInAllSequences(sequences, ind, false);
			totalFitness += ind.getFitness();
		}
		this.sort();
	}

	public float similarity(String motif, String seq) {
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

	public float find2(String motif, String seq) {
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

	public float find(String motif, String seq) {
		if (motif.length() != seq.length()) {
			return 0;
		}
		float match = 0;
		double right = 1, wrong = 1;
		for (int i = 0; i < motif.length(); i++) {
			if (motif.charAt(i) == seq.charAt(i)) {
				match += right;
				right += 1;
				wrong = 1;
			} else {
				match -= wrong;
				wrong += 1;
				right = 1;
			}
		}
		return match;
	}

	public void sort() {
		Collections.sort(individuals, new CompareIndividual());
	}

	public int size() {
		return individuals.size();
	}

	public static synchronized Population getInstance() {
		if (population == null) {
			population = new Population();
		}

		return population;
	}

}
