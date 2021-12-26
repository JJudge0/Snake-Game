import javax.swing.JFrame;
public class GameFrame extends JFrame{

	
	GameFrame(){
	///////////// Short cut///////////////
		// this.add(new GamePanel());	
    ///////////// Short cut///////////////	
		GamePanel panel = new GamePanel(); // Creates new game panel with the name
		this.add(panel);
		this.setTitle("Snake Game by JJudge0");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); //NOTE:Set to false so it cannot be editted. Also to be false other creates white spacing around the frame. 
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		

		
	}
}
