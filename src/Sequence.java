public class Sequence {

	private String sequence;
	private String name;
	private Integer lenght;

	public Sequence(String name, String seq) {
		this.setName(name);
		this.setSequence(seq);
		this.lenght = sequence.length();
	}

	public float findInSequence(Individual ind, boolean verbose) {
		float match1, match2, temp = 0;
		double threshold = 0.7;
		String subSeq = "", tempSeq = "";
		String motif = ind.getSequence();
		int initSeq = 0;
		for (int i = 0; i < this.sequence.length() - motif.length(); i++) {
			subSeq = this.sequence.substring(i, i + motif.length());

			match1 = similarity(motif, subSeq);
			match2 = similarity(Util.reverse(motif), subSeq);
			if (match1 >= threshold && match1 > temp && match1 > match2) {
				temp = find(motif, subSeq);
				tempSeq = subSeq;
				initSeq = i;
			}
			if (match2 >= threshold && match2 > temp && match2 > match1) {
				temp = find(ind.getRevSequence(), subSeq);
				tempSeq = Util.reverse(subSeq);
				initSeq = -i;
			}
		}

		if (temp > 0) {
			ind.setPresence(ind.getPresence() + 1);
			ind.setFitness(ind.getFitness() + temp);
			//ind.getMatches().add(tempSeq);
			ind.getMatches().put(this, initSeq);
		}
		return temp;
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

	public String getSubSequence(int init, int size) {
		String seq = this.sequence.substring(Math.abs(init), Math.abs(init) + size);
		return init >= 0 ? seq : Util.reverse(seq);
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int lenght() {
		return this.lenght;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lenght == null) ? 0 : lenght.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Sequence other = (Sequence) obj;
		if (lenght == null) {
			if (other.lenght != null)
				return false;
		} else if (!lenght.equals(other.lenght))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		return true;
	}

}
