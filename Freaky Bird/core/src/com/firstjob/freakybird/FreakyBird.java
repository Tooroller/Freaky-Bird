package com.firstjob.freakybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class FreakyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird ;
	Texture ufo1;
	Texture ufo2;
	Texture ufo3;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.9f;
	Random random;
	Circle birdCircle;
	int score = 0;
	int scoredEnemy = 0;

	ShapeRenderer shapeRenderer;

	Circle [] enemyCircle;
	Circle [] enemyCircle2;
	Circle [] enemyCircle3;

	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];
	float [] enemyOffSet = new float[numberOfEnemies];
	float [] enemyOffSet2 = new float[numberOfEnemies];
	float [] enemyOffSet3 = new float[numberOfEnemies];
	float distance = 0;

	float enemyVelocity = 20;
	BitmapFont font;
	BitmapFont font2;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		ufo1 = new Texture("ufo.png");
		ufo2 = new Texture("ufo.png");
		ufo3 = new Texture("ufo.png");
		birdX=Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2;
		birdY=Gdx.graphics.getHeight() / 3;
		random = new Random();

		distance = Gdx.graphics.getWidth() / 2;

		shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircle= new Circle[numberOfEnemies];
		enemyCircle2= new Circle[numberOfEnemies];
		enemyCircle3= new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.BLACK);
		font2.getData().setScale(6);


		birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2;
		for (int i = 0; i<numberOfEnemies; i++){
			enemyCircle[i] = new Circle();
			enemyCircle2[i] = new Circle();
			enemyCircle3[i] = new Circle();

			enemyOffSet [i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet2 [i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet3 [i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i]= Gdx.graphics.getWidth() - ufo1.getWidth() / 2 + i * distance;
		}


	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


			if (gameState == 1){
				if (enemyX[scoredEnemy] <Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2) {
					score++;
					if (scoredEnemy < numberOfEnemies - 1){
						scoredEnemy++;
					}else{
						scoredEnemy = 0;
					}

				}

				if (Gdx.input.justTouched()){
					velocity= -15;
				}
				for (int i = 0; i <numberOfEnemies; i++){




					if (enemyX[i] < Gdx.graphics.getWidth()/15) {
						enemyX[i] = enemyX [i] =numberOfEnemies * distance;
						enemyOffSet [i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						enemyOffSet2 [i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						enemyOffSet3 [i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					}else{
						enemyX[i] = enemyX [i] - enemyVelocity;
					}




					batch.draw(ufo1,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffSet [i],Gdx.graphics.getWidth() /13,Gdx.graphics.getHeight()/9);
					batch.draw(ufo2,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffSet2 [i],Gdx.graphics.getWidth() /13,Gdx.graphics.getHeight()/9);
					batch.draw(ufo3,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffSet3 [i],Gdx.graphics.getWidth() /13,Gdx.graphics.getHeight()/9);

					enemyCircle[i] =new Circle(enemyX[i] + Gdx.graphics.getWidth() /26, Gdx.graphics.getHeight() / 2 + enemyOffSet [i] +Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth() /26);
					enemyCircle[i] =new Circle(enemyX[i] + Gdx.graphics.getWidth() /26, Gdx.graphics.getHeight() / 2 + enemyOffSet [i] +Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth() /26);
					enemyCircle[i] =new Circle(enemyX[i] + Gdx.graphics.getWidth() /26, Gdx.graphics.getHeight() / 2 + enemyOffSet [i] +Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth() /26);
				}






				if (birdY > 0 ){
					velocity = velocity + gravity ;
					birdY = birdY - velocity;
				}else{
					gameState =2;

				}

		}else if(gameState ==0) {
				if (Gdx.input.justTouched()) {
					gameState = 1;
			}

				} else if (gameState == 2) {
				font2.draw(batch,"Game Over! Tap To Play Again!",100,Gdx.graphics.getHeight() /2);
					if (Gdx.input.justTouched()) {
						gameState = 1;


						birdY = Gdx.graphics.getHeight() / 3;
						for (int i = 0; i < numberOfEnemies; i++) {

							enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
							enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
							enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

							enemyX[i] = Gdx.graphics.getWidth() - ufo1.getWidth() / 2 + i * distance;

							enemyCircle[i] = new Circle();
							enemyCircle2[i] = new Circle();
							enemyCircle3[i] = new Circle();


						}

						velocity = 0;
						scoredEnemy = 0;
						score = 0;
					}


				}




		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth() /13,Gdx.graphics.getHeight()/9);
		font.draw(batch,String.valueOf(score),100,200);
		batch.end();
		birdCircle.set(birdX + Gdx.graphics.getWidth() /26,birdY + Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth() /26);

		/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);
		*/

		for (int i =0; i< numberOfEnemies; i++){

			/*shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() /26, Gdx.graphics.getHeight() / 2 + enemyOffSet [i] +Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth() /26);
			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() /26, Gdx.graphics.getHeight() / 2 + enemyOffSet2 [i] +Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth() /26);
			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() /26, Gdx.graphics.getHeight() / 2 + enemyOffSet3 [i] +Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth() /26);

			 */
			if (Intersector.overlaps(birdCircle,enemyCircle[i])|| Intersector.overlaps(birdCircle,enemyCircle2[i]) || Intersector.overlaps(birdCircle,enemyCircle3[i])){
				gameState =2;
			}



		}

	}
	
	@Override
	public void dispose () {

	}
}
