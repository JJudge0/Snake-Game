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
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		

		
	}
}
