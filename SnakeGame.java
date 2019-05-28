package at.kevwpl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class SnakeGame extends JFrame implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public int dead = 0;
	
	int started = 0;
	
	public int dir = 3;
	
	public int score = 0;
	
	public int snakePosX;
	public int snakePosY;
	
	public int foodX;
	public int foodY;
	
	public int[][] field = new int[17][17];

	public SnakeGame() {
        super("Snake");
        addWindowListener(new OwnWindowAdapter());
        this.setSize(570, 590);
    	setFocusable(true);
    	addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        this.setBackground(Color.WHITE);
        this.setMaximumSize(new Dimension(570,590));
        this.setMinimumSize(new Dimension(570,590));
		spawnFood();

        
    }
	
	public void start() {
		snakePosX = 4;
		snakePosY = 8;
		field[4][8] = score + 3;
		field[3][8] = score + 2;
		field[2][8] = score + 1;
		this.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}

	
	public void paint(Graphics g) {
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				if(i == 0 || i == 18 || j == 0 || j == 18) {
					Color col0 = new Color(0, 102, 0);
					g.setColor(col0);
				}else if(i%2 == j%2) {
					Color col1 = new Color(0, 255, 0);
					g.setColor(col1);
				}else {
					Color col2 = new Color(128, 255, 0);
					g.setColor(col2);
				}
					
				g.fillRect(i * 30, j * 30 + 20, 30, 30);	
				
			}
		}
		
		for(int i = 0; i < 17; i++) {
			for(int j = 0; j < 17; j++) {
				
				if(field[i][j] > 0 && field[i][j] < 199) {
					Color col4 = new Color(255 - field[i][j] * 2, field[i][j] * 2, 255 - field[i][j] * 2);
					g.setColor(col4);
					g.fillRect(i * 30 + 30, j * 30 + 30 + 20, 30, 30);
				}else if(field[i][j] > 199) {
					Color col5 = new Color(255, 0, 0);
					g.setColor(col5);
					g.fillOval(i * 30 + 30, j * 30 + 30 + 20, 30, 30);
				}
			}
		}
		
		
	}
	
	public void snakeMove() {
		if(dead == 0) {
			if(dir == 0) {
				snakePosY--;
			}else if(dir == 1) {
				snakePosX--;
			}else if(dir == 2) {
				snakePosY++;
			}else if(dir == 3) {
				snakePosX++;
			}
	
			for(int i = 0; i < 17; i++) {
				for(int j = 0; j < 17; j++) {
					
					if(field[i][j] > 0) {
						field[i][j]--;
					}
				}
			}
			if(snakePosX < 0 || snakePosX > 17 || snakePosY < 0 || snakePosY > 17) {
				dead++;
				System.out.println("You are dead!");
			}
			if(field[snakePosX][snakePosY] > 0 && field[snakePosX][snakePosY] < (score + 2)) {
				dead++;
				System.out.println("You are dead!");
			}
			field[snakePosX][snakePosY] = score + 3;
			
			
			if(foodX == snakePosX && foodY == snakePosY) {
				spawnFood();
				score++;
				System.out.println("Score: " + score);
			}
			repaint();
		}
		
	}
	
	public void spawnFood() {
		int value1;
		int value2;
		do{
			Random rand1 = new Random();
			value1 = rand1.nextInt(17);
			Random rand2 = new Random();
			value2 = rand2.nextInt(17);
		}while(field[value1][value2] != 0);
		field[value1][value2] = 999;
		foodX = value1;
		foodY = value2;
	}
	
	private void gameStart() {
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(new Runnable() {
	        @Override
	        public void run() {
	            snakeMove();
	        }
	    }, 0, 200 - score * 5, TimeUnit.MILLISECONDS);	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(started == 0) {
			gameStart();
			started++;
		}
		
		if(e.getKeyChar() == 'w' && dir != 2) {
			dir = 0;
		}else if(e.getKeyChar() == 'a' && dir != 3) {
			dir = 1;
		}else if(e.getKeyChar() == 's' && dir != 0) {
			dir = 2;
		}else if(e.getKeyChar() == 'd' && dir != 1) {
			dir = 3;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
