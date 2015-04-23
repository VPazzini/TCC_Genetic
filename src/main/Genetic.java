package main;

import GUI.MainWindow;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Genetic implements Runnable {

    private final ArrayList<Sequence> sequences = new ArrayList<>();
    private final Population population;
    private int gen;

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
                        if (!seqName.equals("")) {
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

    public ArrayList<Individual> readPopulationFile(String filePath) {
        ArrayList<Individual> inputPopulation = null;
        try {
            inputPopulation = new ArrayList<>();
            File newFile = new File(filePath);
            FileReader fw = new FileReader(newFile.getAbsoluteFile());
            BufferedReader input = new BufferedReader(fw);
            String line;
            while ((line = input.readLine()) != null) {
                if (line.length() > 0) {
                    inputPopulation.add(new Individual(line.trim()));
                }
            }

            input.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return inputPopulation;
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

    private int maxGens;
    private int size;
    private int motifSize;
    private double survivors;
    private int selectionMethod;
    private MainWindow window;
    private Selection select;
    private CrossOver crossOver;

    public void setUp(int maxGens, int size, int motifSize, int generateMethod, String pathToInputFile,
            double survivors, int selectionMethod, MainWindow window) {
        this.maxGens = maxGens;
        this.size = size;
        this.motifSize = motifSize;
        this.survivors = survivors;
        this.selectionMethod = selectionMethod;
        this.window = window;

        select = new Selection();
        crossOver = new CrossOver();

        // Generate the initial population
        population.setPopulation(new ArrayList<>());
        if (size > 0) {
            if (generateMethod == 0 || generateMethod == 1) {
                population.generatePopulation(size, this.motifSize);
            } else if (generateMethod == 2) {
                population.rpsGeneratePopulation(size, this.motifSize, sequences);
            }
        }
        if (!pathToInputFile.equals("")) {
            population.getPopulation().addAll(readPopulationFile(pathToInputFile));
        }

        //population.getPopulation().add(new Individual("TTCTGCTCTTC"));
    }

    @Override
    public void run() {

        // Threshold value that determines how much of a new Individual can be
        // equal to another individual in the population, using it to increase
        // diversity
        double threshold = 0.9;
        //double t1 = System.currentTimeMillis();
        // Main loop
        Individual best = null;
        int shiftCount = 0;
        for (gen = 0; gen < maxGens; gen++) {

            population.calculateFitness(sequences);

            
            
            
            //population.cleanDuplicates();
            population.cleanDuplicatesWaterman();
            population.completePopulation(this.size, this.motifSize);

            window.attGeneration(population.getPopulation());

            if (gen == maxGens - 1 || shiftCount >= maxGens * 0.20) {
                break;
            }

            ArrayList<Individual> newPopulation = new ArrayList<>();
            int spaceUsed = 0;

            if (population.getPopulation().get(0) == best) {
                shiftCount++;
            } else {
                best = population.getPopulation().get(0);
                shiftCount = 0;
            }

            // Save the more adapted individual from the current generation
            // to the new generation
            for (int j = 0; j < size * survivors; j++) {
                newPopulation.add(population.getPopulation().get(j));
                spaceUsed++;
            }
            if (shiftCount >= 5) {
                for (int j = 0; j < size * 0.10; j++) {
                    newPopulation.addAll(crossOver.shift(population
                            .getPopulation().get(j)));
                    spaceUsed += 2;
                }
            }

            // Fill up the remaining population space using crossover methods
            // and try to increase the diversity, the while statement should
            // be all OR's instead of AND's but it would increase the
            // computational cost a lot
            for (int j = 0; j < size - spaceUsed; j += 3) {

                Individual newInd1, newInd2, newInd3;
                Individual temp1 = null, temp2 = null;
                do {
                    if (selectionMethod == 1 || selectionMethod == 0) {
                        temp1 = select.randomSelection();
                        temp2 = select.randomSelection();
                    } else if (selectionMethod == 2) {
                        temp1 = select.roulletSelection();
                        temp2 = select.roulletSelection();
                    }

                    newInd1 = crossOver.onePointCO(temp1, temp2);
                    newInd2 = crossOver.onePointCO(temp2, temp1);
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

            population.setPopulation((ArrayList<Individual>) newPopulation
                    .clone());

        }

        population.cleanDuplicatesWaterman();
        
        window.attGeneration(population.getPopulation());

        this.writeToFile();
        window.finished();

    }

    public void resetPopulation() {
        this.population.resetPopulation();
    }

    public int getGen() {
        return gen;
    }

    public void setGen(int gen) {
        this.gen = gen;
    }

}
