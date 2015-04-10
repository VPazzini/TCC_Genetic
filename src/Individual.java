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
	private float fitness2 = 1;

	private String sequence;
	private String revSequence;
	private int presence = 0;

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
		return calculateFitness();
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

	public void addMatch(Sequence s, int init) {
		matches.put(s, init);
		this.changed = true;
	}

	public float pwm(Sequence seq, String subSeq) {

		float[] count = seq.getFreq();
		float[][] m = matrix();
		for (char c : subSeq.toCharArray()) {
			switch (c) {
			case ('A'):
				count[0]--;
				break;
			case ('C'):
				count[1]--;
				break;
			case ('T'):
				count[2]--;
				break;
			case ('G'):
				count[3]--;
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			count[i] /= (seq.lenght() - sequence.length());
		}
		for (int i = 0; i < sequence.length(); i++) {
			for (int j = 0; j < 4; j++) {
				m[j][i] /= count[j];
			}
		}

		float score = 0;
		int index = 0;
		for (char c : subSeq.toCharArray()) {
			switch (c) {
			case ('A'):
				score += Math.log(m[0][index]);
				break;
			case ('C'):
				score += Math.log(m[1][index]);
				break;
			case ('T'):
				score += Math.log(m[2][index]);
				break;
			case ('G'):
				score += Math.log(m[3][index]);
				break;
			}
			index++;
		}

		// if (score > 0)

		// System.out.println(seq.getName() + " " + init + " "+
		// seq.getSubSequence(init, sequence.length()) + " "+ score);

		return score;

	}

	private boolean changed = true;
	private float[][] pwmMatrix;

	public float[][] matrix() {
		if (changed) {
			pwmMatrix = new float[4][sequence.length()];

			for (int i = 0; i < sequence.length(); i++) {
				for (int j = 0; j < 4; j++) {
					pwmMatrix[j][i] = (float) 0.01;
				}
			}

			for (int i = 0; i < sequence.length(); i++) {
				// for (char c : sequence.toCharArray()) {
				switch (sequence.charAt(i)) {
				case ('A'):
					pwmMatrix[0][i]++;
					break;
				case ('C'):
					pwmMatrix[1][i]++;
					break;
				case ('T'):
					pwmMatrix[2][i]++;
					break;
				case ('G'):
					pwmMatrix[3][i]++;
					break;
				}
				// }
			}

			for (int i = 0; i < sequence.length(); i++) {
				for (Sequence key : matches.keySet()) {

					int init = matches.get(key);
					switch (key.getSubSequence(init, sequence.length()).charAt(
							i)) {
					case ('A'):
						pwmMatrix[0][i]++;
						break;
					case ('C'):
						pwmMatrix[1][i]++;
						break;
					case ('T'):
						pwmMatrix[2][i]++;
						break;
					case ('G'):
						pwmMatrix[3][i]++;
						break;
					}
				}
			}
			for (int i = 0; i < sequence.length(); i++) {
				for (int j = 0; j < 4; j++) {
					pwmMatrix[j][i] /= (matches.size() + 1.04);
				}
			}
			changed = false;
		}
		float[][] temp = new float[4][sequence.length()];
		for (int i = 0; i < sequence.length(); i++) {
			for (int j = 0; j < 4; j++) {
				temp[j][i] = pwmMatrix[j][i];
			}
		}
		return temp;
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
		return "Individual [fitness=" + calculateFitness() + ", sequence="
				+ consensus() + ", presence=" + presence + "]";
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
			// } else if (!sequence.equals(other.sequence))
		} else if (!consensus().equals(other.consensus()))
			return false;
		return true;
	}

	public float calculateFitness() {
		float temp = 0;
		if (matches.isEmpty()) {
			return 1;
		}
		for (Sequence seq : matches.keySet()) {
			String m = seq.getSubSequence(matches.get(seq),
					this.sequence.length());

			temp += this.pwm(seq, m);

		}
		/*return temp
				/ (matches.size())
				- ((Population.getInstance().getNumSequences() / 2) / matches
						.size());*/
		temp = temp/(matches.size())-((3*this.sequence.length())/(matches.size()));
		if (temp < 0){
			return 1;
		}else{
			return temp;
		}
	}

	public void writeToFile() {
		BufferedWriter output = null;
		try {
			File file = new File("output.txt");
			output = new BufferedWriter(new FileWriter(file, true));
			// writer = new PrintWriter("output.txt", "UTF-8");

			output.write("Motif:\t" + sequence + "| Fitness: "
					+ this.calculateFitness() + "\n");
			output.write("Consensus:\t" + this.consensus() + "\n");
			output.write("Matches:\n");

			int seqN = 0;
			for (Sequence seq : matches.keySet()) {
				String m = seq.getSubSequence(matches.get(seq),
						this.sequence.length());
				output.write(seqN++ + ".\t");
				String con = this.consensus();
				for (int i = 0; i < m.length(); i++) {
					// if (m.charAt(i) == this.sequence.charAt(i)) {

					if (m.charAt(i) == con.charAt(i)) {
						output.write((m.charAt(i) + "").toUpperCase());
					} else {
						output.write((m.charAt(i) + "").toLowerCase());
					}
				}
				output.write(" | "
						// + Population.getInstance().find(this.sequence, m)
						+ this.pwm(seq, m) + " | "
						+ Population.getInstance().similarity(con, m) * 100
						+ "% | " + seq.getName() + " (" + matches.get(seq)
						+ ")\n");
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
