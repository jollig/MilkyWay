package is.ru.tgra;

import java.nio.FloatBuffer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.graphics.g3d.loaders.wavefront.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;

public class Galaxy_3D implements ApplicationListener, InputProcessor {
	// constant useful for logging
	public static final String LOG = Galaxy_3D.class.getSimpleName();
	// a libgdx helper class that logs the current FPS each second
	private FPSLogger fpsLogger;
	
	// create a instance of the camera. “Everything has beauty, but not everyone sees it.” ― Confucius
	private Camera cam;
	// all the distant stars, where no man has gone before, or ever
	private Sphere stars;
	// all the stars closest to us
	private Sphere sun;
	private Sphere mercury;
	private Sphere venus;
	private Sphere earth;
	private Sphere moon;
	private Sphere mars;
	private Sphere jupiter;
	private Sphere saturn;
	private Plain rings;
	private Sphere uranus;
	private Sphere neptune;
	private Sphere pluto;
	
	private float rotate_angle = 0;
	private float alpha = 0f;
	
	private StillModel model;

	@Override
	public void create() {
		//Texture.setEnforcePotImages(false);
		
		ObjLoader loader = new ObjLoader();
		model = loader.loadObj(Gdx.files.internal("assets/models/SpaceShuttleOrbiter.obj"), true);
		
		stars = new Sphere(30, 90, "stars_texture.jpg");
		sun = new Sphere(30, 90, "sun.jpg");
		mercury = new Sphere(20, 60, "mercury.jpg");
		venus = new Sphere(20, 60, "venus.bmp");
		earth = new Sphere(20, 60, "earth2.png");
		moon = new Sphere(20, 60, "moon.jpg");
		mars = new Sphere(20, 60, "mars.jpg");
		jupiter = new Sphere(30, 90, "jupiter.jpg");
		saturn = new Sphere(30, 90, "saturn.jpg");
		rings = new Plain("rings.png");
		uranus = new Sphere(30, 90, "uranus.jpg");
		neptune = new Sphere(30, 90, "neptune.jpg");
		pluto = new Sphere(20, 60, "pluto.jpg");
		
		Gdx.input.setInputProcessor(this);
		
		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		
		Gdx.gl11.glEnable(GL11.GL_LIGHT1);
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		
		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluPerspective(Gdx.gl11, 90, 1.333333f, 0.05f, 250.0f);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		FloatBuffer vertexBuffer = BufferUtils.newFloatBuffer(72);
		vertexBuffer.put(new float[] {-0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
									  0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
									  0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
									  0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
									  0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
									  -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f,
									  0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f,
									  0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f});
		vertexBuffer.rewind();

		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		cam = new Camera(new Point3D(19.5f, -30.0f, -7.0f), new Point3D(2.0f, 3.0f, 3.0f), new Vector3D(0.0f, 1.0f, 0.0f));
		//cam = new Camera(new Point3D(0.0f, 0.0f, 0.0f), new Point3D(2.0f, 3.0f, 3.0f), new Vector3D(0.0f, 1.0f, 0.0f));
		//cam = new Camera(new Point3D(1.5f, 2.5f, 1.5f), new Point3D(2.5f, 2.5f, 2.5f), new Vector3D(0.0f, 1.0f, 0.0f));
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		update();
		display();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	public void update(){
		// let there be light
		Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		this.rotate_angle += 10 * deltaTime;
		
		// look up and down - for now leave them out
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) 
			cam.pitch(-90.0f * deltaTime);
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) 
			cam.pitch(90.0f * deltaTime);
		
		// look to the sides
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			cam.yaw(-90.0f * deltaTime);
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 
			cam.yaw(90.0f * deltaTime);
		
