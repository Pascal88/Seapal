package en.htwg.seapal.gui;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import en.htwg.seapal.R;
import en.htwg.seapal.gui.listener.dashboard.SeekBarChangeListener;
import en.htwg.seapal.model.tables.TableLog;
import en.htwg.seapal.model.tables.TableLog.Tupel;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

public class DashboardView extends View {

	private List<Tupel<Date, Float>> list = null;
	private List<Tupel<Date, Float>> secList = null;
	private SeekBar seek = null;
	private int numElements = 0;
	
	public DashboardView(Context context) {
		super(context);
		init();
	}

	public DashboardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public DashboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public void setList(List<Tupel<Date, Float>> list) {
		this.list = list;
	}
	
	public void setSeekBar(SeekBar seek) {
		this.seek = seek;
		if(list != null) {
			this.seek.setMax(list.size());
		} else {
			this.seek.setMax(100);
		}
		this.seek.setProgress(this.seek.getMax());
		this.numElements = this.seek.getMax();
		this.seek.setOnSeekBarChangeListener(new SeekBarChangeListener(this));
	}

	private void init(){
		setBackgroundColor(Color.WHITE);
		secList = new ArrayList<Tupel<Date, Float>>();
		
		Time now = new Time();
		now.setToNow();
		Date tmp = new Date(now.toMillis(true));
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		
			for(int i = 0; i < 100; i++) {
				Tupel<Date, Float> t = new Tupel<Date, Float>(tmp, random.nextFloat() * 100);
				secList.add(t);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawGraphOuter(canvas);
		super.onDraw(canvas);
	}
	
	protected void drawGraphOuter(Canvas canvas){
		
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
		
		int startX = getWidth()/10;
		int startY = getHeight()/10;
		int endX = (startX*10)-bitmap.getHeight();
		int endY = (startY*9)-(bitmap.getWidth()/2);
		canvas.drawLine(startX, startY, startX, startY*9, paint);
		canvas.drawLine(startX, startY*9, endX, startY*9, paint);
		canvas.drawBitmap(bitmap, startX-(bitmap.getWidth()/2), startY-bitmap.getHeight(), paint);
		canvas.rotate(90, endX, endY);
		canvas.drawBitmap(bitmap, endX, endY, paint);
		canvas.rotate(270, endX, endY);
		
		float diffX = endX - startX;
		float stepX = diffX / 10;
		float diffY = (startY*9) - startY;
		float stepY = diffY / 10;
		for(int i = 0; i < 10; i++) {
			canvas.drawLine(startX + (stepX * i), (startY*9) - 10, startX + (stepX * i), (startY*9) + 10, paint);
			canvas.drawLine(startX - 10, (startY) + (stepY * (i+1)), startX + 10, (startY) + (stepY * (i+1)), paint);
		}
		
		diffX = (startX*9) - startX;
		Path path = new Path();
		path.moveTo(startX, startY*9);
		
		List<Tupel<Date, Float>> temp ;
		
		if(list == null) {
			temp = secList.subList(0, numElements);
			canvas.drawText("No Data availible", getWidth()/2, getHeight()/2, paint);
		} else {
			temp = list.subList(0, numElements);
		}
		
		float divider = 0;
		for(Tupel<Date, Float> t: temp) {
			if(t.b > divider) divider = t.b;
		}
		stepX = (diffX/temp.size());
		stepY = diffY/divider;
		
		Iterator<Tupel<Date, Float>> it = temp.iterator();
		for(int i = 0; i < numElements && i < temp.size() && it.hasNext(); i++) {
			Tupel<Date, Float> tmp = it.next();
			path.lineTo(startX + (stepX * i), (float) (startY*9 - (diffY * (tmp.b/ divider))));
			path.lineTo(startX + (stepX * i), startY*9);	
		
		}
		canvas.drawPath(path, paint);
	}
	
	public void setNumElements(int numElements) {
		this.numElements = numElements;
	}
}
