package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

public class Population {

    private static Population population;
    private int popSize;
    private int numSequences = 0;
    private ArrayList<Individual> individuals = new ArrayList<>();
    private double thresholdComparison = 0.7;

    public Population() {
        this.individuals = new ArrayList<>();
    }

    public Population(int size, int motifSize) {
        for (int i = 0; i < size; i++) {
            Individual ind = new Individual(generateMotif(motifSize));
            this.individuals.add(ind);
        }
    }

    public ArrayList<Individual> getPopulation() {
        return individuals;
    }

    public void setPopulation(ArrayList<Individual> clone) {
        this.individuals = clone;
    }

    public void resetPopulation() {
        this.individuals = new ArrayList<>();
    }

    public void generatePopulation(int size, int motifSize) {
        for (int i = 0; i < size; i++) {
            Individual ind = new Individual(generateMotif(motifSize));
            individuals.add(ind);
        }

    }
    
    public void completePopulation(int size,int motifSize){
        for (int i = individuals.size(); i < size; i++) {
            Individual ind = new Individual(generateMotif(motifSize));
            individuals.add(ind);
        }
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
        String[] nucleotides = {"A", "C", "T", "G"};
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
            for (Sequence seq : sequences) {
                seq.findInSequence(ind, verb);
            }
        }

    }

    public void calculateFitness(ArrayList<Sequence> sequences) {
        this.numSequences = sequences.size();
        IntStream.range(0, individuals.size()).parallel().forEach(i -> {
            findInAllSequences(sequences, individuals.get(i), false);
        });
        
        this.sort();
    }

    public void cleanDuplicates() {
        ArrayList<Individual> cleaned = new ArrayList<>();
        for (Individual ind : individuals) {
            if (!cleaned.contains(ind)) {
                cleaned.add(ind);
            }
        }
        individuals = cleaned;
    }

    SWaterman sw;

    public void cleanDuplicatesWaterman() {

        ArrayList<Individual> cleaned = new ArrayList<>();
        for (Individual ind : individuals) {
            boolean b = false;
            for (Individual ind2 : cleaned) {

                sw = new SWaterman(ind.consensus(), ind2.consensus());
                int sco = sw.computeSmithWaterman();
                
                if (sco > (ind.consensus().length() * 2 * 0.8)) {
                    b = true;
                    break;
                }
                
                sw = new SWaterman(ind2.consensus(), ind.consensus());
                sco = sw.computeSmithWaterman();
                
                if (sco > (ind.consensus().length() * 2 * 0.8)) {
                    b = true;
                    break;
                }
                
                sw = new SWaterman(ind.consensus(), Util.reverse(ind2.consensus()));
                sco = sw.computeSmithWaterman();
                
                if (sco > (ind.consensus().length() * 2 * 0.8)) {
                    b = true;
                    break;
                }
                
                sw = new SWaterman(ind2.consensus(), Util.reverse(ind.consensus()));
                sco = sw.computeSmithWaterman();
                
                if (sco > (ind.consensus().length() * 2 * 0.8)) {
                    b = true;
                    break;
                }

            }
            if (!b) {
                cleaned.add(ind);
                //System.out.println("added " + ind.consensus());
            }
        }

        individuals = cleaned;
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

    public int getNumSequences() {
        return this.numSequences;
    }

    public double getThresholdComparison() {
        return thresholdComparison;
    }

    public void setThresholdComparison(double thresholdComparison) {
        this.thresholdComparison = thresholdComparison;
    }

}