		// moving back, forth and to the sides
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.slide(0.0f, 0.0f, -2.5f * deltaTime);
			//playSteps("assets/sounds/footstep.wav");
		}	
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			cam.slide(0.0f, 0.0f, 2.5f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			cam.slide(-2.5f * deltaTime, 0.0f, 0.0f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			cam.slide(2.5f * deltaTime, 0.0f, 0.0f);
		}
		
		// no need for these bad boys
		if(Gdx.input.isKeyPressed(Input.Keys.R)) 
			cam.slide(0.0f, 10.0f * deltaTime, 0.0f);
		if(Gdx.input.isKeyPressed(Input.Keys.F)) 
			cam.slide(0.0f, -10.0f * deltaTime, 0.0f);
	}
	
	public void display(){
		// display x, y & z coordinates of the camera eye
		Gdx.app.log(Galaxy_3D.LOG, "x: " + cam.getEye().x + " y: " + cam.getEye().y + " z: " + cam.getEye().z);
		
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		cam.setModelViewMatrix();		
		/*
		// Configure light 0
		float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse, 1);
		float[] lightPosition = {0.0f, 0.0f, 0.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition, 1);
		float[] lightAmbience = {0f, 0f, 0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_AMBIENT, lightAmbience, 1);
		
		// Configure light 1
		float[] lightDiffuse1 = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, lightDiffuse1, 0);
		float[] lightPosition1 = {-50.0f, 0.0f, 0.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPosition1, 0);
		
		// Configure light 2
		float[] lightDiffuse2 = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, lightDiffuse2, 0);
		float[] lightPosition2 = {0f, -20.0f, 0.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT2, GL11.GL_POSITION, lightPosition2, 0);
		
		// Configure light 3
		float[] lightDiffuse3 = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT3, GL11.GL_DIFFUSE, lightDiffuse3, 0);
		float[] lightPosition3 = {0f, 20.0f, 0.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT3, GL11.GL_POSITION, lightPosition3, 0);
		
		// Configure light 6
		float[] lightDiffuse6 = {0.1f, 0.1f, 0.1f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT6, GL11.GL_DIFFUSE, lightDiffuse6, 0);
		float[] lightPosition6 = {cam.getEye().x, cam.getEye().y, cam.getEye().z, 0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT6, GL11.GL_POSITION, lightPosition6, 0);
		
		// Configure light 7
		float[] lightDiffuse7 = {0.1f, 0.1f, 0.1f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT7, GL11.GL_DIFFUSE, lightDiffuse7, 0);
		float[] lightPosition7 = {100.0f, 100.0f, 100.0f, 0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT7, GL11.GL_POSITION, lightPosition7, 0);*/
		
		// veit ekkert hvað þetta gerir - skoða seinna
		//Gdx.gl11.glLightfv(GL11.GL_LIGHT2, GL11.GL_AMBIENT, new float[] { 0.2f, 0.2f, 0.2f, 1.0f }, 0);
		
		/**
		 * Moving and scaling
		 * Everthing starts at origin at 1/1 scale. This procedure is used when drawing and positioning objects:
		 * Moving, scaling, drawing, re-scaling, moving to origin - repeat as needed
		 */
		Gdx.gl11.glPushMatrix();
			// sun drawn without lighting so it can be illuminated and give the effect of a light source in the center of our galaxy		
			Gdx.gl11.glDisable(GL11.GL_LIGHTING);
			
			// scale before drawing the sun making it "bigger"
			Gdx.gl11.glScalef(10.9f, 10.9f, 10.9f);
			sun.draw();
			
			// scale down to original size
			Gdx.gl11.glScalef(0.092f, 0.092f, 0.092f);
			
			// not working properly - the idea is to make the universe follow the camera to appear still (affects all the rest)
			// this would also allow the drawing to be smaller and save resources
			//Gdx.gl11.glTranslatef(this.cam.getEye().x, this.cam.getEye().y, this.cam.getEye().z);
			//Gdx.gl11.glTranslatef(0, 0, 0);
			
			// scale up to draw the universe
			Gdx.gl11.glScalef(60.0f, 60.0f, 60.0f);
			stars.draw();
			// scale back down to original size
			Gdx.gl11.glScalef(0.017f, 0.017f, 0.017f);
			
			// lighting enabled to make dark side of planets facing away from the sun
			Gdx.gl11.glEnable(GL11.GL_LIGHTING);
			
			// Configure light 0
			// the sun is the center of the galaxy and the light source. it casts light upon all the planets
			float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 0.0f};
			Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse, 0);
			float[] lightPosition = {0f, 0f, 0f, 1.0f};
			Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition, 0);
			float[] lightAmbience = {0.1f, 0.1f, 0.1f, 1.0f};
			Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_AMBIENT, lightAmbience, 0);
			
			// Configure light 1
			// a faint light is positioned behind earth. this gives effect of night lights being visible in cities in North America e.g.
			float[] lightDiffuse1 = {0.1f, 0.1f, 0.1f, 1.0f};
			Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, lightDiffuse1, 0);
			float[] lightPosition1 = {50.0f, 0.0f, 0.0f, 0f};
			Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPosition1, 0);
			
			Gdx.gl11.glPushMatrix();
			
				// ekki að gera sig. öll model sem ég prófa (nema Batman) fokkast upp
				//Gdx.gl11.glTranslatef(20.0f, 0.0f, 15.0f);
				model.render();
				//Gdx.gl11.glTranslatef(-20.0f, 0.0f, -15.0f);
					
				
				// rotate planet around origin, the sun
				// find a way to stop the rest from spinning
				//Gdx.gl11.glRotatef(rotate_angle, 1, 1, 1);
			
				// MERCURY
				// still at original point, move from there to new drawing point
				Gdx.gl11.glTranslatef(0.0f, 20.0f, 0.0f);
				// scale down before drawing smaller planet
				Gdx.gl11.glScalef(0.382f, 0.382f, 0.382f);
				//Gdx.gl11.glRotatef(rotate_angle, 1, 1, 1);
				mercury.draw();
				// return scale to original size
				Gdx.gl11.glScalef(2.618f, 2.618f, 2.618f);
				// return to point of origin
				Gdx.gl11.glTranslatef(0.0f, -20.0f, 0.0f);
				
				// VENUS
				// move to a new drawing point
				Gdx.gl11.glTranslatef(20.0f, 20.0f, 10.0f);
				// scale to draw a new planet
				Gdx.gl11.glScalef(0.949f, 0.949f, 0.949f);
				venus.draw();
				// return scale to original size				
				Gdx.gl11.glScalef(1.054f, 1.054f, 1.054f);
				// return to origin
				Gdx.gl11.glTranslatef(-20.0f, -20.0f, -10.0f);
				
				// EARTH
				// no scaling needed for earth
				Gdx.gl11.glPushMatrix();
					Gdx.gl11.glTranslatef(20.0f,  0f, 0f);
					// turn the "night" part of earth away from the sun
					Gdx.gl11.glRotatef(60, 0, 10, 0);
					earth.draw();
					// make the moon rotate around earth
					Gdx.gl11.glRotatef(rotate_angle, 0, 0, 1);
					//Gdx.gl11.glTranslatef(-20.0f,  0f, 0f);
				
					// THE MOON
					Gdx.gl11.glTranslatef(1.0f, 2.0f, 1.5f);
					Gdx.gl11.glScalef(0.23f, 0.23f, 0.23f);
					moon.draw();
					Gdx.gl11.glScalef(4.348f, 4.348f, 4.348f);
					Gdx.gl11.glTranslatef(-21.0f, -2.0f, -1.5f);
				Gdx.gl11.glPopMatrix();
				
				// MARS
				Gdx.gl11.glTranslatef(20.0f, -5.0f, -10.0f);
				Gdx.gl11.glScalef(0.532f, 0.532f, 0.532f);
				mars.draw();
				Gdx.gl11.glScalef(1.88f, 1.88f, 1.88f);
				Gdx.gl11.glTranslatef(-20.0f, 5.0f, 10.0f);
				
				// JUPITER
				Gdx.gl11.glDisable(GL11.GL_LIGHTING);
				Gdx.gl11.glTranslatef(5.0f, -30.0f, 0.0f);
				Gdx.gl11.glScalef(11.209f, 11.209f, 11.209f);
				Gdx.gl11.glRotatef(60, 0, 10, 0);
				jupiter.draw();
				Gdx.gl11.glScalef(0.089f, 0.089f, 0.089f);
				Gdx.gl11.glTranslatef(-5.0f, 30.0f, 0.0f);
				Gdx.gl11.glEnable(GL11.GL_LIGHTING);

				// URANUS
				Gdx.gl11.glTranslatef(-5.0f, -20.0f, 10.0f);
				Gdx.gl11.glScalef(0.026f, 0.026f, 0.026f);
				uranus.draw();
				Gdx.gl11.glScalef(38.46f, 38.46f, 38.46f);
				Gdx.gl11.glTranslatef(5.0f, 20.0f, -10.0f);
								
				// NEPTUNE
				Gdx.gl11.glTranslatef(-20.0f, 0.0f, 0.0f);
				Gdx.gl11.glScalef(0.25f, 0.25f, 0.25f);				
				neptune.draw();
				Gdx.gl11.glScalef(4f, 4f, 4f);
				Gdx.gl11.glTranslatef(20.0f, 0.0f, 0.0f);
				
				// PLUTO
				Gdx.gl11.glTranslatef(-20.0f, 5.0f, -10.0f);
				Gdx.gl11.glScalef(0.18f, 0.18f, 0.18f);
				pluto.draw();
				Gdx.gl11.glScalef(5.56f, 5.56f, 5.56f);
				Gdx.gl11.glTranslatef(20.0f, -5.0f, 10.0f);
				
				// SATURN AND IT'S RINGS
				Gdx.gl11.glDisable(GL11.GL_LIGHTING);
				Gdx.gl11.glTranslatef(-5.0f, 40.0f, 0.0f);
				Gdx.gl11.glScalef(9.449f, 9.449f, 9.449f);
				saturn.draw();
				Gdx.gl11.glScalef(4.5f, 4.5f, 4.5f);
				rings.draw();
			Gdx.gl11.glPopMatrix();
		Gdx.gl11.glPopMatrix();
	}

	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
