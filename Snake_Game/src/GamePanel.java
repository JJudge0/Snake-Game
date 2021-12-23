import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public  class GamePanel extends JPanel implements ActionListener {

	
	public static void main(String[] args) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(Running) 
		{
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	static final int SCREEN_WIDTH =600;
	static final int SCREEN_HEIGHT =600;
	static final int UNIT_SIZE =25; //objects in the game that will be on the grid ( GRID SIZE)
	static final int GAME_UNITS= (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; 
	static final int DELAY= 75; // Higher number slower the game is
	final int x[]= new int[GAME_UNITS];
	final int y[]= new int[GAME_UNITS];
	int Body_Parts =6;// body parts for the snake
	int Apples_Eaten;
	int AppleX;
	int AppleY;
	char Direction= 'R';  // Where does the snake on the board start the game:    'R'= Right, 'D'= Down, 'L'= Left, 'U'= Up
	boolean Running= false;
	Timer Timer;
	Random Random;
	
	GamePanel()
	{
        Random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        StartGame();
        
	}

	public void StartGame()
	{
		newApple();
		Running= true;
		Timer= new Timer(DELAY,this);
		Timer.start();
		
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		draw(g);
		
		
	}
	public void draw (Graphics g) // Draws all the visual aspects on the application
	{ 
		if (Running)
		{
			// Grid allows visual dynamic so we can see where the snake and apple is on the grid! NOTE: COMMENT OUT LATER ON!!
			for( int i=0; i<SCREEN_HEIGHT/UNIT_SIZE;i++)
			{
				g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE, SCREEN_HEIGHT);// Draws Y axis lines
				g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH, i*UNIT_SIZE); // Draws X axis lines
				//	g.drawLine(i*UNIT_SIZE,0,SCREEN_WIDTH, i*UNIT_SIZE); // draws a spiral  (NOT NEEDED) testing
			}
			////////////////
			g.setColor(Color.red);
			g.fillOval(AppleX, AppleY, UNIT_SIZE, UNIT_SIZE);


			//Draws head of the snake
			for( int i=0; i< Body_Parts; i++)
			{
				if (i==0)
				{
					g.setColor(Color.green);
					g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else
				{
					g.setColor(new Color(45,180,0));
					g.fillRect(x[i],y[i],UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial",Font.BOLD,20)); // Gives the output message a font/ Size of the text
			FontMetrics metrics = getFontMetrics(g.getFont());
			String Message= "Score:"+ Apples_Eaten;
			g.drawString(Message,(SCREEN_WIDTH- metrics.stringWidth(Message))/2,g.getFont().getSize());
		}
		else
		{
			gameover(g);
		}
	}
	public void newApple()// Populates game with apple when scoring a point
	{ // Generates new apple coordinates  when the game starts, snake eats, or a point is collected.
		AppleX = Random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		AppleY = Random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	
	}
	public void move ()
	{
		for(int i = Body_Parts;i>0;i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(Direction) {
		case 'U': //Goes Up using Y axis
			y[0] =y[0] - UNIT_SIZE;
		break;
		case 'D': //Goes Down using Y axis
			y[0] =y[0] + UNIT_SIZE;
		break;
		case 'L': //Goes Left using x axis
			x[0] =x[0] - UNIT_SIZE;
		break;
		case 'R': //Goes Right using x axis
			x[0] =x[0] + UNIT_SIZE;
		break;
		
		}
		
	}
	public void checkApple ( )
	{
		if((x[0] == AppleX) && (y[0] == AppleY)) {
		Body_Parts++;
		Apples_Eaten++;
		newApple();
		}
		
	}
	public void checkCollisions()
	//Checks if head collides with body
	{
		for( int i=Body_Parts;i>0;i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				Running =false;
			}
		}
		//Checks if head touches the left border
		if(x[0] < 0) {
			Running=false;
		}
		//Checks if head touches the right border
		if(x[0] > SCREEN_WIDTH) {
			Running=false;
		}
		//Checks if head touches the top border
		if(y[0] <0) {
			Running=false;
		}
		//Checks if head touches the bottom border
		if(y[0] > SCREEN_HEIGHT) {
			Running=false;
		}
		//Checks if head touches any border or bodypart it should stop
		if(!Running) {
			Timer.stop();
		}
	}
	public void gameover ( Graphics g)
	{
		//game overtext
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.BOLD,45)); // Gives the output message a font/ Size of the text
		FontMetrics metrics = getFontMetrics(g.getFont());
		String Game_Over_Message= "Game Over!!!";
		String Score = "Score:" + Apples_Eaten;
		g.drawString(Game_Over_Message + Score,(SCREEN_WIDTH- metrics.stringWidth(Game_Over_Message))/2,SCREEN_HEIGHT/2);
	}
	
	
	public class MyKeyAdapter extends KeyAdapter{
		 @Override // Important use for controls 
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(Direction != 'R') {
					Direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(Direction != 'L') {
					Direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(Direction != 'D') {
					Direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(Direction != 'U') {
					Direction = 'D';
				}
				break;
			}
		}
	
	}

	
}
