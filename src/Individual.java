import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class Individual {
	private float fitness = 1;
	private String sequence;
	private String revSequence;
	private int presence = 0;
	private ArrayList<String> matches2 = new ArrayList<>();
	private HashMap<Sequence, Integer> matches = new HashMap<>();
	String[] nucleotides = { "A", "C", "T", "G" };

	public Individual(float fitness, String sequence) {
		this.fitness = fitness;
		this.sequence = sequence;
	}

	public Individual(String sequence) {
		this.sequence = sequence;
		revSequence = "";
		for (int i = sequence.length() - 1; i >= 0; i--) {
			if (sequence.charAt(i) == 'A') {
				revSequence += 'T';
			}
			if (sequence.charAt(i) == 'C') {
				revSequence += 'G';
			}
			if (sequence.charAt(i) == 'T') {
				revSequence += 'A';
			}
			if (sequence.charAt(i) == 'G') {
				revSequence += 'C';
			}
		}
	}

	public float getFitness() {
		return fitness;
	}

	public void setFitness(float fitness) {
		this.fitness = fitness;
	}

	public String getSequence() {
		return sequence;
	}

	public String getRevSequence() {
		return revSequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public int getPresence() {
		return presence;
	}

	public void setPresence(int presence) {
		this.presence = presence;
	}

	public HashMap<Sequence, Integer> getMatches() {
		return matches;
	}

	public float[][] matrix() {
		float[][] matrix = new float[4][sequence.length()];
		for (int i = 0; i < sequence.length(); i++) {
			for (Sequence key : matches.keySet()) {

				int init = matches.get(key);
				switch (key.getSubSequence(init, sequence.length()).charAt(i)) {
				case ('A'):
					matrix[0][i]++;
					break;
				case ('C'):
					matrix[1][i]++;
					break;
				case ('T'):
					matrix[2][i]++;
					break;
				case ('G'):
					matrix[3][i]++;
					break;
				}
			}
		}
		for (int i = 0; i < sequence.length(); i++) {
			for (int j = 0; j < 4; j++) {
				matrix[j][i] /= matches.size();
			}
		}
		return matrix;
	}

	public String consensus() {
		float[][] m = matrix();
		if (matches.isEmpty()) {
			return sequence;
		}
		String cons = "";
		float max = 0;
		int pos = 0;
		for (int i = 0; i < sequence.length(); i++) {
			max = 0;
			for (int j = 0; j < 4; j++) {
				if (m[j][i] > max) {
					max = m[j][i];
					pos = j;
				}
			}
			cons += nucleotides[pos];
		}

		return cons;
	}

	@Override
	public String toString() {
		return "Individual [fitness=" + fitness + ", sequence=" + sequence
				+ ", presence=" + presence + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(fitness);
		result = prime * result
				+ ((sequence == null) ? 0 : sequence.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Individual other = (Individual) obj;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		return true;
	}

	public void writeToFile() {
		BufferedWriter output = null;
		try {
			File file = new File("output.txt");
			output = new BufferedWriter(new FileWriter(file, true));
			// writer = new PrintWriter("output.txt", "UTF-8");

			output.write("Motif:\t" + sequence + "| Fitness: " + this.fitness
					+ "\n");
			output.write("Consensus:\t" + this.consensus() + "\n");
			output.write("Matches:\n");
			
			int seqN = 0;
			for (Sequence seq : matches.keySet()) {
				String m = seq.getSubSequence(matches.get(seq), this.sequence.length());
				output.write(seqN++ + ".\t");
				for (int i = 0; i < m.length(); i++) {
					if (m.charAt(i) == this.sequence.charAt(i)) {
						output.write((m.charAt(i) + "").toUpperCase());
					} else {
						output.write((m.charAt(i) + "").toLowerCase());
					}
				}
				output.write(" | "
						+ Population.getInstance().find(this.sequence, m)
						+ " | "
						+ Population.getInstance().similarity(this.sequence, m)
						* 100 + "% | " + seq.getName() + " (" + matches.get(seq)  + ")\n");
			}
			float[][] m = matrix();
			for (int j = 0; j < 4; j++) {
				output.write(nucleotides[j] + " ");
				for (int i = 0; i < sequence.length(); i++) {
					output.write(String.format("%.4f  ", m[j][i]));
				}
				output.write("\n");
			}
			output.write("--------------------------------------------------------\n");
			output.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
