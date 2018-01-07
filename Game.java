import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Game extends JFrame{
	
	private Image hoop, basketballIcon;
	private GamePanel game;
	
	
	public Game(){
		super("Basketball Game");
		hoop = new ImageIcon("Hoop.gif").getImage();
		basketballIcon = new ImageIcon("basketballIcon.png").getImage();
		game = new GamePanel(basketballIcon, hoop);		
		initialize();
	}
	
	private void initialize(){
		
		setSize(500,500);
		setLocationRelativeTo(null);
		setBackground(Color.white);
		setResizable(false);
		addKeyListener(game);
		add(game);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setIconImage(basketballIcon);
		
		game.run();//game offically starts with this method
		
		
	}
}
