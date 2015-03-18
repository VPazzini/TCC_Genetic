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
			ArrayList<String> fullSeqs) {

		int hashSize = motifSize / 4;
		int pos;
		ArrayList<Integer> positions = new ArrayList<>();
		Random r = new Random();

		ArrayList<String> seqs = new ArrayList<>();

		for (String s : fullSeqs) {
			for (int i = 0; i < s.length() - motifSize; i++) {
				seqs.add(s.substring(i, i + motifSize));
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

		ArrayList<Individual> temp = new ArrayList<>();
		for (String hash : buckets.keySet()) {
			Individual ind = null;
			for (String s : buckets.get(hash)) {

				if (ind == null) {
					ind = new Individual(s);
				} else {
					ind.getMatches().add(s);
				}
			}
			temp.add(ind);

		}

		for (int i = 0; i < temp.size(); i++) {
			individuals.add(new Individual(temp.get(i).consensus()));
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
				float f = find(i.getSequence(), ind.getSequence());
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
				float f = find(i.getSequence(), ind.getSequence());
				if (f > biggest) {
					biggest = f;
				}
			}
		}

		return biggest;
	}

	public float findInSequence(Individual ind, String seq, boolean verbose) {
		float match1, match2, temp = 0;
		String s = "";
		String subSeq = "";
		String tempSeq = "";
		String motif = ind.getSequence();
		for (int i = 0; i < seq.length() - motif.length(); i++) {
			subSeq = seq.substring(i, i + motif.length());

			match1 = find(motif, subSeq);
			match2 = find(ind.getRevSequence(), subSeq);
			if (match1 >= 0.8 && match1 > temp && match1 > match2) {
			//if (match1 >= Math.pow(2, motif.length()-2)-1 && match1 > temp && match1 >= match2) {
				temp = match1;
				tempSeq = subSeq;
			}
			if (match2 >= 0.8 && match2 > temp && match2 > match1) {
			//if (match2 >= Math.pow(2, motif.length()-2)-1 && match2 > temp && match2 >= match1) {
				temp = match2;
				tempSeq = reverse(subSeq);
			}
		}
		if (!s.equals("")) {
			System.out.println(s);
		}
		if (temp > 0) {
			ind.setPresence(ind.getPresence() + 1);
			ind.setFitness(ind.getFitness() + temp);
			// ind.setFitness(temp);
			// ind.getMatches().add(tempSeq + "|" + seq);
			ind.getMatches().add(tempSeq);
		}
		return temp;
	}

	public void findInAllSequences(ArrayList<String> sequences, Individual ind,
			boolean verb) {
		if (ind.getFitness() == 1) {
			int i = 0;
			for (String seq : sequences) {
				// System.out.println("seq" + i++);
				findInSequence(ind, seq, verb);
			}
		}
	}

	public void calculateFitness(ArrayList<String> sequences) {
		for (Individual ind : individuals) {
			findInAllSequences(sequences, ind, false);
			totalFitness += ind.getFitness();
		}
		this.sort();
	}

	public float find(String motif, String seq) {
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
		double right = 1, wrong = 1;
		for (int i = 0; i < motif.length(); i++) {
			if (motif.charAt(i) == seq.charAt(i)) {
				match += right;
				right += right;
				wrong = 1;
			} else {
				match -= wrong;
				wrong += wrong;
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
