package lab14;

import java.util.ArrayList;

import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;
import lab14lib.GeneratorDrawer;
import lab14lib.GeneratorPlayer;
import lab14lib.MultiGenerator;

public class Main {
	public static void main(String[] args) {
		/** Your code here. */
	// Generator generator = new SineWaveGenerator(200);
    // GeneratorPlayer gp = new GeneratorPlayer(generator);
    // gp.play(100000);
	// GeneratorDrawer gd = new GeneratorDrawer(generator);
	// gd.draw(4096);
	// GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
	// gav.drawAndPlay(4096, 100000);
	// Generator g1 = new SineWaveGenerator(60);
	// Generator g2 = new SineWaveGenerator(61);

	// ArrayList<Generator> generators = new ArrayList<>();
	// generators.add(g1);
	// generators.add(g2);
	// MultiGenerator mg = new MultiGenerator(generators);
	
	// GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(mg);
	// gav.drawAndPlay(500000, 1000000);
	// Generator generator = new SawToothGenerator(512);
    // GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
    // gav.drawAndPlay(4096, 1000000);

	Generator generator = new StrangeBitwiseGenerator(512);
    GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
    gav.drawAndPlay(4096, 1000000);
	}
} 