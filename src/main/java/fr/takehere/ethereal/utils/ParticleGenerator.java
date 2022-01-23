package fr.takehere.ethereal.utils;

import fr.takehere.ethereal.GameScene;
import fr.takehere.ethereal.components.Actor;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ParticleGenerator {

    public int amount;
    public int minSpeed;
    public int maxSpeed;
    public long lifeTime;

    public Vector2 location;
    public Dimension dimension;
    public Image texture;
    public boolean gravity;
    public GameScene scene;

    public static List<ParticleGenerator> particleGenerators = new ArrayList<>();

    public ParticleGenerator(Vector2 location, Dimension dimension, Image texture, boolean gravity, int amount, int minSpeed, int maxSpeed, long lifeTimeMs, GameScene scene) {
        this.location = location;
        this.dimension = dimension;
        this.gravity = gravity;
        this.amount = amount;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.lifeTime = lifeTimeMs;
        this.scene = scene;

        if (texture != null){
            this.texture = texture;
        }else {
            this.texture = ImageCreator.generateRandomColorImage(dimension.width, dimension.height);
        }

        particleGenerators.add(this);
    }

    public List<Actor> particles = new ArrayList<>();

    public void generate(){
        List<Actor> createdParticles = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Actor particle = new Actor("particle", location, dimension, texture, scene);
            particle.gravity = gravity;
            particle.velocity = MathUtils.randomDirection().multiply(MathUtils.randomNumberBetween(minSpeed, maxSpeed));

            particles.add(particle);
            createdParticles.add(particle);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (Actor createdParticle : createdParticles) {
                    createdParticle.destroy();
                    particles.remove(createdParticle);
                }
            }
        }, lifeTime);
    }
}
