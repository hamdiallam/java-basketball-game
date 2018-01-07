import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements KeyListener{
	//fields
	private int x, i;
	private Image ball, hoop;
	private int velX, velY;
	private int ballX, ballY;
	private int lives, makes, misses;
	private boolean MOVING, ENDOFGAME, RESTART; //flags 

	
	
	
	public GamePanel(Image ball, Image hoop){
		this.ball = ball;
		this.hoop = hoop;	
		ballX = 0; ballY = 400;
		velX = 5; velY = 10;
		MOVING = true; 
		makes = 0; misses = 0;
		lives = 5;
		setBackground(Color.WHITE);
		ENDOFGAME = false; RESTART = false;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < lives; i++){
			g.drawImage(ball, 465 - (i*25), 45, 25, 25, null);
		}
		
		//draws the strings at the end of the game
		if(ENDOFGAME){
			g.setFont(new Font("Times New Roman", Font.BOLD, 40));
			g.drawString("Makes: " + makes, 173, 250);
			g.setFont(new Font("serif", Font.ITALIC, 21));     
			g.drawString("spacebar to restart" , 174, 275);
		}
		g.setFont(new Font("serif", Font.PLAIN, 25)); //counter for makes
		g.drawString("" + makes, 10, 27);
		g.drawImage(hoop, 190, 0 , 125, 125, null);  //draws the hoop image
		g.drawImage(ball, ballX, ballY, 50, 50, null); // draws the basketball
	}
	
	public boolean checkBall(){
		if(ballX >= 200 && ballX <= 255  && ballY < 80)
			return true;
		return false;	
	}
	
	public void playSound(int x){
		//buzzer sound
		if(x == 1){
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("endOfGame.wav").getAbsoluteFile()));
				clip.start();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		//swish sound
		else{
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("swish.wav").getAbsoluteFile()));
				clip.start();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	//game engine. whole game runs in this method
	public void run(){	
		//loop for basketball moving side to side
		for(i = 0; i <= (450/velX) * 2; i = i + 1){
			//basketball has been shot
			if(!MOVING){ 
				for(int x = 0; x <= 415/velY; x++){
					//basketball is missed
					if(x == 415/velY){
						ballY = 415;
						lives--;
						misses++;
						MOVING = !MOVING;
						break;
					}
					ballY = ballY - velY;
					repaint();
					try{
						Thread.sleep(20);
					} catch(InterruptedException e){
						e.printStackTrace();
					}
					//check for a made basket
					if(checkBall()){
						MOVING = !MOVING;
						playSound(-1);
						makes++;
						try{
							Thread.sleep(50);
						} catch(InterruptedException e){
							e.printStackTrace();
						}
						velX = velX + 1;
						velY = velY + 1;
						i = 0;
						ballX = 0;
						ballY = 400;
						break;
					}
				}
			}
			if(i == (450/velX * 2)) // prevents ball for moving alll the way past the rim
				i = 0;
			if(i < 450/velX){
				ballX = ballX + velX;
				repaint();
				try{
					Thread.sleep(20);
				} catch(InterruptedException e){
					e.printStackTrace();
				}
			}
						
			else{
				ballX = ballX - velX;
				repaint();
				try{
					Thread.sleep(20);
				} catch(InterruptedException e){
					e.printStackTrace();
				}
			}	
			
			if(lives == 0 && !ENDOFGAME){ //"holds" the loop temporarily so waiting for the player to restart the game
				playSound(1);
				ballX = -10000; //to make it seem like the ball dissapears
				
				ENDOFGAME = true; //prevents the buzzer for sounding infinetly until the spacebar is pressed
			}	
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//for a restart
		if(ENDOFGAME){
			ballX = 0;
			ballY = 400;
			lives = 5;
			i = 0;
			velX = 5; velY = 10;
			makes = 0; misses = 0;
			ENDOFGAME = false;
			repaint();
			try{
				Thread.sleep(50);
			} catch(InterruptedException ex){
				ex.printStackTrace();
			}
			return;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			MOVING = !MOVING;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}