package com.example.sudoku;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;


import android.widget.TextView;
public class MainActivity extends Activity implements OnClickListener {
	
	char[] init1 = {'0','0','0','0','1','5','3','0','2',
					'0','3','1','2','0','0','0','4','0',
					'8','6','0','4','0','0','9','5','0',
					
					'9','0','0','6','0','7','0','0','0',
					'0','5','0','3','0','2','0','9','0',
					'0','0','0','1','0','9','0','0','7',
					
					'0','7','3','0','0','8','0','1','4',
					'0','1','0','0','0','4','6','8','0',
					'4','0','8','5','6','0','0','0','0'};
	/*	char[] init1 = {'7','4','9','8','1','5','3','6','2',
					'5','3','1','2','9','6','7','4','8',
					'8','6','2','4','7','3','9','5','1',
					
					'9','2','4','6','8','7','1','3','5',
					'1','5','7','3','4','2','8','9','6',
					'3','8','6','1','5','9','4','2','7',
					
					'6','7','3','9','2','8','5','1','4',
					'2','1','5','7','3','4','6','8','9',
					'4','9','8','5','6','1','2','7','0'};
	*/	
	Button restartButton;
	Button[][] buttons;
	Button[] digits; 
	Button submitButton;
	Button clearButton;
	TextView message;
	int width, width1;
	RelativeLayout mainLayout;
	InputStream inputs = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		RelativeLayout mainLayout = new RelativeLayout(this);
		TableLayout board = new TableLayout(this);
		MyView grid = new MyView(this);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		//int height = metrics.heightPixels;
		restartButton = new Button(this);
		restartButton.setText("Restart");
		restartButton.setClickable(false);
		restartButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				indexI = indexJ = 10;
				for(int i=0; i<9; i++){
					for(int j=0; j<9; j++){
						buttons[i][j].setBackgroundColor(Color.GRAY);
						
						if(init1[i*9+j]!='0'){
							buttons[i][j].setText(init1[i*9+j]+"");
							buttons[i][j].setTypeface(null, Typeface.BOLD);
						}
						else
							buttons[i][j].setText(" ");
					}
				}
			}
		});
		
		width = Math.round(metrics.widthPixels/9);
		width1 =Math.round(metrics.widthPixels/10);
		buttons = new Button[9][9];
		digits = new Button[9];
		submitButton = new Button(this);
		message = new TextView(this);
		
		
		TableRow rowDigits = new TableRow(this);
		//TableRow row2 = new TableRow(this);
		TableRow[] rows = new TableRow[9];
		
		
		//MyView myView = new MyView(this);
		//board.addView(myView);
		submitButton.setText("submitButton");
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(checkIfFull()){
				if(checkIfCorrect())
					message.setText("Congratulations");
				else
					message.setText("Your Solution is Incorrect");
			}
				else 
					message.setText("The Board is Not Full");
			}
			
		});
	
		
		for(int i=0; i<9; i++){
			rows[i] = new TableRow(this);
			for(int j=0; j<9; j++){
					buttons[i][j] = new Button(this);
					buttons[i][j].setWidth(width);
					buttons[i][j].setHeight(width);
					buttons[i][j].setId(j+9*i);
					buttons[i][j].setBackgroundColor(Color.GRAY);
					
					if(init1[i*9+j]!='0'){
						buttons[i][j].setText(init1[i*9+j]+"");
						buttons[i][j].setTypeface(null, Typeface.BOLD);
					}
					else
						buttons[i][j].setText(" ");
					buttons[i][j].setOnClickListener(this);
					
					rows[i].addView(buttons[i][j]);
					//rows[i].setTop(width*i + 2);
				
			}
			board.addView(rows[i]);
		}
		int width1 =Math.round(((9*width)/10));
		//board.setBackgroundColor(Color.WHITE);
		for(int k=0; k<9; k++){
			
			digits[k] = new Button(this);
			digits[k].setWidth(width1);
			digits[k].setId(81+k);
			digits[k].setOnClickListener(this);	
			digits[k].setText(Integer.toString(k+1));
			rowDigits.addView(digits[k]);
		}
		
		clearButton = new Button(this);
		clearButton.setText("C");
		clearButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(indexJ!=10){
					buttons[indexI][indexJ].setBackgroundColor(Color.GRAY);
					buttons[indexI][indexJ].setText(" ");
					indexI=indexJ=10;
				}
			}
		});
		
		board.addView(rowDigits);
		board.addView(submitButton);
		board.addView(message);
		board.addView(clearButton);
		board.addView(restartButton);
		//board.addView(row2);
		mainLayout.addView(board);
		mainLayout.addView(grid);
		setContentView(mainLayout);
		

		//setContentView(R.layout.activity_main);
		//i.setAdjustViewBounds(true);
		//i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		//myLinearLayout.addView(i);
		
	}
	
	public class MyView extends View {
        public MyView(Context context) {
             super(context);
             // TODO Auto-generated constructor stub
        }

		@Override
        public void onDraw(Canvas canvas) {
           // TODO Auto-generated method stub
            
        	super.onDraw(canvas);
        	
        	Paint paintBorderLine = new Paint();
        	paintBorderLine.setStrokeWidth(3);
        	Paint paintInnerLine = new Paint();
        	paintInnerLine.setStrokeWidth(1);
        	for(int i=0; i<4; i++){
        		canvas.drawLine(3*width*i, 0, 3*width*i, 9*width, paintBorderLine);
        		canvas.drawLine(0, 3*width*i, 9*width, 3*width*i, paintBorderLine);
        	}
        	for(int i=0; i<10; i++){
        		canvas.drawLine(width*i, 0, width*i, 9*width, paintInnerLine);
        		canvas.drawLine(0, width*i, 9*width, width*i, paintInnerLine);
        	}
        	
        	
        	           
           //canvas.drawCircle(x / 2, y / 2, radius, paint);
          /* InputStream inputs = null;
   		   Log.i("Tag", "********************************************");
           try {
        	   inputs = this.getResources().getAssets().open("android3d.png");
   			} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   			}
   		
   		
   			//i.setImageBitmap(BitmapFactory.decodeStream(is));
           Bitmap icon;
           icon = BitmapFactory.decodeStream(inputs);
           //canvas.drawBitmap(icon, x/2, y/2, new Paint());
          */
        }
	

	}


	int indexI=10; // will keep the i coordinate of clicked button
	int indexJ=10;
	
	@Override
	public void onClick(View v) {
		
		// TODO Auto-generated method stub
		int m = v.getId();
		restartButton.setClickable(true);
		
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(m == buttons[i][j].getId())
						{
							if(indexJ!=10){
								buttons[indexI][indexJ].setBackgroundColor(Color.GRAY);
								indexI=indexJ=10;
							}
							
							if(init1[i*9+j] == '0'){
								indexI=i;
								indexJ=j;
								buttons[i][j].setBackgroundColor(Color.WHITE);
							}
							
						}
				}
			
		}
		
		
		for(int k=0; k<9; k++){
			if(m == digits[k].getId() && indexJ!=10){
					buttons[indexI][indexJ].setText(Integer.toString(k+1));
					buttons[indexI][indexJ].setBackgroundColor(Color.GRAY);
					indexJ=10;
					break;
					}
		}
		
	}
	public int filledNumber;
	public boolean checkIfFull(){
		filledNumber=0;
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(buttons[i][j].getText() != " ")
					filledNumber++;
				
			}
			
		}
		if(filledNumber == 81)
			return true;
		else
			return false;
	}
	
	int a=223092870;
	int[] primes={2,3,5,7,11,13,17,19,23};
	
	public boolean checkIfCorrect(){
		int b,c;
		for(int i=0;i<9; i++){
			b=c=a;
			for(int j=0; j<9; j++)	{
				if(b%primes[Integer.parseInt((String)buttons[i][j].getText())-1] != 0)
					return false;
				else
					b/=primes[Integer.parseInt((String)buttons[i][j].getText())-1];
				
			}
			for(int j=0; j<9; j++)	{
				if(c%primes[Integer.parseInt((String)buttons[j][i].getText())-1] != 0)
					return false;
				else
					c/=primes[Integer.parseInt((String)buttons[j][i].getText())-1];
				
			}
		}
		return true;
	}
	
	/*public void winning(){
  		   //Log.i("Tag", "********************************************");
          try {
       	   inputs = this.getResources().getAssets().open("android3d.png");
  			} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  			}
  		
  		
  			//i.setImageBitmap(BitmapFactory.decodeStream(is));
          Bitmap icon;
          icon = BitmapFactory.decodeStream(inputs);
          
          ImageView image = new ImageView(this);
          image.setImageBitmap(icon);
          
	}*/
}

	
